package com.tokelon.toktales.extens.def.core.game.controller;

import com.tokelon.toktales.extens.def.core.game.model.IScreenDialog;
import com.tokelon.toktales.extens.def.core.game.model.ScreenDialog;

public class DefaultDialogController extends DefaultTextBoxController implements IDialogController {

	private final ScreenDialog dialog;
	
	
	// TODO: Change to accepting the interface IScreenDialog
	public DefaultDialogController(ScreenDialog screenDialog) {
		super(screenDialog);
		this.dialog = screenDialog;
	}
	
	
	@Override
	public IScreenDialog getDialog() {
		return dialog;
	}
	

}
