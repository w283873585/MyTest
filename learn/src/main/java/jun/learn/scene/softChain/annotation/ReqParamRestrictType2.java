package jun.learn.scene.softChain.annotation;

import com.alibaba.fastjson.JSONArray;

import jun.learn.scene.softChain.annotation.Chain.Node;
import jun.learn.scene.softChain.annotation.Chain.Result;

public enum ReqParamRestrictType2 implements Node{
	
	/**
	 * 参数可为空
	 */
	optional(null) {
		
		@Override
		protected boolean expect() {
			return true;
		}

		@Override
		public boolean isPass(Object c) {
			return "".equals(c) || c == null;
		}
	}, 
	
	
	/**
	 * 不能为空
	 */
	notEmpty("不能为空") {
		@Override
		public boolean isPass(Object c) {
			return "".equals(c) || c == null;
		}
	},
	
	
	/**
	 * 必须为列表
	 */
	list("不是列表", notEmpty) {

		@Override
		public boolean isPass(Object c) {
			try {
				JSONArray.parseArray(c.toString());
				return true;
			} catch (Exception e) {
				return false;
			}
		}
	};
	
	ReqParamRestrictType2(String info) {
		this(info, null);
	}
	
	ReqParamRestrictType2(String info, ReqParamRestrictType2 parent) {
		this.info = info;
		this.parent = parent;
	}
	
	protected String info;
	private ReqParamRestrictType2 parent;
	
	public abstract boolean isPass(Object c);
	
	protected boolean expect() {
		return false;
	}
	
	public Result exec(Object target, Chain chain) {
		Result r = this.check(target);
		if (r.isSuccess() == expect())	
			return r;
		return chain.proceed();
	}
	
	private Result check(Object c) {
		if (parent != null) {
			Result result = parent.check(c);
			if (!result.isSuccess())
				return result;
		}
		boolean isPass = this.isPass(c);
		return new Result(isPass, isPass ? null : info);
	}
}
