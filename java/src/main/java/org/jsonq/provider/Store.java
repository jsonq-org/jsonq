package org.jsonq.provider;

import org.jsonq.*;
import org.jsonq.util.*;

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

}
