package org.jsonq;

/**
 * Class holding constants for JSON/q
 */
public final class JSONQConstants {

	/**
	 * Constructor 
	 */
	private JSONQConstants() { }

	/** Enumeration of all possible keys in a JSON/q request */
	public enum Request { 

		ID, 
		OP, 
		STORE,
		PAYLOAD,
		TXN_ID;

		/**
		 * Override toString
		 */
		@Override
		public String toString() {
			return name().toLowerCase();
		}
	};

	/** Enumeration of all possible keys in a JSON/q response */
	public enum Response {

		REQUEST_ID, 
		SUCCESS,
		PAYLOAD,
		TXN_ID;

		/**
		 * Override toString
		 */
		@Override
		public String toString() {
			return name().toLowerCase();
		}
	};

	/** Enumeration of all JSON/q operations */
	public enum Op {

		PROVISION,
		SAVE, 
		FETCH,
		DELETE,
		QUERY;

		/**
		 * Override toString
		 */
		@Override
		public String toString() {
			return name().toLowerCase();
		}
	}

}
