package com.tokelon.toktales.core.render.order;

import java.util.Collection;

/** A continuous list of render calls ordered by the positions they have been assigned to.
 * <br><br>
 * Double values are used for positions, which allows for calls to be placed in between other calls indefinitely.
 * Both positive and negative values are valid positions. The position 0.0 is thought as the main call, but has no special function.
 * <br>
 * The smallest position is considered the lowest and therefore the first in priority.
 * <br><br>
 * The underlying implementation must ensure thread safety internally for calls and externally for iterations when synchronized on this object.
 */
public interface IRenderLayerStack {	//IRenderStack
	
	
	/** Adds the given position to the stack and assigns the given callback to it.
	 * <br><br>
	 * Any double value, positive or negative is a valid position.
	 * Unless you are positioning in between calls it is recommended to use integer values.
	 * 
	 * 
	 * @param position The position at which the callback should be placed at.
	 * @param callback The callback.
	 * 
	 */
	public void addCallbackAt(double position, IRenderCall callback);
	//public void setPreCallback(double prePosition, IRenderCallback callback);
	//public void setPostCallback(double postPosition, IRenderCallback callback);
	
	
	
	/** Beware that the given value must match exactly the position value for the callback you are trying to remove.
	 * 
	 * @param position
	 * @return True if the position was removed, or false if there was no change. 
	 */
	public boolean removeCallbackAt(double position);
	//public void removeCallback(IRenderCallback callback);	// Ambiguity for positions
	
	
	/** Beware that the given value must match exactly the position value for the callback you are trying to remove.
	 * 
	 * @param position
	 * @return The callback for the given positions, or null if there is none.
	 */
	public IRenderCall getCallbackAt(double position);
	//public IRenderCallback getPreCallback(double prePosition);
	//public IRenderCallback getPostCallback(double postPosition);
	
	
	
	/** Note that when iterating over the returned collection you must synchronize on this object to ensure thread safety.
	 * 
	 * @return An unmodifiable collection containing all callbacks sorted from lowest to highest position.
	 */
	public Collection<IRenderCall> getCallbacks();
	
	/** Note that when iterating over the returned collection you must synchronize on this object to ensure thread safety.
	 * 
	 * @return An unmodifiable collection containing all positions sorted from lowest to highest.
	 */
	public Collection<Double> getPositions();
	
	
	/**
	 * @param position
	 * @return The greatest position less than the given one.
	 */
	public double getLowerPosition(double position);

	/**
	 * @param position
	 * @return The lowest position greater than the given one. 
	 */
	public double getHigherPosition(double position);

	
	//public Entry<Double, IRenderCallback> getLowerEntry(double position);
	//public Entry<Double, IRenderCallback> getHigherEntry(double position);
	
	
	
	//public int getPreCallbackCount();
	//public int getPostCallbackCount();

	/**
	 * @return The smallest value for positions which also has the highest priority.
	 */
	public double getLowestPosition();
	
	/**
	 * @return The biggest value for positions which also has the lowest priority.
	 */
	public double getHighestPosition();

	
	/**
	 * @return The number of callbacks for this stack.
	 */
	public int getStackSize();
	
	
	/**
	 * 
	 * @return A new navigator for this stack.
	 */
	public IStackNavigator navigator();
	
	
	/** Can be used to navigate through the positions of a stack.
	 * <br><br>
	 * All navigation positions return whether the current position is valid or not.
	 * 
	 */
	public interface IStackNavigator {

		//public boolean hasNext();
		//public boolean hasPrevious();
		
		/** Navigates to the given position.
		 * 
		 * @param position The position to navigate to.
		 * @return True if the new position is valid, false if not.
		 */
		public boolean navigateToPosition(double position);
		
		
		/** Navigates to the next position.
		 * 
		 * @return True if the new position is valid, false if not.
		 * @throws IllegalStateException If the current position is invalid.
		 */
		public boolean navigateToNext();
		
		/** Navigates to the previous position.
		 * 
		 * @return True if the new position is valid, false if not.
		 * @throws IllegalStateException If the current position is invalid.
		 */
		public boolean navigateToPrevious();
		

		/** Navigates to the first position.
		 * 
		 * @return True if there are any positions, false if not.
		 */
		public boolean navigateToFirst();
		
		/** Navigates to the last position.
		 * 
		 * @return True if there are any positions, false if not.
		 */
		public boolean navigateToLast();

		
		/**
		 * @return True if this navigator is at a valid position, false if not.
		 */
		public boolean isValid();
		
		
		/**
		 * @return The current position.
		 * @throws IllegalStateException If the current position is invalid.
		 */
		public double getCurrentPosition();
		
		/**
		 * @return The callback for the current position.
		 * @throws IllegalStateException If the current position is invalid.
		 */
		public IRenderCall getCurrentCallback();
		
	}
	
	
}
