package mq;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.SerializationUtils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
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
	public static int timeOut = 3 * 60 * 1000;	// 3 minutes
	
	/**
	 * Starting a RabbitMQ client.
	 * 
	 * @param argv Unsed.
	 * @throws Exception
	 */
	public static void main(String[] argv) throws Exception {
		// Initialize application with configuration
		Application app = ApplicationConfigurator.getInstance().getApplication();

		// rabbitMQ connection
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(app.getRabbitMQ().getHost());
		if (app.getServerFactory().isRemote()) {
			factory.setUsername(app.getRabbitMQ().getUserName());
			factory.setPassword(app.getRabbitMQ().getPassword());
		}
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.queueDeclare(app.getRabbitMQ().getMaudeQueue(), false, false, false, null);
		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
		
		// Get Maude instance and preload
		RunMaude maude = RunMaude.getInstance();
		
		// `list` contains a list of sequenceStates being inserted to DB
		// we do a batch insert to DB instead of one by one, namely around maximum 100 records per time. 
		ArrayList<SequenceStates> list = new ArrayList<SequenceStates>();
		
		DeliverCallback deliverCallback = (consumerTag, delivery) -> {
			String seq = SerializationUtils.deserialize(delivery.getBody());
//			String seq = AES.decrypt(cipher, CaseStudy.SECRETE_KEY);
			System.out.println(" [x] Received ");
			maude.checkSeq(app.getCaseStudy().getCommand(), seq, 2);
			OutputParser output = new OutputParser(maude.getOutput());
			output.parsing();
			// TODO:: Should save to database
			SequenceStates sequencesStates = new SequenceStates();
			sequencesStates.setSeq(seq);
			sequencesStates.setRuntime(CaseStudy.RUNTIME);
			
			if (output.getSuccess().size() > 0) {
				sequencesStates.setType("success");
				sequencesStates.setResult(output.getSuccessString());
				System.out.println("------> success");
			}
			
			if (output.getFailure().size() > 0) {
				sequencesStates.setType("failure");
				sequencesStates.setResult(output.getFailureString());
				ErrorMessage msg = output.parseError();
				if (msg != null) {
					sequencesStates.setState_from(msg.getFrom());
					sequencesStates.setState_to(msg.getTo());
					sequencesStates.setState_index(msg.getIndex());
					sequencesStates.setDepth(msg.getDepth());
				}
				System.out.println("------> failure " + seq);
			}
			if (output.getError().size() > 0) {
				sequencesStates.setType("warning");
				sequencesStates.setResult(output.getErrorString());
				System.out.println("------> warning " + seq);
			}
			
			do {
				try {
					list.add(sequencesStates);
					if (list.size() > 100 || DateUtil.getTime() - MaudeWorker.currentTime > MaudeWorker.timeOut) {
						SequenceStatesService.insertBatch(list);
						list.clear();
						MaudeWorker.currentTime = DateUtil.getTime();
					}
//					SequenceStatesService.insert(sequencesStates);
					break;
				} catch (Exception e) {
					// TODO Auto-generated catch block
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
			
			channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
		};

		boolean autoAck = false;
		channel.basicConsume(app.getRabbitMQ().getMaudeQueue(), autoAck, deliverCallback, consumerTag -> {
		});
	}

}
