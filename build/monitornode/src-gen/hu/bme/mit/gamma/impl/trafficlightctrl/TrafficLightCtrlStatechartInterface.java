package hu.bme.mit.gamma.impl.trafficlightctrl;

import hu.bme.mit.gamma.impl.interfaces.LightCommandsInterface;
import hu.bme.mit.gamma.impl.interfaces.ControlInterface;
import hu.bme.mit.gamma.impl.interfaces.PoliceInterruptInterface;

public interface TrafficLightCtrlStatechartInterface {
	
	LightCommandsInterface.Provided getLightCommands();
	ControlInterface.Required getControl();
	PoliceInterruptInterface.Required getPoliceInterrupt();
	
	void runCycle();
	
} 
