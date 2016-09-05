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

import vr.com.data.Condition;
import vr.com.pojo.Pojo;

public class MongoUtil {
	
	public static JSONObject toPlainObject(Object obj) {
		JSONObject result = new JSONObject();
		
		try {
			Stack<Field> fieldStack = new Stack<Field>();
			Stack<String> keyStack = new Stack<String>();
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
						result.put(key, curField.get(valueDeque.getLast()));
				} else {
					Object childObj = curField.get(valueDeque.getLast());
					valueDeque.addLast(childObj);
					// add a null as a separator
					fieldStack.add(null);
					fieldStack.addAll(getFieldList(childObj));
				}
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private static List<Field> getFieldList(Object obj) {
		Class<?> c = obj.getClass();
		Field[] fArr = c.getDeclaredFields();
		return Arrays.asList(fArr);
	}

	public static Bson toUpdateBson(Object obj)  {
		JSONObject result = new JSONObject();
		result.put("$set", toPlainObject(obj));
		return new Document(result);
	}
	
	/**
	 * ToBson
	 */
	public static Bson toBson(Condition c) {
		if (c instanceof MongoCondition)
			return new Document((MongoCondition) c);
		return new Document();
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
		System.out.println(toUpdateBson(o));
		System.out.println(toPlainObject(o));
	}
}
