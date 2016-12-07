package jun.learn.foundation.innerClass;

public class TestForNestedClass {
	/**
		Ƕ�������ͨ���ڲ��໹��һ��������ͨ�ڲ��಻����static���ݺ�static���ԣ�Ҳ���ܰ���Ƕ���࣬��Ƕ�������
	 */
	
	private static String name = "��Χ��";
	private static int count = 0;
	
	public static class NestedClass{
		/**
		 * ��̬�ڲ��࣬Ҳ����Ƕ����
		 * �ɷ�����Χ�����о�̬��Ա
		 * ����Χ����������Ĺ���,���ʲ�����Χʵ�����������	 �������ƾ�̬������������
		 * ������������ͨ������
		 */
		static{
			System.out.println(name);
			count++;
		}
		public void show(){
			
			System.out.println("���ܸɺܶ���");
		}
		public NestedClass(String str){
			System.out.println(str);
		}
	}
	
	
	public static void main(String[] args) {
		/* ����Ϊ��������ʹ��	*/
		NestedClass nc = new TestForNestedClass.NestedClass("���빹�캯��");
		nc.show();
		System.out.println(TestForNestedClass.count);
	}
}
