package org.jsonq.gwt;

import org.jsonq.provider.*;

import com.google.gwt.core.client.*;
import com.google.gwt.user.client.*;
import org.timepedia.exporter.client.*;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Main implements EntryPoint {

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		DefaultDatabase db = new DefaultDatabase();
		db.registerProvider( "mem", SimpleStore.FACTORY );
		org.jsonq.JSONQ.setDatabase( db );
		org.jsonq.JSONQ.setJSONObjectFactory( JSONObject.FACTORY );
		ExporterUtil.exportAll();
	}
}
