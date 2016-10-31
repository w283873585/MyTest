package learn.atest.ABCThreadPrint;

class MyTask2 extends Thread {

    private int gData;
    private int     n;
    private String  info;

    public MyTask2(int gData, int n, String info) {
        super("thread " + info);
        this.gData = gData;
        this.n = n;
        this.info = info;
    }

    public void run() {
    	try {
	        int i = 10;
	
	        while (i > 0) {
                if (gData % 3 == n) {
                	i--;
                	System.out.print(info + " ");
                	Thread.sleep(100);
                	gData++;
                }
	           
	        }
            Thread.yield();
            sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) throws InterruptedException {
    	Integer gData = new Integer(0);
    	Integer back = gData;
        gData++;
        System.out.println(back);			// 0
        System.out.println(gData == back);	// false, Integer对象自增后, 引用直接改变了指向
        Thread  thread1 = new MyTask2(gData, 0, "A");
        Thread  thread2 = new MyTask2(gData, 1, "B");
        Thread  thread3 = new MyTask2(gData, 2, "C");

        thread1.start();
        thread2.start();
        thread3.start();
    }
}