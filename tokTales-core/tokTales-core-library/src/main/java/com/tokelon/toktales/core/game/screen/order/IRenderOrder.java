package com.tokelon.toktales.core.game.screen.order;

import java.util.Collection;
import java.util.Map;

/** Represents a list of layers which can have stacks containing render calls.
 * <br><br>
 * The layers are indexed starting from zero but indexes can remain empty, meaning they contain no layer or stack.
 * This allows for us to insert layers at specific indexes without having to fill the ones in between.
 * Additionally it will not insert a layer at an index if it already contains the same layer (name).
 * All this enables this structure to function independently of the order the layers are registered.
 * 
 * <br><br>
 * There are two special layers, bottom and top. You can access them via their names {@link #LAYER_BOTTOM} and {@link #LAYER_TOP}.<br>
 * <br>
 * Bottom layer is always at index 0 and cannot be moved or replaced.<br>
 * Top layer is always at index count - 1 and cannot be replaced.<br>
 * Other than that they act like any other layer, except they will never be empty.
 * 
 * <br><br>
 * The underlying implementation must ensure thread safety internally for calls and externally for iterations when synchronized on this object.
 *
 */
public interface IRenderOrder {

	public static final String LAYER_BOTTOM = "bottom";
	public static final String LAYER_TOP = "top";
	
	

	/** Same as {@link #insertTaggedLayerAt(int, String, String)} using the a default (empty) tag.
	 * 
	 * @param index
	 * @param layerName
	 * @throws IllegalArgumentException If index <= 0 or layerName is null.
	 */
	public void insertLayerAt(int index, String layerName); 	// TODO: Return LayerStack ?
	//public void insertLayerAfter(String layerNameBefore, String layerName);
	
	
	/** Checks if there already is a layer on the given index.<br>
	 * If not, creates the layer with the given name. If yes, checks if the layer name matches the given name.<br>
	 * If the names match, the existing layer will be reused but it will be assigned to the given tag (!). If not, inserts a new layer with the given name at the given index, and moves the old layer to index+1.<br>
	 * <br>
	 * If the layer on the index is empty, a new layer will be set on the given index. 
	 * <br><br>
	 * Note: You cannot insert at index 0 because of the bottom layer. (See {@link IRenderOrder} for more info on bottom and top).
	 * 
	 * @param index
	 * @param layerName
	 * @param tag
	 * @throws IllegalArgumentException If index <= 0 or layerName or tag is null.
	 */
	public void insertTaggedLayerAt(int index, String layerName, String tag);
	
	/** Removes the layer with the given name and tag.
	 * If there is no such layer, it will have no effect.
	 * If there are multiple layers with the given name and tag, it will remove the first in order. 
	 * 
	 * @param layerName
	 * @param tag
	 * @return True if a layer was removed, false if not.
	 * @throws IllegalArgumentException If layerName or tag is null.
	 */
	public boolean removeTaggedLayer(String layerName, String tag);
	
	/** Removes all layers with the given tag.
	 * If there are no such layer, it will have no effect.
	 * 
	 * @param tag
	 * @return True if any layers were removed, false if not.
	 * @throws IllegalArgumentException If tag is null.
	 */
	public boolean removeAllTaggedLayers(String tag);
	
	
	/** Removes the layer with the given name.
	 * If there is no such layer, it will have no effect.
	 * If there are multiple layers with the given name, it will remove the first in order.
	 * 
	 * @param layerName
	 * @return True if a layer was removed, false if not.
	 * @throws IllegalArgumentException If layerName is null.
	 */
	public boolean removeLayer(String layerName); 

	/* TODO: Implement
	public void removeLayer(String layerName);
	public void removeLayerAt(int index);
	
	public void deleteLayer(String layerName);
	public void deleteLayerAt(int index);
	*/
	
	
	/** Registers a call for the given layer and position.
	 * <br><br>
	 * If a layer with the given name already exists, the call is added immediately.
	 * If multiple layers with the given name exist, the call is added to the first in order.
	 * 
	 * @param layerName
	 * @param position
	 * @param callback
	 * @return True if the call was immediately added, false if not.
	 */
	public boolean registerCall(String layerName, double position, IRenderCallback callback);
	
	/** Removes the registered call for the given layer name and position.
	 * <br><br>
	 * If multiple calls for the given position exist, the first in order is removed.
	 * 
	 * @param layerName
	 * @param position
	 * @return True if there was a call removed from the given position, false if not.
	 */
	public boolean removeRegisteredCall(String layerName, double position);
	//public IRenderCallback getRegisteredCall(String layerName, double position);
	
	
	
