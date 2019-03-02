package com.tokelon.toktales.core.content.manage.files;

import java.nio.file.Path;

public class FileKey implements IFileKey {
	
	
	private Path path;

	public FileKey(Path path) {
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
		if (!(obj instanceof IFileKey)) {
			return false;
		}
		
		IFileKey other = (IFileKey) obj;
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
