package hu.bme.mit.gamma.impl.secondarybnode;

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

public class SecondaryBNode  {			
	// Component instances
	private TrafficLightWrapper secondaryB = new TrafficLightWrapper();
	// Port instances
	private SecondaryBOutput secondaryBOutput = new SecondaryBOutput();
	// Inner channel instances
	// Outer channel topics
	private Topics secondaryControlOfController;			
	private Topics secondaryPoliceOfController;			
	
	public SecondaryBNode() {
		init();
	}
	
	/** Enters the contained statemachines recursively. Should be used only be the container (composite system) class. */
	public void reset() {
		secondaryB.reset();
	}
	
	/** Creates the channel mappings and enters the wrapped statemachines. */
	private void init() {
		DDSLib.init();
		// Registration of simple channels
		// Registration of broadcast channels
		// Instantiation of topics
		secondaryControlOfController = new Topics("crossroad","secondaryControlOfController");
		secondaryControlOfController.addSubscriptionListener(new SecondaryControlOfControllerListener());
		secondaryPoliceOfController = new Topics("crossroad","secondaryPoliceOfController");
		secondaryPoliceOfController.addSubscriptionListener(new SecondaryPoliceOfControllerListener());
		reset();
	}
	
	// Inner classes representing Ports
	public class SecondaryBOutput implements LightCommandsInterface.Provided {
	
		
		@Override
		public boolean isRaisedDisplayYellow() {
			return secondaryB.getLightCommands().isRaisedDisplayYellow();
		}
		@Override
		public boolean isRaisedDisplayGreen() {
			return secondaryB.getLightCommands().isRaisedDisplayGreen();
		}
		@Override
		public boolean isRaisedDisplayRed() {
			return secondaryB.getLightCommands().isRaisedDisplayRed();
		}
		@Override
		public boolean isRaisedDisplayNone() {
			return secondaryB.getLightCommands().isRaisedDisplayNone();
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
	
	public SecondaryBOutput getSecondaryBOutput() {
		return secondaryBOutput;
	}
	
	// Inner classes for publishing events
	
	// Inner classes for receiving events
	class SecondaryControlOfControllerListener implements SubscriptionListener{
		public void gotMessage(String topic, String event, String params){
			switch(event){
				case "toggle": secondaryB.getControl().raiseToggle();
						break;
			}
		}	
	}	
	class SecondaryPoliceOfControllerListener implements SubscriptionListener{
		public void gotMessage(String topic, String event, String params){
			switch(event){
				case "reset": secondaryB.getPoliceInterrupt().raiseReset();
						break;
				case "police": secondaryB.getPoliceInterrupt().raisePolice();
						break;
			}
		}	
	}	
	
	/** Starts the running of the asynchronous node. */
	public void start() {
		secondaryB.start();
	}
	
	/** Setter for the timer e.g., a virtual timer. */
	public void setTimer(ITimer timer) {
		secondaryB.setTimer(timer);
	}
	
	/**  Getter for node instances, e.g. enabling to check their states. */
	public TrafficLightWrapper getSecondaryB() {
		return secondaryB;
	}
	
	// Method for closing the topics
	public void closeTopics(){
		secondaryControlOfController.closeTopic();
		secondaryPoliceOfController.closeTopic();
	}
	
}
