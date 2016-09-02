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
			Stack<String> keyStack = new Stack<String>();
			Stack<Field> fieldStack = new Stack<Field>();
			Deque<Object> valueDeque = new LinkedList<Object>();
			
			valueDeque.add(obj);
			fieldStack.addAll(getFieldList(obj));
			
			while (!fieldStack.isEmpty()) {
				Field curField = fieldStack.pop();
				if (curField == null) {
					valueDeque.removeLast();
					keyStack.pop();
					continue;
				}
				
				curField.setAccessible(true);
				keyStack.add(curField.getName());
				boolean isPojo = curField.getType().getAnnotation(Pojo.class) != null;
				
				if (!isPojo) {
					String key = keyStack.pop();
					for (int i = keyStack.size() - 1; i >= 0; i--)
						key = keyStack.get(i) + "." + key;
					body.put(key, curField.get(valueDeque.getLast()));
				} else {
					Object childObj = curField.get(valueDeque.getLast());
					valueDeque.addLast(childObj);
					// add a null as a separator
					fieldStack.add(null);
					fieldStack.addAll(getFieldList(childObj));
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
