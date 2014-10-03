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
	public static class Request { 

		public static final String ID = "id";
		public static final String OP = "op";
		public static final String STORE = "store";
		public static final String PAYLOAD = "payload";
		public static final String TXN_ID = "txn_id";
	}

	/** Enumeration of all possible keys in a JSON/q response */
	public static class Response {

		public static final String REQUEST_ID = "request_id";
		public static final String SUCCESS = "success";
		public static final String PAYLOAD = "payload";
		public static final String TXN_ID = "txn_id";
	}

	/** Enumeration of all JSON/q operations */
	public static class Op {

		public static final String PROVISION = "provision";
		public static final String SAVE = "save";
		public static final String FETCH = "fetch";
		public static final String DELETE = "delete";
		public static final String QUERY = "query";
	}
}
