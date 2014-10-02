package org.jsonq.util;

import java.util.concurrent.*;
import java.util.*;

/**
 * Rudimentary scheduler for executing things in the future
 */
public final class Scheduler {

	// member variables
	private static final Executor _executor = Executors.newSingleThreadExecutor();
	//private static final Timer _timer;
		//_timer = new Timer( "SystemEventQueue" );

	/**
	 * Private Constructor - singleton 
	 */
	private Scheduler() {}

	/**
	 * Runs the given code on another thread
	 *
	 * @param runnable the Runnable to execute
	 */
	public static void runAsync( Runnable runnable ) {
		_executor.execute( runnable );
	}
}
