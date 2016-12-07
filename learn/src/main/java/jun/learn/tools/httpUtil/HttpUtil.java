package jun.learn.tools.httpUtil;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HttpUtil {

	public static String httpPost(String url, Map<String, String> params) {
		MyResponse result = request(url, params, false, true);
		return result.toString();
	}

	public static MyResponse request(String requestUrl,
			Map<String, String> params, boolean isGet, boolean useTLS) {
		byte[] data = mapToParams(params);
		return request(requestUrl, isGet, data, useTLS);
	}

	public static MyResponse request(String requestUrl, boolean isGet,
			byte[] data, boolean useTLS) {
		return request(requestUrl, isGet, data, useTLS, null);
	}
	
	private static byte[] mapToParams(Map<String, String> params) {
		StringBuffer sb = new StringBuffer();
		if (params != null) {
			try {
				for (Entry<String, String> e : params.entrySet()) {
					sb.append(e.getKey());
					sb.append("=");
					sb.append(URLEncoder.encode(e.getValue(), "UTF-8"));
					sb.append("&");
				}
				sb.deleteCharAt(sb.length() - 1);
				// U.alert("params=%s", sb);
				byte[] data = sb.toString().getBytes("UTF-8");
				return data;
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
		}
		return null;
	}

	public static MyResponse request(String requestUrl, boolean isGet,
			byte[] data, boolean useTLS, Map<String, String> header) {
		MyResponse result = new MyResponse();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		try {
			if (isGet && data != null && data.length > 0) {
				if (requestUrl.indexOf('?') > 0) {
					requestUrl += '&';
				} else {
					requestUrl += '?';
				}
				requestUrl += new String(data, "UTF-8");
			}
			URL url = new URL(requestUrl);
			HttpURLConnection httpUrlConn = (HttpURLConnection) url
					.openConnection();
			if (httpUrlConn instanceof HttpsURLConnection) {
				System.setProperty("jsse.enableSNIExtension", "false");
				// 创建SSLContext对象，并使用我们指定的信任管理器初始化
				TrustManager[] tm = { new MyX509TrustManager() };
				SSLContext sslContext;
				if (useTLS == false) {
					sslContext = SSLContext.getInstance("SSL", "SunJSSE");
					sslContext.init(null, tm, new SecureRandom());
				} else {
					sslContext = SSLContext.getInstance("TLS");
					sslContext.init(null, tm, null);
				}
				// 从上述SSLContext对象中得到SSLSocketFactory对象
				SSLSocketFactory ssf = sslContext.getSocketFactory();
				((HttpsURLConnection) httpUrlConn).setSSLSocketFactory(ssf);
			}
			boolean truePost = !isGet && data != null && data.length > 0;
			httpUrlConn.setDoOutput(truePost);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(isGet ? "GET" : "POST");
			httpUrlConn.setRequestProperty("User-Agent",
					"Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			if (header != null) {
				Set<Entry<String, String>> set = header.entrySet();
				for (Entry<String, String> entry : set) {
					httpUrlConn.setRequestProperty(entry.getKey(),
							entry.getValue());
				}
			}
			if (isGet) {
				httpUrlConn.connect();
			} else if (truePost) {
				// 提交数据
				OutputStream outputStream = httpUrlConn.getOutputStream();
				outputStream.write(data);
				outputStream.close();
			}
			result.code = httpUrlConn.getResponseCode();
			result.header = httpUrlConn.getHeaderFields();
			if (result.code == 200) {
				// 读取返回数据
				InputStream inputStream = httpUrlConn.getInputStream();
				byte[] buf = new byte[1024 * 2];
				int len;
				while ((len = inputStream.read(buf)) != -1) {
					out.write(buf, 0, len);
				}
				// 释放资源
				inputStream.close();
				inputStream = null;
			}
			out.close();
			httpUrlConn.disconnect();
			result.data = out.toByteArray();
		} catch (Exception e) {
			result.e = e;
		}
		return result;
	}
	
	// 响应类
	public static class MyResponse {
		public int code;
		public byte[] data;
		public Map<String, List<String>> header;
		public Exception e;

		private String s;

		public String getString() {
			if (s != null) {
				return s;
			}
			if (code == 200 && data != null && data.length > 0) {
				try {
					this.s = new String(data, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			return s;
		}

		public String getAll() {
			StringBuilder sb = new StringBuilder();
			sb.append(String.format("Code:%s\r\n", this.code));
			sb.append("Header:\r\n");
			sb.append(getHeaderString());
			sb.append("Body:\r\n");
			getString();
			sb.append(this.s == null ? "(null)" : s);
			return sb.toString();
		}

		public String getHeaderString() {
			if (header == null) {
				return "";
			}
			StringBuilder sb = new StringBuilder();
			Set<Entry<String, List<String>>> set = header.entrySet();
			StringBuilder sb2 = new StringBuilder();
			for (Entry<String, List<String>> item : set) {
				List<String> list = item.getValue();
				sb2.setLength(0);
				for (int i = 0; i < list.size(); i++) {
					sb2.append(list.get(i));
					if (i > 0) {
						sb2.append("\r\n");
					}
				}
				sb.append(String.format("%s=%s\r\n", item.getKey(), sb2));
			}
			return sb.toString();
		}

		@Override
		public String toString() {
			if (code == 200) {
				return getString();
			} else {
				return String.format("code=%s", code);
			}
		}
	}

	private static class MyX509TrustManager implements X509TrustManager {

		public void checkClientTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
		}

		public void checkServerTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
		}

		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}
	}
}
