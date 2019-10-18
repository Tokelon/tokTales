package com.tokelon.toktales.tools.assets.files;

import java.nio.file.Path;

public class PathKey implements IPathKey {
	

	private final Path path;

	public PathKey(Path path) {
		this.path = path;
	}

	
	@Override
	public Path getPath() {
		return path;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((path == null) ? 0 : path.hashCode());
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
		if (!(obj instanceof IPathKey)) {
			return false;
		}
		
		IPathKey other = (IPathKey) obj;
		if (path == null) {
			if (other.getPath() != null) {
				return false;
			}
		} else if (!path.equals(other.getPath())) {
			return false;
		}
		
		return true;
	}
}
