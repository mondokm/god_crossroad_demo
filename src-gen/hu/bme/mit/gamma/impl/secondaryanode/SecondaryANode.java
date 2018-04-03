package hu.bme.mit.gamma.impl.secondaryanode;

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

public class SecondaryANode  {			
	// Component instances
	private TrafficLightWrapper secondaryA = new TrafficLightWrapper();
	// Port instances
	private SecondaryAOutput secondaryAOutput = new SecondaryAOutput();
	// Inner channel instances
	// Outer channel topics
	private Topics secondaryPoliceOfController;			
	private Topics secondaryControlOfController;			
	
	public SecondaryANode() {
		init();
	}
	
	/** Enters the contained statemachines recursively. Should be used only be the container (composite system) class. */
	public void reset() {
		secondaryA.reset();
	}
	
	/** Creates the channel mappings and enters the wrapped statemachines. */
	private void init() {
		DDSLib.init();
		// Registration of simple channels
		// Registration of broadcast channels
		// Instantiation of topics
		secondaryPoliceOfController = new Topics("crossroad","secondaryPoliceOfController");
		secondaryPoliceOfController.addSubscriptionListener(new SecondaryPoliceOfControllerListener());
		secondaryControlOfController = new Topics("crossroad","secondaryControlOfController");
		secondaryControlOfController.addSubscriptionListener(new SecondaryControlOfControllerListener());
		reset();
	}
	
	// Inner classes representing Ports
	public class SecondaryAOutput implements LightCommandsInterface.Provided {
	
		
		@Override
		public boolean isRaisedDisplayNone() {
			return secondaryA.getLightCommands().isRaisedDisplayNone();
		}
		@Override
		public boolean isRaisedDisplayGreen() {
			return secondaryA.getLightCommands().isRaisedDisplayGreen();
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
	
	public SecondaryAOutput getSecondaryAOutput() {
		return secondaryAOutput;
	}
	
	// Inner classes for publishing events
	
	// Inner classes for receiving events
	class SecondaryPoliceOfControllerListener implements SubscriptionListener{
		public void gotMessage(String topic, String event, String params){
			switch(event){
				case "reset": secondaryA.getPoliceInterrupt().raiseReset();
						break;
				case "police": secondaryA.getPoliceInterrupt().raisePolice();
						break;
			}
		}	
	}	
	class SecondaryControlOfControllerListener implements SubscriptionListener{
		public void gotMessage(String topic, String event, String params){
			switch(event){
				case "toggle": secondaryA.getControl().raiseToggle();
						break;
			}
		}	
	}	
	
	/** Starts the running of the asynchronous node. */
	public void start() {
		secondaryA.start();
	}
	
	/** Setter for the timer e.g., a virtual timer. */
	public void setTimer(ITimer timer) {
		secondaryA.setTimer(timer);
	}
	
	/**  Getter for node instances, e.g. enabling to check their states. */
	public TrafficLightWrapper getSecondaryA() {
		return secondaryA;
	}
	
	// Method for closing the topics
	public void closeTopics(){
		secondaryPoliceOfController.closeTopic();
		secondaryControlOfController.closeTopic();
	}
	
}
