package jun.learn.foundation.generic;

public class LinkedList<T> {
	private static class Node<U> {
		U item;
		Node<U> next;
		Node(U u, Node<U> next) {
			this.item = u;
			this.next = next;
		}
		Node() {
			this.item = null;
			this.next = null;
		}
		// ĩ���ڱ�
		boolean end() {
			return item == null && next == null;
		}
	}
	
	private Node<T> top = new Node<T>();
	
	public void push(T t) {
		top = new Node<T>(t, top);
	}
	
	public T pop() {
		T ret = top.item;
		if (!top.end()) {
			top = top.next;
		}
		return ret;
	}
	
	
	public static void main(String[] args) {
		LinkedList<String> list = new LinkedList<String>();
		list.push("1");
		list.push("2");
		list.push("3");
		String item;
		while ( (item = list.pop()) != null) {
			System.out.println(item);
		}
		// java =��ֵҲ���з���ֵ��
		Object obj = new Object();
		System.out.println((obj = "123") == obj);       //true
		System.out.println((obj = "123").getClass());   //class java.lang.String
		System.out.println(obj.getClass()); 			//class java.lang.String
	}
}
