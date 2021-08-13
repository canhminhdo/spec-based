package mq;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.SerializationUtils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import application.model.ErrorMessage;
import application.model.SequenceStates;
import application.service.SequenceStatesService;
import config.CaseStudy;
import maude.OutputParser;
import server.Application;
import server.ApplicationConfigurator;
import utils.DateUtil;

/**
 * Receiver program as RabbitMQ client Whenever receiving a message from
 * RabbitMQ master. Start checking such state sequence that conforms to the
 * given specification.
 * 
 * @author OgataLab
 *
 */
public class MaudeWorker {
	
	public static Long currentTime = DateUtil.getTime();
	public static int timeOut = 1 * 60 * 1000;	// 1 minutes
	public static int batch = 100;
	public static int count;
	
	/**
	 * Starting a RabbitMQ client.
	 */
	public static void main(String[] argv) throws Exception {
		Application app = ApplicationConfigurator.getInstance().getApplication();
		RabbitMQClient rabbitClient = RabbitMQClient.getInstance();
		Channel channel = rabbitClient.getChannel();
		String queueName = app.getRabbitMQ().getMaudeQueue();
		rabbitClient.queueDeclare(queueName);
		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

		// Get Maude instance and preload
		RunMaude maude = RunMaude.getInstance();
		
		// `list` contains a list of sequenceStates being inserted to DB
		// we do a batch insert to DB instead of one by one, namely around maximum 100 records per time. 
		ArrayList<SequenceStates> list = new ArrayList<SequenceStates>();
		
		DeliverCallback deliverCallback = (consumerTag, delivery) -> {
			String seq = SerializationUtils.deserialize(delivery.getBody());
//			String seq = AES.decrypt(cipher, CaseStudy.SECRETE_KEY);
			MaudeWorker.count += 1;
			System.out.println(" [x] Received " + MaudeWorker.count);
			maude.checkSeq(app.getCaseStudy().getCommand(), seq, CaseStudy.MAUDE_DEPTH);
			OutputParser output = new OutputParser(maude.getOutput());
			output.parsing();
			
			if (CaseStudy.MYSQL_IS_ENABLE) {
				saving_to_mysql(list, output, seq);
			}
			
			if (output.getSuccess().size() > 0) {
				System.out.println("------> success");
			}
			
			if (output.getFailure().size() > 0) {
				System.out.println("------> failure " + seq);
			}
			
			if (output.getError().size() > 0) {
				System.out.println("------> warning " + seq);
			}
			
			channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
		};
		rabbitClient.setDeliverCallBack(deliverCallback);
		rabbitClient.basicConsume(queueName);
	}
	
	public static void saving_to_mysql(ArrayList<SequenceStates> list, OutputParser output, String seq) {
		SequenceStates sequencesStates = new SequenceStates();
		sequencesStates.setSeq(seq);
		sequencesStates.setRuntime(CaseStudy.RUNTIME);
		if (output.getSuccess().size() > 0) {
			if (CaseStudy.MYSQL_IS_ENABLE) {
				sequencesStates.setType("success");
				sequencesStates.setResult(output.getSuccessString());
			}
		}
		if (output.getFailure().size() > 0) {
			ErrorMessage msg = output.parseError();
			sequencesStates.setType("failure");
			sequencesStates.setResult(output.getFailureString());
			if (msg != null) {
				sequencesStates.setState_from(msg.getFrom());
				sequencesStates.setState_to(msg.getTo());
				sequencesStates.setState_index(msg.getIndex());
				sequencesStates.setDepth(msg.getDepth());
			}
		}
		if (output.getError().size() > 0) {
			sequencesStates.setType("warning");
			sequencesStates.setResult(output.getErrorString());
		}
		do {
			try {
				list.add(sequencesStates);
				if (list.size() > batch || DateUtil.getTime() - MaudeWorker.currentTime > MaudeWorker.timeOut) {
					SequenceStatesService.insertBatch(list);
					list.clear();
					MaudeWorker.currentTime = DateUtil.getTime();
				}
				break;
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(" [MySQL] Waiting 3 sec");
				try {
					TimeUnit.SECONDS.sleep(3);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		} while (true);
	}
}
