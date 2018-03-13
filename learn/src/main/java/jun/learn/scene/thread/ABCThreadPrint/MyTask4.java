package jun.learn.scene.thread.ABCThreadPrint;
class MyTask4 extends Thread {
    private Object prev;
    private Object curr;
    private String info;

    public MyTask4(Object prev, Object curr, String info) {
        this.prev = prev;
        this.curr = curr;
        this.info = info;
    }

    public void run() {
        int cnt = 10;

        while (cnt-- > 0) {
        	synchronized (prev) {
                synchronized (curr) {
                	System.out.print(info + " ");
                    curr.notify();
                }

                try {
                    prev.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    
    public static void main(String[] args) throws InterruptedException {
        Object A = new Object();
        Object B = new Object();
        Object C = new Object();

        MyTask4 myThread1 = new MyTask4(C, A, "A");
        MyTask4 myThread2 = new MyTask4(A, B, "B");
        MyTask4 myThread3 = new MyTask4(B, C, "C\n");

        myThread1.start();
        Thread.sleep(10);
        myThread2.start();
        Thread.sleep(10);
        myThread3.start();

        try {
            myThread1.join();
            myThread2.join();
            myThread3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}