package com.thread.testModuleBuild;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;



/**
 * 缓存计算
 * Semaphore
 * @author Administrator
 *
 */
public class Memoizer{
	public interface Computable<A, V> {
		V compute(A arg) throws InterruptedException;
	}
	
	public class ExpensiveFunction implements Computable<String, BigInteger> {
		public BigInteger compute(String arg) throws InterruptedException {
			return new BigInteger(arg);
		}
	}
	
	public class Memoizer2<A, V> implements Computable<A, V> {
		private final ConcurrentHashMap<A, Future<V>> cache = new ConcurrentHashMap<A, Future<V>>();
		private final Computable<A, V> c;
		
		public Memoizer2(Computable<A, V> c){
			this.c = c;
		}

		public synchronized V compute(final A arg) throws InterruptedException {
			Future<V> f = cache.get(arg);
			if (f == null) {
				Callable<V> call = new Callable<V>(){
					@Override
					public V call() throws Exception {
						return c.compute(arg);
					}
				};
				FutureTask<V> ft = new FutureTask<V>(call);
				f = cache.putIfAbsent(arg, f);
				if (f == null) {
					f = ft;
					ft.run();
				}
			}
			try {
				return f.get();
			} catch (ExecutionException e) {
				return null;
			}
		}
	}
	
	
	public class Memoizer1<A, V> implements Computable<A, V> {
		private final Map<A, V> cache = new HashMap<A, V>();
		private final Computable<A, V> c;
		
		public Memoizer1(Computable<A, V> c){
			this.c = c;
		}

		public synchronized V compute(A arg) throws InterruptedException {
			V result = cache.get(arg);
			if (result == null) {
				result = c.compute(arg);
				cache.put(arg, result);
			}
			return result;
		}
	}
}
