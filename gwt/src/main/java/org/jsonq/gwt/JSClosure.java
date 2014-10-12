package org.jsonq.gwt;

import org.jsonq.util.*;
import com.google.gwt.core.client.*;

/**
 * Closure implementation to call out to a native JS function
 */
public class JSClosure<T> implements Closure<T> {

	protected final JavaScriptObject _func;

	/**
	 * Constructor 
	 */
	public JSClosure( JavaScriptObject func ) {
		_func = func;
	}

	/**
	 * Apply the closure with the given value
	 */
	public void apply( T value ) {
		call( _func, value );
	}

	/**
	 * Call the method
	 */
	private native void call( JavaScriptObject func, T value ) /*-{
		func( value );
	}-*/;
}
