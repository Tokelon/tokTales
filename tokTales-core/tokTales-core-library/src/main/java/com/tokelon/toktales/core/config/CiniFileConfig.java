package com.tokelon.toktales.core.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.WeakHashMap;

import com.tokelon.toktales.core.prog.annotation.NotThreadSafe;
import com.tokelon.toktales.tools.core.config.AbstractConfig;
import com.tokelon.toktales.tools.core.config.ConfigDataException;
import com.tokelon.toktales.tools.core.config.ICiniConfig;
import com.tokelon.toktales.tools.core.config.ICiniConfig.IMutableCiniConfig;
import com.tokelon.toktales.tools.core.objects.pools.SynchronizedPool;
import com.tokelon.toktales.tools.core.objects.pools.SynchronizedPool.PoolObjectFactory;


@NotThreadSafe
/** Beware that this implementation is not very efficient and getter and editor methods should not be called inside loops (hundreds of times a second),
 * or performance will radically degrade.
 *
 */
public class CiniFileConfig extends AbstractConfig implements IFileConfig {

	// Implement this?
	//public void setIgnoreIdentifierCase(boolean ignore);	threat all as lowercase

	

	
	
	/* File Categories and Keys */
	
	public static final String CATEGORY_CONFIG = "Config";
	public static final String KEY_CONFIG_TYPE = "Type";
	public static final String KEY_CONFIG_VERSION = "Version";
	
	
	
	private final IMutableCiniConfig mutableCiniConfig;
	
	private final Editor editor;
	private final Set<OnConfigChangeListener> listenerSet;
	
	private boolean logParsingErrors = true;
	
	
	
	public CiniFileConfig(IMutableCiniConfig mutableCiniConfig) {
		if(mutableCiniConfig == null) {
			throw new NullPointerException();
		}
		
		this.mutableCiniConfig = mutableCiniConfig;
		
		
		editor = new Editor();
		listenerSet = Collections.synchronizedSet(
				Collections.newSetFromMap(new WeakHashMap<OnConfigChangeListener, Boolean>())
		);	//Collections.synchronizedSet(new HashSet<OnConfigChangeListener>());
	}
	
	//public CiniFileConfig(ICiniConfig ciniConfig) { // Constructor which copies the config into a mutable
	
	
	public void enableErrorLoggingForParsing(boolean enable) {
		logParsingErrors = enable;
	}
	
	public boolean isErrorLoggingForParsingEnabled() {
		return logParsingErrors;
	}
	
	protected IMutableCiniConfig getCiniConfig() {
		return mutableCiniConfig;
	}
	
	
	// Add to interface ?
	protected void checkConfigHeader(ICiniConfig ciniConfig, String expectedTypeValue, String expectedVersionValue) throws ConfigDataException {
		
		String configTypeValue = ciniConfig.getPropertyValue(CATEGORY_CONFIG, KEY_CONFIG_TYPE);
		if(!(expectedTypeValue.equals(configTypeValue))) {
			throw new ConfigDataException(String.format("Config Type \"%s\" is not supported by this class", configTypeValue));
		}
		
		String configVersionValue = ciniConfig.getPropertyValue(CATEGORY_CONFIG, KEY_CONFIG_VERSION);
		if(!(expectedVersionValue.equals(configVersionValue))) {
			throw new ConfigDataException(String.format("Config Version \"%s\" is not supported by this class", configVersionValue));
		}
	}
	
	
	/**
	 * 
	 * @param csvString
	 * @param list
	 * @param valueFactory
	 * @throws ConfigDataException If {@link IValueFactory.newValue()} throws an exception.
	 */
	public <T> void parseCSVIntoList(String csvString, List<T> list, IValueFactory<T> valueFactory) throws ConfigDataException {
		
		
		int lastStart = 0;
		for(int i = 0; i < csvString.length(); i++) {
		
			char nextChar = csvString.charAt(i);
			
			// if this character is a comma, or if this is the last character and not a comma
			//boolean addValue = ;		//(isLastChar && lastStart != i+1);	//(nextChar != ',' && i == csvString.length());
			if(nextChar == ',') {
				String valueString = csvString.substring(lastStart, i);
				
				
				T value = valueFactory.newValue(valueString.trim());
				if(value == null) {
					// Null is not allowed
					throw new NullPointerException();
				}
				
				list.add(value);
				
				
				lastStart = i+1;
			}
			
			
			// If last character
			if(i == csvString.length() - 1) {
				String valueString = csvString.substring(lastStart, csvString.length());

				if(valueString.trim().isEmpty()) {
					// If this is the last character and the value string is empty, ignore it
					continue;
				}

				
				T value = valueFactory.newValue(valueString.trim());
				if(value == null) {
					// Null is not allowed
					throw new NullPointerException();
				}
				
				list.add(value);
			}
			
		}
		
	}
	
	
	
	
	
	
	@Override
	public String getString(String category, String key, String defaultValue) {
		return parseString(mutableCiniConfig.getPropertyValue(category, key), defaultValue, logParsingErrors);
	}

	@Override
	public int getInt(String category, String key, int defaultValue) {
		return parseInt(mutableCiniConfig.getPropertyValue(category, key), defaultValue, logParsingErrors);
	}

	@Override
	public long getLong(String category, String key, long defaultValue) {
		return parseLong(mutableCiniConfig.getPropertyValue(category, key), defaultValue, logParsingErrors);
	}
	
	@Override
	public boolean getBoolean(String category, String key, boolean defaultValue) {
		return parseBoolean(mutableCiniConfig.getPropertyValue(category, key), defaultValue, logParsingErrors);
	}
	
	@Override
	public float getFloat(String category, String key, float defaultValue) {
		return parseFloat(mutableCiniConfig.getPropertyValue(category, key), defaultValue, logParsingErrors);
	}
	
