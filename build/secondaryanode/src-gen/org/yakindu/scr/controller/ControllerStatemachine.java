package org.yakindu.scr.controller;
import java.util.LinkedList;
import java.util.List;
import org.yakindu.scr.ITimer;

public class ControllerStatemachine implements IControllerStatemachine {

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
	
	protected class SCIPriorityPoliceImpl implements SCIPriorityPolice {
	
		private List<SCIPriorityPoliceListener> listeners = new LinkedList<SCIPriorityPoliceListener>();
		
		public List<SCIPriorityPoliceListener> getListeners() {
			return listeners;
		}
		private boolean police;
		
		public boolean isRaisedPolice() {
			return police;
		}
		
		protected void raisePolice() {
			police = true;
			for (SCIPriorityPoliceListener listener : listeners) {
				listener.onPoliceRaised();
			}
		}
		
		private boolean reset;
		
		public boolean isRaisedReset() {
			return reset;
		}
		
		protected void raiseReset() {
			reset = true;
			for (SCIPriorityPoliceListener listener : listeners) {
				listener.onResetRaised();
			}
		}
		
		protected void clearEvents() {
		}
		protected void clearOutEvents() {
		
		police = false;
		reset = false;
		}
		
	}
	
	protected SCIPriorityPoliceImpl sCIPriorityPolice;
	
	protected class SCISecondaryPoliceImpl implements SCISecondaryPolice {
	
		private List<SCISecondaryPoliceListener> listeners = new LinkedList<SCISecondaryPoliceListener>();
		
		public List<SCISecondaryPoliceListener> getListeners() {
			return listeners;
		}
		private boolean police;
		
		public boolean isRaisedPolice() {
			return police;
		}
		
		protected void raisePolice() {
			police = true;
			for (SCISecondaryPoliceListener listener : listeners) {
				listener.onPoliceRaised();
			}
		}
		
		private boolean reset;
		
		public boolean isRaisedReset() {
			return reset;
		}
		
		protected void raiseReset() {
			reset = true;
			for (SCISecondaryPoliceListener listener : listeners) {
				listener.onResetRaised();
			}
		}
		
		protected void clearEvents() {
		}
		protected void clearOutEvents() {
		
		police = false;
		reset = false;
		}
		
	}
	
	protected SCISecondaryPoliceImpl sCISecondaryPolice;
	
	protected class SCIPedestrianPoliceImpl implements SCIPedestrianPolice {
	
		private List<SCIPedestrianPoliceListener> listeners = new LinkedList<SCIPedestrianPoliceListener>();
		
		public List<SCIPedestrianPoliceListener> getListeners() {
			return listeners;
		}
		private boolean police;
		
		public boolean isRaisedPolice() {
			return police;
		}
		
		protected void raisePolice() {
			police = true;
			for (SCIPedestrianPoliceListener listener : listeners) {
				listener.onPoliceRaised();
			}
		}
		
		private boolean reset;
		
		public boolean isRaisedReset() {
			return reset;
		}
		
		protected void raiseReset() {
			reset = true;
			for (SCIPedestrianPoliceListener listener : listeners) {
				listener.onResetRaised();
			}
		}
		
		protected void clearEvents() {
		}
		protected void clearOutEvents() {
		
		police = false;
		reset = false;
		}
		
	}
	
	protected SCIPedestrianPoliceImpl sCIPedestrianPolice;
	
	protected class SCIPriorityControlImpl implements SCIPriorityControl {
	
		private List<SCIPriorityControlListener> listeners = new LinkedList<SCIPriorityControlListener>();
		
		public List<SCIPriorityControlListener> getListeners() {
			return listeners;
		}
		private boolean toggle;
		
		public boolean isRaisedToggle() {
			return toggle;
		}
		
		protected void raiseToggle() {
			toggle = true;
			for (SCIPriorityControlListener listener : listeners) {
				listener.onToggleRaised();
			}
		}
		
		protected void clearEvents() {
		}
		protected void clearOutEvents() {
		
		toggle = false;
		}
		
	}
	
	protected SCIPriorityControlImpl sCIPriorityControl;
	
	protected class SCISecondaryControlImpl implements SCISecondaryControl {
	
		private List<SCISecondaryControlListener> listeners = new LinkedList<SCISecondaryControlListener>();
		
		public List<SCISecondaryControlListener> getListeners() {
			return listeners;
		}
		private boolean toggle;
		
