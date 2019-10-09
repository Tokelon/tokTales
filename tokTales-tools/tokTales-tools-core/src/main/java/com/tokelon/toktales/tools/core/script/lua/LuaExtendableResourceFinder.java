package com.tokelon.toktales.tools.core.script.lua;

import java.io.InputStream;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.luaj.vm2.lib.ResourceFinder;

import com.tokelon.toktales.tools.core.script.IExtendableResourceFinder;
import com.tokelon.toktales.tools.core.script.IResourceFinder;

public class LuaExtendableResourceFinder implements IExtendableResourceFinder, ResourceFinder {

	
	private final Set<IResourceFinder> resourceFinderSet;
	
	public LuaExtendableResourceFinder() {
		resourceFinderSet = Collections.synchronizedSet(new HashSet<IResourceFinder>());
	}
	
	
	@Override
	public void addResourceFinder(IResourceFinder finder) {
		resourceFinderSet.add(finder);
	}
	
	@Override
	public void removeResourceFinder(IResourceFinder finder) {
		resourceFinderSet.remove(finder);
	}
	
	@Override
	public boolean hasResourceFinder(IResourceFinder finder) {
		return resourceFinderSet.contains(finder);
	}
	
	
	
	@Override
	public InputStream findResource(String filename) {
		
		synchronized (resourceFinderSet) {
			
			for(IResourceFinder rf: resourceFinderSet) {
				InputStream stream = rf.findResource(filename);
				if(stream != null) {
					return stream;
				}
			}
		}
		
		return null;
	}

	
	
}
