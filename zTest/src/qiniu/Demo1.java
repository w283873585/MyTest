package qiniu;

import java.io.IOException;
import java.util.Properties;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

public class Demo1 {
	private static final String FILENAME = "qiniu.properties";
	// 七牛权限对象
	private Auth auth = Auth.create(getProperty("ACCESS_KEY"), getProperty("SECRET_KEY"));
	
	public void upload(){
		//创建上传对象
		UploadManager uploadManager = new UploadManager();
		try {
			Response res = uploadManager.put("C:/Users/xnxs/Desktop/tianxia.txt", "tianxia.txt", auth.uploadToken("junq"));
			System.out.println(res.bodyString());
			
		} catch (QiniuException e) {
			System.out.println(e.response.toString());
			try {
	            //响应的文本信息
	            System.out.println(e.response.bodyString());
	        } catch (QiniuException e1) { /*ignore*/ }
		}
	}
	
	
	private String getProperty(String key) {
		Properties propertis = new Properties();
		try {
			propertis.load(this.getClass().getClassLoader().getResourceAsStream(FILENAME));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return propertis.getProperty(key);
	}
	
	public static void main(String[] args) {
		new Demo1().upload();
	}
}