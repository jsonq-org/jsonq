package org.jsonq;

import static org.jsonq.JSONQConstants.*;

/**
 * Singleton class for JSON/q functionality
 */
public final class JSONQ {

	/**
	 * Constructor 
	 */
	protected JSONQ() { }

	/**
	 * Execute a JSON/q command
	 *
	 * @param obj the JSON object representing a JSON/q command
	 *
	 * @return a future for sampling results
	 */
	public static String exec( JSONObject obj ) {
		return "Got command "+obj.getString(OP);
	}

}
