package org.jsonq.util;

import java.util.*;

/**
 * Basic, thread-safe implementation of Future
 */
public class FutureImpl<T,E> extends FutureBase<T,E> {

	protected FutureImpl<T,E> _next;
	protected Closure<T> _successHandler;
	protected Closure<E> _errorHandler;
	protected Closure<Double> _progressHandler;
	protected boolean _handlersSet;

	protected boolean _fired;

	/**
	 * Constructor 
	 */
	public FutureImpl() {
		super( ""+System.currentTimeMillis());
		//UUID.randomUUID() );
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
	public synchronized Future<T,E> then(
			Closure<T> successHandler,
			Closure<E> failureHandler,
			Closure<Double> progressHandler ) {

		if ( _handlersSet ) {
			throw new IllegalStateException( "Cannot call then() twice!" );
		}

		_successHandler = successHandler;
		_errorHandler = failureHandler;
		_progressHandler = progressHandler;
		_handlersSet = true;

		if ( isComplete() ) {
			finish();
		}
		_next = new FutureImpl<T,E>();
		return _next;
	}

	/**
	 * Complete in a successful fashion
	 */
	public synchronized void complete( T result ) {
		setResults( result, null );
		finish();
	}

	/**
	 * Complete in an erroneous fashion
	 */
	public synchronized void fail( E error ) {
		setResults( null, error );
		finish();
	}

	/**
	 * Finish execution, firing any handlers necessary based on the state of completion
	 */
	private synchronized void finish() {
		if ( ! isComplete() ) {
			throw new IllegalStateException( "Cannot call finish() if not complete" );
		}
		if ( _fired ) {
			throw new IllegalStateException( "Cannot fire Future when it has already fired" );
		}

		// nothing to do (yet)
		if ( ! _handlersSet ) {
			return;
		}

		if ( isFailure() && null != _errorHandler ) {
			_errorHandler.apply( _error );
		} else if ( ! isFailure() && null != _successHandler ) {
			_successHandler.apply( _result );
		}
		_fired = true;

		Scheduler.runAsync(
				new Runnable() {
					public void run() {
						_next.setResults( _result, _error );
					}
				});
	}
}
