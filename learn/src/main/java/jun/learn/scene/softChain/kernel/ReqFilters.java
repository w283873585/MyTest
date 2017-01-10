package jun.learn.scene.softChain.kernel;

import com.alibaba.fastjson.JSONObject;

import jun.learn.scene.softChain.security.VR_Security_Util;

public enum ReqFilters implements ReqFilter{
	decrypt {
		public void filter(MetaData metaData, ReqData reqData, ReqResult result) {
			if (result.hasCommit())
				return;
			
			if (metaData.reqEncrypt())
				VR_Security_Util.decode(reqData, result);
		}
	},
	
	adapt {
		public void filter(MetaData metaData, ReqData reqData, ReqResult result) {
			if (result.hasCommit())
				return;
			
			if (!metaData.reqEncrypt()) {
				final JSONObject data = new JSONObject();
				reqData.each(new Carrier() {
					@Override
					public void put(String key, Object val) {
						data.put(key, val);
					}
				});
				reqData.clear();
				reqData.put("paramsMap", data);
			}
		}
	},
	
	check {
		public void filter(MetaData metaData, ReqData reqData, ReqResult result) {
			if (result.hasCommit())
				return;
			
			metaData.checkParams(reqData.getJSONObject("paramsMap"), result);
		}
	};
}
