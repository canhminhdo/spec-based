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

	private final int DEPTH = CaseStudy.DEPTH;
	private final int BOUND = CaseStudy.BOUND;
	private boolean DEPTH_FLAG = CaseStudy.DEPTH_FLAG;
	private boolean BOUND_FLAG = CaseStudy.BOUND_FLAG;
	private int COUNT = 0;
	private int SEQ_UNIQUE_COUNT = 0;

	private int STARTUP = 1;
	private ArrayList<OC> seq;
	private Jedis jedis = null;
	private HeapJPF heapJPF = null;

	public SequenceState(Config conf, JPF jpf) {
		initialize();
		jedis.flushAll();
	}

	public SequenceState() {
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
		OC config = seq.get(0);
		StringBuffer sb = new StringBuffer();
		sb.append("(");
		sb.append(config);
		for (int i = 1; i < seq.size(); i++) {
			if (config.equals(seq.get(i))) {
//				Logger.log("Dupplicated");
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
		try {
			if (seq.size() > 0) {
				String seqString = seqToString();
				String seqSha256 = GFG.getSHA(seqString);
				if (!jedis.exists(seqSha256)) {
					jedis.set(seqSha256, seqString);
					// TODO :: Sending to Maude Queue master
					mq.Sender.getInstance().sendMaudeJob(seqString);
					SEQ_UNIQUE_COUNT++;
				}
				OC lastElement = seq.get(seq.size() - 1);
				if (lastElement != null) {
					String elementSha256 = GFG.getSHA(lastElement.toString());
					if (!jedis.exists(elementSha256)) {
						jedis.set(elementSha256, lastElement.toString());
						// TODO :: submit job to the queue broker
//						if (lastElement.getFinish().get() == false) {
						if (lastElement.isFinished() == false) {
							mq.Sender.getInstance().sendJob(lastElement);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
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
			STARTUP++;
			heapJPF.startup(search.getVM());
		}
		OC config = heapJPF.getConfiguration(search);
//		Configuration<String> config = getConfiguration(search);
		if (config == null) {
			// Finish program
			search.requestBacktrack();
			Logger.log("Finish program at " + search.getDepth());
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
//				Logger.log("Reach to the bound depth " + search.getDepth());
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
		while (seq.size() > 0 && seq.get(seq.size() - 1).getStateId() != search.getStateId()) {
			seq.remove(seq.size() - 1);
		}
	}

	@Override
	public void searchStarted(Search search) {
		Logger.log("Started");
	}

	@Override
	public void searchFinished(Search search) {
		Logger.log(COUNT + " - " + SEQ_UNIQUE_COUNT);
		Logger.log("Finished");
	}
}
