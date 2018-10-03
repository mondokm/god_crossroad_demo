package hu.bme.mit.gamma.impl.controllerwrapper;

import hu.bme.mit.gamma.impl.interfaces.PoliceInterruptInterface;
import hu.bme.mit.gamma.impl.interfaces.ErrorInterface;
import hu.bme.mit.gamma.impl.interfaces.ControlInterface;

public interface ControllerWrapperInterface {
	
	PoliceInterruptInterface.Required getPoliceInterrupt();
	ErrorInterface.Required getError();
	PoliceInterruptInterface.Provided getPedestrianPolice();
	ControlInterface.Provided getSecondaryControl();
	PoliceInterruptInterface.Provided getSecondaryPolice();
	PoliceInterruptInterface.Provided getPriorityPolice();
	ControlInterface.Provided getPedestrianControl();
	ControlInterface.Provided getPriorityControl();
	
	
} 
