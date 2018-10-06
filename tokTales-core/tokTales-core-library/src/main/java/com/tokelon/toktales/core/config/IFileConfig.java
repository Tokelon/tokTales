package com.tokelon.toktales.core.config;

import java.util.List;

public interface IFileConfig {
	
	
	public String getString(String category, String key, String defaultValue);
	
	public int getInt(String category, String key, int defaultValue);
	
	public long getLong(String category, String key, long defaultValue);
	
	public boolean getBoolean(String category, String key, boolean defaultValue);
	
	public float getFloat(String category, String key, float defaultValue);

	public double getDouble(String category, String key, double defaultValue);
	
	
	//public List getCategories()
	//public List getKeys()

	
	public IConfigEditor edit();
	
	public void refreshAllValues();
	
	
	public interface IConfigEditor {
		
		/* Do this one of two ways.
		 * 
		 * 1. Changes to config are applied immediately when using one of the put...() methods.
		 * After every change all listeners are called.
		 * Changes are written to file only when commit() is called.
		 * 
		 * 2. Changes to config are not applies with calls to put..() methods,
		 * but instead are cached in the editor and applied only when apply() is called.
		 * Listeners are also called when apply() is called, once for each config value that has changed.
		 * Changes are writte to file only when commit() is called.
		 */
		

		
		public void putString(String category, String key, String value);

		public void putInt(String category, String key, int value);
		
		public void putLong(String category, String key, long value);
		
		public void putBoolean(String category, String key, boolean value);

		public void putFloat(String category, String key, float value);

		public void putDouble(String category, String key, double value);
		
		
		
		//public void apply();
		//public void save();
		
		public void commit();
		
		public void refreshCommitAll();
		
	}
	

	
	
	public void passListeners(IFileConfig config);
	
	public void removeAllListeners();
	
	
	
	public void registerOnConfigChangeListener(OnConfigChangeListener listener);
	
	public void removeOnConfigChangeListener(OnConfigChangeListener listenerToRemove);
	
	
	public interface OnConfigChangeListener {
		
		
		public void onConfigChanged(IFileConfig config, String category, String key);
		
		
		/** Beware not to use the entry lists after this method has return, as they will be cleared.
		 * 
		 * @param config
		 * @param entryCategory
		 * @param entryKey
		 */
		public void onConfigBatchChanged(IFileConfig config, List<String> entryCategory, List<String> entryKey);
		
	}
	
}
