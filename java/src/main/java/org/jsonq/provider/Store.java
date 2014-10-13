package org.jsonq.provider;

import org.jsonq.*;
import org.jsonq.util.*;
import java.util.*;

/**
 * API for a virtual store provider
 */
public interface Store {

	/**
	 * Synchronous method to return the schema-specified ID field for this store
	 */
	public String getIdField();

	/**
	 * Save the given object
	 *
	 * @param obj the object to save
	 *
	 * @return a Future representing the eventual result of the save
	 */
	public Future<String,JSONObject> save( JSONObject obj );

	/**
	 * Retrieves an item by its ID
	 *
	 * @param request the valid JSON/q request
	 *
	 * @return a Future representing the result of the fetch operation
	 */
	public Future<JSONObject,JSONObject> fetch( JSONObject request );

	/**
	 * Delete an item by its ID
	 *
	 * @param request the valid JSON/q request
	 *
	 * @return a Future representing the result of the delete operation
	 */
	public Future<Void,JSONObject> delete( JSONObject request );

	/**
	 * List all the items in a given store
	 *
	 * @param request the valid JSON/q request
	 *
	 * @return a Future representing the result of the list operation
	 */
	public Future<List<JSONObject>,JSONObject> list( JSONObject request );

}
