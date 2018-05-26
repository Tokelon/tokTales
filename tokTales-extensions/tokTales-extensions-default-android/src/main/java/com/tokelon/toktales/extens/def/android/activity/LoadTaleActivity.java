package com.tokelon.toktales.extens.def.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.tokelon.toktales.android.R;
import com.tokelon.toktales.android.activity.AbstractIntegratedActivity;
import com.tokelon.toktales.core.engine.TokTales;
import com.tokelon.toktales.core.engine.storage.IStorageService;
import com.tokelon.toktales.core.engine.storage.StorageException;
import com.tokelon.toktales.core.storage.IApplicationLocation;
import com.tokelon.toktales.core.storage.utils.LocationImpl;
import com.tokelon.toktales.core.storage.utils.MutablePathImpl;

public class LoadTaleActivity extends AbstractIntegratedActivity {

	public static final String TAG = "LoadTaleActivity";
	
	
	public static final String ACTIVITY_RESULT_TALE_DIR_APP_PATH = "ACTIVITY_RESULT_TALE_DIR_APP_PATH";

	private static final String TALES_LOCATION_PATH = "Tales";	// TODO: Static location! Implement with configs
	private static final IApplicationLocation talesLocation = new LocationImpl(TALES_LOCATION_PATH);
	
	
	private ListView taleListView;
	private String[] taleList;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		init();
		
		setContentView(taleListView);
	}
	
	
	private void init() {
		taleListView = new ListView(this);
		
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
