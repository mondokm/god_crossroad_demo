package hu.bme.mit.gamma.impl.controller;

import hu.bme.mit.gamma.impl.interfaces.PoliceInterruptInterface;
import hu.bme.mit.gamma.impl.interfaces.ErrorInterface;
import hu.bme.mit.gamma.impl.interfaces.ControlInterface;

public interface ControllerStatechartInterface {
	
	PoliceInterruptInterface.Required getPoliceInterrupt();
	ErrorInterface.Required getError();
	PoliceInterruptInterface.Provided getPedestrianPolice();
	ControlInterface.Provided getSecondaryControl();
	PoliceInterruptInterface.Provided getSecondaryPolice();
	PoliceInterruptInterface.Provided getPriorityPolice();
	ControlInterface.Provided getPedestrianControl();
	ControlInterface.Provided getPriorityControl();
	
	void runCycle();
	
} 