	/**
	 * @param layerName
	 * @return True if there is a layer for the given name, false if not.
	 */
	public boolean hasLayer(String layerName);
	
	/** Use with caution: Calling this method twice is not guaranteed to return the same result, as layers can shift.<br>
	 * 
	 * @param index
	 * @return True if the given index is empty, false if not.
	 * @throws IllegalArgumentException If index < 0 or index >= index count.
	 */
	public boolean isIndexEmpty(int index);
	
	
	
	/**
	 * @param layerName
	 * @return The index for the layer with the given name, or -1 if there is no such layer.
	 */
	public int getIndexForLayer(String layerName);
	
	/** Use with caution: Calling this method twice is not guaranteed to return the same result, as layers can shift.<br>
	 * 
	 * @param index
	 * @return The layer name for the given index, or null if the index is empty.
	 * @throws IllegalArgumentException If index < 0 or index >= index count.
	 */
	public String getLayerForIndex(int index);
	
	
	
	/**
	 * @param layerName
	 * @return The stack for the layer with the given name, or null if there is no such layer.
	 */
	public IRenderLayerStack getStackForLayer(String layerName);
	
	/** Use with caution: Calling this method twice is not guaranteed to return the same result, as layers can shift.<br> 
	 * Known layers should always be accessed by their names via {@link #getStackForLayer(String)}.
	 * 
	 * 
	 * @param index
	 * @return The layer stack for the given index, or null if the index is empty. 
	 * @throws IllegalArgumentException If index < 0 or index >= index count.
	 */
	public IRenderLayerStack getStackForIndex(int index);

	
	
	/**
	 * The returned collection is ordered by indexes and includes null values for empty indexes.
	 * <br><br>
	 * Note that when iterating over the returned collection you must synchronize on this object to ensure thread safety.
	 * 
	 * @return An unmodifiable collection representing the layer stacks.
	 */
	public Collection<IRenderLayerStack> getLayerStacks();
	
	/**
	 * The returned collection is ordered by indexes and includes null values for empty indexes. 
	 * <br><br>
	 * Note that when iterating over the returned collection you must synchronize on this object to ensure thread safety.
	 * 
	 * @return An unmodifiable collection representing the layer names.
	 */
	public Collection<String> getLayers();
	
	
	/**
	 * Note that when iterating over the returned map you must synchronize on this object to ensure thread safety.
	 * 
	 * @return An unmodifiable map containing all registered mappings from layer name to layer stack.
	 */
	public Map<String, IRenderLayerStack> getRegisteredCalls();
	
	// Replace with collections ?
	//public Collection<String> getRegisteredLayers();
	//public Collection<IRenderLayerStack> getRegisteredStacks();
	
	public Map<String, ? extends Collection<IRenderLayerStack>> getTaggedLayers();
	
	
	/**
	 * @return The number of layers currently. This includes empty layers as well as bottom and top layers.
	 */
	public int getFullLayerCount();
	
	/**
	 * @return The number of layers that were added. This includes empty layers. Does not include bottom and top.
	 */
	public int getAddedLayerCount();
	
	
	/**
	 * @return The index of the top layer, which is also the highest index.
	 */
	public int getTopIndex();
	
	
	public IOrderNavigator navigator();
	
	
	/** Can be used to navigate through the layers and positions of a render order.
	 * <br><br>
	 * All navigation methods return whether the navigator is at a valid position.
	 * 
	 */
	public interface IOrderNavigator {
		
		
		/** Navigates to the given order index and layer position.
		 * <br><br> 
		 * If the index is empty or invalid, or there is no such position in the layer,
		 * the current position will be set to invalid.
		 * 
		 * @param index The order index to navigate to.
		 * @param position The layer position to navigate to.
		 * @return True if the new position is valid, false if not.
		 */
		public boolean navigateTo(int index, double position);
		
		/** Navigates to the given order index and layer position.
		 * <br><br>
		 * If there is no such layer or position,
		 * the current position will be set to invalid.
		 * 
		 * @param layer The name of the layer to navigate to.
		 * @param position The layer position to navigate to.
		 * @return True if the new position is valid, false if not.
		 * @throws NullPointerException If layer is null.
		 */
		public boolean navigateTo(String layer, double position);
		


		/** Navigates to the next layer index.
		 * The current position will be set to the first layer position.
		 * <br><br>
		 * If this index is empty or invalid, the current position will be set to invalid.
		 * 
		 * @return True if the new position is valid, false if not.
		 * @throws IllegalStateException If the current position is invalid.
		 * 
		 */
		public boolean navigateToNextIndex();
		
