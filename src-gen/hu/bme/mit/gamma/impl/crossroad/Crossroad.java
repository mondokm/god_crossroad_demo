package hu.bme.mit.gamma.impl.crossroad;

import java.util.List;
import org.yakindu.scr.ITimer;

import hu.bme.mit.gamma.impl.interfaces.*;
import hu.bme.mit.gamma.impl.channels.*;
import hu.bme.mit.gamma.impl.controllerwrapper.*;
import hu.bme.mit.gamma.impl.trafficlightwrapper.*;
import hu.bme.mit.gamma.impl.monitorwrapper.*;
import hu.bme.mit.gamma.impl.pedestrianlightwrapper.*;

public class Crossroad implements CrossroadInterface {			
	// Component instances
	private ControllerWrapper controller = new ControllerWrapper();
	private TrafficLightWrapper priorA = new TrafficLightWrapper();
	private TrafficLightWrapper secondaryA = new TrafficLightWrapper();
	private PedestrianLightWrapper pedestrianA = new PedestrianLightWrapper();
	private TrafficLightWrapper priorB = new TrafficLightWrapper();
	private TrafficLightWrapper secondaryB = new TrafficLightWrapper();
	private PedestrianLightWrapper pedestrianB = new PedestrianLightWrapper();
	private MonitorWrapper monitor = new MonitorWrapper();
	// Port instances
	private Police police = new Police();
	private PriorityAOutput priorityAOutput = new PriorityAOutput();
	private SecondaryAOutput secondaryAOutput = new SecondaryAOutput();
	private PedestrianAOutput pedestrianAOutput = new PedestrianAOutput();
	private PriorityBOutput priorityBOutput = new PriorityBOutput();
	private SecondaryBOutput secondaryBOutput = new SecondaryBOutput();
	private PedestrianBOutput pedestrianBOutput = new PedestrianBOutput();
	// Channel instances
	private ErrorChannelInterface channelErrorOutOfMonitor;
	private ControlChannelInterface channelPriorityControlOfController;
	private PoliceInterruptChannelInterface channelSecondaryPoliceOfController;
	private PoliceInterruptChannelInterface channelPriorityPoliceOfController;
	private ControlChannelInterface channelSecondaryControlOfController;
	private ControlChannelInterface channelSecondaryControlOfController;
	private ControlChannelInterface channelPriorityControlOfController;
	private PoliceInterruptChannelInterface channelPriorityPoliceOfController;
	private PoliceInterruptChannelInterface channelSecondaryPoliceOfController;
	private PoliceInterruptChannelInterface channelPedestrianPoliceOfController;
	private PoliceInterruptChannelInterface channelPedestrianPoliceOfController;
	private ControlChannelInterface channelPedestrianControlOfController;
	private ControlChannelInterface channelPedestrianControlOfController;
	
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
		priorA.reset();
		secondaryA.reset();
		pedestrianA.reset();
		priorB.reset();
		secondaryB.reset();
		pedestrianB.reset();
		monitor.reset();
	}
	
	/** Creates the channel mappings and enters the wrapped statemachines. */
	private void init() {
		// Registration of simple channels
		channelErrorOutOfMonitor = new ErrorChannel(monitor.getErrorOut());
		channelErrorOutOfMonitor.registerPort(controller.getError());
		// Registration of broadcast channels
		channelPriorityControlOfController = new ControlChannel(controller.getPriorityControl());
		channelSecondaryPoliceOfController = new PoliceInterruptChannel(controller.getSecondaryPolice());
		channelPriorityPoliceOfController = new PoliceInterruptChannel(controller.getPriorityPolice());
		channelSecondaryControlOfController = new ControlChannel(controller.getSecondaryControl());
		channelSecondaryControlOfController = new ControlChannel(controller.getSecondaryControl());
		channelPriorityControlOfController = new ControlChannel(controller.getPriorityControl());
		channelPriorityPoliceOfController = new PoliceInterruptChannel(controller.getPriorityPolice());
		channelSecondaryPoliceOfController = new PoliceInterruptChannel(controller.getSecondaryPolice());
		channelPedestrianPoliceOfController = new PoliceInterruptChannel(controller.getPedestrianPolice());
		channelPedestrianPoliceOfController = new PoliceInterruptChannel(controller.getPedestrianPolice());
		channelPedestrianControlOfController = new ControlChannel(controller.getPedestrianControl());
		channelPedestrianControlOfController = new ControlChannel(controller.getPedestrianControl());
		reset();
	}
	
	// Inner classes representing Ports
	public class Police implements PoliceInterruptInterface.Required {
	
		@Override
		public void raiseReset() {
			controller.getPoliceInterrupt().raiseReset();
		}
		
		@Override
		public void raisePolice() {
			controller.getPoliceInterrupt().raisePolice();
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
	
	public class PriorityAOutput implements LightCommandsInterface.Provided {
	
		
		@Override
		public boolean isRaisedDisplayGreen() {
			return priorA.getLightCommands().isRaisedDisplayGreen();
		}
		@Override
		public boolean isRaisedDisplayNone() {
			return priorA.getLightCommands().isRaisedDisplayNone();
		}
		@Override
		public boolean isRaisedDisplayRed() {
			return priorA.getLightCommands().isRaisedDisplayRed();
		}
		@Override
		public boolean isRaisedDisplayYellow() {
			return priorA.getLightCommands().isRaisedDisplayYellow();
		}
		
		@Override
		public void registerListener(LightCommandsInterface.Listener.Provided listener) {
			priorA.getLightCommands().registerListener(listener);
		}
		
		@Override
		public List<LightCommandsInterface.Listener.Provided> getRegisteredListeners() {
			return priorA.getLightCommands().getRegisteredListeners();
		}
		
	}
	
	@Override
	public PriorityAOutput getPriorityAOutput() {
		return priorityAOutput;
	}
	
	public class SecondaryAOutput implements LightCommandsInterface.Provided {
	
		
		@Override
		public boolean isRaisedDisplayGreen() {
			return secondaryA.getLightCommands().isRaisedDisplayGreen();
		}
		@Override
		public boolean isRaisedDisplayNone() {
			return secondaryA.getLightCommands().isRaisedDisplayNone();
		}
		@Override
		public boolean isRaisedDisplayRed() {
			return secondaryA.getLightCommands().isRaisedDisplayRed();
		}
		@Override
		public boolean isRaisedDisplayYellow() {
			return secondaryA.getLightCommands().isRaisedDisplayYellow();
		}
		
		@Override
		public void registerListener(LightCommandsInterface.Listener.Provided listener) {
			secondaryA.getLightCommands().registerListener(listener);
		}
		
		@Override
		public List<LightCommandsInterface.Listener.Provided> getRegisteredListeners() {
			return secondaryA.getLightCommands().getRegisteredListeners();
		}
		
	}
	
	@Override
	public SecondaryAOutput getSecondaryAOutput() {
		return secondaryAOutput;
	}
	
	public class PedestrianAOutput implements LightCommandsInterface.Provided {
	
		
		@Override
		public boolean isRaisedDisplayGreen() {
			return pedestrianA.getLightCommands().isRaisedDisplayGreen();
		}
		@Override
		public boolean isRaisedDisplayNone() {
			return pedestrianA.getLightCommands().isRaisedDisplayNone();
		}
		@Override
		public boolean isRaisedDisplayRed() {
			return pedestrianA.getLightCommands().isRaisedDisplayRed();
		}
		@Override
		public boolean isRaisedDisplayYellow() {
			return pedestrianA.getLightCommands().isRaisedDisplayYellow();
		}
		
		@Override
		public void registerListener(LightCommandsInterface.Listener.Provided listener) {
			pedestrianA.getLightCommands().registerListener(listener);
		}
		
		@Override
		public List<LightCommandsInterface.Listener.Provided> getRegisteredListeners() {
			return pedestrianA.getLightCommands().getRegisteredListeners();
		}
		
	}
	
	@Override
	public PedestrianAOutput getPedestrianAOutput() {
		return pedestrianAOutput;
	}
	
	public class PriorityBOutput implements LightCommandsInterface.Provided {
	
		
		@Override
		public boolean isRaisedDisplayGreen() {
			return priorB.getLightCommands().isRaisedDisplayGreen();
		}
		@Override
		public boolean isRaisedDisplayNone() {
			return priorB.getLightCommands().isRaisedDisplayNone();
		}
		@Override
		public boolean isRaisedDisplayRed() {
			return priorB.getLightCommands().isRaisedDisplayRed();
		}
		@Override
		public boolean isRaisedDisplayYellow() {
			return priorB.getLightCommands().isRaisedDisplayYellow();
		}
		
		@Override
		public void registerListener(LightCommandsInterface.Listener.Provided listener) {
			priorB.getLightCommands().registerListener(listener);
		}
		
		@Override
		public List<LightCommandsInterface.Listener.Provided> getRegisteredListeners() {
			return priorB.getLightCommands().getRegisteredListeners();
		}
		
	}
	
	@Override
	public PriorityBOutput getPriorityBOutput() {
		return priorityBOutput;
	}
	
	public class SecondaryBOutput implements LightCommandsInterface.Provided {
	
		
		@Override
		public boolean isRaisedDisplayGreen() {
			return secondaryB.getLightCommands().isRaisedDisplayGreen();
		}
		@Override
		public boolean isRaisedDisplayNone() {
			return secondaryB.getLightCommands().isRaisedDisplayNone();
		}
		@Override
		public boolean isRaisedDisplayRed() {
			return secondaryB.getLightCommands().isRaisedDisplayRed();
		}
		@Override
		public boolean isRaisedDisplayYellow() {
			return secondaryB.getLightCommands().isRaisedDisplayYellow();
		}
		
		@Override
		public void registerListener(LightCommandsInterface.Listener.Provided listener) {
			secondaryB.getLightCommands().registerListener(listener);
		}
		
		@Override
		public List<LightCommandsInterface.Listener.Provided> getRegisteredListeners() {
			return secondaryB.getLightCommands().getRegisteredListeners();
		}
		
	}
	
	@Override
	public SecondaryBOutput getSecondaryBOutput() {
		return secondaryBOutput;
	}
	
	public class PedestrianBOutput implements LightCommandsInterface.Provided {
	
		
		@Override
		public boolean isRaisedDisplayGreen() {
			return pedestrianB.getLightCommands().isRaisedDisplayGreen();
		}
		@Override
		public boolean isRaisedDisplayNone() {
			return pedestrianB.getLightCommands().isRaisedDisplayNone();
		}
		@Override
		public boolean isRaisedDisplayRed() {
			return pedestrianB.getLightCommands().isRaisedDisplayRed();
		}
		@Override
		public boolean isRaisedDisplayYellow() {
			return pedestrianB.getLightCommands().isRaisedDisplayYellow();
		}
		
		@Override
		public void registerListener(LightCommandsInterface.Listener.Provided listener) {
			pedestrianB.getLightCommands().registerListener(listener);
		}
		
		@Override
		public List<LightCommandsInterface.Listener.Provided> getRegisteredListeners() {
			return pedestrianB.getLightCommands().getRegisteredListeners();
		}
		
	}
	
	@Override
	public PedestrianBOutput getPedestrianBOutput() {
		return pedestrianBOutput;
	}
	
	/** Starts the running of the asynchronous component. */
	public void start() {
		controller.start();
		priorA.start();
		secondaryA.start();
		pedestrianA.start();
		priorB.start();
		secondaryB.start();
		pedestrianB.start();
		monitor.start();
	}
	
	public boolean isWaiting() {
		return controller.isWaiting() && priorA.isWaiting() && secondaryA.isWaiting() && pedestrianA.isWaiting() && priorB.isWaiting() && secondaryB.isWaiting() && pedestrianB.isWaiting() && monitor.isWaiting();
	}
	
	/** Setter for the timer e.g., a virtual timer. */
	public void setTimer(ITimer timer) {
		controller.setTimer(timer);
		priorA.setTimer(timer);
		secondaryA.setTimer(timer);
		pedestrianA.setTimer(timer);
		priorB.setTimer(timer);
		secondaryB.setTimer(timer);
		pedestrianB.setTimer(timer);
	}
	
	/**  Getter for component instances, e.g. enabling to check their states. */
	public ControllerWrapper getController() {
		return controller;
	}
	
	public TrafficLightWrapper getPriorA() {
		return priorA;
	}
	
	public TrafficLightWrapper getSecondaryA() {
		return secondaryA;
	}
	
	public PedestrianLightWrapper getPedestrianA() {
		return pedestrianA;
	}
	
	public TrafficLightWrapper getPriorB() {
		return priorB;
	}
	
	public TrafficLightWrapper getSecondaryB() {
		return secondaryB;
	}
	
	public PedestrianLightWrapper getPedestrianB() {
		return pedestrianB;
	}
	
	public MonitorWrapper getMonitor() {
		return monitor;
	}
	
	
}
