package hu.bme.mit.gamma.impl.channels;

import hu.bme.mit.gamma.impl.interfaces.PoliceInterruptInterface;

public interface PoliceInterruptChannelInterface {	    	
	
	void registerPort(PoliceInterruptInterface.Provided providedPort);
	
	void registerPort(PoliceInterruptInterface.Required requiredPort);

}
