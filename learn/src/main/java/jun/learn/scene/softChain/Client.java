package jun.learn.scene.softChain;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jun.learn.scene.softChain.kernel.Carrier;
import jun.learn.scene.softChain.kernel.ReqResult;
import jun.learn.scene.softChain.kernel.RequestManager;

public class Client {
	@SuppressWarnings("null")
	public static void main(String[] args) {
		Method handler = null;
		final HttpServletRequest request = null;
		final HttpServletResponse response = null;
		
		ReqResult result = RequestManager.invoke(request, handler);
		if (!result.isSuccess()) {
			result.doResponse(response);
			return;
		}
		
		result.each(new Carrier() {
			@Override
			public void put(String key, Object val) {
				request.setAttribute(key, val);
			}
		});
		
		/**
			Object handler = null;
			final HttpServletRequest request = null;
			final HttpServletResponse response = null;
			
			ReqChain chain = ReqChain.build(request, handler);
			Result result = chain.exec(data);
			if (!result.isSuccess()) {
				result.doResponse(response);
				return false;
			}
			
			result.eachAttr(new Carrier() {
				public void invoke(String key, Object value) {
					request.setAttribute(key, value);
				}
			});
		*/
		/**
		 * 

			功能描述
			
				解析请求
				
					验证请求参数
					
					失败响应
					
					成功转发
				
		 */
	}
}
