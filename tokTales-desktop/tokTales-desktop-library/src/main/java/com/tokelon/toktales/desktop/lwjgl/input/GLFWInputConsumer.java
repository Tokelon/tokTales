package com.tokelon.toktales.desktop.lwjgl.input;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.lwjgl.glfw.GLFW;
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

import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.log.ILogging;
import com.tokelon.toktales.desktop.lwjgl.ILWJGLInputDispatch;

public class GLFWInputConsumer extends GLFWInputRegistration implements IGLFWInputConsumer {


	private final GLFWCharCallbackI masterCharCallback;
	private final GLFWCharModsCallbackI masterCharModsCallback;
	private final GLFWCursorEnterCallbackI masterCursorEnterCallback;
	private final GLFWCursorPosCallbackI masterCursorPosCallback;
	private final GLFWDropCallbackI masterDropCallback;
	private final GLFWErrorCallbackI masterErrorCallback;
	private final GLFWFramebufferSizeCallbackI masterFramebufferSizeCallback;
	private final GLFWJoystickCallbackI masterJoystickCallback;
	private final GLFWKeyCallbackI masterKeyCallback;
	private final GLFWMonitorCallbackI masterMonitorCallback;
	private final GLFWMouseButtonCallbackI masterMouseButtonCallback;
	private final GLFWScrollCallbackI masterScrollCallback;
	private final GLFWWindowCloseCallbackI masterWindowCloseCallback;
	private final GLFWWindowContentScaleCallbackI masterWindowContentScaleCallback;
	private final GLFWWindowFocusCallbackI masterWindowFocusCallback;
	private final GLFWWindowIconifyCallbackI masterWindowIconifyCallback;
	private final GLFWWindowMaximizeCallbackI masterWindowMaximizeCallback;
	private final GLFWWindowPosCallbackI masterWindowPosCallback;
	private final GLFWWindowRefreshCallbackI masterWindowRefreshCallback;
	private final GLFWWindowSizeCallbackI masterWindowSizeCallback;

	private final ILogger logger;

