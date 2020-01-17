package com.tokelon.toktales.extens.def.android.activity;

import javax.inject.Inject;

import com.tokelon.toktales.android.activity.ActivityHelper;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.log.ILogging;
import com.tokelon.toktales.core.engine.storage.IStorageService;
import com.tokelon.toktales.core.engine.storage.StorageException;
import com.tokelon.toktales.core.values.LocationsAndPlaces;
import com.tokelon.toktales.extens.def.android.R;
import com.tokelon.toktales.tools.android.activity.integration.AbstractIntegratedActivity;

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
	private IStorageService storageService;
	
	
	public LoadMapActivity() {
		super(ActivityHelper.createDefaultActivityIntegratorBuilder());
	}
	
	@Inject
	protected void injectDependencies(ILogging logging, IStorageService storageService) {
		this.logger = logging.getLogger(getClass());
		this.storageService = storageService;
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		ActivityHelper.injectActivityDependencies(this);

		super.onCreate(savedInstanceState);
		
		init();

		setContentView(mapList);
	}
	
	
	private void init() {
		mapList = new ListView(this);
		
		try {
			maps = storageService.listAppDirOnExternal(LocationsAndPlaces.LOCATION_EXTERNAL_MAPS);
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
