package jun.learn.foundation.generic;

import java.util.ArrayList;
import java.util.List;

public class GenericVarargs {
	public static <T> List<T> makeList(T... args) {
		List<T> list = new ArrayList<T>();
		for (T t : args) {
			list.add(t);
		}
		return list;
	}
	
	public static void main(String[] args) {
		List list = makeList("1","1",2,false);		// �о�Ҳ�������ƶ�������
		System.out.println(list.get(3).getClass()); //class java.lang.Boolean
		// ˵�����ǻ���Ӳ�ͬ���͵�
		// List<String> list_t = makeList("1","1",2,false); //Error, �����ͼ��
	}
}
