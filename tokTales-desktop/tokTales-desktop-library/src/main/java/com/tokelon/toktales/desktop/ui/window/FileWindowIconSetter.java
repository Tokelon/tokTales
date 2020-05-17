package com.tokelon.toktales.desktop.ui.window;

import java.io.File;

import com.tokelon.toktales.core.content.graphics.IBitmap;
import com.tokelon.toktales.core.content.manage.bitmap.IBitmapAsset;
import com.tokelon.toktales.core.content.manage.bitmap.IBitmapAssetManager;
import com.tokelon.toktales.core.content.manage.bitmap.ReadDelegateBitmapAssetKey;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.log.ILogging;
import com.tokelon.toktales.tools.assets.exception.AssetException;
import com.tokelon.toktales.tools.assets.files.FileKey;

public class FileWindowIconSetter implements IWindowIconSetter {


	private final ILogger logger;
	private final IBitmapAssetManager bitmapAssetManager;
	private final String iconPath;

	public FileWindowIconSetter(ILogging logging, IBitmapAssetManager bitmapAssetManager, String iconPath) {
		this.logger = logging.getLogger(getClass());
		this.bitmapAssetManager = bitmapAssetManager;
		this.iconPath = iconPath;
	}


	@Override
	public void set(IWindow window, IWindowToolkit windowToolkit) {
		ReadDelegateBitmapAssetKey iconBitmapKey = new ReadDelegateBitmapAssetKey(new FileKey(new File(iconPath)));

		try(IBitmapAsset iconBitmapAsset = bitmapAssetManager.getLoader().load(iconBitmapKey)) {
			IBitmap iconBitmap = iconBitmapAsset.getBitmap();
			window.setWindowIcon(iconBitmap.getWidth(), iconBitmap.getHeight(), iconBitmap.getData());

			logger.debug("Loaded window icon from [{}]", iconPath);
		}
		catch(AssetException e) {
			logger.warn("Could not load window icon from [{}]. Enable debug logging for more information.", iconPath);
			logger.debug("Failed to load window icon:", e);
		}
	}

}
