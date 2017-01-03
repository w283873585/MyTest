package jun.learn.scene.softChain.kernel;

import java.util.Map;
import com.alibaba.fastjson.JSONObject;

public class ReqData extends JSONObject{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3480911415943541472L;

	public ReqData(Map<String, Object> data) {
		this.putAll(data);
	}
	public void each(Carrier c) {
		for (String key : this.keySet())
			c.put(key, this.get(key));
	}
}
