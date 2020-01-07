package jpf.common;

import java.io.Serializable;

/**
 * Observer Component for all case studies
 * 
 * @author OgataLab
 *
 */
public interface OC extends Serializable {
	
	public boolean isReady();
	
	public int getStateId();

	public boolean isFinished();
	
	public String getPassedMessage();
	
	public int getCurrentDepth();
	
	public void setCurrentDepth(int depth);

}
