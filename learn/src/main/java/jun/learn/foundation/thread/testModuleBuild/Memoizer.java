package jun.learn.foundation.thread.testModuleBuild;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import jun.learn.foundation.thread.util.GuardedBy;
import jun.learn.foundation.thread.util.ThreadSafe;



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
		// 缓存的是可返回的任务
		private final ConcurrentHashMap<A, Future<V>> cache = new ConcurrentHashMap<A, Future<V>>();
		private final Computable<A, V> c;
		
		public Memoizer2(Computable<A, V> c){
			this.c = c;
		}

		public V compute(final A arg) throws InterruptedException {
			/**
				1. 从缓存中取出对应的任务
				2. 判断取出的任务是否为空
				3. 如果为空，则放入一个新的任务，（如果为空则放入，否则返回之前已有的任务，注意此操作为原子性操作）
				4. 如果返回的是null（说明这是一次添加操作），则将新任务赋值给这个变量，并执行新任务。
				第一次的取缓存，与第二次的放缓存有一定的依赖性。
				但在放缓存时，做了特殊的处理，并且考虑到了如何处理这个依赖性
				从而使得整个类的不变性条件不收到影响
			*/
			Future<V> f = cache.get(arg);
			if (f == null) {
				Callable<V> call = new Callable<V>(){
					@Override
					public V call() throws Exception {
						return c.compute(arg);
					}
				};
				FutureTask<V> ft = new FutureTask<V>(call);
				f = cache.putIfAbsent(arg, ft);
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
	
	@ThreadSafe
	public class Memoizer1<A, V> implements Computable<A, V> {
		@GuardedBy("this")
		private final Map<A, V> cache = new HashMap<A, V>();
		private final Computable<A, V> c;
		
		public Memoizer1(Computable<A, V> c){
			this.c = c;
		}
		// 粗粒度的锁，影响性能
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
