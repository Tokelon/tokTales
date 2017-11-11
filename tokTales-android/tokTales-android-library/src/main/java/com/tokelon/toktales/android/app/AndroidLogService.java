package com.tokelon.toktales.android.app;

import com.tokelon.toktales.core.engine.AbstractEngineService;
import com.tokelon.toktales.core.engine.log.ILogService;

import android.util.Log;

public class AndroidLogService extends AbstractEngineService implements ILogService {

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
