package org.yakindu.scr.controller;
import java.util.List;

import org.yakindu.scr.ITimer;
import org.yakindu.scr.ITimerCallback;
import org.yakindu.scr.controller.ControllerStatemachine.State;

/**
 * Runnable wrapper of ControllerStatemachine. This wrapper provides a thread-safe
 * instance of the state machine.
 * 
 * Please report bugs and issues...
 */

public class SynchronizedControllerStatemachine implements IControllerStatemachine {
	
	/**
	 * The core state machine is simply wrapped and the event processing will be
	 * delegated to that state machine instance. This instance will be created
	 * implicitly.
	 */
	protected ControllerStatemachine statemachine = new ControllerStatemachine();
	
	/**
	 * Interface object for SCIPoliceInterrupt
	 */		
	protected class SynchronizedSCIPoliceInterrupt implements SCIPoliceInterrupt {
		
		public void raisePolice() {
			
			synchronized (statemachine) {
				statemachine.getSCIPoliceInterrupt().raisePolice();
				statemachine.runCycle();
			}
		}
		
		public void raiseReset() {
			
			synchronized (statemachine) {
				statemachine.getSCIPoliceInterrupt().raiseReset();
				statemachine.runCycle();
			}
		}
		
	};
	
	protected SCIPoliceInterrupt sCIPoliceInterrupt;
	
	/**
	 * Interface object for SCIPriorityPolice
	 */		
	protected class SynchronizedSCIPriorityPolice implements SCIPriorityPolice {
		
		public List<SCIPriorityPoliceListener> getListeners() {
			synchronized(statemachine) {
				return statemachine.getSCIPriorityPolice().getListeners();
			}
		}
		
		public boolean isRaisedPolice() {
			synchronized(statemachine) {
				return statemachine.getSCIPriorityPolice().isRaisedPolice();
			}
		}
		
		public boolean isRaisedReset() {
			synchronized(statemachine) {
				return statemachine.getSCIPriorityPolice().isRaisedReset();
			}
		}
		
	};
	
	protected SCIPriorityPolice sCIPriorityPolice;
	
	/**
	 * Interface object for SCISecondaryPolice
	 */		
	protected class SynchronizedSCISecondaryPolice implements SCISecondaryPolice {
		
		public List<SCISecondaryPoliceListener> getListeners() {
			synchronized(statemachine) {
				return statemachine.getSCISecondaryPolice().getListeners();
			}
		}
		
		public boolean isRaisedPolice() {
			synchronized(statemachine) {
				return statemachine.getSCISecondaryPolice().isRaisedPolice();
			}
		}
		
		public boolean isRaisedReset() {
			synchronized(statemachine) {
				return statemachine.getSCISecondaryPolice().isRaisedReset();
			}
		}
		
	};
	
	protected SCISecondaryPolice sCISecondaryPolice;
	
	/**
	 * Interface object for SCIPedestrianPolice
	 */		
	protected class SynchronizedSCIPedestrianPolice implements SCIPedestrianPolice {
		
		public List<SCIPedestrianPoliceListener> getListeners() {
			synchronized(statemachine) {
				return statemachine.getSCIPedestrianPolice().getListeners();
			}
		}
		
		public boolean isRaisedPolice() {
			synchronized(statemachine) {
				return statemachine.getSCIPedestrianPolice().isRaisedPolice();
			}
		}
		
		public boolean isRaisedReset() {
			synchronized(statemachine) {
				return statemachine.getSCIPedestrianPolice().isRaisedReset();
			}
		}
		
	};
	
	protected SCIPedestrianPolice sCIPedestrianPolice;
	
	/**
	 * Interface object for SCIPriorityControl
	 */		
	protected class SynchronizedSCIPriorityControl implements SCIPriorityControl {
		
		public List<SCIPriorityControlListener> getListeners() {
			synchronized(statemachine) {
				return statemachine.getSCIPriorityControl().getListeners();
			}
		}
		
		public boolean isRaisedToggle() {
			synchronized(statemachine) {
				return statemachine.getSCIPriorityControl().isRaisedToggle();
			}
		}
		
	};
	
	protected SCIPriorityControl sCIPriorityControl;
	
	/**
	 * Interface object for SCISecondaryControl
	 */		
	protected class SynchronizedSCISecondaryControl implements SCISecondaryControl {
		
