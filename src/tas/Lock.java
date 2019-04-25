package tas;

public interface Lock {
	public void requestCS();

	public void releaseCS();
}
