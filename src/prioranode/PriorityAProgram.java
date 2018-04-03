package prioranode;

import hu.bme.mit.gamma.impl.interfaces.LightCommandsInterface;
import hu.bme.mit.gamma.impl.priornode.PriorNode;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

public class PriorityAProgram {

	PriorNode node;
	GpioController gpio;
	GpioPinDigitalOutput greenPin, yellowPin, redPin;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PriorityAProgram prog = new PriorityAProgram();
		prog.run();
	}
	
	public void run() {
		gpio = GpioFactory.getInstance();
		greenPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02, "MyLED", PinState.LOW);
		yellowPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03, "MyLED", PinState.LOW);
		redPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04, "MyLED", PinState.LOW);
		
		node=new PriorNode();
		node.getPriorityOutput().registerListener(new LightsListener());
	}
	
	class LightsListener implements LightCommandsInterface.Listener.Provided{

		@Override
		public void raiseDisplayNone() {
			redPin.low();
			yellowPin.low();
			greenPin.low();
		}

		@Override
		public void raiseDisplayGreen() {
			redPin.low();
			yellowPin.low();
			greenPin.high();
		}

		@Override
		public void raiseDisplayYellow() {
			redPin.low();
			yellowPin.high();
			greenPin.low();
		}

		@Override
		public void raiseDisplayRed() {
			redPin.high();
			yellowPin.low();
			greenPin.low();
		}
		
	}
	
	
}
