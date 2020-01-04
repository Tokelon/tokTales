package com.tokelon.toktales.android.activity;

import javax.inject.Inject;

import com.tokelon.toktales.android.R;
import com.tokelon.toktales.android.activity.integration.IGameIntegration;
import com.tokelon.toktales.android.activity.integration.ISurfaceViewIntegration;
import com.tokelon.toktales.android.render.opengl.RenderGLSurfaceView;
import com.tokelon.toktales.tools.android.activity.integration.AbstractIntegratedCompatActivity;
import com.tokelon.toktales.tools.android.activity.integration.IActivityIntegrator;
import com.tokelon.toktales.tools.android.activity.integration.IActivityIntegratorBuilder;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.view.View;

public class RenderActivity extends AbstractIntegratedCompatActivity {


	public static final String ACTIVITY_INTEGRATION_SURFACE_VIEW = "RenderActivity_Integration_SurfaceView";
	public static final String ACTIVITY_INTEGRATION_GAME = "RenderActivity_Integration_Game";

	/**
	 * Some older devices needs a small delay between UI widget updates
	 * and a change of the status and navigation bar.
	 */
	private static final int UI_ANIMATION_DELAY = 300;
	

	private final Runnable activityShowRunnable = new Runnable() {
		@Override
		public void run() {
			// Delayed display of UI elements
			ActionBar actionBar = getSupportActionBar();
			if (actionBar != null) {
				actionBar.show();
			}
		}
	};
	
	private final Runnable activityHideRunnable = new Runnable() {
		@Override
		public void run() {
			hide();
		}
	};
	
	private final Runnable activityHidePart2Runnable = new Runnable() {
		@SuppressLint("InlinedApi")
		@Override
		public void run() {
			// Delayed removal of status and navigation bar
			
			// Note that some of these constants are new as of API 16 (Jelly Bean)
			// and API 19 (KitKat). It is safe to use them, as they are inlined
			// at compile-time and do nothing on earlier devices.
			activityContentView.setSystemUiVisibility(
					View.SYSTEM_UI_FLAG_LOW_PROFILE
					| View.SYSTEM_UI_FLAG_FULLSCREEN
					| View.SYSTEM_UI_FLAG_LAYOUT_STABLE
					| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
					| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
					| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
		}
	};
	

	private boolean activityVisible;

	private RenderGLSurfaceView activityContentView;
	private final Handler activityHideHandler = new Handler();
	
	
	private ISurfaceViewIntegration surfaceViewIntegration;
	private IGameIntegration gameIntegration;
	
	
	public RenderActivity() {
		super(ActivityHelper.createDefaultActivityIntegratorBuilder());
	}
	
	public RenderActivity(IActivityIntegratorBuilder integratorBuilder) {
		super(integratorBuilder);
	}
	

	@Inject
	protected void injectDependencies(ISurfaceViewIntegration surfaceViewIntegration, IGameIntegration gameIntegration) {
		this.surfaceViewIntegration = surfaceViewIntegration;
		this.gameIntegration = gameIntegration;
	}
	
	@Override
	protected IActivityIntegrator buildIntegrator(IActivityIntegratorBuilder builder) {
		builder.addIntegration(ACTIVITY_INTEGRATION_SURFACE_VIEW, surfaceViewIntegration);
		builder.addIntegration(ACTIVITY_INTEGRATION_GAME, gameIntegration);
		
		return super.buildIntegrator(builder);
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		ActivityHelper.injectActivityDependencies(this);

		super.onCreate(savedInstanceState); // Call this after assignment?
		
		
		setContentView(R.layout.activity_render);
		
		activityVisible = true;
		activityContentView = (RenderGLSurfaceView) findViewById(R.id.fullscreen_content);

		surfaceViewIntegration.integrateRenderView(activityContentView);
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		
		hide();
	}
	
	private void toggle() {
		if (activityVisible) {
			hide();
		}
		else {
			show();
		}
	}
	
	private void hide() {
		// Hide UI first
		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.hide();
		}
		activityVisible = false;
		
		// Schedule a runnable to remove the status and navigation bar after a delay
		activityHideHandler.removeCallbacks(activityShowRunnable);
		activityHideHandler.postDelayed(activityHidePart2Runnable, UI_ANIMATION_DELAY);
	}

	@SuppressLint("InlinedApi")
	private void show() {
		// Show the system bar
		activityContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
		activityVisible = true;
		
		// Schedule a runnable to display UI elements after a delay
		activityHideHandler.removeCallbacks(activityHidePart2Runnable);
		activityHideHandler.postDelayed(activityShowRunnable, UI_ANIMATION_DELAY);
	}
	
	/**
	 * Schedules a call to hide() in delay milliseconds, canceling any
	 * previously scheduled calls.
	 */
	private void delayedHide(int delayMillis) {
		activityHideHandler.removeCallbacks(activityHideRunnable);
		activityHideHandler.postDelayed(activityHideRunnable, delayMillis);
	}
	
}
