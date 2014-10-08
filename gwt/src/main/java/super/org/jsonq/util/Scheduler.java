package org.jsonq.util;

import java.util.concurrent.*;
import java.util.*;

/**
 * Rudimentary scheduler for executing things in the future
 */
public final class Scheduler {

	/**
	 * Private Constructor - singleton 
	 */
	private Scheduler() {}

	/**
	 * Runs the given code on another thread
	 *
	 * @param runnable the Runnable to execute
	 */
	public static void runAsync( final Runnable runnable ) {
		com.google.gwt.core.client.Scheduler.get().scheduleDeferred(
				new com.google.gwt.core.client.Scheduler.ScheduledCommand() {
					public void execute() { runnable.run(); }
				} );
	}
}