	public GLFWInputConsumer(ILogging logging) {
		this.logger = logging.getLogger(getClass());

		// Unfortunately all this is a bit verbose to keep object creation to zero during the main loop
		this.masterErrorCallback = (error, description) -> {
			for(int i = 0; i < getErrorCallbacks().size(); i++) {
				getErrorCallbacks().get(i).invoke(error, description);
			}
		};

		this.masterMonitorCallback = (monitor, event) -> {
			for(int i = 0; i < getMonitorCallbacks().size(); i++) {
				getMonitorCallbacks().get(i).invoke(monitor, event);
			}
		};

		this.masterJoystickCallback = (jid, event) -> {
			for(int i = 0; i < getJoystickCallbacks().size(); i++) {
				getJoystickCallbacks().get(i).invoke(jid, event);
			}
		};

		this.masterCharCallback = (window, codepoint) -> {
			List<GLFWCharCallbackI> callbacks = getCharCallbacks().get(window);
			for(int i = 0; callbacks != null && i < callbacks.size(); i++) {
				callbacks.get(i).invoke(window, codepoint);
			}
		};
		this.masterCharModsCallback = (window, codepoint, mods) -> {
			List<GLFWCharModsCallbackI> callbacks = getCharModsCallbacks().get(window);
			for(int i = 0; callbacks != null && i < callbacks.size(); i++) {
				callbacks.get(i).invoke(window, codepoint, mods);
			}
		};
		this.masterCursorEnterCallback = (window, entered) -> {
			List<GLFWCursorEnterCallbackI> callbacks = getCursorEnterCallbacks().get(window);
			for(int i = 0; callbacks != null && i < callbacks.size(); i++) {
				callbacks.get(i).invoke(window, entered);
			}
		};
		this.masterCursorPosCallback = (window, xpos, ypos) -> {
			List<GLFWCursorPosCallbackI> callbacks = getCursorPosCallbacks().get(window);
			for(int i = 0; callbacks != null && i < callbacks.size(); i++) {
				callbacks.get(i).invoke(window, xpos, ypos);
			}
		};
		this.masterDropCallback = (window, count, names) -> {
			List<GLFWDropCallbackI> callbacks = getDropCallbacks().get(window);
			for(int i = 0; callbacks != null && i < callbacks.size(); i++) {
				callbacks.get(i).invoke(window, count, names);
			}
		};
		this.masterFramebufferSizeCallback = (window, width, height) -> {
			List<GLFWFramebufferSizeCallbackI> callbacks = getFramebufferSizeCallbacks().get(window);
			for(int i = 0; callbacks != null && i < callbacks.size(); i++) {
				callbacks.get(i).invoke(window, width, height);
			}
		};
		this.masterKeyCallback = (window, key, scancode, action, mods) -> {
			List<GLFWKeyCallbackI> callbacks = getKeyCallbacks().get(window);
			for(int i = 0; callbacks != null && i < callbacks.size(); i++) {
				callbacks.get(i).invoke(window, key, scancode, action, mods);
			}
		};
		this.masterMouseButtonCallback = (window, button, action, mods) -> {
			List<GLFWMouseButtonCallbackI> callbacks = getMouseButtonCallbacks().get(window);
			for(int i = 0; callbacks != null && i < callbacks.size(); i++) {
				callbacks.get(i).invoke(window, button, action, mods);
			}
		};
		this.masterScrollCallback = (window, xoffset, yoffset) -> {
			List<GLFWScrollCallbackI> callbacks = getScrollCallbacks().get(window);
			for(int i = 0; callbacks != null && i < callbacks.size(); i++) {
				callbacks.get(i).invoke(window, xoffset, yoffset);
			}
		};
		this.masterWindowCloseCallback = window -> {
			List<GLFWWindowCloseCallbackI> callbacks = getWindowCloseCallbacks().get(window);
			for(int i = 0; callbacks != null && i < callbacks.size(); i++) {
				callbacks.get(i).invoke(window);
			}
		};
		this.masterWindowContentScaleCallback = (window, xscale, yscale) -> {
			List<GLFWWindowContentScaleCallbackI> callbacks = getWindowContentScaleCallbacks().get(window);
			for(int i = 0; callbacks != null && i < callbacks.size(); i++) {
				callbacks.get(i).invoke(window, xscale, yscale);
			}
		};
		this.masterWindowFocusCallback = (window, focused) -> {
			List<GLFWWindowFocusCallbackI> callbacks = getWindowFocusCallbacks().get(window);
			for(int i = 0; callbacks != null && i < callbacks.size(); i++) {
				callbacks.get(i).invoke(window, focused);
			}
		};
		this.masterWindowIconifyCallback = (window, iconified) -> {
			List<GLFWWindowIconifyCallbackI> callbacks = getWindowIconifyCallbacks().get(window);
			for(int i = 0; callbacks != null && i < callbacks.size(); i++) {
				callbacks.get(i).invoke(window, iconified);
			}
		};
		this.masterWindowMaximizeCallback = (window, maximized) -> {
			List<GLFWWindowMaximizeCallbackI> callbacks = getWindowMaximizeCallbacks().get(window);
			for(int i = 0; callbacks != null && i < callbacks.size(); i++) {
				callbacks.get(i).invoke(window, maximized);
			}
		};
		this.masterWindowPosCallback = (window, xpos, ypos) -> {
			List<GLFWWindowPosCallbackI> callbacks = getWindowPosCallbacks().get(window);
			for(int i = 0; callbacks != null && i < callbacks.size(); i++) {
				callbacks.get(i).invoke(window, xpos, ypos);
			}
		};
		this.masterWindowRefreshCallback = window -> {
			List<GLFWWindowRefreshCallbackI> callbacks = getWindowRefreshCallbacks().get(window);
			for(int i = 0; callbacks != null && i < callbacks.size(); i++) {
				callbacks.get(i).invoke(window);
			}
		};
		this.masterWindowSizeCallback = (window, width, height) -> {
			List<GLFWWindowSizeCallbackI> callbacks = getWindowSizeCallbacks().get(window);
			for(int i = 0; callbacks != null && i < callbacks.size(); i++) {
				callbacks.get(i).invoke(window, width, height);
			}
		};
	}


	@Override
	public void registerCallbacks() {
		GLFW.glfwSetErrorCallback(masterErrorCallback);
		GLFW.glfwSetJoystickCallback(masterJoystickCallback);
		GLFW.glfwSetMonitorCallback(masterMonitorCallback);

		logger.debug("Callbacks were registered");
	}

