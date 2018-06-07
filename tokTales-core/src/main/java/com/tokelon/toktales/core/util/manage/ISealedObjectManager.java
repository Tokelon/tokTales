package com.tokelon.toktales.core.util.manage;

/** Manages a number of named objects without enabling modification.
 * 
 * @param <T> The type of objects.
 */
public interface ISealedObjectManager<T> {

	
	/** Returns the object for the given name.
	 * 
	 * @param name
	 * @return The object assigned to the name.
	 */
	public T get(String name);
	
	/** Returns whether there is an object for the given name.
	 * 
	 * @param name
	 * @return True if there is an object assigned to the name, false if not. 
	 */
	public boolean has(String name);

	//public int count();
	//public Iterator<T> iterator();

	
	/** Manages a number of named objects.
	 *
	 * @param <T> The type of objects.
	 */
	public interface IObjectManager<T> extends ISealedObjectManager<T> {

		/** Adds the given object with the given name.
		 * 
		 * @param name
		 * @param object
		 * @return The object previously assigned to the name, or null if there was none.
		 */
		public T add(String name, T object);
		

		/** Removes the object for the given name.
		 * 
		 * @param name
		 * @return The object previously assigned to the name, or null if there was none.
		 */
		public T remove(String name);
		
		//public void clear();
	}
	
}
