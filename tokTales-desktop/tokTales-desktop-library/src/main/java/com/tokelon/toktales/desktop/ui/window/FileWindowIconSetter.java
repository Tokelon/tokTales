package com.tokelon.toktales.desktop.ui.window;

import java.io.File;

import com.tokelon.toktales.core.content.graphics.IBitmap;
import com.tokelon.toktales.core.content.manage.bitmap.IBitmapAsset;
import com.tokelon.toktales.core.content.manage.bitmap.IBitmapAssetManager;
import com.tokelon.toktales.core.content.manage.bitmap.ReadDelegateBitmapAssetKey;
import com.tokelon.toktales.tools.assets.files.FileKey;

public class FileWindowIconSetter implements IWindowIconSetter {


	private final IBitmapAssetManager bitmapAssetManager;
	private final String iconPath;

	public FileWindowIconSetter(IBitmapAssetManager bitmapAssetManager, String iconPath) {
		this.bitmapAssetManager = bitmapAssetManager;
		this.iconPath = iconPath;
	}


	@Override
	public void set(IWindow window, IWindowToolkit windowToolkit) {
		ReadDelegateBitmapAssetKey iconBitmapKey = new ReadDelegateBitmapAssetKey(new FileKey(new File(iconPath)));
		IBitmapAsset iconBitmapAsset = bitmapAssetManager.getAssetLoadIfNeeded(iconBitmapKey); // Use load and dispose after setting?

		if(bitmapAssetManager.isAssetValid(iconBitmapAsset)) {
			IBitmap iconBitmap = iconBitmapAsset.getBitmap();
			window.setWindowIcon(iconBitmap.getWidth(), iconBitmap.getHeight(), iconBitmap.getData());
		}
	}

}
