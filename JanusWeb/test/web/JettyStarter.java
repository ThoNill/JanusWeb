package web;

import java.io.File;


import janus.tech.web.session.JanusServlet;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlet.DefaultServlet;


public class JettyStarter{
	
	 public static void main( String[] args ) throws Exception
	    {
	        Server server = new Server(8082);
	        ServletHandler handler = new ServletHandler();
	        
	        server.setHandler(handler);
	        
	        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
	        
	        addDefaultServlet(handler,contextHandler);
        
	        addJanusServlet(handler,contextHandler);
	        
	        server.setHandler(contextHandler);
	        
	       
	        
	        server.start();
	        server.dumpStdErr();
	        server.join();
	    }

	protected static void addJanusServlet(ServletHandler handler,ServletContextHandler contextHandler) {
		ServletHolder atomHopServer=new ServletHolder(JanusServlet.class);
		atomHopServer.setInitParameter("appname","testapp");
		atomHopServer.setInitParameter("pagelist","data TablePage");
		contextHandler.addServlet(atomHopServer, "/test");
		handler.addServlet(atomHopServer);
	}
	
	protected static void addDefaultServlet(ServletHandler handler,ServletContextHandler contextHandler) {
		ServletHolder atomHopServer=new ServletHolder(DefaultServlet.class);
		atomHopServer.setInitParameter("dirAllowed","true");
		atomHopServer.setInitParameter("resourceBase",new File("").getAbsolutePath());
		contextHandler.addServlet(atomHopServer, "/*");
		handler.addServlet(atomHopServer);
	}
}