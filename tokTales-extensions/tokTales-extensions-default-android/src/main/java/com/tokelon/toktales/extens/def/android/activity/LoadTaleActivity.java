package com.tokelon.toktales.extens.def.android.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.tokelon.toktales.android.R;
import com.tokelon.toktales.android.activity.AbstractIntegratedActivity;
import com.tokelon.toktales.android.activity.integration.IActivityIntegration;
import com.tokelon.toktales.android.activity.integration.SimpleRequestPermissionsIntegration;
import com.tokelon.toktales.core.engine.TokTales;
import com.tokelon.toktales.core.engine.storage.IStorageService;
import com.tokelon.toktales.core.engine.storage.StorageException;
import com.tokelon.toktales.core.storage.IApplicationLocation;
import com.tokelon.toktales.core.storage.utils.LocationImpl;
import com.tokelon.toktales.core.storage.utils.MutablePathImpl;

import java.util.HashMap;
import java.util.Map;

public class LoadTaleActivity extends AbstractIntegratedActivity {

	public static final String TAG = "LoadTaleActivity";
	
	public static final String ACTIVITY_INTEGRATION_REQUEST_PERMISSIONS = "LoadTaleActivity_Integration_RequestPermissions";
	
	public static final String ACTIVITY_RESULT_TALE_DIR_APP_PATH = "ACTIVITY_RESULT_TALE_DIR_APP_PATH";

	private static final String TALES_LOCATION_PATH = "Tales";	// TODO: Static location! Implement with configs
	private static final IApplicationLocation talesLocation = new LocationImpl(TALES_LOCATION_PATH);
	
	
	private ListView taleListView;
	private String[] taleList;
	
	private SimpleRequestPermissionsIntegration requestPermissionsIntegration;
	
	@Override
	protected Map<String, IActivityIntegration> createActivityIntegrations() {
		Map<String, IActivityIntegration> integrations = new HashMap<>(); // do not use default integrations returned by superclass
		
		requestPermissionsIntegration = new SimpleRequestPermissionsIntegration(TokTales.getLog(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
		integrations.put(ACTIVITY_INTEGRATION_REQUEST_PERMISSIONS, requestPermissionsIntegration);
		
		return integrations;
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
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
			TokTales.getLog().e(TAG, "List directory Tales location failed: " +se.getMessage());
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
			TokTales.getLog().d(TAG, "Tale selected: " +position);
			
			
			String taleDirName = taleList[position];
			
			String taleDirAppPath = new MutablePathImpl(talesLocation.getLocationPath()).getPathAppendedBy(taleDirName);
			
			
			Intent returnIntent = new Intent();
			returnIntent.putExtra(ACTIVITY_RESULT_TALE_DIR_APP_PATH, taleDirAppPath);
			setResult(RESULT_OK, returnIntent);
			
			finish();
		}
	}
	
}
