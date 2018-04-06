package hu.bme.mit.gamma.impl.pedestriananode;

import hu.bme.mit.gamma.ddslib.model.*;
import hu.bme.mit.gamma.ddslib.opensplice.*;

import java.util.List;
import org.yakindu.scr.ITimer;

import hu.bme.mit.gamma.impl.interfaces.*;
import hu.bme.mit.gamma.impl.channels.*;
import hu.bme.mit.gamma.impl.monitorwrapper.*;
import hu.bme.mit.gamma.impl.pedestrianlightwrapper.*;
import hu.bme.mit.gamma.impl.trafficlightwrapper.*;
import hu.bme.mit.gamma.impl.controllerwrapper.*;

public class PedestrianANode  {			
	// Component instances
	private PedestrianLightWrapper pedestrianA = new PedestrianLightWrapper();
	// Port instances
	private PedestrianAOutput pedestrianAOutput = new PedestrianAOutput();
	// Inner channel instances
	// Outer channel topics
	private Topics pedestrianControlOfController;			
	private Topics pedestrianPoliceOfController;			
	
	public PedestrianANode() {
		init();
	}
	
	/** Enters the contained statemachines recursively. Should be used only be the container (composite system) class. */
	public void reset() {
		pedestrianA.reset();
	}
	
	/** Creates the channel mappings and enters the wrapped statemachines. */
	private void init() {
		DDSLib.init();
		// Registration of simple channels
		// Registration of broadcast channels
		// Instantiation of topics
		pedestrianControlOfController = new Topics("crossroad","pedestrianControlOfController");
		pedestrianControlOfController.addSubscriptionListener(new PedestrianControlOfControllerListener());
		pedestrianPoliceOfController = new Topics("crossroad","pedestrianPoliceOfController");
		pedestrianPoliceOfController.addSubscriptionListener(new PedestrianPoliceOfControllerListener());
		reset();
	}
	
	// Inner classes representing Ports
	public class PedestrianAOutput implements LightCommandsInterface.Provided {
	
		
		@Override
		public boolean isRaisedDisplayGreen() {
			return pedestrianA.getLightCommands().isRaisedDisplayGreen();
		}
		@Override
		public boolean isRaisedDisplayYellow() {
			return pedestrianA.getLightCommands().isRaisedDisplayYellow();
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
		public void registerListener(LightCommandsInterface.Listener.Provided listener) {
			pedestrianA.getLightCommands().registerListener(listener);
		}
		
		@Override
		public List<LightCommandsInterface.Listener.Provided> getRegisteredListeners() {
			return pedestrianA.getLightCommands().getRegisteredListeners();
		}
		
	}
	
	public PedestrianAOutput getPedestrianAOutput() {
		return pedestrianAOutput;
	}
	
	// Inner classes for publishing events
	
	// Inner classes for receiving events
	class PedestrianControlOfControllerListener implements SubscriptionListener{
		public void gotMessage(String topic, String event, String params){
			switch(event){
				case "toggle": pedestrianA.getControl().raiseToggle();
						break;
			}
		}	
	}	
	class PedestrianPoliceOfControllerListener implements SubscriptionListener{
		public void gotMessage(String topic, String event, String params){
			switch(event){
				case "reset": pedestrianA.getPoliceInterrupt().raiseReset();
						break;
				case "police": pedestrianA.getPoliceInterrupt().raisePolice();
						break;
			}
		}	
	}	
	
	/** Starts the running of the asynchronous node. */
	public void start() {
		pedestrianA.start();
	}
	
	/** Setter for the timer e.g., a virtual timer. */
	public void setTimer(ITimer timer) {
		pedestrianA.setTimer(timer);
	}
	
	/**  Getter for node instances, e.g. enabling to check their states. */
	public PedestrianLightWrapper getPedestrianA() {
		return pedestrianA;
	}
	
	// Method for closing the topics
	public void closeTopics(){
		pedestrianControlOfController.closeTopic();
		pedestrianPoliceOfController.closeTopic();
	}
	
}
