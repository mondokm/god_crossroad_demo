package prioranode;

import hu.bme.mit.gamma.impl.interfaces.LightCommandsInterface;
import hu.bme.mit.gamma.impl.prioranode.PriorANode;


import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

import controllernode.Start;
import hu.bme.mit.ftsrg.monitoring.client.*;
public class PriorityPedestrianAProgram {

	PriorANode node;
	GpioController gpio;
	GpioPinDigitalOutput greenPin, yellowPin, redPin, pedRedPin, pedGreenPin;
	
    public static void main(String[] args) {
        PriorityPedestrianAProgram a = new PriorityPedestrianAProgram();
        a.run();
}
public void run(){
	Start client = new Start("PriorANode");
	client.run();
		gpio = GpioFactory.getInstance();
		greenPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_07, "MyLED", PinState.HIGH);
		yellowPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_09, "MyLED", PinState.HIGH);
		redPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_08, "MyLED", PinState.HIGH);
		
		pedGreenPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02, "MyLED", PinState.HIGH);
		pedRedPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00, "MyLED", PinState.HIGH);
		
		
		node=new PriorANode();
		node.getPriorityAOutput().registerListener(new LightsListener());
		node.getPedestrianBOutput().registerListener(new PedLightsListener());
		node.start();
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
	class PedLightsListener implements LightCommandsInterface.Listener.Provided{

		@Override
		public void raiseDisplayNone() {
			pedRedPin.low();
			pedGreenPin.low();
		}

		@Override
		public void raiseDisplayGreen() {
			pedRedPin.low();
			pedGreenPin.high();
		}

		@Override
		public void raiseDisplayYellow() {
		}

		@Override
		public void raiseDisplayRed() {
			pedRedPin.high();
			pedGreenPin.low();
		}
		
	}
	
	
}

