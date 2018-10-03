package org.yakindu.scr.trafficlightctrl;
import java.util.LinkedList;
import java.util.List;
import org.yakindu.scr.ITimer;

public class TrafficLightCtrlStatemachine implements ITrafficLightCtrlStatemachine {

	protected class SCILightCommandsImpl implements SCILightCommands {
	
		private List<SCILightCommandsListener> listeners = new LinkedList<SCILightCommandsListener>();
		
		public List<SCILightCommandsListener> getListeners() {
			return listeners;
		}
		private boolean displayRed;
		
		public boolean isRaisedDisplayRed() {
			return displayRed;
		}
		
		protected void raiseDisplayRed() {
			displayRed = true;
			for (SCILightCommandsListener listener : listeners) {
				listener.onDisplayRedRaised();
			}
		}
		
		private boolean displayGreen;
		
		public boolean isRaisedDisplayGreen() {
			return displayGreen;
		}
		
		protected void raiseDisplayGreen() {
			displayGreen = true;
			for (SCILightCommandsListener listener : listeners) {
				listener.onDisplayGreenRaised();
			}
		}
		
		private boolean displayYellow;
		
		public boolean isRaisedDisplayYellow() {
			return displayYellow;
		}
		
		protected void raiseDisplayYellow() {
			displayYellow = true;
			for (SCILightCommandsListener listener : listeners) {
				listener.onDisplayYellowRaised();
			}
		}
		
		private boolean displayNone;
		
		public boolean isRaisedDisplayNone() {
			return displayNone;
		}
		
		protected void raiseDisplayNone() {
			displayNone = true;
			for (SCILightCommandsListener listener : listeners) {
				listener.onDisplayNoneRaised();
			}
		}
		
		protected void clearEvents() {
		}
		protected void clearOutEvents() {
		
		displayRed = false;
		displayGreen = false;
		displayYellow = false;
		displayNone = false;
		}
		
	}
	
	protected SCILightCommandsImpl sCILightCommands;
	
	protected class SCIPoliceInterruptImpl implements SCIPoliceInterrupt {
	
		private boolean police;
		
		public void raisePolice() {
			police = true;
		}
		
		private boolean reset;
		
		public void raiseReset() {
			reset = true;
		}
		
		protected void clearEvents() {
			police = false;
			reset = false;
		}
	}
	
	protected SCIPoliceInterruptImpl sCIPoliceInterrupt;
	
	protected class SCIControlImpl implements SCIControl {
	
		private boolean toggle;
		
		public void raiseToggle() {
			toggle = true;
		}
		
		protected void clearEvents() {
			toggle = false;
		}
	}
	
	protected SCIControlImpl sCIControl;
	
	private boolean initialized = false;
	
	public enum State {
		main_region_Normal,
		main_region_Normal_normal_Yellow,
		main_region_Normal_normal_Green,
		main_region_Normal_normal_Red,
		main_region_Interrupted,
		main_region_Interrupted_interrupted_Black,
		main_region_Interrupted_interrupted_Yellow,
		$NullState$
	};
	
	private State[] historyVector = new State[1];
	private final State[] stateVector = new State[1];
	
	private int nextStateIndex;
	
	private ITimer timer;
	
	private final boolean[] timeEvents = new boolean[2];
	
	
	public TrafficLightCtrlStatemachine() {
		sCILightCommands = new SCILightCommandsImpl();
		sCIPoliceInterrupt = new SCIPoliceInterruptImpl();
		sCIControl = new SCIControlImpl();
	}
	
	public void init() {
		this.initialized = true;
		if (timer == null) {
			throw new IllegalStateException("timer not set.");
		}
		
		for (int i = 0; i < 1; i++) {
			stateVector[i] = State.$NullState$;
		}
		for (int i = 0; i < 1; i++) {
			historyVector[i] = State.$NullState$;
		}
		clearEvents();
		clearOutEvents();
	}
	
	public void enter() {
		if (!initialized) {
			throw new IllegalStateException(
					"The state machine needs to be initialized first by calling the init() function.");
		}
		if (timer == null) {
			throw new IllegalStateException("timer not set.");
		}
	
		enterSequence_main_region_default();
	}
	
	public void exit() {
		exitSequence_main_region();
	}
	
