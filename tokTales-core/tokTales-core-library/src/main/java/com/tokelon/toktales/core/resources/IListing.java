package com.tokelon.toktales.core.resources;

import java.util.List;

import com.tokelon.toktales.core.location.IUniformLocation;

public interface IListing {
	
	
	public FileDescriptor lookupName(String value);
	public FileDescriptor lookupNameStart(String startingValue);
	public FileDescriptor lookupNameExpression(String regex);
	
	
	public void searchForName(String value, List<FileDescriptor> results);
	public List<FileDescriptor> searchForName(String value);
	
	public void searchForNameStart(String startingValue, List<FileDescriptor> results);
	public List<FileDescriptor> searchForNameStart(String startingValue);
	
	public void searchForNameExpression(String regex, List<FileDescriptor> results);
	public List<FileDescriptor> searchForNameExpression(String regex);

	
	public boolean containsFile(FileDescriptor fd);
	
	
	public interface FileDescriptor {
		
		public String getName();
		public IUniformLocation getLocation();
	}
	
}
