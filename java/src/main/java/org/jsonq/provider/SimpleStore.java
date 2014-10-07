package org.jsonq.provider;

import org.jsonq.*;
import org.jsonq.util.*;

/**
 * API for a virtual store provider
 */
public class SimpleStore implements Store {

	public static final StoreFactory<SimpleStore> FACTORY = new Factory();

	/**
	 * Factory for creating SimpleStores
	 */
	private static class Factory implements StoreFactory<SimpleStore> {

		public Future<SimpleStore,JSONObject> create( JSONObject schema ) {
			FutureImpl<SimpleStore,JSONObject> future = new FutureImpl<>();

			// TODO: schema shit
			future.complete( new SimpleStore() );
			return future;
		}
	}

	/**
	 * Protected Constructor. Only subclasses and the factory should be able to create a SimpleStore
	 */
	protected SimpleStore() { }

	/**
	 * Save the given object
	 *
	 * @param obj the object to save
	 *
	 * @return a Future representing the eventual result of the save
	 */
	public Future<JSONObject,JSONObject> save( JSONObject obj ) {
		throw new IllegalStateException("fixme");
	}

}
