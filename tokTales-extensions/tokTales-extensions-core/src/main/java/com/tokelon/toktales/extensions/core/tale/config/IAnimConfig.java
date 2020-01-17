package com.tokelon.toktales.extensions.core.tale.config;

import java.util.List;

import com.tokelon.toktales.core.config.IFileConfig;

public interface IAnimConfig extends IFileConfig {

	
	
	/* Categories and Keys */
	
	public static final String CATEGORY_ANIMATION = "Animation";

	public static final String KEY_ANIMATION_RESOURCE_DIR = "ResourceDir";
	public static final String KEY_ANIMATION_SEQUENCE = "Sequence";
	public static final String KEY_ANIMATION_SEQUENCE_TIMING = "SequenceTiming";
	public static final String KEY_ANIMATION_ANIM_TIME = "AnimTime";
	
	

	public String getConfigAnimationResourceDir();

	public List<String> getConfigAnimationSequence();
	public List<Integer> getConfigAnimationSequenceTiming();	// TODO: Replace List<Integer> with primitive implementation
	
	public int getConfigAnimationAnimTime();

	
}
