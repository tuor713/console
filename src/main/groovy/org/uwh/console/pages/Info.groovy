package org.uwh.console.pages;

import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.ioc.annotations.*;
import org.osgi.framework.*;
import org.osgi.service.packageadmin.*

@IncludeJavaScriptLibrary('${tapestry.scriptaculous}/scriptaculous.js')
public class Info {
	@Inject
	private BundleContext ctx
	
	@Inject
	private PackageAdmin packageAdmin
	
	@Property
	private Bundle bundle
	
	@Property
	private String state
	
	@Property
	private ServiceReference registeredService
	
	@Property
	private ServiceReference usedService
	
	@Property
	private Bundle clientBundle
	
	@Property
	private String serviceClass
	
	@Property
	private Map<String,String> headers
	
	@Property
	private Map.Entry<String,String> entry
	
	@Property
	private List<ExportedPackage> imports
	
	@Property
	private List<ExportedPackage> exports
	
	@Property
	private ExportedPackage exportedPackage
	
	void onActivate(int bundleId) {
		bundle = ctx.getBundle(bundleId)
		if (bundle) {
			state = Info.state(bundle)
			generateHeaders()
			generatePackages()
		}
	}
	
	private generateHeaders() {
		headers = new HashMap<String,String>()
		bundle.headers.keys().each { e->
			headers.put(e, bundle.headers.get(e))
		}
	}
	
	private generatePackages() {
		imports = []
		exports = packageAdmin.getExportedPackages(bundle)?.flatten()?.sort { it.name }
		
		ctx.bundles.each { b ->
			packageAdmin.getExportedPackages(b).each { p->
				p.importingBundles.each { ib ->
					if (ib == bundle)
						imports << p
				}
			}
		}
		
		imports = imports.sort { it.name }
	}

	def serviceId(serviceRef) {
		return serviceRef.getProperty(Constants.SERVICE_ID)
	}
	
	def serviceClasses(serviceRef) {
		return serviceRef.getProperty(Constants.OBJECTCLASS).flatten().sort()
	}
	
	public static state(bundle) {
		def res = [
			(Bundle.UNINSTALLED) : "UNINSTALLED", (Bundle.INSTALLED) : "INSTALLED", 
		 	(Bundle.RESOLVED) : "RESOLVED", (Bundle.ACTIVE) : "ACTIVE", 
			(Bundle.STARTING) : "STARTING", (Bundle.STOPPING) : "STOPPING"
		][bundle.state]
		
		return res
	}
}
