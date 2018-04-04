package org.yakindu.scr.monitor;
import java.util.List;

import org.yakindu.scr.monitor.MonitorStatemachine.State;

/**
 * Runnable wrapper of MonitorStatemachine. This wrapper provides a thread-safe
 * instance of the state machine.
 * 
 * Please report bugs and issues...
 */

public class SynchronizedMonitorStatemachine implements IMonitorStatemachine {
	
	/**
	 * The core state machine is simply wrapped and the event processing will be
	 * delegated to that state machine instance. This instance will be created
	 * implicitly.
	 */
	protected MonitorStatemachine statemachine = new MonitorStatemachine();
	
	/**
	 * Interface object for SCIErrorOut
	 */		
	protected class SynchronizedSCIErrorOut implements SCIErrorOut {
		
		public List<SCIErrorOutListener> getListeners() {
			synchronized(statemachine) {
				return statemachine.getSCIErrorOut().getListeners();
			}
		}
		
		public boolean isRaisedHealthError() {
			synchronized(statemachine) {
				return statemachine.getSCIErrorOut().isRaisedHealthError();
			}
		}
		
	};
	
	protected SCIErrorOut sCIErrorOut;
	
	public SynchronizedMonitorStatemachine() {
		sCIErrorOut = new SynchronizedSCIErrorOut();
	}
	
	public synchronized SCIErrorOut getSCIErrorOut() {
		return sCIErrorOut;
	}
	
	/**
	 * init() will be delegated thread-safely to the wrapped state machine.
	 */
	public void init() {
		synchronized(statemachine) {
			statemachine.init();
		}
	}
	
	/**
	 * enter() will be delegated thread-safely to the wrapped state machine.
	 */
	public void enter() {
		synchronized(statemachine) {
			statemachine.enter();
		}
	}
	
	/**
	 * exit() will be delegated thread-safely to the wrapped state machine.
	 */
	public void exit() {
		synchronized(statemachine) {
			statemachine.exit();
		}
	}
	
	/**
	 * isActive() will be delegated thread-safely to the wrapped state machine.
	 */
	public boolean isActive() {
		synchronized(statemachine) {
			return statemachine.isActive();
		}
	}
	
	/**
	 * isFinal() will be delegated thread-safely to the wrapped state machine.
	 */
	public boolean isFinal() {
		synchronized(statemachine) {
			return statemachine.isFinal();
		}
	}
	
	/**
	 * isStateActive() will be delegated thread-safely to the wrapped state machine.
	 */
	public boolean isStateActive(State state) {
		synchronized(statemachine) {
			return statemachine.isStateActive(state);
		}
	}
	
	/**
	 * runCycle() will be delegated thread-safely to the wrapped state machine.
	 */ 
	@Override
	public void runCycle() {
		synchronized (statemachine) {
			statemachine.runCycle();
		}
	}
}
