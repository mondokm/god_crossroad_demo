package hu.bme.mit.gamma.impl.channels;

import hu.bme.mit.gamma.impl.interfaces.ErrorInterface;

public interface ErrorChannelInterface {	    	
	
	void registerPort(ErrorInterface.Provided providedPort);
	
	void registerPort(ErrorInterface.Required requiredPort);

}
