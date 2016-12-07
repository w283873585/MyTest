package jun.learn.foundation.innerClass;


public class TestForInnerClass {
	
	private static String name = "��Χ��";
	private void show(){
		
		System.out.println("show something");
	}
	
	/**
	 * �����Ƽ�ʹ��getxxx()����ȡ��Ա�ڲ��࣬�����Ǹ��ڲ���Ĺ��캯���޲���ʱ 
	 * @return
	 */
	public InnerClass getInner(){
		
		return new InnerClass();
	}
	/**
	 * ��Ա�ڲ���
	 */
	private class InnerClass{
		/**
		 * �ڲ����ǰ�����Χ��ʵ���ϵ�
		 * ���ɷ�����Χ�����е�����
		 * ��Ҳ������Χ��ĳ�Ա
		 * ��Ա�ڲ��಻�ܺ���static�ı����ͷ���,�����
		 */
		{
			System.out.println(name);
			TestForInnerClass.this.show();
		}
	}
	
	
	public static void main(String[] args) {
		TestForInnerClass tfc = new TestForInnerClass();
		InnerClass ic = tfc.getInner();
		/** ��ӵ��һ��Ȩ��ʱ��������  .new �ķ�ʽʵ����Ա�ڲ��� **/
		InnerClass ic1 = tfc.new InnerClass();
		
	}
	
}
