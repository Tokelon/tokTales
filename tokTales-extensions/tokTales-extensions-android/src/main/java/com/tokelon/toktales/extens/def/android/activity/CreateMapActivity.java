package com.tokelon.toktales.extens.def.android.activity;

import javax.inject.Inject;

import com.tokelon.toktales.android.activity.ActivityHelper;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.log.ILogging;
import com.tokelon.toktales.extens.def.android.R;
import com.tokelon.toktales.tools.android.activity.integration.AbstractIntegratedActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class CreateMapActivity extends AbstractIntegratedActivity {


	public static final String ACTIVITY_RESULT_MAP_NAME = "ACTIVITY_RESULT_MAP_NAME";
	public static final String ACTIVITY_RESULT_MAP_WIDTH = "ACTIVITY_RESULT_MAP_WIDTH";
	public static final String ACTIVITY_RESULT_MAP_HEIGHT = "ACTIVITY_RESULT_MAP_HEIGHT";
	
	private Button buttonCreate;
	private EditText editTextName;
	private EditText editTextWidth;
	private EditText editTextHeight;

	private ILogger logger;
	
	
	public CreateMapActivity() {
		super(ActivityHelper.createDefaultActivityIntegratorBuilder());
	}
	
	@Inject
	protected void injectDependencies(ILogging logging) {
		this.logger = logging.getLogger(getClass());
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		ActivityHelper.injectActivityDependencies(this);

		super.onCreate(savedInstanceState);
		
		
		LayoutInflater layoutInflater = LayoutInflater.from(this);
		View rootView = layoutInflater.inflate(R.layout.activity_create_map, null);
		
		init(rootView);
		
		
		setContentView(rootView);
	}
	
	private void init(View rootView) {
		buttonCreate = (Button) rootView.findViewById(R.id.buttonCreateMapCreate);
		buttonCreate.setOnClickListener(new CreateButtonListener());

		editTextName = (EditText) rootView.findViewById(R.id.editTextCreateMapNameValue);
		editTextWidth = (EditText) rootView.findViewById(R.id.editTextCreateMapWidthValue);
		editTextHeight = (EditText) rootView.findViewById(R.id.editTextCreateMapHeightValue);
	}
	
	
	
	private class CreateButtonListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			String nameVal = editTextName.getText().toString();
			if(nameVal.trim().equals("")) {
				logger.debug("Invalid name given");
				return;
			}
			
			
			String widthVal = editTextWidth.getText().toString();
			String heightVal = editTextHeight.getText().toString();
			
			int width;
			int height;
			try {
				width = Integer.parseInt(widthVal);
				height = Integer.parseInt(heightVal);
			} catch(NumberFormatException nfe) {
				logger.debug("Invalid size value given");
				return;
			}
			
			
			Intent returnIntent = new Intent();
			returnIntent.putExtra(ACTIVITY_RESULT_MAP_NAME, nameVal);
			returnIntent.putExtra(ACTIVITY_RESULT_MAP_WIDTH, width);
			returnIntent.putExtra(ACTIVITY_RESULT_MAP_HEIGHT, height);
			setResult(RESULT_OK, returnIntent);
			
			finish();
		}
	}
	
}
