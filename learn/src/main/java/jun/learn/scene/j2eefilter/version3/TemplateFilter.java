package jun.learn.scene.j2eefilter.version3;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public abstract class TemplateFilter implements javax.servlet.Filter {
	private FilterConfig filterConfig;

	public void setFilterConfig(FilterConfig fc) {
		filterConfig = fc;
	}

	public FilterConfig getFilterConfig() {
		return filterConfig;
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// Common processing for all filters can go here
		doPreProcessing(request, response, chain);

		// Common processing for all filters can go here
		doMainProcessing(request, response, chain);

		// Common processing for all filters can go here
		doPostProcessing(request, response, chain);

		// Common processing for all filters can go here

		// Pass control to the next filter in the chain or
		// to the target resource
		chain.doFilter(request, response);
	}

	public void doPreProcessing(ServletRequest request, ServletResponse response, FilterChain chain) {
	}

	public void doPostProcessing(ServletRequest request, ServletResponse response, FilterChain chain) {
	}

	public abstract void doMainProcessing(ServletRequest request, ServletResponse response, FilterChain chain);
}