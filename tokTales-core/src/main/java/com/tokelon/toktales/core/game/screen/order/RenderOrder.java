package com.tokelon.toktales.core.game.screen.order;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tokelon.toktales.core.game.screen.order.IRenderLayerStack.IStackNavigator;

public class RenderOrder implements IRenderOrder {

	private static final String EMPTY_TAG = "";

	
	private final List<IRenderLayerStack> layerStacks;
	private final List<String> layerNames;
	
	private final List<IRenderLayerStack> unmodLayerStacks;
	private final List<String> unmodLayerNames;
	
	private final Map<String, IRenderLayerStack> registerMap;
	private final Map<String, IRenderLayerStack> unmodRegisterMap;
	
	private final Map<String, Set<IRenderLayerStack>> taggedMap;
	
	public RenderOrder() {
		layerStacks = new ArrayList<IRenderLayerStack>();
		unmodLayerStacks = Collections.unmodifiableList(layerStacks);
		
		layerNames = new ArrayList<String>();
		unmodLayerNames = Collections.unmodifiableList(layerNames);
		
		registerMap = new HashMap<String, IRenderLayerStack>();
		unmodRegisterMap = Collections.unmodifiableMap(registerMap);
		
		taggedMap = new HashMap<String, Set<IRenderLayerStack>>();
		
		insertNewLayer(0, LAYER_BOTTOM);
		insertNewLayer(1, LAYER_TOP);
	}
	
	


	
	@Override
	public synchronized void insertLayerAt(int index, String layerName) {
		insertTaggedLayerAt(index, layerName, EMPTY_TAG);
	}

	
	@Override
	public synchronized boolean removeLayer(String layerName) {
		return removeTaggedLayer(layerName, EMPTY_TAG);
	}
	

	@Override
	public synchronized void insertTaggedLayerAt(int index, String layerName, String tag) {
		if(index < 0) {
			throw new IllegalArgumentException("index must be > 0");
		}
		if(index == 0) {
			throw new IllegalArgumentException("cannot insert at index 0 because of bottom layer");
		}
		if(layerName == null) {
			throw new IllegalArgumentException("layerName must not be null");
		}
		if(tag == null) {
			throw new IllegalArgumentException("tag must not be null");
		}
		
		
		int topIndex = layerStacks.size() - 1;
		
		if(index >= layerStacks.size()) {
			// Fill in empty layers
			for(int i = layerStacks.size(); i <= index; i++) {
				insertPlaceholderLayer(i);
			}
		}
		
		
		if(index >= layerStacks.size() - 1) {
			// Move top layer to new top index
			IRenderLayerStack topStack = layerStacks.get(topIndex);
			String topName = layerNames.get(topIndex);
			
			layerStacks.set(topIndex, null);
			layerNames.set(topIndex, null);
			
			layerStacks.add(index + 1, topStack);
			layerNames.add(index + 1, topName);
		}
		
		IRenderLayerStack resultingStack = insertLayerInternal(index, layerName);
		
		
		// Add to tagged
		Set<IRenderLayerStack> tagStacks = taggedMap.get(tag);
		if(tagStacks == null) {
			tagStacks = new HashSet<IRenderLayerStack>();
			taggedMap.put(tag, tagStacks);
		}
		
		tagStacks.add(resultingStack);	// Will replace it if already there
		
	}
	
	@Override
	public synchronized boolean removeTaggedLayer(String layerName, String tag) {
		if(layerName == null) {
			throw new IllegalArgumentException("layerName must not be null");
		}
		if(tag == null) {
			throw new IllegalArgumentException("tag must not be null");
		}
		
		Set<IRenderLayerStack> tagStacks = taggedMap.get(tag);
		
		if(tagStacks != null) {
			for(int i = 0; i < layerNames.size(); i++) {
				String layer = layerNames.get(i);
				
				if(layer.equals(layerName) && tagStacks.remove(layerStacks.get(i))) {
					layerNames.remove(i);
					layerStacks.remove(i);
					
					return true;
				}
			}
		}
		
		return false;
	}
	