		public boolean isRaisedToggle() {
			return toggle;
		}
		
		protected void raiseToggle() {
			toggle = true;
			for (SCISecondaryControlListener listener : listeners) {
				listener.onToggleRaised();
			}
		}
		
		protected void clearEvents() {
		}
		protected void clearOutEvents() {
		
		toggle = false;
		}
		
	}
	
	protected SCISecondaryControlImpl sCISecondaryControl;
	
	protected class SCIPedestrianControlImpl implements SCIPedestrianControl {
	
		private List<SCIPedestrianControlListener> listeners = new LinkedList<SCIPedestrianControlListener>();
		
		public List<SCIPedestrianControlListener> getListeners() {
			return listeners;
		}
		private boolean toggle;
		
		public boolean isRaisedToggle() {
			return toggle;
		}
		
		protected void raiseToggle() {
			toggle = true;
			for (SCIPedestrianControlListener listener : listeners) {
				listener.onToggleRaised();
			}
		}
		
		protected void clearEvents() {
		}
		protected void clearOutEvents() {
		
		toggle = false;
		}
		
	}
	
	protected SCIPedestrianControlImpl sCIPedestrianControl;
	
	protected class SCIErrorImpl implements SCIError {
	
		private boolean healthError;
		
		public void raiseHealthError() {
			healthError = true;
		}
		
		protected void clearEvents() {
			healthError = false;
		}
	}
	
	protected SCIErrorImpl sCIError;
	
	private boolean initialized = false;
	
	public enum State {
		main_region_Operating,
		main_region_Operating_operating_Init,
		main_region_Operating_operating_PriorityPrepares,
		main_region_Operating_operating_Secondary,
		main_region_Operating_operating_SecondaryPrepares,
		main_region_Operating_operating_Prioity,
		main_region_Interrupted,
		main_region_Resetting,
		main_region_Error,
		$NullState$
	};
	
	private State[] historyVector = new State[1];
	private final State[] stateVector = new State[1];
	
	private int nextStateIndex;
	
	private ITimer timer;
	
	private final boolean[] timeEvents = new boolean[7];
	
	
	public ControllerStatemachine() {
		sCIPoliceInterrupt = new SCIPoliceInterruptImpl();
		sCIPriorityPolice = new SCIPriorityPoliceImpl();
		sCISecondaryPolice = new SCISecondaryPoliceImpl();
		sCIPedestrianPolice = new SCIPedestrianPoliceImpl();
		sCIPriorityControl = new SCIPriorityControlImpl();
		sCISecondaryControl = new SCISecondaryControlImpl();
		sCIPedestrianControl = new SCIPedestrianControlImpl();
		sCIError = new SCIErrorImpl();
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
		sCIPoliceInterrupt.clearEvents();
		sCIPriorityPolice.clearEvents();
		sCISecondaryPolice.clearEvents();
		sCIPedestrianPolice.clearEvents();
		sCIPriorityControl.clearEvents();
		sCISecondaryControl.clearEvents();
		sCIPedestrianControl.clearEvents();
		sCIError.clearEvents();
		for (int i=0; i<timeEvents.length; i++) {
			timeEvents[i] = false;
		}
	}
	
	/**
	* This method resets the outgoing events.
	*/
	protected void clearOutEvents() {
		sCIPriorityPolice.clearOutEvents();
		sCISecondaryPolice.clearOutEvents();
		sCIPedestrianPolice.clearOutEvents();
		sCIPriorityControl.clearOutEvents();
		sCISecondaryControl.clearOutEvents();
		sCIPedestrianControl.clearOutEvents();
	}
	
