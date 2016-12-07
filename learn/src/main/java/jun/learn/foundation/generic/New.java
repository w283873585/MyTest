package jun.learn.foundation.generic;

import java.util.*;


public class New {
	
	// �ܸ��������Ͳ����ƶ�
	public static <K, V> Map<K, V> map() {
		return new HashMap<K, V>();
	}
	
	// ���ͷ�������ʽ����˵��
	public static void f(Map<String, List<Pet>> petPeople) {
		
	}
	
	public static void main(String[] args) {
		Map<String, String> map = New.map();
		map.put("123", "123");
		map.put("11", "123");
		map.put("112331223", "123");
		map.put("112223", "123");
		System.out.println(map);
		
		// ���ͷ�������ʽ����˵��
		f(New.<String, List<Pet>>map());
	}
	
	class Pet{}
}
