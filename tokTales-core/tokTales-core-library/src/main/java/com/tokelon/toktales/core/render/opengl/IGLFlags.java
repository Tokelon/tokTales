package com.tokelon.toktales.core.render.opengl;

public interface IGLFlags {

	
	/** Compares the given value with the internal value of glFlag.
	 * <p>
	 * If there is flag for glFlag it compares the given value with the given glFlag.
	 *  
	 * @param value
	 * @param glFlag
	 * @return True if the given value matches the internal glFlag value, false if not.
	 */
	public boolean isGLFlag(int value, int glFlag); //isGLFlagValue
	
	/** Compares the given value with the internal values of all given glFlags.
	 * 
	 * @param value
	 * @param glFlags
	 * @return True if the given value matches one of the internal glFlag values, false if not.
	 */
	public boolean isInGLFlags(int value, int... glFlags);
	
	
	/** Returns the internal value for the given glFlag.
	 * @param glFlag
	 * @return The value for glFlag, or if there is no such flag, glFlag itself.
	 */
	public int getGLFlagValue(int glFlag); // If no entry, return glFlag or error value?
	
	/** Looks for glFlags for which with internal value matches the given value, and stores them in results.
	 * <p>
	 * If there is not enough space in results, only the first results.length results will be stored.
	 * 
	 * @param value
	 * @param results
	 * @return The number of matching glFlags found.
	 */
	public int getGLFlagsForValue(int value, int[] results);
	
}
