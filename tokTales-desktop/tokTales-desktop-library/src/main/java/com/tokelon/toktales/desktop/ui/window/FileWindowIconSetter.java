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

/** Implementation of {@link IWindowIconSetter} that uses an icon that is loaded from a file path into a bitmap.
 */
public class FileWindowIconSetter implements IWindowIconSetter {


	private final ILogger logger;
	private final IBitmapAssetManager bitmapAssetManager;
	private final String iconPath;

	/** Constructor with logging, a bitmap asset manager and an icon path.
	 *
	 * @param logging
	 * @param bitmapAssetManager
	 * @param iconPath Must point to an image file.
	 */
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
