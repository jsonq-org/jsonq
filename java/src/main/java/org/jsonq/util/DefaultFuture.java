package org.jsonq.util;

/**
 * Default implementation of a Future
 */
public class DefaultFuture<T> implements Future<T> {

	protected final String _id;

	protected boolean _complete = false;
	protected T _result;
	protected Exception _error;

	/**
	 * Constructor 
	 */
	protected DefaultFuture( String id ) {
		_id = id;
	}

	/**
	 * Returns the transaction ID for this future. This method should always return instantly.
	 */
	public String getId() {
		return _id;
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
	 * @throws IllegalStateException if this method has been called before
	 */
	public void then(
			Closure<T> successHandler,
			Closure<Exception> failureHandler,
			Closure<Double> progressHandler ) {
	}

	/**
	 * Returns true if this future is completed (success or failure)
	 */
	public boolean isComplete() {
		return _complete;
	}

	/**
	 * Returns true if this future has completed in a failure state
	 */
	public boolean isFailure() {
		return _complete && null != _error;
	}

	/**
	 * Retrieves the value of this future in a synchronous fashion. If the future is completed in an
	 * error state, the resultant Exception will be thrown from the invocation of this method.
	 *
	 * @throws IllegalStateException if the Future is not complete
	 */
	public T get() {
		if ( ! _complete ) {
			throw new IllegalStateException( "Future has not completede" );
		}
		if ( null != _error ) {
			if ( _error instanceof RuntimeException ) {
				throw (RuntimeException)_error;
			} else {
				throw new RuntimeException( _error );
			}
		}
		return _result;
	}

	/**
	 * Returns the error value of this future in a synchronous fashion. If the future has completed
	 * successfully, this method returns null.
	 *
	 * @throws IllegalStateException if the Future is not complete
	 */
	public Exception getError() {
		if ( ! _complete ) {
			throw new IllegalStateException( "Future has not completede" );
		}
		return _error;
	}

}
