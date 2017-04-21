package jun.learn.scene.j2eefilter.version1;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public interface Processor {
	
	void execute(ServletRequest req, ServletResponse resp) throws IOException, ServletException;
}
