package com.tokelon.toktales.tools.assets.files;

import java.io.File;

public class FileKey implements IFileKey {
	
	
	private final File file;

	public FileKey(File path) {
		this.file = path;
	}

	
	@Override
	public File getFile() {
		return file;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((file == null) ? 0 : file.hashCode());
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
		if (file == null) {
			if (other.getFile() != null) {
				return false;
			}
		} else if (!file.equals(other.getFile())) {
			return false;
		}
		
		return true;
	}

}
