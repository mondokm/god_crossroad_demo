package org.yakindu.scr.trafficlightctrl;

import java.util.List;
import org.yakindu.scr.IStatemachine;
import org.yakindu.scr.ITimerCallback;

public interface ITrafficLightCtrlStatemachine extends ITimerCallback,IStatemachine {

	public interface SCILightCommands {
	
		public boolean isRaisedDisplayRed();
		
		public boolean isRaisedDisplayGreen();
		
		public boolean isRaisedDisplayYellow();
		
		public boolean isRaisedDisplayNone();
		
	public List<SCILightCommandsListener> getListeners();
	}
	
	public interface SCILightCommandsListener {
	
		public void onDisplayRedRaised();
		public void onDisplayGreenRaised();
		public void onDisplayYellowRaised();
		public void onDisplayNoneRaised();
		}
	
	public SCILightCommands getSCILightCommands();
	
	public interface SCIPoliceInterrupt {
	
		public void raisePolice();
		
		public void raiseReset();
		
	}
	
	public SCIPoliceInterrupt getSCIPoliceInterrupt();
	
	public interface SCIControl {
	
		public void raiseToggle();
		
	}
	
	public SCIControl getSCIControl();
	
}
