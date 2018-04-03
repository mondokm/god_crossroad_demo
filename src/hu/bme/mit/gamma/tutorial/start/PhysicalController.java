package hu.bme.mit.gamma.tutorial.start;

import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

import hu.bme.mit.gamma.impl.controllernode.ControllerNode;

public class PhysicalController {
	
	GpioController gpio;
	GpioPinDigitalInput resButton, intButton;
	ControllerNode node;

	public static void main(String[] args) {

		PhysicalController phys=new PhysicalController();
		phys.run();
		
	}
	
	void run() {
		node=new ControllerNode();
		node.start();
		
		gpio=GpioFactory.getInstance();
		resButton = gpio.provisionDigitalInputPin(RaspiPin.GPIO_09,PinPullResistance.PULL_DOWN);
		intButton = gpio.provisionDigitalInputPin(RaspiPin.GPIO_07,PinPullResistance.PULL_DOWN);
		
		resButton.addListener(new ResetListener());
		intButton.addListener(new PoliceListener());
		
	}
	
	class ResetListener implements GpioPinListenerDigital{
		public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
			node.getPolice().raiseReset();
		}
	}
	
	class PoliceListener implements GpioPinListenerDigital{
		public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
			node.getPolice().raisePolice();
		}
	}

}
