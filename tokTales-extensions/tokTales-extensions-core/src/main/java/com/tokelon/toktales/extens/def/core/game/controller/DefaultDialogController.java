package com.tokelon.toktales.extens.def.core.game.controller;

import com.tokelon.toktales.extens.def.core.game.model.IScreenDialog;

public class DefaultDialogController extends DefaultTextBoxController implements IDialogController {

	
	private final IScreenDialog dialog;
	
	public DefaultDialogController(IScreenDialog screenDialog) {
		super(screenDialog);
		this.dialog = screenDialog;
	}
	
	
	@Override
	public IScreenDialog getDialog() {
		return dialog;
	}

}
