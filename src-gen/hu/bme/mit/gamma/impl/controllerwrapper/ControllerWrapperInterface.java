package hu.bme.mit.gamma.impl.controllerwrapper;

import hu.bme.mit.gamma.impl.interfaces.ErrorInterface;
import hu.bme.mit.gamma.impl.interfaces.ControlInterface;
import hu.bme.mit.gamma.impl.interfaces.PoliceInterruptInterface;

public interface ControllerWrapperInterface {
	
	ErrorInterface.Required getError();
	ControlInterface.Provided getSecondaryControl();
	PoliceInterruptInterface.Provided getPriorityPolice();
	ControlInterface.Provided getPriorityControl();
	PoliceInterruptInterface.Required getPoliceInterrupt();
	ControlInterface.Provided getPedestrianControl();
	PoliceInterruptInterface.Provided getSecondaryPolice();
	PoliceInterruptInterface.Provided getPedestrianPolice();
	
	
} 
