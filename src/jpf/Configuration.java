package jpf;

import java.util.Collection;
import java.util.List;

import main.Cell;
import main.Channel;
import main.Pair;

public class Configuration<P> {
	
	private Channel<Pair<P,Boolean>> channel1;
	private Integer index;
    private Channel<Boolean> channel2;
    private Collection<P> packetsToBeSent;
    private Collection<P> packetsReceived;
    private Cell<Boolean> finish;
    private Boolean flag1;
    private Boolean flag2;
    private Integer stateId;
    private Integer depth;
    
	public Configuration() {
		
	}

	public Configuration(Channel<Pair<P, Boolean>> channel1, Channel<Boolean> channel2, Collection<P> packetsToBeSent,
			Collection<P> packetsReceived, Cell<Boolean> finish, Boolean flag1, Boolean flag2, int stateId, int depth) {
		super();
		this.channel1 = channel1;
		this.channel2 = channel2;
		this.packetsToBeSent = packetsToBeSent;
		this.packetsReceived = packetsReceived;
		this.finish = finish;
		this.flag1 = flag1;
		this.flag2 = flag2;
		this.stateId = stateId;
		this.depth = depth;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		Configuration<P> other = (Configuration<P>) obj;
		return index.equals(other.getIndex()) &&  
				flag1.equals(other.getFlag1()) &&
				flag2.equals(other.getFlag2()) &&
				finish.equals(other.getFinish()) && 
				packetsReceived.equals(other.getPacketsReceived()) &&
				channel1.equals(other.getChannel1()) &&
				channel2.equals(other.getChannel2());
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Channel<Pair<P, Boolean>> getChannel1() {
		return channel1;
	}

	public void setChannel1(Channel<Pair<P, Boolean>> channel1) {
		this.channel1 = channel1;
	}

	public Channel<Boolean> getChannel2() {
		return channel2;
	}

	public void setChannel2(Channel<Boolean> channel2) {
		this.channel2 = channel2;
	}

	public Collection<P> getPacketsToBeSent() {
		return packetsToBeSent;
	}

	public void setPacketsToBeSent(Collection<P> packetsToBeSent) {
		this.packetsToBeSent = packetsToBeSent;
	}

	public Collection<P> getPacketsReceived() {
		return packetsReceived;
	}

	public void setPacketsReceived(Collection<P> packetsReceived) {
		this.packetsReceived = packetsReceived;
	}

	public Cell<Boolean> getFinish() {
		return finish;
	}

	public void setFinish(Cell<Boolean> finish) {
		this.finish = finish;
	}

	public Boolean getFlag1() {
		return flag1;
	}

	public void setFlag1(Boolean flag1) {
		this.flag1 = flag1;
	}

	public Boolean getFlag2() {
		return flag2;
	}

	public void setFlag2(Boolean flag2) {
		this.flag2 = flag2;
	}

	public int getStateId() {
		return stateId;
	}

	public void setStateId(int stateId) {
		this.stateId = stateId;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	@Override
	public String toString() {
		return getObserverComponents();
//		return "(stateId=" + stateId + ", depth=" + depth + ", channel1=" + channel1 + ", channel2=" + channel2 
//				+ ", packetsToBeSent=" + packetsToBeSent + ", packetsReceived=" + packetsReceived 
//				+ ", finish=" + finish + ", flag1=" + flag1 + ", flag2=" + flag2 + ", index=" + index + ")";
	}
	
	public String getAbpData() {
		return "d(" + ((List<P>)packetsToBeSent).get(index) + ")";
	}
	
	public String getAbpBuf() {
		if (packetsReceived.size() == 0) {
			return "nil";
		}
		StringBuffer sb = new StringBuffer();
		sb.append("(");
		sb.append("d(" + ((List<P>)packetsReceived).get(0) + ")");
		for (int i = 1; i < packetsReceived.size(); i ++) {
			sb.append(" | ");
			sb.append("d(" + ((List<P>)packetsReceived).get(i) + ")");
		}
		sb.append(" | nil)");
		return sb.toString();
	}
	
	public String getObserverComponents() {
		return "{" +
					"abp-sb: " + flag1 +
					" abp-data: " + getAbpData() +
					" abp-dc: " + channel1 +
					" abp-ac: " + channel2 +
					" abp-rb: " + flag2 +
					" abp-buf: " + getAbpBuf() +
			   "}";
	}
}
