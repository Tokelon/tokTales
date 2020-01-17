package com.tokelon.toktales.extens.def.core.game.model;

import java.util.ArrayList;
import java.util.List;

import com.tokelon.toktales.core.game.model.IConsole;
import com.tokelon.toktales.extens.def.core.game.logic.IConsoleInterpreter;

public class Console implements IConsole {

	
	private final List<String> mHistory = new ArrayList<String>();

	private StringBuilder strBuilder = new StringBuilder();
	
	private String mPrompt = "";
	
	//private int lastLength = 0;
	//private int currentLength = 0;
	
	
	private final IConsoleInterpreter interpreter;
	
	public Console(IConsoleInterpreter consoleInterpreter) {
		this.interpreter = consoleInterpreter;
	}
	
	
	@Override
	public String getPrompt() {
		return mPrompt;
	}
	
	@Override
	public void setPrompt(String value) {
		this.mPrompt = value;
	}
	
	
	@Override
	public void append(char c) {
		strBuilder.append(c);
	}
	
	@Override
	public void append(int codepoint) {
		strBuilder.appendCodePoint(codepoint);
	}

	
	@Override
	public void write(String str) {
		// TODO: Go through the string and find new line chars and trigger commit?
		strBuilder.append(str);
	}
	
	
	@Override
	public void print(String str) {
		strBuilder.append(str);
		commitInternal(false);
	}
	
	
	@Override
	public void delete() {
		if(strBuilder.length() == 0) {
			return;
		}
		
		strBuilder.deleteCharAt(strBuilder.length() - 1);	// TODO: Implement deletion of whole last codepoint
	}
	
	@Override
	public void deleteAll() {
		if(strBuilder.length() == 0) {
			return;
		}
		
		strBuilder.delete(0, strBuilder.length());
	}

	
	@Override
	public void commit() {
		commitInternal(true);
	}
	
	private void commitInternal(boolean interpret) {
		String consoleInput = strBuilder.toString();
		mHistory.add(consoleInput);
		
		//strBuilder.setLength(0);
		strBuilder.delete(0, strBuilder.length());
		
		if(interpret) {
			interpreter.interpret(this, consoleInput);			
		}

	}

	
	@Override
	public int getCodepointCount() {
		return strBuilder.codePointCount(0, strBuilder.length());
	}
	
	@Override
	public int getCodepointAt(int index) {
		return strBuilder.codePointAt(index);
	}
	
	
	@Override
	public int getHistoryCount() {
		return mHistory.size();
	}
	
	@Override
	public String getHistoryAt(int index) {
		return mHistory.get(index);
	}
	
	@Override
	public int getHistoryCodepoint(int historyIndex, int codepointIndex) {
		return mHistory.get(historyIndex).codePointAt(codepointIndex);
	}
	
	
}
