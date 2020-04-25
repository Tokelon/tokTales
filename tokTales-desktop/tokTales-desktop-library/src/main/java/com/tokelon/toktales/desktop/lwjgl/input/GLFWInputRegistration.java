package com.tokelon.toktales.desktop.lwjgl.input;

import java.util.ArrayList;
import java.util.List;

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

import gnu.trove.map.TLongObjectMap;
import gnu.trove.map.hash.TLongObjectHashMap;

public class GLFWInputRegistration implements IGLFWInputRegistration {


	private final List<GLFWErrorCallbackI> containerErrorCallbacks;
	private final List<GLFWMonitorCallbackI> containerMonitorCallbacks;
	private final List<GLFWJoystickCallbackI> containerJoystickCallbacks;

	private final TLongObjectMap<List<GLFWCharCallbackI>> containerCharCallbacks;
	private final TLongObjectMap<List<GLFWCharModsCallbackI>> containerCharModsCallbacks;
	private final TLongObjectMap<List<GLFWCursorEnterCallbackI>> containerCursorEnterCallbacks;
	private final TLongObjectMap<List<GLFWCursorPosCallbackI>> containerCursorPosCallbacks;
	private final TLongObjectMap<List<GLFWDropCallbackI>> containerDropCallbacks;
	private final TLongObjectMap<List<GLFWFramebufferSizeCallbackI>> containerFramebufferSizeCallbacks;
	private final TLongObjectMap<List<GLFWKeyCallbackI>> containerKeyCallbacks;
	private final TLongObjectMap<List<GLFWMouseButtonCallbackI>> containerMouseButtonCallbacks;
	private final TLongObjectMap<List<GLFWScrollCallbackI>> containerScrollCallbacks;
	private final TLongObjectMap<List<GLFWWindowCloseCallbackI>> containerWindowCloseCallbacks;
	private final TLongObjectMap<List<GLFWWindowContentScaleCallbackI>> containerWindowContentScaleCallbacks;
	private final TLongObjectMap<List<GLFWWindowFocusCallbackI>> containerWindowFocusCallbacks;
	private final TLongObjectMap<List<GLFWWindowIconifyCallbackI>> containerWindowIconifyCallbacks;
	private final TLongObjectMap<List<GLFWWindowMaximizeCallbackI>> containerWindowMaximizeCallbacks;
	private final TLongObjectMap<List<GLFWWindowPosCallbackI>> containerWindowPosCallbacks;
	private final TLongObjectMap<List<GLFWWindowRefreshCallbackI>> containerWindowRefreshCallbacks;
	private final TLongObjectMap<List<GLFWWindowSizeCallbackI>> containerWindowSizeCallbacks;

	public GLFWInputRegistration() {
		this.containerErrorCallbacks = new ArrayList<>();
		this.containerMonitorCallbacks = new ArrayList<>();
		this.containerJoystickCallbacks = new ArrayList<>();

		this.containerCharCallbacks = new TLongObjectHashMap<>(1);
		this.containerCharModsCallbacks = new TLongObjectHashMap<>(1);
		this.containerCursorEnterCallbacks = new TLongObjectHashMap<>(1);
		this.containerCursorPosCallbacks = new TLongObjectHashMap<>(1);
		this.containerDropCallbacks = new TLongObjectHashMap<>(1);
		this.containerFramebufferSizeCallbacks = new TLongObjectHashMap<>(1);
		this.containerKeyCallbacks = new TLongObjectHashMap<>(1);
		this.containerMouseButtonCallbacks = new TLongObjectHashMap<>(1);
		this.containerScrollCallbacks = new TLongObjectHashMap<>(1);
		this.containerWindowCloseCallbacks = new TLongObjectHashMap<>(1);
		this.containerWindowContentScaleCallbacks = new TLongObjectHashMap<>(1);
		this.containerWindowFocusCallbacks = new TLongObjectHashMap<>(1);
		this.containerWindowIconifyCallbacks = new TLongObjectHashMap<>(1);
		this.containerWindowMaximizeCallbacks = new TLongObjectHashMap<>(1);
		this.containerWindowPosCallbacks = new TLongObjectHashMap<>(1);
		this.containerWindowRefreshCallbacks = new TLongObjectHashMap<>(1);
		this.containerWindowSizeCallbacks = new TLongObjectHashMap<>(1);
	}


	protected List<GLFWErrorCallbackI> getErrorCallbacks() {
		return containerErrorCallbacks;
	}

	protected List<GLFWMonitorCallbackI> getMonitorCallbacks() {
		return containerMonitorCallbacks;
	}

	protected List<GLFWJoystickCallbackI> getJoystickCallbacks() {
		return containerJoystickCallbacks;
	}

	protected TLongObjectMap<List<GLFWCharCallbackI>> getCharCallbacks() {
		return containerCharCallbacks;
	}

