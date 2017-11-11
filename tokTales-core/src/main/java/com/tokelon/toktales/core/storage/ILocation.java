package com.tokelon.toktales.core.storage;

import com.tokelon.toktales.core.storage.utils.IConformedPath;

/** A LOCATION on the filesystem, represented by a path.
 *
 */
public interface ILocation {

	public IConformedPath getLocationPath();
	
}
