package com.tokelon.toktales.desktop.lwjgl.input;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCharCallback;
import org.lwjgl.glfw.GLFWCursorEnterCallback;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

import com.tokelon.toktales.core.engine.TokTales;
import com.tokelon.toktales.desktop.input.IDesktopInputDriver;
import com.tokelon.toktales.desktop.lwjgl.LWJGLWindow;

public class GLFWInputDriver implements IDesktopInputDriver {
	
	public static final String TAG = "GLFWInputDriver";
	
	
	private final DriverMouseButtonCallback glfwButtonCallback;
	private final DriverCursorEnterCallback glfwCursorEnterCallback;
	private final DriverCursorPosCallback glfwCursorPosCallback;
	private final DriverKeyCallback glfwKeyCallback;
	private final DriverCharCallback glfwCharCallback;
	
	private volatile boolean cursorInWindow = false;
	
	
	
	private InputMouseButtonCallback inputMouseButtonCallback;
	private InputCursorMoveCallback inputCursorMoveCallback;
	private InputKeyCallback inputKeyCallback;
	private InputCharCallback inputCharCallback;
	
	private final LWJGLWindow mWindow;
	private final long mWindowHandle;
	
	private boolean ignoreCursorOutsideOfWindow = true;
	
	
	public GLFWInputDriver(LWJGLWindow window) {
		this.mWindow = window;
		this.mWindowHandle = window.getID();
		
		
		GLFW.glfwSetMouseButtonCallback(mWindowHandle, glfwButtonCallback = new DriverMouseButtonCallback());
		GLFW.glfwSetCursorEnterCallback(mWindowHandle, glfwCursorEnterCallback = new DriverCursorEnterCallback());
		GLFW.glfwSetCursorPosCallback(mWindowHandle, glfwCursorPosCallback = new DriverCursorPosCallback());
		
		GLFW.glfwSetKeyCallback(mWindowHandle, glfwKeyCallback = new DriverKeyCallback());
		GLFW.glfwSetCharCallback(mWindowHandle, glfwCharCallback = new DriverCharCallback());
	}

	
	public void setIgnoreCursorOutsideOfWindow(boolean ignore) {
		this.ignoreCursorOutsideOfWindow = ignore;
	}
	
	
	
	@Override
	public int getKeyState(int vk) {
		int glfwKey = GLFWInput.keyVkToGlfw(vk);
		int glfwState = GLFW.glfwGetKey(mWindowHandle, glfwKey);
		
		return GLFWInput.stateGlfwToInput(glfwState);
	}
	
	
	
	
	
	
	
	private class DriverMouseButtonCallback extends GLFWMouseButtonCallback {

		@Override
		public void invoke(long window, int button, int action, int mods) {
			if(ignoreCursorOutsideOfWindow && !cursorInWindow) {
				TokTales.getLog().d(TAG, String.format("IGNORED: Mouse action=%d for Button %d with mods=%d", action, button, mods));
				
				// Ignore actions where the cursor is outside the window
				return;
			}
			TokTales.getLog().d(TAG, String.format("Mouse action=%d for Button %d with mods=%d", action, button, mods));
			
			
			int vb = GLFWInput.keyGlfwToVk(button);
			int vbAction = GLFWInput.stateGlfwToInput(action);
			int vbMods = GLFWInput.modGlfwToInput(mods);
			
			if(inputMouseButtonCallback != null) {
				inputMouseButtonCallback.invoke(vb, vbAction, vbMods);
			}
		}
	}
	
	private class DriverCursorEnterCallback extends GLFWCursorEnterCallback {

		@Override
		public void invoke(long window, int entered) {
			//TokTales.getLog().d(TAG, String.format("Cursor: %s", entered == GLFW.GLFW_TRUE ? "entered" : "left"));
			
			cursorInWindow = entered == GLFW.GLFW_TRUE;
		}
	}
	
	private class DriverCursorPosCallback extends GLFWCursorPosCallback {

		@Override
		public void invoke(long window, double xpos, double ypos) {
			if(ignoreCursorOutsideOfWindow && !cursorInWindow) {
				//TokTales.getLog().d(TAG, String.format("IGNORED: Cursor move to (x=%.2f, y=%.2f)", xpos, ypos));
				
				// Ignore actions where the cursor is outside the window
				return;
			}
			//TokTales.getLog().d(TAG, String.format("Cursor move to (x=%.2f, y=%.2f)", xpos, ypos));
			
			
			if(inputCursorMoveCallback != null) {
				inputCursorMoveCallback.invoke(xpos, ypos);
			}
		}
	}
	
	
	private class DriverKeyCallback extends GLFWKeyCallback {

		@Override
		public void invoke(long window, int key, int scancode, int action, int mods) {
			//TokTales.getLog().d(TAG, String.format("Key action=%d for key=%d with scancode=%d, mods=%d", action, key, scancode, mods));
			
			
			int vk = GLFWInput.keyGlfwToVk(key);
			int vkAction = GLFWInput.stateGlfwToInput(action);
			int vbMods = GLFWInput.modGlfwToInput(mods);
			int vbScanCode = scancode; // scancodeGlfwToInput not needed because there is no known scancodes
			
			if(inputKeyCallback != null) {
				inputKeyCallback.invoke(vk, vkAction, vbScanCode, vbMods);
			}
		}
	}
	
	
	private class DriverCharCallback extends GLFWCharCallback {

		@Override
		public void invoke(long window, int codepoint) {
			TokTales.getLog().d(TAG, String.format("Char action for codepoint: %s", new String(Character.toChars(codepoint))));
			
			if(inputCharCallback != null) {
				inputCharCallback.invoke(codepoint);
			}
		}
	}


	
	@Override
	public void setMouseButtonCallback(InputMouseButtonCallback callback) {
		this.inputMouseButtonCallback = callback;
	}


	@Override
	public void setCursorMoveCallback(InputCursorMoveCallback callback) {
		this.inputCursorMoveCallback = callback;
	}


	@Override
	public void setKeyCallback(InputKeyCallback callback) {
		this.inputKeyCallback = callback;
	}

	@Override
	public void setCharCallback(InputCharCallback callback) {
		this.inputCharCallback = callback;
	}
	
	
}
