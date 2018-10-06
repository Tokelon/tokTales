package com.tokelon.toktales.core.render;

import com.tokelon.toktales.core.util.IParams;

public interface IRenderDriverFactory {

	
	public boolean supports(String target);
	
	public IRenderDriver newDriver(IParams params);
	
}
