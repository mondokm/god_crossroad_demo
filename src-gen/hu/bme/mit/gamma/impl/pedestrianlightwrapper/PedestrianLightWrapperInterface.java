package hu.bme.mit.gamma.impl.pedestrianlightwrapper;

import hu.bme.mit.gamma.impl.interfaces.ControlInterface;
import hu.bme.mit.gamma.impl.interfaces.PoliceInterruptInterface;
import hu.bme.mit.gamma.impl.interfaces.LightCommandsInterface;

public interface PedestrianLightWrapperInterface {
	
	ControlInterface.Required getControl();
	PoliceInterruptInterface.Required getPoliceInterrupt();
	LightCommandsInterface.Provided getLightCommands();
	
	
} 
