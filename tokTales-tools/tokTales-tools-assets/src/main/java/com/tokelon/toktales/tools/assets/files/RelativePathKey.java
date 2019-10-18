package com.tokelon.toktales.tools.assets.files;

import java.nio.file.Path;

public class RelativePathKey implements IRelativePathKey {

	
	private final Path path;
	private final Object parent;

	public RelativePathKey(Path file) {
		this(file, null);
	}
	
	public RelativePathKey(Path file, Object parentIdentifier) {
		this.path = file;
		this.parent = parentIdentifier;
	}
	
	
	@Override
	public Path getPath() {
		return path;
	}

	@Override
	public Object getParentIdentifier() {
		return parent;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof IRelativePathKey)) {
			return false;
		}
		IRelativePathKey other = (IRelativePathKey) obj;
		
		if (path == null) {
			if (other.getPath() != null) {
				return false;
			}
		} else if (!path.equals(other.getPath())) {
			return false;
		}
		
		if (parent == null) {
			if (other.getParentIdentifier() != null) {
				return false;
			}
		} else if (!parent.equals(other.getParentIdentifier())) {
			return false;
		}
		
		return true;
	}

}
