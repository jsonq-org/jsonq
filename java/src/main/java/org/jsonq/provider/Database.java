package org.jsonq.provider;

import org.jsonq.*;
import org.jsonq.util.*;

/**
 * API for a JSON/q virtual database. Implementors can expect the JSON/q messages passed into this
 * API to be valid, containing all required values.
 */
public interface Database {

	/**
	 * Provisions a new store on this Database
	 *
	 * @param request the valid JSON/q request
	 *
	 * @return a Future representing the resultant JSON/q response
	 */
	public Future<JSONObject,JSONObject> provision( JSONObject request );

	/**
	 * Saves an item
	 *
	 * @param request the valid JSON/q request
	 *
	 * @return a Future representing the result of the save operation
	 */
	public Future<JSONObject,JSONObject> save( JSONObject request );

	/**
	 * Retrieves an item by its ID
	 *
	 * @param request the valid JSON/q request
	 *
	 * @return a Future representing the result of the fetch operation
	 */
	public Future<JSONObject,JSONObject> fetch( JSONObject request );

}
