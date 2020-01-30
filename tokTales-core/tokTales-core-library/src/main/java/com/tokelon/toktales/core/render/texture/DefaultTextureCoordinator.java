package com.tokelon.toktales.core.render.texture;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.inject.Inject;

public class DefaultTextureCoordinator implements ITextureCoordinator {
	// TODO: Add optional logging


	private static final int RESERVED_INDICES_COUNT = 2; // 0 and the managing index
	private static final int TEXTURE_UNIT_STARTING_CAPACITY = 8;
	
	private boolean enableMaxCapacityResize = true;
	private boolean enableSafeMode = true;

	
	private final Map<ITexture, BindingInfo> textureBindingsMap;
	private BindingInfo[] textureIndexBindings;

	private int currentBindingCapacity = 0;
	private int currentBindingCount = 0; // Use textureBindingMap.getSize() instead?

	private int replaceIndex = 1;
	
	private final ITextureManager textureManager;
	private final ITextureDriver textureDriver;

	@Inject
	public DefaultTextureCoordinator(ITextureManager textureManager) {
		this.textureManager = textureManager;
		this.textureDriver = textureManager.getTextureDriver();
		
		currentBindingCapacity = TEXTURE_UNIT_STARTING_CAPACITY;
		textureBindingsMap = initBindingsMap(currentBindingCapacity);
		textureIndexBindings = initBindingInfoArray(currentBindingCapacity);
	}
	
	
	private Map<ITexture, BindingInfo> initBindingsMap(int initialCapacity) {
		HashMap<ITexture, BindingInfo> map = new HashMap<>(16); 
		return map;
	}
	
	private BindingInfo[] initBindingInfoArray(int initialCapacity) {
		BindingInfo[] array = new BindingInfo[initialCapacity];
		
		for(int i = 0; i < initialCapacity; i++) {
			array[i] = new BindingInfo(i);
		}
		
		return array;
	}
	
	
	public void enableMaxCapacityResize(boolean enabled) {
		this.enableMaxCapacityResize = enabled;
	}
	
	public void enableSafeMode(boolean enabled) {
		this.enableSafeMode = enabled;
	}
	
	
	private BindingInfo getBindingInfoForIndex(int textureIndex) {
		if(textureIndex >= textureIndexBindings.length) {
			return null;
		}
		
		return textureIndexBindings[textureIndex];
	}
	
	private void setTextureBindingFor(ITexture texture, int textureIndex) {
		BindingInfo currentBinding = getBindingInfoForIndex(textureIndex);
		if(currentBinding.getTexture() != null) {
			BindingInfo previousBinding = textureBindingsMap.remove(currentBinding.getTexture()); // Remove current direct binding, if existing
			if(previousBinding != null) {
				currentBindingCount--;
			}
		}
		
		
		textureBindingsMap.put(texture, currentBinding);
		currentBinding.setTexture(texture);
		currentBindingCount++;
	}

	private void checkIfTextureLoaded(ITexture texture) {
		// Check if texture is loaded in texture manager
		if(!textureManager.hasTextureLoaded(texture)) {
			if(textureManager.hasTexture(texture)) {
				throw new IllegalStateException("Texture is not loaded by this texture manager");
			}
			else {
				throw new IllegalStateException("Texture is not managed by this texture manager");
			}
		}
	}
	

	private int determineOptimalIndexForTexture(ITexture texture, boolean checkIfBound) {
		int textureIndexBound = getTextureIndexIfBound(texture);
		if(checkIfBound && textureIndexBound >= 0) {
			return textureIndexBound;
		}
		
		
		int optimalIndex = -1;
		if(currentBindingCount < currentBindingCapacity - RESERVED_INDICES_COUNT) {
			for(BindingInfo info: textureIndexBindings) {
				
				if(info.getTexture() == null &&
						info.getTextureIndex() != 0 &&
						info.getTextureIndex() != textureManager.getManagingTextureIndex())
				{
					
					optimalIndex = info.getTextureIndex();
					break;
				}
			}
		}
		else {
			// Either try to expand the capacity or pick an existing binding to replace
			
			int maxIndexCount = textureDriver.getMaxTextureUnits() - RESERVED_INDICES_COUNT;
			if(enableMaxCapacityResize && currentBindingCapacity < maxIndexCount) {
				optimalIndex = currentBindingCapacity; // Just the next index
				
				changeMaxCapacity(maxIndexCount);
			}
			else {
				// TODO: Possibly implement replacing the last used render texture
				if(replaceIndex == textureManager.getManagingTextureIndex()) {
					replaceIndex++;
				}
				if(replaceIndex >= currentBindingCapacity) {
					replaceIndex = textureManager.getManagingTextureIndex() == 1 ? 2 : 1; // Do not use 0 as an index
				}
				
				optimalIndex = replaceIndex++;
			}
		}
		
		return optimalIndex;
	}
	
	private int getTextureIndexIfBound(ITexture texture) {
		BindingInfo directBinding = textureBindingsMap.get(texture);
		if(directBinding == null) {
			return -1;
		}
		else {
			int currentIndex = directBinding.getTextureIndex();
			if(enableSafeMode) {
				int currentTextureLocation = textureManager.getTextureLocation(texture);
				int currentBoundTextureLocation = textureDriver.getTextureBoundToIndex(currentIndex);
				
				if(currentTextureLocation == currentBoundTextureLocation) {
					return currentIndex;
				}
				else {
					// some external call has bound the index to a different texture
					return -1;
				}
			}
			else {
				return currentIndex;
			}
		}
	}
	
