package atomic;

/**
 * Lock implements Test&Set protocol
 * 
 * @author OgataLab
 *
 */
public interface Lock {
	/**
	 * Request to enter critical section 
	 */
	public void requestCS();

	/**
	 * Release critical section
	 */
	public void releaseCS();
}
