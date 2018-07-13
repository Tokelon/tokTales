package com.tokelon.toktales.android.activity;

import java.util.Map;

import com.tokelon.toktales.android.R;
import com.tokelon.toktales.android.activity.integration.GameIntegration;
import com.tokelon.toktales.android.activity.integration.IActivityIntegration;
import com.tokelon.toktales.android.activity.integration.IGameIntegration;
import com.tokelon.toktales.android.activity.integration.SurfaceViewIntegration;
import com.tokelon.toktales.android.render.opengl.RenderGLSurfaceView;
import com.tokelon.toktales.core.engine.TokTales;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class RenderActivity extends AbstractIntegratedCompatActivity {
	
	public static final String ACTIVITY_INTEGRATION_SURFACE_VIEW = "RenderActivity_Integration_SurfaceView";
	public static final String ACTIVITY_INTEGRATION_GAME = "RenderActivity_Integration_Game";

	/**
	 * Whether or not the system UI should be auto-hidden after
	 * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
	 */
	private static final boolean AUTO_HIDE = true;
	
	/**
	 * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
	 * user interaction before hiding the system UI.
	 */
	private static final int AUTO_HIDE_DELAY_MILLIS = 3000;
	
	/**
	 * Some older devices needs a small delay between UI widget updates
	 * and a change of the status and navigation bar.
	 */
	private static final int UI_ANIMATION_DELAY = 300;
	private final Handler mHideHandler = new Handler();
	private RenderGLSurfaceView mContentView;
	private final Runnable mHidePart2Runnable = new Runnable() {
		@SuppressLint("InlinedApi")
		@Override
		public void run() {
			// Delayed removal of status and navigation bar
			
			// Note that some of these constants are new as of API 16 (Jelly Bean)
			// and API 19 (KitKat). It is safe to use them, as they are inlined
			// at compile-time and do nothing on earlier devices.
			mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
					| View.SYSTEM_UI_FLAG_FULLSCREEN
					| View.SYSTEM_UI_FLAG_LAYOUT_STABLE
					| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
					| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
					| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
		}
	};
	
	private final Runnable mShowPart2Runnable = new Runnable() {
		@Override
		public void run() {
			// Delayed display of UI elements
			ActionBar actionBar = getSupportActionBar();
			if (actionBar != null) {
				actionBar.show();
			}
		}
	};
	
	private boolean mVisible;
	private final Runnable mHideRunnable = new Runnable() {
		@Override
		public void run() {
			hide();
		}
	};
	
	/**
	 * Touch listener to use for in-layout UI controls to delay hiding the
	 * system UI. This is to prevent the jarring behavior of controls going away
	 * while interacting with activity UI.
	 */
	private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
		@Override
		public boolean onTouch(View view, MotionEvent motionEvent) {
			if (AUTO_HIDE) {
				delayedHide(AUTO_HIDE_DELAY_MILLIS);
			}
			return false;
		}
	};
	
	private Button mInteractButton;
	
	private SurfaceViewIntegration surfaceViewIntegration;
	
	@Override
	protected Map<String, IActivityIntegration> createActivityIntegrations() {
		Map<String, IActivityIntegration> integrations = super.createActivityIntegrations();
		
		surfaceViewIntegration = new SurfaceViewIntegration(TokTales.getLog(), TokTales.getEngine(), TokTales.getGame());
		integrations.put(ACTIVITY_INTEGRATION_SURFACE_VIEW, surfaceViewIntegration);
		
		IGameIntegration gameIntegration = new GameIntegration(TokTales.getGame());
		integrations.put(ACTIVITY_INTEGRATION_GAME, gameIntegration);
		
		return integrations;
	}
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); // TODO: Call this after assignment?
		
		setContentView(R.layout.activity_render);
		
		mVisible = true;
		mContentView = (RenderGLSurfaceView) findViewById(R.id.fullscreen_content);
		mInteractButton = (Button) findViewById(R.id.interact_button);
		
		// Set up the user interaction to manually show or hide the system UI.
		//mInteractButton.setOnClickListener(view -> toggle());
		
		
		surfaceViewIntegration.integrateRenderView(mContentView);
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		
		hide();
	}
	
	
	
	private void toggle() {
		if (mVisible) {
			hide();
		} else {
			show();
		}
	}
	
	private void hide() {
		// Hide UI first
		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.hide();
		}
		mVisible = false;
		
		// Schedule a runnable to remove the status and navigation bar after a delay
		mHideHandler.removeCallbacks(mShowPart2Runnable);
		mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
	}

	@SuppressLint("InlinedApi")
	private void show() {
		// Show the system bar
		mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
				| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
		mVisible = true;
		
		// Schedule a runnable to display UI elements after a delay
		mHideHandler.removeCallbacks(mHidePart2Runnable);
		mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
	}
	
	/**
	 * Schedules a call to hide() in delay milliseconds, canceling any
	 * previously scheduled calls.
	 */
	private void delayedHide(int delayMillis) {
		mHideHandler.removeCallbacks(mHideRunnable);
		mHideHandler.postDelayed(mHideRunnable, delayMillis);
	}
	
}
