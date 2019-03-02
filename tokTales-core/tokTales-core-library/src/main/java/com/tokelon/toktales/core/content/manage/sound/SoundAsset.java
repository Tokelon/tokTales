package com.tokelon.toktales.core.content.manage.sound;

import com.tokelon.toktales.core.content.audio.ISound;

public class SoundAsset implements ISoundAsset {

	
	private ISound sound;

	public SoundAsset(ISound sound) {
		this.sound = sound;
	}
	
	
	@Override
	public ISound getSound() {
		return sound;
	}

	@Override
	public void dispose() {
		sound.dispose();
	}

}
