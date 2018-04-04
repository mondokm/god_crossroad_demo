package hu.bme.mit.gamma.impl.crossroad;

import java.util.List;
import org.yakindu.scr.ITimer;

import hu.bme.mit.gamma.impl.interfaces.*;
import hu.bme.mit.gamma.impl.channels.*;
import hu.bme.mit.gamma.impl.controllerwrapper.*;
import hu.bme.mit.gamma.impl.monitorwrapper.*;
import hu.bme.mit.gamma.impl.trafficlightwrapper.*;
import hu.bme.mit.gamma.impl.pedestrianlightwrapper.*;

public class Crossroad implements CrossroadInterface {			
	// Component instances
	private ControllerWrapper controller = new ControllerWrapper();
	private TrafficLightWrapper prior = new TrafficLightWrapper();
	private TrafficLightWrapper secondary = new TrafficLightWrapper();
	private PedestrianLightWrapper pedestrian = new PedestrianLightWrapper();
	private MonitorWrapper monitor = new MonitorWrapper();
	// Port instances
	private Police police = new Police();
	private PriorityOutput priorityOutput = new PriorityOutput();
	private SecondaryOutput secondaryOutput = new SecondaryOutput();
	private PedestrianOutput pedestrianOutput = new PedestrianOutput();
	// Channel instances
	private ControlChannelInterface channelPriorityControlOfController;
	private PoliceInterruptChannelInterface channelPedestrianPoliceOfController;
	private ControlChannelInterface channelPedestrianControlOfController;
	private PoliceInterruptChannelInterface channelSecondaryPoliceOfController;
	private ErrorChannelInterface channelErrorOutOfMonitor;
	private ControlChannelInterface channelSecondaryControlOfController;
	private PoliceInterruptChannelInterface channelPriorityPoliceOfController;
	
	public Crossroad(ITimer timer) {
		setTimer(timer);
		init();
	}
	
	public Crossroad() {
		init();
	}
	
	/** Resets the contained statemachines recursively. Should be used only be the container (composite system) class. */
	public void reset() {
		controller.reset();
		prior.reset();
		secondary.reset();
		pedestrian.reset();
		monitor.reset();
	}
	
	/** Creates the channel mappings and enters the wrapped statemachines. */
	private void init() {
		// Registration of simple channels
		channelPriorityControlOfController = new ControlChannel(controller.getPriorityControl());
		channelPriorityControlOfController.registerPort(prior.getControl());
		channelPedestrianPoliceOfController = new PoliceInterruptChannel(controller.getPedestrianPolice());
		channelPedestrianPoliceOfController.registerPort(pedestrian.getPoliceInterrupt());
		channelPedestrianControlOfController = new ControlChannel(controller.getPedestrianControl());
		channelPedestrianControlOfController.registerPort(pedestrian.getControl());
		channelSecondaryPoliceOfController = new PoliceInterruptChannel(controller.getSecondaryPolice());
		channelSecondaryPoliceOfController.registerPort(secondary.getPoliceInterrupt());
		channelErrorOutOfMonitor = new ErrorChannel(monitor.getErrorOut());
		channelErrorOutOfMonitor.registerPort(controller.getError());
		channelSecondaryControlOfController = new ControlChannel(controller.getSecondaryControl());
		channelSecondaryControlOfController.registerPort(secondary.getControl());
		channelPriorityPoliceOfController = new PoliceInterruptChannel(controller.getPriorityPolice());
		channelPriorityPoliceOfController.registerPort(prior.getPoliceInterrupt());
		// Registration of broadcast channels
		reset();
	}
	
	// Inner classes representing Ports
	public class Police implements PoliceInterruptInterface.Required {
	
		@Override
		public void raisePolice() {
			controller.getPoliceInterrupt().raisePolice();
		}
		
		@Override
		public void raiseReset() {
			controller.getPoliceInterrupt().raiseReset();
		}
		
		
		@Override
		public void registerListener(PoliceInterruptInterface.Listener.Required listener) {
			controller.getPoliceInterrupt().registerListener(listener);
		}
		
		@Override
		public List<PoliceInterruptInterface.Listener.Required> getRegisteredListeners() {
			return controller.getPoliceInterrupt().getRegisteredListeners();
		}
		
	}
	
	@Override
	public Police getPolice() {
		return police;
	}
	
