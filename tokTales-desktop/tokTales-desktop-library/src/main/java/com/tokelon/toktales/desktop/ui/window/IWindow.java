package com.tokelon.toktales.desktop.ui.window;

import java.nio.ByteBuffer;

/** Represents a native window on a device.
 * <p>
 * A window must be created by using {@link #create()} before any methods are called on it.
 * In addition, certain methods may only be called if the window is visible.
 */
public interface IWindow {
	// TODO: Implement framebuffer callback support?
	// TODO: Add state with enum?


	/** Creates this window as a native window.
	 * <p>
	 * This should always be the first call on this object.
	 */
	public void create();

	/** Makes this window visible.
	 * <p>
	 * Note that certain methods may only be called if the window is visible.
	 */
	public void show();

	/** Makes this window invisible.
	 * <p>
	 * Note that certain methods may only be called if the window is visible.
	 */
	public void hide();

	/** Destroys this window as a native window.
	 * <p>
	 * This method should be called to free any resources used by this window.
	 */
	public void destroy();


	/** Returns whether the user has requested for this window to close.
	 *
	 * @return True if this window should close, false if not.
	 */
	public boolean shouldClose();

	/** Makes the context of this window current on the calling thread.
	 * <p>
	 * A context must only be current on a single thread a a time and each thread can have only a single current context at a time.
	 */
	public void makeContextCurrent();

	/** Detaches any native window context from the calling thread.
	 */
	public void detachContext();

	/** Requests for the frame buffers of this window to be swapped.
	 *
	 * @see #setSwapInterval(int)
	 */
	public void swapBuffers();


	/**
	 * @return The identifier of this window.
	 * @throws WindowException If this window has not been initialized by {@link #create()}.
	 */
	public long getId();

	/**
	 * @return The width of this window in screen coordinates.
	 * @see #getFrameBufferWidth()
	 */
	public int getWidth();

	/**
	 * @return The height of this window in screen coordinates.
	 * @see #getFrameBufferHeight()
	 */
	public int getHeight();

	/**
	 * @return The horizontal position of the upper-left corner of this window in screen coordinates.
	 */
	public int getPositionX();

	/**
	 * @return The vertical position of the upper-left corner of this window in screen coordinates.
	 */
	public int getPositionY();

	/**
	 * @return The title of this window.
	 */
	public String getTitle();

	/**
	 * @return The monitor identifier for this window, or 0 if none is set.
	 */
	public long getMonitor();

	/**
	 * @return The buffer swap interval for this window.
	 */
	public int getSwapInterval();

	/**
	 * @param mode
	 * @return The value for the given input mode of this window.
	 */
	public int getInputMode(int mode);

	/**
	 * @param attribute
	 * @return The value for the given attribute of this window.
	 */
	public int getAttribute(int attribute);

	/**
	 * @return The frame buffer width of this window in pixels.
	 */
	public int getFrameBufferWidth();

	/**
	 * @return The frame buffer height of this window in pixels.
	 */
	public int getFrameBufferHeight();

	/**
	 * @return True if this window is visible, false if not.
	 */
	public boolean isVisible();

	/**
	 * @return True if this window is in fullscreen mode, false if not.
	 */
	public boolean isFullscreen();

	/**
	 * @return True if this window is in borderless fullscreen mode, false if not.
	 */
	public boolean isBorderless();


	/** Returns the monitor this window is currently on, on a best-guess basis.
	 * <p>
	 * As a general rule that will be the monitor on which this window covers the most area on.
	 *
	 * @return The identifier for the monitor this window is currently on.
	 */
	public long getCurrentMonitor();


	/** Sets the size of this window to the given size in screen coordinates.
	 *
	 * @param width
	 * @param height
	 */
	public void setSize(int width, int height);

	/** Sets the position of this window to the given position in screen coordinates.
	 *
	 * @param x
	 * @param y
	 */
	public void setPosition(int x, int y);

	/** Sets the title of this window to the given title.
	 *
	 * @param title
	 */
	public void setTitle(String title);

	/** Sets the monitor configuration of this window to the given monitor, while keeping all sane values from the current configuration.
	 *
	 * @param monitor
	 */
	public void setMonitor(long monitor);

	/** Sets the monitor configuration of this window to the given values.
	 *
	 * @param monitor
	 * @param xpos
	 * @param ypos
	 * @param width
	 * @param height
	 * @param refreshRate
	 */
	public void setMonitor(long monitor, int xpos, int ypos, int width, int height, int refreshRate);

	/** Sets the buffer swap interval for this window.
	 * <p>
	 * The interval specifies the minimum number of screen updates to wait for until the buffers are swapped.
	 * This is also called vertical synchronization.
	 *
	 * @param interval
	 */
	public void setSwapInterval(int interval);

	/** Sets the value for the given input mode of this window.
	 *
	 * @param mode
	 * @param value
	 */
	public void setInputMode(int mode, int value);

	/** Sets the value for the given attribute of this window.
	 *
	 * @param attribute
	 * @param value
	 */
	public void setAttribute(int attribute, int value);

	/** Sets the icon for this window to the given bitmap.
	 *
	 * @param width
	 * @param height
	 * @param pixels
	 */
	public void setWindowIcon(int width, int height, ByteBuffer pixels);

	/** Removes the icon for this window.
	 */
	public void removeWindowIcon();



	/** Sets this window to windowed mode, using the last known position and size.
	 * <p>
	 * Generally the last known position and size are from when the screen mode was previously switched to something other than windowed.
	 */
	public void setWindowed();

	/** Sets this window to windowed mode, using the given size in screen coordinates and while moving it to the top-left corner of the given monitor.
	 *
	 * @param monitor
	 * @param width
	 * @param height
	 */
	public void setWindowed(long monitor, int width, int height);

	/** Sets this window to windowed mode, using the given size and position in screen coordinates.
	 *
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public void setWindowed(int x, int y, int width, int height);


	/** Sets this window to fullscreen mode on the current monitor as specified by {@link #getCurrentMonitor()}.
	 */
	public void setFullscreen();

	/** Sets this window to fullscreen mode on the given monitor.
	 *
	 * @param monitor
	 */
	public void setFullscreen(long monitor);


	/** Sets this window to borderless fullscreen mode on the current monitor as specified by {@link #getCurrentMonitor()}.
	 */
	public void setBorderless(); // Should be called setBorderlessFullscreen?

	/** Sets this window to borderless fullscreen mode on the given monitor.
	 *
	 * @param monitor
	 */
	public void setBorderless(long monitor);

}
