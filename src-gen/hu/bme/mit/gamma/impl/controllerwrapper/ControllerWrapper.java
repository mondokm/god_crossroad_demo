package hu.bme.mit.gamma.impl.controllerwrapper;

import java.util.Collections;
import java.util.List;

import lbmq.*; 
import org.yakindu.scr.*; 
import hu.bme.mit.gamma.impl.event.*;
import hu.bme.mit.gamma.impl.interfaces.*;

import hu.bme.mit.gamma.impl.controller.*;

public class ControllerWrapper implements Runnable, ControllerWrapperInterface {			
	// Thread running this wrapper instance
	private Thread thread;
	// Wrapped synchronous instance
	private ControllerStatechart controllerStatechart = new ControllerStatechart();
	// Control port instances
	// Wrapped port instances
	private Error error = new Error();
	private PriorityPolice priorityPolice = new PriorityPolice();
	private PedestrianControl pedestrianControl = new PedestrianControl();
	private PriorityControl priorityControl = new PriorityControl();
	private SecondaryPolice secondaryPolice = new SecondaryPolice();
	private PoliceInterrupt policeInterrupt = new PoliceInterrupt();
	private PedestrianPolice pedestrianPolice = new PedestrianPolice();
	private SecondaryControl secondaryControl = new SecondaryControl();
	// Clocks
	private ITimer timerService;
	private final int clockSignal = 0;
	// Main queue
	private LinkedBlockingMultiQueue<String, Event> __asyncQueue = new LinkedBlockingMultiQueue<String, Event>();
	// Subqueues
	private LinkedBlockingMultiQueue<String, Event>.SubQueue executionMessages;
	private LinkedBlockingMultiQueue<String, Event>.SubQueue pingMessages;
	
	public ControllerWrapper(ITimer timer) {
		setTimer(timer);
		// Init is done in setTimer
	}
	
	public ControllerWrapper() {
		timerService = new TimerService();
		init();
	}
	
	/** Enters the wrapped component. Should be used only be the container (composite system) class. */
	public void reset() {
		controllerStatechart.reset();
	}
	
	/** Creates the subqueues, clocks and enters the wrapped synchronous component. */
	private void init() {
		// Creating subqueues: the negative conversion regarding prioirities is needed,
		// beacase the lbmq defines higher priority with lower integer values
		__asyncQueue.addSubQueue("executionMessages", -(2), 8);
		executionMessages = __asyncQueue.getSubQueue("executionMessages");
		__asyncQueue.addSubQueue("pingMessages", -(1), 8);
		pingMessages = __asyncQueue.getSubQueue("pingMessages");
		// Creating clock callbacks for the single timer service
		timerService.setTimer(createTimerCallback(), clockSignal, 300, true);
		// Entering the statecharts
		reset();
		// The thread has to be started manually
	}
	
	private ITimerCallback createTimerCallback() {
		return new ITimerCallback() {
			@Override
			public void timeElapsed(int eventId) {
				switch (eventId) {
					case clockSignal:
						executionMessages.offer(new Event("clockSignal", null));
					break;
					default:
						throw new IllegalArgumentException("No such event id: " + eventId);
				}
			}
		};
	}
	
	// Inner classes representing control ports
	
	// Inner classes representing wrapped ports
	public class Error implements ErrorInterface.Required {
		
		@Override
		public void raiseHealthError() {
			pingMessages.offer(new Event("Error.healthError", null));
		}
		
		
		@Override
		public void registerListener(ErrorInterface.Listener.Required listener) {
			controllerStatechart.getError().registerListener(listener);
		}
		
		@Override
		public List<ErrorInterface.Listener.Required> getRegisteredListeners() {
			return controllerStatechart.getError().getRegisteredListeners();
		}
		
	}
	
	@Override
	public Error getError() {
		return error;
	}
	
	public class PriorityPolice implements PoliceInterruptInterface.Provided {
		
		
		@Override
		public boolean isRaisedReset() {
			return controllerStatechart.getPriorityPolice().isRaisedReset();
		}
		
		@Override
		public boolean isRaisedPolice() {
			return controllerStatechart.getPriorityPolice().isRaisedPolice();
		}
		
		@Override
		public void registerListener(PoliceInterruptInterface.Listener.Provided listener) {
			controllerStatechart.getPriorityPolice().registerListener(listener);
		}
		
		@Override
		public List<PoliceInterruptInterface.Listener.Provided> getRegisteredListeners() {
			return controllerStatechart.getPriorityPolice().getRegisteredListeners();
		}
		
	}
	
	@Override
	public PriorityPolice getPriorityPolice() {
		return priorityPolice;
	}
	
	public class PedestrianControl implements ControlInterface.Provided {
		
		
		@Override
		public boolean isRaisedToggle() {
			return controllerStatechart.getPedestrianControl().isRaisedToggle();
		}
		
		@Override
		public void registerListener(ControlInterface.Listener.Provided listener) {
			controllerStatechart.getPedestrianControl().registerListener(listener);
		}
		
		@Override
		public List<ControlInterface.Listener.Provided> getRegisteredListeners() {
			return controllerStatechart.getPedestrianControl().getRegisteredListeners();
		}
		
	}
	
	@Override
	public PedestrianControl getPedestrianControl() {
		return pedestrianControl;
	}
	
	public class PriorityControl implements ControlInterface.Provided {
		
		
		@Override
		public boolean isRaisedToggle() {
			return controllerStatechart.getPriorityControl().isRaisedToggle();
		}
		
