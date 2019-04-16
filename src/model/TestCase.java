package model;

public class TestCase {
	@Override
	public String toString() {
		return "TestCase [id=" + id + ", seqNo=" + seqNo + ", stateId=" + stateId + ", depth=" + depth
				+ ", packetsToBeSent=" + packetsToBeSent + ", packetsReceived=" + packetsReceived + ", channel1="
				+ channel1 + ", channel2=" + channel2 + ", index=" + index + ", finish=" + finish + ", flag1=" + flag1
				+ ", flag2=" + flag2 + "]";
	}
	private int id;
	private int seqNo;
	private int stateId;
	private int depth;
	private String packetsToBeSent;
	private String packetsReceived;
	private String channel1;
	private String channel2;
	private int index;
	private String finish;
	private Boolean flag1;
	private Boolean flag2;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(int seqNo) {
		this.seqNo = seqNo;
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
	public String getPacketsToBeSent() {
		return packetsToBeSent;
	}
	public void setPacketsToBeSent(String packetsToBeSent) {
		this.packetsToBeSent = packetsToBeSent;
	}
	public String getPacketsReceived() {
		return packetsReceived;
	}
	public void setPacketsReceived(String packetsReceived) {
		this.packetsReceived = packetsReceived;
	}
	public String getChannel1() {
		return channel1;
	}
	public void setChannel1(String channel1) {
		this.channel1 = channel1;
	}
	public String getChannel2() {
		return channel2;
	}
	public void setChannel2(String channel2) {
		this.channel2 = channel2;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	
	public String getFinish() {
		return finish;
	}
	public void setFinish(String finish) {
		this.finish = finish;
	}
	public Boolean isFlag1() {
		return flag1;
	}
	public void setFlag1(Boolean flag1) {
		this.flag1 = flag1;
	}
	public Boolean isFlag2() {
		return flag2;
	}
	public void setFlag2(Boolean flag2) {
		this.flag2 = flag2;
	}
}
