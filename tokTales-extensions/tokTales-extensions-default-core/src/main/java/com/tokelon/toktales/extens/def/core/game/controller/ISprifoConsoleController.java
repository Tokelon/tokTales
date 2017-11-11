package com.tokelon.toktales.extens.def.core.game.controller;

import com.tokelon.toktales.core.content.text.ISpriteFont;
import com.tokelon.toktales.core.game.controller.IController;
import com.tokelon.toktales.core.game.model.IConsole;

public interface ISprifoConsoleController extends IController {

	
	public IConsole getConsole();
	
	
	// Maybe have the font in the renderer rather than the controller?
	//public ISpriteFont getFont();
	public ISpriteFont getFont();
	
	public void setFont(ISpriteFont font);
	
	
	
	//public void inputChar(char c);
	
	//public void inputString(String str);
	
	public void inputCodepoint(int codepoint);
	
	public void inputText(String text);
	
	public void enter();
	
	public void backspace();
	
	public void clear();
	
	
	//public void scrollUp();
	//public void scrollDown();
	
}
