package jun.learn.scene.j2eefilter.version1;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class AuthenticationFilter implements Processor {

	 private Processor target;

	 public AuthenticationFilter(Processor myTarget) {
	    target = myTarget;
	 }

	 public void execute(ServletRequest req, 
	  ServletResponse res) throws IOException, 
	    ServletException    {
	    // Do some filter processing here, such as 
	    // displaying request parameters
	    target.execute(req, res);
	 }
}
