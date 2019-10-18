package com.tokelon.toktales.tools.assets.files;

import java.io.File;

public class RelativeFileKey implements IRelativeFileKey {

	
	private final File file;
	private final Object parent;

	public RelativeFileKey(File file) {
		this(file, null);
	}
	
	public RelativeFileKey(File file, Object parentIdentifier) {
		this.file = file;
		this.parent = parentIdentifier;
	}
	
	
	@Override
	public File getFile() {
		return file;
	}

	@Override
	public Object getParentIdentifier() {
		return parent;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((file == null) ? 0 : file.hashCode());
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
		if (!(obj instanceof IRelativeFileKey)) {
			return false;
		}
		IRelativeFileKey other = (IRelativeFileKey) obj;
		
		if (file == null) {
			if (other.getFile() != null) {
				return false;
			}
		} else if (!file.equals(other.getFile())) {
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
