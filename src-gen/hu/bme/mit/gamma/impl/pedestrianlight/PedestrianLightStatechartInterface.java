package hu.bme.mit.gamma.impl.pedestrianlight;

import hu.bme.mit.gamma.impl.interfaces.ControlInterface;
import hu.bme.mit.gamma.impl.interfaces.LightCommandsInterface;
import hu.bme.mit.gamma.impl.interfaces.PoliceInterruptInterface;

public interface PedestrianLightStatechartInterface {
	
	ControlInterface.Required getControl();
	LightCommandsInterface.Provided getLightCommands();
	PoliceInterruptInterface.Required getPoliceInterrupt();
	
	void runCycle();
	
} 
