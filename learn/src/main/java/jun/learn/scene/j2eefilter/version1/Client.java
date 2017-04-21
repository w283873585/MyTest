package jun.learn.scene.j2eefilter.version1;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class Client {
	public static void processRequest(ServletRequest req, ServletResponse res) throws IOException, ServletException {
		Processor processor = new Builder().build("expression or xml");
		processor.execute(req, res);

		// Then dispatch to next resource, which is probably
		// the View to display
		dispatch(req, res);
	}

	private static void dispatch(ServletRequest req, ServletResponse res) {}

	public static void main(String[] args) {
		ServletRequest req = null;
		ServletResponse res = null;

		try {
			// 处理请求
			processRequest(req, res);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
}
