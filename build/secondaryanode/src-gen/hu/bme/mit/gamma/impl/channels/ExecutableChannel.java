package hu.bme.mit.gamma.impl.channels;

import hu.bme.mit.gamma.impl.interfaces.ExecutableInterface;
import java.util.List;
import java.util.LinkedList;

public class ExecutableChannel implements ExecutableChannelInterface {
	
	private ExecutableInterface.Provided providedPort;
	private List<ExecutableInterface.Required> requiredPorts = new LinkedList<ExecutableInterface.Required>();
	
	public ExecutableChannel() {}
	
	public ExecutableChannel(ExecutableInterface.Provided providedPort) {
		this.providedPort = providedPort;
	}
	
	public void registerPort(ExecutableInterface.Provided providedPort) {
		// Former port is forgotten
		this.providedPort = providedPort;
		// Registering the listeners
		for (ExecutableInterface.Required requiredPort : requiredPorts) {
			providedPort.registerListener(requiredPort);
			requiredPort.registerListener(providedPort);
		}
	}
	
	public void registerPort(ExecutableInterface.Required requiredPort) {
		requiredPorts.add(requiredPort);
		// Checking whether a provided port is already given
		if (providedPort != null) {
			providedPort.registerListener(requiredPort);
			requiredPort.registerListener(providedPort);
		}
	}

}
