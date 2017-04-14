package jun.learn.scene.thread.sellTicket;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
 
public class SellTicket implements Runnable {
    private int count = 100;
    private static Map<String,Integer> map = new ConcurrentHashMap<String,Integer>(); 
     
    public static void main(String[] args) {
        SellTicket st = new SellTicket();
        for (int i = 0; i < 20; i++) {
            Thread thread = new Thread(st);
            map.put(thread.getName(), 0);
            thread.start();
        }
    }
 
    public void run() {
    	String threadName = Thread.currentThread().getName();
        while (true) {
    		if (count == 0) {
    			int cCount = 0;
    			for (Entry<String,Integer> e : map.entrySet())
    				cCount += e.getValue();
    			System.out.println("finally: " + cCount);
    			return;
    		}  
    			if (count > 0) {
    				synchronized (this) { 
    					count--; 
    					// System.out.println(123);
    				}
	    		}
    		for (Entry<String,Integer> e : map.entrySet()) {
    			if (e.getKey().equals(threadName))
    				map.put(threadName, e.getValue().intValue() + 1);
    		}
        }
    }
 
}