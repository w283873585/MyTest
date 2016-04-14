package com.thread.testThreadAbort;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


/**
 * 协作中断
 * 用boolean协作中断
 */
public class PrimeGenerator implements Runnable{
	private final List<BigInteger> primes 
		= new ArrayList<BigInteger>();
	private volatile boolean cancelled;
	
	@Override
	public void run() {
		BigInteger p = BigInteger.ONE;
		while (!cancelled) {
			p = p.nextProbablePrime();
			synchronized (this) {
				primes.add(p);
			}
		}
	}
	
	public void cancel() { this.cancelled = true; }
	
	public synchronized List<BigInteger> get() {
		return new ArrayList<BigInteger>(primes);
	}
}
