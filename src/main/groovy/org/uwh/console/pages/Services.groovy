package org.uwh.console.pages;

import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.ioc.annotations.*;
import org.osgi.framework.*

public class Services {
	@Inject
	private BundleContext ctx
	
	@Property
	private List<ServiceReference> services
	
	@Property
	private ServiceReference service
	
	@Property 
	private String serviceClass
	
	@Persist
	@Property
	private String filterString
	
	void onActivate() {
		if (!filterString)
			filterString = ""
		
		services = []
		ctx.bundles.each { b->
			b.registeredServices.each { s->
				if (serviceClasses(s).any { it =~ filterString }) {
					services << s
				}
			}
		}
		
		services = services.sort { serviceId(it) }
	}
	
	def serviceId(serviceRef) {
		return serviceRef.getProperty(Constants.SERVICE_ID)
	}
	
	def serviceClasses(serviceRef) {
		return serviceRef.getProperty(Constants.OBJECTCLASS).flatten().sort()
	}
}
