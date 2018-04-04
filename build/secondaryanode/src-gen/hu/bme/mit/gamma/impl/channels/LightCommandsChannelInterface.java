package hu.bme.mit.gamma.impl.channels;

import hu.bme.mit.gamma.impl.interfaces.LightCommandsInterface;

public interface LightCommandsChannelInterface {	    	
	
	void registerPort(LightCommandsInterface.Provided providedPort);
	
	void registerPort(LightCommandsInterface.Required requiredPort);

}
