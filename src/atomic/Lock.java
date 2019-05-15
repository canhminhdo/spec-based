package atomic;

public interface Lock {
	public void requestCS();

	public void releaseCS();
}