	public class PriorityOutput implements LightCommandsInterface.Provided {
	
		
		@Override
		public boolean isRaisedDisplayRed() {
			return prior.getLightCommands().isRaisedDisplayRed();
		}
		@Override
		public boolean isRaisedDisplayYellow() {
			return prior.getLightCommands().isRaisedDisplayYellow();
		}
		@Override
		public boolean isRaisedDisplayGreen() {
			return prior.getLightCommands().isRaisedDisplayGreen();
		}
		@Override
		public boolean isRaisedDisplayNone() {
			return prior.getLightCommands().isRaisedDisplayNone();
		}
		
		@Override
		public void registerListener(LightCommandsInterface.Listener.Provided listener) {
			prior.getLightCommands().registerListener(listener);
		}
		
		@Override
		public List<LightCommandsInterface.Listener.Provided> getRegisteredListeners() {
			return prior.getLightCommands().getRegisteredListeners();
		}
		
	}
	
	@Override
	public PriorityOutput getPriorityOutput() {
		return priorityOutput;
	}
	
	public class SecondaryOutput implements LightCommandsInterface.Provided {
	
		
		@Override
		public boolean isRaisedDisplayRed() {
			return secondary.getLightCommands().isRaisedDisplayRed();
		}
		@Override
		public boolean isRaisedDisplayYellow() {
			return secondary.getLightCommands().isRaisedDisplayYellow();
		}
		@Override
		public boolean isRaisedDisplayGreen() {
			return secondary.getLightCommands().isRaisedDisplayGreen();
		}
		@Override
		public boolean isRaisedDisplayNone() {
			return secondary.getLightCommands().isRaisedDisplayNone();
		}
		
		@Override
		public void registerListener(LightCommandsInterface.Listener.Provided listener) {
			secondary.getLightCommands().registerListener(listener);
		}
		
		@Override
		public List<LightCommandsInterface.Listener.Provided> getRegisteredListeners() {
			return secondary.getLightCommands().getRegisteredListeners();
		}
		
	}
	
	@Override
	public SecondaryOutput getSecondaryOutput() {
		return secondaryOutput;
	}
	
	public class PedestrianOutput implements LightCommandsInterface.Provided {
	
		
		@Override
		public boolean isRaisedDisplayRed() {
			return pedestrian.getLightCommands().isRaisedDisplayRed();
		}
		@Override
		public boolean isRaisedDisplayYellow() {
			return pedestrian.getLightCommands().isRaisedDisplayYellow();
		}
		@Override
		public boolean isRaisedDisplayGreen() {
			return pedestrian.getLightCommands().isRaisedDisplayGreen();
		}
		@Override
		public boolean isRaisedDisplayNone() {
			return pedestrian.getLightCommands().isRaisedDisplayNone();
		}
		
		@Override
		public void registerListener(LightCommandsInterface.Listener.Provided listener) {
			pedestrian.getLightCommands().registerListener(listener);
		}
		
		@Override
		public List<LightCommandsInterface.Listener.Provided> getRegisteredListeners() {
			return pedestrian.getLightCommands().getRegisteredListeners();
		}
		
	}
	
	@Override
	public PedestrianOutput getPedestrianOutput() {
		return pedestrianOutput;
	}
	
	/** Starts the running of the asynchronous component. */
	public void start() {
		controller.start();
		prior.start();
		secondary.start();
		pedestrian.start();
		monitor.start();
	}
	
	public boolean isWaiting() {
		return controller.isWaiting() && prior.isWaiting() && secondary.isWaiting() && pedestrian.isWaiting() && monitor.isWaiting();
	}
	
	/** Setter for the timer e.g., a virtual timer. */
	public void setTimer(ITimer timer) {
		controller.setTimer(timer);
		prior.setTimer(timer);
		secondary.setTimer(timer);
	}
	
	/**  Getter for component instances, e.g. enabling to check their states. */
	public ControllerWrapper getController() {
		return controller;
	}
	
	public TrafficLightWrapper getPrior() {
		return prior;
	}
	
	public TrafficLightWrapper getSecondary() {
		return secondary;
	}
	
	public PedestrianLightWrapper getPedestrian() {
		return pedestrian;
	}
	
	public MonitorWrapper getMonitor() {
		return monitor;
	}
	
	
}
