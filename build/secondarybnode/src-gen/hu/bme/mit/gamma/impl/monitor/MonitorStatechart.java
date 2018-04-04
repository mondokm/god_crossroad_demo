package hu.bme.mit.gamma.impl.monitor;

import java.util.Queue;
import java.util.List;
import java.util.LinkedList;

import hu.bme.mit.gamma.impl.event.*;
import hu.bme.mit.gamma.impl.interfaces.*;
import org.yakindu.scr.monitor.IMonitorStatemachine.SCIErrorOutListener;
import org.yakindu.scr.monitor.MonitorStatemachine;
import org.yakindu.scr.monitor.MonitorStatemachine.State;

public class MonitorStatechart implements MonitorStatechartInterface {
	// The wrapped Yakindu statemachine
	private MonitorStatemachine monitorStatemachine = new MonitorStatemachine();
	// Port instances
	private ErrorOut errorOut = new ErrorOut();
	// Indicates which queues are active in this cycle
	private boolean insertQueue = true;
	private boolean processQueue = false;
	// Event queues for the synchronization of statecharts
	private Queue<Event> eventQueue1 = new LinkedList<Event>();
	private Queue<Event> eventQueue2 = new LinkedList<Event>();
	
	public MonitorStatechart() {
		// Initializing and entering the wrapped statemachine
		monitorStatemachine.init();
		monitorStatemachine.enter();
	}
	
	/** Resets the statemachine. Should be used only be the container (composite system) class. */
	public void reset() {
		monitorStatemachine.enter();
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
					default:
						throw new IllegalArgumentException("No such event!");
				}
		}
		monitorStatemachine.runCycle();
	}    		
	
	// Inner classes representing Ports
	public class ErrorOut implements ErrorInterface.Provided {
		private List<ErrorInterface.Listener.Provided> registeredListeners = new LinkedList<ErrorInterface.Listener.Provided>();


		@Override
		public boolean isRaisedHealthError() {
			return monitorStatemachine.getSCIErrorOut().isRaisedHealthError();
		}
		@Override
		public void registerListener(ErrorInterface.Listener.Provided listener) {
			registeredListeners.add(listener);
			monitorStatemachine.getSCIErrorOut().getListeners().add(new SCIErrorOutListener() {
				@Override
				public void onHealthErrorRaised() {
					listener.raiseHealthError();
				}
			});
		}
		
		@Override
		public List<ErrorInterface.Listener.Provided> getRegisteredListeners() {
			return registeredListeners;
		}

	}
	
	@Override
	public ErrorOut getErrorOut() {
		return errorOut;
	}
	
	/** Checks whether the wrapped statemachine is in the given state. */
	public boolean isStateActive(State state) {
		return monitorStatemachine.isStateActive(state);
	}
	
	
}
