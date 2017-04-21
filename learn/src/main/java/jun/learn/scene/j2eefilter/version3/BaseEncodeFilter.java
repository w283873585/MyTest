package jun.learn.scene.j2eefilter.version3;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

public class BaseEncodeFilter implements javax.servlet.Filter {
	private javax.servlet.FilterConfig myFilterConfig;

	public BaseEncodeFilter() {
	}

	public void doFilter(javax.servlet.ServletRequest servletRequest, javax.servlet.ServletResponse servletResponse,
			javax.servlet.FilterChain filterChain) throws java.io.IOException, javax.servlet.ServletException {
		filterChain.doFilter(servletRequest, servletResponse);
	}

	public javax.servlet.FilterConfig getFilterConfig() {
		return myFilterConfig;
	}

	public void setFilterConfig(javax.servlet.FilterConfig filterConfig) {
		myFilterConfig = filterConfig;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}
}