package jun.learn.scene.j2eefilter.version2;

import java.util.Iterator;
import java.util.Vector;

public class FilterChain {
  // filter chain 
  private Vector myFilters = new Vector();

  // Creates new FilterChain 
  public FilterChain()  {
    // plug-in default filter services for example 
    // only. This would typically be done in the 
    // FilterManager, but is done here for example 
    // purposes
    addFilter(new DebugFilter());
    addFilter(new LoginFilter());
    addFilter(new AuditFilter());
  }

  public void processFilter( 
    javax.servlet.http.HttpServletRequest request,
    javax.servlet.http.HttpServletResponse response)
  throws javax.servlet.ServletException, 
    java.io.IOException         {
    Filter filter;

    // apply filters
    Iterator filters = myFilters.iterator();
    while (filters.hasNext())
    {
      filter = (Filter)filters.next();
      // pass request & response through various 
      // filters
      filter.execute(request, response);
    }
  }

  public void addFilter(Filter filter)  {
    myFilters.add(filter);
  }
}