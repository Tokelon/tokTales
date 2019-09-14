package com.tokelon.toktales.extens.def.android.activity;

import java.util.HashMap;
import java.util.Map;

import com.tokelon.toktales.android.activity.AbstractIntegratedActivity;
import com.tokelon.toktales.android.activity.integration.IActivityIntegration;
import com.tokelon.toktales.android.activity.integration.SimpleRequestPermissionsIntegration;
import com.tokelon.toktales.core.engine.TokTales;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.storage.IStorageService;
import com.tokelon.toktales.core.engine.storage.StorageException;
import com.tokelon.toktales.core.storage.IApplicationLocation;
import com.tokelon.toktales.core.storage.utils.LocationImpl;
import com.tokelon.toktales.core.storage.utils.MutablePathImpl;
import com.tokelon.toktales.extens.def.android.R;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class LoadTaleActivity extends AbstractIntegratedActivity {
	// TODO: Refactor to use DI

	public static final String ACTIVITY_INTEGRATION_REQUEST_PERMISSIONS = "LoadTaleActivity_Integration_RequestPermissions";
	
	public static final String ACTIVITY_RESULT_TALE_DIR_APP_PATH = "ACTIVITY_RESULT_TALE_DIR_APP_PATH";

	private static final String TALES_LOCATION_PATH = "Tales";	// TODO: Static location! Implement with configs
	private static final IApplicationLocation talesLocation = new LocationImpl(TALES_LOCATION_PATH);
	
	
	private ILogger logger;
	
	private ListView taleListView;
	private String[] taleList;
	
	private SimpleRequestPermissionsIntegration requestPermissionsIntegration;
	
	@Override
	protected Map<String, IActivityIntegration> createActivityIntegrations() {
		Map<String, IActivityIntegration> integrations = new HashMap<>(); // do not use default integrations returned by superclass
		
		requestPermissionsIntegration = new SimpleRequestPermissionsIntegration(TokTales.getLogging(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
		integrations.put(ACTIVITY_INTEGRATION_REQUEST_PERMISSIONS, requestPermissionsIntegration);
		
		return integrations;
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		logger = TokTales.getLogging().getLogger(getClass());

		init();
		
		setContentView(taleListView);
	}
	
	
	private void init() {
		taleListView = new ListView(this);
		
		// TODO: Implement listing tales only after permission has been granted
		IStorageService storageService = TokTales.getEngine().getStorageService();
		try {
			taleList = storageService.listAppDirOnExternal(talesLocation);
			
			
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.entry_list, R.id.textViewListEntry, taleList);
			taleListView.setAdapter(adapter);
			taleListView.setOnItemClickListener(new ListClickListener());
			
		} catch (StorageException se) {
			logger.error("List directory Tales location failed:", se);
		}
		
	}
	
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		
		if(requestPermissionsIntegration != null) {
			requestPermissionsIntegration.onActivityRequestPermissionsResult(this, requestCode, permissions, grantResults);
		}
	}
	
	
	private class ListClickListener implements OnItemClickListener {
		
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			logger.debug("Tale selected: {}", position);
			
			
			String taleDirName = taleList[position];
			
			String taleDirAppPath = new MutablePathImpl(talesLocation.getLocationPath()).getPathAppendedBy(taleDirName);
			
			
			Intent returnIntent = new Intent();
			returnIntent.putExtra(ACTIVITY_RESULT_TALE_DIR_APP_PATH, taleDirAppPath);
			setResult(RESULT_OK, returnIntent);
			
			finish();
		}
	}
	
}
