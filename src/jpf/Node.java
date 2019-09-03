package jpf;

import java.util.ArrayList;
import java.util.List;

/**
 * Tree data structure to maintain sequence of state
 * 
 * @author OgataLab
 *
 * @param <T>
 */
public class Node<T> {

	private T data = null;

	private List<Node<T>> children = new ArrayList<Node<T>>();

	private Node<T> parent = null;

	public Node() {
	}

	public Node(T data) {
		this.data = data;
	}

	public Node<T> addChild(Node<T> child) {
		child.setParent(this);
		this.children.add(child);
		return child;
	}

	public void addChildren(List<Node<T>> children) {
		children.forEach(each -> each.setParent(this));
		this.children.addAll(children);
	}

	public List<Node<T>> getChildren() {
		return children;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	private void setParent(Node<T> parent) {
		this.parent = parent;
	}

	public Node<T> getParent() {
		return parent;
	}

	public boolean isRoot() {
		return (this.parent == null);
	}

	public boolean isLeaf() {
		return this.children.size() == 0;
	}

	@Override
	public String toString() {
		if (parent != null) {
			return "Node [parent=" + parent.getData() + ", data=" + data + "]";
		}
		return "Node [data=" + data + "]";
	}
}