		public List<SCISecondaryControlListener> getListeners() {
			synchronized(statemachine) {
				return statemachine.getSCISecondaryControl().getListeners();
			}
		}
		
		public boolean isRaisedToggle() {
			synchronized(statemachine) {
				return statemachine.getSCISecondaryControl().isRaisedToggle();
			}
		}
		
	};
	
	protected SCISecondaryControl sCISecondaryControl;
	
	/**
	 * Interface object for SCIPedestrianControl
	 */		
	protected class SynchronizedSCIPedestrianControl implements SCIPedestrianControl {
		
		public List<SCIPedestrianControlListener> getListeners() {
			synchronized(statemachine) {
				return statemachine.getSCIPedestrianControl().getListeners();
			}
		}
		
		public boolean isRaisedToggle() {
			synchronized(statemachine) {
				return statemachine.getSCIPedestrianControl().isRaisedToggle();
			}
		}
		
	};
	
	protected SCIPedestrianControl sCIPedestrianControl;
	
	/**
	 * Interface object for SCIError
	 */		
	protected class SynchronizedSCIError implements SCIError {
		
		public void raiseHealthError() {
			
			synchronized (statemachine) {
				statemachine.getSCIError().raiseHealthError();
				statemachine.runCycle();
			}
		}
		
	};
	
	protected SCIError sCIError;
	
	public SynchronizedControllerStatemachine() {
		sCIPoliceInterrupt = new SynchronizedSCIPoliceInterrupt();
		sCIPriorityPolice = new SynchronizedSCIPriorityPolice();
		sCISecondaryPolice = new SynchronizedSCISecondaryPolice();
		sCIPedestrianPolice = new SynchronizedSCIPedestrianPolice();
		sCIPriorityControl = new SynchronizedSCIPriorityControl();
		sCISecondaryControl = new SynchronizedSCISecondaryControl();
		sCIPedestrianControl = new SynchronizedSCIPedestrianControl();
		sCIError = new SynchronizedSCIError();
	}
	
	public synchronized SCIPoliceInterrupt getSCIPoliceInterrupt() {
		return sCIPoliceInterrupt;
	}
	public synchronized SCIPriorityPolice getSCIPriorityPolice() {
		return sCIPriorityPolice;
	}
	public synchronized SCISecondaryPolice getSCISecondaryPolice() {
		return sCISecondaryPolice;
	}
	public synchronized SCIPedestrianPolice getSCIPedestrianPolice() {
		return sCIPedestrianPolice;
	}
	public synchronized SCIPriorityControl getSCIPriorityControl() {
		return sCIPriorityControl;
	}
	public synchronized SCISecondaryControl getSCISecondaryControl() {
		return sCISecondaryControl;
	}
	public synchronized SCIPedestrianControl getSCIPedestrianControl() {
		return sCIPedestrianControl;
	}
	public synchronized SCIError getSCIError() {
		return sCIError;
	}
	/*================ TIME EVENT HANDLING ================
	
	/** An external timer instance is required. */
	protected ITimer externalTimer;
	
	/** Internally we use a timer proxy that queues time events together with other input events. */
	protected ITimer timerProxy = new ITimer() {
		/** Simply delegate to external timer with a modified callback. */
		@Override
		public void setTimer(ITimerCallback callback, int eventID, long time,
				boolean isPeriodic) {
			externalTimer.setTimer(SynchronizedControllerStatemachine.this, eventID, time, isPeriodic);
		}
		
		@Override
		public void unsetTimer(ITimerCallback callback, int eventID) {
			externalTimer.unsetTimer(SynchronizedControllerStatemachine.this, eventID);
		}
	};
	
	/**
	 * Set the {@link ITimer} for the state machine. It must be set externally
	 * on a timed state machine before a run cycle can be correct executed.
	 * 
	 * @param timer
	 */
	public void setTimer(ITimer timer) {
		synchronized(statemachine) {
			this.externalTimer = timer;
			/* the wrapped state machine uses timer proxy as timer */
			statemachine.setTimer(timerProxy);
		}
	}
	
	/**
	* Returns the currently used timer.
	* 
	* @return {@link ITimer}
	*/
	public ITimer getTimer() {
		return externalTimer;
	}
	
	public void timeElapsed(int eventID) {
		synchronized (statemachine) {
			statemachine.timeElapsed(eventID);
		}
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
