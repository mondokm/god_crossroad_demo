package org.yakindu.scr.trafficlightctrl;
import java.util.List;

import org.yakindu.scr.ITimer;
import org.yakindu.scr.ITimerCallback;
import org.yakindu.scr.trafficlightctrl.TrafficLightCtrlStatemachine.State;

/**
 * Runnable wrapper of TrafficLightCtrlStatemachine. This wrapper provides a thread-safe
 * instance of the state machine.
 * 
 * Please report bugs and issues...
 */

public class SynchronizedTrafficLightCtrlStatemachine implements ITrafficLightCtrlStatemachine {
	
	/**
	 * The core state machine is simply wrapped and the event processing will be
	 * delegated to that state machine instance. This instance will be created
	 * implicitly.
	 */
	protected TrafficLightCtrlStatemachine statemachine = new TrafficLightCtrlStatemachine();
	
	/**
	 * Interface object for SCILightCommands
	 */		
	protected class SynchronizedSCILightCommands implements SCILightCommands {
		
		public List<SCILightCommandsListener> getListeners() {
			synchronized(statemachine) {
				return statemachine.getSCILightCommands().getListeners();
			}
		}
		
		public boolean isRaisedDisplayRed() {
			synchronized(statemachine) {
				return statemachine.getSCILightCommands().isRaisedDisplayRed();
			}
		}
		
		public boolean isRaisedDisplayGreen() {
			synchronized(statemachine) {
				return statemachine.getSCILightCommands().isRaisedDisplayGreen();
			}
		}
		
		public boolean isRaisedDisplayYellow() {
			synchronized(statemachine) {
				return statemachine.getSCILightCommands().isRaisedDisplayYellow();
			}
		}
		
		public boolean isRaisedDisplayNone() {
			synchronized(statemachine) {
				return statemachine.getSCILightCommands().isRaisedDisplayNone();
			}
		}
		
	};
	
	protected SCILightCommands sCILightCommands;
	
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
	 * Interface object for SCIControl
	 */		
	protected class SynchronizedSCIControl implements SCIControl {
		
		public void raiseToggle() {
			
			synchronized (statemachine) {
				statemachine.getSCIControl().raiseToggle();
				statemachine.runCycle();
			}
		}
		
	};
	
	protected SCIControl sCIControl;
	
	public SynchronizedTrafficLightCtrlStatemachine() {
		sCILightCommands = new SynchronizedSCILightCommands();
		sCIPoliceInterrupt = new SynchronizedSCIPoliceInterrupt();
		sCIControl = new SynchronizedSCIControl();
	}
	
	public synchronized SCILightCommands getSCILightCommands() {
		return sCILightCommands;
	}
	public synchronized SCIPoliceInterrupt getSCIPoliceInterrupt() {
		return sCIPoliceInterrupt;
	}
	public synchronized SCIControl getSCIControl() {
		return sCIControl;
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
			externalTimer.setTimer(SynchronizedTrafficLightCtrlStatemachine.this, eventID, time, isPeriodic);
		}
		
		@Override
		public void unsetTimer(ITimerCallback callback, int eventID) {
			externalTimer.unsetTimer(SynchronizedTrafficLightCtrlStatemachine.this, eventID);
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
