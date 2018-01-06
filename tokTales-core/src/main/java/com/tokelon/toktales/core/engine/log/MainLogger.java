package com.tokelon.toktales.core.engine.log;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;


public class MainLogger implements ILogger {

	public static final String DEFAULT_TAG = "Log";

	
	private final Set<String> logOnceSet;
	
	private final ILogService logService;
	
	@Inject
	public MainLogger(ILogService logService) {
		this.logService = logService;
		
		logOnceSet = Collections.synchronizedSet(new HashSet<String>());
	}
	
	
	@Override
	public void d(String message) {
		logService.d(DEFAULT_TAG, message);
	}
	@Override
	public void d(String tag, String message) {
		logService.d(tag, message);
	}


	@Override
	public void e(String message) {
		logService.e(DEFAULT_TAG, message);
	}
	@Override
	public void e(String tag, String message) {
		logService.e(tag, message);
	}


	@Override
	public void w(String message) {
		logService.w(DEFAULT_TAG, message);
	}
	@Override
	public void w(String tag, String message) {
		logService.w(tag, message);
	}


	@Override
	public void i(String message) {
		logService.i(DEFAULT_TAG, message);
	}
	@Override
	public void i(String tag, String message) {
		logService.i(tag, message);
	}

	
	@Override
	public void logOnce(char loglevel, String id, String tag, String message) {
		if(!logOnceSet.contains(id)) {
			logOnceSet.add(id);
			System.out.printf("%c | %s : %s", loglevel, tag, message);
			System.out.println();	
		}
	}
	
	
}