	@Override
	public synchronized boolean removeAllTaggedLayers(String tag) {
		if(tag == null) {
			throw new IllegalArgumentException("tag must not be null");
		}
		
		Set<IRenderLayerStack> tagStacks = taggedMap.get(tag);
		
		if(tagStacks != null) {
			boolean removedSome = false;
			for(IRenderLayerStack stack: tagStacks) {
				int index = layerStacks.indexOf(stack);
				
				if(index != -1) {
					layerNames.remove(index);
					layerStacks.remove(index);
					removedSome = true;	
				}
			}
			
			taggedMap.remove(tag);
			return removedSome;			
		}
		
		return false;
	}
	
	
	private IRenderLayerStack insertLayerInternal(int index, String layerName) {
		IRenderLayerStack resultingStack;
		if(index < layerStacks.size()) {	// index entry exists
			if(layerStacks.get(index) == null) { // index is empty
				// set new layer at empty index
				
				RenderLayerStack newStack = new RenderLayerStack();
				setLayer(index, layerName, newStack);
				
				resultingStack = newStack;
			}
			else if(layerNames.get(index).equals(layerName)) {	// layer already exists with the given name
				// Do nothing but return the already existing stack
				resultingStack = layerStacks.get(index);
			}
			else {
				resultingStack = insertNewLayer(index, layerName);
			}
		}
		else {
			resultingStack = insertNewLayer(index, layerName);
		}
		
		return resultingStack;
	}
	
	private IRenderLayerStack insertNewLayer(int index, String layerName) {
		RenderLayerStack newStack = new RenderLayerStack();
		
		layerStacks.add(index, newStack);
		layerNames.add(index, layerName);
		
		return newStack;
	}
	
	private void insertPlaceholderLayer(int index) {
		layerStacks.add(index, null);
		layerNames.add(index, null);
	}
	
	private void setLayer(int index, String layerName, RenderLayerStack stack) {
		layerStacks.set(index, stack);
		layerNames.set(index, layerName);
	}

	

	
	@Override
	public synchronized boolean registerCall(String layerName, double position, IRenderCallback callback) {
		IRenderLayerStack registerStack = registerMap.get(layerName);
		if(registerStack == null) {
			registerStack = new RenderLayerStack();
			registerMap.put(layerName, registerStack);
		}
		
		registerStack.addCallbackAt(position, callback);
		
		
		int possibleLayerIndex = layerNames.indexOf(layerName);
		if(possibleLayerIndex != -1) {
			IRenderLayerStack indexedStack = layerStacks.get(possibleLayerIndex);
			indexedStack.addCallbackAt(position, callback);
			return true;
		}
		
		return false;
	}
	
	@Override
	public synchronized boolean removeRegisteredCall(String layerName, double position) {
		IRenderLayerStack registerStack = registerMap.get(layerName);
		if(registerStack != null) {
			// Remove layer if empty ?
			return registerStack.removeCallbackAt(position);
		}
		
		return false;
	}
	
	
	@Override
	public synchronized boolean hasLayer(String layerName) {
		return layerNames.contains(layerName);
	}
	
	@Override
	public synchronized boolean isIndexEmpty(int index) {
		if(index < 0 || index >= layerStacks.size()) {
			throw new IllegalArgumentException("index must be >= 0 and < index count");
		}
		
		return layerStacks.get(index) == null;
	}
	
	
	@Override
	public synchronized int getIndexForLayer(String layerName) {
		return layerNames.indexOf(layerName);
	}
	
	@Override
	public synchronized String getLayerForIndex(int index) {
		if(index < 0 || index >= layerNames.size()) {
			throw new IllegalArgumentException("index must be >= 0 and < index count");
		}
		
		return layerNames.get(index);
	}

	
	
	@Override
	public synchronized IRenderLayerStack getStackForLayer(String layerName) {
		int layerIndex = layerNames.indexOf(layerName);
		return layerIndex < 0 ? null : layerStacks.get(layerIndex); 
	}
	
	@Override
	public synchronized IRenderLayerStack getStackForIndex(int index) {
		if(index < 0 || index >= layerNames.size()) {
			throw new IllegalArgumentException("index must be >= 0 and < index count");
		}
		
		return layerStacks.get(index);
	}

	
	@Override
	public Collection<IRenderLayerStack> getLayerStacks() {
		return unmodLayerStacks;
	}

	@Override
	public Collection<String> getLayers() {
		return unmodLayerNames;
	}
	
	
	@Override
	public Map<String, IRenderLayerStack> getRegisteredCalls() {
		return unmodRegisterMap;
	}
	
	@Override
	public Map<String, ? extends Collection<IRenderLayerStack>> getTaggedLayers() {
		return taggedMap;
	}
	
	
	@Override
	public int getFullLayerCount() {
		return layerStacks.size();
	}
	
	@Override
	public int getAddedLayerCount() {
		return layerStacks.size() - 2;	// Exclude bottom and top layers
	}
	
