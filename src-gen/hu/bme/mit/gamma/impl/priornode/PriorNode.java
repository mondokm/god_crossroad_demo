package hu.bme.mit.gamma.impl.priornode;

import hu.bme.mit.gamma.ddslib.model.*;
import hu.bme.mit.gamma.ddslib.opensplice.*;

import java.util.List;
import org.yakindu.scr.ITimer;

import hu.bme.mit.gamma.impl.interfaces.*;
import hu.bme.mit.gamma.impl.channels.*;
import hu.bme.mit.gamma.impl.monitorwrapper.*;
import hu.bme.mit.gamma.impl.controllerwrapper.*;
import hu.bme.mit.gamma.impl.pedestrianlightwrapper.*;
import hu.bme.mit.gamma.impl.trafficlightwrapper.*;

public class PriorNode  {			
	// Component instances
	private TrafficLightWrapper prior = new TrafficLightWrapper();
	// Port instances
	private PriorityOutput priorityOutput = new PriorityOutput();
	// Inner channel instances
	// Outer channel topics
	private Topics priorityControlOfController;
	private Topics priorityPoliceOfController;
	
	public PriorNode() {
		init();
	}
	
	/** Enters the contained statemachines recursively. Should be used only be the container (composite system) class. */
	public void reset() {
		prior.reset();
	}
	
	/** Creates the channel mappings and enters the wrapped statemachines. */
	private void init() {
		DDSLib.init();
		// Registration of simple channels
		// Registration of broadcast channels
		// Instantiation of topics
		priorityControlOfController = new Topics("crossroad","priorityControlOfController");
		priorityControlOfController.addSubscriptionListener(new PriorityControlOfControllerListener());
		priorityPoliceOfController = new Topics("crossroad","priorityPoliceOfController");
		priorityPoliceOfController.addSubscriptionListener(new PriorityPoliceOfControllerListener());
		reset();
	}
	
	// Inner classes representing Ports
	public class PriorityOutput implements LightCommandsInterface.Provided {
	
		
		@Override
		public boolean isRaisedDisplayGreen() {
			return prior.getLightCommands().isRaisedDisplayGreen();
		}
		@Override
		public boolean isRaisedDisplayRed() {
			return prior.getLightCommands().isRaisedDisplayRed();
		}
		@Override
		public boolean isRaisedDisplayYellow() {
			return prior.getLightCommands().isRaisedDisplayYellow();
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
	
	public PriorityOutput getPriorityOutput() {
		return priorityOutput;
	}
	
	// Inner classes for publishing events
	
	// Inner classes for receiving events
	class PriorityControlOfControllerListener implements SubscriptionListener{
		public void gotMessage(String topic, String event, String params){
			switch(event){
				case "toggle": prior.getControl().raiseToggle();
						break;
			}
		}	
	}	
	class PriorityPoliceOfControllerListener implements SubscriptionListener{
		public void gotMessage(String topic, String event, String params){
			switch(event){
				case "reset": prior.getPoliceInterrupt().raiseReset();
						break;
				case "police": prior.getPoliceInterrupt().raisePolice();
						break;
			}
		}	
	}	
	
	/** Starts the running of the asynchronous node. */
	public void start() {
		prior.start();
	}
	
	/** Setter for the timer e.g., a virtual timer. */
	public void setTimer(ITimer timer) {
		prior.setTimer(timer);
	}
	
	/**  Getter for node instances, e.g. enabling to check their states. */
	public TrafficLightWrapper getPrior() {
		return prior;
	}
	
	// Method for closing the topics
	public void closeTopics(){
		priorityControlOfController.closeTopic();
		priorityPoliceOfController.closeTopic();
	}
	
}
