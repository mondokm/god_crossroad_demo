package hu.bme.mit.gamma.impl.trafficlightctrl;

import hu.bme.mit.gamma.impl.interfaces.PoliceInterruptInterface;
import hu.bme.mit.gamma.impl.interfaces.ControlInterface;
import hu.bme.mit.gamma.impl.interfaces.LightCommandsInterface;

public interface TrafficLightCtrlStatechartInterface {
	
	PoliceInterruptInterface.Required getPoliceInterrupt();
	ControlInterface.Required getControl();
	LightCommandsInterface.Provided getLightCommands();
	
	void runCycle();
	
} 
