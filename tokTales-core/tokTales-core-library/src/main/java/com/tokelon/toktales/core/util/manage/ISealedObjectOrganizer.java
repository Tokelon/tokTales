package com.tokelon.toktales.core.util.manage;

/** Organizes a number of keyed objects without enabling modification.
 * 
 * @param <T> The type of objects.
 * @param <K> The type of keys.
 */
public interface ISealedObjectOrganizer<T, K> {


	/** Returns the object for the given key.
	 * 
	 * @param name
	 * @return The object assigned to the key.
	 */
	public T get(K key);
	
	/** Returns whether there is an object for the given key.
	 * 
	 * @param name
	 * @return True if there is an object assigned to the key, false if not. 
	 */
	public boolean has(K key);

	//public int count();
	//public Iterator<T> iterator();

	
	/** Organizes a number of keyed objects.
	 *
	 * @param <T> The type of objects.
	 * @param <K> The type of keys.
	 */
	public interface IObjectOrganizer<T, K> extends ISealedObjectOrganizer<T, K> {

		/** Adds the given object with the given key.
		 * 
		 * @param name
		 * @param object
		 * @return The object previously assigned to the key, or null if there was none.
		 */
		public T add(K key, T object);
		

		/** Removes the object for the given key.
		 * 
		 * @param name
		 * @return The object previously assigned to the key, or null if there was none.
		 */
		public T remove(K key);
		
		//public void clear();
	}
	
}
