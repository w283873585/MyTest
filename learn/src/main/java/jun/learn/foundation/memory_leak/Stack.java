package jun.learn.foundation.memory_leak;

import java.util.Arrays;
import java.util.EmptyStackException;

public class Stack {
	private Object[] elements;
	private int size = 0;
	private static final int DEFAULT_INITIAL_CAPATITY = 16;
	
	public Stack(){
		elements = new Object[DEFAULT_INITIAL_CAPATITY];
	}
	
	public void push(Object e){
		ensureCapatity();
		elements[size++] = e;
	}
	
	public Object pop(){
		if(size==0){
			throw new EmptyStackException();
		}
		Object result = elements[--size];
		elements[--size] = null;	// Eliminate obsolete reference
		return result;
	}
	
	private void ensureCapatity(){
		if(elements.length == size){
			elements = Arrays.copyOf(elements, 2 * size + 1);
		}
	}
	
	
	public static void main(String[] args) {
	
	}
}
