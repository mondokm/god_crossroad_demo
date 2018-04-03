package hu.bme.mit.gamma.impl.pedestrianbnode;

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

public class PedestrianBNode  {			
	// Component instances
	private PedestrianLightWrapper pedestrianB = new PedestrianLightWrapper();
	// Port instances
	private PedestrianBOutput pedestrianBOutput = new PedestrianBOutput();
	// Inner channel instances
	// Outer channel topics
	private Topics pedestrianPoliceOfController;			
	private Topics pedestrianControlOfController;			
	
	public PedestrianBNode() {
		init();
	}
	
	/** Enters the contained statemachines recursively. Should be used only be the container (composite system) class. */
	public void reset() {
		pedestrianB.reset();
	}
	
	/** Creates the channel mappings and enters the wrapped statemachines. */
	private void init() {
		DDSLib.init();
		// Registration of simple channels
		// Registration of broadcast channels
		// Instantiation of topics
		pedestrianPoliceOfController = new Topics("crossroad","pedestrianPoliceOfController");
		pedestrianPoliceOfController.addSubscriptionListener(new PedestrianPoliceOfControllerListener());
		pedestrianControlOfController = new Topics("crossroad","pedestrianControlOfController");
		pedestrianControlOfController.addSubscriptionListener(new PedestrianControlOfControllerListener());
		reset();
	}
	
	// Inner classes representing Ports
	public class PedestrianBOutput implements LightCommandsInterface.Provided {
	
		
		@Override
		public boolean isRaisedDisplayNone() {
			return pedestrianB.getLightCommands().isRaisedDisplayNone();
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
	
	public PedestrianBOutput getPedestrianBOutput() {
		return pedestrianBOutput;
	}
	
	// Inner classes for publishing events
	
	// Inner classes for receiving events
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
	
	/** Starts the running of the asynchronous node. */
	public void start() {
		pedestrianB.start();
	}
	
	/** Setter for the timer e.g., a virtual timer. */
	public void setTimer(ITimer timer) {
		pedestrianB.setTimer(timer);
	}
	
	/**  Getter for node instances, e.g. enabling to check their states. */
	public PedestrianLightWrapper getPedestrianB() {
		return pedestrianB;
	}
	
	// Method for closing the topics
	public void closeTopics(){
		pedestrianPoliceOfController.closeTopic();
		pedestrianControlOfController.closeTopic();
	}
	
}