	/**
	 * @see IStatemachine#isActive()
	 */
	public boolean isActive() {
		return stateVector[0] != State.$NullState$;
	}
	
	/** 
	* Always returns 'false' since this state machine can never become final.
	*
	* @see IStatemachine#isFinal()
	*/
	public boolean isFinal() {
		return false;
	}
	/**
	* This method resets the incoming events (time events included).
	*/
	protected void clearEvents() {
		sCILightCommands.clearEvents();
		sCIPoliceInterrupt.clearEvents();
		sCIControl.clearEvents();
		for (int i=0; i<timeEvents.length; i++) {
			timeEvents[i] = false;
		}
	}
	
	/**
	* This method resets the outgoing events.
	*/
	protected void clearOutEvents() {
		sCILightCommands.clearOutEvents();
	}
	
	/**
	* Returns true if the given state is currently active otherwise false.
	*/
	public boolean isStateActive(State state) {
	
		switch (state) {
		case main_region_Normal:
			return stateVector[0].ordinal() >= State.
					main_region_Normal.ordinal()&& stateVector[0].ordinal() <= State.main_region_Normal_normal_Red.ordinal();
		case main_region_Normal_normal_Yellow:
			return stateVector[0] == State.main_region_Normal_normal_Yellow;
		case main_region_Normal_normal_Green:
			return stateVector[0] == State.main_region_Normal_normal_Green;
		case main_region_Normal_normal_Red:
			return stateVector[0] == State.main_region_Normal_normal_Red;
		case main_region_Interrupted:
			return stateVector[0].ordinal() >= State.
					main_region_Interrupted.ordinal()&& stateVector[0].ordinal() <= State.main_region_Interrupted_interrupted_Yellow.ordinal();
		case main_region_Interrupted_interrupted_Black:
			return stateVector[0] == State.main_region_Interrupted_interrupted_Black;
		case main_region_Interrupted_interrupted_Yellow:
			return stateVector[0] == State.main_region_Interrupted_interrupted_Yellow;
		default:
			return false;
		}
	}
	
	/**
	* Set the {@link ITimer} for the state machine. It must be set
	* externally on a timed state machine before a run cycle can be correct
	* executed.
	* 
	* @param timer
	*/
	public void setTimer(ITimer timer) {
		this.timer = timer;
	}
	
	/**
	* Returns the currently used timer.
	* 
	* @return {@link ITimer}
	*/
	public ITimer getTimer() {
		return timer;
	}
	
	public void timeElapsed(int eventID) {
		timeEvents[eventID] = true;
	}
	
	public SCILightCommands getSCILightCommands() {
		return sCILightCommands;
	}
	
	public SCIPoliceInterrupt getSCIPoliceInterrupt() {
		return sCIPoliceInterrupt;
	}
	
	public SCIControl getSCIControl() {
		return sCIControl;
	}
	
	private boolean check_main_region_Normal_tr0_tr0() {
		return sCIPoliceInterrupt.police;
	}
	
	private boolean check_main_region_Normal_tr1_tr1() {
		return sCIPoliceInterrupt.reset;
	}
	
	private boolean check_main_region_Normal_normal_Yellow_tr0_tr0() {
		return sCIControl.toggle;
	}
	
	private boolean check_main_region_Normal_normal_Green_tr0_tr0() {
		return sCIControl.toggle;
	}
	
	private boolean check_main_region_Normal_normal_Red_tr0_tr0() {
		return sCIControl.toggle;
	}
	
	private boolean check_main_region_Interrupted_tr0_tr0() {
		return sCIPoliceInterrupt.police;
	}
	
	private boolean check_main_region_Interrupted_tr1_tr1() {
		return sCIPoliceInterrupt.reset;
	}
	
	private boolean check_main_region_Interrupted_interrupted_Black_tr0_tr0() {
		return timeEvents[0];
	}
	
	private boolean check_main_region_Interrupted_interrupted_Yellow_tr0_tr0() {
		return timeEvents[1];
	}
	
	private void effect_main_region_Normal_tr0() {
		exitSequence_main_region_Normal();
		enterSequence_main_region_Interrupted_default();
	}
	
	private void effect_main_region_Normal_tr1() {
		exitSequence_main_region_Normal();
		enterSequence_main_region_Normal_normal_Red_default();
	}
	
