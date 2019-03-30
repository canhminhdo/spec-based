package jpf;

import java.util.Collection;

import main.Cell;
import main.Channel;
import main.Pair;

public class Configuration<P> {
	
	private Channel<Pair<P,Boolean>> channel1;
    private Channel<Boolean> channel2;
    private Collection<P> packetsToBeSent;
    private Collection<P> packetsReceived;
    private Cell<Boolean> finish;
    private Boolean flag1;
    private Boolean flag2;
    private int stateId;
    private int depth;
    
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
		return "(stateId=" + stateId + ", depth=" + depth + ", channel1=" + channel1 + ", channel2=" + channel2 
				+ ", packetsToBeSent=" + packetsToBeSent + ", packetsReceived=" + packetsReceived 
				+ ", finish=" + finish + ", flag1=" + flag1 + ", flag2=" + flag2 + ")";
	}
	
	public String getOBs() {
		return "";
	}
}
