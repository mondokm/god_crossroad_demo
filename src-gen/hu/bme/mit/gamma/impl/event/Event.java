package hu.bme.mit.gamma.impl.event;

public class Event {

	private String event;
	
	private Object value;
	
	public Event(String event, Object value) {
		this.event = event;
		this.value = value;
	}
	
	public String getEvent() {
		return event;
	}
	
	public Object getValue() {
		return value;
	}

}
