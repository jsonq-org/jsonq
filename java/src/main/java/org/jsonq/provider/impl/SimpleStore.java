package org.jsonq.provider.impl;

import org.jsonq.*;
import org.jsonq.provider.*;
import org.jsonq.util.*;
import java.util.*;

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
	 * Retrieves an item by its ID
	 *
	 * @param request the valid JSON/q request
	 *
	 * @return a Future representing the result of the fetch operation
	 */
	@Override
	public Future<JSONObject,JSONObject> fetch( JSONObject request ) {
		FutureImpl<JSONObject,JSONObject> future = new FutureImpl<>();
		Scheduler.runAsync( new FetchCommand( future, request ) );
		return future;
	}

	/**
	 * Delete an item by its ID
	 *
	 * @param request the valid JSON/q request
	 *
	 * @return a Future representing the result of the delete operation
	 */
	@Override
	public Future<Void,JSONObject> delete( JSONObject request ) {
		FutureImpl<Void,JSONObject> future = new FutureImpl<>();
		Scheduler.runAsync( new DeleteCommand( future, request ) );
		return future;
	}

	/**
	 * List all the items in a given store
	 *
	 * @param request the valid JSON/q request
	 *
	 * @return a Future representing the result of the list operation
	 */
	@Override
	public Future<List<JSONObject>,JSONObject> list( JSONObject request ) {
		FutureImpl<List<JSONObject>,JSONObject> future = new FutureImpl<>();
		Scheduler.runAsync( new ListCommand( future, request ) );
		return future;
	}

	//----------------------------------------
	// Commands
	//----------------------------------------

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
				id = org.jsonq.util.UUID.uuid();
				payload.put( _idField, id );
			}

			// TODO: validate against schema
			_map.put( id, payload );
			complete( id );
		}
	}

	/**
	 * Runnable for fetching an object
	 */
	public class FetchCommand extends Command<JSONObject> {

		/**
		 * Constructor 
		 */
		protected FetchCommand( FutureImpl<JSONObject,JSONObject> future, JSONObject request ) {
			super( future, request );
		}

		/**
		 * Called to execute this command
		 */
		public void run() {
			String id = _request.getString( Request.PAYLOAD );
			complete( _map.getObject( id ));
		}
	}

	/**
	 * Runnable for deleting an object
	 */
	public class DeleteCommand extends Command<Void> {

		/**
		 * Constructor 
		 */
		protected DeleteCommand( FutureImpl<Void,JSONObject> future, JSONObject request ) {
			super( future, request );
		}

		/**
		 * Called to execute this command
		 */
		public void run() {
			String id = _request.getString( Request.PAYLOAD );
			_map.remove( id );
			complete( null );
		}
	}

	/**
	 * Runnable for listing all objects in the store
	 */
	public class ListCommand extends Command<List<JSONObject>> {

		/**
		 * Constructor 
		 */
		protected ListCommand( FutureImpl<List<JSONObject>,JSONObject> future, JSONObject request ) {
			super( future, request );
		}

		/**
		 * Called to execute this command
		 */
		public void run() {
			List<JSONObject> list = new ArrayList<>( _map.size() );
			for ( String key : _map.getKeys()) {
				list.add( _map.getObject( key ) );
			}
			complete( list );
		}
	}
}
