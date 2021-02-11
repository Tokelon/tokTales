package com.tokelon.toktales.core.content.manage;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import com.tokelon.toktales.core.location.ApplicationLocation;
import com.tokelon.toktales.core.location.IApplicationLocation;
import com.tokelon.toktales.tools.assets.annotation.ParentIdentifiers;
import com.tokelon.toktales.tools.assets.files.IParentResolver;

public class ApplicationLocationParentResolver implements IParentResolver<IApplicationLocation> {


	private final Map<Object, IApplicationLocation> parentIdentifierMap;

	public ApplicationLocationParentResolver(Object parentIdentifier, IApplicationLocation parent) {
		parentIdentifierMap = new HashMap<>();
		parentIdentifierMap.put(parentIdentifier, parent);
	}

	@Inject
	public ApplicationLocationParentResolver(@ParentIdentifiers Map<Object, IApplicationLocation> parentIdentifierMap) {
		this.parentIdentifierMap = parentIdentifierMap;
	}


	@Override
	public IApplicationLocation resolve(IApplicationLocation relative, Object parentIdentifier) {
		IApplicationLocation applicationLocation = parentIdentifierMap.get(parentIdentifier);
		return applicationLocation == null ? null : new ApplicationLocation(relative.getLocationPath());
	}

	@Override
	public boolean canResolve(IApplicationLocation relative, Object parentIdentifier) {
		return parentIdentifierMap.containsKey(parentIdentifier);
	}

}
