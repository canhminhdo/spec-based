package checker.manual;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import com.rabbitmq.client.Channel;

import application.model.SystemInfo;
import checker.factory.StarterFactory;
import config.CaseStudy;
import jpf.common.OC;
import mq.RabbitMQClient;
import nslpk.parser.MessageOC;
import nslpk.parser.NslpkMessageParser;
import nspk.main.Cipher;
import nspk.main.Message;
import nspk.main.Network;
import nspk.main.Principal;
import nspk.parser.NspkMessageParser;
import redis.api.RedisConsumerInfo;
import redis.api.RedisLock;
import redis.api.RedisQueueSet;
import redis.api.RedisStoreStates;
import redis.api.RedisSystemInfo;
import utils.SerializationUtilsExt;

public class RandStarter extends StarterFactory {
	protected static Logger logger = (Logger) LogManager.getLogger();
	RabbitMQClient rabbitClient;
	Channel channel;
	int currentDepth;
	int currentLayer;
	double percentage;
	RedisQueueSet jedisSet = null;
	RedisStoreStates jedisHash = null;
	RedisConsumerInfo jedisConsumerInfo = null;
	RedisSystemInfo jedisSysInfo = null;
	RedisLock jedisLock = null;
	int current = 0;

	public RandStarter() {
		super();
		this.currentDepth = 100; // picking states located currentDepth to generate states in the currentLayer
		this.currentLayer = 2; // currentLayer is working on to generate states
		this.percentage = 10; // the percentage to select states from a set of states located currentDepth
		jedisSet = new RedisQueueSet();
		jedisHash = new RedisStoreStates();
		jedisSysInfo = new RedisSystemInfo();
		jedisConsumerInfo = new RedisConsumerInfo();
		jedisLock = new RedisLock();
	}
	
	@Override
	public void start() {
		cleanUp();
		pushInitialJob();
		initializeRedisSysInfo();
	}

