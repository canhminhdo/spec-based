package jpf.abp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mysql.jdbc.StringUtils;

import jpf.common.OC;
import main.Cell;
import main.Channel;
import main.EmptyQueue;
import main.Pair;
import main.Queue;

public class Configuration<P> implements Serializable, OC {
	
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
		if (index >= packetsToBeSent.size())
			return "d(" + index + ")";
//		return "d(" + (Integer.parseInt((String)((List<P>)packetsToBeSent).get(index - 1)) + 1) + ")";
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
	
	public String getPackets(Collection<P> packets) {
		if (packets.size() == 0) {
			return "nil";
		}
		StringBuffer sb = new StringBuffer();
		sb.append(((List<P>)packets).get(0));
		for (int i = 1; i < packets.size(); i ++) {
			sb.append(",");
			sb.append(((List<P>)packets).get(i));
		}
		return sb.toString();
	}
	
	public String getChannel1(String ch1) {
		if (ch1.equals("nil"))
			return ch1;
		
		Pattern p = Pattern.compile("(< d\\([0-9]+\\),(true|false) >)");
		Matcher matcher = p.matcher(ch1);
		ArrayList<String> str = new ArrayList<String>();
        while (matcher.find()) {
        	str.add(String.join("-", matcher.group(1).replace("< d(", "").replace(")", "").replace(" >", "").split(",")));
        }
        return org.apache.commons.lang3.StringUtils.join(str, ",");
	}
	
	public String getChannel2(String ch2) {
		if (ch2.equals("nil"))
			return ch2;
		String[] channel2 = ch2.split(",");
		return org.apache.commons.lang3.StringUtils.join(channel2, ",", 0, channel2.length - 1);
	}
	
	public String getPacketsToBeSentCommand() {
		return getPackets(packetsToBeSent);
	}
	
	public String getPacketsReceivedCommand() {
		return getPackets(packetsReceived);
	}
	
	public String getIndexCommand() {
		return index + "";
	}
	
	public String getFinishCommand() {
		return finish + "";
	}
	
	public String getFlag1Command() {
		return flag1 + "";
	}
	
	public String getFlag2Command() {
		return flag2 + "";
	}
	
	public String getChannel1Command() {
		return getChannel1(channel1.toCommand());
	}
	
	public String getChannel2Command() {
		return getChannel2(channel2.toCommand());
	}
	
	public String getCommandArguments() {
		String cmd = "";
		cmd += getPackets(packetsToBeSent) + " ";
		cmd += getPackets(packetsReceived) + " ";
		cmd += index + " ";
		cmd += finish + " ";
		cmd += flag1 + " ";
		cmd += flag2 + " ";
		cmd += getChannel1(channel1.toCommand()) + " ";
		cmd += getChannel2(channel2.toCommand());
		return cmd;
	}
	
	public String getObserverComponents() {
		return "{" +
					"sb: " + flag1 +
//					" stateId: " + stateId +
					" data: " + getAbpData() +
//					" packets: " + packetsToBeSent +
					" dc: " + channel1 +
					" ac: " + channel2 +
					" rb: " + flag2 +
					" buf: " + getAbpBuf() +
			   "}";
	}
}
