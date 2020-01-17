package com.tokelon.toktales.extens.def.core.game.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.tokelon.toktales.core.game.model.IConsole;

public class DefaultConsoleInterpreterManager implements IConsoleInterpreterManager {

	
	private final ArrayList<IConsoleInterpreter> interpreterList;
	private final List<IConsoleInterpreter> unmodifiableInterpreterList;
	
	public DefaultConsoleInterpreterManager() {
		interpreterList = new ArrayList<>(5);
		unmodifiableInterpreterList = Collections.unmodifiableList(interpreterList);
	}
	
	
	@Override
	public boolean interpret(IConsole console, String input) {
		for(int i = interpreterList.size() - 1; i >= 0; i--) {
			IConsoleInterpreter interpreter = interpreterList.get(i);
			
			if(interpreter.interpret(console, input)) {
				return true;
			}
		}
		
		return false;
	}

	@Override
	public List<IConsoleInterpreter> getInterpreterList() {
		return unmodifiableInterpreterList;
	}
	
	@Override
	public int getHighestIndex() {
		return interpreterList.size() - 1;
	}

	@Override
	public void add(IConsoleInterpreter interpreter) {
		interpreterList.add(interpreter);
	}

	@Override
	public void addAtIndex(IConsoleInterpreter interpreter, int index) {
		if(index < 0 || index > interpreterList.size()) {
			throw new IllegalArgumentException("index out of bounds; was: " + index);
		}
		
		interpreterList.add(index, interpreter);
	}

	@Override
	public void remove(IConsoleInterpreter interpreter) {
		interpreterList.remove(interpreter);
	}

	@Override
	public void removeAtIndex(int index) {
		if(index < 0 || index > interpreterList.size()) {
			throw new IllegalArgumentException("index out of bounds; was: " + index);
		}
		
		interpreterList.remove(index);
	}

	@Override
	public IConsoleInterpreter getAtIndex(int index) {
		if(index < 0 || index > interpreterList.size()) {
			throw new IllegalArgumentException("index out of bounds; was: " + index);
		}
		
		return interpreterList.get(index);
	}

	@Override
	public int findIndex(IConsoleInterpreter interpreter) {
		return interpreterList.indexOf(interpreter);
	}

}
