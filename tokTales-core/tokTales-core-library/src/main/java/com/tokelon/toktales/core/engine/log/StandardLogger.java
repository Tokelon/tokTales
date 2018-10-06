package com.tokelon.toktales.core.engine.log;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class StandardLogger implements ILogger {

	public static final String DEFAULT_TAG = "Log";


	private final Set<String> logOnceSet;

	public StandardLogger() {
		logOnceSet = Collections.synchronizedSet(new HashSet<String>());
	}
	
	
	@Override
	public void d(String message) {
		d(DEFAULT_TAG, message);
	}
	
	@Override
	public void d(String tag, String message) {
		System.out.printf("d | %s : %s", tag, message);
		System.out.println();
	}

	
	@Override
	public void e(String message) {
		e(DEFAULT_TAG, message);
	}
	
	@Override
	public void e(String tag, String message) {
		System.err.printf("e | %s : %s", tag, message);
		System.err.println();
	}
	
	
	@Override
	public void w(String message) {
		w(DEFAULT_TAG, message);
	}

	@Override
	public void w(String tag, String message) {
		System.out.printf("w | %s : %s", tag, message);
		System.out.println();
	}


	@Override
	public void i(String message) {
		i(DEFAULT_TAG, message);
	}
	
	@Override
	public void i(String tag, String message) {
		System.out.printf("i | %s : %s", tag, message);
		System.out.println();
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
