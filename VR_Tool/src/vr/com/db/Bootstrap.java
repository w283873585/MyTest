package vr.com.db;


import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppClassLoader;
import org.eclipse.jetty.webapp.WebAppContext;

public class Bootstrap{
	
	public static void main(String[] args) throws Exception {
		System.setProperty("file.encoding", "utf-8");  
		int port = args.length > 1 ? Integer.parseInt(args[1]) : 8989;
		Server server = new Server(port);
		server.setAttribute("CharacterEncoding", "utf-8");
		WebAppContext webAppContext = new WebAppContext("WebRoot", "/");
		webAppContext.setContextPath("/VR_Tool");
		webAppContext.setDescriptor("WebRoot/WEB-INF/web.xml");
		webAppContext.setResourceBase("WebRoot");
		webAppContext.setDisplayName("VR_Tool");
		webAppContext.setClassLoader(new WebAppClassLoader(Bootstrap.class.getClassLoader(), webAppContext));
		webAppContext.setConfigurationDiscovered(true);
		webAppContext.setParentLoaderPriority(true);
		server.setHandler(webAppContext);
		server.start();
	}
}
