package hu.bme.mit.gamma.impl.priorbnode;

import hu.bme.mit.ftsrg.ddslib.model.*;
import hu.bme.mit.ftsrg.ddslib.opensplice.*;

import java.util.List;
import org.yakindu.scr.ITimer;

import hu.bme.mit.gamma.impl.interfaces.*;
import hu.bme.mit.gamma.impl.channels.*;
import hu.bme.mit.gamma.impl.trafficlightwrapper.*;
import hu.bme.mit.gamma.impl.controllerwrapper.*;
import hu.bme.mit.gamma.impl.pedestrianlightwrapper.*;
import hu.bme.mit.gamma.impl.monitorwrapper.*;

public class PriorBNode  {			
	// Component instances
	private TrafficLightWrapper priorB = new TrafficLightWrapper();
	// Port instances
	private PriorityBOutput priorityBOutput = new PriorityBOutput();
	// Inner channel instances
	// Outer channel topics
	private Topics priorityPoliceOfController;			
	private Topics priorityControlOfController;			
	
	public PriorBNode() {
		init();
	}
	
	/** Enters the contained statemachines recursively. Should be used only be the container (composite system) class. */
	public void reset() {
		priorB.reset();
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
	public class PriorityBOutput implements LightCommandsInterface.Provided {
	
		
		@Override
		public boolean isRaisedDisplayRed() {
			return priorB.getLightCommands().isRaisedDisplayRed();
		}
		@Override
		public boolean isRaisedDisplayNone() {
			return priorB.getLightCommands().isRaisedDisplayNone();
		}
		@Override
		public boolean isRaisedDisplayGreen() {
			return priorB.getLightCommands().isRaisedDisplayGreen();
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
	
	public PriorityBOutput getPriorityBOutput() {
		return priorityBOutput;
	}
	
	// Inner classes for publishing events
	
	// Inner classes for receiving events
	class PriorityPoliceOfControllerListener implements SubscriptionListener{
		public void gotMessage(String topic, String event, String params){
			switch(event){
				case "reset": priorB.getPoliceInterrupt().raiseReset();
						break;
				case "police": priorB.getPoliceInterrupt().raisePolice();
						break;
			}
		}	
	}	
	class PriorityControlOfControllerListener implements SubscriptionListener{
		public void gotMessage(String topic, String event, String params){
			switch(event){
				case "toggle": priorB.getControl().raiseToggle();
						break;
			}
		}	
	}	
	
	/** Starts the running of the asynchronous node. */
	public void start() {
		priorB.start();
	}
	
	/** Setter for the timer e.g., a virtual timer. */
	public void setTimer(ITimer timer) {
		priorB.setTimer(timer);
	}
	
	/**  Getter for node instances, e.g. enabling to check their states. */
	public TrafficLightWrapper getPriorB() {
		return priorB;
	}
	
	// Method for closing the topics
	public void closeTopics(){
		priorityPoliceOfController.closeTopic();
		priorityControlOfController.closeTopic();
	}
	
}
