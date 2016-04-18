package qiniu;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.qiniu.util.Auth;

public class QiniuUtil {
	private static final String FILENAME = "qiniu.properties";
	private static final String ACCESS_KEY = "ACCESS_KEY";
	private static final String SECRET_KEY = "SECRET_KEY";
	private static Properties propertis = new Properties();
	private static Auth auth;
	private static final String domain = "";
	private static final ConcurrentHashMap<String, String> tokenCache = new ConcurrentHashMap<>();
	static {
		try {
			propertis.load(QiniuUtil.class.getClassLoader().getResourceAsStream(FILENAME));
			auth = Auth.create(propertis.getProperty(ACCESS_KEY), propertis.getProperty(SECRET_KEY));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static String getToken(String bucket) {
		String result = tokenCache.get(bucket);
		/*
		if (result == null) {
			synchronized (QiniuUtil.class) {
				if (tokenCache.get(bucket) == null) {
					tokenCache.put(bucket, auth.uploadToken(bucket));
				}
			}
			result = tokenCache.get(bucket);
		}
		*/
		/**
			如果为空，则放入一个新的，
			多线程时可能会同时放入两个新的，
			putIfAbsent则可以处理这种情况，因为他是串行操作，且返回的必定是第一个放入的，或者返回null（返回null则说明，此次放入就是第一次放入）
		*/
		if (result == null) {
			String token = auth.uploadToken(bucket);
			result = tokenCache.putIfAbsent(bucket, token);
			if (result == null) {
				result = token;
			}
		}
		return result;
	}
	
	public static String getPrivateUrl(String bucket, String key) {
		return auth.privateDownloadUrl(domain + bucket + key, 3600);
	}
	
	public static void main(String[] args) {
		ExecutorService exec = Executors.newCachedThreadPool();
		for (int i = 0; i < 1000; i++) {
			final int j = i;
			exec.execute(new Runnable() {
				public void run() {
					try {
						Thread.sleep(10);
						getToken("123" + (j % 10));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
		}
		exec.shutdown();
	}
}