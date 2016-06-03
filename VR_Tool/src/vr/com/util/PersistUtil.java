package vr.com.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class PersistUtil {
	private static final String CHARSET = "UTF-8";
	private static final String TARGETDIR = "C:/Users/xnxs/git/MyTest/VR_Tool/config/";
	
	private static void persist(String filename, String key, String val){
		FileOutputStream fos = null;
		FileChannel channel = null;
		try {
			fos = new FileOutputStream(TARGETDIR + filename, true);
			channel = fos.getChannel();
			byte[] content = ("\n" + key + "=" + val).getBytes(CHARSET);
			ByteBuffer buff = ByteBuffer.wrap(content);
			channel.write(buff);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (channel != null)
					channel.close();
				if (fos != null)
					fos.close();
			} catch (IOException e) {}
		}
	}
	
	public static void cacheCommand(String key, String val){
		persist("commandCache.properties", key, val);
	}
	
	public static void cacheRequest(String key, String val){
		persist("request_cache.properties", key, val);
	}
}
