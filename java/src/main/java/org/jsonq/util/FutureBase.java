package org.jsonq.util;

/**
 * Base implementation of a Future. Subclasses need only implement <code>then()</code> and the
 * functionality to fire the handlers at the proper time.
 *
 * @see org.jsonq.util.Future#then()
 */
public abstract class FutureBase<T> implements Future<T> {

	protected final String _id;

	protected boolean _complete = false;
	protected T _result;
	protected Exception _error;

	/**
	 * Constructor 
	 */
	protected FutureBase( String id ) {
		_id = id;
	}

	/**
	 * Returns the transaction ID for this future. This method should always return instantly.
	 */
	public String getId() {
		return _id;
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