		@Override
		public void registerListener(ControlInterface.Listener.Provided listener) {
			controllerStatechart.getPriorityControl().registerListener(listener);
		}
		
		@Override
		public List<ControlInterface.Listener.Provided> getRegisteredListeners() {
			return controllerStatechart.getPriorityControl().getRegisteredListeners();
		}
		
	}
	
	@Override
	public PriorityControl getPriorityControl() {
		return priorityControl;
	}
	
	public class SecondaryPolice implements PoliceInterruptInterface.Provided {
		
		
		@Override
		public boolean isRaisedReset() {
			return controllerStatechart.getSecondaryPolice().isRaisedReset();
		}
		
		@Override
		public boolean isRaisedPolice() {
			return controllerStatechart.getSecondaryPolice().isRaisedPolice();
		}
		
		@Override
		public void registerListener(PoliceInterruptInterface.Listener.Provided listener) {
			controllerStatechart.getSecondaryPolice().registerListener(listener);
		}
		
		@Override
		public List<PoliceInterruptInterface.Listener.Provided> getRegisteredListeners() {
			return controllerStatechart.getSecondaryPolice().getRegisteredListeners();
		}
		
	}
	
	@Override
	public SecondaryPolice getSecondaryPolice() {
		return secondaryPolice;
	}
	
	public class PoliceInterrupt implements PoliceInterruptInterface.Required {
		
		@Override
		public void raiseReset() {
			pingMessages.offer(new Event("PoliceInterrupt.reset", null));
		}
		@Override
		public void raisePolice() {
			pingMessages.offer(new Event("PoliceInterrupt.police", null));
		}
		
		
		@Override
		public void registerListener(PoliceInterruptInterface.Listener.Required listener) {
			controllerStatechart.getPoliceInterrupt().registerListener(listener);
		}
		
		@Override
		public List<PoliceInterruptInterface.Listener.Required> getRegisteredListeners() {
			return controllerStatechart.getPoliceInterrupt().getRegisteredListeners();
		}
		
	}
	
	@Override
	public PoliceInterrupt getPoliceInterrupt() {
		return policeInterrupt;
	}
	
	public class PedestrianPolice implements PoliceInterruptInterface.Provided {
		
		
		@Override
		public boolean isRaisedReset() {
			return controllerStatechart.getPedestrianPolice().isRaisedReset();
		}
		
		@Override
		public boolean isRaisedPolice() {
			return controllerStatechart.getPedestrianPolice().isRaisedPolice();
		}
		
		@Override
		public void registerListener(PoliceInterruptInterface.Listener.Provided listener) {
			controllerStatechart.getPedestrianPolice().registerListener(listener);
		}
		
		@Override
		public List<PoliceInterruptInterface.Listener.Provided> getRegisteredListeners() {
			return controllerStatechart.getPedestrianPolice().getRegisteredListeners();
		}
		
	}
	
	@Override
	public PedestrianPolice getPedestrianPolice() {
		return pedestrianPolice;
	}
	
	public class SecondaryControl implements ControlInterface.Provided {
		
		
		@Override
		public boolean isRaisedToggle() {
			return controllerStatechart.getSecondaryControl().isRaisedToggle();
		}
		
		@Override
		public void registerListener(ControlInterface.Listener.Provided listener) {
			controllerStatechart.getSecondaryControl().registerListener(listener);
		}
		
		@Override
		public List<ControlInterface.Listener.Provided> getRegisteredListeners() {
			return controllerStatechart.getSecondaryControl().getRegisteredListeners();
		}
		
	}
	
	@Override
	public SecondaryControl getSecondaryControl() {
		return secondaryControl;
	}
	
	/** Operation. */
	@Override
	public void run() {
		while (!Thread.currentThread().isInterrupted()) {
			try {
				Event event = __asyncQueue.take();		
				if (!isControlEvent(event)) {
					// Event is forwarded to the wrapped component
					forwardEvent(event);
				}
				performControlActions(event);		
			} catch (InterruptedException e) {
				thread.interrupt();
				System.out.println("Interrupted");
			}
		}
	}
	
	private boolean isControlEvent(Event event) {
		String portName = event.getEvent().split("\\.")[0];
		return portName.equals("clockSignal");
	}
	
	private void forwardEvent(Event event) {
		switch (event.getEvent()) {
			case "Error.healthError":
				controllerStatechart.getError().raiseHealthError();
			break;
			case "PoliceInterrupt.reset":
				controllerStatechart.getPoliceInterrupt().raiseReset();
			break;
			case "PoliceInterrupt.police":
				controllerStatechart.getPoliceInterrupt().raisePolice();
			break;
			default:
				throw new IllegalArgumentException("No such event!");
		}
	}
	
	private void performControlActions(Event event) {
		String[] eventName = event.getEvent().split("\\.");
		// Clock trigger
		if (eventName.length == 1 && eventName[0].equals("clockSignal")) {
			controllerStatechart.runCycle();
			return;
		}
	}
	
	/** Starts this wrapper instance on a thread. */
	public void start() {
		thread = new Thread(this);
		thread.start();
	}
	
	public boolean isWaiting() {
		return thread.getState() == Thread.State.WAITING;
	}
	
	/** Stops the thread running this wrapper instance. */
	public void interrupt() {
		thread.interrupt();
	}
	
	public ControllerStatechart getControllerStatechart() {
		return controllerStatechart;
	}
	
	public void setTimer(ITimer timer) {
		timerService = timer;
		controllerStatechart.setTimer(timer);
		init(); // To set the service into functioning state wih clocks
	}
	
	
}
