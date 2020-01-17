package com.tokelon.toktales.extensions.core.tale.config;

import java.util.ArrayList;
import java.util.List;

import com.tokelon.toktales.core.config.CiniFileConfig;
import com.tokelon.toktales.core.config.CiniFileConfig.IValueFactory;
import com.tokelon.toktales.tools.core.config.ConfigDataException;
import com.tokelon.toktales.tools.core.config.ICiniConfig.IMutableCiniConfig;

public class CiniAnimConfig extends CiniFileConfig implements IAnimConfig {

	
	public static final String VALUE_CONFIG_TYPE_ANIMATION = "Animation";
	public static final String VALUE_CONFIG_VERSION__01 = "0.1";
	
	
	private final StringValueFactory stringValueFactory = new StringValueFactory();
	private final IntValueFactory intValueFactory = new IntValueFactory();
	
	
	private final List<String> mSequenceList = new ArrayList<String>();
	private final List<Integer> mSequenceTimingList = new ArrayList<Integer>();
	
	private final int defaultValueAnimationSequenceTiming = 0;
	
	
	
	public CiniAnimConfig(IMutableCiniConfig mutableCiniConfig) throws ConfigDataException {
		super(mutableCiniConfig);
		
		checkConfigHeader(mutableCiniConfig, VALUE_CONFIG_TYPE_ANIMATION, VALUE_CONFIG_VERSION__01);
		
		parseSequenceIntoList(mSequenceList);
		parseSequenceTimingIntoList(mSequenceTimingList);
	}
	
	
	
	public void parseSequenceIntoList(List<String> list) {
		String sequence = getString(CATEGORY_ANIMATION, KEY_ANIMATION_SEQUENCE, "");
		
		try {
			parseCSVIntoList(sequence, list, stringValueFactory);
		} catch (ConfigDataException e) {
			// Cannot happen with this factory
			throw new RuntimeException(e);
		}
	}
	
	public void parseSequenceTimingIntoList(List<Integer> list) {
		String sequenceTiming = getString(CATEGORY_ANIMATION, KEY_ANIMATION_SEQUENCE_TIMING, "");
		
		try {
			parseCSVIntoList(sequenceTiming, list, intValueFactory);
		} catch (ConfigDataException e) {
			// Cannot happen with this factory
			throw new RuntimeException(e);
		}
	}
	
	
	
	

	@Override
	public String getConfigAnimationResourceDir() {
		return getString(CATEGORY_ANIMATION, KEY_ANIMATION_RESOURCE_DIR, "");
	}

	@Override
	public List<String> getConfigAnimationSequence() {
		return mSequenceList;
	}

	@Override
	public List<Integer> getConfigAnimationSequenceTiming() {
		return mSequenceTimingList;
	}

	@Override
	public int getConfigAnimationAnimTime() {
		return getInt(CATEGORY_ANIMATION, KEY_ANIMATION_ANIM_TIME, 0);
	}

	
	
	
	private class StringValueFactory implements IValueFactory<String> {
		@Override
		public String newValue(String value) throws ConfigDataException {
			return value;
		}
		
	}
	
	private class IntValueFactory implements IValueFactory<Integer> {
		@Override
		public Integer newValue(String value) throws ConfigDataException {
			return parseInt(value, defaultValueAnimationSequenceTiming, CiniAnimConfig.this.isErrorLoggingForParsingEnabled());
		}
		
	}
	
}