	private void effect_main_region_Normal_normal_Yellow_tr0() {
		exitSequence_main_region_Normal_normal_Yellow();
		enterSequence_main_region_Normal_normal_Red_default();
	}
	
	private void effect_main_region_Normal_normal_Green_tr0() {
		exitSequence_main_region_Normal_normal_Green();
		enterSequence_main_region_Normal_normal_Yellow_default();
	}
	
	private void effect_main_region_Normal_normal_Red_tr0() {
		exitSequence_main_region_Normal_normal_Red();
		enterSequence_main_region_Normal_normal_Green_default();
	}
	
	private void effect_main_region_Interrupted_tr0() {
		exitSequence_main_region_Interrupted();
		enterSequence_main_region_Normal_default();
	}
	
	private void effect_main_region_Interrupted_tr1() {
		exitSequence_main_region_Interrupted();
		enterSequence_main_region_Normal_normal_Red_default();
	}
	
	private void effect_main_region_Interrupted_interrupted_Black_tr0() {
		exitSequence_main_region_Interrupted_interrupted_Black();
		enterSequence_main_region_Interrupted_interrupted_Yellow_default();
	}
	
	private void effect_main_region_Interrupted_interrupted_Yellow_tr0() {
		exitSequence_main_region_Interrupted_interrupted_Yellow();
		enterSequence_main_region_Interrupted_interrupted_Black_default();
	}
	
	/* Entry action for state 'Yellow'. */
	private void entryAction_main_region_Normal_normal_Yellow() {
		sCILightCommands.raiseDisplayYellow();
	}
	
	/* Entry action for state 'Green'. */
	private void entryAction_main_region_Normal_normal_Green() {
		sCILightCommands.raiseDisplayGreen();
	}
	
	/* Entry action for state 'Red'. */
	private void entryAction_main_region_Normal_normal_Red() {
		sCILightCommands.raiseDisplayRed();
	}
	
	/* Entry action for state 'Black'. */
	private void entryAction_main_region_Interrupted_interrupted_Black() {
		timer.setTimer(this, 0, 500, false);
		
		sCILightCommands.raiseDisplayNone();
	}
	
	/* Entry action for state 'Yellow'. */
	private void entryAction_main_region_Interrupted_interrupted_Yellow() {
		timer.setTimer(this, 1, 500, false);
		
		sCILightCommands.raiseDisplayYellow();
	}
	
	/* Exit action for state 'Black'. */
	private void exitAction_main_region_Interrupted_interrupted_Black() {
		timer.unsetTimer(this, 0);
	}
	
	/* Exit action for state 'Yellow'. */
	private void exitAction_main_region_Interrupted_interrupted_Yellow() {
		timer.unsetTimer(this, 1);
	}
	
	/* 'default' enter sequence for state Normal */
	private void enterSequence_main_region_Normal_default() {
		enterSequence_main_region_Normal_normal_default();
	}
	
	/* 'default' enter sequence for state Yellow */
	private void enterSequence_main_region_Normal_normal_Yellow_default() {
		entryAction_main_region_Normal_normal_Yellow();
		nextStateIndex = 0;
		stateVector[0] = State.main_region_Normal_normal_Yellow;
		
		historyVector[0] = stateVector[0];
	}
	
	/* 'default' enter sequence for state Green */
	private void enterSequence_main_region_Normal_normal_Green_default() {
		entryAction_main_region_Normal_normal_Green();
		nextStateIndex = 0;
		stateVector[0] = State.main_region_Normal_normal_Green;
		
		historyVector[0] = stateVector[0];
	}
	
	/* 'default' enter sequence for state Red */
	private void enterSequence_main_region_Normal_normal_Red_default() {
		entryAction_main_region_Normal_normal_Red();
		nextStateIndex = 0;
		stateVector[0] = State.main_region_Normal_normal_Red;
		
		historyVector[0] = stateVector[0];
	}
	
	/* 'default' enter sequence for state Interrupted */
	private void enterSequence_main_region_Interrupted_default() {
		enterSequence_main_region_Interrupted_interrupted_default();
	}
	
	/* 'default' enter sequence for state Black */
	private void enterSequence_main_region_Interrupted_interrupted_Black_default() {
		entryAction_main_region_Interrupted_interrupted_Black();
		nextStateIndex = 0;
		stateVector[0] = State.main_region_Interrupted_interrupted_Black;
	}
	
