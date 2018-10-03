package hu.bme.mit.gamma.impl.monitor;

import hu.bme.mit.gamma.impl.interfaces.ErrorInterface;

public interface MonitorStatechartInterface {
	
	ErrorInterface.Provided getErrorOut();
	
	void runCycle();
	
} 
