package org.yakindu.scr.controller;

import java.util.List;
import org.yakindu.scr.IStatemachine;
import org.yakindu.scr.ITimerCallback;

public interface IControllerStatemachine extends ITimerCallback,IStatemachine {

	public interface SCIPoliceInterrupt {
	
		public void raisePolice();
		
		public void raiseReset();
		
	}
	
	public SCIPoliceInterrupt getSCIPoliceInterrupt();
	
	public interface SCIPriorityPolice {
	
		public boolean isRaisedPolice();
		
		public boolean isRaisedReset();
		
	public List<SCIPriorityPoliceListener> getListeners();
	}
	
	public interface SCIPriorityPoliceListener {
	
		public void onPoliceRaised();
		public void onResetRaised();
		}
	
	public SCIPriorityPolice getSCIPriorityPolice();
	
	public interface SCISecondaryPolice {
	
		public boolean isRaisedPolice();
		
		public boolean isRaisedReset();
		
	public List<SCISecondaryPoliceListener> getListeners();
	}
	
	public interface SCISecondaryPoliceListener {
	
		public void onPoliceRaised();
		public void onResetRaised();
		}
	
	public SCISecondaryPolice getSCISecondaryPolice();
	
	public interface SCIPedestrianPolice {
	
		public boolean isRaisedPolice();
		
		public boolean isRaisedReset();
		
	public List<SCIPedestrianPoliceListener> getListeners();
	}
	
	public interface SCIPedestrianPoliceListener {
	
		public void onPoliceRaised();
		public void onResetRaised();
		}
	
	public SCIPedestrianPolice getSCIPedestrianPolice();
	
	public interface SCIPriorityControl {
	
		public boolean isRaisedToggle();
		
	public List<SCIPriorityControlListener> getListeners();
	}
	
	public interface SCIPriorityControlListener {
	
		public void onToggleRaised();
		}
	
	public SCIPriorityControl getSCIPriorityControl();
	
	public interface SCISecondaryControl {
	
		public boolean isRaisedToggle();
		
	public List<SCISecondaryControlListener> getListeners();
	}
	
	public interface SCISecondaryControlListener {
	
		public void onToggleRaised();
		}
	
	public SCISecondaryControl getSCISecondaryControl();
	
	public interface SCIPedestrianControl {
	
		public boolean isRaisedToggle();
		
	public List<SCIPedestrianControlListener> getListeners();
	}
	
	public interface SCIPedestrianControlListener {
	
		public void onToggleRaised();
		}
	
	public SCIPedestrianControl getSCIPedestrianControl();
	
	public interface SCIError {
	
		public void raiseHealthError();
		
	}
	
	public SCIError getSCIError();
	
}
