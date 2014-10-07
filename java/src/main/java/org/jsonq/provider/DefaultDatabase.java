package org.jsonq.provider;

import org.jsonq.*;
import org.jsonq.util.*;
import java.util.*;

import static org.jsonq.JSONQConstants.*;

/**
 * Basic, generic implementation of the database API.
 */
public class DefaultDatabase implements Database {

	protected final Map<String,Store> _storeMap;
	protected final Map<String,StoreFactory> _providerMap;

	/**
	 * Constructor 
	 */
	public DefaultDatabase() {
		_storeMap = new HashMap<>(16);
		_providerMap = new HashMap<>(16);
	}

	/**
	 * Registers a new store provider with this Database.
	 *
	 * @param providerName the name of the store provider. This is used when provisioning a new
	 * store to determine which provider to use
	 * @param factory the factory which can create stores
	 */
	public void registerProvider( String providerName, StoreFactory factory ) {
		synchronized ( _providerMap ) {
			_providerMap.put( providerName, factory );
		}
	}

	/**
	 * Provisions a new store on this Database
	 *
	 * @param request the valid JSON/q request
	 *
	 * @return a Future representing the resultant JSON/q response
	 */
	public Future<JSONObject,JSONObject> provision( JSONObject request ) {
		FutureImpl<JSONObject,JSONObject> future = new FutureImpl<JSONObject,JSONObject>();
		Scheduler.runAsync( new ProvisionCommand( future, request ) );
		return future;
	}

	/**
	 * Saves an item
	 *
	 * @param request the valid JSON/q request
	 *
	 * @return a Future representing the result of the save operation
	 */
	public Future<JSONObject,JSONObject> save( JSONObject request ) {
		return null;
	}

	//----------------------------------------
	// Commands
	//----------------------------------------

	/**
	 * Base class for DB commands
	 */
	public abstract class Command implements Runnable {

		protected final FutureImpl<JSONObject,JSONObject> _future;
		protected final JSONObject _request;

		/**
		 * Constructor 
		 */
		protected Command( FutureImpl<JSONObject,JSONObject> future, JSONObject request ) {
			_future = future;
			_request = request;
		}

		/**
		 * Signals that this command succeeded. Creates and sends the response
		 *
		 * @param payload the result payload to send
		 */
		protected void complete( JSONObject payload ) {
			complete( payload, true );
		}

		/**
		 * Signals that this command failed. Creates and sends the response
		 *
		 * @param error the error payload
		 */
		protected void fail( JSONObject error ) {
			complete( error, false );
		}

		/**
		 * Signals that this command failed. Creates and sends the response
		 *
		 * @param code the error code
		 * @param message the error message
		 * @param args any arguments which need to be added
		 */
		protected void fail( String code, String message, String... args ) {
			JSONObject error = JSONQ.createObject();
			error.put( "code", code );
			error.put( "message", message );
			if ( null != args && args.length > 0 ) {
				error.put( "args", Arrays.asList( args ));
			}
			complete( error, false );
		}

		/**
		 * Signals that this command completed
		 *
		 * @param payload the result payload to send
		 * @param success true if the command completed successfully
		 */
		private void complete( JSONObject payload, boolean success ) {
			JSONObject response = JSONQ.createObject();
			response.put( Response.REQUEST_ID, _request.getString( Request.ID ) );
			response.put( Response.SUCCESS, success );
			response.put( Response.PAYLOAD, payload );

			if ( success ) {
				_future.complete( response );
			} else {
				_future.fail( response );
			}
		}
	}

	/**
	 * Runnable for provisioning a new store
	 */
	public class ProvisionCommand extends Command {

		/**
		 * Constructor 
		 */
		protected ProvisionCommand( FutureImpl<JSONObject,JSONObject> future, JSONObject request ) {
			super( future, request );
		}

		/**
		 * Called to execute this command
		 */
		public void run() {
			final String storeName = _request.getString( Request.STORE );

			synchronized ( _storeMap ) {
				if ( _storeMap.containsKey( storeName )) {
					fail( 
							"err.store.exists",
							"Store {0} exists",
							_request.getString( Request.STORE ) );
					return;
				}
			}

			// clear to create the store now
			JSONObject schema = _request.getObject( Request.PAYLOAD );
			String providerName = schema.getString( "provider" );
			if ( null == providerName ) {
				fail( "err.invalid_input", "No provider specified" );
				return;
			}

			StoreFactory factory = null;
			synchronized ( _providerMap ) {
				factory = _providerMap.get( providerName );
			}
			if ( null == factory ) {
				fail( 
						"err.no_such_provider",
						"Provider {0} has not been registered with the database",
						providerName );
				return;
			}

			Future<Store,JSONObject> storeFuture = factory.create( schema );
			storeFuture.then(
					new Closure<Store>() {
						public void apply( Store store ) {
							synchronized ( _storeMap ) {
								_storeMap.put( storeName, store );
							}
							complete( null );
						}
					},
					new Closure<JSONObject>() {
						public void apply( JSONObject error ) {
							fail( error );
						}
					},
					null );
		}
	}
}
