package jun.learn.scene.j2eefilter.version2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Filter {
	void execute(HttpServletRequest request, HttpServletResponse response);
}
