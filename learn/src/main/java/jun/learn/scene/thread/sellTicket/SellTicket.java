package jun.learn.scene.thread.sellTicket;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
 
public class SellTicket implements Runnable {
     
    private int count = 100;
    static Map<String,Integer> map = new ConcurrentHashMap<String,Integer>(); 
     
    public static void main(String[] args) {    
        SellTicket st = new SellTicket();
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(st);
            map.put(thread.getName(), 0);
            thread.start();
        }
    }
 
    public void run() {
        while (true) {
            synchronized(this){
                if(count > 0){
                    count--;
                    System.out.println("�߳�=" + Thread.currentThread().getName() + "��ʣ��Ʊ��=" + count);                    
                    Iterator<Map.Entry<String,Integer>> i = map.entrySet().iterator();
                    while(i.hasNext()){
                        Map.Entry<String,Integer> entry=(Map.Entry<String,Integer>)i.next();
                        if(entry.getKey().equals(Thread.currentThread().getName()))
                            map.put(entry.getKey(), entry.getValue().intValue() + 1);
                    }                   
                }else if(count==0){                 
                    Iterator<Map.Entry<String,Integer>> i = map.entrySet().iterator();
                    while(i.hasNext()){
                        Map.Entry<String,Integer> entry=(Map.Entry<String,Integer>)i.next();
                        System.out.println("�߳�=" + entry.getKey() + "����Ʊ��=" + entry.getValue());
                    }       
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
 
}