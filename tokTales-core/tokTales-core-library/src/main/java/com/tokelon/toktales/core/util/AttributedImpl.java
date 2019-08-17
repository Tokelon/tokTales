package com.tokelon.toktales.core.util;

import gnu.trove.set.TIntSet;
import gnu.trove.set.hash.TIntHashSet;

public class AttributedImpl implements IAttributed {


	private final TIntSet attributes = new TIntHashSet();
	
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
