package org.uwh.console.pages;

import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.ioc.annotations.*;
import org.osgi.framework.*;

public class Bundles {
	@Inject
	private BundleContext ctx
	
	@Property
	private Bundle bundle
	
	@Persist("flash")
	@Property
	private String[] lastError
	
	@Property
	private String stackElement
	
	Bundle[] getBundles() {
		return ctx.getBundles();
	}
	
	def state(bundle) {
		return Info.state(bundle)
	}

	def onActionFromStart(int bundleId) {
		def bundle = ctx.getBundle(bundleId)
		catchError { bundle?.start() }
		
		return null
	}
	
	def onActionFromstop(int bundleId) {
		def bundle = ctx.getBundle(bundleId)
		catchError { bundle?.stop() }
		
		return null
	}
	
	private catchError(block) {
		try {
			block.call()
		} catch (Exception e) {
			def out = new StringWriter()
			e.printStackTrace(new PrintWriter(out))
			lastError = out.toString().split("\n")
		}
	}
}