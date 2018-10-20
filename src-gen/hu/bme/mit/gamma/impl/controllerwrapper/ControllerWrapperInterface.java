package hu.bme.mit.gamma.impl.controllerwrapper;

import hu.bme.mit.gamma.impl.interfaces.PoliceInterruptInterface;
import hu.bme.mit.gamma.impl.interfaces.ControlInterface;
import hu.bme.mit.gamma.impl.interfaces.ErrorInterface;

public interface ControllerWrapperInterface {
	
	PoliceInterruptInterface.Provided getPedestrianPolice();
	PoliceInterruptInterface.Provided getSecondaryPolice();
	ControlInterface.Provided getPriorityControl();
	PoliceInterruptInterface.Required getPoliceInterrupt();
	ControlInterface.Provided getSecondaryControl();
	ErrorInterface.Required getError();
	PoliceInterruptInterface.Provided getPriorityPolice();
	ControlInterface.Provided getPedestrianControl();
	
	
} 
