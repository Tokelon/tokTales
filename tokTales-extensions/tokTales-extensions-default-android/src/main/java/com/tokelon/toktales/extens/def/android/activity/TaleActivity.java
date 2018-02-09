package com.tokelon.toktales.extens.def.android.activity;

import com.tokelon.toktales.android.R;
import com.tokelon.toktales.android.activity.AbstractIntegratedActivity;
import com.tokelon.toktales.android.activity.IConsoleActivity;
import com.tokelon.toktales.android.activity.IDebugActivity;
import com.tokelon.toktales.android.activity.ProxyTextWatcher;
import com.tokelon.toktales.android.activity.SettingsActivity;
import com.tokelon.toktales.android.activity.integration.IKeyboardActivityIntegration;
import com.tokelon.toktales.android.logic.process.GLRenderingProcess;
import com.tokelon.toktales.android.render.opengl.RenderGLSurfaceView;
import com.tokelon.toktales.core.config.IConfigManager;
import com.tokelon.toktales.core.engine.TokTales;
import com.tokelon.toktales.core.logic.process.GameProcess;
import com.tokelon.toktales.extens.def.android.logic.process.AndroidTaleProcess;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;

public class TaleActivity extends AbstractIntegratedActivity implements IConsoleActivity, IDebugActivity {

	public static final String TAG = "TaleActivity";
	
	
	public static final String ACTIVITY_INTENT_DATA_TALE_DIR_APP_PATH = "ACTIVITY_INTENT_DATA_TALE_DIR_APP_PATH";
	
	
	
	private AlertDialog.Builder backDialogBuilder;

	private LinearLayout rootView;
	private View textViewBar;
	private RenderGLSurfaceView renderGLView;
	
	private EditText textView;
	
	private ProxyTextWatcher textViewProxyTextWatcher;

	
	private GLRenderingProcess renderingProcess;

	private AndroidTaleProcess taleProcess;
	
	
	private boolean errorLoadingActivity = false;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		renderingProcess = new GLRenderingProcess(TokTales.getGame(), TokTales.getEngine());
		
		// Set activity to fullscreen
		setFullscreen();

		// Init 'back' dialog
		initDialog();
		