	protected TLongObjectMap<List<GLFWCharModsCallbackI>> getCharModsCallbacks() {
		return containerCharModsCallbacks;
	}

	protected TLongObjectMap<List<GLFWCursorEnterCallbackI>> getCursorEnterCallbacks() {
		return containerCursorEnterCallbacks;
	}

	protected TLongObjectMap<List<GLFWCursorPosCallbackI>> getCursorPosCallbacks() {
		return containerCursorPosCallbacks;
	}

	protected TLongObjectMap<List<GLFWDropCallbackI>> getDropCallbacks() {
		return containerDropCallbacks;
	}

	protected TLongObjectMap<List<GLFWFramebufferSizeCallbackI>> getFramebufferSizeCallbacks() {
		return containerFramebufferSizeCallbacks;
	}

	protected TLongObjectMap<List<GLFWKeyCallbackI>> getKeyCallbacks() {
		return containerKeyCallbacks;
	}

	protected TLongObjectMap<List<GLFWMouseButtonCallbackI>> getMouseButtonCallbacks() {
		return containerMouseButtonCallbacks;
	}

	protected TLongObjectMap<List<GLFWScrollCallbackI>> getScrollCallbacks() {
		return containerScrollCallbacks;
	}

	protected TLongObjectMap<List<GLFWWindowCloseCallbackI>> getWindowCloseCallbacks() {
		return containerWindowCloseCallbacks;
	}

	protected TLongObjectMap<List<GLFWWindowContentScaleCallbackI>> getWindowContentScaleCallbacks() {
		return containerWindowContentScaleCallbacks;
	}

	protected TLongObjectMap<List<GLFWWindowFocusCallbackI>> getWindowFocusCallbacks() {
		return containerWindowFocusCallbacks;
	}

	protected TLongObjectMap<List<GLFWWindowIconifyCallbackI>> getWindowIconifyCallbacks() {
		return containerWindowIconifyCallbacks;
	}

	protected TLongObjectMap<List<GLFWWindowMaximizeCallbackI>> getWindowMaximizeCallbacks() {
		return containerWindowMaximizeCallbacks;
	}

	protected TLongObjectMap<List<GLFWWindowPosCallbackI>> getWindowPosCallbacks() {
		return containerWindowPosCallbacks;
	}

	protected TLongObjectMap<List<GLFWWindowRefreshCallbackI>> getWindowRefreshCallbacks() {
		return containerWindowRefreshCallbacks;
	}

	protected TLongObjectMap<List<GLFWWindowSizeCallbackI>> getWindowSizeCallbacks() {
		return containerWindowSizeCallbacks;
	}


	@Override
	public void registerErrorCallback(GLFWErrorCallbackI callback) {
		containerErrorCallbacks.add(callback);
	}

	@Override
	public void registerMonitorCallback(GLFWMonitorCallbackI callback) {
		containerMonitorCallbacks.add(callback);
	}

	@Override
	public void registerWindowPosCallback(long window, GLFWWindowPosCallbackI callback) {
		registerCallback(containerWindowPosCallbacks, window, callback);
	}

	@Override
	public void registerWindowSizeCallback(long window, GLFWWindowSizeCallbackI callback) {
		registerCallback(containerWindowSizeCallbacks, window, callback);
	}

	@Override
	public void registerWindowCloseCallback(long window, GLFWWindowCloseCallbackI callback) {
		registerCallback(containerWindowCloseCallbacks, window, callback);
	}

	@Override
	public void registerWindowRefreshCallback(long window, GLFWWindowRefreshCallbackI callback) {
		registerCallback(containerWindowRefreshCallbacks, window, callback);
	}

	@Override
	public void registerWindowFocusCallback(long window, GLFWWindowFocusCallbackI callback) {
		registerCallback(containerWindowFocusCallbacks, window, callback);
	}

	@Override
	public void registerWindowIconifyCallback(long window, GLFWWindowIconifyCallbackI callback) {
		registerCallback(containerWindowIconifyCallbacks, window, callback);
	}

	@Override
	public void registerWindowMaximizeCallback(long window, GLFWWindowMaximizeCallbackI callback) {
		registerCallback(containerWindowMaximizeCallbacks, window, callback);
	}

	@Override
	public void registerWindowContentScaleCallback(long window, GLFWWindowContentScaleCallbackI callback) {
		registerCallback(containerWindowContentScaleCallbacks, window, callback);
	}

	@Override
	public void registerFramebufferSizeCallback(long window, GLFWFramebufferSizeCallbackI callback) {
		registerCallback(containerFramebufferSizeCallbacks, window, callback);
	}

	@Override
	public void registerKeyCallback(long window, GLFWKeyCallbackI callback) {
		registerCallback(containerKeyCallbacks, window, callback);
	}

