package org.jsonq;

import org.jsonq.gwt.*;
import org.jsonq.util.*;
import org.timepedia.exporter.client.*;
import com.google.gwt.user.client.*;

/**
 * Exportable shim for @see org.jsonq.JSONQ
 */
@ExportPackage("")
@Export(value="JSONQ", all=false)
public final class GwtJSONQ implements Exportable {
	
	/**
	 * Pass-through to org.jsonq.JSONQ
	 */
	@Export
	public static FutureWrapper exec( JSONObject obj ) {
		Future<JSONObject,JSONObject> fut = JSONQ.exec( obj );
		return wrap( fut );
	}


	/**
	 * Creates a FutureWrapper
	 */
	public static native FutureWrapper wrap( Future<JSONObject,JSONObject> future ) /*-{
		var wrapper = new org.jsonq.gwt.FutureWrapper();
		wrapper.wrap( future );
		return wrapper
	}-*/;
}
