package cloudsync.jpf;

import java.io.Serializable;
import java.util.ArrayList;

import cloudsync.main.Cloud;
import cloudsync.main.PC;
import jpf.common.OC;
import utils.GFG;

/**
 * Configuration for CloudSync protocol
 * @author OgataLab
 *
 */
public class CloudSyncConfiguration implements Serializable, OC {
	
	private boolean isReady;
	private Integer stateId;
	private Integer depth;
	private Cloud cloud;
	private ArrayList<PC> pcList;
	private int currentDepth = 0;

	public CloudSyncConfiguration() {
		this.isReady = true;
	}
	
	@Override
	public int getStateId() {
		return stateId;
	}

	@Override
	public boolean isFinished() {
		return false;
	}
	
	public int getDepth() {
		return depth;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}

	public void setDepth(Integer depth) {
		this.depth = depth;
	}
	
	public Cloud getCloud() {
		return cloud;
	}

	public ArrayList<PC> getPcList() {
		return pcList;
	}

	public void setCloud(Cloud cloud) {
		this.cloud = cloud;
	}

	public void setPcList(ArrayList<PC> pcList) {
		this.pcList = pcList;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		
		CloudSyncConfiguration other = (CloudSyncConfiguration) obj;
		return GFG.getSHA(this.toString()).equals(GFG.getSHA(other.toString()));
	}
	
	@Override
	public String getPassedMessage() {
		return toString();
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append(cloud);
		for (int i = 0; i < pcList.size(); i ++) {
			sb.append(" ");
			sb.append(pcList.get(i));
		}
		sb.append("}");
		return sb.toString();
	}

	@Override
	public boolean isReady() {
		return this.isReady;
	}
	
	public void setReady(boolean isReady) {
		this.isReady = isReady;
	}
	
	@Override
	public int getCurrentDepth() {
		return currentDepth;
	}

	@Override
	public void setCurrentDepth(int currentDepth) {
		this.currentDepth = currentDepth;
	}

}