	@Override
	public void registerCharCallback(long window, GLFWCharCallbackI callback) {
		registerCallback(containerCharCallbacks, window, callback);
	}

	@Override
	public void registerCharModsCallback(long window, GLFWCharModsCallbackI callback) {
		registerCallback(containerCharModsCallbacks, window, callback);
	}

	@Override
	public void registerMouseButtonCallback(long window, GLFWMouseButtonCallbackI callback) {
		registerCallback(containerMouseButtonCallbacks, window, callback);
	}

	@Override
	public void registerCursorPosCallback(long window, GLFWCursorPosCallbackI callback) {
		registerCallback(containerCursorPosCallbacks, window, callback);
	}

	@Override
	public void registerCursorEnterCallback(long window, GLFWCursorEnterCallbackI callback) {
		registerCallback(containerCursorEnterCallbacks, window, callback);
	}

	@Override
	public void registerScrollCallback(long window, GLFWScrollCallbackI callback) {
		registerCallback(containerScrollCallbacks, window, callback);
	}

	@Override
	public void registerDropCallback(long window, GLFWDropCallbackI callback) {
		registerCallback(containerDropCallbacks, window, callback);
	}

	@Override
	public void registerJoystickCallback(GLFWJoystickCallbackI callback) {
		containerJoystickCallbacks.add(callback);
	}

	private <T> void registerCallback(TLongObjectMap<List<T>> container, long window, T callback) {
		List<T> callbacks = container.get(window);
		if(callbacks == null) {
			container.put(window, callbacks = new ArrayList<>());
		}

		callbacks.add(callback);
	}


	@Override
	public boolean unregisterErrorCallback(GLFWErrorCallbackI callback) {
		return containerErrorCallbacks.remove(callback);
	}

	@Override
	public boolean unregisterMonitorCallback(GLFWMonitorCallbackI callback) {
		return containerMonitorCallbacks.remove(callback);
	}

	@Override
	public boolean unregisterWindowPosCallback(long window, GLFWWindowPosCallbackI callback) {
		return unregisterCallback(containerWindowPosCallbacks, window, callback);
	}

	@Override
	public boolean unregisterWindowSizeCallback(long window, GLFWWindowSizeCallbackI callback) {
		return unregisterCallback(containerWindowSizeCallbacks, window, callback);
	}

	@Override
	public boolean unregisterWindowCloseCallback(long window, GLFWWindowCloseCallbackI callback) {
		return unregisterCallback(containerWindowCloseCallbacks, window, callback);
	}

	@Override
	public boolean unregisterWindowRefreshCallback(long window, GLFWWindowRefreshCallbackI callback) {
		return unregisterCallback(containerWindowRefreshCallbacks, window, callback);
	}

	@Override
	public boolean unregisterWindowFocusCallback(long window, GLFWWindowFocusCallbackI callback) {
		return unregisterCallback(containerWindowFocusCallbacks, window, callback);
	}

	@Override
	public boolean unregisterWindowIconifyCallback(long window, GLFWWindowIconifyCallbackI callback) {
		return unregisterCallback(containerWindowIconifyCallbacks, window, callback);
	}

	@Override
	public boolean unregisterWindowMaximizeCallback(long window, GLFWWindowMaximizeCallbackI callback) {
		return unregisterCallback(containerWindowMaximizeCallbacks, window, callback);
	}

	@Override
	public boolean unregisterWindowContentScaleCallback(long window, GLFWWindowContentScaleCallbackI callback) {
		return unregisterCallback(containerWindowContentScaleCallbacks, window, callback);
	}

	@Override
	public boolean unregisterFramebufferSizeCallback(long window, GLFWFramebufferSizeCallbackI callback) {
		return unregisterCallback(containerFramebufferSizeCallbacks, window, callback);
	}

	@Override
	public boolean unregisterKeyCallback(long window, GLFWKeyCallbackI callback) {
		return unregisterCallback(containerKeyCallbacks, window, callback);
	}

	@Override
	public boolean unregisterCharCallback(long window, GLFWCharCallbackI callback) {
		return unregisterCallback(containerCharCallbacks, window, callback);
	}

	@Override
	public boolean unregisterCharModsCallback(long window, GLFWCharModsCallbackI callback) {
		return unregisterCallback(containerCharModsCallbacks, window, callback);
	}

	@Override
	public boolean unregisterMouseButtonCallback(long window, GLFWMouseButtonCallbackI callback) {
		return unregisterCallback(containerMouseButtonCallbacks, window, callback);
	}

	@Override
	public boolean unregisterCursorPosCallback(long window, GLFWCursorPosCallbackI callback) {
		return unregisterCallback(containerCursorPosCallbacks, window, callback);
	}

