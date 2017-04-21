package jun.learn.scene.j2eefilter.version1;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class CoreProcessor implements Processor {
  @SuppressWarnings("unused")
  private Processor target;
  
  public CoreProcessor()   {
    this(null);
  }

  public CoreProcessor(Processor myTarget)   {
    target = myTarget;
  }

  public void execute(ServletRequest req, 
      ServletResponse res) throws IOException, 
      ServletException   {
    //Do core processing here
  }
}