	/* 'default' enter sequence for state Yellow */
	private void enterSequence_main_region_Interrupted_interrupted_Yellow_default() {
		entryAction_main_region_Interrupted_interrupted_Yellow();
		nextStateIndex = 0;
		stateVector[0] = State.main_region_Interrupted_interrupted_Yellow;
	}
	
	/* 'default' enter sequence for region main_region */
	private void enterSequence_main_region_default() {
		react_main_region__entry_Default();
	}
	
	/* 'default' enter sequence for region normal */
	private void enterSequence_main_region_Normal_normal_default() {
		react_main_region_Normal_normal__entry_Default();
	}
	
	/* shallow enterSequence with history in child normal */
	private void shallowEnterSequence_main_region_Normal_normal() {
		switch (historyVector[0]) {
		case main_region_Normal_normal_Yellow:
			enterSequence_main_region_Normal_normal_Yellow_default();
			break;
		case main_region_Normal_normal_Green:
			enterSequence_main_region_Normal_normal_Green_default();
			break;
		case main_region_Normal_normal_Red:
			enterSequence_main_region_Normal_normal_Red_default();
			break;
		default:
			break;
		}
	}
	
	/* 'default' enter sequence for region interrupted */
	private void enterSequence_main_region_Interrupted_interrupted_default() {
		react_main_region_Interrupted_interrupted__entry_Default();
	}
	
	/* Default exit sequence for state Normal */
	private void exitSequence_main_region_Normal() {
		exitSequence_main_region_Normal_normal();
	}
	
