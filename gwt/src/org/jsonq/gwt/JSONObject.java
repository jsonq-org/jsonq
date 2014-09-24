package org.jsonq.gwt;

import com.google.gwt.core.client.*;
import java.util.*;

/**
 * Interface for a representation of a JSON object
 */
public class JSONObject extends JavaScriptObject implements org.jsonq.JSONObject {

	/**
	 * Create a new JSONObject
	 */
	public static native JSONObject create() /*-{
		return new Object();
	}-*/;

	/**
	 * Returns all the keys in this JsonObject
	 *
	 * @return  an (unmodifiable) set of all the keys in the map
	 */
	@Override
	public Set<String> getKeys() {
		// TODO: implement
		return Collections.emptySet();
	}

	/**
	 * Returns the number of objects in this map
	 *
	 * @return  a count of the number of keys in this map
	 */
	@Override
	public native int size() /*-{
		return Object.keys(this).length;
	}-*/;

	/**
	 * Clears all values from this map
	 */
	@Override
	public void clear() {
		throw new IllegalStateException();
	}

	/**
	 * Returns true if the given key is contained in this object
	 *
	 * @param   key  the key to check for
	 *
	 * @return  true if the key is contained in this object
	 */
	@Override
	public native boolean containsKey( String key ) /*-{
		return this.hasOwnProperty(key);
	}-*/;

	/**
	 * Remove a value from this map
	 *
	 * @param  key  the key which should be removed
	 */
	@Override
	public native void remove( String key ) /*-{
		delete this[key];
	}-*/;

	/**
	 * Add the given JsonObject to the given key
	 *
	 * @param   key    the key
	 * @param   value  the value to add to the key
	 *
	 * @throws  NullPointerException  if the key is null
	 */
	@Override
	public native void add( String key, org.jsonq.JSONObject value ) /*-{
		var current = this[key];
		if ( typeof current == 'undefined' ) {
			this[key] = value;
		} else if ( current instanceof Array ) {
			current.push(value);
		} else {
			var ar = new Array();
			ar.push(current);
			ar.push(value);
		}
	}-*/;

	/**
	 * Adds a boolean to the given key
	 *
	 * @param   key    the key
	 * @param   value  the value to add to the key
	 *
	 * @throws  NullPointerException  if the key is null
	 */
	@Override
	public native void add( String key, boolean value ) /*-{
		var current = this[key];
		if ( typeof current == 'undefined' ) {
			this[key] = value;
		} else if ( current instanceof Array ) {
			current.push(value);
		} else {
			var ar = new Array();
			ar.push(current);
			ar.push(value);
		}
	}-*/;

	/**
	 * Adds a integer to the given key
	 *
	 * @param   key    the key
	 * @param   value  the value to add to the key
	 *
	 * @throws  NullPointerException  if the key is null
	 */
	@Override
	public native void add( String key, int value ) /*-{
		var current = this[key];
		if ( typeof current == 'undefined' ) {
			this[key] = value;
		} else if ( current instanceof Array ) {
			current.push(value);
		} else {
			var ar = new Array();
			ar.push(current);
			ar.push(value);
		}
	}-*/;

	/**
	 * Adds a long to the given key
	 *
	 * @param   key    the key
	 * @param   value  the value to add to the key
	 *
	 * @throws  NullPointerException  if the key is null
	 */
	@Override
	public native void add( String key, long value ) /*-{
		var current = this[key];
		if ( typeof current == 'undefined' ) {
			this[key] = value;
		} else if ( current instanceof Array ) {
			current.push(value);
		} else {
			var ar = new Array();
			ar.push(current);
			ar.push(value);
		}
	}-*/;

	/**
	 * Adds a double to the given key
	 *
	 * @param   key    the key
	 * @param   value  the value to add to the key
	 *
	 * @throws  NullPointerException  if the key is null
	 */
	@Override
	public native void add( String key, double value ) /*-{
		var current = this[key];
		if ( typeof current == 'undefined' ) {
			this[key] = value;
		} else if ( current instanceof Array ) {
			current.push(value);
		} else {
			var ar = new Array();
			ar.push(current);
			ar.push(value);
		}
	}-*/;

	/**
	 * Adds a String to the given key
	 *
	 * @param   key    the key
	 * @param   value  the value to add to the key
	 *
	 * @throws  NullPointerException  if the key is null
	 */
	@Override
	public native void add( String key, String value ) /*-{
		var current = this[key];
		if ( typeof current == 'undefined' ) {
			this[key] = value;
		} else if ( current instanceof Array ) {
			current.push(value);
		} else {
			var ar = new Array();
			ar.push(current);
			ar.push(value);
		}
	}-*/;

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
	@Override
	public native void put( String key, org.jsonq.JSONObject value ) /*-{
		this[key] = value;
	}-*/;

