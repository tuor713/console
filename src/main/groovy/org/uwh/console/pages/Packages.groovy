package org.uwh.console.pages;

import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.ioc.annotations.*;
import org.osgi.framework.*
import org.osgi.service.packageadmin.*

public class Packages {
	@Property
	private ExportedPackage pack
	
	@Property
	private List<ExportedPackage> packages
	
	@Inject
	private BundleContext ctx
	
	@Inject
	private PackageAdmin packageAdmin
	
	@Persist
	@Property
	private String filterString
	
	void onActivate() {
		if (!filterString)
			filterString = ""
		
		packages = populate(filterString)
	}
	
	// simply redirect to the same page
	def onSuccess() {
		return null;
	}
	
	private populate(regex) {
		def packs = []
		
		ctx.bundles.each { b->
			packageAdmin.getExportedPackages(b).each { p->
				if (p.name =~ regex) {
					packs << p
				}
			}
		}
		
		return packs.sort { it.name }
	}
}