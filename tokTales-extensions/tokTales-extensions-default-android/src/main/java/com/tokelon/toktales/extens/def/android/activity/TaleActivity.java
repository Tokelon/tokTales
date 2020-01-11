package com.tokelon.toktales.extens.def.android.activity;

import javax.inject.Inject;

import com.tokelon.toktales.android.activity.ActivityHelper;
import com.tokelon.toktales.android.activity.IConsoleActivity;
import com.tokelon.toktales.android.activity.IDebugActivity;
import com.tokelon.toktales.android.activity.ProxyTextWatcher;
import com.tokelon.toktales.android.activity.SettingsActivity;
import com.tokelon.toktales.android.activity.integration.IKeyboardActivityIntegration;
import com.tokelon.toktales.android.activity.integration.ISimpleRequestPermissionsIntegration;
import com.tokelon.toktales.android.activity.integration.ISimpleRequestPermissionsIntegration.ISimpleRequestPermissionsIntegrationFactory;
import com.tokelon.toktales.android.activity.integration.ISurfaceViewIntegration;
import com.tokelon.toktales.android.activity.integration.engine.ISingleActivityEngineIntegration;
import com.tokelon.toktales.android.render.opengl.RenderGLSurfaceView;
import com.tokelon.toktales.core.config.IConfigManager;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.extens.def.android.R;
import com.tokelon.toktales.extens.def.core.tale.ITaleLoader;
import com.tokelon.toktales.extens.def.core.tale.TaleException;
import com.tokelon.toktales.extens.def.core.values.GameStateExtensionsValues;
import com.tokelon.toktales.tools.android.activity.integration.AbstractIntegratedActivity;
import com.tokelon.toktales.tools.android.activity.integration.IActivityIntegrator;
import com.tokelon.toktales.tools.android.activity.integration.IActivityIntegratorBuilder;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.opengl.GLSurfaceView;
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


	public static final String ACTIVITY_INTENT_DATA_TALE_DIR_APP_PATH = "ACTIVITY_INTENT_DATA_TALE_DIR_APP_PATH";
	
	public static final String ACTIVITY_INTEGRATION_SURFACE_VIEW = "TaleActivity_Integration_SurfaceView";
	public static final String ACTIVITY_INTEGRATION_SINGLE_ACTIVITY_ENGINE = "TaleActivity_Integration_SingleActivityEngine";
	public static final String ACTIVITY_INTEGRATION_REQUEST_PERMISSIONS = "TaleActivity_Integration_RequestPermissions";
	
	
	private AlertDialog.Builder backDialogBuilder;

	private LinearLayout rootView;
	private View textViewBar;
	private RenderGLSurfaceView renderGLView;
	
	private EditText textView;
	
	private ProxyTextWatcher textViewProxyTextWatcher;

	

	private ILogger logger;
	private IEngineContext engineContext;
	private ITaleLoader taleLoader;
	private ISurfaceViewIntegration surfaceViewIntegration;
	private ISingleActivityEngineIntegration singleActivityEngineIntegration;
	private ISimpleRequestPermissionsIntegration requestPermissionsIntegration;

	
	public TaleActivity() {
		super(ActivityHelper.createDefaultActivityIntegratorBuilder());
	}
	
	public TaleActivity(IActivityIntegratorBuilder integratorBuilder) {
		super(integratorBuilder);
	}

	@Inject
	protected void injectDependencies(IEngineContext engineContext, ITaleLoader taleLoader, ISurfaceViewIntegration surfaceViewIntegration, ISingleActivityEngineIntegration singleActivityEngineIntegration, ISimpleRequestPermissionsIntegrationFactory requestPermissionsIntegrationFactory) {
		this.logger = engineContext.getLogging().getLogger(getClass());
		this.engineContext = engineContext;
		this.taleLoader = taleLoader;
		this.surfaceViewIntegration = surfaceViewIntegration;
		this.singleActivityEngineIntegration = singleActivityEngineIntegration;
		this.requestPermissionsIntegration = requestPermissionsIntegrationFactory.create(Manifest.permission.WRITE_EXTERNAL_STORAGE);
	}
	
	@Override
	protected IActivityIntegrator buildIntegrator(IActivityIntegratorBuilder builder) {
		builder.addIntegration(ACTIVITY_INTEGRATION_SURFACE_VIEW, surfaceViewIntegration);
		builder.addIntegration(ACTIVITY_INTEGRATION_SINGLE_ACTIVITY_ENGINE, singleActivityEngineIntegration);
		builder.addIntegration(ACTIVITY_INTEGRATION_REQUEST_PERMISSIONS, requestPermissionsIntegration);
		
		return super.buildIntegrator(builder);
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// Inject dependencies - before integrator is built
		ActivityHelper.injectActivityDependencies(this);
		
		super.onCreate(savedInstanceState);
		
		
		// Set activity to fullscreen
		setFullscreen();

		// Init 'back' dialog
		initDialog();
		
		// Load the layout and set the GL Surface View
		initContentGL();
		

		
		String taleAppPath = getIntent().getStringExtra(ACTIVITY_INTENT_DATA_TALE_DIR_APP_PATH);
		if(taleAppPath == null) {
			logger.error("Intent is missing data");
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
		IConfigManager configManager = (IConfigManager) engineContext.getGame().getConfigManager();
		configManager.getConfig(IConfigManager.MAIN_CONFIG).removeAllListeners();

		
		
		String sceneName = taleApplicationPath;
		String stateName = GameStateExtensionsValues.STATE_LOCAL_MAP;
		try {
			taleLoader.loadTaleIntoGame(taleApplicationPath, sceneName, stateName);
			engineContext.getGame().getStateControl().changeState(stateName);
		} catch (TaleException e) {
			logger.error("Loading tale failed:", e);
		}
		
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
		renderGLView.setDebugFlags(GLSurfaceView.DEBUG_CHECK_GL_ERROR | GLSurfaceView.DEBUG_LOG_GL_CALLS); // TODO: Important - OpenGL Debug Flags

		
		// This text view is hidden but we need it to display our context menu
		textViewBar = rootView.findViewById(R.id.textViewGLGameBar);
		registerForContextMenu(textViewBar);

		textView = new EditText(this);
		rootView.addView(textView, 0, 0);
		
		
		// Set the content view
		setContentView(rootView);
		

		// Use with OpenGL renderer
		surfaceViewIntegration.integrateRenderView(renderGLView);
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
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		
		if(requestPermissionsIntegration != null) {
			requestPermissionsIntegration.onActivityRequestPermissionsResult(this, requestCode, permissions, grantResults);
		}
	}
	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();

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
			logger.error("No integration for IKeyboardIntegration");
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
		logger.info("TaleActivity configuration changed");
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
