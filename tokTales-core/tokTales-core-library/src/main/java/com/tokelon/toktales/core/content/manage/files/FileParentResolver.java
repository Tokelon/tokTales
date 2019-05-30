package com.tokelon.toktales.core.content.manage.files;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import com.tokelon.toktales.core.engine.inject.annotation.ParentIdentifiers;

public class FileParentResolver implements IParentResolver<File> {

	
	private final Map<Object, File> parentIdentifierMap;

	public FileParentResolver(Object parentIdentifier, File parent) {
		parentIdentifierMap = new HashMap<>();
		parentIdentifierMap.put(parentIdentifier, parent);
	}
	
	@Inject
	public FileParentResolver(@ParentIdentifiers Map<Object, File> parentIdentifierMap) {
		this.parentIdentifierMap = parentIdentifierMap;
	}
	
	
	@Override
	public File resolve(File relative, Object parentIdentifier) {
		File file = parentIdentifierMap.get(parentIdentifier);
		return file == null ? null : new File(file, relative.getPath());
	}

	@Override
	public boolean canResolve(File relative, Object parentIdentifier) {
		return parentIdentifierMap.containsKey(parentIdentifier);
	}

}
