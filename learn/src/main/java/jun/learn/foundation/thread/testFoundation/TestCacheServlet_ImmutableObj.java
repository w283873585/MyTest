package jun.learn.foundation.thread.testFoundation;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jun.learn.foundation.thread.util.ThreadSafe;
import jun.learn.foundation.thread.util.ThreadUtil;
import jun.learn.foundation.thread.util.ThreadUtil.Task;

/**
 * 用不变对象同步一个缓存因数的servlet
 * @author Administrator
 *
 */
@ThreadSafe
public class TestCacheServlet_ImmutableObj {
	// one situation for concurrent
	// @Immutable
	public static class OneValueCache {
		private final BigInteger lastNumber;
		private final BigInteger[] lastFactors;
		
		public OneValueCache(BigInteger i, BigInteger[] factors) {
			lastNumber = i;
			lastFactors = factors;
		}
		
		public BigInteger[] getFactors(BigInteger i) {
			if (lastNumber == null || !lastNumber.equals(i)) {
				return null;
			} else {
				return Arrays.copyOf(lastFactors, lastFactors.length);
			}
		}
		
		public String toString() {
			String factors = "";
			for (BigInteger b : lastFactors) {
				factors += " " + b.toString();
			}
			return Thread.currentThread().toString() 
				+  "    lastNumber-->" + lastNumber 
				+  "    lastFactors--> " + factors 
				+  "    path-->" + lastFactors;
		}
	}
	
	
	public static class OneServlet {
		private volatile OneValueCache cache = new OneValueCache(null, null);
		
		// 分析此类的不变性条件是： 必须得到请求进入的数的因数集合
		// 而cache变量的不可变，保证了从其拿出来的值一定是正确的因数集合，否则拿不到。
		// volatile可以增加缓存命中的几率
		public void service(BigInteger i) {
			System.out.println(Thread.currentThread().toString() + "---->" + i);
			BigInteger[] factors = cache.getFactors(i);
			if (factors == null) {
				factors = factor(i);
				cache = new OneValueCache(i, factors);
			}
			// 输出因数
			print(factors);
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