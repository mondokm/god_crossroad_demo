package secondarybnode;

import hu.bme.mit.gamma.impl.interfaces.LightCommandsInterface;
import hu.bme.mit.gamma.impl.secondarybnode.SecondaryBNode;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

public class SecondaryBProgram {

	SecondaryBNode node;
	GpioController gpio;
	GpioPinDigitalOutput greenPin, yellowPin, redPin;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SecondaryBProgram prog = new SecondaryBProgram();
		prog.run();
	}
	
	public void run() {
		gpio = GpioFactory.getInstance();
		greenPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_07, "MyLED", PinState.HIGH);
		yellowPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_09, "MyLED", PinState.HIGH);
		redPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_08, "MyLED", PinState.HIGH);
		
		node=new SecondaryBNode();
		node.getSecondaryBOutput().registerListener(new LightsListener());
		node.start();	
}
	
	class LightsListener implements LightCommandsInterface.Listener.Provided{

		@Override
		public void raiseDisplayNone() {
			redPin.low();
			yellowPin.low();
			greenPin.low();
			System.out.println("none");
		}

		@Override
		public void raiseDisplayGreen() {
			redPin.low();
			yellowPin.low();
			greenPin.high();
			System.out.println("green");
		}

		@Override
		public void raiseDisplayYellow() {
			redPin.low();
			yellowPin.high();
			greenPin.low();
			System.out.println("yellow");
		}

		@Override
		public void raiseDisplayRed() {
			redPin.high();
			yellowPin.low();
			greenPin.low();
			System.out.println("red");
		}
		
	}
	
	
}
