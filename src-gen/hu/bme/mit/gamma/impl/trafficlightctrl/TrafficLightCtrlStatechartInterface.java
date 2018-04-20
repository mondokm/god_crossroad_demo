package hu.bme.mit.gamma.impl.trafficlightctrl;

import hu.bme.mit.gamma.impl.interfaces.LightCommandsInterface;
import hu.bme.mit.gamma.impl.interfaces.PoliceInterruptInterface;
import hu.bme.mit.gamma.impl.interfaces.ControlInterface;

public interface TrafficLightCtrlStatechartInterface {
	
	LightCommandsInterface.Provided getLightCommands();
	PoliceInterruptInterface.Required getPoliceInterrupt();
	ControlInterface.Required getControl();
	
	void runCycle();
	
} 
