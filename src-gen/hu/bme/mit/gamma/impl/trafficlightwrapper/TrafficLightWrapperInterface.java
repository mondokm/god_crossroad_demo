package hu.bme.mit.gamma.impl.trafficlightwrapper;

import hu.bme.mit.gamma.impl.interfaces.LightCommandsInterface;
import hu.bme.mit.gamma.impl.interfaces.PoliceInterruptInterface;
import hu.bme.mit.gamma.impl.interfaces.ControlInterface;

public interface TrafficLightWrapperInterface {
	
	LightCommandsInterface.Provided getLightCommands();
	PoliceInterruptInterface.Required getPoliceInterrupt();
	ControlInterface.Required getControl();
	
	
} 
