package com.tokelon.toktales.tools.assets.files;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import com.tokelon.toktales.tools.assets.annotation.ParentIdentifiers;

public class PathParentResolver implements IParentResolver<Path> {


	private final Map<Object, Path> parentIdentifierMap;
	
	public PathParentResolver(Object parentIdentifier, Path parent) {
		parentIdentifierMap = new HashMap<>();
		parentIdentifierMap.put(parentIdentifier, parent);
	}
	
	@Inject
	public PathParentResolver(@ParentIdentifiers Map<Object, Path> parentIdentifierMap) {
		this.parentIdentifierMap = parentIdentifierMap;
	}

	
	@Override
	public Path resolve(Path relative, Object parentIdentifier) {
		Path path = parentIdentifierMap.get(parentIdentifier);
		return path == null ? null : path.resolve(relative);
	}

	@Override
	public boolean canResolve(Path relative, Object parentIdentifier) {
		return parentIdentifierMap.containsKey(parentIdentifier);
	}

}
