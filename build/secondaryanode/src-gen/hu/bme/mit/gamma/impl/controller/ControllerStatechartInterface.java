package hu.bme.mit.gamma.impl.controller;

import hu.bme.mit.gamma.impl.interfaces.ControlInterface;
import hu.bme.mit.gamma.impl.interfaces.PoliceInterruptInterface;
import hu.bme.mit.gamma.impl.interfaces.ErrorInterface;

public interface ControllerStatechartInterface {
	
	ControlInterface.Provided getPriorityControl();
	PoliceInterruptInterface.Required getPoliceInterrupt();
	PoliceInterruptInterface.Provided getPriorityPolice();
	ControlInterface.Provided getPedestrianControl();
	PoliceInterruptInterface.Provided getSecondaryPolice();
	ControlInterface.Provided getSecondaryControl();
	ErrorInterface.Required getError();
	PoliceInterruptInterface.Provided getPedestrianPolice();
	
	void runCycle();
	
} 
