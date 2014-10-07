package org.jsonq.provider;

import org.jsonq.*;
import org.jsonq.util.*;

/**
 * API for creating stores
 */
public interface StoreFactory<T extends Store> {

	/**
	 * Creates a new store
	 *
	 * @param schema the schema to define this store
	 *
	 * @return a Future which will be fired when the store is created
	 */
	public Future<T,JSONObject> create( JSONObject schema );

}
