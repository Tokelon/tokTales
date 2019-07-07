package com.tokelon.toktales.desktop.content;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Map;

import javax.inject.Inject;

import org.lwjgl.system.MemoryUtil;

import com.tokelon.toktales.core.content.graphics.IBitmap;
import com.tokelon.toktales.core.engine.content.AbstractContentService;
import com.tokelon.toktales.core.engine.content.ContentException;
import com.tokelon.toktales.core.engine.content.IContentService;
import com.tokelon.toktales.core.engine.inject.annotation.services.ContentServiceExtensions;
import com.tokelon.toktales.core.engine.inject.annotation.services.StorageServiceExtensions;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.storage.IStorageService;
import com.tokelon.toktales.core.engine.storage.IStorageService.IStorageServiceFactory;
import com.tokelon.toktales.core.engine.storage.StorageException;
import com.tokelon.toktales.core.game.model.IRectangle2i;
import com.tokelon.toktales.core.resources.IListing;
import com.tokelon.toktales.core.storage.IApplicationLocation;
import com.tokelon.toktales.desktop.engine.inject.annotation.AssetRoot;
import com.tokelon.toktales.desktop.lwjgl.data.ISTBBitmap;
import com.tokelon.toktales.desktop.lwjgl.data.STBBitmap;

public class DesktopContentService extends AbstractContentService implements IContentService {
	
	public static final String TAG = "DesktopContentService";
	
	
	private final IStorageService assetStorageService;

	public DesktopContentService(ILogger logger, IStorageServiceFactory storageServiceFactory, @AssetRoot String assetRoot) {
		this.assetStorageService = storageServiceFactory.create(assetRoot);
	}
	
	@Inject
	public DesktopContentService(ILogger logger, IStorageServiceFactory storageServiceFactory, @AssetRoot String assetRoot, @ContentServiceExtensions Map<String, IServiceExtension> contentExtensions, @StorageServiceExtensions Map<String, IServiceExtension> storageExtensions) {
		super(contentExtensions);
		
		this.assetStorageService = storageServiceFactory.create(assetRoot, storageExtensions);
	}
	
	
	@Override
	public String[] listAssetDir(IApplicationLocation location) throws ContentException {
		try {
			return assetStorageService.listAppDirOnExternal(location);
		} catch (StorageException e) {
			throw new ContentException(e);
		}
	}

	@Override
	public IListing createAssetListing(IApplicationLocation location) throws ContentException {
		try {
			return assetStorageService.createFileListing(location);
		} catch (StorageException e) {
			throw new ContentException(e);
		}
	}
	
	
	@Override
	public InputStream readAppFileOnAssets(IApplicationLocation location, String fileName) throws ContentException {
		try {
			return assetStorageService.readAppFileOnExternal(location, fileName);
		} catch (StorageException e) {
			throw new ContentException(e);
		}
	}
	
	@Override
	public InputStream tryReadAppFileOnAssets(IApplicationLocation location, String fileName) {
		return assetStorageService.tryReadAppFileOnExternal(location, fileName);
	}
	

	
	@Override
	public IBitmap cropBitmap(IBitmap bitmap, IRectangle2i bounds) {
		IBitmap result;
		if(bitmap instanceof ISTBBitmap) {
			ISTBBitmap stbBitmap = (ISTBBitmap) bitmap;
			result = cropSTBBitmap(stbBitmap, bounds);
		}
		else {
			result = super.cropBitmap(bitmap, bounds);
		}
		
		return result;
	}
	
	private static IBitmap cropSTBBitmap(ISTBBitmap bitmap, IRectangle2i bounds) {
		int channels = bitmap.getChannels();
		
		ByteBuffer cropBuffer = MemoryUtil.memAlloc(channels * bounds.width() * bounds.height());
		
		byte[] arrayBuffer = new byte[channels * bounds.width()];
		for(int i = 0; i < bounds.height(); i++) {
			
			int srcPos = (channels * (i + bounds.top()) * bitmap.getWidth()) + 4 * bounds.left();
			bitmap.getData().position(srcPos);
			bitmap.getData().get(arrayBuffer);
			
			//int dstPos = (4 * i * source.getWidth());
			//cropBuffer.position()
			cropBuffer.put(arrayBuffer);
		}
		cropBuffer.position(0);
		bitmap.getData().position(0);
		

		STBBitmap texImage = new STBBitmap(cropBuffer, bounds.width(), bounds.height(), channels, bitmap.getSourceChannels(), (image) -> MemoryUtil.memFree(cropBuffer));
		return texImage;
	}

	/*
	public static TextureImage cropTexture(TextureImage source, TRectangle bounds) {
		ByteBuffer cropBuffer = BufferUtils.createByteBuffer(4 * bounds.width() * bounds.height());
		
		TokTales.getLog().d("size: " +source.getWidth() + ", " +source.getHeight());
		TokTales.getLog().d("limit: " +source.getData().limit());
		
		int result = STBImageResize.stbir_resize_region(
		//int result = STBImageResize.stbir_resize_subpixel(
				source.getData(), source.getWidth(), source.getHeight(), 0,
				cropBuffer, bounds.width(), bounds.height(), 0,
				STBImageResize.STBIR_TYPE_UINT8, 4, 3, 0,
				STBImageResize.STBIR_EDGE_CLAMP, STBImageResize.STBIR_EDGE_CLAMP, STBImageResize.STBIR_FILTER_DEFAULT, STBImageResize.STBIR_FILTER_DEFAULT, STBImageResize.STBIR_COLORSPACE_LINEAR,
				bounds.left, bounds.top, bounds.right, bounds.bottom);
				//1, 1, bounds.left, bounds.top);
		
		if(result != 1) {
			TokTales.getLog().e(TAG, "Image resize failed with error: " +result);
			return null;
		}
		
		TextureImage cropImage = new TextureImage(cropBuffer, bounds.width(), bounds.hashCode(), 4);
		return cropImage;
	}
	*/
	
}
