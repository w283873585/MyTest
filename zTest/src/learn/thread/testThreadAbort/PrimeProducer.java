package com.thread.testThreadAbort;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * 协作中断
 * 用boolean协作中断
 */
public class PrimeProducer extends Thread{
	private final BlockingQueue<BigInteger> queue;
	
	public PrimeProducer(BlockingQueue<BigInteger> queue) {
		this.queue = queue;
	}
	@Override
	public void run() {
		try {
			System.out.println("self-" + Thread.currentThread().getName());
			BigInteger p = BigInteger.ONE;
			while (!Thread.currentThread().isInterrupted()) {
				queue.put(p = p.nextProbablePrime());
			}
		} catch (InterruptedException e) { 
			System.out.println(Thread.currentThread().getName());
			Thread.currentThread().interrupt();
		/* 允许程序退出 */ }
	}
	
	static void consumePrimes() throws InterruptedException {
		System.out.println("main-" + Thread.currentThread().getName());
		BlockingQueue<BigInteger> primes = new LinkedBlockingQueue<BigInteger>(1);
		PrimeProducer producer = new PrimeProducer(primes);
		producer.start();
		Thread.sleep(1000);
		producer.interrupt();
		try {
			while (needMorePrimes()) {
				consume(primes.take());
			}
		} finally {
			producer.interrupt();
		}
	}
	private static void consume(BigInteger take) {}
	private static int count = 0;
	private static boolean needMorePrimes() {
		if (count < 10) {
			count++;
			return true;
		}
		return false;
	}
	
	
	public static void main(String[] args) throws InterruptedException {
		consumePrimes();
	}
}
