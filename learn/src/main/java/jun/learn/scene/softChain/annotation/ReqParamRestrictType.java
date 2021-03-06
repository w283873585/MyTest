package jun.learn.scene.softChain.annotation;

import com.alibaba.fastjson.JSONArray;

import jun.learn.scene.softChain.annotation.ReqParamVerifyChain.Node;
import jun.learn.scene.softChain.annotation.ReqParamVerifyChain.Result;

public enum ReqParamRestrictType implements Node{
	
	/**
	 * 参数可为空
	 */
	optional(null) {
		
		@Override
		protected boolean expect(Result result) {
			return result.isSuccess() == true;
		}

		@Override
		public boolean match(Object c) {
			return "".equals(c) || c == null;
		}
	}, 
	
	/**
	 * 不能为空
	 */
	noEmpty("参数不能为空") {
		@Override
		public boolean match(Object c) {
			return c != null && !"".equals(c);
		}
	},
	
	/**
	 * 必须为rsa加密, extends NoEmpty
	 */
	rsa("RSA解密失败", noEmpty) {

		@Override
		public boolean match(Object c) {
			return true;
		}
		
		public Result exec(Object target, ReqParamVerifyChain chain) {
			Result r = this.check(target);
			if (expect(r))
				return r;
			
			try {
				// rsa解密
				String newTarget = target.toString();
				chain.setTarget(newTarget);
			} catch (Exception e) {
				return new Result(false, info);
			}
			
			return chain.proceed();
		}
	},
	
	/**
	 * 必须为纯数字, extends NoEmpty
	 */
	number("必须为数字", noEmpty){
		@Override
		public boolean match(Object c) {
			return c.toString().matches("[0-9]+");
		}
	},
	
	/**
	 * 长度必须为6, extends NoEmpty
	 */
	length6("长度必须为6", noEmpty){
		@Override
		public boolean match(Object c) {
			return c.toString().length() == 6;
		}
	},
	
	/**
	 * 长度必须为16, extends NoEmpty
	 */
	length16("长度必须为16", noEmpty){
		@Override
		public boolean match(Object c) {
			return c.toString().length() == 16;
		}
	},
	
	/**
	 * 必须为列表
	 */
	list("不是列表", noEmpty) {

		@Override
		public boolean match(Object c) {
			try {
				JSONArray.parseArray(c.toString());
				return true;
			} catch (Exception e) {
				return false;
			}
		}
	},
	
	ipAddress("必须为ip地址", noEmpty) {
		@Override
		public boolean match(Object c) {
			return c.toString().matches("((?:(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))))");
		}
	};
	
	ReqParamRestrictType(String info) {
		this(info, null);
	}
	
	ReqParamRestrictType(String info, ReqParamRestrictType parent) {
		this.info = info;
		this.parent = parent;
	}
	
	protected String info;
	protected ReqParamRestrictType parent;
	
	public abstract boolean match(Object c);
	
	protected boolean expect(Result result) {
		return result.isSuccess() == false;
	}
	
	public Result exec(Object target, ReqParamVerifyChain chain) {
		Result result = this.check(target);
		if (!expect(result))
			chain.proceed();
		return result;
	}
	
	protected Result check(Object c) {
		if (parent != null) {
			Result result = parent.check(c);
			if (expect(result))
				return result;
		}
		boolean matched = this.match(c);
		return new Result(matched, matched ? null : info);
	}
}