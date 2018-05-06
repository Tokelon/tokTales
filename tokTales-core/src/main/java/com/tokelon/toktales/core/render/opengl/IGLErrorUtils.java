package com.tokelon.toktales.core.render.opengl;

import java.util.List;

/** Provides utilities for handling of GL errors.
 */
public interface IGLErrorUtils {
	// Make empty implementation that does nothing
	
	
	/** Enables or disables error checking according to the given value.
	 * <p>
	 * The setting will affect all calls to this utils instance. 
	 * 
	 * @param enabled True to enable, false to disable.
	 */
	public void enableErrorChecking(boolean enabled);
	
	/** Returns whether error checking for this instance is enabled or not.
	 * 
	 * @return True if enabled, false if disabled.
	 */
	public boolean isErrorCheckingEnabled();
	
	
	/** Checks if there are GL errors and throws an exceptions if there are.
	 * <p>
	 * If error checking is disabled there will be no effect.
	 * 
	 * @throws OpenGLErrorException If there are GL errors.
	 */
	public void assertNoGLErrors() throws OpenGLErrorException;
	
	/** Makes a log entry with all GL errors and the given context.  
	 * <p>
	 * If error checking is disabled there will be no effect.
	 * 
	 * @param context Indicates which context this check is in.
	 * @return The number of errors logged.
	 */
	public int logGLErrors(String context);
	
	
	/** Checks for GL errors and returns them in a list.
	 * 
	 * @return A list of GL errors.
	 */
	public List<IGLError> getGLErrors();
	
	
	
	public interface IGLError {
		
		public int getErrorCode();
		public String getErrorMessage();
	}
	
}