	@Override
	public void cleanUp() {
		try {
			this.cleanRabbitMQAndLog();
			this.trunscateDatabase();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void cleanRabbitMQAndLog() throws IOException, InterruptedException {
		if (!app.getServerFactory().isRemote()) {
			Process p1 = Runtime.getRuntime().exec("/usr/local/opt/rabbitmq/sbin/rabbitmqadmin purge queue name="
					+ app.getRabbitMQ().getQueueName() + "0");

			Process p2 = Runtime.getRuntime().exec("/usr/local/opt/rabbitmq/sbin/rabbitmqadmin purge queue name="
					+ app.getRabbitMQ().getQueueName() + "1");

			Process p3 = Runtime.getRuntime().exec("/usr/local/opt/rabbitmq/sbin/rabbitmqadmin purge queue name="
					+ app.getRabbitMQ().getQueueName() + "2");

			p1.waitFor();
			p2.waitFor();
			p3.waitFor();
		}
	}

	@Override
	public void pushInitialJob() {
		try {
			// Connect to rabbit message
			rabbitClient = RabbitMQClient.getInstance();
			channel = rabbitClient.getChannel();
			String queueName = app.getRabbitMQ().getQueueName() + current;
			rabbitClient.queueDeclare(queueName);
			
			// Do backup and randomly select states from a set of states in Redis
			this.proceedRandom();
			// add error states if have
			this.addErrorStates();
			
			// publish selected states to the rabbit message
			Set<String> states = jedisSet.smembers(jedisSet.getDepthSetName(currentDepth));
			for (String key : states) {
				String state = jedisHash.hget(jedisHash.getStoreNameAtDepth(currentDepth), key);
				OC message = SerializationUtilsExt.deserialize(state);
				rabbitClient.basicPublish(queueName, SerializationUtils.serialize(message));
				logger.debug("key = " + key);
				logger.debug(" [x] Sent '" + message);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void filterStates() {
		Set<String> states = jedisSet.smembers(jedisSet.getDepthSetName(currentDepth));
		int count = 0;
		for (String key : states) {
			String state = jedisHash.hget(jedisHash.getStoreNameAtDepth(currentDepth), key);
			OC message = SerializationUtilsExt.deserialize(state);
			
			
			MessageOC oc;
			if (CaseStudy.CASE_STUDY_NAME.equalsIgnoreCase("nslpk")) {
				oc = NslpkMessageParser.parse(message.toString());
			} else {
				oc = NspkMessageParser.parse(message.toString());
			}
			Principal p = oc.getP();
			if (p.getRand().size() == 2) {
				jedisSet.srem(jedisSet.getDepthSetName(currentDepth), key);
			}
			
			if (p.getRand().size() == 1) {
				count += 1;
			}
			
			if (p.getRand().size() == 0) {
				Network<Message<Cipher>> nw = p.getNw();
				ArrayList<Message<Cipher>> nw_list = nw.getAll();
				boolean flag = false;
				for (int i = 0; i < nw_list.size(); i ++) {
					if (nw_list.get(i).getCipher().mustHave()) {
						flag = true;
						break;
					}
				}
				if (flag) {
					count += 1;
					continue;
				}
				jedisSet.srem(jedisSet.getDepthSetName(currentDepth), key);
			}
			
		}
		logger.debug(count);
	}
	
	public void proceedRandom() {
		assert percentage >= 0 && percentage <= 100 : "Percentage value is invalid";
		
		// save for back-up
		if (jedisSet.scard(jedisSet.getDepthSetBackupName(currentDepth)) == 0) {
			// create a backup set from a current depth set
			jedisSet.sdiffstore(jedisSet.getDepthSetBackupName(currentDepth), jedisSet.getDepthSetName(currentDepth));
		} else {
			// copy from a backup set to the current depth set
			jedisSet.sdiffstore(jedisSet.getDepthSetName(currentDepth), jedisSet.getDepthSetBackupName(currentDepth));
		}
		
		// build distinct states at currentDepth
		int depth = 0;
		while (depth < currentDepth) {
			jedisSet.sdiffstore(jedisSet.getDepthSetName(currentDepth), jedisSet.getDepthSetName(currentDepth), jedisSet.getDepthSetName(depth));
			depth += CaseStudy.DEPTH;
		}
		
		this.filterStates();
		// store to a filter set of state.
		jedisSet.sdiffstore(jedisSet.getDepthSetFilterName(currentDepth), jedisSet.getDepthSetName(currentDepth));
				
		if (percentage == 100)
			return;
		
		// calculate the number of removed states
		long numberOfStates = jedisSet.scard(jedisSet.getDepthSetName(currentDepth));
		long numberOfKeepStates  = (long) Math.ceil(percentage * (1.0 * numberOfStates / 100));
		long numberOfRemovedStates = numberOfStates - numberOfKeepStates;
		
		logger.debug("numberOfStates = " + numberOfStates);
		logger.debug("numberOfKeepStates = " + numberOfKeepStates);
		logger.debug("numberOfRemovedStates = " + numberOfRemovedStates);
		
		// remove some states
		jedisSet.spop(jedisSet.getDepthSetName(currentDepth), (int) numberOfRemovedStates);
	}
	
	public void addErrorStates() {
		jedisSet.sunion(jedisSet.getDepthSetName(currentDepth), jedisSet.getDepthSetName(currentDepth), jedisSet.getDepthSetError(currentDepth));
	}

	@Override
	public void saveInitialMessageToRedis() {
	}

	@Override
	public void initializeRedisSysInfo() {
		// update the current system info
		jedisSysInfo.hset(RedisSystemInfo.SYSTEM_KEY, SystemInfo.MODE_KEY, SystemInfo.BMC_MODE);
		jedisSysInfo.hset(RedisSystemInfo.SYSTEM_KEY, SystemInfo.CURRENT_DEPTH_KEY, String.valueOf(this.currentDepth));
		jedisSysInfo.hset(RedisSystemInfo.SYSTEM_KEY, SystemInfo.CURRENT_LAYER_KEY, String.valueOf(this.currentLayer));
		// clear all information about worker
		jedisConsumerInfo.clearWorkers();
		// release lock if needed
		jedisLock.releaseCS(RedisLock.LOCK_MODIFY_INFO_FIELD);
		// clean the next depth if needed
		int nextDepth = this.currentDepth;
		if (nextDepth < CaseStudy.MAX_DEPTH) {
			if (nextDepth + CaseStudy.DEPTH > CaseStudy.MAX_DEPTH) {
				nextDepth = CaseStudy.MAX_DEPTH;
			} else {
				nextDepth += CaseStudy.DEPTH;
			}
			jedisSet.deleteKey(jedisSet.getDepthSetName(nextDepth));
			jedisSet.deleteKey(jedisSet.getDepthSetBackupName(nextDepth));
			jedisHash.deleteKey(jedisHash.getStoreNameAtDepth(nextDepth));
			jedisHash.deleteKey(jedisHash.getStoreErrorNameAtDepth(nextDepth));
		}
	}
}
