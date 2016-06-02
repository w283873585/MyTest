package vr.com.db;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Bootstrap2{
	private static final String PATH = Bootstrap2.class.getClassLoader().getResource("").getPath(); 
	
	public static void main(String[] args) throws Exception {
		String path = args.length > 0 ? args[0] : PATH;
		int port = args.length > 1 ? Integer.parseInt(args[1]) : 8888;
		
		Server server = new Server(port);
        ServletContextHandler context = new ServletContextHandler(
                ServletContextHandler.SESSIONS);
        context.setContextPath("/VR_Tool");
        context.setResourceBase(path + "/../WebRoot/");
        
        ServletHolder holder = new ServletHolder();
        holder.setClassName("org.springframework.web.servlet.DispatcherServlet");
        holder.setInitParameter("contextConfigLocation", "file:" + path + "spring-mvc.xml");
        context.addServlet(holder, "/*");
        
        server.setHandler(context);
        server.start();
        server.join();
	}
}
