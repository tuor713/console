package org.uwh.console.internal;

import org.osgi.framework.*;
import org.mortbay.jetty.*;
import org.mortbay.jetty.webapp.*;

public class Activator implements BundleActivator {
	private Server server;
	private static BundleContext __ctx;
	
	public static BundleContext getBundleContext() {
		return __ctx;
	}
	
	public void start(BundleContext ctx) {
		__ctx = ctx;
		server = new Server(8080);
		try {
			WebAppContext context = new WebAppContext("jar:" + ctx.getBundle().getLocation() + "!/", "/console");
			WebAppClassLoader cl = new WebAppClassLoader(Activator.class.getClassLoader(), context);
			context.setClassLoader(cl);
			context.setParentLoaderPriority(true);

			server.setHandler(context);

			server.start();
			
		} catch (Exception e) {
			server = null;
			throw new RuntimeException(e);
		}
	}
	
	public void stop(BundleContext ctx) {
		__ctx = null;
		if (server != null) {
			try {
				server.stop(); 
			} catch (Exception e) {
				throw new RuntimeException(e);
			} finally {
				server = null;
			}
		}
	}
}