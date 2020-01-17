package com.tokelon.toktales.extens.def.core.game.logic;

import java.util.List;

/** Manages a list of console interpreters that are ordered by their indices.
 * <p>
 * The index signifies their priority, so a higher index means a higher priority
 * and interpreters with a higher priority will be executed first.
 * 
 */
public interface IConsoleInterpreterManager extends IConsoleInterpreter {
	
	
	public List<IConsoleInterpreter> getInterpreterList();
	public int getHighestIndex();
	
	public void add(IConsoleInterpreter interpreter);
	public void addAtIndex(IConsoleInterpreter interpreter, int index);
	
	public void remove(IConsoleInterpreter interpreter);
	public void removeAtIndex(int index);
	
	public IConsoleInterpreter getAtIndex(int index);
	public int findIndex(IConsoleInterpreter interpreter);

}
