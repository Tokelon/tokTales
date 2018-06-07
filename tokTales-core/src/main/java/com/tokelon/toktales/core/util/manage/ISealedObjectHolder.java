package com.tokelon.toktales.core.util.manage;

/** Holds a number of objects without enabling modification.
 *
 * @param <T> The type of objects.
 */
public interface ISealedObjectHolder<T> {


	/** Returns whether the given object is managed by this manager.
	 * 
	 * @param object
	 * @return True if the object is managed, false if not.
	 */
	public boolean has(T object);
	
	//public int count();
	//public Iterator<T> iterator();
	
	
	/** Holds a number of objects.
	 *
	 * @param <T> The type of objects.
	 */
	public interface IObjectHolder<T> extends ISealedObjectHolder<T> {

		
		/** Adds the given object to this manager.
		 * 
		 * @param object
		 * @return True if the object was added, false if it was already managed.
		 */
		public boolean add(T object);

		
		/** Removes the given object from this manager.
		 * 
		 * @param object
		 * @return True if the object was managed, false if it was not.
		 */
		public boolean remove(T object);
		
		//public void clear();
	}
	
}
