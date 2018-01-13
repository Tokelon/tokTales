package com.tokelon.toktales.extens.def.desktop.game.states;

import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.desktop.game.states.DesktopGameStateInput;
import com.tokelon.toktales.desktop.input.IDesktopInputRegistration.IKeyInputCallback;
import com.tokelon.toktales.extens.def.core.game.states.ChunkTestingGamestate;

public class DesktopChunkTestingGamestate extends ChunkTestingGamestate {

	private DesktopGameStateInput stateInput;
	
	public DesktopChunkTestingGamestate(IEngineContext context) {
		super(context);
	}
	
	
	@Override
	public void onEngage() {
		super.onEngage();
		
		stateInput = new DesktopGameStateInput();
		
		stateInput.registerKeyInputCallback(new IKeyInputCallback() {
			
			private int keyCount = 0;
			
			@Override
			public boolean invokeKeyInput(int vk, int action) {
				if(keyCount++ >= 2) {
					keyCount = 0;
					getGame().getStateControl().changeState("console_state");
					
					return true;
				}
				else {
					return false;
				}
			}
		});
		
		
		setStateInput(stateInput);
	}

}
