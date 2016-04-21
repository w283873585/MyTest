package test;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class Atest {
	public static class H<T> {
		T t;
		public H(T t) {
			this.t = t;
			if (t instanceof String) {
				System.out.println("-->" + t);	
			}
			if (String.class.isInstance(t)) {
				System.out.println(t);
			}
		}
	}
	public static void main(String[] args) throws MalformedURLException, IOException {
		System.out.println(URLEncoder.encode("-1", "UTF-8"));
		H<String> h = new H("123");
		HttpURLConnection conn = (HttpURLConnection) new URL("HTTP", "www.vrshow.com", 80, "/").openConnection();
		System.out.println(conn.getInputStream());
		out(conn.getInputStream());
	}
	
	public static void out(InputStream a) {
		byte[] b = new byte[1024];
		int i = 0;
		try {
			while (i != -1) {
				i = a.read(b);
				System.out.println(new String(b));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
