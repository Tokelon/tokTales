package com.tokelon.toktales.core.render.order;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.Map.Entry;

public class RenderLayerStack implements IRenderLayerStack {

	private final TreeMap<Double, IRenderCallback> callbackMap;
	
	private final Collection<Double> unmodCallbackKeys;
	private final Collection<IRenderCallback> unmodCallbackValues;
	
	
	public RenderLayerStack() {
		callbackMap = new TreeMap<Double, IRenderCallback>();
		unmodCallbackKeys = Collections.unmodifiableCollection(callbackMap.keySet());
		unmodCallbackValues = Collections.unmodifiableCollection(callbackMap.values());
	}
	
	
	@Override
	public synchronized void addCallbackAt(double position, IRenderCallback callback) {
		if(callback == null) {
			throw new IllegalArgumentException("callback must not be null");
		}
		
		callbackMap.put(position, callback);
	}
	

	@Override
	public synchronized boolean removeCallbackAt(double position) {
		return callbackMap.remove(position) != null;
	}
	
	@Override
	public IRenderCallback getCallbackAt(double position) {
		return callbackMap.get(position);
	}
	
	
	@Override
	public Collection<IRenderCallback> getCallbacks() {
		return unmodCallbackValues;
	}

	@Override
	public Collection<Double> getPositions() {
		return unmodCallbackKeys;
	}

	
	@Override
	public double getLowerPosition(double position) {
		return callbackMap.lowerKey(position);
	}
	
	@Override
	public double getHigherPosition(double position) {
		return callbackMap.higherKey(position);
	}
	

	@Override
	public double getLowestPosition() {
		return callbackMap.firstKey();
	}

	@Override
	public double getHighestPosition() {
		return callbackMap.lastKey();
	}
	
	
	@Override
	public int getStackSize() {
		return callbackMap.size();
	}
	
	
	@Override
	public IStackNavigator navigator() {
		return new Navigator();
	}

	
	private class Navigator implements IStackNavigator {

		private boolean isValid = false;
		
		private double currentPosition = 0d;
		private IRenderCallback currentCallback = null;
		
		
		private void setValid(double pos, IRenderCallback call) {
			this.currentPosition = pos;
			this.currentCallback = call;
			this.isValid = true;
		}
		private void setInvalid() {
			this.isValid = false;
			this.currentPosition = 0d;
			this.currentCallback = null;
		}
		
		private void checkValid() {
			if(!isValid) {
				throw new IllegalStateException("navigator is not at a valid position");
			}
		}
		
		
		@Override
		public boolean navigateToPosition(double position) {
			IRenderCallback call = callbackMap.get(position);
			if(call == null) {
				setInvalid();
			}
			else {
				setValid(position, call);
			}
			
			return isValid;
		}
		
		@Override
		public boolean navigateToNext() {
			checkValid();
			
			Entry<Double, IRenderCallback> nextEntry = callbackMap.higherEntry(currentPosition);
			if(nextEntry == null) {
				setInvalid();
			}
			else {
				setValid(nextEntry.getKey(), nextEntry.getValue());
			}
			
			return isValid;
		}

		@Override
		public boolean navigateToPrevious() {
			checkValid();
			
			Entry<Double, IRenderCallback> previousEntry = callbackMap.lowerEntry(currentPosition);
			if(previousEntry == null) {
				setInvalid();
			}
			else {
				setValid(previousEntry.getKey(), previousEntry.getValue());
			}
			
			return isValid;
		}

		@Override
		public boolean navigateToFirst() {
			Entry<Double, IRenderCallback> firstEntry = callbackMap.firstEntry();
			if(firstEntry == null) {
				setInvalid();
			}
			else {
				setValid(firstEntry.getKey(), firstEntry.getValue());
			}
			
			return isValid;
		}

		@Override
		public boolean navigateToLast() {
			Entry<Double, IRenderCallback> lastEntry = callbackMap.lastEntry();
			if(lastEntry == null) {
				setInvalid();
			}
			else {
				setValid(lastEntry.getKey(), lastEntry.getValue());
			}

			return isValid;
		}

		@Override
		public boolean isValid() {
			return isValid;
		}

		@Override
		public double getCurrentPosition() {
			checkValid();
			return currentPosition;
		}

		@Override
		public IRenderCallback getCurrentCallback() {
			checkValid();
			return currentCallback;
		}
		
	}
	
	
	/** Creates a new stack by passing the call structure from the given base.
	 * 
	 * @param base The stack which should be used as a base.
	 * @return A new stack containing the same calls as the base.
	 */
	public static RenderLayerStack CreateFromBase(IRenderLayerStack base) {
		RenderLayerStack result = new RenderLayerStack();
		
		synchronized (base) {
			Iterator<Double> positionsIterator = base.getPositions().iterator();
			Iterator<IRenderCallback> callbacksIterator = base.getCallbacks().iterator();
			
			for(int i = 0; i < base.getStackSize(); i++) {
				result.addCallbackAt(positionsIterator.next(), callbacksIterator.next());
			}
		}
		
		return result;
	}
	
	
}
