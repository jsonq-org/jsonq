package org.jsonq.provider;

import org.jsonq.*;
import org.jsonq.util.*;

/**
 * API for a JSON/q virtual database
 */
public interface Database {

	/**
	 * Saves an item
	 *
	 * @param store the name of the store to save the item into
	 * @param obj the object to save
	 *
	 * @return a Future representing the result of the save operation
	 */
	public Future<JSONObject> save( String store, JSONObject obj );

}
