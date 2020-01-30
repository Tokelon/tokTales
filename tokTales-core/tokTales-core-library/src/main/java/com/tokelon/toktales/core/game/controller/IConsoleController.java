package com.tokelon.toktales.core.game.controller;

import com.tokelon.toktales.core.content.text.IFont;
import com.tokelon.toktales.core.game.controller.IController;
import com.tokelon.toktales.core.game.model.IConsole;

public interface IConsoleController extends IController {

	
	public IConsole getConsole();
	
	
	// Maybe have the font in the renderer rather than the controller?
	//public ISpriteFont getFont();
	public IFont getFont();
	
	public void setFont(IFont font);
	
	
	
	//public void inputChar(char c);
	
	//public void inputString(String str);
	
	public void inputCodepoint(int codepoint);
	
	public void inputText(String text);
	
	public void enter();
	
	public void backspace();
	
	public void clear();


	public void setConsoleOpen(boolean open);
	public void toggleConsole();
	public boolean isConsoleOpen();
	
	
	//public void scrollUp();
	//public void scrollDown();
	
}
