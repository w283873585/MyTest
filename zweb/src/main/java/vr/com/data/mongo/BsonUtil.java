package vr.com.data.mongo;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.alibaba.fastjson.JSONObject;

import vr.com.pojo.Pojo;

public class BsonUtil {
	public static Bson toBson(Object obj)  {
		JSONObject result = new JSONObject();
		JSONObject body = new JSONObject();
		
		try {
			Stack<String> keyQueue = new Stack<String>();
			Stack<Field> fieldQueue = new Stack<Field>();
			Deque<Object> objDeque = new LinkedList<Object>();
			
			objDeque.add(obj);
			fieldQueue.addAll(getFieldList(obj));
			
			while (!fieldQueue.isEmpty()) {
				Field curField = fieldQueue.pop();
				if (curField == null) {
					objDeque.removeLast();
					keyQueue.pop();
					continue;
				}
				
				curField.setAccessible(true);
				keyQueue.add(curField.getName());
				boolean isPojo = curField.getType().getAnnotation(Pojo.class) != null;
				
				if (!isPojo) {
					String key = keyQueue.pop();
					for (int i = keyQueue.size() - 1; i >= 0; i--)
						key = keyQueue.get(i) + "." + key;
					body.put(key, curField.get(objDeque.getLast()));
				} else {
					Object childObj = curField.get(objDeque.getLast());
					objDeque.addLast(childObj);
					fieldQueue.add(null);
					fieldQueue.addAll(getFieldList(childObj));
				}
			}
		} catch (Exception e) {}
		result.put("$set", body);
		return new Document(result);
	}
	
	private static List<Field> getFieldList(Object obj) {
		Class<?> c = obj.getClass();
		Field[] fArr = c.getDeclaredFields();
		return Arrays.asList(fArr);
	}
	
	@Pojo
	public static class O{
		public int a;
		public String f;
		public B bb = new B();
	}
	
	@Pojo
	public static class B{
		public int d = 0;
		public int e = 1;
		public C c = new C();
	}
	
	@Pojo
	public static class C{
		public int c = 2;
	}
	
	public static void main(String[] args) throws Exception {
		O o = new O();
		o.a = 4;
		o.f = "3";
		System.out.println(toBson(o));
	}
}
