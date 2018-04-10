package hu.bme.mit.gamma.impl.prioranode;

import hu.bme.mit.gamma.ddslib.model.*;
import hu.bme.mit.gamma.ddslib.opensplice.*;

import java.util.List;
import org.yakindu.scr.ITimer;

import hu.bme.mit.gamma.impl.interfaces.*;
import hu.bme.mit.gamma.impl.channels.*;
import hu.bme.mit.gamma.impl.trafficlightwrapper.*;
import hu.bme.mit.gamma.impl.monitorwrapper.*;
import hu.bme.mit.gamma.impl.controllerwrapper.*;
import hu.bme.mit.gamma.impl.pedestrianlightwrapper.*;

public class PriorANode  {			
	// Component instances
	private TrafficLightWrapper priorA = new TrafficLightWrapper();
	private PedestrianLightWrapper pedestrianB = new PedestrianLightWrapper();
	// Port instances
	private PriorityAOutput priorityAOutput = new PriorityAOutput();
	private PedestrianBOutput pedestrianBOutput = new PedestrianBOutput();
	// Inner channel instances
	// Outer channel topics
	private Topics priorityControlOfController;			
	private Topics pedestrianPoliceOfController;			
	private Topics pedestrianControlOfController;			
	private Topics priorityPoliceOfController;			
	
	public PriorANode() {
		init();
	}
	
	/** Enters the contained statemachines recursively. Should be used only be the container (composite system) class. */
	public void reset() {
		priorA.reset();
		pedestrianB.reset();
	}
	
	/** Creates the channel mappings and enters the wrapped statemachines. */
	private void init() {
		DDSLib.init();
		// Registration of simple channels
		// Registration of broadcast channels
		// Instantiation of topics
		priorityControlOfController = new Topics("crossroad","priorityControlOfController");
		priorityControlOfController.addSubscriptionListener(new PriorityControlOfControllerListener());
		pedestrianPoliceOfController = new Topics("crossroad","pedestrianPoliceOfController");
		pedestrianPoliceOfController.addSubscriptionListener(new PedestrianPoliceOfControllerListener());
		pedestrianControlOfController = new Topics("crossroad","pedestrianControlOfController");
		pedestrianControlOfController.addSubscriptionListener(new PedestrianControlOfControllerListener());
		priorityPoliceOfController = new Topics("crossroad","priorityPoliceOfController");
		priorityPoliceOfController.addSubscriptionListener(new PriorityPoliceOfControllerListener());
		reset();
	}
	
	// Inner classes representing Ports
	public class PriorityAOutput implements LightCommandsInterface.Provided {
	
		
		@Override
		public boolean isRaisedDisplayYellow() {
			return priorA.getLightCommands().isRaisedDisplayYellow();
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
		public boolean isRaisedDisplayNone() {
			return priorA.getLightCommands().isRaisedDisplayNone();
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
	
	public class PedestrianBOutput implements LightCommandsInterface.Provided {
	
		
		@Override
		public boolean isRaisedDisplayYellow() {
			return pedestrianB.getLightCommands().isRaisedDisplayYellow();
		}
		@Override
		public boolean isRaisedDisplayGreen() {
			return pedestrianB.getLightCommands().isRaisedDisplayGreen();
		}
		@Override
		public boolean isRaisedDisplayRed() {
			return pedestrianB.getLightCommands().isRaisedDisplayRed();
		}
		@Override
		public boolean isRaisedDisplayNone() {
			return pedestrianB.getLightCommands().isRaisedDisplayNone();
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
	
	public PedestrianBOutput getPedestrianBOutput() {
		return pedestrianBOutput;
	}
	
	// Inner classes for publishing events
	
	// Inner classes for receiving events
	class PriorityControlOfControllerListener implements SubscriptionListener{
		public void gotMessage(String topic, String event, String params){
			switch(event){
				case "toggle": priorA.getControl().raiseToggle();
						break;
			}
		}	
	}	
	class PedestrianPoliceOfControllerListener implements SubscriptionListener{
		public void gotMessage(String topic, String event, String params){
			switch(event){
				case "reset": pedestrianB.getPoliceInterrupt().raiseReset();
						break;
				case "police": pedestrianB.getPoliceInterrupt().raisePolice();
						break;
			}
		}	
	}	
	class PedestrianControlOfControllerListener implements SubscriptionListener{
		public void gotMessage(String topic, String event, String params){
			switch(event){
				case "toggle": pedestrianB.getControl().raiseToggle();
						break;
			}
		}	
	}	
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
	
	/** Starts the running of the asynchronous node. */
	public void start() {
		priorA.start();
		pedestrianB.start();
	}
	
	/** Setter for the timer e.g., a virtual timer. */
	public void setTimer(ITimer timer) {
		priorA.setTimer(timer);
		pedestrianB.setTimer(timer);
	}
	
	/**  Getter for node instances, e.g. enabling to check their states. */
	public TrafficLightWrapper getPriorA() {
		return priorA;
	}
	
	public PedestrianLightWrapper getPedestrianB() {
		return pedestrianB;
	}
	
	// Method for closing the topics
	public void closeTopics(){
		priorityControlOfController.closeTopic();
		pedestrianPoliceOfController.closeTopic();
		pedestrianControlOfController.closeTopic();
		priorityPoliceOfController.closeTopic();
	}
	
}
