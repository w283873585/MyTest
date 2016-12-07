package jun.learn.foundation.thread.testBuildSyncUtil;

public class TestNotify {
	public static void main(String[] args) {
		BeObj bObj = new BeObj();
		try {
			synchronized (bObj) {
				new Object().notify();
				bObj.wait();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(123);
	}
	
	
	
	public static class BeObj{}
}
