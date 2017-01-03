package jun.learn.scene.softChain.kernel;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

public class ReqResult {
	private boolean success;
	private boolean committed;
	private JSONObject attach = new JSONObject();
	
	public boolean isSuccess() {
		return this.success;
	}
	
	public void success() {
		this.committed = true;
		this.success = true;
	}
	
	public void failed(String msg) {
		this.attach("resDesc", msg);
		this.committed = true;
		this.success = false;
	}
	
	public ReqResult attach(String key, Object value) {
		attach.put(key, value);
		return this;
	}
	
	public void each(Carrier carrier) {
		for (String key : attach.keySet())
			carrier.put(key, attach.get(key));
	}
	
	public void doResponse(HttpServletResponse response) {
		PrintWriter out;
		try {
			out = response.getWriter();
			response.setContentType("text/json");
			out.print(attach.toJSONString());
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean hasCommit() {
		return committed == true;
	}
}