package hu.bme.mit.gamma.impl.controllernode;

import hu.bme.mit.ftsrg.ddslib.model.*;
import hu.bme.mit.ftsrg.ddslib.opensplice.*;

import java.util.List;
import org.yakindu.scr.ITimer;

import hu.bme.mit.gamma.impl.interfaces.*;
import hu.bme.mit.gamma.impl.channels.*;
import hu.bme.mit.gamma.impl.trafficlightwrapper.*;
import hu.bme.mit.gamma.impl.pedestrianlightwrapper.*;
import hu.bme.mit.gamma.impl.monitorwrapper.*;
import hu.bme.mit.gamma.impl.controllerwrapper.*;

public class ControllerNode  {			
	// Component instances
	private ControllerWrapper controller = new ControllerWrapper();
	// Port instances
	private Police police = new Police();
	// Inner channel instances
	// Outer channel topics
	private Topics errorOutOfMonitor;
	private Topics priorityPoliceOfController;			
	private Topics priorityControlOfController;			
	private Topics pedestrianControlOfController;			
	private Topics pedestrianPoliceOfController;			
	private Topics secondaryControlOfController;			
	private Topics secondaryPoliceOfController;			
	
	public ControllerNode() {
		init();
	}
	
	/** Enters the contained statemachines recursively. Should be used only be the container (composite system) class. */
	public void reset() {
		controller.reset();
	}
	
	/** Creates the channel mappings and enters the wrapped statemachines. */
	private void init() {
		DDSLib.init();
		// Registration of simple channels
		// Registration of broadcast channels
		// Instantiation of topics
		errorOutOfMonitor = new Topics("crossroad","errorOutOfMonitor");
		errorOutOfMonitor.addSubscriptionListener(new ErrorOutOfMonitorListener());
		priorityPoliceOfController = new Topics("crossroad","priorityPoliceOfController");
		controller.getPriorityPolice().registerListener(new PriorityPoliceOfControllerListener());
		priorityControlOfController = new Topics("crossroad","priorityControlOfController");
		controller.getPriorityControl().registerListener(new PriorityControlOfControllerListener());
		pedestrianControlOfController = new Topics("crossroad","pedestrianControlOfController");
		controller.getPedestrianControl().registerListener(new PedestrianControlOfControllerListener());
		pedestrianPoliceOfController = new Topics("crossroad","pedestrianPoliceOfController");
		controller.getPedestrianPolice().registerListener(new PedestrianPoliceOfControllerListener());
		secondaryControlOfController = new Topics("crossroad","secondaryControlOfController");
		controller.getSecondaryControl().registerListener(new SecondaryControlOfControllerListener());
		secondaryPoliceOfController = new Topics("crossroad","secondaryPoliceOfController");
		controller.getSecondaryPolice().registerListener(new SecondaryPoliceOfControllerListener());
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
	
	public Police getPolice() {
		return police;
	}
	
	// Inner classes for publishing events
	class PriorityPoliceOfControllerListener implements PoliceInterruptInterface.Listener.Provided {
	
		public void raiseReset() {
			priorityPoliceOfController.publishEvent("reset", "");
		}	
			
		public void raisePolice() {
			priorityPoliceOfController.publishEvent("police", "");
		}	
			
	
	}	
	class PriorityControlOfControllerListener implements ControlInterface.Listener.Provided {
	
		public void raiseToggle() {
			priorityControlOfController.publishEvent("toggle", "");
		}	
			
	
	}	
	class PedestrianControlOfControllerListener implements ControlInterface.Listener.Provided {
	
		public void raiseToggle() {
			pedestrianControlOfController.publishEvent("toggle", "");
		}	
			
	
	}	
	class PedestrianPoliceOfControllerListener implements PoliceInterruptInterface.Listener.Provided {
	
		public void raiseReset() {
			pedestrianPoliceOfController.publishEvent("reset", "");
		}	
			
		public void raisePolice() {
			pedestrianPoliceOfController.publishEvent("police", "");
		}	
			
	
	}	
	class SecondaryControlOfControllerListener implements ControlInterface.Listener.Provided {
	
		public void raiseToggle() {
			secondaryControlOfController.publishEvent("toggle", "");
		}	
			
	
	}	
	class SecondaryPoliceOfControllerListener implements PoliceInterruptInterface.Listener.Provided {
	
		public void raiseReset() {
			secondaryPoliceOfController.publishEvent("reset", "");
		}	
			
		public void raisePolice() {
			secondaryPoliceOfController.publishEvent("police", "");
		}	
			
	
	}	
	
	// Inner classes for receiving events
	class ErrorOutOfMonitorListener implements SubscriptionListener{
		public void gotMessage(String topic, String event, String params){
			switch(event){
				case "healthError": controller.getError().raiseHealthError();
						break;
			}
		}	
	}	
	
	/** Starts the running of the asynchronous node. */
	public void start() {
		controller.start();
	}
	
	/** Setter for the timer e.g., a virtual timer. */
	public void setTimer(ITimer timer) {
		controller.setTimer(timer);
	}
	
	/**  Getter for node instances, e.g. enabling to check their states. */
	public ControllerWrapper getController() {
		return controller;
	}
	
	// Method for closing the topics
	public void closeTopics(){
		errorOutOfMonitor.closeTopic();
		priorityPoliceOfController.closeTopic();
		priorityControlOfController.closeTopic();
		pedestrianControlOfController.closeTopic();
		pedestrianPoliceOfController.closeTopic();
		secondaryControlOfController.closeTopic();
		secondaryPoliceOfController.closeTopic();
	}
	
}
