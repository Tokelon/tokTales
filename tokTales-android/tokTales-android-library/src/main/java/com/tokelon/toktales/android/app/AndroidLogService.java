package com.tokelon.toktales.android.app;

import java.util.Map;

import javax.inject.Inject;

import com.tokelon.toktales.core.engine.AbstractEngineService;
import com.tokelon.toktales.core.engine.inject.annotation.services.LogServiceExtensions;
import com.tokelon.toktales.core.engine.log.ILogService;

import android.util.Log;

public class AndroidLogService extends AbstractEngineService implements ILogService {


	public AndroidLogService() {
		super();
	}
	
	@Inject
	public AndroidLogService(@LogServiceExtensions Map<String, IServiceExtension> extensions) {
		super(extensions);
	}
	

	@Override
	public void d(String tag, String message) {
		Log.d(tag, message);
	}

	@Override
	public void e(String tag, String message) {
		Log.e(tag, message);
	}

	@Override
	public void w(String tag, String message) {
		Log.w(tag, message);
	}

	@Override
	public void i(String tag, String message) {
		Log.i(tag, message);
	}

}