	@Override
	public int getTopIndex() {
		return layerStacks.size() - 1;
	}
	
	
	@Override
	public IOrderNavigator navigator() {
		return new Navigator();
	}
	
	
	private class Navigator implements IOrderNavigator {

		
		private Map<IRenderLayerStack, IRenderLayerStack.IStackNavigator> layerNavigatorMap;
		
		private boolean isValid = false;
		
		private int currentIndex = 0;
		private String currentLayer = null;
		private IRenderLayerStack currentStack = null;
		
		private IStackNavigator currentStackNavigator = null;
		
		
		
		public Navigator() {
			layerNavigatorMap = new HashMap<IRenderLayerStack, IRenderLayerStack.IStackNavigator>();
		}
		
		
		private IStackNavigator setValid(int index) {
			this.currentIndex = index;
			this.currentLayer = layerNames.get(index);
			this.currentStack = layerStacks.get(index);
			
			IStackNavigator navigator = layerNavigatorMap.get(currentStack);
			if(navigator == null) {
				navigator = currentStack.navigator();
				layerNavigatorMap.put(currentStack, navigator);
			}
			this.currentStackNavigator = navigator;
			
			this.isValid = true;
			return navigator;
		}
		
		private void setInvalid() {
			this.isValid = false;
			this.currentIndex = 0;
			this.currentLayer = null;
			this.currentStack = null;
			this.currentStackNavigator = null;
		}
		
		private void checkValid() {
			if(!isValid) {
				throw new IllegalStateException("navigator is not at a valid position");
			}
		}
		
		
		
		@Override
		public boolean navigateTo(int index, double position) {
			if(index < 0 || index >= layerStacks.size() || layerStacks.get(index) == null) {
				setInvalid();
			}
			else {
				IStackNavigator navigator = setValid(index);
				if(!navigator.navigateToPosition(position)) {
					setInvalid();
				}
			}
			
			return isValid;
		}

		@Override
		public boolean navigateTo(String layer, double position) {
			if(layer == null) {
				throw new NullPointerException();
			}
			
			return navigateTo(layerNames.indexOf(layer), position);
		}

		
		@Override
		public boolean navigateToNextIndex() {
			checkValid();
			
			int nextIndex = currentIndex + 1;
			if(nextIndex < 0 || nextIndex >= layerStacks.size() || layerStacks.get(nextIndex) == null) {
				setInvalid();
			}
			else {
				IStackNavigator navigator = setValid(nextIndex);
				if(!navigator.navigateToFirst()) {
					setInvalid();
				}
			}
			
			return isValid;
		}

		@Override
		public boolean navigateToPreviousIndex() {
			checkValid();
			
			int previousIndex = currentIndex - 1;
			if(previousIndex < 0 || previousIndex >= layerStacks.size() || layerStacks.get(previousIndex) == null) {
				setInvalid();
			}
			else {
				IStackNavigator navigator = setValid(previousIndex);
				if(!navigator.navigateToFirst()) {
					setInvalid();
				}
			}
			
			return isValid;
		}

		
		@Override
		public boolean navigateToNextLayer() {
			checkValid();
			
			int nextIndex = currentIndex + 1;
			if(!navigateToNextLayerStartingAt(nextIndex, true)) {
				setInvalid();
			}
						
			return isValid;
		}
		
		@Override
		public boolean navigateToPreviousLayer() {
			checkValid();
			
			int previousIndex = currentIndex - 1;
			if(!navigateToPreviousLayerStartingAt(previousIndex, true)) {
				setInvalid();
			}
			
			return isValid;
		}
		
		
		@Override
		public boolean navigateToBottomLayer() {
			if(layerNames.isEmpty()) {
				setInvalid();
			}
			else {
				IStackNavigator navigator = setValid(0);
				if(!navigator.navigateToFirst()) {
					setInvalid();
				}
			}
			
			return isValid;
		}

		@Override
		public boolean navigateToTopLayer() {
			if(layerNames.isEmpty()) {
				setInvalid();
			}
			else {
				IStackNavigator navigator = setValid(0);
				if(!navigator.navigateToLast()) {
					setInvalid();
				}
			}
			
			return isValid;
		}
		
		
		@Override
		public boolean navigateToNextPosition() {
			checkValid();
			if(!currentStackNavigator.navigateToNext()) {
				setInvalid();
			}
			
			return isValid;
		}

		@Override
		public boolean navigateToPreviousPosition() {
			checkValid();
			if(!currentStackNavigator.navigateToPrevious()) {
				setInvalid();
			}
			
			return isValid;
		}

		
		@Override
		public boolean navigateToNextValidPosition() {
			checkValid();
			if(!currentStackNavigator.navigateToNext()) {
				if(!navigateToNextLayerStartingAt(currentIndex + 1, true)) {
					setInvalid();
				}
			}
			
			return isValid;
		}
		
