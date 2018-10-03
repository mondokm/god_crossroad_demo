package hu.bme.mit.gamma.impl.pedestrianlight;

import hu.bme.mit.gamma.impl.interfaces.LightCommandsInterface;
import hu.bme.mit.gamma.impl.interfaces.PoliceInterruptInterface;
import hu.bme.mit.gamma.impl.interfaces.ControlInterface;

public interface PedestrianLightStatechartInterface {
	
	LightCommandsInterface.Provided getLightCommands();
	PoliceInterruptInterface.Required getPoliceInterrupt();
	ControlInterface.Required getControl();
	
	void runCycle();
	
} 
