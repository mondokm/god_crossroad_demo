package hu.bme.mit.gamma.impl.crossroad;

import hu.bme.mit.gamma.impl.interfaces.LightCommandsInterface;
import hu.bme.mit.gamma.impl.interfaces.PoliceInterruptInterface;

public interface CrossroadInterface {
	
	LightCommandsInterface.Provided getPedestrianBOutput();
	LightCommandsInterface.Provided getSecondaryAOutput();
	LightCommandsInterface.Provided getPedestrianAOutput();
	LightCommandsInterface.Provided getPriorityAOutput();
	LightCommandsInterface.Provided getSecondaryBOutput();
	PoliceInterruptInterface.Required getPolice();
	LightCommandsInterface.Provided getPriorityBOutput();
	
	
} 