	/**
	 * Store a boolean
	 *
	 * @param   key    the key
	 * @param   value  the new value for the key
	 *
	 * @throws  NullPointerException  if the key is null
	 */
	@Override
	public native void put( String key, boolean value ) /*-{
		this[key] = value;
	}-*/;

	/**
	 * Store an integer
	 *
	 * @param   key    the key
	 * @param   value  the new value for the key
	 *
	 * @throws  NullPointerException  if the key is null
	 */
	@Override
	public native void put( String key, int value ) /*-{
		this[key] = value;
	}-*/;

	/**
	 * Store a long
	 *
	 * @param   key    the key
	 * @param   value  the new value for the key
	 *
	 * @throws  NullPointerException  if the key is null
	 */
	@Override
	public native void put( String key, long value ) /*-{
		this[key] = value;
	}-*/;

	/**
	 * Store a float
	 *
	 * @param   key    the key
	 * @param   value  the new value for the key
	 *
	 * @throws  NullPointerException  if the key is null
	 */
	@Override
	public native void put( String key, float value ) /*-{
		this[key] = value;
	}-*/;

	/**
	 * Store a double
	 *
	 * @param   key    the key
	 * @param   value  the new value for the key
	 *
	 * @throws  NullPointerException  if the key is null
	 */
	@Override
	public native void put( String key, double value ) /*-{
		this[key] = value;
	}-*/;

	/**
	 * Store a String
	 *
	 * @param   key    the key
	 * @param   value  the new value for the key
	 *
	 * @throws  NullPointerException  if the key is null
	 */
	@Override
	public native void put( String key, String value ) /*-{
		this[key] = value;
	}-*/;

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
	@Override
	public native void put( String key, List<?> values ) /*-{
		var ar = new Array();
		var len = values.@java.util.List::size()();
		for (var i=0; i<len; i++) {
			ar.push(values.@java.util.List::get(I)(i));
		}
		this[key] = ar;
	}-*/;

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
	public <T> T getSingle( String key ) {
		throw new IllegalStateException();
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
	public List get( String key ) {
		throw new IllegalStateException();
	}

	// ----------------------------------------
	// Convenience typed accessors for JsonObjects
	// ----------------------------------------

	/**
	 * Returns a String for the given key
	 *
	 * @throws  IllegalArgumentException if the value at the given key is not a String
	 */
	@Override
	public native String getString( String key ) /*-{
		var val = this[key];
		var t = typeof val;
		if (t == 'string') {
			return val;
		} elseif (t == 'undefined') {
			return null;
		}
		throw @java.lang.IllegalArgumentException::new(Ljava/lang/String;)(key+" does not contain a string");
	}-*/;

	/**
	 * Returns an integer for the given key
	 *
	 * @throws  IllegalArgumentException if the value at the given key is not an integer
	 */
	@Override
	public native int getInt( String key ) /*-{
		var val = this[key];
		var t = typeof val;
		if (t == 'number') {
			return val;
		} elseif (t == 'undefined') {
			return 0;
		}
		throw @java.lang.IllegalArgumentException::new(Ljava/lang/String;)(key+" does not contain an integer");
	}-*/;
	
	/**
	 * Returns a long value for the given key
	 *
	 * @throws  IllegalArgumentException if the value at the given key is not a long
	 */
	@Override
	public native long getLong( String key ) /*-{
		var val = this[key];
		var t = typeof val;
		if (t == 'number') {
			return val;
		} elseif (t == 'undefined') {
			return 0;
		}
		throw @java.lang.IllegalArgumentException::new(Ljava/lang/String;)(key+" does not contain a long");
	}-*/;
	
	/**
	 * Returns a double value for the given key
	 *
	 * @throws  IllegalArgumentException if the value at the given key is not a double
	 */
	@Override
	public native double getDouble( String key ) /*-{
		var val = this[key];
		var t = typeof val;
		if (t == 'number') {
			return val;
		} elseif (t == 'undefined') {
			return 0.0;
		}
		throw @java.lang.IllegalArgumentException::new(Ljava/lang/String;)(key+" does not contain a double");
	}-*/;

	/**
	 * Convenience method to return a boolean for the given key. If nothing exists at the given key,
	 * returns null
	 *
	 * @param  key  the key to fetch
	 *
	 * @throws  IllegalArgumentException if the value at the given key is not a boolean
	 */
	@Override
	public native boolean getBoolean( String key ) /*-{
		var val = this[key];
		var t = typeof val;
		if (t == 'boolean') {
			return val;
		} elseif (t == 'undefined') {
			return false;
		}
		throw @java.lang.IllegalArgumentException::new(Ljava/lang/String;)(key+" does not contain a boolean");
	}-*/;

}
