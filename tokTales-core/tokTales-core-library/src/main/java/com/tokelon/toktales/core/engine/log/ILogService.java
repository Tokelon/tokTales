package com.tokelon.toktales.core.engine.log;

import com.tokelon.toktales.core.engine.IEngineService;

public interface ILogService extends IEngineService {

	public void d(String tag, String message);
	
	public void e(String tag, String message);

	public void w(String tag, String message);
	
	public void i(String tag, String message);

}
