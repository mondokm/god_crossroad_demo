package hu.bme.mit.gamma.impl.channels;

import hu.bme.mit.gamma.impl.interfaces.ExecutableInterface;

public interface ExecutableChannelInterface {	    	
	
	void registerPort(ExecutableInterface.Provided providedPort);
	
	void registerPort(ExecutableInterface.Required requiredPort);

}
