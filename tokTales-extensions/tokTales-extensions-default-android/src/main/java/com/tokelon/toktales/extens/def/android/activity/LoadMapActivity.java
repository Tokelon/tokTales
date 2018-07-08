package com.tokelon.toktales.extens.def.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.tokelon.toktales.android.R;
import com.tokelon.toktales.android.activity.AbstractIntegratedActivity;
import com.tokelon.toktales.core.engine.TokTales;
import com.tokelon.toktales.core.engine.storage.StorageException;
import com.tokelon.toktales.core.values.LocationsAndPlaces;

public class LoadMapActivity extends AbstractIntegratedActivity {
	//StartGameActivity
	
	public static final String ACTIVITY_RESULT_MAP_FILE_NAME = "ACTIVITY_RESULT_MAP_FILE_NAME";


	private static final String TAG = "StartGameActivity";
	
	private ListView mapList;
	private String[] maps;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		init();

		setContentView(mapList);
	}
	
	
	private void init() {
		mapList = new ListView(this);
		
		try {
			maps = TokTales.getEngine().getStorageService().listAppDirOnExternal(LocationsAndPlaces.LOCATION_EXTERNAL_MAPS);
			if(maps != null) {
				initList(maps);
			}
		}
		catch (StorageException e) {
			TokTales.getLog().e(TAG, e.getMessage());
		}
		
	}
	
	
	private void initList(String[] list) {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.entry_list, R.id.textViewListEntry, list);
		
		mapList.setAdapter(adapter);
		mapList.setOnItemClickListener(new MapClickListener());
	}
	
	
	private class MapClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Log.d(TAG, "Map selected: " +position);
			
			final String mapFileName = maps[position];


			Intent returnIntent = new Intent();
			returnIntent.putExtra(ACTIVITY_RESULT_MAP_FILE_NAME, mapFileName);
			setResult(RESULT_OK, returnIntent);
			
			finish();
		}
		
	}
	
}
