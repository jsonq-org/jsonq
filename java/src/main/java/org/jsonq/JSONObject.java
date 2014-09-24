package org.jsonq;

import java.util.*;

/**
 * Interface for a representation of a JSON object
 */
public interface JSONObject {

	/**
	 * Returns all the keys in this JsonObject
	 *
	 * @return  an (unmodifiable) set of all the keys in the map
	 */
	public Set<String> getKeys();

	/**
	 * Returns the number of objects in this map
	 *
	 * @return  a count of the number of keys in this map
	 */
	public int size();

	/**
	 * Clears all values from this map
	 */
	public void clear();

	/**
	 * Returns true if the given key is contained in this object
	 *
	 * @param   key  the key to check for
	 *
	 * @return  true if the key is contained in this object
	 */
	public boolean containsKey( String key );

	/**
	 * Remove a value from this map
	 *
	 * @param  key  the key which should be removed
	 */
	public void remove( String key );

	/**
	 * Add the given JsonObject to the given key
	 *
	 * @param   key    the key
	 * @param   value  the value to add to the key
	 *
	 * @throws  NullPointerException  if the key is null
	 */
	public void add( String key, JSONObject value );

	/**
	 * Adds a boolean to the given key
	 *
	 * @param   key    the key
	 * @param   value  the value to add to the key
	 *
	 * @throws  NullPointerException  if the key is null
	 */
	public void add( String key, boolean value );

	/**
	 * Adds a integer to the given key
	 *
	 * @param   key    the key
	 * @param   value  the value to add to the key
	 *
	 * @throws  NullPointerException  if the key is null
	 */
	public void add( String key, int value );

	/**
	 * Adds a long to the given key
	 *
	 * @param   key    the key
	 * @param   value  the value to add to the key
	 *
	 * @throws  NullPointerException  if the key is null
	 */
	public void add( String key, long value );

	/**
	 * Adds a double to the given key
	 *
	 * @param   key    the key
	 * @param   value  the value to add to the key
	 *
	 * @throws  NullPointerException  if the key is null
	 */
	public void add( String key, double value );

	/**
	 * Adds a String to the given key
	 *
	 * @param   key    the key
	 * @param   value  the value to add to the key
	 *
	 * @throws  NullPointerException  if the key is null
	 */
	public void add( String key, String value );

	// ----------------------------------------
	// Single setters
	// ----------------------------------------

	/**
	 * Store a JSONObject to the given key
	 *
	 * @param   key    the key
	 * @param   value  the value to add to the key
	 *
	 * @throws  NullPointerException  if the key is null
	 */
	public void put( String key, JSONObject value );

	/**
	 * Store a boolean
	 *
	 * @param   key    the key
	 * @param   value  the new value for the key
	 *
	 * @throws  NullPointerException  if the key is null
	 */
	public void put( String key, boolean value );

	/**
	 * Store an integer
	 *
	 * @param   key    the key
	 * @param   value  the new value for the key
	 *
	 * @throws  NullPointerException  if the key is null
	 */
	public void put( String key, int value );

	/**
	 * Store a long
	 *
	 * @param   key    the key
	 * @param   value  the new value for the key
	 *
	 * @throws  NullPointerException  if the key is null
	 */
	public void put( String key, long value );

	/**
	 * Store a float
	 *
	 * @param   key    the key
	 * @param   value  the new value for the key
	 *
	 * @throws  NullPointerException  if the key is null
	 */
	public void put( String key, float value );

	/**
	 * Store a double
	 *
	 * @param   key    the key
	 * @param   value  the new value for the key
	 *
	 * @throws  NullPointerException  if the key is null
	 */
	public void put( String key, double value );

	/**
	 * Store a String
	 *
	 * @param   key    the key
	 * @param   value  the new value for the key
	 *
	 * @throws  NullPointerException  if the key is null
	 */
	public void put( String key, String value );

	// ----------------------------------------
	// Multi-setters
	// ----------------------------------------

	/**
	 * Store a list of objects for the given key. The list may contain any valid
	 * POJO which can be turned into a @see nuvos.core.json.JsonValue as well as
	 *
	 * @param   key    the key
	 * @param   value  the values to replace the current value with
	 *
	 * @throws  NullPointerException  if the key is null
	 *
	 * @see     nuvos.core.json.JsonObject or
	 * @see     nuvos.core.json.JsonValue. <b>JsonObjects contained in the list
	 *          will not be cloned by this implementation.</b>
	 */
	public void put( String key, List<?> values );

	// ----------------------------------------
	// Single accessors
	// ----------------------------------------

	/**
	 * Fetches a single value for the given key
	 *
	 * @param   key  the key to fetch a value for
	 *
	 * @return  the value stored at the key or null
	 *
	 * @throws  IllegalStateException  if there are multiple values for the key
	 * @throws  NullPointerException   if the key is null
	 */
	public <T> T getSingle( String key );

	// ----------------------------------------
	// Multi-accessors
	// ----------------------------------------

	/**
	 * Fetches the value for the given key. This method will always return an
	 * unmodifiable List or null
	 *
	 * @param   key  the key to fetch a value for
	 *
	 * @return  the value stored at the key or an empty list
	 *
	 * @throws  NullPointerException  if the key is null
	 */
	public List get( String key );

	// ----------------------------------------
	// Convenience typed accessors for JsonObjects
	// ----------------------------------------

	/**
	 * Returns a String for the given key
	 *
	 * @param  key  the key to fetch
	 *
	 * @throws  IllegalArgumentException if the value at the given key is not a String
	 */
	public String getString( String key );

	/**
	 * Returns an integer for the given key
	 *
	 * @param  key  the key to fetch
	 *
	 * @throws  IllegalArgumentException if the value at the given key is not an integer
	 */
	public int getInt( String key );
	
	/**
	 * Returns a long value for the given key
	 *
	 * @param  key  the key to fetch
	 *
	 * @throws  IllegalArgumentException if the value at the given key is not a long
	 */
	public long getLong( String key );
	
	/**
	 * Returns a double value for the given key
	 *
	 * @param  key  the key to fetch
	 *
	 * @throws  IllegalArgumentException if the value at the given key is not a double
	 */
	public double getDouble( String key );

	/**
	 * Returns a boolean value for the given key
	 *
	 * @param  key  the key to fetch
	 *
	 * @throws  IllegalArgumentException if the value at the given key is not a boolean
	 */
	public boolean getBoolean( String key );

}
