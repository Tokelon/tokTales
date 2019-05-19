package com.tokelon.toktales.core.content.manage.files;

import java.nio.file.Path;

public class StorageFileKey implements IStorageFileKey {
	
	
	private Path storagePath;

	public StorageFileKey(Path storagePath) {
		this.storagePath = storagePath;
	}

	
	@Override
	public Path getStoragePath() {
		return storagePath;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((storagePath == null) ? 0 : storagePath.hashCode());
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
		if (!(obj instanceof IStorageFileKey)) {
			return false;
		}
		
		IStorageFileKey other = (IStorageFileKey) obj;
		if (storagePath == null) {
			if (other.getStoragePath() != null) {
				return false;
			}
		} else if (!storagePath.equals(other.getStoragePath())) {
			return false;
		}
		
		return true;
	}

}
