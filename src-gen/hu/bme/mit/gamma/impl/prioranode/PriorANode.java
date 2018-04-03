package hu.bme.mit.gamma.impl.prioranode;

import hu.bme.mit.gamma.ddslib.model.*;
import hu.bme.mit.gamma.ddslib.opensplice.*;

import java.util.List;
import org.yakindu.scr.ITimer;

import hu.bme.mit.gamma.impl.interfaces.*;
import hu.bme.mit.gamma.impl.channels.*;
import hu.bme.mit.gamma.impl.pedestrianlightwrapper.*;
import hu.bme.mit.gamma.impl.monitorwrapper.*;
import hu.bme.mit.gamma.impl.trafficlightwrapper.*;
import hu.bme.mit.gamma.impl.controllerwrapper.*;

public class PriorANode  {			
	// Component instances
	private TrafficLightWrapper priorA = new TrafficLightWrapper();
	// Port instances
	private PriorityAOutput priorityAOutput = new PriorityAOutput();
	// Inner channel instances
	// Outer channel topics
	private Topics priorityPoliceOfController;			
	private Topics priorityControlOfController;			
	
	public PriorANode() {
		init();
	}
	
	/** Enters the contained statemachines recursively. Should be used only be the container (composite system) class. */
	public void reset() {
		priorA.reset();
	}
	
	/** Creates the channel mappings and enters the wrapped statemachines. */
	private void init() {
		DDSLib.init();
		// Registration of simple channels
		// Registration of broadcast channels
		// Instantiation of topics
		priorityPoliceOfController = new Topics("crossroad","priorityPoliceOfController");
		priorityPoliceOfController.addSubscriptionListener(new PriorityPoliceOfControllerListener());
		priorityControlOfController = new Topics("crossroad","priorityControlOfController");
		priorityControlOfController.addSubscriptionListener(new PriorityControlOfControllerListener());
		reset();
	}
	
	// Inner classes representing Ports
	public class PriorityAOutput implements LightCommandsInterface.Provided {
	
		
		@Override
		public boolean isRaisedDisplayNone() {
			return priorA.getLightCommands().isRaisedDisplayNone();
		}
		@Override
		public boolean isRaisedDisplayGreen() {
			return priorA.getLightCommands().isRaisedDisplayGreen();
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
	
	public PriorityAOutput getPriorityAOutput() {
		return priorityAOutput;
	}
	
	// Inner classes for publishing events
	
	// Inner classes for receiving events
	class PriorityPoliceOfControllerListener implements SubscriptionListener{
		public void gotMessage(String topic, String event, String params){
			switch(event){
				case "reset": priorA.getPoliceInterrupt().raiseReset();
						break;
				case "police": priorA.getPoliceInterrupt().raisePolice();
						break;
			}
		}	
	}	
	class PriorityControlOfControllerListener implements SubscriptionListener{
		public void gotMessage(String topic, String event, String params){
			switch(event){
				case "toggle": priorA.getControl().raiseToggle();
						break;
			}
		}	
	}	
	
	/** Starts the running of the asynchronous node. */
	public void start() {
		priorA.start();
	}
	
	/** Setter for the timer e.g., a virtual timer. */
	public void setTimer(ITimer timer) {
		priorA.setTimer(timer);
	}
	
	/**  Getter for node instances, e.g. enabling to check their states. */
	public TrafficLightWrapper getPriorA() {
		return priorA;
	}
	
	// Method for closing the topics
	public void closeTopics(){
		priorityPoliceOfController.closeTopic();
		priorityControlOfController.closeTopic();
	}
	
}
