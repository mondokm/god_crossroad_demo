package hu.bme.mit.gamma.impl.channels;

import hu.bme.mit.gamma.impl.interfaces.ControlInterface;
import java.util.List;
import java.util.LinkedList;

public class ControlChannel implements ControlChannelInterface {
	
	private ControlInterface.Provided providedPort;
	private List<ControlInterface.Required> requiredPorts = new LinkedList<ControlInterface.Required>();
	
	public ControlChannel() {}
	
	public ControlChannel(ControlInterface.Provided providedPort) {
		this.providedPort = providedPort;
	}
	
	public void registerPort(ControlInterface.Provided providedPort) {
		// Former port is forgotten
		this.providedPort = providedPort;
		// Registering the listeners
		for (ControlInterface.Required requiredPort : requiredPorts) {
			providedPort.registerListener(requiredPort);
			requiredPort.registerListener(providedPort);
		}
	}
	
	public void registerPort(ControlInterface.Required requiredPort) {
		requiredPorts.add(requiredPort);
		// Checking whether a provided port is already given
		if (providedPort != null) {
			providedPort.registerListener(requiredPort);
			requiredPort.registerListener(providedPort);
		}
	}

}
