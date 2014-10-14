package org.jsonq.gwt;

import org.jsonq.*;
import org.jsonq.util.*;
import org.timepedia.exporter.client.*;
import com.google.gwt.user.client.*;
import java.util.*;

import static org.jsonq.JSONQConstants.*;

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

	/**
	 * Generate an RFC4122-compliant UUID
	 */
	@Export
	public static String uuid() {
		return org.jsonq.util.UUID.uuid();
	}

	/**
	 * Provisions a store.
	 *
	 * @param storeName the name of the store to provision
	 * @param schema the schema object to configure the store
	 */
	@Export
	public static FutureWrapper provision( String storeName, JSONObject schema ) {
		return doExec( storeName, Op.PROVISION, schema );
	}

	/**
	 * Saves the given object into the given store
	 *
	 * @param storeName the name of the store to save the object into
	 * @param data the object to store
	 */
	@Export
	public static FutureWrapper save( String storeName, JSONObject data ) {
		return doExec( storeName, Op.SAVE, data );
	}

	/**
	 * Fetches an object by ID from the given store
	 *
	 * @param storeName the name of the store to save the object into
	 * @param id the ID of the object to fetch
	 */
	@Export
	public static FutureWrapper fetch( String storeName, String id ) {
		return doExec( storeName, Op.FETCH, id );
	}

	/**
	 * Fetches an object by ID from the given store
	 *
	 * @param storeName the name of the store to save the object into
	 * @param id the ID of the object to fetch
	 */
	@Export("remove")
	public static FutureWrapper delete( String storeName, String id ) {
		return doExec( storeName, Op.DELETE, id );
	}

	/**
	 * Lists all objects in a store
	 *
	 * @param storeName the name of the store to save the object into
	 */
	@Export
	public static FutureWrapper list( String storeName ) {
		return doExec( storeName, Op.LIST, "" );
	}

	/**
	 * Funnel for all named exec methods to use
	 */
	private static FutureWrapper doExec( String storeName, String op, Object payload ) {
		JSONObject request = JSONObject.create();
		request.put( Request.ID, org.jsonq.util.UUID.uuid() );
		request.put( Request.OP, op );
		request.put( Request.STORE, storeName );
		if ( null == payload ) {
			request.put( Request.PAYLOAD, (String)null );
		} else if ( payload instanceof JSONObject ) {
			request.put( Request.PAYLOAD, (JSONObject)payload );
		} else if ( payload instanceof String ) {
			request.put( Request.PAYLOAD, (String)payload );
		} else if ( payload instanceof Boolean ) {
			request.put( Request.PAYLOAD, (Boolean)payload );
		} else if ( payload instanceof Integer ) {
			request.put( Request.PAYLOAD, (Integer)payload );
		} else if ( payload instanceof Float ) {
			request.put( Request.PAYLOAD, (Float)payload );
		} else if ( payload instanceof Double ) {
			request.put( Request.PAYLOAD, (Double)payload );
		} else if ( payload instanceof List ) {
			request.put( Request.PAYLOAD, (List)payload );
		} else if ( payload instanceof Object[] ) {
			request.put( Request.PAYLOAD, Arrays.asList( (Object[])payload ) );
		} else {
			throw new IllegalArgumentException( "Unsupported payload type "+payload.getClass());
		}
		return exec( request );
	}

}
