package com.tokelon.toktales.desktop.ui.window;

/** Provides tools related to window functionality.
 */
public interface IWindowToolkit {


	/** Returns the primary monitor of this device.
	 *
	 * @return The identifier of the primary monitor.
	 */
	public long getPrimaryMonitor();

	/** Returns the monitors of this devices.
	 *
	 * @return An array containing the identifiers of all monitors.
	 */
	public long[] getMonitors();


	/** Sets the global integer value for the given window hint.
	 *
	 * @param hint
	 * @param value
	 * @see #setWindowHintString(int, String)
	 * @see #setDefaultWindowHints()
	 */
	public void setWindowHint(int hint, int value);

	/** Sets the global string value for the given window hint.
	 *
	 * @param hint
	 * @param value
	 * @see #setWindowHint(int, int)
	 * @see #setDefaultWindowHints()
	 */
	public void setWindowHintString(int hint, String value);

	/** Sets the global window hints to their default values.
	 *
	 * @see #setWindowHint(int, int)
	 * @see #setWindowHintString(int, String)
	 */
	public void setDefaultWindowHints();

}
