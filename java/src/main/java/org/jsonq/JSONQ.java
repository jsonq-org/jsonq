package org.jsonq;

import org.jsonq.provider.*;
import org.jsonq.util.*;
import static org.jsonq.JSONQConstants.*;

/**
 * Singleton class for JSON/q functionality
 */
public final class JSONQ {

	private static Database _db;
	private static JSONObjectFactory _joFactory;

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
	 * Sets the factory implementation for creating JSONObjects
	 *
	 * @throws IllegalStateException if the factory has already been set
	 */
	public static synchronized void setJSONObjectFactory( JSONObjectFactory factory ) {
		if ( null != _joFactory ) {
			throw new IllegalStateException( "JSONObject factory has already been set" );
		}
		if ( null == factory ) {
			throw new NullPointerException( "factory cannot be null" );
		}
		_joFactory = factory;
	}

	/**
	 * Throws an Exception if the database has not been set
	 */
	private static synchronized void ensureInitialized() {
		if ( null == _db || null == _joFactory ) {
			throw new IllegalStateException( "JSON/q has not been initialized" );
		}
	}

	/**
	 * Creates a JSONObject
	 */
	public static JSONObject createObject() {
		ensureInitialized();
		return _joFactory.create();
	}

	/**
	 * Execute a JSON/q command
	 *
	 * @param obj the JSON object representing a JSON/q command
	 *
	 * @return a future for sampling results
	 *
	 * @throws IllegalArgumentException if the request is not valid
	 */
	public static Future<JSONObject,JSONObject> exec( JSONObject obj ) {
		ensureInitialized();

		// validate request
		String id = obj.getString( ""+Request.ID );
		if ( null == id ) {
			throw new IllegalArgumentException( Request.ID+" is required" );
		}

		String opStr = obj.getString( ""+Request.OP );
		Op op = null;
		try {
			op = ( null == opStr ? null : Op.valueOf( opStr ));
		} catch ( IllegalArgumentException e ) {
			throw new IllegalArgumentException( "No JSON/q operation "+opStr );
		}
		if ( null == op ) {
			throw new IllegalArgumentException( Request.OP+" is required" );
		}

		String store = obj.getString( ""+Request.STORE );
		if ( null == store ) {
			throw new IllegalArgumentException( Request.STORE+" is required" );
		}

		if ( ! obj.containsKey( ""+Request.PAYLOAD ) ) {
			throw new IllegalArgumentException( Request.PAYLOAD+" is required" );
		}

		switch ( op ) {
			case PROVISION: return _db.provision( obj );
			case SAVE: return _db.save( obj );
			default: throw new IllegalStateException( "Unhandled operation: "+op );
		}
	}
}
