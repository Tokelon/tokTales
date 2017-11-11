package com.tokelon.toktales.core.util;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractFlags implements IStringFlags {	//AbstractProperties

	
	private final Set<String> flags = new HashSet<String>();
	
	
	@Override
	public boolean setFlag(String flag) {
		return flags.add(flag);
	}
	
	@Override
	public boolean unsetFlag(String flag) {
		return flags.remove(flag);
	}
	
	@Override
	public boolean hasFlag(String flag) {
		return flags.contains(flag);
	}

}
