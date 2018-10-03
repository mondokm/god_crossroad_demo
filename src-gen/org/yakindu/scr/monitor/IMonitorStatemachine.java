package org.yakindu.scr.monitor;

import java.util.List;
import org.yakindu.scr.IStatemachine;

public interface IMonitorStatemachine extends IStatemachine {

	public interface SCIErrorOut {
	
		public boolean isRaisedHealthError();
		
	public List<SCIErrorOutListener> getListeners();
	}
	
	public interface SCIErrorOutListener {
	
		public void onHealthErrorRaised();
		}
	
	public SCIErrorOut getSCIErrorOut();
	
}
