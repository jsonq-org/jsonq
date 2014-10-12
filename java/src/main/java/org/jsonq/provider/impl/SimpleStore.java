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
	protected final String _idField;
	protected final JSONObject _schema;

	/**
	 * Protected Constructor. Only subclasses and the factory should be able to create a SimpleStore
	 */
	protected SimpleStore( JSONObject schema ) {
		_map = JSONObject.create();
		_schema = schema;
		String idField = _schema.getString( Schema.ID_FIELD );
		if ( null == idField ) {
			idField = Schema.DEFAULT_ID_FIELD;
		}
		_idField = idField;
	}

	/**
	 * Returns the ID field for this store
	 */
	@Override
	public String getIdField() {
		return _idField;
	}

	/**
	 * Save the given object
	 *
	 * @param request the valid JSON/q request
	 *
	 * @return a Future representing the eventual result of the save
	 */
	@Override
	public Future<String,JSONObject> save( JSONObject request ) {
		FutureImpl<String,JSONObject> future = new FutureImpl<>();
		Scheduler.runAsync( new SaveCommand( future, request ) );
		return future;
	}

	/**
	 * Runnable for saving an object
	 */
	public class SaveCommand extends Command<String> {

		/**
		 * Constructor 
		 */
		protected SaveCommand( FutureImpl<String,JSONObject> future, JSONObject request ) {
			super( future, request );
		}

		/**
		 * Called to execute this command
		 */
		public void run() {
			JSONObject payload = _request.getObject( Request.PAYLOAD );

			String id = payload.getString( _idField );
			if ( null == id ) {
				id = UUID.uuid();
				payload.put( _idField, id );
			}

			// TODO: validate against schema
			_map.put( id, payload );
			complete( id );
		}
	}

}
