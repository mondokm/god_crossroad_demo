package hu.bme.mit.gamma.impl.secondaryanode;

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

public class SecondaryANode  {			
	// Component instances
	private TrafficLightWrapper secondaryA = new TrafficLightWrapper();
	private PedestrianLightWrapper pedestrianA = new PedestrianLightWrapper();
	// Port instances
	private SecondaryAOutput secondaryAOutput = new SecondaryAOutput();
	private PedestrianAOutput pedestrianAOutput = new PedestrianAOutput();
	// Inner channel instances
	// Outer channel topics
	private Topics pedestrianControlOfController;			
	private Topics pedestrianPoliceOfController;			
	private Topics secondaryControlOfController;			
	private Topics secondaryPoliceOfController;			
	
	public SecondaryANode() {
		init();
	}
	
	/** Enters the contained statemachines recursively. Should be used only be the container (composite system) class. */
	public void reset() {
		secondaryA.reset();
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
		secondaryControlOfController = new Topics("crossroad","secondaryControlOfController");
		secondaryControlOfController.addSubscriptionListener(new SecondaryControlOfControllerListener());
		secondaryPoliceOfController = new Topics("crossroad","secondaryPoliceOfController");
		secondaryPoliceOfController.addSubscriptionListener(new SecondaryPoliceOfControllerListener());
		reset();
	}
	
	// Inner classes representing Ports
	public class SecondaryAOutput implements LightCommandsInterface.Provided {
	
		
		@Override
		public boolean isRaisedDisplayYellow() {
			return secondaryA.getLightCommands().isRaisedDisplayYellow();
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
		public boolean isRaisedDisplayGreen() {
			return secondaryA.getLightCommands().isRaisedDisplayGreen();
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
	
	public class PedestrianAOutput implements LightCommandsInterface.Provided {
	
		
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
		public boolean isRaisedDisplayGreen() {
			return pedestrianA.getLightCommands().isRaisedDisplayGreen();
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
	class SecondaryControlOfControllerListener implements SubscriptionListener{
		public void gotMessage(String topic, String event, String params){
			switch(event){
				case "toggle": secondaryA.getControl().raiseToggle();
						break;
			}
		}	
	}	
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
	
	/** Starts the running of the asynchronous node. */
	public void start() {
		secondaryA.start();
		pedestrianA.start();
	}
	
	/** Setter for the timer e.g., a virtual timer. */
	public void setTimer(ITimer timer) {
		secondaryA.setTimer(timer);
		pedestrianA.setTimer(timer);
	}
	
	/**  Getter for node instances, e.g. enabling to check their states. */
	public TrafficLightWrapper getSecondaryA() {
		return secondaryA;
	}
	
	public PedestrianLightWrapper getPedestrianA() {
		return pedestrianA;
	}
	
	// Method for closing the topics
	public void closeTopics(){
		pedestrianControlOfController.closeTopic();
		pedestrianPoliceOfController.closeTopic();
		secondaryControlOfController.closeTopic();
		secondaryPoliceOfController.closeTopic();
	}
	
}
