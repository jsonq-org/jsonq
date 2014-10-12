package org.jsonq.provider.impl;

import org.jsonq.*;
import org.jsonq.util.*;
import java.util.*;

import static org.jsonq.JSONQConstants.*;

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
		JSONObject error = JSONObject.create();
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
		JSONObject response = JSONObject.create();
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
