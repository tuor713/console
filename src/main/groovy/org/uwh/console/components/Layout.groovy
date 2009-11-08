package org.uwh.console.components;

import org.apache.tapestry5.*;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.ioc.annotations.*;
import org.apache.tapestry5.BindingConstants;

@IncludeStylesheet(
	value = ["context:css/site.css"]
)
public class Layout {
  @Property
  @Parameter(required = true, defaultPrefix = 'literal')
  private String title;
}