		/** Navigates to the previous layer index.
		 * The current position will be set to the first layer position.
		 * <br><br>
		 * If this index is empty or invalid, the current position will be set to invalid.
		 * 
		 * @return True if the new position is valid, false if not.
		 * @throws IllegalStateException If the current position is invalid.
		 */
		public boolean navigateToPreviousIndex();
		
		
		/** Navigates to the next non-empty index.
		 * The current position will be set to the first layer position. 
		 * <br><br>
		 * If there is no next layer, the current position will be set to invalid.
		 * 
		 * @return True if the new position is valid (there is a next layer), false if not.
		 * @throws IllegalStateException If the current position is invalid. 
		 */
		public boolean navigateToNextLayer();
		
		/** Navigates to the previous non-empty index.
		 * The current position will be set to the first layer position.
		 * <br><br>
		 * If there is no previous layer, the current position will be set to invalid.
		 * 
		 * @return True if the new position is valid (there is a previous layer), false if not.
		 * @throws IllegalStateException If the current position is invalid. 
		 */
		public boolean navigateToPreviousLayer();
		
		
		
		/** Navigates to the bottom layer.
		 * The current position will be set to the first layer position.
		 * <br><br>
		 * If the bottom layer has no calls, the current position will be set to invalid.
		 * 
		 * @return True if the new position if valid, false if not.
		 */
		public boolean navigateToBottomLayer();
		
		/** Navigates to the top layer.
		 * The current position will be set to the first layer position.
		 * <br><br>
		 * If the top layer has no calls, the current position will be set to invalid.
		 * 
		 * @return True if the new position is valid, false if not.
		 */
		public boolean navigateToTopLayer();

		
		
		/** Navigates to the next position in the current layer.
		 * <br><br>
		 * If there is no next position, the current position will be set to invalid.
		 * 
		 * @return True if the new position is valid, false if not.
		 * @throws IllegalStateException If the current position is invalid.
		 */
		public boolean navigateToNextPosition();
		
		/** Navigates to the previous position in the current layer.
		 * <br><br>
		 * If there is no previous position, the current position will be set to invalid.
		 * 
		 * @return True if the new position is valid, false if not.
		 * @throws IllegalStateException If the current position is invalid.
		 */
		public boolean navigateToPreviousPosition();
		

		// maybe rename all *ValidPosition to *call ?
		/** Navigates to the next valid position in the render order.
		 * <br><br>
		 * If the current stack has no next position the navigator, will move to the next non-empty layer first.
		 * If there is no such layer, the current position will be set to invalid.
		 * 
		 * @return True if the new position is valid (there is a next position), false if not.
		 * @throws IllegalStateException If the current position is invalid.
		 */
		public boolean navigateToNextValidPosition();
		
		/** Navigates to the previous valid position in the render order.
		 * <br><br>
		 * If the current stack has no previous position, the navigator will move to the previous non-empty layer first.
		 * If there is no such layer, the current position will be set to invalid.
		 * 
		 * @return True if the new position is valid (there is a previous position), false if not.
		 * @throws IllegalStateException If the current position is invalid.
		 */
		public boolean navigateToPreviousValidPosition();
		
		
		/** Navigates to the first valid position in the render order.
		 * <br><br>
		 * If there are no calls in the navigator's order, the current position will be set to invalid.
		 * 
		 * @return True if the current position is valid, false if it's not.
		 */
		public boolean navigateToFirstValidPosition();
		
		/** Navigates to the last valid position in the render order.
		 * If there are no calls in the navigator's order, the current position will be set to invalid.
		 * 
		 * @return True if the current position is valid, false if it's not.
		 */
		public boolean navigateToLastValidPosition();
		
		
		/**
		 * @return True if the current position is valid, false if not.
		 */
		public boolean isValid();
		
		
		
		/**
		 * @return The current layer index.
		 * @throws IllegalStateException If the current position is invalid.
		 */
		public int getCurrentIndex();
		
		/**
		 * @return The layer for the current index.
		 * @throws IllegalStateException If the current position is invalid.
		 */
		public String getCurrentLayer();
		
		/**
		 * @return The stack for the current index.
		 * @throws IllegalStateException If the current position is invalid.
		 */
		public IRenderLayerStack getCurrentStack();
		
		/**
		 * @return The current position in the current layer.
		 * @throws IllegalStateException If the current position is invalid.
		 */
		public double getCurrentPosition();
		
		/**
		 * @return The callback for the current position.
		 * @throws IllegalStateException If the current position is invalid.
		 */
		public IRenderCallback getCurrentCallback();
		
	}
	
	
}
