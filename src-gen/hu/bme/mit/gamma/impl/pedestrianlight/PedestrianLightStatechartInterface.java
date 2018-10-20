package hu.bme.mit.gamma.impl.pedestrianlight;

import hu.bme.mit.gamma.impl.interfaces.PoliceInterruptInterface;
import hu.bme.mit.gamma.impl.interfaces.ControlInterface;
import hu.bme.mit.gamma.impl.interfaces.LightCommandsInterface;

public interface PedestrianLightStatechartInterface {
	
	PoliceInterruptInterface.Required getPoliceInterrupt();
	ControlInterface.Required getControl();
	LightCommandsInterface.Provided getLightCommands();
	
	void runCycle();
	
} 
