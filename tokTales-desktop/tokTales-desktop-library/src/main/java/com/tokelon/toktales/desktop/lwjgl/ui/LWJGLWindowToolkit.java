package com.tokelon.toktales.desktop.lwjgl.ui;

import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFW;

import com.tokelon.toktales.desktop.ui.window.IWindowToolkit;

/** LWJGL implementation of {@link IWindowToolkit}.
 */
public class LWJGLWindowToolkit implements IWindowToolkit {


	@Override
	public long getPrimaryMonitor() {
		return GLFW.glfwGetPrimaryMonitor();
	}

	@Override
	public long[] getMonitors() {
		PointerBuffer monitorsBuffer = GLFW.glfwGetMonitors();

		long[] monitorsArray = new long[monitorsBuffer.capacity()];
		monitorsBuffer.get(monitorsArray);

		return monitorsArray;
	}

	@Override
	public void setWindowHint(int hint, int value) {
		GLFW.glfwWindowHint(hint, value);
	}

	@Override
	public void setWindowHintString(int hint, String value) {
		GLFW.glfwWindowHintString(hint, value);
	}

	@Override
	public void setDefaultWindowHints() {
		GLFW.glfwDefaultWindowHints();
	}

}
