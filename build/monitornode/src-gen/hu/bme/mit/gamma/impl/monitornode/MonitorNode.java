package hu.bme.mit.gamma.impl.monitornode;

import hu.bme.mit.gamma.ddslib.model.*;
import hu.bme.mit.gamma.ddslib.opensplice.*;

import java.util.List;
import org.yakindu.scr.ITimer;

import hu.bme.mit.gamma.impl.interfaces.*;
import hu.bme.mit.gamma.impl.channels.*;
import hu.bme.mit.gamma.impl.trafficlightwrapper.*;
import hu.bme.mit.gamma.impl.pedestrianlightwrapper.*;
import hu.bme.mit.gamma.impl.controllerwrapper.*;
import hu.bme.mit.gamma.impl.monitorwrapper.*;

public class MonitorNode  {			
	// Component instances
	private MonitorWrapper monitor = new MonitorWrapper();
	// Port instances
	// Inner channel instances
	// Outer channel topics
	private Topics errorOutOfMonitor;
	
	public MonitorNode() {
		init();
	}
	
	/** Enters the contained statemachines recursively. Should be used only be the container (composite system) class. */
	public void reset() {
		monitor.reset();
	}
	
	/** Creates the channel mappings and enters the wrapped statemachines. */
	private void init() {
		DDSLib.init();
		// Registration of simple channels
		// Registration of broadcast channels
		// Instantiation of topics
		errorOutOfMonitor = new Topics("crossroad","errorOutOfMonitor");
		monitor.getErrorOut().registerListener(new ErrorOutOfMonitorListener());
		reset();
	}
	
	// Inner classes representing Ports
	
	// Inner classes for publishing events
	class ErrorOutOfMonitorListener implements ErrorInterface.Listener.Provided {
	
		public void raiseHealthError() {
			errorOutOfMonitor.publishEvent("healthError", "");
		}	
			
	
	}	
	
	// Inner classes for receiving events
	
	/** Starts the running of the asynchronous node. */
	public void start() {
		monitor.start();
	}
	
	/** Setter for the timer e.g., a virtual timer. */
	public void setTimer(ITimer timer) {
	}
	
	/**  Getter for node instances, e.g. enabling to check their states. */
	public MonitorWrapper getMonitor() {
		return monitor;
	}
	
	// Method for closing the topics
	public void closeTopics(){
		errorOutOfMonitor.closeTopic();
	}
	
}
