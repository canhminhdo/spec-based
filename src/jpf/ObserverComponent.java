package jpf;

public class ObserverComponent<K,V> {
	private K key;
	private V val;
	
	public ObserverComponent(K key, V val) {
		this.key = key;
		this.val = val;
	}
	public K getKey() {
		return key;
	}
	public void setKey(K key) {
		this.key = key;
	}
	public V getVal() {
		return val;
	}
	public void setVal(V val) {
		this.val = val;
	}
}
