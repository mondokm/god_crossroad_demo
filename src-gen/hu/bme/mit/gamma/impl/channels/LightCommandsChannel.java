package hu.bme.mit.gamma.impl.channels;

import hu.bme.mit.gamma.impl.interfaces.LightCommandsInterface;
import java.util.List;
import java.util.LinkedList;

public class LightCommandsChannel implements LightCommandsChannelInterface {
	
	private LightCommandsInterface.Provided providedPort;
	private List<LightCommandsInterface.Required> requiredPorts = new LinkedList<LightCommandsInterface.Required>();
	
	public LightCommandsChannel() {}
	
	public LightCommandsChannel(LightCommandsInterface.Provided providedPort) {
		this.providedPort = providedPort;
	}
	
	public void registerPort(LightCommandsInterface.Provided providedPort) {
		// Former port is forgotten
		this.providedPort = providedPort;
		// Registering the listeners
		for (LightCommandsInterface.Required requiredPort : requiredPorts) {
			providedPort.registerListener(requiredPort);
			requiredPort.registerListener(providedPort);
		}
	}
	
	public void registerPort(LightCommandsInterface.Required requiredPort) {
		requiredPorts.add(requiredPort);
		// Checking whether a provided port is already given
		if (providedPort != null) {
			providedPort.registerListener(requiredPort);
			requiredPort.registerListener(providedPort);
		}
	}

}
