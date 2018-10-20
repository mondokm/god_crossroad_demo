package hu.bme.mit.gamma.impl.pedestrianlightwrapper;

import java.util.Collections;
import java.util.List;

import lbmq.*; 
import org.yakindu.scr.*; 
import hu.bme.mit.gamma.impl.event.*;
import hu.bme.mit.gamma.impl.interfaces.*;

import hu.bme.mit.gamma.impl.pedestrianlight.*;

public class PedestrianLightWrapper implements Runnable, PedestrianLightWrapperInterface {			
	// Thread running this wrapper instance
	private Thread thread;
	// Wrapped synchronous instance
	private PedestrianLightStatechart pedestrianLightStatechart = new PedestrianLightStatechart();
	// Control port instances
	// Wrapped port instances
	private LightCommands lightCommands = new LightCommands();
	private PoliceInterrupt policeInterrupt = new PoliceInterrupt();
	private Control control = new Control();
	// Clocks
	private ITimer timerService;
	private final int clockSignal = 0;
	// Main queue
	private LinkedBlockingMultiQueue<String, Event> __asyncQueue = new LinkedBlockingMultiQueue<String, Event>();
	// Subqueues
	private LinkedBlockingMultiQueue<String, Event>.SubQueue executionMessages;
	private LinkedBlockingMultiQueue<String, Event>.SubQueue pingMessages;
	
	public PedestrianLightWrapper(ITimer timer) {
		setTimer(timer);
		// Init is done in setTimer
	}
	
	public PedestrianLightWrapper() {
		timerService = new TimerService();
		init();
	}
	
	/** Enters the wrapped component. Should be used only be the container (composite system) class. */
	public void reset() {
		pedestrianLightStatechart.reset();
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
	public class LightCommands implements LightCommandsInterface.Provided {
		
		
		@Override
		public boolean isRaisedDisplayGreen() {
			return pedestrianLightStatechart.getLightCommands().isRaisedDisplayGreen();
		}
		
		@Override
		public boolean isRaisedDisplayRed() {
			return pedestrianLightStatechart.getLightCommands().isRaisedDisplayRed();
		}
		
		@Override
		public boolean isRaisedDisplayNone() {
			return pedestrianLightStatechart.getLightCommands().isRaisedDisplayNone();
		}
		
		@Override
		public boolean isRaisedDisplayYellow() {
			return pedestrianLightStatechart.getLightCommands().isRaisedDisplayYellow();
		}
		
		@Override
		public void registerListener(LightCommandsInterface.Listener.Provided listener) {
			pedestrianLightStatechart.getLightCommands().registerListener(listener);
		}
		
		@Override
		public List<LightCommandsInterface.Listener.Provided> getRegisteredListeners() {
			return pedestrianLightStatechart.getLightCommands().getRegisteredListeners();
		}
		
	}
	
	@Override
	public LightCommands getLightCommands() {
		return lightCommands;
	}
	
	public class PoliceInterrupt implements PoliceInterruptInterface.Required {
		
		@Override
		public void raisePolice() {
			pingMessages.offer(new Event("PoliceInterrupt.police", null));
		}
		@Override
		public void raiseReset() {
			pingMessages.offer(new Event("PoliceInterrupt.reset", null));
		}
		
		
		@Override
		public void registerListener(PoliceInterruptInterface.Listener.Required listener) {
			pedestrianLightStatechart.getPoliceInterrupt().registerListener(listener);
		}
		
		@Override
		public List<PoliceInterruptInterface.Listener.Required> getRegisteredListeners() {
			return pedestrianLightStatechart.getPoliceInterrupt().getRegisteredListeners();
		}
		
	}
	
	@Override
	public PoliceInterrupt getPoliceInterrupt() {
		return policeInterrupt;
	}
	
	public class Control implements ControlInterface.Required {
		
		@Override
		public void raiseToggle() {
			pingMessages.offer(new Event("Control.toggle", null));
		}
		
		
		@Override
		public void registerListener(ControlInterface.Listener.Required listener) {
			pedestrianLightStatechart.getControl().registerListener(listener);
		}
		
		@Override
		public List<ControlInterface.Listener.Required> getRegisteredListeners() {
			return pedestrianLightStatechart.getControl().getRegisteredListeners();
		}
		
	}
	
	@Override
	public Control getControl() {
		return control;
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
			case "PoliceInterrupt.police":
				pedestrianLightStatechart.getPoliceInterrupt().raisePolice();
			break;
			case "PoliceInterrupt.reset":
				pedestrianLightStatechart.getPoliceInterrupt().raiseReset();
			break;
			case "Control.toggle":
				pedestrianLightStatechart.getControl().raiseToggle();
			break;
			default:
				throw new IllegalArgumentException("No such event!");
		}
	}
	
	private void performControlActions(Event event) {
		String[] eventName = event.getEvent().split("\\.");
		// Clock trigger
		if (eventName.length == 1 && eventName[0].equals("clockSignal")) {
			pedestrianLightStatechart.runCycle();
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
	
	public PedestrianLightStatechart getPedestrianLightStatechart() {
		return pedestrianLightStatechart;
	}
	
	public void setTimer(ITimer timer) {
		timerService = timer;
		pedestrianLightStatechart.setTimer(timer);
		init(); // To set the service into functioning state wih clocks
	}
	
	
}