	@Override
	public boolean unregisterCursorEnterCallback(long window, GLFWCursorEnterCallbackI callback) {
		return unregisterCallback(containerCursorEnterCallbacks, window, callback);
	}

	@Override
	public boolean unregisterScrollCallback(long window, GLFWScrollCallbackI callback) {
		return unregisterCallback(containerScrollCallbacks, window, callback);
	}

	@Override
	public boolean unregisterDropCallback(long window, GLFWDropCallbackI callback) {
		return unregisterCallback(containerDropCallbacks, window, callback);
	}

	@Override
	public boolean unregisterJoystickCallback(GLFWJoystickCallbackI callback) {
		return containerJoystickCallbacks.remove(callback);
	}

	private <T> boolean unregisterCallback(TLongObjectMap<List<T>> container, long window, T callback) {
		List<T> callbacks = container.get(window);
		if(callbacks == null) {
			return false;
		}

		return callbacks.remove(callback);
	}


	@Override
	public boolean hasErrorCallback(GLFWErrorCallbackI callback) {
		return containerErrorCallbacks.contains(callback);
	}

	@Override
	public boolean hasMonitorCallback(GLFWMonitorCallbackI callback) {
		return containerMonitorCallbacks.contains(callback);
	}

	@Override
	public boolean hasWindowPosCallback(long window, GLFWWindowPosCallbackI callback) {
		return hasCallback(containerWindowPosCallbacks, window, callback);
	}

	@Override
	public boolean hasWindowSizeCallback(long window, GLFWWindowSizeCallbackI callback) {
		return hasCallback(containerWindowSizeCallbacks, window, callback);
	}

	@Override
	public boolean hasWindowCloseCallback(long window, GLFWWindowCloseCallbackI callback) {
		return hasCallback(containerWindowCloseCallbacks, window, callback);
	}

	@Override
	public boolean hasWindowRefreshCallback(long window, GLFWWindowRefreshCallbackI callback) {
		return hasCallback(containerWindowRefreshCallbacks, window, callback);
	}

	@Override
	public boolean hasWindowFocusCallback(long window, GLFWWindowFocusCallbackI callback) {
		return hasCallback(containerWindowFocusCallbacks, window, callback);
	}

	@Override
	public boolean hasWindowIconifyCallback(long window, GLFWWindowIconifyCallbackI callback) {
		return hasCallback(containerWindowIconifyCallbacks, window, callback);
	}

	@Override
	public boolean hasWindowMaximizeCallback(long window, GLFWWindowMaximizeCallbackI callback) {
		return hasCallback(containerWindowMaximizeCallbacks, window, callback);
	}

	@Override
	public boolean hasWindowContentScaleCallback(long window, GLFWWindowContentScaleCallbackI callback) {
		return hasCallback(containerWindowContentScaleCallbacks, window, callback);
	}

	@Override
	public boolean hasFramebufferSizeCallback(long window, GLFWFramebufferSizeCallbackI callback) {
		return hasCallback(containerFramebufferSizeCallbacks, window, callback);
	}

	@Override
	public boolean hasKeyCallback(long window, GLFWKeyCallbackI callback) {
		return hasCallback(containerKeyCallbacks, window, callback);
	}

	@Override
	public boolean hasCharCallback(long window, GLFWCharCallbackI callback) {
		return hasCallback(containerCharCallbacks, window, callback);
	}

	@Override
	public boolean hasCharModsCallback(long window, GLFWCharModsCallbackI callback) {
		return hasCallback(containerCharModsCallbacks, window, callback);
	}

	@Override
	public boolean hasMouseButtonCallback(long window, GLFWMouseButtonCallbackI callback) {
		return hasCallback(containerMouseButtonCallbacks, window, callback);
	}

	@Override
	public boolean hasCursorPosCallback(long window, GLFWCursorPosCallbackI callback) {
		return hasCallback(containerCursorPosCallbacks, window, callback);
	}

	@Override
	public boolean hasCursorEnterCallback(long window, GLFWCursorEnterCallbackI callback) {
		return hasCallback(containerCursorEnterCallbacks, window, callback);
	}

	@Override
	public boolean hasScrollCallback(long window, GLFWScrollCallbackI callback) {
		return hasCallback(containerScrollCallbacks, window, callback);
	}

	@Override
	public boolean hasDropCallback(long window, GLFWDropCallbackI callback) {
		return hasCallback(containerDropCallbacks, window, callback);
	}

	@Override
	public boolean hasJoystickCallback(GLFWJoystickCallbackI callback) {
		return containerJoystickCallbacks.contains(callback);
	}

	private <T> boolean hasCallback(TLongObjectMap<List<T>> container, long window, T callback) {
		List<T> callbacks = container.get(window);
		if(callbacks == null) {
			return false;
		}

		return callbacks.contains(callback);
	}

}
