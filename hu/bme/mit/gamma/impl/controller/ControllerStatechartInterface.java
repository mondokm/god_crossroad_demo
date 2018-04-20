package hu.bme.mit.gamma.impl.controller;

import hu.bme.mit.gamma.impl.interfaces.ErrorInterface;
import hu.bme.mit.gamma.impl.interfaces.ControlInterface;
import hu.bme.mit.gamma.impl.interfaces.PoliceInterruptInterface;

public interface ControllerStatechartInterface {
	
	ErrorInterface.Required getError();
	ControlInterface.Provided getSecondaryControl();
	PoliceInterruptInterface.Provided getPriorityPolice();
	ControlInterface.Provided getPriorityControl();
	PoliceInterruptInterface.Required getPoliceInterrupt();
	ControlInterface.Provided getPedestrianControl();
	PoliceInterruptInterface.Provided getSecondaryPolice();
	PoliceInterruptInterface.Provided getPedestrianPolice();
	
	void runCycle();
	
} 
