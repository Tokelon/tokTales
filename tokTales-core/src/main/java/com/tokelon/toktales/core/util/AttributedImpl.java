package com.tokelon.toktales.core.util;

import java.util.HashSet;
import java.util.Set;

public class AttributedImpl implements IAttributed {

	

	// TODO: Check - Boxing type
	
	private final Set<Integer> attributes = new HashSet<Integer>(); 
	
	
	@Override
	public boolean isAttributed(int attributeCode) {
		return attributes.contains(attributeCode);
	}

	@Override
	public void setAttributed(int attributeCode) {
		attributes.add(attributeCode);
	}

	@Override
	public void unsetAttributed(int attributeCode) {
		// throw exception if not contained?
		attributes.remove(attributeCode);
	}
	
}
