package jun.learn.foundation.innerClass;

public class TestForPartInnerClass {
	/**
		 �ֲ��ڲ���Ҳ������һ�����б��룬
		 ��ֻ��������ͬ���ѣ�
		 ֻ�ڸ÷������������������ڲ���ʹ�ã�
		 �˳���Щ��������޷����õġ�
	*/
	
	/**	�ֲ��ڲ��ඨ���ڷ����� **/
	public TestForPartInnerInterface get(String s){
		
		/**
		 * �ֲ��ڲ���
		 * �����ڵķ������β���Ҫ���ڲ�������ʹ��ʱ�����βα���Ϊfinal��
		 * ----------------ԭ��--------------
		 * �ڲ������Χ���ڱ���ʱ�����������ļ���
		 * �ڲ����ڵ�����Χ�෽���β�ʱ��
		 * �õ������ô��ݣ����������ã�
		 * Ϊ�˱�������ֵ�����ı䣬
		 * ���类�ⲿ��ķ����޸ĵȣ��������ڲ���õ���ֵ��һ�£�
		 * ������final���ø����ò��ɸı�)
		 * 
		 */
		
		class PDestination implements TestForPartInnerInterface { 
			
            private PDestination(String whereTo) { 
               
            } 
			public void show() {
				System.out.println(234);
			} 
        } 
        return new PDestination(s); 
		
	}
	
	/**	�ֲ��ڲ��ඨ������������ **/
	{
		boolean flag = true;
		if(flag){
			class hello{
				
			}
		}
		
	}
	
	
	
	
}
