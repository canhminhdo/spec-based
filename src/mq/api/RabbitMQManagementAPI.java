package mq.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

import application.model.RabbitConsumer;
import application.model.RabbitQueue;

public class RabbitMQManagementAPI {

	private static RabbitMQManagementAPI _instance;
	private static String USER_NAME = "guest";
	private static String PASSWORD = "guest";
	private static String BASE_URL = "http://localhost:15672";
	private HttpClient client;
	final static String API_QUEUES = "/api/queues";
	final static String API_CONSUMERS = "/api/consumers";
	final static String API_SHOVEL_MOVE_MESSAGES = "/api/parameters/shovel/%2f/move-messages";

	public static RabbitMQManagementAPI getInstance() {
		// TODO: Need to improve here, to make connection alive
//		if (_instance == null)
		_instance = new RabbitMQManagementAPI();

		return _instance;
	}

	private RabbitMQManagementAPI() {
		CredentialsProvider provider = new BasicCredentialsProvider();
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(USER_NAME, PASSWORD);
		provider.setCredentials(AuthScope.ANY, credentials);
		client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
	}
	
	public String getJsonStringFromResponse(HttpResponse response) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
		String json = reader.readLine();
		return json;
	}
	
	public void moveMessagesBetweenQueues(String sourceQueue, String destQueue) throws ClientProtocolException, IOException {
		HttpPut request = new HttpPut(BASE_URL + API_SHOVEL_MOVE_MESSAGES);
		request.setHeader("Content-type", "application/json");
		String bodyString = getBodyString(sourceQueue, destQueue);
	    StringEntity stringEntity = new StringEntity(bodyString);
	    request.setEntity(stringEntity);
	    HttpResponse response = client.execute(request);
		int statusCode = response.getStatusLine().getStatusCode();
		assert statusCode == 200 : "RabbitMQ APIs have some problem";
	}
	
	private String getBodyString(String sourceQueue, String destQueue) {
		JSONObject jValue = new JSONObject();
		jValue.put("src-protocol", "amqp091");
		jValue.put("src-uri", "amqp://localhost");
		jValue.put("src-queue", sourceQueue);
		jValue.put("dest-protocol", "amqp091");
		jValue.put("dest-uri", "amqp://localhost");
		jValue.put("dest-queue", destQueue);
		JSONObject jData = new JSONObject();
		jData.put("value", jValue);
		return jData.toString();
	}
	
	public String getAPI(String apiUrl) throws IOException {
		HttpResponse response = client.execute(new HttpGet(BASE_URL + apiUrl));
		int statusCode = response.getStatusLine().getStatusCode();
		assert statusCode == 200 : "RabbitMQ APIs have some problem";
		return getJsonStringFromResponse(response);
	}

	public HashMap<String, RabbitQueue> getQueueInfo() {
		try {
			String json = getAPI(API_QUEUES);
			if (json == null)
				return null;
			return getQueueInfoFromJsonString(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public HashMap<String, RabbitQueue> getQueueInfoFromJsonString(String json) {
		JSONArray jsonArray = new JSONArray(json);
		HashMap<String, RabbitQueue> queueHashMap = new HashMap<String, RabbitQueue>();
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObj = jsonArray.getJSONObject(i);
			String name = jsonObj.getString("name");
			int messages = jsonObj.getInt("messages");
			int messages_ready = jsonObj.getInt("messages_ready");
			int messages_unacknowledged = jsonObj.getInt("messages_unacknowledged");
			RabbitQueue queue = new RabbitQueue(name, messages, messages_ready, messages_unacknowledged);
			queueHashMap.put(name, queue);
		}
		return queueHashMap;
	}
	
	public ArrayList<RabbitConsumer> getConsumerInfo() {
		try {
			String json = getAPI(API_CONSUMERS);
			if (json == null)
				return null;
			return getConsumerInfoFromJsonString(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public ArrayList<RabbitConsumer> getConsumerInfoFromJsonString(String json) {
		JSONArray jsonArray = new JSONArray(json);
		ArrayList<RabbitConsumer> consumers = new ArrayList<RabbitConsumer>();
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObj = jsonArray.getJSONObject(i);
			String consumerTag = jsonObj.getString("consumer_tag");
			String queueName = jsonObj.getJSONObject("queue").getString("name");
			RabbitConsumer consumer = new RabbitConsumer(consumerTag, queueName);
			consumers.add(consumer);
		}
		return consumers;
	}

	public static void main(String[] argv) throws ClientProtocolException, IOException {
		System.out.println(RabbitMQManagementAPI.getInstance().getConsumerInfo());
		ArrayList<RabbitConsumer> consumers = RabbitMQManagementAPI.getInstance().getConsumerInfo();
		for (int i = 0; i < consumers.size(); i ++) {
			System.out.println(consumers.get(i));
		}
		RabbitMQManagementAPI.getInstance().moveMessagesBetweenQueues("NSPK_DEPTH", "NSPK0");
	}
}
