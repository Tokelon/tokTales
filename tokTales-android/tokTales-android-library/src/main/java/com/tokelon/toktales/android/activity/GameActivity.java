package com.tokelon.toktales.android.activity;

import com.tokelon.toktales.android.R;
import com.tokelon.toktales.android.activity.integration.IKeyboardActivityIntegration;
import com.tokelon.toktales.android.logic.process.GLRenderingProcess;
import com.tokelon.toktales.android.render.opengl.RenderGLSurfaceView;
import com.tokelon.toktales.core.engine.TokTales;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;

public class GameActivity extends AbstractBaseActivity implements IConsoleActivity, IDebugActivity {

	public static final String TAG = "GameActivity";
	
	
	private View mRootView;
	private RenderGLSurfaceView mRenderView;
	private EditText mTextView;
	
	private AlertDialog.Builder mBackDialogBuilder;
	
	private ProxyTextWatcher textViewProxyTextWatcher;
	
	private GLRenderingProcess renderingProcess;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setToFullscreen();
		initDialog();
		initContent();
		initLogic();
	}
	
	
	
	/* Init methods */
	
	private void initContent() {
		
		LinearLayout layout = new LinearLayout(this);
		
		mRenderView = new RenderGLSurfaceView(this);
		mTextView = new EditText(this);

		registerForContextMenu(mRenderView);
		
		
		layout.addView(mRenderView);
		layout.addView(mTextView, 0, 0);

		
		mRootView = layout;
		setContentView(mRootView);
	}
	
	private void initLogic() {
		
		mTextView.addTextChangedListener(textViewProxyTextWatcher = new ProxyTextWatcher());
		mTextView.setOnKeyListener(new TextViewOnKeyListener());
		
		renderingProcess = new GLRenderingProcess(TokTales.getGame(), TokTales.getEngine());

		// Use with OpenGL renderer
		renderingProcess.setObjRenderView(mRenderView);
		renderingProcess.startProcess();
	}
	
	private void initDialog() {
		// Initialize the exit dialog builder
		
		mBackDialogBuilder = createBackDialogBuilder();
	}
	

	private void setToFullscreen() {
		// Remove title from the activity window
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// Set the activity to fullscreen and no sleep
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}
	
	
	
	/* Lifecycle methods */
	
	@Override
	protected void onResume() {
		super.onResume();
		
		renderingProcess.unpause();
	}


	@Override
	protected void onPause() {
		super.onPause();
		
		renderingProcess.pause();
	}
	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		renderingProcess.endProcess();
	}
	
	
	
	@SuppressLint("NewApi") @Override
	public void onWindowFocusChanged(boolean hasFocus) { 
		super.onWindowFocusChanged(hasFocus);

		if (hasFocus) {
	
			// Puts the activity window into 'immersive fullscreen', if supported
			if (Build.VERSION.SDK_INT > 16) {
				mRootView.setSystemUiVisibility(
						View.SYSTEM_UI_FLAG_LAYOUT_STABLE
						| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
						| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
						| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
						| View.SYSTEM_UI_FLAG_FULLSCREEN
						| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
			}
		}
	}
	
	
	@Override
	public void onBackPressed() {
		// Show a confirmation dialog instead of exiting immediately
		mBackDialogBuilder.create().show();
	}

	private void superOnBackPressed() {
		// Actually exit activity
		super.onBackPressed();
	}
	
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		
		
		// When asked for a context menu, we are actually going to inflate our options menu
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(getMenuResource(), menu);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		
		// And then pass any actions to the options menu
		return this.onOptionsItemSelected(item);
	}
	
	
	
	/* Other Activity methods */
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemID = item.getItemId();
		
		if(itemID == R.id.itemMenuGameOrientationPortrait) {
			
			if(getResources().getConfiguration().orientation != Configuration.ORIENTATION_PORTRAIT) {
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);	
			}
			
			return true;
		}
		else if(itemID == R.id.itemMenuGameOrientationLandscape) {
			
			if(getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);	
			}
			
			return true;
		}
		else {
			return super.onOptionsItemSelected(item);
		}
	}
	
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		
		// Stop Activity from recreating
		TokTales.getLog().i(TAG, "TaleActivity configuration changed");
	}
	
	
	
	/* Overridable methods */
	
	
	/**
	 * 
	 * @return The menu layout id.
	 */
	protected int getMenuResource() {
		return R.menu.game_menu;
	}
	

	protected AlertDialog.Builder createBackDialogBuilder() {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle(getResources().getText(R.string.dialog_title_exit))
		.setPositiveButton(R.string.dialog_button_exit_positive, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				GameActivity.this.superOnBackPressed();
			}
		})
		.setNegativeButton(R.string.dialog_button_exit_negative, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// Nothing
			}
		});

		return builder;
	}

	
	
	/* Interface methods */
	
	
	@Override
	public void getConsoleInput(TextWatcher consoleInputTextWatcher) {
		textViewProxyTextWatcher.setClient(consoleInputTextWatcher);
		
		IKeyboardActivityIntegration keyboardIntegration = getIntegrator().getIntegrationByType(IKeyboardActivityIntegration.class);
		if(keyboardIntegration == null) {
			TokTales.getLog().e(TAG, "No integration for IKeyboardIntegration");
		}
		else {
			keyboardIntegration.showKeyboard(mTextView);
		}
	}
	
	
	@Override
	public void openContextMenu() {
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				openContextMenu(mRenderView);				
			}
		});
	}
	
	
	
	private class TextViewOnKeyListener implements OnKeyListener {

		@Override
		public boolean onKey(View v, int keyCode, KeyEvent event) {
			TokTales.getLog().d(TAG, "Hardware input from key: " +keyCode);
			return false;
		}
		
	}

	
}
