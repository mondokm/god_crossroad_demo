package hu.bme.mit.gamma.impl.pedestrianlight;

import java.util.Queue;
import java.util.List;
import java.util.LinkedList;

import hu.bme.mit.gamma.impl.event.*;
import hu.bme.mit.gamma.impl.interfaces.*;
import org.yakindu.scr.pedestrianlight.IPedestrianLightStatemachine.SCILightCommandsListener;
import org.yakindu.scr.pedestrianlight.PedestrianLightStatemachine;
import org.yakindu.scr.pedestrianlight.PedestrianLightStatemachine.State;

public class PedestrianLightStatechart implements PedestrianLightStatechartInterface {
	// The wrapped Yakindu statemachine
	private PedestrianLightStatemachine pedestrianLightStatemachine = new PedestrianLightStatemachine();
	// Port instances
	private PoliceInterrupt policeInterrupt = new PoliceInterrupt();
	private Control control = new Control();
	private LightCommands lightCommands = new LightCommands();
	// Indicates which queues are active in this cycle
	private boolean insertQueue = true;
	private boolean processQueue = false;
	// Event queues for the synchronization of statecharts
	private Queue<Event> eventQueue1 = new LinkedList<Event>();
	private Queue<Event> eventQueue2 = new LinkedList<Event>();
	
	public PedestrianLightStatechart() {
		// Initializing and entering the wrapped statemachine
		pedestrianLightStatemachine.init();
		pedestrianLightStatemachine.enter();
	}
	
	/** Resets the statemachine. Should be used only be the container (composite system) class. */
	public void reset() {
		pedestrianLightStatemachine.enter();
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
					case "PoliceInterrupt.Police": 
						pedestrianLightStatemachine.getSCIPoliceInterrupt().raisePolice();
					break;
					case "PoliceInterrupt.Reset": 
						pedestrianLightStatemachine.getSCIPoliceInterrupt().raiseReset();
					break;
					case "Control.Toggle": 
						pedestrianLightStatemachine.getSCIControl().raiseToggle();
					break;
					default:
						throw new IllegalArgumentException("No such event!");
				}
		}
		pedestrianLightStatemachine.runCycle();
	}    		
	
	// Inner classes representing Ports
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
	
	public class Control implements ControlInterface.Required {
		private List<ControlInterface.Listener.Required> registeredListeners = new LinkedList<ControlInterface.Listener.Required>();

		@Override
		public void raiseToggle() {
			getInsertQueue().add(new Event("Control.Toggle", null));
		}

		@Override
		public void registerListener(ControlInterface.Listener.Required listener) {
			registeredListeners.add(listener);
		}
		
		@Override
		public List<ControlInterface.Listener.Required> getRegisteredListeners() {
			return registeredListeners;
		}

	}
	
	@Override
	public Control getControl() {
		return control;
	}
	
	public class LightCommands implements LightCommandsInterface.Provided {
		private List<LightCommandsInterface.Listener.Provided> registeredListeners = new LinkedList<LightCommandsInterface.Listener.Provided>();


		@Override
		public boolean isRaisedDisplayRed() {
			return pedestrianLightStatemachine.getSCILightCommands().isRaisedDisplayRed();
		}
		@Override
		public boolean isRaisedDisplayYellow() {
			return pedestrianLightStatemachine.getSCILightCommands().isRaisedDisplayYellow();
		}
		@Override
		public boolean isRaisedDisplayGreen() {
			return pedestrianLightStatemachine.getSCILightCommands().isRaisedDisplayGreen();
		}
		@Override
		public boolean isRaisedDisplayNone() {
			return pedestrianLightStatemachine.getSCILightCommands().isRaisedDisplayNone();
		}
		@Override
		public void registerListener(LightCommandsInterface.Listener.Provided listener) {
			registeredListeners.add(listener);
			pedestrianLightStatemachine.getSCILightCommands().getListeners().add(new SCILightCommandsListener() {
				@Override
				public void onDisplayRedRaised() {
					listener.raiseDisplayRed();
				}
				
				@Override
				public void onDisplayYellowRaised() {
					listener.raiseDisplayYellow();
				}
				
				@Override
				public void onDisplayGreenRaised() {
					listener.raiseDisplayGreen();
				}
				
				@Override
				public void onDisplayNoneRaised() {
					listener.raiseDisplayNone();
				}
			});
		}
		
		@Override
		public List<LightCommandsInterface.Listener.Provided> getRegisteredListeners() {
			return registeredListeners;
		}

	}
	
	@Override
	public LightCommands getLightCommands() {
		return lightCommands;
	}
	
	/** Checks whether the wrapped statemachine is in the given state. */
	public boolean isStateActive(State state) {
		return pedestrianLightStatemachine.isStateActive(state);
	}
	
	
}
