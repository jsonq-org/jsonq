package org.jsonq;

import java.util.*;

/**
 * Interface for a representation of a JSON object
 */
public final class JSONObject {

	private final Map<String,List> _map;

	/**
	 * Private constructor. JSONObject must be created using the static generator. 
	 */
	private JSONObject() {
		_map = new HashMap<>(32);
	}

	/**
	 * Creates a new JSONObject
	 */
	public static JSONObject create() {
		return new JSONObject();
	}

	/**
	 * Returns all the keys in this JsonObject
	 *
	 * @return  an (unmodifiable) set of all the keys in the map
	 */
	public Set<String> getKeys() {
		return Collections.unmodifiableSet( _map.keySet() );
	}

	/**
	 * Returns the number of objects in this map
	 *
	 * @return  a count of the number of keys in this map
	 */
	public int size() {
		return _map.size();
	}

	/**
	 * Clears all values from this map
	 */
	public void clear() {
		_map.clear();
	}

	/**
	 * Returns true if the given key is contained in this object
	 *
	 * @param   key  the key to check for
	 *
	 * @return  true if the key is contained in this object
	 */
	public boolean containsKey( String key ) {
		return _map.containsKey( key );
	}

	/**
	 * Remove a value from this map
	 *
	 * @param  key  the key which should be removed
	 */
	public void remove( String key ) {
		_map.remove( key );
	}

	/**
	 * Add the given JsonObject to the given key
	 *
	 * @param   key    the key
	 * @param   value  the value to add to the key
	 *
	 * @throws  NullPointerException  if the key is null
	 */
	public void add( String key, JSONObject value ) {
		doAdd( key, value );
	}

	/**
	 * Adds a boolean to the given key
	 *
	 * @param   key    the key
	 * @param   value  the value to add to the key
	 *
	 * @throws  NullPointerException  if the key is null
	 */
	public void add( String key, boolean value ) {
		doAdd( key, value );
	}

	/**
	 * Adds a integer to the given key
	 *
	 * @param   key    the key
	 * @param   value  the value to add to the key
	 *
	 * @throws  NullPointerException  if the key is null
	 */
	public void add( String key, int value ) {
		doAdd( key, value );
	}

	/**
	 * Adds a double to the given key
	 *
	 * @param   key    the key
	 * @param   value  the value to add to the key
	 *
	 * @throws  NullPointerException  if the key is null
	 */
	public void add( String key, double value ) {
		doAdd( key, value );
	}

	/**
	 * Adds a String to the given key
	 *
	 * @param   key    the key
	 * @param   value  the value to add to the key
	 *
	 * @throws  NullPointerException  if the key is null
	 */
	public void add( String key, String value ) {
		doAdd( key, value );
	}

	/**
	 * Aggregate method to add the given value to the key
	 */
	@SuppressWarnings("unchecked")
	private void doAdd( String key, Object value ) {
		if ( null == key ) {
			throw new NullPointerException( "key cannot be null" );
		}
		synchronized ( _map ) {
			List values = _map.get( key );
			if ( null == values ) {
				values = new ArrayList<>(3);
				_map.put( key, values );
			}
			values.add( value );
		}
	}

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
	public void put( String key, JSONObject value ) {
		doPut( key, value );
	}

	/**
	 * Store a boolean
	 *
	 * @param   key    the key
	 * @param   value  the new value for the key
	 *
	 * @throws  NullPointerException  if the key is null
	 */
	public void put( String key, boolean value ) {
		doPut( key, value );
	}

	/**
	 * Store an integer
	 *
	 * @param   key    the key
	 * @param   value  the new value for the key
	 *
	 * @throws  NullPointerException  if the key is null
	 */
	public void put( String key, int value ) {
		doPut( key, value );
	}

	/**
	 * Store a float
	 *
	 * @param   key    the key
	 * @param   value  the new value for the key
	 *
	 * @throws  NullPointerException  if the key is null
	 */
	public void put( String key, float value ) {
		doPut( key, value );
	}

