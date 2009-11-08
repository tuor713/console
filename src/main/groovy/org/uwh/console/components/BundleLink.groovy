package org.uwh.console.components;

import org.osgi.framework.*
import org.apache.tapestry5.*
import org.apache.tapestry5.annotations.*

public class BundleLink {
	@Property
	@Parameter(required=true)
	private Bundle bundle
}