package jpf;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import checker.factory.SenderFactory;
import config.CaseStudy;
import gov.nasa.jpf.ListenerAdapter;
import gov.nasa.jpf.search.Search;
import jpf.common.HeapJPF;
import jpf.common.OC;
import redis.api.RedisHash;
import redis.api.RedisQueueSet;
import redis.api.RedisStoreStates;
import server.Application;
import server.ApplicationConfigurator;
import utils.GFG;

public abstract class StateSequence extends ListenerAdapter {
	
	private static Logger logger = (Logger) LogManager.getLogger();
	protected int DEPTH = CaseStudy.DEPTH;
	protected int BOUND = CaseStudy.BOUND;
	protected boolean DEPTH_FLAG = CaseStudy.DEPTH_FLAG;
	protected boolean BOUND_FLAG = CaseStudy.BOUND_FLAG;
	private int COUNT = 0;
	private int STARTUP = 1;
	protected ArrayList<OC> seq;
	protected RedisQueueSet jedisSet = null;
	protected RedisStoreStates jedisHash = null;
	protected HeapJPF heapJPF = null;
	protected Application app;
	protected SenderFactory sender;
	
	protected void initialize() {
		app = ApplicationConfigurator.getInstance().getApplication();
		jedisSet = new RedisQueueSet();
		jedisHash = new RedisStoreStates();
		seq = new ArrayList<OC>();
		heapJPF = app.getHeapJPF();
		app.getCaseStudy().printConfiguration();
		sender = app.getModelChecker().createSender();
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
		
		if (!jedisSet.sismember(app.getRabbitMQ().getMaudeQueue(), seqSha256)) {
			jedisSet.sadd(app.getRabbitMQ().getMaudeQueue(), seqSha256);
			//sending seqString to Maude Queue master
			sender.sendMaudeJob(seqString);
		}
	}
	
	public abstract void stateHandle();
	
	public void proceedStateSequence() {
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
			proceedStateSequence();
		} else {
			seq.add(config);
			if (search.isEndState() || !search.isNewState()) {
				// JPF will back track automatically
				COUNT++;
				proceedStateSequence();
			}
			if (DEPTH_FLAG && search.getDepth() >= DEPTH) {
				// backtrack if current depth is greater or equal than DEPTH, 
				search.requestBacktrack();
				COUNT++;
				proceedStateSequence();
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
	public void propertyViolated(Search search) {
		logger.info("Property Violated at " + search.getDepth());
	}
}
