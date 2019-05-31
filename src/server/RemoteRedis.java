package server;

public class RemoteRedis implements Redis {
	String host = null;
	Integer port = null;

	public RemoteRedis(String host, Integer port) {
		this.host = host;
		this.port = port;
	}

	public String getHost() {
		return host;
	}

	public Integer getPort() {
		return this.port;
	}

}
