package com.thread.testThreadAbort;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * 协作中断
 * 用boolean协作中断
 */
public class BrokenPrimeProducer extends Thread{
	private final BlockingQueue<BigInteger> queue;
	private volatile boolean cancelled = false;
	
	public BrokenPrimeProducer(BlockingQueue<BigInteger> queue) {
		this.queue = queue;
	}
	@Override
	public void run() {
		try {
			BigInteger p = BigInteger.ONE;
			while (!cancelled) {
				queue.put(p = p.nextProbablePrime());
			}
			
		} catch (InterruptedException e) { }
	}
	
	public void cancel() { this.cancelled = true; }
	
	static void consumePrimes() throws InterruptedException {
		BlockingQueue<BigInteger> primes = new LinkedBlockingQueue<BigInteger>();
		BrokenPrimeProducer producer = new BrokenPrimeProducer(primes);
		producer.start();
		try {
			while (needMorePrimes()) {
				consume(primes.take());
			}
		} finally {
			producer.cancel();
		}
	}
	private static void consume(BigInteger take) {}
	private static boolean needMorePrimes() {
		return false;
	}
}
