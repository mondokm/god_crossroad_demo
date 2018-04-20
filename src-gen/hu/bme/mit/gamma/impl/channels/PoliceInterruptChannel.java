package hu.bme.mit.gamma.impl.channels;

import hu.bme.mit.gamma.impl.interfaces.PoliceInterruptInterface;
import java.util.List;
import java.util.LinkedList;

public class PoliceInterruptChannel implements PoliceInterruptChannelInterface {
	
	private PoliceInterruptInterface.Provided providedPort;
	private List<PoliceInterruptInterface.Required> requiredPorts = new LinkedList<PoliceInterruptInterface.Required>();
	
	public PoliceInterruptChannel() {}
	
	public PoliceInterruptChannel(PoliceInterruptInterface.Provided providedPort) {
		this.providedPort = providedPort;
	}
	
	public void registerPort(PoliceInterruptInterface.Provided providedPort) {
		// Former port is forgotten
		this.providedPort = providedPort;
		// Registering the listeners
		for (PoliceInterruptInterface.Required requiredPort : requiredPorts) {
			providedPort.registerListener(requiredPort);
			requiredPort.registerListener(providedPort);
		}
	}
	
	public void registerPort(PoliceInterruptInterface.Required requiredPort) {
		requiredPorts.add(requiredPort);
		// Checking whether a provided port is already given
		if (providedPort != null) {
			providedPort.registerListener(requiredPort);
			requiredPort.registerListener(providedPort);
		}
	}

}
