package abp.main;

import java.io.Serializable;

public class EmptyQueue<E> implements Queue<E>, Serializable {
	
    public EmptyQueue() {
    	
    }
    
    public E top() {
    	return null;
	}
    
    public NeQueue<E> enqueue(E e) {
        Queue<E> eq = new EmptyQueue<E>();
        return new NeQueue<E>(e,eq);
    }
    
    public Queue<E> dequeue() { return this; }
    public Queue<E> duptop() { return this; }
    
    public String toString() {
    	return "nil";
    }

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj instanceof NeQueue) {
			return false;
		}
		return true;
	}

	@Override
	public String toCommand() {
		return "nil";
	}
}