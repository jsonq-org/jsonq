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

	/**
	 * Constructor 
	 */
	public DefaultDatabase() {
		_storeMap = new HashMap<>(16);
	}

	/**
	 * Provisions a new store on this Database
	 *
	 * @param request the valid JSON/q request
	 *
	 * @return a Future representing the resultant JSON/q response
	 */
	public Future<JSONObject> provision( JSONObject request ) {
		Future<JSONObject> future = null; // TODO
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
	public Future<JSONObject> save( JSONObject request ) {
		return null;
	}


	/**
	 * Base class for DB commands
	 */
	public abstract class Command implements Runnable {

		protected final Future<JSONObject> _future;
		protected final JSONObject _request;

		/**
		 * Constructor 
		 */
		protected Command( Future<JSONObject> future, JSONObject request ) {
			_future = future;
			_request = request;
		}
	}

	/**
	 * Runnable for provisioning a new store
	 */
	public class ProvisionCommand extends Command {

		/**
		 * Constructor 
		 */
		protected ProvisionCommand( Future<JSONObject> future, JSONObject request ) {
			super( future, request );
		}

		/**
		 * Called to execute this command
		 */
		public void run() {
			String storeName = _request.getString( ""+Request.STORE );
			synchronized ( _storeMap ) {
				if ( _storeMap.containsKey( storeName )) {
					// XXX error
				}
			}
		}
	}

}
