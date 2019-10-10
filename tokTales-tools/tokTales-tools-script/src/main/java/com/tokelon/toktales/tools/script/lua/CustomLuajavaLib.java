package com.tokelon.toktales.tools.script.lua;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.jse.LuajavaLib;

public class CustomLuajavaLib extends LuajavaLib {
	
	
	/* opcodes from LuajavaLib */
	private static final int INIT           = 0;
	private static final int BINDCLASS      = 1;
	private static final int NEWINSTANCE	= 2;
	private static final int NEW			= 3;
	private static final int CREATEPROXY	= 4;
	private static final int LOADLIB		= 5;
	
	
	
	/* TODO: Fix that this object is loaded via reflection, which means that a new object is created
	 * and values in the blacklist and whitelist are not retained.
	 * 
	 * Which is why they are static.
	 */
	
	private static final Set<String> classnameWhitelistSet = Collections.synchronizedSet(new HashSet<String>());
	private static final Set<String> classnameBlacklistSet = Collections.synchronizedSet(new HashSet<String>());
	
	
	public static void addToWhitelist(String classname) {
		classnameWhitelistSet.add(classname);
	}
	
	public static void addToWhitelist(Collection<String> classnameCollection) {
		classnameWhitelistSet.addAll(classnameCollection);
	}
	
	public static void removeFromWhitelist(String classname) {
		classnameWhitelistSet.remove(classname);
	}
	
	public static void removeFromWhitelist(Collection<String> classnameCollection) {
		classnameWhitelistSet.removeAll(classnameCollection);
	}
	
	public static boolean isInWhitelist(String classname) {
		return classnameWhitelistSet.contains(classname);
	}
	
	
	public static void addToBlacklist(String classname) {
		classnameBlacklistSet.add(classname);
	}
	
	public static void addToBlacklist(Collection<String> classnameCollection) {
		classnameBlacklistSet.addAll(classnameCollection);
	}
	
	public static void removeFromBlacklist(String classname) {
		classnameBlacklistSet.remove(classname);
	}
	
	public static void removeFromBlacklist(Collection<String> classnameCollection) {
		classnameBlacklistSet.removeAll(classnameCollection);
	}
	
	public static boolean isInBlacklist(String classname) {
		return classnameBlacklistSet.contains(classname);
	}
	
	
	
	
	@Override
	public Varargs invoke(Varargs args) {
		switch ( opcode ) {
		case INIT:
			// Nothing to filter
			break;
		case BINDCLASS:
			String cnBind = args.checkjstring(1);
			
			if(!filter(cnBind)) {
				throw new LuaError(new ClassNotFoundException(cnBind));
			}
			break;
		case NEWINSTANCE:
			LuaValue cNewInstance = args.checkvalue(1);
			String cnNewInstance = cNewInstance.tojstring();
			
			if(!filter(cnNewInstance)) {
				throw new LuaError(new ClassNotFoundException(cnNewInstance));
			}
			break;
		case NEW:
			LuaValue cNew = args.checkvalue(1);
			Class clazz = (Class) cNew.checkuserdata(Class.class);
			String cnNew = clazz.getName(); 
			
			if(!filter(cnNew)) {
				throw new LuaError(new ClassNotFoundException(cnNew));
			}
			break;
		case CREATEPROXY:
			for(int i = 0; i < args.narg()-1; i++) {
				String cn = args.checkjstring(i+1); 
				
				if(!filter(cn)) {
					throw new LuaError(new ClassNotFoundException(cn));
				}
			}
			break;
		case LOADLIB:
			String cnLoadLib = args.checkjstring(1);
			
			if(!(filter(cnLoadLib))) {
				throw new LuaError(new ClassNotFoundException(cnLoadLib));
			}
			break;
		default:
			// Nothing to filter
		}
		
		
		return super.invoke(args);
	}

	
	public static boolean filter(String classname) {
	
		synchronized (classnameBlacklistSet) {
		
			for(String cn: classnameBlacklistSet) {
				if(classname.contains(cn)) {
					return false;
				}
			}
		}
		
		synchronized (classnameWhitelistSet) {
			
			for(String cn: classnameWhitelistSet) {
				if(classname.contains(cn)) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	
	
	
	
	/* Overriding this method is necessary to fix a bug that causes every call to luajava.newInstance or luajava.bindClass,
	 * that is directed to a NON Java System Library to fire a ClassNotFoundException.
	 * 
	 * (At least under Android)
	 *  
	 */
	@SuppressWarnings("rawtypes")	// Suppress original warning
	@Override
	protected Class classForName(String name) throws ClassNotFoundException {
		//return Class.forName(name, true, ClassLoader.getSystemClassLoader());
		return Class.forName(name, true, Thread.currentThread().getContextClassLoader());
	}
	

	/* Original method (version 3.0.1)
	 * 
	// load classes using app loader to allow luaj to be used as an extension
	protected Class classForName(String name) throws ClassNotFoundException {
		return Class.forName(name, true, ClassLoader.getSystemClassLoader());
	}
	*/
	
	
}
