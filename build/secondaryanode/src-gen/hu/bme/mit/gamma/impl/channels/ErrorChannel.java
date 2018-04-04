package hu.bme.mit.gamma.impl.channels;

import hu.bme.mit.gamma.impl.interfaces.ErrorInterface;
import java.util.List;
import java.util.LinkedList;

public class ErrorChannel implements ErrorChannelInterface {
	
	private ErrorInterface.Provided providedPort;
	private List<ErrorInterface.Required> requiredPorts = new LinkedList<ErrorInterface.Required>();
	
	public ErrorChannel() {}
	
	public ErrorChannel(ErrorInterface.Provided providedPort) {
		this.providedPort = providedPort;
	}
	
	public void registerPort(ErrorInterface.Provided providedPort) {
		// Former port is forgotten
		this.providedPort = providedPort;
		// Registering the listeners
		for (ErrorInterface.Required requiredPort : requiredPorts) {
			providedPort.registerListener(requiredPort);
			requiredPort.registerListener(providedPort);
		}
	}
	
	public void registerPort(ErrorInterface.Required requiredPort) {
		requiredPorts.add(requiredPort);
		// Checking whether a provided port is already given
		if (providedPort != null) {
			providedPort.registerListener(requiredPort);
			requiredPort.registerListener(providedPort);
		}
	}

}
