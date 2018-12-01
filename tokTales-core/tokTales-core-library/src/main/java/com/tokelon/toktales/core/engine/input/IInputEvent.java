package com.tokelon.toktales.core.engine.input;

public interface IInputEvent {

	
	/** Marks this event as handled, but only if the given condition is true.
	 * 
	 * @param condition
	 * @return The previous value of handled.
	 */
	public boolean markHandledIf(boolean condition);
	
	/** Marks this event as handled.
	 * 
	 * @return The previous value of handled.
	 */
	public boolean markHandled();
	
	/** Resets this event's handled value to false.
	 * 
	 * @return The previous value of handled.
	 */
	public boolean resetHandled();
	
	/** Returns whether this event has been marked as handled and has not been reset since then.
	 * 
	 * @return True if the event is handled, false if not.
	 */
	public boolean isHandled();

	
	//public String getEventType(); // Maybe even return a class here?

	//public long getEventId(); // Must be unique across different events
	
	//public String getSource();
	
	//public long getCreationTimeMillis(); // Use a datetime format?
	//public void setCreationTimeMillis(long timeMillis);
	
	
	//public boolean wasHandled();
	//public void markHandled(String source);
	//public List<String> getHandledSources();
	
}
