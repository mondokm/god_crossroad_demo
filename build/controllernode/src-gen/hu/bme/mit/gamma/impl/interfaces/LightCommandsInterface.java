package hu.bme.mit.gamma.impl.interfaces;

import java.util.List;

public interface LightCommandsInterface {
	
	interface Provided extends Listener.Required {
		
		public boolean isRaisedDisplayYellow();
		public boolean isRaisedDisplayNone();
		public boolean isRaisedDisplayGreen();
		public boolean isRaisedDisplayRed();
		
		void registerListener(Listener.Provided listener);
		List<Listener.Provided> getRegisteredListeners();
	}
	
	interface Required extends Listener.Provided {
		
		
		void registerListener(Listener.Required listener);
		List<Listener.Required> getRegisteredListeners();
	}
	
	interface Listener {
		
		interface Provided  {
			void raiseDisplayYellow();
			void raiseDisplayNone();
			void raiseDisplayGreen();
			void raiseDisplayRed();
		}
		
		interface Required   {
		}
		
	}
} 
