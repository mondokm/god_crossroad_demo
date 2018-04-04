package hu.bme.mit.gamma.impl.controller;

import java.util.Queue;
import java.util.List;
import java.util.LinkedList;

import hu.bme.mit.gamma.impl.event.*;
import hu.bme.mit.gamma.impl.interfaces.*;
import org.yakindu.scr.controller.IControllerStatemachine.SCISecondaryControlListener;
import org.yakindu.scr.controller.IControllerStatemachine.SCIPriorityControlListener;
import org.yakindu.scr.controller.IControllerStatemachine.SCIPedestrianControlListener;
import org.yakindu.scr.controller.IControllerStatemachine.SCIPedestrianPoliceListener;
import org.yakindu.scr.controller.IControllerStatemachine.SCIPriorityPoliceListener;
import org.yakindu.scr.controller.IControllerStatemachine.SCISecondaryPoliceListener;
import org.yakindu.scr.TimerService;
import org.yakindu.scr.ITimer;
import org.yakindu.scr.controller.ControllerStatemachine;
import org.yakindu.scr.controller.ControllerStatemachine.State;

public class ControllerStatechart implements ControllerStatechartInterface {
	// The wrapped Yakindu statemachine
	private ControllerStatemachine controllerStatemachine = new ControllerStatemachine();
	// Port instances
	private SecondaryControl secondaryControl = new SecondaryControl();
	private PriorityControl priorityControl = new PriorityControl();
	private PedestrianControl pedestrianControl = new PedestrianControl();
	private Error error = new Error();
	private PedestrianPolice pedestrianPolice = new PedestrianPolice();
	private PoliceInterrupt policeInterrupt = new PoliceInterrupt();
	private PriorityPolice priorityPolice = new PriorityPolice();
	private SecondaryPolice secondaryPolice = new SecondaryPolice();
	// Indicates which queues are active in this cycle
	private boolean insertQueue = true;
	private boolean processQueue = false;
	// Event queues for the synchronization of statecharts
	private Queue<Event> eventQueue1 = new LinkedList<Event>();
	private Queue<Event> eventQueue2 = new LinkedList<Event>();
	
	public ControllerStatechart() {
		// Initializing and entering the wrapped statemachine
		controllerStatemachine.setTimer(new TimerService());
		controllerStatemachine.init();
		controllerStatemachine.enter();
	}
	
	/** Resets the statemachine. Should be used only be the container (composite system) class. */
	public void reset() {
		controllerStatemachine.enter();
	}
	
	/** Changes the event queues of the component instance. Should be used only be the container (composite system) class. */
	public void changeEventQueues() {
		insertQueue = !insertQueue;
		processQueue = !processQueue;
	}
	
	/** Changes the event queues to which the events are put. Should be used only be a cascade container (composite system) class. */
	public void changeInsertQueue() {
	    insertQueue = !insertQueue;
	}
	
	/** Returns whether the eventQueue containing incoming messages is empty. Should be used only be the container (composite system) class. */
	public boolean isEventQueueEmpty() {
		return getInsertQueue().isEmpty();
	}
	
	/** Returns the event queue into which events should be put in the particular cycle. */
	private Queue<Event> getInsertQueue() {
		if (insertQueue) {
			return eventQueue1;
		}
		return eventQueue2;
	}
	
	/** Returns the event queue from which events should be inspected in the particular cycle. */
	private Queue<Event> getProcessQueue() {
		if (processQueue) {
			return eventQueue1;
		}
		return eventQueue2;
	}
	
	/** Changes event queues and initiating a cycle run. */
	@Override
	public void runCycle() {
		changeEventQueues();
		runComponent();
	}
	
	/** Changes the insert queue and initiates a run. */
	public void runAndRechangeInsertQueue() {
		// First the insert queue is changed back, so self-event sending can work
	    changeInsertQueue();
	    runComponent();
	}
	
