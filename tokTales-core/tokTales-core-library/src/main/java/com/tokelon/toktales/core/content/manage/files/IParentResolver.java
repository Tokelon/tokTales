package com.tokelon.toktales.core.content.manage.files;

public interface IParentResolver<T> {
	// IParentTranslator, IKeyParentResolver
	
	
	public T resolve(T relative, Object parentIdentifier);

	public boolean canResolve(T relative, Object parentIdentifier);
	
}
