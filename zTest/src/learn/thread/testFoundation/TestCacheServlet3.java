package com.thread.testFoundation;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thread.util.ThreadUtil;
import com.thread.util.ThreadUtil.Task;

/**
 * 同步一个缓存因数的servlet
 * 用map缓存多个因数
 * @author Administrator
 *
 */
public class TestCacheServlet3 {
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
		private Map<BigInteger, BigInteger[]> cache = new HashMap<BigInteger, BigInteger[]>();
		
		public void service(BigInteger i) {
			System.out.println(Thread.currentThread().toString() + "---->" + i);
			BigInteger[] factors = cache.get(i);
			if (factors == null) {
				factors = factor(i);
				// 如果返回null，则说明新增
				// 返回BigInger数组说明覆盖了，覆盖则说明发生不好的事情
				// TODO 复合性操作只能用同步处理吗？
				System.out.println(cache.put(i, factors));
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