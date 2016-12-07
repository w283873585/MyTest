package jun.learn.foundation.generic;

public class LinkedList_t<T> {
	private class Node {
		T item;
		Node next;
		Node(T u, Node next) {
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
	
	private Node top = new Node();
	
	public void push(T t) {
		top = new Node(t, top);
	}
	
	public T pop() {
		T ret = top.item;
		if (!top.end()) {
			top = top.next;
		}
		return ret;
	}
	
	
	public static void main(String[] args) {
		LinkedList_t<String> list = new LinkedList_t<String>();
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
