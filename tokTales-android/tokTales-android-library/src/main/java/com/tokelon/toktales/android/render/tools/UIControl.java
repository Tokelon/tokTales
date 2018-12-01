package com.tokelon.toktales.android.render.tools;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.tokelon.toktales.android.input.TokelonTypeAInputs;
import com.tokelon.toktales.android.input.dispatch.IAndroidInputProducer;
import com.tokelon.toktales.android.input.events.AndroidScreenButtonInputProducer;
import com.tokelon.toktales.android.input.events.AndroidScreenPointerInputProducer;
import com.tokelon.toktales.android.input.events.AndroidScreenPressInputProducer;
import com.tokelon.toktales.core.engine.TokTales;
import com.tokelon.toktales.core.game.screen.view.IScreenViewport;

import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;

public class UIControl implements Runnable {		// Make interface for this ?

	public static final String TAG = "UIControl";

	
	private static final int DELAY_TIME_MILLIS = 16;
	
	private static final int LAST_ACTION_DOWN = 1;
	private static final int LAST_ACTION_UP = 2;
	
	
	
	
	
	private volatile boolean running = false;
	
	private Thread thread;
	private Lock pointerLock = new ReentrantLock();
	
	private final CallControl callControlCross = new CrossCallControl();
	private final CallControl callControlButtons = new ButtonCallControl();
	private final PointerControl pointerControl = new PointerControl();
	
	
	private final AndroidScreenButtonInputProducer androidScreenButtonInputProducer;
	private final AndroidScreenPressInputProducer androidScreenPressInputProducer;
	private final AndroidScreenPointerInputProducer androidScreenPointerInputProducer;
	
	private IScreenViewport uiViewport; // TODO: Extract this, the UIControl should not care about the viewport

	private final IUIOverlayProvider mOverlayProvider;
	
	public UIControl(IUIOverlayProvider overlayProvider, IAndroidInputProducer inputProducer) {
		if(overlayProvider == null || inputProducer == null) {
			throw new NullPointerException();
		}
		
		this.mOverlayProvider = overlayProvider;
		
		this.androidScreenButtonInputProducer = new AndroidScreenButtonInputProducer(inputProducer);
		this.androidScreenPressInputProducer = new AndroidScreenPressInputProducer(inputProducer);
		this.androidScreenPointerInputProducer = new AndroidScreenPointerInputProducer(inputProducer);
	}

	
	public void setViewport(IScreenViewport viewport) {
		this.uiViewport = viewport;
	}
	
	
	
