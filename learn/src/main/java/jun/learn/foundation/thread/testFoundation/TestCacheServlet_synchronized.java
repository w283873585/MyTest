package jun.learn.foundation.thread.testFoundation;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import jun.learn.foundation.thread.util.GuardedBy;
import jun.learn.foundation.thread.util.ThreadUtil;
import jun.learn.foundation.thread.util.ThreadUtil.Task;

/**
 * 同步一个缓存因数的servlet，
 * 另附加了缓存命中计数
 * @author Administrator
 *
 */
public class TestCacheServlet_synchronized {
	public static class OneServlet {
		@GuardedBy("this") private BigInteger[] lastFactors;
		@GuardedBy("this") private BigInteger lastNumber;
		@GuardedBy("this") private long hits;
		@GuardedBy("this") private long cacheHits;
		
		
		public synchronized long getHits() {
			return hits;
		}
		public synchronized long getCacheHits() {
			return cacheHits;
		}
		
		// 利用内置锁，保护多个变量的操作。
		public void service(BigInteger i) {
			System.out.println(Thread.currentThread().toString() + "---->" + i);
			BigInteger[] factors = null;
			// 获取缓存中的因数
			// 该方法是由内置锁保护，
			factors = getFactors(i);
			if (factors == null) {
				factors = factor(i);
				// 保存新生成的因数
				// 该方法也是由内置锁绑定
				saveFactors(i, factors);
			}
			// 抽离同步块结构体到私有同步方法。
			// 结构更清晰，强化表达。
			// 封装有利于线程安全的管理
			/*
			synchronized (this) {
				++hits;
				if (i.equals(lastNumber)) {
					++cacheHits;
					factors = lastFactors.clone();
				}
			}
			if (factors == null) {
				factors = factor(i);
				synchronized (this) {
					lastNumber = i;
					lastFactors = factors;
				}
			}
			*/
			// 输出因数
			print(factors);
		}
		// 内置锁锁定的方法，
		// 用来更新缓存因数
		private synchronized void saveFactors(BigInteger i, BigInteger[] factors) {
			lastNumber = i;
			lastFactors = factors;
		}
		// 内置锁锁定的方法，
		// 用来获取缓存因数
		private synchronized BigInteger[] getFactors(BigInteger i) {
			++hits;
			if (i.equals(lastNumber)) {
				++cacheHits;
				return lastFactors.clone();
			}
			return null;
		}
		
		
		private BigInteger[] factor(BigInteger i) {
			List<BigInteger> result = new ArrayList<BigInteger>();
			for (int j = 2; j < i.intValue(); j++) {
				if (i.intValue() % j == 0) {
					result.add(new BigInteger(String.valueOf(j)));
				}
			}
			BigInteger[] ret = new BigInteger[result.size()];
			return result.toArray(ret);
		}
		
		private void print(BigInteger[] arr) {
			String factors = "";
			for (BigInteger b : arr) {
				factors += " " + b.toString();
			}
			System.out.println(Thread.currentThread().toString() + "="+ factors);
		}
	}
	
	public static void main(String[] args) {
		final OneServlet servlet = new OneServlet();
		final BigInteger[] arr = {
			BigInteger.valueOf(10L),
			BigInteger.valueOf(20L),
			BigInteger.valueOf(21L),
			BigInteger.valueOf(25L),
			BigInteger.valueOf(10L),
			BigInteger.valueOf(10L),
			BigInteger.valueOf(21L),
			BigInteger.valueOf(25L),
			BigInteger.valueOf(10L),
			BigInteger.valueOf(10L)
		};
		ThreadUtil.exec(new Task(){
			@Override
			public void invoke(int index) {
				servlet.service(arr[index]);
			}
		}, 10);
	}
}