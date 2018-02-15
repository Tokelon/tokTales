package com.tokelon.toktales.test.core.game;

import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.game.IGameAdapter;

public class DummyGameAdapter implements IGameAdapter {
	
	@Override
	public void onCreate(IEngineContext engineContext) { }

	@Override
	public void onDestroy() { }

	@Override
	public void onStart() {	}
	
	@Override
	public void onResume() { }

	@Override
	public void onPause() { }

	@Override
	public void onStop() { }
	
	@Override
	public void onUpdate() { }
	
	@Override
	public void onRender() { }
	
}