	/* Default exit sequence for state Yellow */
	private void exitSequence_main_region_Normal_normal_Yellow() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
	}
	
	/* Default exit sequence for state Green */
	private void exitSequence_main_region_Normal_normal_Green() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
	}
	
	/* Default exit sequence for state Red */
	private void exitSequence_main_region_Normal_normal_Red() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
	}
	
	/* Default exit sequence for state Interrupted */
	private void exitSequence_main_region_Interrupted() {
		exitSequence_main_region_Interrupted_interrupted();
	}
	
	/* Default exit sequence for state Black */
	private void exitSequence_main_region_Interrupted_interrupted_Black() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
		
		exitAction_main_region_Interrupted_interrupted_Black();
	}
	
	/* Default exit sequence for state Yellow */
	private void exitSequence_main_region_Interrupted_interrupted_Yellow() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
		
		exitAction_main_region_Interrupted_interrupted_Yellow();
	}
	
	/* Default exit sequence for region main_region */
	private void exitSequence_main_region() {
		switch (stateVector[0]) {
		case main_region_Normal_normal_Yellow:
			exitSequence_main_region_Normal_normal_Yellow();
			break;
		case main_region_Normal_normal_Green:
			exitSequence_main_region_Normal_normal_Green();
			break;
		case main_region_Normal_normal_Red:
			exitSequence_main_region_Normal_normal_Red();
			break;
		case main_region_Interrupted_interrupted_Black:
			exitSequence_main_region_Interrupted_interrupted_Black();
			break;
		case main_region_Interrupted_interrupted_Yellow:
			exitSequence_main_region_Interrupted_interrupted_Yellow();
			break;
		default:
			break;
		}
	}
	
	/* Default exit sequence for region normal */
	private void exitSequence_main_region_Normal_normal() {
		switch (stateVector[0]) {
		case main_region_Normal_normal_Yellow:
			exitSequence_main_region_Normal_normal_Yellow();
			break;
		case main_region_Normal_normal_Green:
			exitSequence_main_region_Normal_normal_Green();
			break;
		case main_region_Normal_normal_Red:
			exitSequence_main_region_Normal_normal_Red();
			break;
		default:
			break;
		}
	}
	
	/* Default exit sequence for region interrupted */
	private void exitSequence_main_region_Interrupted_interrupted() {
		switch (stateVector[0]) {
		case main_region_Interrupted_interrupted_Black:
			exitSequence_main_region_Interrupted_interrupted_Black();
			break;
		case main_region_Interrupted_interrupted_Yellow:
			exitSequence_main_region_Interrupted_interrupted_Yellow();
			break;
		default:
			break;
		}
	}
	
	/* The reactions of state Yellow. */
	private void react_main_region_Normal_normal_Yellow() {
		if (check_main_region_Normal_tr0_tr0()) {
			effect_main_region_Normal_tr0();
		} else {
			if (check_main_region_Normal_tr1_tr1()) {
				effect_main_region_Normal_tr1();
			} else {
				if (check_main_region_Normal_normal_Yellow_tr0_tr0()) {
					effect_main_region_Normal_normal_Yellow_tr0();
				}
			}
		}
	}
	
	/* The reactions of state Green. */
	private void react_main_region_Normal_normal_Green() {
		if (check_main_region_Normal_tr0_tr0()) {
			effect_main_region_Normal_tr0();
		} else {
			if (check_main_region_Normal_tr1_tr1()) {
				effect_main_region_Normal_tr1();
			} else {
				if (check_main_region_Normal_normal_Green_tr0_tr0()) {
					effect_main_region_Normal_normal_Green_tr0();
				}
			}
		}
	}
	
	/* The reactions of state Red. */
	private void react_main_region_Normal_normal_Red() {
		if (check_main_region_Normal_tr0_tr0()) {
			effect_main_region_Normal_tr0();
		} else {
			if (check_main_region_Normal_tr1_tr1()) {
				effect_main_region_Normal_tr1();
			} else {
				if (check_main_region_Normal_normal_Red_tr0_tr0()) {
					effect_main_region_Normal_normal_Red_tr0();
				}
			}
		}
	}
	
	/* The reactions of state Black. */
	private void react_main_region_Interrupted_interrupted_Black() {
		if (check_main_region_Interrupted_tr0_tr0()) {
			effect_main_region_Interrupted_tr0();
		} else {
			if (check_main_region_Interrupted_tr1_tr1()) {
				effect_main_region_Interrupted_tr1();
			} else {
				if (check_main_region_Interrupted_interrupted_Black_tr0_tr0()) {
					effect_main_region_Interrupted_interrupted_Black_tr0();
				}
			}
		}
	}
	
	/* The reactions of state Yellow. */
	private void react_main_region_Interrupted_interrupted_Yellow() {
		if (check_main_region_Interrupted_tr0_tr0()) {
			effect_main_region_Interrupted_tr0();
		} else {
			if (check_main_region_Interrupted_tr1_tr1()) {
				effect_main_region_Interrupted_tr1();
			} else {
				if (check_main_region_Interrupted_interrupted_Yellow_tr0_tr0()) {
					effect_main_region_Interrupted_interrupted_Yellow_tr0();
				}
			}
		}
	}
	
	/* Default react sequence for initial entry  */
	private void react_main_region__entry_Default() {
		enterSequence_main_region_Normal_default();
	}
	
	/* Default react sequence for shallow history entry  */
	private void react_main_region_Normal_normal__entry_Default() {
		/* Enter the region with shallow history */
		if (historyVector[0] != State.$NullState$) {
			shallowEnterSequence_main_region_Normal_normal();
		} else {
			enterSequence_main_region_Normal_normal_Red_default();
		}
	}
	
	/* Default react sequence for initial entry  */
	private void react_main_region_Interrupted_interrupted__entry_Default() {
		enterSequence_main_region_Interrupted_interrupted_Yellow_default();
	}
	
	public void runCycle() {
		if (!initialized)
			throw new IllegalStateException(
					"The state machine needs to be initialized first by calling the init() function.");
		clearOutEvents();
		for (nextStateIndex = 0; nextStateIndex < stateVector.length; nextStateIndex++) {
			switch (stateVector[nextStateIndex]) {
			case main_region_Normal_normal_Yellow:
				react_main_region_Normal_normal_Yellow();
				break;
			case main_region_Normal_normal_Green:
				react_main_region_Normal_normal_Green();
				break;
			case main_region_Normal_normal_Red:
				react_main_region_Normal_normal_Red();
				break;
			case main_region_Interrupted_interrupted_Black:
				react_main_region_Interrupted_interrupted_Black();
				break;
			case main_region_Interrupted_interrupted_Yellow:
				react_main_region_Interrupted_interrupted_Yellow();
				break;
			default:
				// $NullState$
			}
		}
		clearEvents();
	}
}
