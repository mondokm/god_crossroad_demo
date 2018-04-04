package hu.bme.mit.gamma.impl.controllerwrapper;

import hu.bme.mit.gamma.impl.interfaces.ControlInterface;
import hu.bme.mit.gamma.impl.interfaces.PoliceInterruptInterface;
import hu.bme.mit.gamma.impl.interfaces.ErrorInterface;

public interface ControllerWrapperInterface {
	
	ControlInterface.Provided getPriorityControl();
	PoliceInterruptInterface.Required getPoliceInterrupt();
	PoliceInterruptInterface.Provided getPriorityPolice();
	ControlInterface.Provided getPedestrianControl();
	PoliceInterruptInterface.Provided getSecondaryPolice();
	PoliceInterruptInterface.Provided getPedestrianPolice();
	ErrorInterface.Required getError();
	ControlInterface.Provided getSecondaryControl();
	
	
} 
