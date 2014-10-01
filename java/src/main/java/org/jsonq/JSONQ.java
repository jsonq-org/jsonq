package org.jsonq;

import org.jsonq.provider.*;
import org.jsonq.util.*;
import static org.jsonq.JSONQConstants.*;

/**
 * Singleton class for JSON/q functionality
 */
public final class JSONQ {

	private static Database _db;

	/**
	 * Constructor 
	 */
	protected JSONQ() { }

	/**
	 * Sets the database implementation to use. This method must only be called once to initialize
	 * JSON/q
	 *
	 * @throws IllegalStateException if the database has already been set
	 */
	public static synchronized void setDatabase( Database db ) {
		if ( null != _db ) {
			throw new IllegalStateException( "Database has already been set" );
		}
		if ( null == db ) {
			throw new NullPointerException( "db cannot be null" );
		}
		_db = db;
	}

	/**
	 * Throws an Exception if the database has not been set
	 */
	private static synchronized void ensureInitialized() {
		if ( null == _db ) {
			throw new IllegalStateException( "JSON/q has not been initialized" );
		}
	}

	/**
	 * Execute a JSON/q command
	 *
	 * @param obj the JSON object representing a JSON/q command
	 *
	 * @return a future for sampling results
	 */
	public static Future<JSONObject> exec( JSONObject obj ) {
		ensureInitialized();

		return _db.save( null, obj );
	}

}
