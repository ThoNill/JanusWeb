package web;

import janus.tech.web.session.JanusServlet;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;


public class JettyStarter{
	
	 public static void main( String[] args ) throws Exception
	    {
	        Server server = new Server(8080);
	        
	        
	  
	 /*
	         HashSessionIdManager idmanager = new HashSessionIdManager();
	        server.setSessionIdManager(idmanager);
	  */

	        ServletHandler handler = new ServletHandler();
	        server.setHandler(handler);
	        
	        
        
	        ServletHolder atomHopServer=new ServletHolder(JanusServlet.class);
	        atomHopServer.setInitParameter("appname","testapp");
	        atomHopServer.setInitParameter("pagelist","data");
	        
	        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
	        contextHandler.addServlet(atomHopServer, "/test");
	        
	  /*      HashSessionManager manager = new HashSessionManager();
	        SessionHandler sessions = new SessionHandler(manager);
	        contextHandler.setHandler(sessions);*/
	        
	        server.setHandler(contextHandler);
	        
	        server.start();
	        server.dumpStdErr();
	        server.join();
	    }
}