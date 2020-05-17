package com.tokelon.toktales.desktop.lwjgl.input;

import org.lwjgl.glfw.GLFWCharCallbackI;
import org.lwjgl.glfw.GLFWCharModsCallbackI;
import org.lwjgl.glfw.GLFWCursorEnterCallbackI;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;
import org.lwjgl.glfw.GLFWDropCallbackI;
import org.lwjgl.glfw.GLFWErrorCallbackI;
import org.lwjgl.glfw.GLFWFramebufferSizeCallbackI;
import org.lwjgl.glfw.GLFWJoystickCallbackI;
import org.lwjgl.glfw.GLFWKeyCallbackI;
import org.lwjgl.glfw.GLFWMonitorCallbackI;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;
import org.lwjgl.glfw.GLFWScrollCallbackI;
import org.lwjgl.glfw.GLFWWindowCloseCallbackI;
import org.lwjgl.glfw.GLFWWindowContentScaleCallbackI;
import org.lwjgl.glfw.GLFWWindowFocusCallbackI;
import org.lwjgl.glfw.GLFWWindowIconifyCallbackI;
import org.lwjgl.glfw.GLFWWindowMaximizeCallbackI;
import org.lwjgl.glfw.GLFWWindowPosCallbackI;
import org.lwjgl.glfw.GLFWWindowRefreshCallbackI;
import org.lwjgl.glfw.GLFWWindowSizeCallbackI;

import com.tokelon.toktales.desktop.lwjgl.ILWJGLInputDispatch;

public interface IGLFWInputConsumer extends IGLFWInputRegistration {


	public void registerCallbacks();

	public void unregisterCallbacks();

	public void registerWindowCallbacks(long window);

	public void unregisterWindowCallbacks(long window);


	public GLFWErrorCallbackI getMasterErrorCallback();

	public GLFWMonitorCallbackI getMasterMonitorCallback();

	public GLFWWindowPosCallbackI getMasterWindowPosCallback();
	public GLFWWindowSizeCallbackI getMasterWindowSizeCallback();
	public GLFWWindowCloseCallbackI getMasterWindowCloseCallback();
	public GLFWWindowRefreshCallbackI getMasterWindowRefreshCallback();
	public GLFWWindowFocusCallbackI getMasterWindowFocusCallback();
	public GLFWWindowIconifyCallbackI getMasterWindowIconifyCallback();
	public GLFWWindowMaximizeCallbackI getMasterWindowMaximizeCallback();
	public GLFWWindowContentScaleCallbackI getMasterWindowContentScaleCallback();

	public GLFWFramebufferSizeCallbackI getMasterFramebufferSizeCallback();

	public GLFWKeyCallbackI getMasterKeyCallback();
	public GLFWCharCallbackI getMasterCharCallback();
	public GLFWCharModsCallbackI getMasterCharModsCallback();
	public GLFWMouseButtonCallbackI getMasterMouseButtonCallback();
	public GLFWCursorPosCallbackI getMasterCursorPosCallback();
	public GLFWCursorEnterCallbackI getMasterCursorEnterCallback();
	public GLFWScrollCallbackI getMasterScrollCallback();
	public GLFWDropCallbackI getMasterDropCallback();
	public GLFWJoystickCallbackI getMasterJoystickCallback();


	public interface IGLFWInputConsumerFactory {

		public IGLFWInputConsumer create(ILWJGLInputDispatch dispatch);
	}

}
