package com.thread.testModuleBuild;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.LinkedBlockingQueue;





/**
 * 线程栅栏
 * Semaphore
 * @author Administrator
 *
 */
public class TestBarrier{
	public static class Guoshanche {
		private BlockingQueue<Visitor> queue = new LinkedBlockingQueue<Visitor>(5);
		public CyclicBarrier cb = new CyclicBarrier(5, new Runnable() {
			private int i = 0;
			@Override
			public void run() {
				// 同时处理5个上车的人
				queue.poll().sayHello();
				queue.poll().sayHello();
				queue.poll().sayHello();
				queue.poll().sayHello();
				queue.poll().sayHello();
				System.out.println("最后一个上车的是" + Thread.currentThread().toString());
				i++;
				System.out.println("这是第" + i + "趟车");
				System.out.println("过山车开始出发咯。。。");
			}
		});
		
		public void acceptOn(Visitor visitor) {
			try {
				queue.put(visitor);
				cb.await();
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static class Visitor extends Thread{
		private final String name;
		private final Guoshanche gsc;
		public Visitor(String name, Guoshanche gsc) {
			this.name = name;
			this.gsc = gsc;
		}
		public void run() {
			gsc.acceptOn(this);
		}
		public void sayHello() {
			System.out.println(name + ": hello");
		}
		public String toString() {
			return name;
		}
	}
	
	public static void main(String[] args) {
		Guoshanche gsc = new Guoshanche();
		for (int i = 0; i < 100; i++) {
			new Visitor("游客" + i, gsc).start();
		}
	}
}
