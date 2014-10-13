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
	 *
	 * @throws IllegalArgumentException if the request is not valid
	 */
	public static Future<JSONObject,JSONObject> exec( JSONObject obj ) {
		ensureInitialized();

		// validate request
		String id = obj.getString( Request.ID );
		if ( null == id ) {
			throw new IllegalArgumentException( Request.ID+" is required" );
		}

		String op = obj.getString( Request.OP );
		if ( null == op ) {
			throw new IllegalArgumentException( Request.OP+" is required" );
		}

		String store = obj.getString( Request.STORE );
		if ( null == store ) {
			throw new IllegalArgumentException( Request.STORE+" is required" );
		}

		if ( ! obj.containsKey( Request.PAYLOAD ) ) {
			throw new IllegalArgumentException( Request.PAYLOAD+" is required" );
		}

		if ( Op.PROVISION.equals( op ) ) {
			return _db.provision( obj );
		} else if ( Op.SAVE.equals( op ) ) {
			return _db.save( obj );
		} else if ( Op.FETCH.equals( op ) ) {
			return _db.fetch( obj );
		} else if ( Op.DELETE.equals( op ) ) {
			return _db.delete( obj );
		} else if ( Op.LIST.equals( op ) ) {
			return _db.list( obj );
		} else {
			throw new IllegalStateException( "Unhandled operation: "+op );
		}
	}
}
