package org.jsonq.provider.impl;

import org.jsonq.*;
import org.jsonq.provider.*;
import org.jsonq.util.*;

import static org.jsonq.JSONQConstants.*;

/**
 * API for a virtual store provider
 */
public class SimpleStore implements Store {

	public static final StoreFactory<SimpleStore> FACTORY = new Factory();

	/**
	 * Factory for creating SimpleStores
	 */
	private static class Factory implements StoreFactory<SimpleStore> {

		public Future<SimpleStore,JSONObject> create( JSONObject schema ) {
			FutureImpl<SimpleStore,JSONObject> future = new FutureImpl<>();

			// TODO: schema shit
			future.complete( new SimpleStore( schema ) );
			return future;
		}
	}

	protected final JSONObject _map;
	protected final String _key;
	protected final JSONObject _schema;

	/**
	 * Protected Constructor. Only subclasses and the factory should be able to create a SimpleStore
	 */
	protected SimpleStore( JSONObject schema ) {
		_map = JSONObject.create();
		_schema = schema;
		String key = _schema.getString( Schema.KEY );
		if ( null == key ) {
			key = Schema.DEFAULT_KEY;
		}
		_key = key;
	}

	/**
	 * Save the given object
	 *
	 * @param request the valid JSON/q request
	 *
	 * @return a Future representing the eventual result of the save
	 */
	public Future<JSONObject,JSONObject> save( JSONObject request ) {
		FutureImpl<JSONObject,JSONObject> future = new FutureImpl<>();
		Scheduler.runAsync( new SaveCommand( future, request ) );
		return future;
	}

	/**
	 * Runnable for saving an object
	 */
	public class SaveCommand extends Command {

		/**
		 * Constructor 
		 */
		protected SaveCommand( FutureImpl<JSONObject,JSONObject> future, JSONObject request ) {
			super( future, request );
		}

		/**
		 * Called to execute this command
		 */
		public void run() {
			JSONObject payload = _request.getObject( Request.PAYLOAD );

			String key = payload.getString( _key );
			if ( null == key ) {
				key = UUID.uuid();
				payload.put( _key, key );
			}

			// TODO: validate against schema
			_map.put( key, payload );

			JSONObject response = JSONObject.create();
			response.put( _key, key );
			complete( response );
		}
	}

}
