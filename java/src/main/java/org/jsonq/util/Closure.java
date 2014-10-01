package org.jsonq.util;

/**
 * Interface for a closure of code which can be called asynchronously
 */
public interface Closure<T> {

	/**
	 * Called to execute the closure on the given value
	 *
	 * @param value the value to be processed
	 */
	public void apply( T value );

}
