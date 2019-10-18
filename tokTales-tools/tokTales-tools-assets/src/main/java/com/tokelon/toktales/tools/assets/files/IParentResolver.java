package com.tokelon.toktales.tools.assets.files;

public interface IParentResolver<T> {
	// IParentTranslator, IKeyParentResolver
	
	
	public T resolve(T relative, Object parentIdentifier);

	public boolean canResolve(T relative, Object parentIdentifier);
	
}
