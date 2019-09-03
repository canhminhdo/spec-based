package main.abp;

import java.io.Serializable;

import atomic.Lock;

public class Channel<P> implements Serializable {
    private Queue<P> queue;
    private final int bound;
    private int nop;
    private Lock lock;

    public Channel(int bound) {
        this.queue = new EmptyQueue<P>();
        this.bound = bound;
        this.nop = 0;
    }
    
    public void setLock(Lock lock) {
    	this.lock = lock;
    }
    
    public Lock getLock() {
    	return lock;
    }

    public P put(P p) {
    	lock.requestCS();
        if (bound <= nop) {
        	lock.releaseCS();
        	return null;
        }
        queue = queue.enqueue(p);
        nop++;
        lock.releaseCS();
        return p;
    }
    
    public P put_asyn(P p) {
        if (bound <= nop) {
        	lock.releaseCS();
        	return null;
        }
        queue = queue.enqueue(p);
        nop++;
        return p;
    }


    public P get() {
//    	lock.requestCS();
        if (nop <= 0) {
//        	lock.releaseCS();
        	return null;
        }
        P p = queue.top();
        queue = queue.dequeue();
        nop--;
//        lock.releaseCS();
        return p;
    }

    public P duptop() {
    	lock.requestCS();
        if (bound <= nop || nop <= 0) {
        	lock.releaseCS();
        	return null;
        }
        queue = queue.duptop();
        nop++;
        P p = queue.top();
        lock.releaseCS();
        return p;
    }
    
    public String toString() {
    	if (queue instanceof EmptyQueue)
    		return queue.toString();
    	return "(" + queue.toString() + ")";
    }
    
    public String toCommand() {
    	if (queue instanceof EmptyQueue)
    		return queue.toCommand();
    	return queue.toCommand();
    }
    
    public Queue<P> getQueue() {
		return queue;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		Channel<P> other = (Channel<P>) obj;
		return this.getQueue().equals(other.getQueue()); 
	}
}