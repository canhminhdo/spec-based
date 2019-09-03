package main.abp;

import java.io.Serializable;

public class Cell<E> implements Serializable {
    private E element;
    public Cell(E e) { this.element = e; }
    public E get() { return element; }
    public void set(E e) { this.element = e; }
    public String toString() { return element.toString(); }
    
    @Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		Cell<E> other = (Cell<E>) obj;
		return this.get().equals(other.get());
	}
}