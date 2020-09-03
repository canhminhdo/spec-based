package nspk.jpf;

import java.io.Serializable;

import jpf.common.OC;
import nspk.main.Controller;
import nspk.main.MultiSet;
import utils.GFG;

public class NspkConfiguration implements Serializable, OC {
	
	private boolean isReady;
	private Integer stateId;
	private Integer depth;
	private MultiSet<String> nw;
	private MultiSet<String> rand;
	private MultiSet<String> nonces;
	private MultiSet<String> prins;
	private Controller<String> pRwController;
	private Controller<String> qRwController;
	private Controller<String> intrdrRwController;
	private int currentDepth = 0;

	public NspkConfiguration() {
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

	public MultiSet<String> getNw() {
		return nw;
	}

	public MultiSet<String> getRand() {
		return rand;
	}

	public MultiSet<String> getNonces() {
		return nonces;
	}

	public MultiSet<String> getPrins() {
		return prins;
	}

	public Controller<String> getpRwController() {
		return pRwController;
	}

	public Controller<String> getqRwController() {
		return qRwController;
	}

	public Controller<String> getIntrdrRwController() {
		return intrdrRwController;
	}

	public void setNw(MultiSet<String> nw) {
		this.nw = nw;
	}

	public void setRand(MultiSet<String> rand) {
		this.rand = rand;
	}

	public void setNonces(MultiSet<String> nonces) {
		this.nonces = nonces;
	}

	public void setPrins(MultiSet<String> prins) {
		this.prins = prins;
	}

	public void setpRwController(Controller<String> pRwController) {
		this.pRwController = pRwController;
	}

	public void setqRwController(Controller<String> qRwController) {
		this.qRwController = qRwController;
	}

	public void setIntrdrRwController(Controller<String> intrdrRwController) {
		this.intrdrRwController = intrdrRwController;
	}
	
	public String getPassedMessage() {
		return "{" + this.nw + " " + this.rand + " " + this.nonces + " " + this.prins + " " + "rw_p: " + this.pRwController + " rw_q: " + this.qRwController + " rw_intrdr: " + this.intrdrRwController + "}";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		
		NspkConfiguration other = (NspkConfiguration) obj;
		return GFG.getSHA(this.toString()).equals(GFG.getSHA(other.toString()));
	}

	@Override
	public String toString() {
		return this.rand + "\t" + this.nonces + "\t" + this.nw; // for analysis
//		return "{" + this.nw + " " + this.rand + " " + this.nonces + " " + this.prins + "}"; // for state
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