	private void changeMaxCapacity(int newCapacity) {
		int difference = newCapacity - currentBindingCapacity;
		if(difference < 0) { // less entries
			int counter = - difference;
			
			// Resize binding map
			Iterator<ITexture> mapIterator = textureBindingsMap.keySet().iterator();
			while(mapIterator.hasNext() && counter-- > 0) {
				mapIterator.next();
				mapIterator.remove();
			}
		}
		
		// Resize index bindings array
		BindingInfo[] newIndexBindings = new BindingInfo[newCapacity];
		for(int i = 0; i < newIndexBindings.length; i++) {
			if(i < textureIndexBindings.length) {
				newIndexBindings[i] = textureIndexBindings[i];
			}
			else {
				newIndexBindings[i] = new BindingInfo(i);
			}
		}
		textureIndexBindings = newIndexBindings;
		
		
		// Set new current values
		currentBindingCapacity = newCapacity;
		currentBindingCount = currentBindingCount < currentBindingCapacity ? currentBindingCount : currentBindingCapacity;
	}
	

	private int makeTextureIndex(ITexture texture) {
		checkIfTextureLoaded(texture);

		
		// Find the best binding index
		int targetIndex = determineOptimalIndexForTexture(texture, false);

		// Manage binding information
		setTextureBindingFor(texture, targetIndex);
		
		return targetIndex;
	}
	

	@Override
	public ITextureManager getTextureManager() {
		return textureManager;
	}
	
	@Override
	public int bindTexture(ITexture texture) {
		if(texture == null) {
			throw new NullPointerException();
		}
		
		int textureIndexBound = getTextureIndexIfBound(texture);
		if(textureIndexBound >= 0) {
			// Texture is already bound, no need to do anything
			return textureIndexBound;
		}
		
		
		// Choose the texture index
		int targetIndex = makeTextureIndex(texture);
		
		
		// Get the texture location
		int textureLocation = textureManager.getTextureLocation(texture);
		if(textureLocation < 0) {
			throw new IllegalStateException("Invalid texture location for texture: " + texture);
		}
		
		// Do the binding
		textureDriver.bindTexture(textureLocation, targetIndex);
		
		
		//TokTales.getLog().d(TAG, String.format("Bound texture [%s], location [0x00000%d] to index [%d]", texture, textureLocation, targetIndex));
		return targetIndex;
	}

	@Override
	public int requestIndexFor(ITexture texture) {
		if(texture == null) {
			throw new NullPointerException();
		}
		
		int textureIndexBound = getTextureIndexIfBound(texture);
		if(textureIndexBound >= 0) {
			// Texture is already bound, no need to do anything
			return textureIndexBound;
		}
		
		
		// Choose the texture index
		int targetIndex = makeTextureIndex(texture);

		
		// Unbind
		textureDriver.unbindTexture(targetIndex);
		
		return targetIndex;
	}

	@Override
	public int peekIndexFor(ITexture texture) {
		if(texture == null) {
			throw new NullPointerException();
		}
		
		checkIfTextureLoaded(texture);

		return determineOptimalIndexForTexture(texture, true);
	}

	@Override
	public int getTextureIndexFor(ITexture texture) {
		if(texture == null) {
			throw new NullPointerException();
		}
		
		BindingInfo info = textureBindingsMap.get(texture);
		return info == null ? -1 : info.getTextureIndex();
	}

	@Override
	public void unbindTexture(ITexture texture) {
		if(texture == null) {
			throw new NullPointerException();
		}
		
		BindingInfo info = textureBindingsMap.get(texture);
		
		if(info != null) {
			textureDriver.unbindTexture(info.getTextureIndex());
		}
	}
	
	@Override
	public ITexture getBoundTextureForIndex(int textureIndex) {
		if(textureIndex < 0) {
			throw new IllegalArgumentException("textureIndex must be >= 0");
		}
		
		BindingInfo info = getBindingInfoForIndex(textureIndex);
		return info == null ? null : info.getTexture();
	}

	@Override
	public boolean isTextureBoundForIndex(ITexture texture, int textureIndex) {
		if(texture == null) {
			throw new NullPointerException();
		}
		if(textureIndex < 0) {
			throw new IllegalArgumentException("textureIndex must be >= 0");
		}
		
		BindingInfo info = getBindingInfoForIndex(textureIndex);
		return info == null ? false : info.getTexture().equals(texture);
	}
	
	@Override
	public int getUsedIndexCount() {
		return currentBindingCapacity;
	}
	
	@Override
	public int getBoundIndexCount() {
		return currentBindingCount;
	}
	
	
	public static class BindingInfo {
		private ITexture texture;
		
		private final int textureIndex;
		
		public BindingInfo(int textureIndex) {
			this.textureIndex = textureIndex;
		}
		
		public int getTextureIndex() {
			return textureIndex;
		}
		
		public ITexture getTexture() {
			return texture;
		}

		public void setTexture(ITexture texture) {
			this.texture = texture;
		}
	}
	
	
	public static class TextureCoordinatorFactory implements ITextureCoordinatorFactory {

		@Override
		public ITextureCoordinator create(ITextureManager textureManager) {
			return new DefaultTextureCoordinator(textureManager);
		}
	}

}