	@Override
	public void unregisterCallbacks() {
		GLFW.glfwSetErrorCallback(null);
		GLFW.glfwSetJoystickCallback(null);
		GLFW.glfwSetMonitorCallback(null);

		logger.debug("Callbacks were unregistered");
	}

	@Override
	public void registerWindowCallbacks(long window) {
		this.getCharCallbacks().putIfAbsent(window, new ArrayList<>());
		this.getCharModsCallbacks().putIfAbsent(window, new ArrayList<>());
		this.getCursorEnterCallbacks().putIfAbsent(window, new ArrayList<>());
		this.getCursorPosCallbacks().putIfAbsent(window, new ArrayList<>());
		this.getDropCallbacks().putIfAbsent(window, new ArrayList<>());
		this.getFramebufferSizeCallbacks().putIfAbsent(window, new ArrayList<>());
		this.getKeyCallbacks().putIfAbsent(window, new ArrayList<>());
		this.getMouseButtonCallbacks().putIfAbsent(window, new ArrayList<>());
		this.getScrollCallbacks().putIfAbsent(window, new ArrayList<>());
		this.getWindowCloseCallbacks().putIfAbsent(window, new ArrayList<>());
		this.getWindowContentScaleCallbacks().putIfAbsent(window, new ArrayList<>());
		this.getWindowFocusCallbacks().putIfAbsent(window, new ArrayList<>());
		this.getWindowIconifyCallbacks().putIfAbsent(window, new ArrayList<>());
		this.getWindowMaximizeCallbacks().putIfAbsent(window, new ArrayList<>());
		this.getWindowPosCallbacks().putIfAbsent(window, new ArrayList<>());
		this.getWindowRefreshCallbacks().putIfAbsent(window, new ArrayList<>());
		this.getWindowSizeCallbacks().putIfAbsent(window, new ArrayList<>());

		GLFW.glfwSetCharCallback(window, masterCharCallback);
		GLFW.glfwSetCharModsCallback(window, masterCharModsCallback);
		GLFW.glfwSetCursorEnterCallback(window, masterCursorEnterCallback);
		GLFW.glfwSetCursorPosCallback(window, masterCursorPosCallback);
		GLFW.glfwSetDropCallback(window, masterDropCallback);
		GLFW.glfwSetFramebufferSizeCallback(window, masterFramebufferSizeCallback);
		GLFW.glfwSetKeyCallback(window, masterKeyCallback);
		GLFW.glfwSetMouseButtonCallback(window, masterMouseButtonCallback);
		GLFW.glfwSetScrollCallback(window, masterScrollCallback);
		GLFW.glfwSetWindowCloseCallback(window, masterWindowCloseCallback);
		GLFW.glfwSetWindowContentScaleCallback(window, masterWindowContentScaleCallback);
		GLFW.glfwSetWindowFocusCallback(window, masterWindowFocusCallback);
		GLFW.glfwSetWindowIconifyCallback(window, masterWindowIconifyCallback);
		GLFW.glfwSetWindowMaximizeCallback(window, masterWindowMaximizeCallback);
		GLFW.glfwSetWindowPosCallback(window, masterWindowPosCallback);
		GLFW.glfwSetWindowRefreshCallback(window, masterWindowRefreshCallback);
		GLFW.glfwSetWindowSizeCallback(window, masterWindowSizeCallback);

		logger.debug("Callbacks for window [{}] were registered", window);
	}

