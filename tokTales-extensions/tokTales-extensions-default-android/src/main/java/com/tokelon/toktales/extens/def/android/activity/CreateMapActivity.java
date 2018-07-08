package com.tokelon.toktales.extens.def.android.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.tokelon.toktales.android.R;
import com.tokelon.toktales.core.engine.TokTales;

public class CreateMapActivity extends Activity {
	
	public static final String ACTIVITY_RESULT_MAP_NAME = "ACTIVITY_RESULT_MAP_NAME";
	public static final String ACTIVITY_RESULT_MAP_WIDTH = "ACTIVITY_RESULT_MAP_WIDTH";
	public static final String ACTIVITY_RESULT_MAP_HEIGHT = "ACTIVITY_RESULT_MAP_HEIGHT";
	
	private Button buttonCreate;
	private EditText editTextName;
	private EditText editTextWidth;
	private EditText editTextHeight;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
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
				TokTales.getLog().d("Invalid name given");
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
				TokTales.getLog().d("Invalid size value given");
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