	/**
	* Returns true if the given state is currently active otherwise false.
	*/
	public boolean isStateActive(State state) {
	
		switch (state) {
		case main_region_Operating:
			return stateVector[0].ordinal() >= State.
					main_region_Operating.ordinal()&& stateVector[0].ordinal() <= State.main_region_Operating_operating_Prioity.ordinal();
		case main_region_Operating_operating_Init:
			return stateVector[0] == State.main_region_Operating_operating_Init;
		case main_region_Operating_operating_PriorityPrepares:
			return stateVector[0] == State.main_region_Operating_operating_PriorityPrepares;
		case main_region_Operating_operating_Secondary:
			return stateVector[0] == State.main_region_Operating_operating_Secondary;
		case main_region_Operating_operating_SecondaryPrepares:
			return stateVector[0] == State.main_region_Operating_operating_SecondaryPrepares;
		case main_region_Operating_operating_Prioity:
			return stateVector[0] == State.main_region_Operating_operating_Prioity;
		case main_region_Interrupted:
			return stateVector[0] == State.main_region_Interrupted;
		case main_region_Resetting:
			return stateVector[0] == State.main_region_Resetting;
		case main_region_Error:
			return stateVector[0] == State.main_region_Error;
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
	
	public SCIPoliceInterrupt getSCIPoliceInterrupt() {
		return sCIPoliceInterrupt;
	}
	
	public SCIPriorityPolice getSCIPriorityPolice() {
		return sCIPriorityPolice;
	}
	
	public SCISecondaryPolice getSCISecondaryPolice() {
		return sCISecondaryPolice;
	}
	
	public SCIPedestrianPolice getSCIPedestrianPolice() {
		return sCIPedestrianPolice;
	}
	
	public SCIPriorityControl getSCIPriorityControl() {
		return sCIPriorityControl;
	}
	
	public SCISecondaryControl getSCISecondaryControl() {
		return sCISecondaryControl;
	}
	
	public SCIPedestrianControl getSCIPedestrianControl() {
		return sCIPedestrianControl;
	}
	
	public SCIError getSCIError() {
		return sCIError;
	}
	
	private boolean check_main_region_Operating_tr0_tr0() {
		return sCIPoliceInterrupt.police;
	}
	
	private boolean check_main_region_Operating_tr1_tr1() {
		return sCIPoliceInterrupt.reset;
	}
	
	private boolean check_main_region_Operating_tr2_tr2() {
		return sCIError.healthError;
	}
	
	private boolean check_main_region_Operating_operating_Init_tr0_tr0() {
		return timeEvents[0];
	}
	
	private boolean check_main_region_Operating_operating_PriorityPrepares_tr0_tr0() {
		return timeEvents[1];
	}
	
	private boolean check_main_region_Operating_operating_Secondary_tr0_tr0() {
		return timeEvents[2];
	}
	
	private boolean check_main_region_Operating_operating_SecondaryPrepares_tr0_tr0() {
		return timeEvents[3];
	}
	
	private boolean check_main_region_Operating_operating_Prioity_tr0_tr0() {
		return timeEvents[4];
	}
	
	private boolean check_main_region_Interrupted_tr0_tr0() {
		return sCIPoliceInterrupt.police;
	}
	
	private boolean check_main_region_Interrupted_tr1_tr1() {
		return sCIPoliceInterrupt.reset;
	}
	
	private boolean check_main_region_Resetting_tr0_tr0() {
		return timeEvents[5];
	}
	
	private boolean check_main_region_Error_tr0_tr0() {
		return timeEvents[6];
	}
	
	private boolean check_main_region_Error_tr1_tr1() {
		return sCIError.healthError;
	}
	
	private void effect_main_region_Operating_tr0() {
		exitSequence_main_region_Operating();
		sCIPriorityPolice.raisePolice();
		
		sCISecondaryPolice.raisePolice();
		
		sCIPedestrianPolice.raisePolice();
		
		enterSequence_main_region_Interrupted_default();
	}
	
	private void effect_main_region_Operating_tr1() {
		exitSequence_main_region_Operating();
		enterSequence_main_region_Resetting_default();
	}
	
	private void effect_main_region_Operating_tr2() {
		exitSequence_main_region_Operating();
		sCIPriorityPolice.raisePolice();
		
		sCISecondaryPolice.raisePolice();
		
		sCIPedestrianPolice.raisePolice();
		
		enterSequence_main_region_Error_default();
	}
	
	private void effect_main_region_Operating_operating_Init_tr0() {
		exitSequence_main_region_Operating_operating_Init();
		enterSequence_main_region_Operating_operating_PriorityPrepares_default();
	}
	
	private void effect_main_region_Operating_operating_PriorityPrepares_tr0() {
		exitSequence_main_region_Operating_operating_PriorityPrepares();
		enterSequence_main_region_Operating_operating_Secondary_default();
	}
	
	private void effect_main_region_Operating_operating_Secondary_tr0() {
		exitSequence_main_region_Operating_operating_Secondary();
		enterSequence_main_region_Operating_operating_SecondaryPrepares_default();
	}
	
	private void effect_main_region_Operating_operating_SecondaryPrepares_tr0() {
		exitSequence_main_region_Operating_operating_SecondaryPrepares();
		enterSequence_main_region_Operating_operating_Prioity_default();
	}
	
	private void effect_main_region_Operating_operating_Prioity_tr0() {
		exitSequence_main_region_Operating_operating_Prioity();
		enterSequence_main_region_Operating_operating_PriorityPrepares_default();
	}
	
	private void effect_main_region_Interrupted_tr0() {
		exitSequence_main_region_Interrupted();
		sCIPriorityPolice.raisePolice();
		
		sCISecondaryPolice.raisePolice();
		
		sCIPedestrianPolice.raisePolice();
		
		enterSequence_main_region_Operating_default();
	}
	
	private void effect_main_region_Interrupted_tr1() {
		exitSequence_main_region_Interrupted();
		enterSequence_main_region_Resetting_default();
	}
	
	private void effect_main_region_Resetting_tr0() {
		exitSequence_main_region_Resetting();
		enterSequence_main_region_Operating_operating_Init_default();
	}
	
	private void effect_main_region_Error_tr0() {
		exitSequence_main_region_Error();
		sCIPriorityPolice.raisePolice();
		
		sCISecondaryPolice.raisePolice();
		
		sCIPedestrianPolice.raisePolice();
		
		enterSequence_main_region_Operating_default();
	}
	
	private void effect_main_region_Error_tr1() {
		exitSequence_main_region_Error();
		enterSequence_main_region_Error_default();
	}
	
	/* Entry action for state 'Init'. */
	private void entryAction_main_region_Operating_operating_Init() {
		timer.setTimer(this, 0, 2 * 1000, false);
		
		sCIPriorityControl.raiseToggle();
		
		sCIPedestrianControl.raiseToggle();
	}
	
	/* Entry action for state 'PriorityPrepares'. */
	private void entryAction_main_region_Operating_operating_PriorityPrepares() {
		timer.setTimer(this, 1, 1 * 1000, false);
		
		sCIPriorityControl.raiseToggle();
		
		sCIPedestrianControl.raiseToggle();
	}
	
	/* Entry action for state 'Secondary'. */
	private void entryAction_main_region_Operating_operating_Secondary() {
		timer.setTimer(this, 2, 2 * 1000, false);
		
		sCIPriorityControl.raiseToggle();
		
		sCISecondaryControl.raiseToggle();
		
		sCIPedestrianControl.raiseToggle();
	}
	
	/* Entry action for state 'SecondaryPrepares'. */
	private void entryAction_main_region_Operating_operating_SecondaryPrepares() {
		timer.setTimer(this, 3, 1 * 1000, false);
		
		sCISecondaryControl.raiseToggle();
	}
	
	/* Entry action for state 'Prioity'. */
	private void entryAction_main_region_Operating_operating_Prioity() {
		timer.setTimer(this, 4, 2 * 1000, false);
		
		sCIPriorityControl.raiseToggle();
		
		sCISecondaryControl.raiseToggle();
		
		sCIPedestrianControl.raiseToggle();
	}
	
	/* Entry action for state 'Resetting'. */
	private void entryAction_main_region_Resetting() {
		timer.setTimer(this, 5, 500, false);
	}
	
	/* Entry action for state 'Error'. */
	private void entryAction_main_region_Error() {
		timer.setTimer(this, 6, 1500, false);
	}
	
	/* Exit action for state 'Init'. */
	private void exitAction_main_region_Operating_operating_Init() {
		timer.unsetTimer(this, 0);
	}
	
	/* Exit action for state 'PriorityPrepares'. */
	private void exitAction_main_region_Operating_operating_PriorityPrepares() {
		timer.unsetTimer(this, 1);
	}
	
	/* Exit action for state 'Secondary'. */
	private void exitAction_main_region_Operating_operating_Secondary() {
		timer.unsetTimer(this, 2);
	}
	
	/* Exit action for state 'SecondaryPrepares'. */
	private void exitAction_main_region_Operating_operating_SecondaryPrepares() {
		timer.unsetTimer(this, 3);
	}
	
	/* Exit action for state 'Prioity'. */
	private void exitAction_main_region_Operating_operating_Prioity() {
		timer.unsetTimer(this, 4);
	}
	
	/* Exit action for state 'Resetting'. */
	private void exitAction_main_region_Resetting() {
		timer.unsetTimer(this, 5);
	}
	
	/* Exit action for state 'Error'. */
	private void exitAction_main_region_Error() {
		timer.unsetTimer(this, 6);
	}
	
	/* 'default' enter sequence for state Operating */
	private void enterSequence_main_region_Operating_default() {
		enterSequence_main_region_Operating_operating_default();
	}
	
	/* 'default' enter sequence for state Init */
	private void enterSequence_main_region_Operating_operating_Init_default() {
		entryAction_main_region_Operating_operating_Init();
		nextStateIndex = 0;
		stateVector[0] = State.main_region_Operating_operating_Init;
		
		historyVector[0] = stateVector[0];
	}
	
	/* 'default' enter sequence for state PriorityPrepares */
	private void enterSequence_main_region_Operating_operating_PriorityPrepares_default() {
		entryAction_main_region_Operating_operating_PriorityPrepares();
		nextStateIndex = 0;
		stateVector[0] = State.main_region_Operating_operating_PriorityPrepares;
		
		historyVector[0] = stateVector[0];
	}
	
	/* 'default' enter sequence for state Secondary */
	private void enterSequence_main_region_Operating_operating_Secondary_default() {
		entryAction_main_region_Operating_operating_Secondary();
		nextStateIndex = 0;
		stateVector[0] = State.main_region_Operating_operating_Secondary;
		
		historyVector[0] = stateVector[0];
	}
	
	/* 'default' enter sequence for state SecondaryPrepares */
	private void enterSequence_main_region_Operating_operating_SecondaryPrepares_default() {
		entryAction_main_region_Operating_operating_SecondaryPrepares();
		nextStateIndex = 0;
		stateVector[0] = State.main_region_Operating_operating_SecondaryPrepares;
		
		historyVector[0] = stateVector[0];
	}
	
	/* 'default' enter sequence for state Prioity */
	private void enterSequence_main_region_Operating_operating_Prioity_default() {
		entryAction_main_region_Operating_operating_Prioity();
		nextStateIndex = 0;
		stateVector[0] = State.main_region_Operating_operating_Prioity;
		
		historyVector[0] = stateVector[0];
	}
	
	/* 'default' enter sequence for state Interrupted */
	private void enterSequence_main_region_Interrupted_default() {
		nextStateIndex = 0;
		stateVector[0] = State.main_region_Interrupted;
	}
	
	/* 'default' enter sequence for state Resetting */
	private void enterSequence_main_region_Resetting_default() {
		entryAction_main_region_Resetting();
		nextStateIndex = 0;
		stateVector[0] = State.main_region_Resetting;
	}
	
	/* 'default' enter sequence for state Error */
	private void enterSequence_main_region_Error_default() {
		entryAction_main_region_Error();
		nextStateIndex = 0;
		stateVector[0] = State.main_region_Error;
	}
	
	/* 'default' enter sequence for region main_region */
	private void enterSequence_main_region_default() {
		react_main_region__entry_Default();
	}
	
	/* 'default' enter sequence for region operating */
	private void enterSequence_main_region_Operating_operating_default() {
		react_main_region_Operating_operating__entry_Default();
	}
	
	/* shallow enterSequence with history in child operating */
	private void shallowEnterSequence_main_region_Operating_operating() {
		switch (historyVector[0]) {
		case main_region_Operating_operating_Init:
			enterSequence_main_region_Operating_operating_Init_default();
			break;
		case main_region_Operating_operating_PriorityPrepares:
			enterSequence_main_region_Operating_operating_PriorityPrepares_default();
			break;
		case main_region_Operating_operating_Secondary:
			enterSequence_main_region_Operating_operating_Secondary_default();
			break;
		case main_region_Operating_operating_SecondaryPrepares:
			enterSequence_main_region_Operating_operating_SecondaryPrepares_default();
			break;
		case main_region_Operating_operating_Prioity:
			enterSequence_main_region_Operating_operating_Prioity_default();
			break;
		default:
			break;
		}
	}
	
	/* Default exit sequence for state Operating */
	private void exitSequence_main_region_Operating() {
		exitSequence_main_region_Operating_operating();
	}
	
	/* Default exit sequence for state Init */
	private void exitSequence_main_region_Operating_operating_Init() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
		
		exitAction_main_region_Operating_operating_Init();
	}
	
	/* Default exit sequence for state PriorityPrepares */
	private void exitSequence_main_region_Operating_operating_PriorityPrepares() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
		
		exitAction_main_region_Operating_operating_PriorityPrepares();
	}
	
