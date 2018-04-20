package hu.bme.mit.gamma.impl.pedestrianlight;

import hu.bme.mit.gamma.impl.interfaces.ControlInterface;
import hu.bme.mit.gamma.impl.interfaces.PoliceInterruptInterface;
import hu.bme.mit.gamma.impl.interfaces.LightCommandsInterface;

public interface PedestrianLightStatechartInterface {
	
	ControlInterface.Required getControl();
	PoliceInterruptInterface.Required getPoliceInterrupt();
	LightCommandsInterface.Provided getLightCommands();
	
	void runCycle();
	
} 
