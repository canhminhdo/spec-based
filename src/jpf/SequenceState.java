package jpf;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import config.CaseStudy;
import database.RedisClient;
import gov.nasa.jpf.Config;
import gov.nasa.jpf.JPF;
import gov.nasa.jpf.ListenerAdapter;
import gov.nasa.jpf.search.Search;
import jpf.common.HeapJPF;
import jpf.common.OC;
import redis.api.RedisQueueSet;
import redis.clients.jedis.Jedis;
import server.Application;
import server.ApplicationConfigurator;
import server.instances.Redis;
import utils.GFG;

public class SequenceState extends ListenerAdapter {
	
	private static Logger logger = (Logger) LogManager.getLogger();
	private int DEPTH = CaseStudy.CURRENT_DEPTH;
	private int BOUND = CaseStudy.BOUND;
	private boolean DEPTH_FLAG = CaseStudy.DEPTH_FLAG;
	private boolean BOUND_FLAG = CaseStudy.BOUND_FLAG;
	private int COUNT = 0;
	private int SEQ_UNIQUE_COUNT = 0;

	private int STARTUP = 1;
	private ArrayList<OC> seq;
	private RedisQueueSet jedisSet = null;
	private HeapJPF heapJPF = null;
	
	private boolean is_publish = true;
	private int nextDepth = 0;
	
	public SequenceState(Config conf, JPF jpf) {
		initialize();
		jedisSet.flushAll();
	}
	
	public SequenceState(int currentDepth) {
		if (CaseStudy.IS_BOUNDED_MODEL_CHECKING) {
			if (currentDepth + DEPTH >= CaseStudy.CURRENT_MAX_DEPTH) {
				DEPTH = CaseStudy.CURRENT_MAX_DEPTH - currentDepth;
				is_publish = false;
			}
		}
		this.nextDepth = currentDepth + DEPTH;
		initialize();
	}

	private void initialize() {
		Application app = ApplicationConfigurator.getInstance().getApplication();
		Redis redis = app.getRedis();
		Jedis jedisInstance = RedisClient.getInstance(redis.getHost(), redis.getPort()).getConnection();
		jedisSet = new RedisQueueSet(jedisInstance);
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
				logger.info("Duplicated");
				continue;
			}
			config = seq.get(i);
			sb.append(" | ");
			sb.append(config);
		}
		sb.append(" | nil)");
		return sb.toString();
	}
	
	public void seqHandle() {
		String seqString = seqToString();
		String seqSha256 = GFG.getSHA(seqString);
		
		if (!jedisSet.sismember(RedisQueueSet.SEQ_SET, seqSha256)) {
			jedisSet.sadd(RedisQueueSet.SEQ_SET, seqSha256);
			//sending seqString to Maude Queue master
			mq.Sender.getInstance().sendMaudeJob(seqString);
			SEQ_UNIQUE_COUNT++;
		}
	}
	
	public void stateHandle() {
		OC lastElement = seq.get(seq.size() - 1);
		if (lastElement == null)
			return;
		String elementSha256 = GFG.getSHA(lastElement.toString());
		lastElement.setCurrentDepth(this.nextDepth);
		// if already existing is depth set
		if (jedisSet.sismember(jedisSet.getDepthSetName(this.nextDepth), elementSha256))
			return;
		
		if (!lastElement.isFinished() && lastElement.isReady()) {
			jedisSet.sadd(jedisSet.getDepthSetName(this.nextDepth), elementSha256);
			if (is_publish) {
				mq.Sender.getInstance().sendJob(lastElement);
			} else {
				// if states located at the maximum depth
				if (CaseStudy.RANDOM_MODE) {
					mq.Sender.getInstance().sendJobAtDepth(lastElement);
				}	
			}
		}
	}
	
	public void writeSeqStringToFile() {
		if (seq.size() > 0) {
			if (CaseStudy.MAUDE_WORKER_IS_ENABLE) {
				seqHandle();
			}
			if (DEPTH_FLAG) {
				stateHandle();
			}
		}
	}
	
	public boolean isReadyAndStartup(Search search) {
		if (STARTUP == 1) {
			heapJPF.startup(search.getVM());
			if (heapJPF.lookupTable.size() == 0)
				return false;
			STARTUP++;
		}
		return true;
	}
	/**
	 * got the next state
	 * 
	 * Note - this will be notified before any potential propertyViolated, in which
	 * case the currentError will be already set
	 */
	@Override
	public void stateAdvanced(Search search) {
		if (!isReadyAndStartup(search))
			return;
		
		OC config = heapJPF.getConfiguration(search);
		if (config == null) {
			// finish program
			search.requestBacktrack();
			logger.info("Finish program at " + search.getDepth());
			COUNT++;
			writeSeqStringToFile();
		} else {
			seq.add(config);
			if (search.isEndState() || !search.isNewState()) {
				// JPF will back track automatically
				COUNT++;
				writeSeqStringToFile();
			}
			if (DEPTH_FLAG && search.getDepth() >= DEPTH) {
				// backtrack if current depth is greater or equal than DEPTH, 
				logger.debug("Reach to the bound depth " + search.getDepth());
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
	public void searchFinished(Search search) {
		logger.debug("Finished: " + COUNT + " - " + SEQ_UNIQUE_COUNT);
		mq.Sender.getInstance().close();
	}
}
