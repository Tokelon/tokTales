package com.tokelon.toktales.extens.def.core.game.controller;

import com.tokelon.toktales.core.content.text.ISpriteFont;
import com.tokelon.toktales.core.game.controller.AbstractController;
import com.tokelon.toktales.core.game.model.IConsole;

public class SprifoConsoleController extends AbstractController implements ISprifoConsoleController {

	
	private final IConsole mConsole;
	
	private ISpriteFont mFont;
	
	
	public SprifoConsoleController(IConsole console) {
		this.mConsole = console;
	}

	
	
	@Override
	public IConsole getConsole() {
		return mConsole;
	}
	
	
	@Override
	public ISpriteFont getFont() {
		return mFont;
	}
	
	@Override
	public void setFont(ISpriteFont font) {
		this.mFont = font;
	}
	
	
	@Override
	public void inputCodepoint(int codepoint) {
		mConsole.append(codepoint);
	}
	
	@Override
	public void inputText(String text) {
		for(int i = 0; i < text.codePointCount(0, text.length()); i++) {
			mConsole.append(text.codePointAt(i));
		}
	}

	@Override
	public void enter() {
		mConsole.commit();
	}

	@Override
	public void backspace() {
		mConsole.delete();
	}
	
	@Override
	public void clear() {
		mConsole.deleteAll();
	}


}