	/** Initiates a cycle run without changing the event queues. It is needed if this component is contained (wrapped) by another component.
	Should be used only be the container (composite system) class. */
	public void runComponent() {
		Queue<Event> eventQueue = getProcessQueue();
		while (!eventQueue.isEmpty()) {
				Event event = eventQueue.remove();
				switch (event.getEvent()) {
					case "Error.HealthError": 
						controllerStatemachine.getSCIError().raiseHealthError();
					break;
					case "PoliceInterrupt.Police": 
						controllerStatemachine.getSCIPoliceInterrupt().raisePolice();
					break;
					case "PoliceInterrupt.Reset": 
						controllerStatemachine.getSCIPoliceInterrupt().raiseReset();
					break;
					default:
						throw new IllegalArgumentException("No such event!");
				}
		}
		controllerStatemachine.runCycle();
	}    		
	
	// Inner classes representing Ports
	public class SecondaryControl implements ControlInterface.Provided {
		private List<ControlInterface.Listener.Provided> registeredListeners = new LinkedList<ControlInterface.Listener.Provided>();


		@Override
		public boolean isRaisedToggle() {
			return controllerStatemachine.getSCISecondaryControl().isRaisedToggle();
		}
		@Override
		public void registerListener(ControlInterface.Listener.Provided listener) {
			registeredListeners.add(listener);
			controllerStatemachine.getSCISecondaryControl().getListeners().add(new SCISecondaryControlListener() {
				@Override
				public void onToggleRaised() {
					listener.raiseToggle();
				}
			});
		}
		
		@Override
		public List<ControlInterface.Listener.Provided> getRegisteredListeners() {
			return registeredListeners;
		}

	}
	
	@Override
	public SecondaryControl getSecondaryControl() {
		return secondaryControl;
	}
	
	public class PriorityControl implements ControlInterface.Provided {
		private List<ControlInterface.Listener.Provided> registeredListeners = new LinkedList<ControlInterface.Listener.Provided>();


		@Override
		public boolean isRaisedToggle() {
			return controllerStatemachine.getSCIPriorityControl().isRaisedToggle();
		}
		@Override
		public void registerListener(ControlInterface.Listener.Provided listener) {
			registeredListeners.add(listener);
			controllerStatemachine.getSCIPriorityControl().getListeners().add(new SCIPriorityControlListener() {
				@Override
				public void onToggleRaised() {
					listener.raiseToggle();
				}
			});
		}
		
		@Override
		public List<ControlInterface.Listener.Provided> getRegisteredListeners() {
			return registeredListeners;
		}

	}
	
	@Override
	public PriorityControl getPriorityControl() {
		return priorityControl;
	}
	
	public class PedestrianControl implements ControlInterface.Provided {
		private List<ControlInterface.Listener.Provided> registeredListeners = new LinkedList<ControlInterface.Listener.Provided>();


		@Override
		public boolean isRaisedToggle() {
			return controllerStatemachine.getSCIPedestrianControl().isRaisedToggle();
		}
		@Override
		public void registerListener(ControlInterface.Listener.Provided listener) {
			registeredListeners.add(listener);
			controllerStatemachine.getSCIPedestrianControl().getListeners().add(new SCIPedestrianControlListener() {
				@Override
				public void onToggleRaised() {
					listener.raiseToggle();
				}
			});
		}
		
		@Override
		public List<ControlInterface.Listener.Provided> getRegisteredListeners() {
			return registeredListeners;
		}

	}
	
	@Override
	public PedestrianControl getPedestrianControl() {
		return pedestrianControl;
	}
	
	public class Error implements ErrorInterface.Required {
		private List<ErrorInterface.Listener.Required> registeredListeners = new LinkedList<ErrorInterface.Listener.Required>();

		@Override
		public void raiseHealthError() {
			getInsertQueue().add(new Event("Error.HealthError", null));
		}

		@Override
		public void registerListener(ErrorInterface.Listener.Required listener) {
			registeredListeners.add(listener);
		}
		
		@Override
		public List<ErrorInterface.Listener.Required> getRegisteredListeners() {
			return registeredListeners;
		}

	}
	
	@Override
	public Error getError() {
		return error;
	}
	
	public class PedestrianPolice implements PoliceInterruptInterface.Provided {
		private List<PoliceInterruptInterface.Listener.Provided> registeredListeners = new LinkedList<PoliceInterruptInterface.Listener.Provided>();