		// Load the layout and set the GL Surface View
		initContentGL();
		

		
		String taleAppPath = getIntent().getStringExtra(ACTIVITY_INTENT_DATA_TALE_DIR_APP_PATH);
		if(taleAppPath == null) {
			TokTales.getLog().e(TAG, "Intend is missing data");
			
			errorLoadingActivity = true;
			finish();
		}
		else {
			// Initialize the game etc.
			initLogic(taleAppPath);
		}
	}
	
	
	
	private void initLogic(String taleApplicationPath) {
		
		textView.addTextChangedListener(textViewProxyTextWatcher = new ProxyTextWatcher());

		
		// TODO: What needs to be done here with configs and preferences?

		
		/* TODO: 096 Do this somehow else
		 * 
		 * Since main config changes so regularly it should be in Game
		 * 
		 */
		// Removes any old listeners for the main config
		IConfigManager configManager = (IConfigManager) TokTales.getGame().getConfigManager();
		configManager.getConfig(IConfigManager.MAIN_CONFIG).removeAllListeners();
		
		

		// Use with OpenGL renderer
		renderingProcess.setObjRenderView(renderGLView);
		renderingProcess.startProcess();
		
		
		
		// Game / Tale process
		GameProcess gameProcess = new GameProcess(TokTales.getContext());
		
		taleProcess = new AndroidTaleProcess(TokTales.getContext(), gameProcess);

		taleProcess.setObjTaleAppPath(taleApplicationPath);
		
		// WORKAROUND
		TokTales.getGame().getGameControl().pauseGame();
		TokTales.getGame().getGameControl().stopGame();
		
		taleProcess.startProcess();


	}
	
	

	private void initContentGL() {
		
		// The resources we are going to use
		int glLayoutId = R.layout.activity_game_gl_surface;
		int glSurfaceId = R.id.glSurfaceRenderViewGLGame;
		
		
		// Inflate the root view
		LayoutInflater layoutInflater = LayoutInflater.from(this);
		rootView = (LinearLayout) layoutInflater.inflate(glLayoutId, null);
		
		
		// Our rendering view
		renderGLView = (RenderGLSurfaceView) rootView.findViewById(glSurfaceId);
		
		
		// This text view is hidden but we need it to display our context menu
		textViewBar = rootView.findViewById(R.id.textViewGLGameBar);
		registerForContextMenu(textViewBar);

		textView = new EditText(this);
		rootView.addView(textView, 0, 0);
		
		
		// Set the content view
		setContentView(rootView);
	}
	

	
	private void initDialog() {
		// Initialize the exit dialog builder
		
		backDialogBuilder = new AlertDialog.Builder(this);
		backDialogBuilder.setTitle(getResources().getText(R.string.dialog_title_exit))
		.setPositiveButton(R.string.dialog_button_exit_positive, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				TaleActivity.this.superOnBackPressed();
			}
		})
		.setNegativeButton(R.string.dialog_button_exit_negative, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// Nothing
			}
		});
	}
	
	
	
	
	@Override
	protected void onResume() {
		super.onResume();
		
		if(!errorLoadingActivity) {

			renderingProcess.unpause();
			taleProcess.unpause();	
		}
	}


	@Override
	protected void onPause() {
		super.onPause();
		
		if(!errorLoadingActivity) {
			taleProcess.pause();
			renderingProcess.pause();
		}
	}
	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		if(!errorLoadingActivity) {
			taleProcess.endProcess();
			renderingProcess.endProcess();
		}

		// Make sure application is terminated completely
		Process.killProcess(Process.myPid());
	}

	

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemID = item.getItemId();
		
		if(itemID == R.id.itemMenuTaleOrientationPortrait) {
			
			if(getResources().getConfiguration().orientation != Configuration.ORIENTATION_PORTRAIT) {
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);	
			}
			
			return true;
		}
		else if(itemID == R.id.itemMenuTaleOrientationLandscape) {
			
			if(getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);	
			}
			
			return true;
		}
		else if(itemID == R.id.itemMenuTaleSettings) {
			
			Intent prefsIntent = new Intent(this, SettingsActivity.class);
			startActivity(prefsIntent);
			return true;
		}
		else {
			return super.onOptionsItemSelected(item);
		}
		
	}
	
	
	
	/* IDebugActivity method */
	
	@Override
	public void openContextMenu() {
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				openContextMenu(textViewBar);				
			}
		});
	}
	
	
	/* IConsoleActivity method */

	@Override
	public void getConsoleInput(TextWatcher consoleInputTextWatcher) {
		textViewProxyTextWatcher.setClient(consoleInputTextWatcher);
		
		IKeyboardActivityIntegration keyboardIntegration = getIntegrator().getIntegrationByType(IKeyboardActivityIntegration.class);
		if(keyboardIntegration == null) {
			TokTales.getLog().e(TAG, "No integration for IKeyboardIntegration");
		}
		else {
			keyboardIntegration.showKeyboard(textView);	
		}
	}
	

	
	/* Other activity methods */
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		
		
		// When asked for a context menu, we are actually going to inflate our options menu
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.tale_menu, menu);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		
		// And then pass any actions to the options menu
		return this.onOptionsItemSelected(item);
	}
	
	

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		
		// Stop Activity from recreating
		TokTales.getLog().i(TAG, "TaleActivity configuration changed");
	}
	

	
	@Override
	public void onBackPressed() {
		// Show a confirmation dialog instead of exiting immediately
		backDialogBuilder.create().show();
	}

	private void superOnBackPressed() {
		// Actually exit activity
		super.onBackPressed();
	}
	
	
	
	private void setFullscreen() {
		// Remove title from the activity window
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		// Set the activity to fullscreen and no sleep
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}
	
	
	@SuppressLint("NewApi") @Override
	public void onWindowFocusChanged(boolean hasFocus) { 
		super.onWindowFocusChanged(hasFocus);

		if (hasFocus) {
	
			// Puts the activity window into 'immersive fullscreen', if supported
			if (Build.VERSION.SDK_INT > 16) {
				rootView.setSystemUiVisibility(
						View.SYSTEM_UI_FLAG_LAYOUT_STABLE
						| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
						| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
						| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
						| View.SYSTEM_UI_FLAG_FULLSCREEN
						| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
			}
		}
	}


	
}
