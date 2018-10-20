package hu.bme.mit.gamma.impl.trafficlightwrapper;

import hu.bme.mit.gamma.impl.interfaces.PoliceInterruptInterface;
import hu.bme.mit.gamma.impl.interfaces.ControlInterface;
import hu.bme.mit.gamma.impl.interfaces.LightCommandsInterface;

public interface TrafficLightWrapperInterface {
	
	PoliceInterruptInterface.Required getPoliceInterrupt();
	ControlInterface.Required getControl();
	LightCommandsInterface.Provided getLightCommands();
	
	
} 
