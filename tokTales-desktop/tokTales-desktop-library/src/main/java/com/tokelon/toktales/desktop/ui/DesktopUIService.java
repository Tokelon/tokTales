package com.tokelon.toktales.desktop.ui;

import java.util.Map;

import javax.inject.Inject;

import com.tokelon.toktales.core.engine.AbstractEngineService;
import com.tokelon.toktales.core.engine.inject.annotation.services.UIServiceExtensions;
import com.tokelon.toktales.core.engine.ui.IUIService;

public class DesktopUIService extends AbstractEngineService implements IUIService {


	public DesktopUIService() {
		super();
	}
	
	@Inject
	public DesktopUIService(@UIServiceExtensions Map<String, IServiceExtension> extensions) {
		super(extensions);
	}
	
}
