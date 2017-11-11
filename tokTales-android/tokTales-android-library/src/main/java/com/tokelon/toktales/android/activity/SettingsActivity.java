package com.tokelon.toktales.android.activity;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.tokelon.toktales.android.R;

public class SettingsActivity extends PreferenceActivity {

	@SuppressWarnings("deprecation")	// Not using fragments yet
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		addPreferencesFromResource(R.xml.preferences);
	}
	
}
