package hu.bme.mit.gamma.tutorial.start;

import io.silverspoon.bulldog.beagleboneblack.BBBNames;
import io.silverspoon.bulldog.core.Signal;
import io.silverspoon.bulldog.core.gpio.DigitalInput;
import io.silverspoon.bulldog.core.platform.Board;
import io.silverspoon.bulldog.core.platform.Platform;
import io.silverspoon.bulldog.core.util.BulldogUtil;
import io.silverspoon.bulldog.devices.switches.Button;
import io.silverspoon.bulldog.devices.switches.ButtonListener;
import java.io.IOException;

import hu.bme.mit.gamma.impl.controllernode.ControllerNode;

public class BeagleController {

	Board board;
    DigitalInput resSignal, policeSignal;
	
    Button resButton, policeButton;

    ControllerNode node;

	public static void main(String[] args) {

		PhysicalController phys=new PhysicalController();
		phys.run();
		
	}
	
	void run() {
		node=new ControllerNode();
		node.start();
		
		board = Platform.createBoard();
		resSignal = board.getPin(BBBNames.P8_11).as(DigitalInput.class);
		policeSignal = board.getPin(BBBNames.P8_12).as(DigitalInput.class);
		resButton = new Button(resSignal, Signal.Low);
		policeButton = new Button(policeSignal, Signal.Low);
				
		resButton.addListener(new ResetListener());
		policeButton.addListener(new PoliceListener());
		
	}
	
	class ResetListener implements ButtonListener{
		public void buttonPressed() {
			node.getPolice().raiseReset();
		}
	}
	
	class PoliceListener implements ButtonListener{
		public void buttonPressed() {
			node.getPolice().raisePolice();	
		}
	}
}
