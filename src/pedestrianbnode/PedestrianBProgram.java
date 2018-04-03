package pedestrianbnode;

import hu.bme.mit.gamma.impl.interfaces.LightCommandsInterface;
import hu.bme.mit.gamma.impl.priornode.PriorNode;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

public class PedestrianBProgram {

	PriorNode node;
	GpioController gpio;
	GpioPinDigitalOutput greenPin, redPin;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PedestrianBProgram prog = new PedestrianBProgram();
		prog.run();
	}
	
	public void run() {
		gpio = GpioFactory.getInstance();
		greenPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02, "MyLED", PinState.LOW);
		redPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04, "MyLED", PinState.LOW);
		
		node=new PriorNode();
		node.getPriorityOutput().registerListener(new LightsListener());
	}
	
	class LightsListener implements LightCommandsInterface.Listener.Provided{

		@Override
		public void raiseDisplayNone() {
			redPin.low();
			greenPin.low();
		}

		@Override
		public void raiseDisplayGreen() {
			redPin.low();
			greenPin.high();
		}

		@Override
		public void raiseDisplayYellow() {
		}

		@Override
		public void raiseDisplayRed() {
			redPin.high();
			greenPin.low();
		}
		
	}
	
	
}