	@Override
	public double getDouble(String category, String key, double defaultValue) {
		return parseDouble(mutableCiniConfig.getPropertyValue(category, key), defaultValue, logParsingErrors);
	}
	
	
	
	
	@Override
	public IConfigEditor edit() {
		return editor;
	}

	
	@Override
	public synchronized void refreshAllValues() {
		editor.refreshCommitAll();
	}

	
	
	
	@Override
	public synchronized void passListeners(IFileConfig config) {
		
		for(OnConfigChangeListener listener: listenerSet) {
			config.registerOnConfigChangeListener(listener);
		}
	}
	
	@Override
	public synchronized void removeAllListeners() {
		listenerSet.clear();
	}
	
	
	
	
	@Override
	public synchronized void registerOnConfigChangeListener(OnConfigChangeListener listener) {
		if(listener == null) {
			throw new NullPointerException();
		}
		
		listenerSet.add(listener);
	}

	@Override
	public synchronized void removeOnConfigChangeListener(OnConfigChangeListener listenerToRemove) {
		if(listenerToRemove == null) {
			throw new NullPointerException();
		}
		
		listenerSet.remove(listenerToRemove);
	}

	
	
	public interface IValueFactory<T> {
		
		/** Null is not permitted as a return value.
		 * 
		 * @param value
		 * @return
		 * @throws ConfigDataException If this factory is set to disallow bad data instead of using a default value.
		 */
		public T newValue(String value) throws ConfigDataException;
	}
	
	
	
	private class Editor implements IConfigEditor {

		private final SynchronizedPool<Object> configChangePool;
		
		private final Set<ConfigChange> configChangeSet = new HashSet<>();
		private final List<String> configChangeCategories = new ArrayList<>();
		private final List<String> configChangeKeys = new ArrayList<>();
		
		public Editor() {
			configChangePool = new SynchronizedPool<Object>(new ConfigChangeFactory(), 10);
		}
		
		
		@Override
		public synchronized void putString(String category, String key, String value) {
			if(!mutableCiniConfig.hasSection(category)) {	// Really add section if it does not exist?
				mutableCiniConfig.addSection(category);
			}
			
			
			ConfigChange stringCC = (ConfigChange) configChangePool.newObject();
			stringCC.set(category, key, value);
			configChangeSet.add(stringCC);
		}

		
		@Override
		public synchronized void putInt(String category, String key, int value) {
			putString(category, key, Integer.toString(value));
		}

		@Override
		public synchronized void putLong(String category, String key, long value) {
			putString(category, key, Long.toString(value));
		}
		
		@Override
		public synchronized void putBoolean(String category, String key, boolean value) {
			putString(category, key, Boolean.toString(value));
		}
		
		@Override
		public synchronized void putFloat(String category, String key, float value) {
			putString(category, key, Float.toString(value));
		}
		
		@Override
		public synchronized void putDouble(String category, String key, double value) {
			putString(category, key, Double.toString(value));
		}
		
		
		
		@Override
		public synchronized void refreshCommitAll() {
			
			for(String section: mutableCiniConfig.getSections()) {
				for(String prop: mutableCiniConfig.getProperties(section)) {
					putString(section, prop, mutableCiniConfig.getPropertyValue(section, prop));
				}
			}
			
			commit();
		}
		
		
		@Override
		public synchronized void commit() {
			
			if(configChangeSet.isEmpty()) {
				// No change in config
				return;
			}
			else if(configChangeSet.size() == 1) {
				// Single config value change
				
				ConfigChange cc = configChangeSet.iterator().next();
				mutableCiniConfig.addProperty(cc.category, cc.key, cc.value);
				
				
				for(OnConfigChangeListener listener: listenerSet) {
					listener.onConfigChanged(CiniFileConfig.this, cc.category, cc.key);
				}
				
				configChangeSet.clear();
				configChangePool.free(cc);		// Clearing the config change data is not needed because it is always being set
			}
			else {
				// Multiple config value change
				
				for(ConfigChange cc: configChangeSet) {
					
					// Apply data
					mutableCiniConfig.addProperty(cc.category, cc.key, cc.value);
					
					
					configChangeCategories.add(cc.category);
					configChangeKeys.add(cc.key);
					
					configChangePool.free(cc);		// Clear data not needed either
				}
				
				configChangeSet.clear();
				
				
				
				/* Call listeners */
				for(OnConfigChangeListener listener: listenerSet) {
					listener.onConfigBatchChanged(CiniFileConfig.this, configChangeCategories, configChangeKeys);
				}
				
				configChangeCategories.clear();
				configChangeKeys.clear();
			}
			
		}
		
		

		/** A ConfigChange is equal another ConfigChange if their category and key are equal.
		 *
		 */
		private class ConfigChange {
			
			private String category = "";
			private String key = "";
			private String value = "";
			
			public void set(String c, String k, String v) {
				if(c==null || k==null || v==null) {
					throw new NullPointerException();
				}
				
				category = c;
				key = k;
				value = v;
			}

			@Override
			public boolean equals(Object obj) {
				if(obj == this) {
					return true;
				}
				if(obj == null) {
					return false;
				}
				if(!(obj instanceof ConfigChange)) {
					return false;
				}
				
				ConfigChange that = (ConfigChange) obj;
				return key.equals(that.key) && category.equals(that.category);
			}

			@Override
			public int hashCode() {
				return 17 + (11 * key.hashCode()) + (13 * category.hashCode());
			}
			
		}
		
		
		private class ConfigChangeFactory implements PoolObjectFactory<Object> {
			
			@Override
			public Object createObject() {
				return new ConfigChange();
			}
		}
		
	}
	
	
}
