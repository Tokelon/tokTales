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

public interface IGLFWInputRegistration {


	public void registerErrorCallback(GLFWErrorCallbackI callback);

	public void registerMonitorCallback(GLFWMonitorCallbackI callback);

	public void registerWindowPosCallback(long window, GLFWWindowPosCallbackI callback);
	public void registerWindowSizeCallback(long window, GLFWWindowSizeCallbackI callback);
	public void registerWindowCloseCallback(long window, GLFWWindowCloseCallbackI callback);
	public void registerWindowRefreshCallback(long window, GLFWWindowRefreshCallbackI callback);
	public void registerWindowFocusCallback(long window, GLFWWindowFocusCallbackI callback);
	public void registerWindowIconifyCallback(long window, GLFWWindowIconifyCallbackI callback);
	public void registerWindowMaximizeCallback(long window, GLFWWindowMaximizeCallbackI callback);
	public void registerWindowContentScaleCallback(long window, GLFWWindowContentScaleCallbackI callback);

	public void registerFramebufferSizeCallback(long window, GLFWFramebufferSizeCallbackI callback);

	public void registerKeyCallback(long window, GLFWKeyCallbackI callback);
	public void registerCharCallback(long window, GLFWCharCallbackI callback);
	public void registerCharModsCallback(long window, GLFWCharModsCallbackI callback);
	public void registerMouseButtonCallback(long window, GLFWMouseButtonCallbackI callback);
	public void registerCursorPosCallback(long window, GLFWCursorPosCallbackI callback);
	public void registerCursorEnterCallback(long window, GLFWCursorEnterCallbackI callback);
	public void registerScrollCallback(long window, GLFWScrollCallbackI callback);
	public void registerDropCallback(long window, GLFWDropCallbackI callback);

	public void registerJoystickCallback(GLFWJoystickCallbackI callback);


	public boolean unregisterErrorCallback(GLFWErrorCallbackI callback);

	public boolean unregisterMonitorCallback(GLFWMonitorCallbackI callback);

	public boolean unregisterWindowPosCallback(long window, GLFWWindowPosCallbackI callback);
	public boolean unregisterWindowSizeCallback(long window, GLFWWindowSizeCallbackI callback);
	public boolean unregisterWindowCloseCallback(long window, GLFWWindowCloseCallbackI callback);
	public boolean unregisterWindowRefreshCallback(long window, GLFWWindowRefreshCallbackI callback);
	public boolean unregisterWindowFocusCallback(long window, GLFWWindowFocusCallbackI callback);
	public boolean unregisterWindowIconifyCallback(long window, GLFWWindowIconifyCallbackI callback);
	public boolean unregisterWindowMaximizeCallback(long window, GLFWWindowMaximizeCallbackI callback);
	public boolean unregisterWindowContentScaleCallback(long window, GLFWWindowContentScaleCallbackI callback);

	public boolean unregisterFramebufferSizeCallback(long window, GLFWFramebufferSizeCallbackI callback);

	public boolean unregisterKeyCallback(long window, GLFWKeyCallbackI callback);
	public boolean unregisterCharCallback(long window, GLFWCharCallbackI callback);
	public boolean unregisterCharModsCallback(long window, GLFWCharModsCallbackI callback);
	public boolean unregisterMouseButtonCallback(long window, GLFWMouseButtonCallbackI callback);
	public boolean unregisterCursorPosCallback(long window, GLFWCursorPosCallbackI callback);
	public boolean unregisterCursorEnterCallback(long window, GLFWCursorEnterCallbackI callback);
	public boolean unregisterScrollCallback(long window, GLFWScrollCallbackI callback);
	public boolean unregisterDropCallback(long window, GLFWDropCallbackI callback);

	public boolean unregisterJoystickCallback(GLFWJoystickCallbackI callback);


	public boolean hasErrorCallback(GLFWErrorCallbackI callback);

	public boolean hasMonitorCallback(GLFWMonitorCallbackI callback);

	public boolean hasWindowPosCallback(long window, GLFWWindowPosCallbackI callback);
	public boolean hasWindowSizeCallback(long window, GLFWWindowSizeCallbackI callback);
	public boolean hasWindowCloseCallback(long window, GLFWWindowCloseCallbackI callback);
	public boolean hasWindowRefreshCallback(long window, GLFWWindowRefreshCallbackI callback);
	public boolean hasWindowFocusCallback(long window, GLFWWindowFocusCallbackI callback);
	public boolean hasWindowIconifyCallback(long window, GLFWWindowIconifyCallbackI callback);
	public boolean hasWindowMaximizeCallback(long window, GLFWWindowMaximizeCallbackI callback);
	public boolean hasWindowContentScaleCallback(long window, GLFWWindowContentScaleCallbackI callback);

	public boolean hasFramebufferSizeCallback(long window, GLFWFramebufferSizeCallbackI callback);

	public boolean hasKeyCallback(long window, GLFWKeyCallbackI callback);
	public boolean hasCharCallback(long window, GLFWCharCallbackI callback);
	public boolean hasCharModsCallback(long window, GLFWCharModsCallbackI callback);
	public boolean hasMouseButtonCallback(long window, GLFWMouseButtonCallbackI callback);
	public boolean hasCursorPosCallback(long window, GLFWCursorPosCallbackI callback);
	public boolean hasCursorEnterCallback(long window, GLFWCursorEnterCallbackI callback);
	public boolean hasScrollCallback(long window, GLFWScrollCallbackI callback);
	public boolean hasDropCallback(long window, GLFWDropCallbackI callback);

	public boolean hasJoystickCallback(GLFWJoystickCallbackI callback);

}
