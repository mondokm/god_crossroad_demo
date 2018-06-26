package hu.bme.mit.gamma.impl.trafficlightwrapper;

import hu.bme.mit.gamma.impl.interfaces.LightCommandsInterface;
import hu.bme.mit.gamma.impl.interfaces.ControlInterface;
import hu.bme.mit.gamma.impl.interfaces.PoliceInterruptInterface;

public interface TrafficLightWrapperInterface {
	
	LightCommandsInterface.Provided getLightCommands();
	ControlInterface.Required getControl();
	PoliceInterruptInterface.Required getPoliceInterrupt();
	
	
} 
