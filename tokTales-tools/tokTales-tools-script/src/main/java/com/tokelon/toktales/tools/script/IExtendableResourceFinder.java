package com.tokelon.toktales.tools.script;

public interface IExtendableResourceFinder extends IResourceFinder {

	public void addResourceFinder(IResourceFinder finder);
	
	public void removeResourceFinder(IResourceFinder finder);
	
	public boolean hasResourceFinder(IResourceFinder finder);
	
	
	
}
