package hu.bme.mit.gamma.impl.controller;

import hu.bme.mit.gamma.impl.interfaces.PoliceInterruptInterface;
import hu.bme.mit.gamma.impl.interfaces.ControlInterface;
import hu.bme.mit.gamma.impl.interfaces.ErrorInterface;

public interface ControllerStatechartInterface {
	
	PoliceInterruptInterface.Provided getPedestrianPolice();
	ControlInterface.Provided getPriorityControl();
	PoliceInterruptInterface.Provided getSecondaryPolice();
	PoliceInterruptInterface.Required getPoliceInterrupt();
	ControlInterface.Provided getSecondaryControl();
	ErrorInterface.Required getError();
	PoliceInterruptInterface.Provided getPriorityPolice();
	ControlInterface.Provided getPedestrianControl();
	
	void runCycle();
	
} 
