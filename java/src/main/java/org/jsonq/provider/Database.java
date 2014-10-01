package org.jsonq.provider;

import org.jsonq.*;
import org.jsonq.util.*;

/**
 * API for a JSON/q virtual database. Implementors can expect the JSON/q messages passed into this
 * API to be valid, containing all required values.
 */
public interface Database {

	/**
	 * Saves an item
	 *
	 * @param message the valid JSON/q message
	 *
	 * @return a Future representing the result of the save operation
	 */
	public Future<JSONObject> save( JSONObject message );

}
