package jpf;

import java.util.ArrayList;

import config.CaseStudy;
import database.RedisClient;
import gov.nasa.jpf.Config;
import gov.nasa.jpf.JPF;
import gov.nasa.jpf.ListenerAdapter;
import gov.nasa.jpf.search.Search;
import jpf.common.HeapJPF;
import jpf.common.OC;
import redis.clients.jedis.Jedis;
import server.Application;
import server.ApplicationConfigurator;
import server.instances.Redis;
import utils.GFG;

public class SequenceState extends ListenerAdapter {

	private int DEPTH = CaseStudy.DEPTH;
	private int BOUND = CaseStudy.BOUND;
	private boolean DEPTH_FLAG = CaseStudy.DEPTH_FLAG;
	private boolean BOUND_FLAG = CaseStudy.BOUND_FLAG;
	private int COUNT = 0;
	private int SEQ_UNIQUE_COUNT = 0;

	private int STARTUP = 1;
	private ArrayList<OC> seq;
	private Jedis jedis = null;
	private HeapJPF heapJPF = null;
	
	private boolean is_publish = true;
	private int nextDepth = 0;
	

	public SequenceState(Config conf, JPF jpf) {
		initialize();
		jedis.flushAll();
	}
	
	public SequenceState(int currentDepth) {
		if (CaseStudy.IS_BOUNDED_MODEL_CHECKING) {
			if (currentDepth + DEPTH >= CaseStudy.MAX_DEPTH) {
				DEPTH = CaseStudy.MAX_DEPTH - currentDepth;
				is_publish = false;
			}
		}
		this.nextDepth = currentDepth + DEPTH;
		initialize();
	}

	private void initialize() {
		Application app = ApplicationConfigurator.getInstance().getApplication();
		Redis redis = app.getRedis();
		jedis = RedisClient.getInstance(redis.getHost(), redis.getPort()).getConnection();
		seq = new ArrayList<OC>();
		heapJPF = app.getHeapJPF();
		app.getCaseStudy().printConfiguration();
	}

	public String seqToString() {
		if (seq.size() == 0) {
			return "nil";
		}
		int j = -1;
		for (int i = 0; i < seq.size(); i++) {
			if (seq.get(i).isReady()) {
				j = i;
				break;
			}
		}
		if (j == -1) {
			return "nil";
		}
		
		OC config = seq.get(j);
		StringBuffer sb = new StringBuffer();
		sb.append("(");
		sb.append(config);
		
		for (int i = j + 1; i < seq.size(); i++) {
			if (!seq.get(i).isReady()) {
				break;
			}
			if (config.equals(seq.get(i))) {
//				System.out.println("Duplicated");
				continue;
			}
			config = seq.get(i);
			sb.append(" | ");
			sb.append(config);
		}
		sb.append(" | nil)");
		return sb.toString();
	}

	public void writeSeqStringToFile() {
		if (seq.size() > 0) {
			String seqString = seqToString();
			String seqSha256 = GFG.getSHA(seqString);
			if (!jedis.exists(seqSha256)) {
				jedis.set(seqSha256, seqString);
				// TODO :: Sending to Maude Queue master
				mq.Sender.getInstance().sendMaudeJob(seqString);
				SEQ_UNIQUE_COUNT++;
			}
			
			if (DEPTH_FLAG) {
				OC lastElement = seq.get(seq.size() - 1);
				if (lastElement != null) {
					String elementSha256 = GFG.getSHA(lastElement.toString());
					if (!jedis.exists(elementSha256)) {
						jedis.set(elementSha256, lastElement.toString());
						// TODO :: submit job to the queue broker
						if (lastElement.isFinished() == false && lastElement.isReady() == true && is_publish) {
							lastElement.setCurrentDepth(this.nextDepth);
							mq.Sender.getInstance().sendJob(lastElement);
						}
					}
				}
			}
		}
	}

	/**
	 * got the next state
	 * 
	 * Note - this will be notified before any potential propertyViolated, in which
	 * case the currentError will be already set
	 */
	@Override
	public void stateAdvanced(Search search) {
		if (STARTUP == 1) {
			heapJPF.startup(search.getVM());
			if (heapJPF.lookupTable.size() == 0)
				return;
			STARTUP++;
		}
		
		OC config = heapJPF.getConfiguration(search);
		if (config == null) {
			// Finish program
			search.requestBacktrack();
			System.out.println("Finish program at " + search.getDepth());
			COUNT++;
			writeSeqStringToFile();
		} else {
			seq.add(config);
			if (search.isEndState() || !search.isNewState()) {
				// End state or is not new state (visited state). JPF will back track
				// automatically
				COUNT++;
				writeSeqStringToFile();
			}
			if (DEPTH_FLAG && search.getDepth() >= DEPTH) {
				// current depth is greater than DEPTH, back track
//				System.out.println("Reach to the bound depth " + search.getDepth());
				search.requestBacktrack();
				COUNT++;
				writeSeqStringToFile();
			}
		}
		if (BOUND_FLAG && COUNT >= BOUND) {
			// terminate when number of sequence of states reach to BOUND
			search.terminate();
		}
	}

	@Override
	public void stateBacktracked(Search search) {
		if (heapJPF.lookupTable.size() == 0)
			return;
		
		while (seq.size() > 0 && seq.get(seq.size() - 1).getStateId() != search.getStateId()) {
			seq.remove(seq.size() - 1);
		}
	}

	@Override
	public void searchStarted(Search search) {
		System.out.println("Started");
	}

	@Override
	public void searchFinished(Search search) {
		System.out.println(COUNT + " - " + SEQ_UNIQUE_COUNT);
		System.out.println("Finished");
		mq.Sender.getInstance().close();
	}
}
