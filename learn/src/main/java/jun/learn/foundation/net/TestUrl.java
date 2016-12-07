package jun.learn.foundation.net;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class TestUrl {
	public static void main(String[] args) throws IOException {
		URL url = new URL("http://localhost:8080/VR_Show");
		URLConnection connection = url.openConnection();
		connection.connect();
		InputStream in = connection.getInputStream();
		int i;
		byte[] buf = new byte[1024];
		while ((i = in.read(buf)) != -1) {
			System.out.println(new String(buf));
		}
		in.close();
		
		/**
		 * java.net.URL
		 * URL(Uniform Resource Locator)基于www的唯一资源定位
		 * 包含一些基本属性, 如 host, path, query, protocol等等.
		 * 且能够通过getOutputStream, getContent获取远程资源内容
		 * 获取内容过程是有个中间层的, 使用URLStreamHandler.openConnection()创建URLConnection,(URLStreamHandler提供创建各种协议连接的策略)
		 * URLConnection抽象了连接, 可以设置一些相关属性, 然后进行连接通信
		 */
	}
}
