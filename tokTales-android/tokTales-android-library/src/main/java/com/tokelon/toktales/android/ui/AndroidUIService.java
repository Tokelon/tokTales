package com.tokelon.toktales.android.ui;

import javax.inject.Inject;

import com.tokelon.toktales.core.engine.AbstractEngineService;

public class AndroidUIService extends AbstractEngineService implements IAndroidUIService {

	
	private final IUserInterface userInterface;

	@Inject
	public AndroidUIService(IUserInterface userInterface) {
		this.userInterface = userInterface;
	}
	
	
	@Override
	public IUserInterface getUserInterface() {
		return userInterface;
	}
	
}
