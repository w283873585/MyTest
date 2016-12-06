package learn.atest.ABCThreadPrint;

import java.util.concurrent.atomic.AtomicInteger;

class MyTask extends Thread {

    private AtomicInteger gData;
    private int     n;
    private String  info;

    public MyTask(AtomicInteger gData, int n, String info) {
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
                	System.out.print(info + " ");
                	Thread.sleep(100);
                	gData.incrementAndGet();
                }
	            synchronized (gData) {
	            }
	        }
            Thread.yield();
            sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) throws InterruptedException {
        AtomicInteger gData = new AtomicInteger(0);
        Thread  thread1 = new MyTask(gData, 0, "A");
        Thread  thread2 = new MyTask(gData, 1, "B");
        Thread  thread3 = new MyTask(gData, 2, "C");

        thread1.start();
        thread2.start();
        thread3.start();
    }
}