		@Override
		public boolean navigateToPreviousValidPosition() {
			checkValid();
			if(!currentStackNavigator.navigateToPrevious()) {
				if(!navigateToPreviousLayerStartingAt(currentIndex - 1, false)) {
					setInvalid();
				}
			}
			
			return isValid;
		}

		
		@Override
		public boolean navigateToFirstValidPosition() {
			if(layerNames.isEmpty()) {
				setInvalid();
			}
			else {
				if(!navigateToNextLayerStartingAt(0, true)) {
					setInvalid();
				}
			}
			
			return isValid;
		}

		@Override
		public boolean navigateToLastValidPosition() {
			if(layerNames.isEmpty()) {
				setInvalid();
			}
			else {
				if(!navigateToPreviousLayerStartingAt(layerStacks.size() - 1, false)) {
					setInvalid();
				}
			}
			
			return isValid;
		}
		
		
		private boolean navigateToNextLayerStartingAt(int index, boolean firstNotLastPosition) {
			for(int i = index; i < layerStacks.size(); i++) {
				IRenderLayerStack stack = layerStacks.get(i);
				if(stack != null && stack.getStackSize() > 0) {
					
					IStackNavigator navigator = setValid(i);
					boolean valid = firstNotLastPosition ? navigator.navigateToFirst() : navigator.navigateToLast();
					if(!valid) {
						// should not happen because stack is not empty
						setInvalid();
					}
					
					return true;
				}
			}
			
			return false;
		}
		
		private boolean navigateToPreviousLayerStartingAt(int index, boolean firstNotLastPosition) {
			for(int i = index; i >= 0; i--) {
				IRenderLayerStack stack = layerStacks.get(i);
				if(stack != null && stack.getStackSize() > 0) {
					
					IStackNavigator navigator = setValid(i);
					boolean valid = firstNotLastPosition ? navigator.navigateToFirst() : navigator.navigateToLast();
					if(!valid) {
						// should not happen because stack is not empty
						setInvalid();
					}
					
					return true;
				}
			}
			
			return false;
		}
		

		@Override
		public boolean isValid() {
			checkValid();
			return isValid;
		}

		@Override
		public int getCurrentIndex() {
			checkValid();
			return currentIndex;
		}

		@Override
		public String getCurrentLayer() {
			checkValid();
			return currentLayer;
		}

		@Override
		public IRenderLayerStack getCurrentStack() {
			checkValid();
			return currentStack;
		}

		@Override
		public double getCurrentPosition() {
			checkValid();
			return currentStackNavigator.getCurrentPosition();
		}

		@Override
		public IRenderCallback getCurrentCallback() {
			checkValid();
			return currentStackNavigator.getCurrentCallback();
		}
		
	}
	
	
	/** Creates a new render order by copying the layer structure from the given base.
	 * 
	 * @param base The render order which should be used as the base.
	 * @return A new render order containing the equal layers structure as base.
	 */
	public static RenderOrder CreateFromBase(IRenderOrder base) {
		RenderOrder result = new RenderOrder();
		
		synchronized (base) {
			Iterator<String> layerIterator = base.getLayers().iterator();
			Iterator<IRenderLayerStack> stackIterator = base.getLayerStacks().iterator();
			
			for(int i = 0; i < base.getFullLayerCount(); i++) {
				String layer = layerIterator.next();
				IRenderLayerStack stack = stackIterator.next();
				
				if(i == 0 || i == base.getFullLayerCount()-1) {
					// bottom and top layers | do not replace name but only the stack
					
					// If one of the layer stacks is null, ignore it
					if(stack != null) {
						result.layerStacks.set(i, RenderLayerStack.CreateFromBase(stack));	
					}
				}
				else {
					// In this case null is a valid value and will be applied
					result.layerStacks.add(i, stack == null ? null : RenderLayerStack.CreateFromBase(stack));
					result.layerNames.add(i, layer);
				}
			}
			
			
			Map<String, IRenderLayerStack> registerMap = base.getRegisteredCalls();
			for(String registeredLayer: registerMap.keySet()) {
				IRenderLayerStack registeredStack = registerMap.get(registeredLayer);
				result.registerMap.put(registeredLayer, RenderLayerStack.CreateFromBase(registeredStack));
			}
			
		}
		
		return result;
	}
	
	
}