	/**
	 * Store a double
	 *
	 * @param   key    the key
	 * @param   value  the new value for the key
	 *
	 * @throws  NullPointerException  if the key is null
	 */
	public void put( String key, double value ) {
		doPut( key, value );
	}

	/**
	 * Store a String
	 *
	 * @param   key    the key
	 * @param   value  the new value for the key
	 *
	 * @throws  NullPointerException  if the key is null
	 */
	public void put( String key, String value ) {
		doPut( key, value );
	}

	/**
	 * Aggregate method to replace the value at the given key
	 */
	@SuppressWarnings("unchecked")
	private void doPut( String key, Object value ) {
		if ( null == key ) {
			throw new NullPointerException( "key cannot be null" );
		}
		synchronized ( _map ) {
			List values = new ArrayList<>(3);
			values.add( value );
			_map.put( key, values );
		}
	}

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
	public void put( String key, List values ) {
		if ( null == key ) {
			throw new NullPointerException( "key cannot be null" );
		}
		synchronized ( _map ) {
			_map.put( key, values );
		}
	}

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
	@SuppressWarnings("unchecked")
	public <T> T getSingle( String key ) {
		if ( null == key ) {
			throw new NullPointerException( "key cannot be null" );
		}
		synchronized ( _map ) {
			List values = _map.get( key );
			if ( values.isEmpty() ) {
				return null;
			} else if ( values.size() > 1 ) {
				throw new IllegalArgumentException( "Multiple values for "+key );
			}
			return (T)values.get(0);
		}
	}

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
	@SuppressWarnings("unchecked")
	public List get( String key ) {
		if ( null == key ) {
			throw new NullPointerException( "key cannot be null" );
		}
		synchronized ( _map ) {
			List values = _map.get( key );
			if ( null == values ) {
				return Collections.emptyList();
			}
			return Collections.unmodifiableList( values );
		}
	}

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
	public String getString( String key ) {
		Object o = getSingle( key );
		if ( null == o ) {
			return null;
		}
		if ( ! (o instanceof String) ) {
			throw new IllegalArgumentException( key+ " does not contain a String" );
		}
		return (String)o;
	}

	/**
	 * Returns an integer for the given key
	 *
	 * @param  key  the key to fetch
	 *
	 * @throws  IllegalArgumentException if the value at the given key is not an integer
	 */
	public int getInt( String key ) {
		Object o = getSingle( key );
		if ( null == o ) {
			return 0;
		}
		if ( ! (o instanceof Integer) ) {
			throw new IllegalArgumentException( key+ " does not contain an integer" );
		}
		return (int)o;
	}

	/**
	 * Returns a double value for the given key
	 *
	 * @param  key  the key to fetch
	 *
	 * @throws  IllegalArgumentException if the value at the given key is not a double
	 */
	public double getDouble( String key ) {
		Object o = getSingle( key );
		if ( null == o ) {
			return 0;
		}
		if ( ! (o instanceof Double) ) {
			throw new IllegalArgumentException( key+ " does not contain a double" );
		}
		return (double)o;
	}


	/**
	 * Returns a boolean value for the given key
	 *
	 * @param  key  the key to fetch
	 *
	 * @throws  IllegalArgumentException if the value at the given key is not a boolean
	 */
	public boolean getBoolean( String key ) {
		Object o = getSingle( key );
		if ( null == o ) {
			return false;
		}
		if ( ! (o instanceof Boolean) ) {
			throw new IllegalArgumentException( key+ " does not contain a boolean" );
		}
		return (boolean)o;
	}


	/**
	 * Returns an object value for the given key
	 *
	 * @param  key  the key to fetch
	 *
	 * @throws  IllegalArgumentException if the value at the given key is not an object
	 */
	public JSONObject getObject( String key ) {
		Object o = getSingle( key );
		if ( null == o ) {
			return null;
		}
		if ( ! (o instanceof JSONObject) ) {
			throw new IllegalArgumentException( key+ " does not contain an object" );
		}
		return (JSONObject)o;
	}


}
