package hu.bme.mit.gamma.impl.crossroad;

import hu.bme.mit.gamma.impl.interfaces.LightCommandsInterface;
import hu.bme.mit.gamma.impl.interfaces.PoliceInterruptInterface;

public interface CrossroadInterface {
	
	LightCommandsInterface.Provided getPedestrianOutput();
	PoliceInterruptInterface.Required getPolice();
	LightCommandsInterface.Provided getPriorityOutput();
	LightCommandsInterface.Provided getSecondaryOutput();
	
	
} 
