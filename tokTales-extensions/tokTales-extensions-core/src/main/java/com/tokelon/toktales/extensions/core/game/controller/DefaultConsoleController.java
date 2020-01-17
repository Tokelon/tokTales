package com.tokelon.toktales.extensions.core.game.controller;

import com.tokelon.toktales.core.content.text.ITextureFont;
import com.tokelon.toktales.core.game.controller.AbstractController;
import com.tokelon.toktales.core.game.controller.IConsoleController;
import com.tokelon.toktales.core.game.model.IConsole;

public class DefaultConsoleController extends AbstractController implements IConsoleController {

	
	private ITextureFont mFont;
	
	private boolean isOpen = false;

	
	private final IConsole mConsole;

	public DefaultConsoleController(IConsole console) {
		this.mConsole = console;
	}

	
	
	@Override
	public IConsole getConsole() {
		return mConsole;
	}
	
	
	@Override
	public ITextureFont getFont() {
		return mFont;
	}
	
	@Override
	public void setFont(ITextureFont font) {
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
	
	
	@Override
	public void setConsoleOpen(boolean open) {
		this.isOpen = open;
	}
	
	@Override
	public void toggleConsole() {
		isOpen = !isOpen;
	}
	
	@Override
	public boolean isConsoleOpen() {
		return isOpen;
	}


}
