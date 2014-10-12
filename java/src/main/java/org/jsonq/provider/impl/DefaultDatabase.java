package org.jsonq.provider.impl;

import org.jsonq.*;
import org.jsonq.provider.*;
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
	@Override
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
	@Override
	public Future<JSONObject,JSONObject> save( JSONObject request ) {
		FutureImpl<JSONObject,JSONObject> future = new FutureImpl<JSONObject,JSONObject>();
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
		return null;
	}

	//----------------------------------------
	// Commands
	//----------------------------------------

	/**
	 * Extension of Command to wrap responses in a JSON/q Response
	 */
	public abstract class DbCommand extends Command<JSONObject> {

		/**
		 * Constructor 
		 */
		protected DbCommand( FutureImpl<JSONObject,JSONObject> future, JSONObject request ) {
			super( future, request );
		}

		/**
		 * Complete successfully with a String payload
		 */
		protected void complete( String payload ) {
			complete( payload, true );
		}

		/**
		 * Signals that this command completed
		 *
		 * @param payload the result payload to send
		 * @param success true if the command completed successfully
		 */
		@Override
		protected void complete( Object payload, boolean success ) {
			JSONObject response = JSONObject.create();
			response.put( Response.REQUEST_ID, _request.getString( Request.ID ) );
			response.put( Response.SUCCESS, success );

			if ( null == payload ) {
				response.put( Response.PAYLOAD, (String)null );
			} else if ( payload instanceof JSONObject ) {
				response.put( Response.PAYLOAD, (JSONObject)payload );
			} else if ( payload instanceof String ) {
				response.put( Response.PAYLOAD, (String)payload );
			} else if ( payload instanceof Boolean ) {
				response.put( Response.PAYLOAD, (Boolean)payload );
			} else if ( payload instanceof Integer ) {
				response.put( Response.PAYLOAD, (Integer)payload );
			} else if ( payload instanceof Float ) {
				response.put( Response.PAYLOAD, (Float)payload );
			} else if ( payload instanceof Double ) {
				response.put( Response.PAYLOAD, (Double)payload );
			} else {
				throw new IllegalArgumentException( "Unsupported payload type "+payload.getClass());
			}

			super.complete( response, success );
		}
	}
	/**
	 * Runnable for provisioning a new store
	 */
	public class ProvisionCommand extends DbCommand {

		/**
		 * Constructor 
		 */
		protected ProvisionCommand( FutureImpl<JSONObject,JSONObject> future, JSONObject request ) {
			super( future, request );
		}

		/**
		 * Called to execute this command
		 */
		@SuppressWarnings("unchecked")
		public void run() {
			final String storeName = _request.getString( Request.STORE );

			synchronized ( _storeMap ) {
				if ( _storeMap.containsKey( storeName )) {
					fail( "err.store.exists", "Store {0} exists", storeName );
					return;
				}
			}

			// clear to create the store now
			JSONObject schema = _request.getObject( Request.PAYLOAD );
			String providerName = schema.getString( Schema.PROVIDER );
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
							complete( (String)null );
						}
					},
					new Closure<JSONObject>() {
						public void apply( JSONObject error ) {
							fail( error.getObject( Response.PAYLOAD ) );
						}
					},
					null );
		}
	}

	/**
	 * Runnable for saving an object
	 */
	public class SaveCommand extends DbCommand {

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
			final String storeName = _request.getString( Request.STORE );
			synchronized ( _storeMap ) {
				if ( ! _storeMap.containsKey( storeName )) {
					fail( "err.invalid.store", "Cannot find store {0}", storeName );
					return;
				}
			}

			final Store store = _storeMap.get( storeName );
			Future<String,JSONObject> future = store.save( _request );
			future.then(
					new Closure<String>() {
						public void apply( String id ) {
							complete( id );
						}
					},
					new Closure<JSONObject>() {
						public void apply( JSONObject error ) {
							fail( error.getObject( Response.PAYLOAD ) );
						}
					},
					null );
		}
	}
}
