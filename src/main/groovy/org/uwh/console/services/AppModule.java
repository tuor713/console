package org.uwh.console.services;

import org.osgi.framework.*;
import org.osgi.service.packageadmin.*;
import org.osgi.util.tracker.*;
import org.uwh.console.internal.*;
import org.apache.tapestry5.services.*;
import org.apache.tapestry5.ioc.*;

public class AppModule {
	public static BundleContext buildBundleContext() {
		return Activator.getBundleContext();
	}
	
	private static ServiceTracker packageAdminTracker = null;
	
	public static PackageAdmin buildPackageAdmin() {
		if (packageAdminTracker == null) {
			packageAdminTracker = new ServiceTracker(buildBundleContext(), PackageAdmin.class.getName(), null);
			packageAdminTracker.open();
		}
		
		return (PackageAdmin) packageAdminTracker.getService();
	}
}