	public synchronized void startControl() {
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stopControl() {
		running = false;
		//thread.join()	 // Wait for the thread to end ?
	}
	
	

	@Override
	public void run() {
		
		while(running) {
			// Overhead?
			pointerLock.lock();
			try {
				
				callControlCross.callControl();
				callControlButtons.callControl();
			}
			finally {
				pointerLock.unlock();
			}
			
			
			
			try {
				Thread.sleep(DELAY_TIME_MILLIS);
			}
			catch(InterruptedException ie) {
				// Nothing
			}
		}
	}
	
	
	
	
	
	
	public boolean onTouch(MotionEvent event) {		// NOT THREAD SAFE | Needs synchronized to be thread-safe

		/* TODO: Implement returning boolean for listeners,
		 * so that we can check here whether the event was consumed
		 */
		
		// Generic Pointer handling for InputScreenPointerCallback
		pointerControl.onTouch(event);
		

		
		// Handling through UIOverlay
		if(!mOverlayProvider.hasUIOverlay()) {
			return true;
		}
		IUIOverlay overlay = mOverlayProvider.getUIOverlay();

		
		
		int actionMasked = MotionEventCompat.getActionMasked(event);
		
		int pointerCount = event.getPointerCount();
		boolean multiPtrs = pointerCount > 1;
		
		boolean consumed = false;
		if(multiPtrs && actionMasked == MotionEvent.ACTION_MOVE) {
			// Call all call controls one single time

			// Call cross control
			callControlCross.onTouchMultiMove(overlay, event);
			
			// Call button control
			callControlButtons.onTouchMultiMove(overlay, event);

			
			// Always return true here ?
			// What happens if false is returned ?
		}
		else {
			
			int pointerIndex = MotionEventCompat.getActionIndex(event);		//event.getActionIndex();
			int pointerId = MotionEventCompat.getPointerId(event, pointerIndex);	//event.getPointerId(pointerIndex);

			int screenx = (int) MotionEventCompat.getX(event, pointerIndex);
			int screeny = (int) MotionEventCompat.getY(event, pointerIndex);
			
			if(uiViewport != null) {
				// This is not synchronized with the viewport setter
				// So it could cause a nullpointerexception or something else
				
				screenx = (int) uiViewport.transformX(screenx);
				screeny = (int) uiViewport.transformY(screeny);
			}
			
			
			// Call cross control
			consumed = callControlCross.onTouch(overlay, multiPtrs, actionMasked, pointerId, screenx, screeny);
			
			// Call button control
			if(!consumed) {
				consumed = callControlButtons.onTouch(overlay, multiPtrs, actionMasked, pointerId, screenx, screeny);
			}
			
		}
		
		// Event was handled by one of the controls
		if(consumed) {
			return true;
		}
		
		
		
		
		// TODO: Fix implementation for multi touch
		
		/* If no listener consumes the event, handle it ourselves.
		 * 
		 */
		if (event.getAction() == MotionEvent.ACTION_DOWN) {						// TODO: What happens on multitouch!?
			//Log.d(TAG, "EVENT X: " +event.getX() +"EVENT Y" +event.getY());
			
			int intx = (int)event.getX();
			int inty = (int)event.getY();

			androidScreenPressInputProducer.invoke(intx, inty);	// TODO: Pass float values instead of int
		}
		
		return true;
		
		
		
		/*
		boolean consumed = false;
		for(int i=0; i < pointerCount; i++) {
			int moveX = (int) MotionEventCompat.getX(event, i);
			int moveY = (int) MotionEventCompat.getY(event, i);
			
			int moveId = MotionEventCompat.getPointerId(event, i); 
			
			consumed = callControlCross.onTouch(event, overlay);
			
			//Prog.getLog().d(TAG, String.format("%d| Consumed: %b", i, consumed));
		}
		*/


		
		/*
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			
			int intx = (int)event.getX();
			int inty = (int)event.getY();
			
			if(overlay.isInCrossBounds(intx, inty)) {
				
				int controlCode = 0;
				switch (overlay.getControlInCross(intx, inty)) {
				case IUIOverlay.CROSS_CONTROL_NONE:
					controlCode = -1;
					break;
				case IUIOverlay.CROSS_CONTROL_LEFT:
					controlCode = TokelonTypeAInputs.VB_LEFT;
					break;
				case IUIOverlay.CROSS_CONTROL_UP:
					controlCode = TokelonTypeAInputs.VB_UP;
					break;
				case IUIOverlay.CROSS_CONTROL_RIGHT:
					controlCode = TokelonTypeAInputs.VB_RIGHT;
					break;
				case IUIOverlay.CROSS_CONTROL_DOWN:
					controlCode = TokelonTypeAInputs.VB_DOWN;
					break;
				default:
					controlCode = 0;
				}
				
				if(controlCode != -1) {
					game.getMasterInputHandler().buttonWentDown(controlCode);
					return true;
				}
			}
		}
		 */
	}

	
	
	private class PointerControl {
		// Copied from TongInputDriver
		
		private static final boolean logDebug = false;

		
		public void onTouch(MotionEvent event) {
			int actionMasked = MotionEventCompat.getActionMasked(event);
			int actionTok = actionFromAndroidToTok(actionMasked);
			
			int pointerCount = event.getPointerCount();
			boolean multiPtrs = pointerCount > 1;
			
			if(multiPtrs) {
				if(actionMasked == MotionEvent.ACTION_MOVE) {
					// Iterate through all pointers with action move

					if(logDebug) TokTales.getLog().d(TAG, String.format("MULTI MOVE Event [pointerCount=%d]", pointerCount));
					
					for(int i=0; i < pointerCount; i++) {
						int moveX = (int) MotionEventCompat.getX(event, i);
						int moveY = (int) MotionEventCompat.getY(event, i);
						
						int moveId = MotionEventCompat.getPointerId(event, i);

						if(logDebug) TokTales.getLog().d(TAG, String.format("MULTI MOVE [actionIndex=%d, pointerId=%d, x=%d, y=%d", i, moveId, moveX, moveY));

						androidScreenPointerInputProducer.invoke(moveId, actionTok, moveX, moveY);
					}
				}
				else {
					// Find the one pointer the action is for and call for that
					
					int actionIndex = MotionEventCompat.getActionIndex(event);
					int pointerId = MotionEventCompat.getPointerId(event, actionIndex);

					int screenx = (int) MotionEventCompat.getX(event, actionIndex);
					int screeny = (int) MotionEventCompat.getY(event, actionIndex);
					
					
					if(logDebug) TokTales.getLog().d(TAG, String.format("MULTI TOUCH Event [action=%d, actionIndex=%d, pointerId=%d, x=%d, y=%d]", actionMasked, actionIndex, pointerId, screenx, screeny));
					
					androidScreenPointerInputProducer.invoke(pointerId, actionTok, screenx, screeny);
				}
			}
			else {
				int pointerIndex = MotionEventCompat.getActionIndex(event);		//event.getActionIndex();
				int pointerId = MotionEventCompat.getPointerId(event, pointerIndex);	//event.getPointerId(pointerIndex);

				int screenx = (int) MotionEventCompat.getX(event, pointerIndex);
				int screeny = (int) MotionEventCompat.getY(event, pointerIndex);
				
				
				if(logDebug) TokTales.getLog().d(TAG, String.format("SINGLE TOUCH Event [action=%d, pointerIndex=%d, pointerId=%d, x=%d, y=%d]", actionMasked, pointerIndex, pointerId, screenx, screeny));

				androidScreenPointerInputProducer.invoke(pointerId, actionTok, screenx, screeny);
			}
			
		}
		
		

		private int actionFromAndroidToTok(int action) {
			int result = -1;
			
			switch (action) {
			case MotionEvent.ACTION_DOWN:
				result = TokelonTypeAInputs.TOUCH_EVENT_DOWN;
				break;
			case MotionEvent.ACTION_MOVE:
				result = TokelonTypeAInputs.TOUCH_EVENT_MOVE;
				break;
			case MotionEvent.ACTION_POINTER_DOWN:
				result = TokelonTypeAInputs.TOUCH_EVENT_POINTER_DOWN;
				break;
			case MotionEvent.ACTION_UP:
				result = TokelonTypeAInputs.TOUCH_EVENT_UP;
				break;
			case MotionEvent.ACTION_POINTER_UP:
				result = TokelonTypeAInputs.TOUCH_EVENT_POINTER_UP;
				break;
			case MotionEvent.ACTION_OUTSIDE:
				result = TokelonTypeAInputs.TOUCH_EVENT_OUTSIDE;
				break;
			case MotionEvent.ACTION_CANCEL:
				result = TokelonTypeAInputs.TOUCH_EVENT_CANCEL;
				break;
			default:
				break;
			}
			
			return result;
		}
		
		private String actionToString(int action) {
		    switch (action) {

		        case MotionEvent.ACTION_DOWN: return "Down";
		        case MotionEvent.ACTION_MOVE: return "Move";
		        case MotionEvent.ACTION_POINTER_DOWN: return "Pointer Down";
		        case MotionEvent.ACTION_UP: return "Up";
		        case MotionEvent.ACTION_POINTER_UP: return "Pointer Up";
		        case MotionEvent.ACTION_OUTSIDE: return "Outside";
		        case MotionEvent.ACTION_CANCEL: return "Cancel";
		    }
		    return "";
		}
	}

	
	private class CallControl {
		
		protected volatile int mCurrentPointerId = -1;
		protected volatile boolean mHasValidPointer = false;
		
		protected volatile boolean mCallButtonDown = false;
		protected volatile boolean mCallButtonUp = false;

		protected volatile int mCallControlDown = -1;
		
		protected volatile int mLastActionId = -1;
		protected int mLastCallControlDown = -1;
		protected int mLastCallControlUp = -1;
		
		
		protected void callControl() {
			if(mCallButtonDown && mCallButtonUp) {
				
				
				if(mLastActionId == LAST_ACTION_DOWN) {
					// Up comes first
					
					// Call up
					callUp(mLastCallControlDown);
					
					
					// Call down
					callDown(mCallControlDown);
				}
				else if(mLastActionId == LAST_ACTION_UP) {
					// Down comes first
					
					// Call up for last control first?
					if(mLastCallControlUp != mLastCallControlDown) {
						callUp(mLastCallControlDown);
					}
					
					// Call down
					callDown(mCallControlDown);
					
					
					
					// Call up
					callUp(mCallControlDown);
				}
				
				
			}
			else if(mCallButtonDown) {
				callDown(mCallControlDown);
				
			}
			else if(mCallButtonUp) {
				callUp(mLastCallControlDown);
			}
		}
		
		
		protected void callDown(int controlDown) {
			int controlCodeDown = getVB(controlDown);
			androidScreenButtonInputProducer.invoke(controlCodeDown, TokelonTypeAInputs.BUTTON_PRESS);
			
			mCallButtonDown = false;
			
			mLastCallControlDown = controlDown;
		}
		
		protected void callUp(int controlUp) {
			int controlCodeUp = getVB(controlUp);
			androidScreenButtonInputProducer.invoke(controlCodeUp, TokelonTypeAInputs.BUTTON_RELEASE);
			
			mCallButtonUp = false;
			
			mLastCallControlUp = controlUp;
		}
		
		

		protected void deleteCurrentPointer() {
			//Prog.getLog().d(TAG, "delete Pointer");
			
			pointerLock.lock();
			try {
				mHasValidPointer = false;
				mCurrentPointerId = -1;

				mCallButtonUp = true;
				
				mLastActionId = LAST_ACTION_UP;
			}
			finally {
				pointerLock.unlock();
			}
		}
		
		protected void setCurrentPointer(int pointerId, int control) {
			//Prog.getLog().d(TAG, "set Pointer: " +pointerId);
			
			pointerLock.lock();
			try {
				mCurrentPointerId = pointerId;
				mHasValidPointer = true;

				mCallControlDown = control;
				mCallButtonDown = true;
				
				mLastActionId = LAST_ACTION_DOWN;
			}
			finally {
				pointerLock.unlock();
			}
		}
		
		
		protected int getVB(int control) {
			return 0;
		}
		
		
		protected void onTouchMultiMove(IUIOverlay overlay, MotionEvent motionEvent) {
			/* Assumes that there is multiple pointers and the action is ACTION_MOVE
			 * 
			 */
		}
		
		protected boolean onTouch(IUIOverlay overlay, boolean multPtr, int action, int pointerId, int screenx, int screeny) {
			
			// Always return false
			return false;
		}
	}
	
	
	
	
	private class ButtonCallControl extends CallControl {
		
		
		@Override
		protected int getVB(int control) {
			int controlCode;
			switch (control) {
			case IUIOverlay.BUTTON_A:
				controlCode = TokelonTypeAInputs.VB_A;
				break;
			case IUIOverlay.BUTTON_B:
				controlCode = TokelonTypeAInputs.VB_B;
				break;
			case IUIOverlay.BUTTON_SP1:
				controlCode = TokelonTypeAInputs.VB_SP1;
				break;
			case IUIOverlay.BUTTON_SET:
				controlCode = TokelonTypeAInputs.VB_SET;
				break;
			default:
				controlCode = -1;	// Must not happen
				break;
			}
			
			return controlCode;
		}
		
		
		
		@Override
		protected void onTouchMultiMove(IUIOverlay overlay, MotionEvent motionEvent) {
			/* Assumes that there is multiple pointers and the action is ACTION_MOVE
			 * 
			 */
			
			if(mHasValidPointer) {
				
				// We only care about our pointer
				// We have to use our pointer id to find all the data we need
				int currentPointerIndex = MotionEventCompat.findPointerIndex(motionEvent, mCurrentPointerId);
				if(currentPointerIndex == -1) {
					TokTales.getLog().w(TAG, "Warning (MultPtr): Action MOVE with a valid pointer had no pointer index for the current pointer id");
					return;
				}
				
				
				
				int moveX = (int) MotionEventCompat.getX(motionEvent, currentPointerIndex);
				int moveY = (int) MotionEventCompat.getY(motionEvent, currentPointerIndex);
				
				if(uiViewport != null) {
					// This is not synchronized with the viewport setter
					// So it could cause a nullpointerexception or something else
					
					moveX = (int) uiViewport.transformX(moveX);
					moveY = (int) uiViewport.transformY(moveY);
				}
				
				
				int moveButton = overlay.getButton(moveX, moveY);
				
				if(moveButton == IUIOverlay.BUTTON_NONE) {
					deleteCurrentPointer();
				}
				else {
					// Test - Is the click still in the same control
					
					if(moveButton != mCallControlDown) {
						// Button has changed
						
						
						pointerLock.lock();
						try {
							// Save the current pointer id because deleteCurrentPointer() is going to delete it
							int currPointerId = mCurrentPointerId;
							
							
							// Delete current control
							deleteCurrentPointer();

							// Set new control
							setCurrentPointer(currPointerId, moveButton);
						}
						finally {
							pointerLock.unlock();
						}
					}
				}
				
			}
			else {
				// Iterate over and check all pointers
				int pointerCount = motionEvent.getPointerCount();
				
				boolean pointerWasSet = false;
				for(int i=0; i < pointerCount && !pointerWasSet; i++) {
					
					int moveX = (int) MotionEventCompat.getX(motionEvent, i);
					int moveY = (int) MotionEventCompat.getY(motionEvent, i);

					if(uiViewport != null) {
						// This is not synchronized with the viewport setter
						// So it could cause a nullpointerexception or something else
						
						moveX = (int) uiViewport.transformX(moveX);
						moveY = (int) uiViewport.transformY(moveY);
					}
					
					
					int moveButton = overlay.getButton(moveX, moveY);
					
					if(moveButton == IUIOverlay.BUTTON_NONE) {
						// Ignore
					}
					else {
						int moveId = MotionEventCompat.getPointerId(motionEvent, i);
						setCurrentPointer(moveId, moveButton);
						pointerWasSet = true;
					}
				}
				
			}
			
		}
		
		
		
		@Override
		protected boolean onTouch(IUIOverlay overlay, boolean multPtr, int action, int pointerId, int screenx, int screeny) {
			
			int button = overlay.getButton(screenx, screeny);
			
			
			if(multPtr) {	// Multiple pointers

				
				if(!mHasValidPointer) {	// No valid pointer
					
					// We cannot use the pointerId because it's always 0 for ACTION_MOVE AND multiple pointers
					if(action == MotionEvent.ACTION_MOVE) {
						
						// Covered by onTouchMultiMove()
						// Should this even be called?
						TokTales.getLog().w(TAG, "Warning (MultPtr): onTouch called with MultiPointerMove");
						
						
						// We always need to return false in this case because the other pointers have to run for this as well
						return false;
					}
					else if(action == MotionEvent.ACTION_POINTER_DOWN) {
						
						if(button == IUIOverlay.BUTTON_NONE) {
							// Do nothing
							return false;
						}
						else {
							setCurrentPointer(pointerId, button);
							return true;
						}
					}
					else if(action == MotionEvent.ACTION_POINTER_UP || action == MotionEvent.ACTION_CANCEL) {
						// We normally don't need to do anything in here
						// But for safety
						
						if(button == IUIOverlay.BUTTON_NONE) {
							// Do nothing
							return false;
						}
						else {
							// Should not happen!
							// There should be a valid pointer in this case
							TokTales.getLog().w(TAG, "Warning (MultPtr): Action UP/CANCEL inside a control with no valid pointer!");
						}
					}
					else {
						TokTales.getLog().d(TAG, "Warning (MultPtr): Some other action just happened (NoValidPtr): " +action);
					}
					
				}
				else {		// YES valid pointer
					

					// We cannot use the pointerId because it's always 0 for ACTION_MOVE AND multiple pointers
					if(action == MotionEvent.ACTION_MOVE) {

						// Covered by onTouchMultiMove()
						// Should this even be called?
						TokTales.getLog().w(TAG, "Warning (MultPtr): onTouch called with MultiPointerMove");
						
						
						// We always need to return false in this case because the other pointers have to run for this as well
						return false;						
					}
					else if(mCurrentPointerId == pointerId) {
						// If its not an ACTION_MOVE, we only care about if this is about our pointer
						
						
						if(action == MotionEvent.ACTION_POINTER_DOWN) {
							// Should not happen!
							TokTales.getLog().w(TAG, "Warning (MultPtr): Action DOWN with a valid pointer!");
						}
						else if(action == MotionEvent.ACTION_POINTER_UP || action == MotionEvent.ACTION_CANCEL) {
							
							// These should not happen because we would have had an ACTION_MOVE before these
							if(button == IUIOverlay.BUTTON_NONE) {
								// Should not happen!
								TokTales.getLog().w(TAG, "Warning (MultPtr): Action UP/CANCEL with a valid pointer outside of buttons!");
							}
							else {
								deleteCurrentPointer();
								return true;
							}
						}
					}
					else {
						
						// There is already another pointer on a button
						// Ignore the second one
						
						// TODO: Put in a priority for some button ?
						
						return false;
					}
				}
				
				
			}
			else {	// Single pointer
				
				if(!mHasValidPointer) {	// No valid pointer
					
					if(action == MotionEvent.ACTION_MOVE || action == MotionEvent.ACTION_DOWN) {
						if(button == IUIOverlay.BUTTON_NONE) {
							// Do nothing
							return false;
						}
						else {
							setCurrentPointer(pointerId, button);
							return true;
						}
					}
					else if(action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {

						if(button == IUIOverlay.BUTTON_NONE) {
							// Do nothing
							return false;
						}
						else {
							// Should not happen!
							// There should be a valid pointer in this case
							TokTales.getLog().w(TAG, "Warning (SinglPtr): Action UP/CANCEL inside a control with no valid pointer!");
						}
					}
				}
				else {	// YES valid pointer
					
					if(mCurrentPointerId == pointerId) {	// Only do something if it is our pointer
						
						if(action == MotionEvent.ACTION_MOVE) {
							if(button == IUIOverlay.BUTTON_NONE) {
								deleteCurrentPointer();
								return false;		// TODO: Why is this a return false ?
							}
							else {
								
								// Test - Is the click still in the same control
								// If not, change
								
								
								if(button != mCallControlDown) {
									// Button has changed
									
									pointerLock.lock();
									try {
										// Delete current control
										deleteCurrentPointer();

										// Set new control
										setCurrentPointer(pointerId, button);
									}
									finally {
										pointerLock.unlock();
									}
								}
								
								
								return true;
							}
						}
						else if(action == MotionEvent.ACTION_DOWN) {
							// Should not happen!
							TokTales.getLog().w(TAG, "Warning (SinglPtr): Action DOWN with a valid pointer!");
						}
						else if(action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
							if(button == IUIOverlay.BUTTON_NONE) {
								// Should not happen!
								TokTales.getLog().w(TAG, "Warning (SinglPtr): Action UP/CANCEL with a valid pointer outside of buttons");
							}
							else {
								deleteCurrentPointer();
								return true;
							}
						}
					}
					else {
						// Must not happen!!
						TokTales.getLog().w(TAG, "Warning (SinglPtr): Valid pointer and does not match the given pointer!");
					}
				}
				
			}

			
			return true;
		}
	}
	

	private class CrossCallControl extends CallControl {
		
		@Override
		protected int getVB(int control) {
			int controlCode;
			switch (control) {
			case ICrossControlOverlayView.CROSS_CONTROL_LEFT:
				controlCode = TokelonTypeAInputs.VB_LEFT;
				break;
			case ICrossControlOverlayView.CROSS_CONTROL_UP:
				controlCode = TokelonTypeAInputs.VB_UP;
				break;
			case ICrossControlOverlayView.CROSS_CONTROL_RIGHT:
				controlCode = TokelonTypeAInputs.VB_RIGHT;
				break;
			case ICrossControlOverlayView.CROSS_CONTROL_DOWN:
				controlCode = TokelonTypeAInputs.VB_DOWN;
				break;
			default:
				controlCode = -1;	// Must not happen
				break;
			}
			
			return controlCode;
		}

		
		@Override
		protected void onTouchMultiMove(IUIOverlay overlay, MotionEvent motionEvent) {
			/* Assumes that there is multiple pointers and the action is ACTION_MOVE
			 * 
			 */
			
			if(mHasValidPointer) {
				
				// We only care about our pointer
				// We have to use our pointer id to find all the data we need
				int currentPointerIndex = MotionEventCompat.findPointerIndex(motionEvent, mCurrentPointerId);
				if(currentPointerIndex == -1) {
					TokTales.getLog().w(TAG, "Warning (MultPtr): Action MOVE with a valid pointer had no pointer index for the current pointer id");
					return;
				}
				
				
				
				int moveX = (int) MotionEventCompat.getX(motionEvent, currentPointerIndex);
				int moveY = (int) MotionEventCompat.getY(motionEvent, currentPointerIndex);
				
				if(uiViewport != null) {
					// This is not synchronized with the viewport setter
					// So it could cause a nullpointerexception or something else
					
					moveX = (int) uiViewport.transformX(moveX);
					moveY = (int) uiViewport.transformY(moveY);
				}
				
				
				int moveControl = overlay.getCrossControlView().getControlInCross(moveX, moveY);
				
				
				if(moveControl == ICrossControlOverlayView.CROSS_OUTSIDE) {
					deleteCurrentPointer();
					//return false;
				}
				else if(moveControl == ICrossControlOverlayView.CROSS_CONTROL_NONE) {
					deleteCurrentPointer();
					//return true;
				}
				else {
					// Test - Is the click still in the same control??
					// If not, it won't be registered!
					
					
					if(moveControl != mCallControlDown) {
						// Control has changed!
						
						pointerLock.lock();
						try {
							// Save the current pointer id because deleteCurrentPointer() is going to delete it
							int currPointerId = mCurrentPointerId;
							
							
							// Delete current control
							deleteCurrentPointer();

							// Set new control
							setCurrentPointer(currPointerId, moveControl);
						}
						finally {
							pointerLock.unlock();
						}
					}

				}
				
			}
			else {
				// Iterate over and check all pointers
				int pointerCount = motionEvent.getPointerCount();
				
				boolean pointerWasSet = false;
				for(int i=0; i < pointerCount && !pointerWasSet; i++) {
					int moveX = (int) MotionEventCompat.getX(motionEvent, i);
					int moveY = (int) MotionEventCompat.getY(motionEvent, i);
					
					if(uiViewport != null) {
						// This is not synchronized with the viewport setter
						// So it could cause a nullpointerexception or something else
						
						moveX = (int) uiViewport.transformX(moveX);
						moveY = (int) uiViewport.transformY(moveY);
					}
					
					
					int moveControl = overlay.getCrossControlView().getControlInCross(moveX, moveY);
					
					
					if(moveControl == ICrossControlOverlayView.CROSS_OUTSIDE) {
						// Ignore
					}
					else if(moveControl == ICrossControlOverlayView.CROSS_CONTROL_NONE) {
						// Ignore
					}
					else {
						
						int moveId = MotionEventCompat.getPointerId(motionEvent, i);
						setCurrentPointer(moveId, moveControl);
						pointerWasSet = true;
					}
				}
				
				// No point in returning anything
				//return pointerWasSet;
			}
			
		}
		
		
		@Override
		protected boolean onTouch(IUIOverlay overlay, boolean multPtr, int action, int pointerId, int screenx, int screeny) {
			
			int control = overlay.getCrossControlView().getControlInCross(screenx, screeny);
			
			
			
			if(multPtr) {	// Multiple pointers

				
				if(!mHasValidPointer) {	// No valid pointer
					
					// We cannot use the pointerId because it's always 0 for ACTION_MOVE AND multiple pointers
					if(action == MotionEvent.ACTION_MOVE) {
						
						// Covered by onTouchMultiMove()
						// Should this even be called?
						TokTales.getLog().w(TAG, "Warning (MultPtr): onTouch called with MultiPointerMove");
						
						
						// We always need to return false in this case because the other pointers have to run for this as well
						return false;
					}
					else if(action == MotionEvent.ACTION_POINTER_DOWN) {
						
						if(control == ICrossControlOverlayView.CROSS_OUTSIDE) {
							// Do nothing
							return false;
						}
						else if(control == ICrossControlOverlayView.CROSS_CONTROL_NONE) {
							// Do nothing but consume the event
							return true;
						}
						else {
							setCurrentPointer(pointerId, control);
							return true;
						}
					}
					else if(action == MotionEvent.ACTION_POINTER_UP || action == MotionEvent.ACTION_CANCEL) {
						// We normally don't need to do anything in here
						// But for safety
						
						if(control == ICrossControlOverlayView.CROSS_OUTSIDE) {
							// Do nothing
							return false;
						}
						else if(control == ICrossControlOverlayView.CROSS_CONTROL_NONE) {
							// Do nothing but consume the event
							return true;
						}
						else {
							// Should not happen!
							// There should be a valid pointer in this case
							TokTales.getLog().w(TAG, "Warning (MultPtr): Action UP/CANCEL inside a control with no valid pointer!");
						}
					}
					else {
						TokTales.getLog().d(TAG, "Warning (MultPtr): Some other action just happened (NoValidPtr): " +action);
					}
					
				}
				else {		// YES valid pointer
					

					// We cannot use the pointerId because it's always 0 for ACTION_MOVE AND multiple pointers
					if(action == MotionEvent.ACTION_MOVE) {

						// Covered by onTouchMultiMove()
						// Should this even be called?
						TokTales.getLog().w(TAG, "Warning (MultPtr): onTouch called with MultiPointerMove");
						
						
						// We always need to return false in this case because the other pointers have to run for this as well
						return false;						
					}
					else if(mCurrentPointerId == pointerId) {
						// If its not an ACTION_MOVE, we only care about if this is about our pointer
						
						
						if(action == MotionEvent.ACTION_POINTER_DOWN) {
							// Should not happen!
							TokTales.getLog().w(TAG, "Warning (MultPtr): Action DOWN with a valid pointer!");
						}
						else if(action == MotionEvent.ACTION_POINTER_UP || action == MotionEvent.ACTION_CANCEL) {
							
							// These should not happen because we would have had an ACTION_MOVE before these
							if(control == ICrossControlOverlayView.CROSS_OUTSIDE) {
								// Should not happen!
								TokTales.getLog().w(TAG, "Warning (MultPtr): Action UP/CANCEL with a valid pointer outside of cross!");
							}
							else if(control == ICrossControlOverlayView.CROSS_CONTROL_NONE) {
								// Should not happen!
								TokTales.getLog().w(TAG, "Warning (MultPtr): Action UP/CANCEL with a valid pointer outside of control!");
							}
							else {
								deleteCurrentPointer();
								return true;
							}
						}
						
					}
					else {
						// There is already another pointer on the cross
						// Ignore the second one
						return false;
					}
				}
				
				
			}
			else {	// Single pointer
				
				if(!mHasValidPointer) {	// No valid pointer
					
					if(action == MotionEvent.ACTION_MOVE || action == MotionEvent.ACTION_DOWN) {
						if(control == ICrossControlOverlayView.CROSS_OUTSIDE) {
							// Do nothing
							return false;
						}
						else if(control == ICrossControlOverlayView.CROSS_CONTROL_NONE) {
							// Do nothing but consume the event
							return true;
						}
						else {
							setCurrentPointer(pointerId, control);
							return true;
						}
					}
					else if(action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {

						if(control == ICrossControlOverlayView.CROSS_OUTSIDE) {
							// Do nothing
							return false;
						}
						else if(control == ICrossControlOverlayView.CROSS_CONTROL_NONE) {	// Should not happen?
							// Do nothing but consume the event
							return true;
						}
						else {
							// Should not happen!
							// There should be a valid pointer in this case
							TokTales.getLog().w(TAG, "Warning (SinglPtr): Action UP/CANCEL inside a control with no valid pointer!");
						}
					}
				}
				else {	// YES valid pointer
					
					if(mCurrentPointerId == pointerId) {	// Only do something if it is our pointer
						
						if(action == MotionEvent.ACTION_MOVE) {
							
							if(control == ICrossControlOverlayView.CROSS_OUTSIDE) {
								deleteCurrentPointer();
								return false;				// TODO 930: Why does this return false ?
							}
							else if(control == ICrossControlOverlayView.CROSS_CONTROL_NONE) {
								deleteCurrentPointer();
								return true;				// TODO 930: And this one true
							}
							else {
								// TODO: Test - Is the click still in the same control??
								// If not, change
								
								if(control != mCallControlDown) {
									// Control has changed!
									
									pointerLock.lock();
									try {
										// Delete current control
										deleteCurrentPointer();

										// Set new control
										setCurrentPointer(pointerId, control);
									}
									finally {
										pointerLock.unlock();
									}
								}
								
								
								return true;
							}
						}
						else if(action == MotionEvent.ACTION_DOWN) {
							// Should not happen!
							TokTales.getLog().w(TAG, "Warning (SinglPtr): Action DOWN with a valid pointer!");
						}
						else if(action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
							
							if(control == ICrossControlOverlayView.CROSS_OUTSIDE) {
								// Should not happen!
								TokTales.getLog().w(TAG, "Warning (SinglPtr): Action UP/CANCEL with a valid pointer outside of cross!");
							}
							else if(control == ICrossControlOverlayView.CROSS_CONTROL_NONE) {
								// Should not happen!
								TokTales.getLog().w(TAG, "Warning (SinglPtr): Action UP/CANCEL with a valid pointer outside of control!");
							}
							else {
								deleteCurrentPointer();
								return true;
							}
						}
						
					}
					else {
						// Must not happen!!
						TokTales.getLog().w(TAG, "Warning (SinglPtr): Valid pointer and does not match the given pointer!");
					}
				}
				
			}
			
			
			return true;
		}
		
	}
	
}
