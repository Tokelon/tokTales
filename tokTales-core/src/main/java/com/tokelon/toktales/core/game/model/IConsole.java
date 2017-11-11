package com.tokelon.toktales.core.game.model;

public interface IConsole {
	
	// TODO: What package to put this in? logic?
	
	
	public void setPrompt(String value);
	public String getPrompt();
	
	
	public void append(char c);
	
	public void append(int codepoint);
	
	public void delete();
	
	public void deleteAll();
	
	public void commit();	//enter
	
	//public void removeCharAt(int index);
	//public void removeStringAt
	
	public void write(String str);
	
	
	public void print(String str);
	
	
	
	public int getCodepointCount();
	
	public int getCodepointAt(int index);
	//public int getCharAt();
	
	
	public int getHistoryCount();
	
	public String getHistoryAt(int index);
	
	public int getHistoryCodepoint(int historyIndex, int codepointIndex);
	
	
	
	
	
	/*
	
	public void print(char c);
	
	public void print(String str);
	
	public void println(String str);

	
	
	public int getLineCount();
	
	public String getLineAt(int lineNum);
	
	//public char getCharAt(int lineNum, int charIdx);
	
	
	
	//public void replaceLine(int lineNum, String newStr);
	
	//public void replaceChar(int lineNum, int charIdx, char newChar);
	
	*/
}
