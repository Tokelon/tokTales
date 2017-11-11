package com.tokelon.toktales.android.activity;

import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;

import com.tokelon.toktales.android.R;
import com.tokelon.toktales.core.config.IConfigManager;
import com.tokelon.toktales.core.config.IFileConfig;
import com.tokelon.toktales.core.config.IMainConfig;
import com.tokelon.toktales.core.config.IFileConfig.IConfigEditor;
import com.tokelon.toktales.core.engine.TokTales;

public class DebugPrefsActivity extends PreferenceActivity {
	
	/*
	public static final String TAG = "DebugPrefsActivity";
	
	
	private static final String PREF_LIST_RENDER_GRID_CREATION_TYPE_KEY = "pref_list_render_grid_creation_type";
	private static final String PREF_EDIT_RENDER_GRID_BLOCKS_HORIZONTAL_KEY = "pref_edit_render_grid_blocks_horizontal";
	private static final String PREF_EDIT_RENDER_GRID_BLOCKS_VERTICAL_KEY = "pref_edit_render_grid_blocks_vertical";
	private static final String PREF_EDIT_RENDER_GRID_BLOCK_PIXEL_SIZE_KEY = "pref_edit_render_grid_block_pixel_size";
	

	
	private DebugPreferencesChangeListener debugPreferenceListener = new DebugPreferencesChangeListener();
	
	
	@SuppressWarnings("deprecation")	// Not using fragments yet
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		IConfigManager configManager = TokTales.getGame().getConfigManager();
		
		IMainConfig mainConfig = (IMainConfig) configManager.getConfig(IConfigManager.MAIN_CONFIG);
		
		
		
		PreferenceScreen rootPrefScreen = getPreferenceManager().createPreferenceScreen(this);

		
		PreferenceCategory displayPrefCategory = new PreferenceCategory(this);
		displayPrefCategory.setTitle(getResources().getString(R.string.pref_category_debug_display_text));
		
		rootPrefScreen.addPreference(displayPrefCategory);
		
		
		ListPreference renderGridCreationTypePref = new ListPreference(this);
		renderGridCreationTypePref.setKey(PREF_LIST_RENDER_GRID_CREATION_TYPE_KEY);
		renderGridCreationTypePref.setTitle(getResources().getString(R.string.pref_list_debug_render_grid_creation_type_text));
		renderGridCreationTypePref.setEntries(R.array.PrefsDebugRenderGridCreationTypeOptions);
		renderGridCreationTypePref.setEntryValues(R.array.PrefsDebugRenderGridCreationTypeOptions);
		renderGridCreationTypePref.setPersistent(false);
		
		// Set actual value from config
		String renderGridCreationTypeStringValue = mainConfig.unparseRenderGridCreationType(mainConfig.getConfigDisplayRenderGridCreationType());
		renderGridCreationTypePref.setDefaultValue(renderGridCreationTypeStringValue);
		//renderGridCreationTypePref.setValue(mainConfig.getConfigDisplayRenderGridCreationType());
		
		displayPrefCategory.addPreference(renderGridCreationTypePref);
		renderGridCreationTypePref.setOnPreferenceChangeListener(debugPreferenceListener);
		
		
		
		EditTextPreference renderGridBlocksHorizontal = new EditTextPreference(this);
		renderGridBlocksHorizontal.setKey(PREF_EDIT_RENDER_GRID_BLOCKS_HORIZONTAL_KEY);
		renderGridBlocksHorizontal.setTitle(getResources().getString(R.string.pref_debug_render_grid_blocks_horizontal_text));
		renderGridBlocksHorizontal.setPersistent(false);
		renderGridBlocksHorizontal.setDefaultValue(String.valueOf(mainConfig.getConfigDisplayBlocksHorizontal()));
		//renderGridBlocksHorizontal.setText(String.valueOf(mainConfig.getConfigDisplayBlocksHorizontal()));
		
		displayPrefCategory.addPreference(renderGridBlocksHorizontal);
		renderGridBlocksHorizontal.setOnPreferenceChangeListener(debugPreferenceListener);
		
		
		
		EditTextPreference renderGridBlocksVertical = new EditTextPreference(this);
		renderGridBlocksVertical.setKey(PREF_EDIT_RENDER_GRID_BLOCKS_VERTICAL_KEY);
		renderGridBlocksVertical.setTitle(getResources().getString(R.string.pref_debug_render_grid_blocks_vertical_text));
		renderGridBlocksVertical.setPersistent(false);
		renderGridBlocksVertical.setDefaultValue(String.valueOf(mainConfig.getConfigDisplayBlocksVertical()));
		//renderGridBlocksVertical.setText(String.valueOf(mainConfig.getConfigDisplayBlocksVertical()));
		
		displayPrefCategory.addPreference(renderGridBlocksVertical);
		renderGridBlocksVertical.setOnPreferenceChangeListener(debugPreferenceListener);
		
		

		EditTextPreference renderGridBlockPixelSize = new EditTextPreference(this);
		renderGridBlockPixelSize.setKey(PREF_EDIT_RENDER_GRID_BLOCK_PIXEL_SIZE_KEY);
		renderGridBlockPixelSize.setTitle(getResources().getString(R.string.pref_debug_render_grid_block_pixel_size_text));
		renderGridBlockPixelSize.setPersistent(false);
		renderGridBlockPixelSize.setDefaultValue(String.valueOf(mainConfig.getConfigDisplayBlockPixelSize()));
		//renderGridBlockPixelSize.setText(String.valueOf(mainConfig.getConfigDisplayBlockPixelSize()));
		
		displayPrefCategory.addPreference(renderGridBlockPixelSize);
		renderGridBlockPixelSize.setOnPreferenceChangeListener(debugPreferenceListener);
		
		
		
		Preference applyPreference = new Preference(this);
		applyPreference.setTitle(getResources().getString(R.string.pref_debug_display_apply_text));
		applyPreference.setSummary(getResources().getString(R.string.pref_debug_display_apply_summary));
		applyPreference.setPersistent(false);
		
		displayPrefCategory.addPreference(applyPreference);
		applyPreference.setOnPreferenceClickListener(new DebugApplyOnClickListener());		// Does this store a weak reference like setOnPreferenceChangeListener() ?
		
		
		
		setPreferenceScreen(rootPrefScreen);

		//getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(debugPreferenceListener);
		
		/*
		debugPreferenceListener.onSharedPreferenceChanged(getPreferenceManager().getSharedPreferences(), PREF_LIST_RENDER_GRID_CREATION_TYPE_KEY);
		debugPreferenceListener.onSharedPreferenceChanged(getPreferenceManager().getSharedPreferences(), PREF_EDIT_RENDER_GRID_BLOCKS_HORIZONTAL_KEY);
		debugPreferenceListener.onSharedPreferenceChanged(getPreferenceManager().getSharedPreferences(), PREF_EDIT_RENDER_GRID_BLOCKS_VERTICAL_KEY);
		debugPreferenceListener.onSharedPreferenceChanged(getPreferenceManager().getSharedPreferences(), PREF_EDIT_RENDER_GRID_BLOCK_PIXEL_SIZE_KEY);
		*//*
	}
	
	private class DebugApplyOnClickListener implements OnPreferenceClickListener {

		@Override
		public boolean onPreferenceClick(Preference preference) {
			IConfigManager configManager = TokTales.getGame().getConfigManager();
			IFileConfig mainConfig = configManager.getConfig(IConfigManager.MAIN_CONFIG);
			
			IConfigEditor configEditor = mainConfig.edit();
			configEditor.commit();
			
			return true;
		}
		
	}
	
	private class DebugPreferencesChangeListener implements OnPreferenceChangeListener {
		

		@Override
		public boolean onPreferenceChange(Preference preference, Object newValue) {
			
			String prefKey = preference.getKey();
			if(PREF_LIST_RENDER_GRID_CREATION_TYPE_KEY.equals(prefKey)) {
				String newValueString = (String) newValue;

				ListPreference listPref = (ListPreference) preference;
				TokTales.getLog().d(TAG, "Preference changed: " +listPref.getTitle() +" to: " +newValueString);
				
				
				IConfigManager configManager = TokTales.getGame().getConfigManager();
				IFileConfig mainConfig = configManager.getConfig(IConfigManager.MAIN_CONFIG);
				
				IConfigEditor configEditor = mainConfig.edit();
				configEditor.putString(IMainConfig.CATEGORY_DISPLAY, IMainConfig.KEY_DISPLAY_RENDER_GRID_CREATION_TYPE, newValueString);
				
				// Write to file - Here or somewhere else?
				//configEditor.commit();
				
			}
			else if(PREF_EDIT_RENDER_GRID_BLOCKS_HORIZONTAL_KEY.equals(prefKey) || PREF_EDIT_RENDER_GRID_BLOCKS_VERTICAL_KEY.equals(prefKey) || PREF_EDIT_RENDER_GRID_BLOCK_PIXEL_SIZE_KEY.equals(prefKey)) {
				String newValueString = (String) newValue;
				
				EditTextPreference editTextPref = (EditTextPreference) preference;
				TokTales.getLog().d(TAG, "Preference changed: " +editTextPref.getTitle() +" to: " +newValueString);
				
				IConfigManager configManager = TokTales.getGame().getConfigManager();
				IFileConfig mainConfig = configManager.getConfig(IConfigManager.MAIN_CONFIG);
				
				IConfigEditor configEditor = mainConfig.edit();
				

				
				// ! The preference key is not the same as the config key !
				String configKeyFromPreferenceKey;
				if(PREF_EDIT_RENDER_GRID_BLOCKS_HORIZONTAL_KEY.equals(prefKey)) {
					configKeyFromPreferenceKey = IMainConfig.KEY_DISPLAY_RENDER_GRID_BLOCKS_HORIZONTAL;
				}
				else if(PREF_EDIT_RENDER_GRID_BLOCKS_VERTICAL_KEY.equals(prefKey)) {
					configKeyFromPreferenceKey = IMainConfig.KEY_DISPLAY_RENDER_GRID_BLOCKS_VERTICAL;
				}
				else {
					configKeyFromPreferenceKey = IMainConfig.KEY_DISPLAY_RENDER_GRID_BLOCK_PIXEL_SIZE;
				}
				
				
				configEditor.putString(IMainConfig.CATEGORY_DISPLAY, configKeyFromPreferenceKey, newValueString);
				
			}
			else {
				TokTales.getLog().d(TAG, "Unknown preference changed with key: " +prefKey);
			}
			
			return true;
		}
	}
	*/
	
}
