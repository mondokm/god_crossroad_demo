package hu.bme.mit.gamma.impl.crossroad;

import hu.bme.mit.gamma.impl.interfaces.PoliceInterruptInterface;
import hu.bme.mit.gamma.impl.interfaces.LightCommandsInterface;

public interface CrossroadInterface {
	
	PoliceInterruptInterface.Required getPolice();
	LightCommandsInterface.Provided getSecondaryAOutput();
	LightCommandsInterface.Provided getPedestrianAOutput();
	LightCommandsInterface.Provided getSecondaryBOutput();
	LightCommandsInterface.Provided getPedestrianBOutput();
	LightCommandsInterface.Provided getPriorityBOutput();
	LightCommandsInterface.Provided getPriorityAOutput();
	
	
} 
