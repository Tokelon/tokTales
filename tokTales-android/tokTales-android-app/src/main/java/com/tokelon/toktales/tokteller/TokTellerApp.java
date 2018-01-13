package com.tokelon.toktales.tokteller;

import com.tokelon.toktales.android.app.TokTalesApp;
import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineLauncher;

/**
 * Created by Tokelon on 04/10/2017.
 */

public class TokTellerApp extends TokTalesApp {

	@Override
	protected void launchEngine(IEngineLauncher defaultLauncher) throws EngineException {
		TokTellerAdapter adapter = new TokTellerAdapter();
		defaultLauncher.launch(adapter);
	}
	
}
