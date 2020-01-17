package com.tokelon.toktales.extensions.core.game.controller;

import com.tokelon.toktales.extensions.core.game.model.IScreenDialog;

public interface IDialogController extends ITextBoxController {

	// TODO: How does this and ScreenDialog interact? Fix this

	public IScreenDialog getDialog();
	
}
