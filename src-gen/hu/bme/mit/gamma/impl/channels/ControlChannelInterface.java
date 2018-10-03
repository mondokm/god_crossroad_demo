package hu.bme.mit.gamma.impl.channels;

import hu.bme.mit.gamma.impl.interfaces.ControlInterface;

public interface ControlChannelInterface {	    	
	
	void registerPort(ControlInterface.Provided providedPort);
	
	void registerPort(ControlInterface.Required requiredPort);

}
