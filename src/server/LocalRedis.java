package server;

public class LocalRedis implements Redis {
	
	String host = null;
	Integer port = null;

	public LocalRedis(String host, Integer port) {
		this.host = host;
		this.port = port;
	}

	public String getHost() {
		return this.host;
	}
	
	public Integer getPort() {
		return this.port;
	}

}
