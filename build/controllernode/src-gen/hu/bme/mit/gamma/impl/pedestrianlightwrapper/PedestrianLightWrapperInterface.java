package hu.bme.mit.gamma.impl.pedestrianlightwrapper;

import hu.bme.mit.gamma.impl.interfaces.ControlInterface;
import hu.bme.mit.gamma.impl.interfaces.LightCommandsInterface;
import hu.bme.mit.gamma.impl.interfaces.PoliceInterruptInterface;

public interface PedestrianLightWrapperInterface {
	
	ControlInterface.Required getControl();
	LightCommandsInterface.Provided getLightCommands();
	PoliceInterruptInterface.Required getPoliceInterrupt();
	
	
} 
