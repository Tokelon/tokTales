package com.tokelon.toktales.android.activity;

import android.text.Editable;
import android.text.TextWatcher;

public class ProxyTextWatcher implements TextWatcher {
	
	
	private TextWatcher client;
	
	public ProxyTextWatcher() {
		// Needed for subclass access
	}
	
	
	public void setClient(TextWatcher textWatcher) {
		this.client = textWatcher;
	}

	
	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		client.beforeTextChanged(s, start, count, after);
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		client.onTextChanged(s, start, before, count);
	}

	@Override
	public void afterTextChanged(Editable s) {
		client.afterTextChanged(s);
	}
	
	
}