	/* Default exit sequence for state Secondary */
	private void exitSequence_main_region_Operating_operating_Secondary() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
		
		exitAction_main_region_Operating_operating_Secondary();
	}
	
	/* Default exit sequence for state SecondaryPrepares */
	private void exitSequence_main_region_Operating_operating_SecondaryPrepares() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
		
		exitAction_main_region_Operating_operating_SecondaryPrepares();
	}
	
	/* Default exit sequence for state Prioity */
	private void exitSequence_main_region_Operating_operating_Prioity() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
		
		exitAction_main_region_Operating_operating_Prioity();
	}
	
	/* Default exit sequence for state Interrupted */
	private void exitSequence_main_region_Interrupted() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
	}
	
	/* Default exit sequence for state Resetting */
	private void exitSequence_main_region_Resetting() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
		
		exitAction_main_region_Resetting();
	}
	
	/* Default exit sequence for state Error */
	private void exitSequence_main_region_Error() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
		
		exitAction_main_region_Error();
	}
	
	/* Default exit sequence for region main_region */
	private void exitSequence_main_region() {
		switch (stateVector[0]) {
		case main_region_Operating_operating_Init:
			exitSequence_main_region_Operating_operating_Init();
			break;
		case main_region_Operating_operating_PriorityPrepares:
			exitSequence_main_region_Operating_operating_PriorityPrepares();
			break;
		case main_region_Operating_operating_Secondary:
			exitSequence_main_region_Operating_operating_Secondary();
			break;
		case main_region_Operating_operating_SecondaryPrepares:
			exitSequence_main_region_Operating_operating_SecondaryPrepares();
			break;
		case main_region_Operating_operating_Prioity:
			exitSequence_main_region_Operating_operating_Prioity();
			break;
		case main_region_Interrupted:
			exitSequence_main_region_Interrupted();
			break;
		case main_region_Resetting:
			exitSequence_main_region_Resetting();
			break;
		case main_region_Error:
			exitSequence_main_region_Error();
			break;
		default:
			break;
		}
	}
	
	/* Default exit sequence for region operating */
	private void exitSequence_main_region_Operating_operating() {
		switch (stateVector[0]) {
		case main_region_Operating_operating_Init:
			exitSequence_main_region_Operating_operating_Init();
			break;
		case main_region_Operating_operating_PriorityPrepares:
			exitSequence_main_region_Operating_operating_PriorityPrepares();
			break;
		case main_region_Operating_operating_Secondary:
			exitSequence_main_region_Operating_operating_Secondary();
			break;
		case main_region_Operating_operating_SecondaryPrepares:
			exitSequence_main_region_Operating_operating_SecondaryPrepares();
			break;
		case main_region_Operating_operating_Prioity:
			exitSequence_main_region_Operating_operating_Prioity();
			break;
		default:
			break;
		}
	}
	
	/* The reactions of state Init. */
	private void react_main_region_Operating_operating_Init() {
		if (check_main_region_Operating_tr0_tr0()) {
			effect_main_region_Operating_tr0();
		} else {
			if (check_main_region_Operating_tr1_tr1()) {
				effect_main_region_Operating_tr1();
			} else {
				if (check_main_region_Operating_tr2_tr2()) {
					effect_main_region_Operating_tr2();
				} else {
					if (check_main_region_Operating_operating_Init_tr0_tr0()) {
						effect_main_region_Operating_operating_Init_tr0();
					}
				}
			}
		}
	}
	
	/* The reactions of state PriorityPrepares. */
	private void react_main_region_Operating_operating_PriorityPrepares() {
		if (check_main_region_Operating_tr0_tr0()) {
			effect_main_region_Operating_tr0();
		} else {
			if (check_main_region_Operating_tr1_tr1()) {
				effect_main_region_Operating_tr1();
			} else {
				if (check_main_region_Operating_tr2_tr2()) {
					effect_main_region_Operating_tr2();
				} else {
					if (check_main_region_Operating_operating_PriorityPrepares_tr0_tr0()) {
						effect_main_region_Operating_operating_PriorityPrepares_tr0();
					}
				}
			}
		}
	}
	
	/* The reactions of state Secondary. */
	private void react_main_region_Operating_operating_Secondary() {
		if (check_main_region_Operating_tr0_tr0()) {
			effect_main_region_Operating_tr0();
		} else {
			if (check_main_region_Operating_tr1_tr1()) {
				effect_main_region_Operating_tr1();
			} else {
				if (check_main_region_Operating_tr2_tr2()) {
					effect_main_region_Operating_tr2();
				} else {
					if (check_main_region_Operating_operating_Secondary_tr0_tr0()) {
						effect_main_region_Operating_operating_Secondary_tr0();
					}
				}
			}
		}
	}
	
	/* The reactions of state SecondaryPrepares. */
	private void react_main_region_Operating_operating_SecondaryPrepares() {
		if (check_main_region_Operating_tr0_tr0()) {
			effect_main_region_Operating_tr0();
		} else {
			if (check_main_region_Operating_tr1_tr1()) {
				effect_main_region_Operating_tr1();
			} else {
				if (check_main_region_Operating_tr2_tr2()) {
					effect_main_region_Operating_tr2();
				} else {
					if (check_main_region_Operating_operating_SecondaryPrepares_tr0_tr0()) {
						effect_main_region_Operating_operating_SecondaryPrepares_tr0();
					}
				}
			}
		}
	}
	
	/* The reactions of state Prioity. */
	private void react_main_region_Operating_operating_Prioity() {
		if (check_main_region_Operating_tr0_tr0()) {
			effect_main_region_Operating_tr0();
		} else {
			if (check_main_region_Operating_tr1_tr1()) {
				effect_main_region_Operating_tr1();
			} else {
				if (check_main_region_Operating_tr2_tr2()) {
					effect_main_region_Operating_tr2();
				} else {
					if (check_main_region_Operating_operating_Prioity_tr0_tr0()) {
						effect_main_region_Operating_operating_Prioity_tr0();
					}
				}
			}
		}
	}
	
	/* The reactions of state Interrupted. */
	private void react_main_region_Interrupted() {
		if (check_main_region_Interrupted_tr0_tr0()) {
			effect_main_region_Interrupted_tr0();
		} else {
			if (check_main_region_Interrupted_tr1_tr1()) {
				effect_main_region_Interrupted_tr1();
			}
		}
	}
	
	/* The reactions of state Resetting. */
	private void react_main_region_Resetting() {
		if (check_main_region_Resetting_tr0_tr0()) {
			effect_main_region_Resetting_tr0();
		}
	}
	
	/* The reactions of state Error. */
	private void react_main_region_Error() {
		if (check_main_region_Error_tr0_tr0()) {
			effect_main_region_Error_tr0();
		} else {
			if (check_main_region_Error_tr1_tr1()) {
				effect_main_region_Error_tr1();
			}
		}
	}
	
	/* Default react sequence for shallow history entry  */
	private void react_main_region_Operating_operating__entry_Default() {
		/* Enter the region with shallow history */
		if (historyVector[0] != State.$NullState$) {
			shallowEnterSequence_main_region_Operating_operating();
		} else {
			enterSequence_main_region_Operating_operating_Init_default();
		}
	}
	
	/* Default react sequence for initial entry  */
	private void react_main_region__entry_Default() {
		enterSequence_main_region_Operating_default();
	}
	
	public void runCycle() {
		if (!initialized)
			throw new IllegalStateException(
					"The state machine needs to be initialized first by calling the init() function.");
		clearOutEvents();
		for (nextStateIndex = 0; nextStateIndex < stateVector.length; nextStateIndex++) {
			switch (stateVector[nextStateIndex]) {
			case main_region_Operating_operating_Init:
				react_main_region_Operating_operating_Init();
				break;
			case main_region_Operating_operating_PriorityPrepares:
				react_main_region_Operating_operating_PriorityPrepares();
				break;
			case main_region_Operating_operating_Secondary:
				react_main_region_Operating_operating_Secondary();
				break;
			case main_region_Operating_operating_SecondaryPrepares:
				react_main_region_Operating_operating_SecondaryPrepares();
				break;
			case main_region_Operating_operating_Prioity:
				react_main_region_Operating_operating_Prioity();
				break;
			case main_region_Interrupted:
				react_main_region_Interrupted();
				break;
			case main_region_Resetting:
				react_main_region_Resetting();
				break;
			case main_region_Error:
				react_main_region_Error();
				break;
			default:
				// $NullState$
			}
		}
		clearEvents();
	}
}
