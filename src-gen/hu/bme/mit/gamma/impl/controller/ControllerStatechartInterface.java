package hu.bme.mit.gamma.impl.controller;

import hu.bme.mit.gamma.impl.interfaces.PoliceInterruptInterface;
import hu.bme.mit.gamma.impl.interfaces.ControlInterface;
import hu.bme.mit.gamma.impl.interfaces.ErrorInterface;

public interface ControllerStatechartInterface {
	
	PoliceInterruptInterface.Required getPoliceInterrupt();
	ControlInterface.Provided getSecondaryControl();
	PoliceInterruptInterface.Provided getPriorityPolice();
	ControlInterface.Provided getPriorityControl();
	ControlInterface.Provided getPedestrianControl();
	ErrorInterface.Required getError();
	PoliceInterruptInterface.Provided getSecondaryPolice();
	PoliceInterruptInterface.Provided getPedestrianPolice();
	
	void runCycle();
	
} 
