package com.tokelon.toktales.desktop.main;

import java.util.Map;

import javax.inject.Inject;

import com.tokelon.toktales.core.engine.AbstractEngineService;
import com.tokelon.toktales.core.engine.inject.annotation.services.LogServiceExtensions;
import com.tokelon.toktales.core.engine.log.ILogService;

public class DesktopLogService extends AbstractEngineService implements ILogService {


	public DesktopLogService() {
		super();
	}
	
	@Inject
	public DesktopLogService(@LogServiceExtensions Map<String, IServiceExtension> extensions) {
		super(extensions);
	}
	
	
	@Override
	public void d(String tag, String message) {
		System.out.printf("d | %s : %s", tag, message);
		System.out.println();
	}

	@Override
	public void e(String tag, String message) {
		System.err.printf("e | %s : %s", tag, message);
		System.err.println();
	}

	@Override
	public void w(String tag, String message) {
		System.out.printf("w | %s : %s", tag, message);
		System.out.println();
	}

	@Override
	public void i(String tag, String message) {
		System.out.printf("i | %s : %s", tag, message);
		System.out.println();
	}
	
}
