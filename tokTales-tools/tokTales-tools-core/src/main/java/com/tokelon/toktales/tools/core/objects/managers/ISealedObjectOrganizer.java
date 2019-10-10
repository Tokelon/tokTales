package com.tokelon.toktales.tools.core.objects.managers;

import java.util.Map;

/** Organizes a number of keyed objects without enabling modification.
 * 
 * @param <T> The type of objects.
 * @param <K> The type of keys.
 */
public interface ISealedObjectOrganizer<K, T> {
	// TODO: Add methods to other types as well

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
	//public boolean hasValue(T value);
	
	/**
	 * @return The number of objects contained.
	 */
	public int count();
	
	/**
	 * @return An unmodifiable map from keys to objects.
	 */
	public Map<K, T> getObjectMap();
	//public Iterator<T> iterator();
	//Stream stream();
	
	
	/** Organizes a number of keyed objects.
	 *
	 * @param <T> The type of objects.
	 * @param <K> The type of keys.
	 */
	public interface IObjectOrganizer<K, T> extends ISealedObjectOrganizer<K, T> {

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

		
		/** Removes all objects.
		 */
		public void empty();
	}
	
}