	@Override
	public void unregisterWindowCallbacks(long window) {
		this.getCharCallbacks().remove(window);
		this.getCharModsCallbacks().remove(window);
		this.getCursorEnterCallbacks().remove(window);
		this.getCursorPosCallbacks().remove(window);
		this.getDropCallbacks().remove(window);
		this.getFramebufferSizeCallbacks().remove(window);
		this.getKeyCallbacks().remove(window);
		this.getMouseButtonCallbacks().remove(window);
		this.getScrollCallbacks().remove(window);
		this.getWindowCloseCallbacks().remove(window);
		this.getWindowContentScaleCallbacks().remove(window);
		this.getWindowFocusCallbacks().remove(window);
		this.getWindowIconifyCallbacks().remove(window);
		this.getWindowMaximizeCallbacks().remove(window);
		this.getWindowPosCallbacks().remove(window);
		this.getWindowRefreshCallbacks().remove(window);
		this.getWindowSizeCallbacks().remove(window);

		GLFW.glfwSetCharCallback(window, null);
		GLFW.glfwSetCharModsCallback(window, null);
		GLFW.glfwSetCursorEnterCallback(window, null);
		GLFW.glfwSetCursorPosCallback(window, null);
		GLFW.glfwSetDropCallback(window, null);
		GLFW.glfwSetFramebufferSizeCallback(window, null);
		GLFW.glfwSetKeyCallback(window, null);
		GLFW.glfwSetMouseButtonCallback(window, null);
		GLFW.glfwSetScrollCallback(window, null);
		GLFW.glfwSetWindowCloseCallback(window, null);
		GLFW.glfwSetWindowContentScaleCallback(window, null);
		GLFW.glfwSetWindowFocusCallback(window, null);
		GLFW.glfwSetWindowIconifyCallback(window, null);
		GLFW.glfwSetWindowMaximizeCallback(window, null);
		GLFW.glfwSetWindowPosCallback(window, null);
		GLFW.glfwSetWindowRefreshCallback(window, null);
		GLFW.glfwSetWindowSizeCallback(window, null);

		logger.debug("Callbacks for window [{}] were unregistered", window);
	}


	@Override
	public GLFWErrorCallbackI getMasterErrorCallback() {
		return masterErrorCallback;
	}

	@Override
	public GLFWMonitorCallbackI getMasterMonitorCallback() {
		return masterMonitorCallback;
	}

	@Override
	public GLFWWindowPosCallbackI getMasterWindowPosCallback() {
		return masterWindowPosCallback;
	}

	@Override
	public GLFWWindowSizeCallbackI getMasterWindowSizeCallback() {
		return masterWindowSizeCallback;
	}

	@Override
	public GLFWWindowCloseCallbackI getMasterWindowCloseCallback() {
		return masterWindowCloseCallback;
	}

	@Override
	public GLFWWindowRefreshCallbackI getMasterWindowRefreshCallback() {
		return masterWindowRefreshCallback;
	}

	@Override
	public GLFWWindowFocusCallbackI getMasterWindowFocusCallback() {
		return masterWindowFocusCallback;
	}

	@Override
	public GLFWWindowIconifyCallbackI getMasterWindowIconifyCallback() {
		return masterWindowIconifyCallback;
	}

	@Override
	public GLFWWindowMaximizeCallbackI getMasterWindowMaximizeCallback() {
		return masterWindowMaximizeCallback;
	}

	@Override
	public GLFWWindowContentScaleCallbackI getMasterWindowContentScaleCallback() {
		return masterWindowContentScaleCallback;
	}

	@Override
	public GLFWFramebufferSizeCallbackI getMasterFramebufferSizeCallback() {
		return masterFramebufferSizeCallback;
	}

	@Override
	public GLFWKeyCallbackI getMasterKeyCallback() {
		return masterKeyCallback;
	}

	@Override
	public GLFWCharCallbackI getMasterCharCallback() {
		return masterCharCallback;
	}

	@Override
	public GLFWCharModsCallbackI getMasterCharModsCallback() {
		return masterCharModsCallback;
	}

	@Override
	public GLFWMouseButtonCallbackI getMasterMouseButtonCallback() {
		return masterMouseButtonCallback;
	}

	@Override
	public GLFWCursorPosCallbackI getMasterCursorPosCallback() {
		return masterCursorPosCallback;
	}

	@Override
	public GLFWCursorEnterCallbackI getMasterCursorEnterCallback() {
		return masterCursorEnterCallback;
	}

	@Override
	public GLFWScrollCallbackI getMasterScrollCallback() {
		return masterScrollCallback;
	}

	@Override
	public GLFWDropCallbackI getMasterDropCallback() {
		return masterDropCallback;
	}

	@Override
	public GLFWJoystickCallbackI getMasterJoystickCallback() {
		return masterJoystickCallback;
	}


	public static class GLFWInputConsumerFactory implements IGLFWInputConsumerFactory {
		private final ILogging logging;

		@Inject
		public GLFWInputConsumerFactory(ILogging logging) {
			this.logging = logging;
		}

		@Override
		public IGLFWInputConsumer create(ILWJGLInputDispatch dispatch) {
			return new GLFWInputConsumer(logging);
		}
	}

}
