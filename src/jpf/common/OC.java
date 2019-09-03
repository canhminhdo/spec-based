package jpf.common;

import java.io.Serializable;

/**
 * Observer Component for all case studies
 * 
 * @author OgataLab
 *
 */
public interface OC extends Serializable {

	public int getStateId();

	public boolean isFinished();
}
