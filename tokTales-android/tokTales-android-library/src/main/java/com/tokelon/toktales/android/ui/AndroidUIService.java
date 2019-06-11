package com.tokelon.toktales.android.ui;

import java.util.Map;

import javax.inject.Inject;

import com.tokelon.toktales.core.engine.AbstractEngineService;
import com.tokelon.toktales.core.engine.inject.annotation.services.UIServiceExtensions;

public class AndroidUIService extends AbstractEngineService implements IAndroidUIService {

	
	private final IUserInterface userInterface;

	public AndroidUIService(IUserInterface userInterface) {
		this.userInterface = userInterface;
	}
	
	@Inject
	public AndroidUIService(IUserInterface userInterface, @UIServiceExtensions Map<String, IServiceExtension> extensions) {
		super(extensions);
		
		this.userInterface = userInterface;
	}
	
	
	@Override
	public IUserInterface getUserInterface() {
		return userInterface;
	}
	
}
