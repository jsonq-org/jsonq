package org.jsonq;

import org.timepedia.exporter.client.*;
import com.google.gwt.user.client.*;

/**
 * Exportable shim for @see org.jsonq.JSONQ
 */
@ExportPackage("")
@Export(value="JSONQ", all=true)
public abstract class XJSONQ implements ExportOverlay<JSONQ> {
	
	// shim
	public static String exec( JSONObject obj ) { return null; }

}