		@Override
		public boolean isRaisedPolice() {
			return controllerStatemachine.getSCIPedestrianPolice().isRaisedPolice();
		}
		@Override
		public boolean isRaisedReset() {
			return controllerStatemachine.getSCIPedestrianPolice().isRaisedReset();
		}
		@Override
		public void registerListener(PoliceInterruptInterface.Listener.Provided listener) {
			registeredListeners.add(listener);
			controllerStatemachine.getSCIPedestrianPolice().getListeners().add(new SCIPedestrianPoliceListener() {
				@Override
				public void onPoliceRaised() {
					listener.raisePolice();
				}
				
				@Override
				public void onResetRaised() {
					listener.raiseReset();
				}
			});
		}
		
		@Override
		public List<PoliceInterruptInterface.Listener.Provided> getRegisteredListeners() {
			return registeredListeners;
		}

	}
	
	@Override
	public PedestrianPolice getPedestrianPolice() {
		return pedestrianPolice;
	}
	
	public class PoliceInterrupt implements PoliceInterruptInterface.Required {
		private List<PoliceInterruptInterface.Listener.Required> registeredListeners = new LinkedList<PoliceInterruptInterface.Listener.Required>();

		@Override
		public void raisePolice() {
			getInsertQueue().add(new Event("PoliceInterrupt.Police", null));
		}
		
		@Override
		public void raiseReset() {
			getInsertQueue().add(new Event("PoliceInterrupt.Reset", null));
		}

		@Override
		public void registerListener(PoliceInterruptInterface.Listener.Required listener) {
			registeredListeners.add(listener);
		}
		
		@Override
		public List<PoliceInterruptInterface.Listener.Required> getRegisteredListeners() {
			return registeredListeners;
		}

	}
	
	@Override
	public PoliceInterrupt getPoliceInterrupt() {
		return policeInterrupt;
	}
	
	public class PriorityPolice implements PoliceInterruptInterface.Provided {
		private List<PoliceInterruptInterface.Listener.Provided> registeredListeners = new LinkedList<PoliceInterruptInterface.Listener.Provided>();


		@Override
		public boolean isRaisedPolice() {
			return controllerStatemachine.getSCIPriorityPolice().isRaisedPolice();
		}
		@Override
		public boolean isRaisedReset() {
			return controllerStatemachine.getSCIPriorityPolice().isRaisedReset();
		}
		@Override
		public void registerListener(PoliceInterruptInterface.Listener.Provided listener) {
			registeredListeners.add(listener);
			controllerStatemachine.getSCIPriorityPolice().getListeners().add(new SCIPriorityPoliceListener() {
				@Override
				public void onPoliceRaised() {
					listener.raisePolice();
				}
				
				@Override
				public void onResetRaised() {
					listener.raiseReset();
				}
			});
		}
		
		@Override
		public List<PoliceInterruptInterface.Listener.Provided> getRegisteredListeners() {
			return registeredListeners;
		}

	}
	
	@Override
	public PriorityPolice getPriorityPolice() {
		return priorityPolice;
	}
	
	public class SecondaryPolice implements PoliceInterruptInterface.Provided {
		private List<PoliceInterruptInterface.Listener.Provided> registeredListeners = new LinkedList<PoliceInterruptInterface.Listener.Provided>();


		@Override
		public boolean isRaisedPolice() {
			return controllerStatemachine.getSCISecondaryPolice().isRaisedPolice();
		}
		@Override
		public boolean isRaisedReset() {
			return controllerStatemachine.getSCISecondaryPolice().isRaisedReset();
		}
		@Override
		public void registerListener(PoliceInterruptInterface.Listener.Provided listener) {
			registeredListeners.add(listener);
			controllerStatemachine.getSCISecondaryPolice().getListeners().add(new SCISecondaryPoliceListener() {
				@Override
				public void onPoliceRaised() {
					listener.raisePolice();
				}
				
				@Override
				public void onResetRaised() {
					listener.raiseReset();
				}
			});
		}
		
		@Override
		public List<PoliceInterruptInterface.Listener.Provided> getRegisteredListeners() {
			return registeredListeners;
		}

	}
	
	@Override
	public SecondaryPolice getSecondaryPolice() {
		return secondaryPolice;
	}
	
	/** Checks whether the wrapped statemachine is in the given state. */
	public boolean isStateActive(State state) {
		return controllerStatemachine.isStateActive(state);
	}
	
	public void setTimer(ITimer timer) {
		controllerStatemachine.setTimer(timer);
		reset();
	}
	
}
