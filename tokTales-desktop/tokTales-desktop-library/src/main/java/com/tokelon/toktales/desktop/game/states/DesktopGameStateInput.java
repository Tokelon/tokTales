package com.tokelon.toktales.desktop.game.states;

import java.util.Set;

import com.tokelon.toktales.desktop.input.DesktopInputRegistration;
import com.tokelon.toktales.desktop.input.IDesktopInputRegistration.ICharInputCallback;
import com.tokelon.toktales.desktop.input.IDesktopInputRegistration.ICursorMoveCallback;
import com.tokelon.toktales.desktop.input.IDesktopInputRegistration.IKeyInputCallback;
import com.tokelon.toktales.desktop.input.IDesktopInputRegistration.IMouseButtonCallback;

public class DesktopGameStateInput extends DesktopInputRegistration implements IDesktopGameStateInput, IMouseButtonCallback, ICursorMoveCallback, IKeyInputCallback, ICharInputCallback {


	@Override
	public boolean invokeMouseButton(int vb, int action) {
		boolean wasHandled = false;
		
		Set<IMouseButtonCallback> mouseButtonCallbackSet = getMouseButtonCallbackSet();
		synchronized (mouseButtonCallbackSet) {
			for(IMouseButtonCallback callback: mouseButtonCallbackSet) {
				wasHandled = callback.invokeMouseButton(vb, action) || wasHandled;
			}
		}
		
		return wasHandled;
	}

	@Override
	public boolean invokeCursorMove(double xpos, double ypos) {
		boolean wasHandled = false;
		
		Set<ICursorMoveCallback> cursorMoveCallbackSet = getCursorMoveCallbackSet();
		synchronized (cursorMoveCallbackSet) {
			for(ICursorMoveCallback callback: cursorMoveCallbackSet) {
				wasHandled = callback.invokeCursorMove(xpos, ypos) || wasHandled;
			}
		}
		
		return wasHandled;
	}
	
	@Override
	public boolean invokeKeyInput(int vk, int action) {
		boolean wasHandled = false;
		
		Set<IKeyInputCallback> keyInputCallbackSet = getKeyInputCallbackSet();
		synchronized (keyInputCallbackSet) {
			for(IKeyInputCallback callback: keyInputCallbackSet) {
				wasHandled = callback.invokeKeyInput(vk, action) || wasHandled;
			}
		}
		
		return wasHandled;
	}

	@Override
	public boolean invokeCharInput(int codepoint) {
		boolean wasHandled = false;
		
		Set<ICharInputCallback> charInputCallbackSet = getCharInputCallbackSet();
		synchronized (charInputCallbackSet) {
			for(ICharInputCallback callback: charInputCallbackSet) {
				wasHandled = callback.invokeCharInput(codepoint) || wasHandled;
			}
		}
		
		return wasHandled;
	}

	
	
	@Override
	public IMouseButtonCallback getMasterMouseButtonCallback() {
		return this;
	}

	@Override
	public ICursorMoveCallback getMasterCursorMoveCallback() {
		return this;
	}

	@Override
	public IKeyInputCallback getMasterKeyInputCallback() {
		return this;
	}

	@Override
	public ICharInputCallback getMasterCharInputCallback() {
		return this;
	}

	
}
