package org.jsonq.gwt;

import org.jsonq.*;
import org.jsonq.util.*;
import com.google.gwt.core.client.*;
import org.timepedia.exporter.client.*;

/**
 * Wrap a Future to expose to JavaScript
 */
@Export
public class FutureWrapper implements Exportable {

	protected Future<JSONObject,JSONObject> _future;

	/**
	 * Wrap the given future
	 */
	public void wrap( Future<JSONObject,JSONObject> future ) {
		System.out.println("Wrapped");
		_future = future;
	}
	
	/**
	 * Returns the transaction ID for this future. This method should always return instantly.
	 */
	public String getId() {
		return _future.getId();
	}

	/**
	 * Register handlers with this future. Any of them may be null. If the future is already
	 * complete (success or failure) when this method is called, the appropriate handler will be
	 * fired immediately.
	 *
	 * @param successHandler the handler to be fired when the future completes
	 * @param failureHandler the handler to be fired when the future fails
	 * @param progressHandler the handler to be fired for updating progress on the operation
	 *
	 * @return a Future to allow chaining. The handlers for the returned Future are called after the
	 * handlers for this Future complete
	 *
	 * @throws IllegalStateException if this method has been called before
	 */
	public FutureWrapper then(
			JavaScriptObject successHandler,
			JavaScriptObject failureHandler,
			JavaScriptObject progressHandler ) {
		return GwtJSONQ.wrap( _future.then( 
					new JSClosure<JSONObject>( successHandler ), 
					new JSClosure<JSONObject>( failureHandler ), 
					new JSClosure<Double>( progressHandler )));
	}

	/**
	 * Returns true if this future is completed (success or failure)
	 */
	public boolean isComplete() {
		return _future.isComplete();
	}

	/**
	 * Returns true if this future has completed in a failure state
	 */
	public boolean isFailure() {
		return _future.isFailure();
	}

	/**
	 * Retrieves the value of this future in a synchronous fashion. If the future is completed in an
	 * error state, the resultant Exception will be thrown from the invocation of this method.
	 *
	 * @throws IllegalStateException if the Future is not complete
	 */
	public JSONObject get() {
		return _future.get();
	}

	/**
	 * Returns the error value of this future in a synchronous fashion. If the future has completed
	 * successfully, this method returns null.
	 *
	 * @throws IllegalStateException if the Future is not complete
	 */
	public JSONObject getError() {
		return _future.getError();
	}
}
