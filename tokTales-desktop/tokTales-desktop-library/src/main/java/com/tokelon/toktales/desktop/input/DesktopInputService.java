package com.tokelon.toktales.desktop.input;

import java.util.Set;

import com.tokelon.toktales.core.engine.AbstractEngineService;
import com.tokelon.toktales.desktop.input.IDesktopInputDriver.InputCharCallback;
import com.tokelon.toktales.desktop.input.IDesktopInputDriver.InputCursorMoveCallback;
import com.tokelon.toktales.desktop.input.IDesktopInputDriver.InputKeyCallback;
import com.tokelon.toktales.desktop.input.IDesktopInputDriver.InputMouseButtonCallback;
import com.tokelon.toktales.desktop.input.IDesktopInputRegistration.ICharInputCallback;
import com.tokelon.toktales.desktop.input.IDesktopInputRegistration.ICursorMoveCallback;
import com.tokelon.toktales.desktop.input.IDesktopInputRegistration.IKeyInputCallback;
import com.tokelon.toktales.desktop.input.IDesktopInputRegistration.IMouseButtonCallback;

public class DesktopInputService extends AbstractEngineService implements IDesktopInputService {

	private final MasterMouseButtonCallback masterMouseButtonCallback = new MasterMouseButtonCallback();
	private final MasterCursorMoveCallback masterCursorMoveCallback = new MasterCursorMoveCallback();
	private final MasterKeyCallback masterKeyCallback = new MasterKeyCallback();
	private final MasterCharCallback masterCharCallback = new MasterCharCallback();
	
	
	private final DesktopInputPoster inputPoster;
	private final DesktopInputDispatcher inputDispatcher;
	
	private IDesktopInputDriver inputDriver;

	public DesktopInputService() {
		inputPoster = new DesktopInputPoster();
		inputDispatcher = new DesktopInputDispatcher();
	}
	

	
	@Override
	public IDesktopInputPoster getInputPoster() {
		return inputPoster;
	}

	@Override
	public IDesktopInputDispatcher getInputDispatcher() {
		return inputDispatcher;
	}

	@Override
	public void setInputDriver(IDesktopInputDriver inputDriver) {
		this.inputDriver = inputDriver;

		inputDriver.setMouseButtonCallback(masterMouseButtonCallback);
		inputDriver.setCursorMoveCallback(masterCursorMoveCallback);
		inputDriver.setKeyCallback(masterKeyCallback);
		inputDriver.setCharCallback(masterCharCallback);
	}

	
	
	
	// Consume event if one callback has it handled? | invoke() return value
	
	private class MasterMouseButtonCallback implements InputMouseButtonCallback {

		@Override
		public void invoke(int vb, int action, int mods) {
			
			Set<IMouseButtonCallback> mouseButtonCallbackSet = inputDispatcher.getMouseButtonCallbackSet();
			synchronized (mouseButtonCallbackSet) {
				
				for(IMouseButtonCallback callback: mouseButtonCallbackSet) {
					callback.invokeMouseButton(vb, action);
				}
			}
		}
	}
	
	private class MasterCursorMoveCallback implements InputCursorMoveCallback {

		@Override
		public void invoke(double xpos, double ypos) {

			Set<ICursorMoveCallback> cursorMoveCallbackSet = inputDispatcher.getCursorMoveCallbackSet();
			synchronized (cursorMoveCallbackSet) {
				
				for(ICursorMoveCallback callback: cursorMoveCallbackSet) {
					callback.invokeCursorMove(xpos, ypos);
				}
			}
		}
	}
	
	private class MasterKeyCallback implements InputKeyCallback {

		@Override
		public void invoke(int vk, int action, int scancode, int mods) {
			
			Set<IKeyInputCallback> keyInputCallbackSet = inputDispatcher.getKeyInputCallbackSet();
			synchronized (keyInputCallbackSet) {
				
				for(IKeyInputCallback callback: keyInputCallbackSet) {
					callback.invokeKeyInput(vk, action);
				}
			}
		}
	}
	
	private class MasterCharCallback implements InputCharCallback {
		
		@Override
		public void invoke(int codepoint) {
			
			Set<ICharInputCallback> charInputCallbackSet = inputDispatcher.getCharInputCallbackSet();
			synchronized (charInputCallbackSet) {
				
				for(ICharInputCallback callback: charInputCallbackSet) {
					callback.invokeCharInput(codepoint);
				}
			}
		}
	}
	
	
	
	
	private class DesktopInputPoster implements IDesktopInputPoster {

		@Override
		public InputMouseButtonCallback getMouseInput() {
			return masterMouseButtonCallback;
		}

		@Override
		public InputCursorMoveCallback getCursorInput() {
			return masterCursorMoveCallback;
		}

		@Override
		public InputKeyCallback getKeyInput() {
			return masterKeyCallback;
		}

		@Override
		public InputCharCallback getCharInput() {
			return masterCharCallback;
		}
	}
	

	private class DesktopInputDispatcher extends DesktopInputRegistration implements IDesktopInputDispatcher {

		@Override
		public int getKeyState(int vk) {
			return inputDriver.getKeyState(vk);
		}

		@Override
		public boolean isKeyPressed(int vk) {
			return inputDriver.getKeyState(vk) == TInput.KEY_PRESS;
		}
	}


}
