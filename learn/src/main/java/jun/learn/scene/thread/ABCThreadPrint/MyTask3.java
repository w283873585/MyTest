package jun.learn.scene.thread.ABCThreadPrint;

class MyTask3 extends Thread {

    private VariableInteger gData;
    private int     n;
    private String  info;

    public MyTask3(VariableInteger gData, int n, String info) {
        super("thread " + info);
        this.gData = gData;
        this.n = n;
        this.info = info;
    }

    public void run() {
    	try {
	        int i = 10;
	
	        while (i > 0) {
                if (gData.get() % 3 == n) {
                	i--;
                	Thread.sleep(100);
                	System.out.print(info + " ");
                	gData.increment();
                }
	        }
            Thread.yield();
            sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    
    public static class VariableInteger{
    	/**
    	 * 可见性
    	 */
    	private volatile int value;
    	
    	public VariableInteger(int value) {
    		this.value = value;
    	}
    	
    	/**
    	 * synchronized可见性：
    	 * 		获取锁能保证可见性
    	 */
    	
    	public void increment() {
    		this.value++;
    	}
    	
    	public int get() {
    		return value;
    	}
    }
    
    public static void main(String[] args) throws InterruptedException {
    	VariableInteger gData = new VariableInteger(0);
        
    	Thread  thread1 = new MyTask3(gData, 0, "A");
        Thread  thread2 = new MyTask3(gData, 1, "B");
        Thread  thread3 = new MyTask3(gData, 2, "C\n");

        thread1.start();
        thread2.start();
        thread3.start();
    }
}