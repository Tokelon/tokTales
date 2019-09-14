package com.tokelon.toktales.extens.def.android.activity;

import com.tokelon.toktales.android.activity.AbstractIntegratedActivity;
import com.tokelon.toktales.core.engine.TokTales;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.storage.StorageException;
import com.tokelon.toktales.core.values.LocationsAndPlaces;
import com.tokelon.toktales.extens.def.android.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class LoadMapActivity extends AbstractIntegratedActivity {


	public static final String ACTIVITY_RESULT_MAP_FILE_NAME = "ACTIVITY_RESULT_MAP_FILE_NAME";


	private ListView mapList;
	private String[] maps;
	
	private ILogger logger;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		logger = TokTales.getLogging().getLogger(getClass());
		
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
			logger.error("Failed to list map files", e);
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
			logger.debug("Map selected: {}", position);
			
			final String mapFileName = maps[position];


			Intent returnIntent = new Intent();
			returnIntent.putExtra(ACTIVITY_RESULT_MAP_FILE_NAME, mapFileName);
			setResult(RESULT_OK, returnIntent);
			
			finish();
		}
		
	}
	
}
