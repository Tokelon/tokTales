package com.tokelon.toktales.desktop.content;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import javax.inject.Inject;
import javax.xml.bind.DatatypeConverter;

import com.tokelon.toktales.core.content.IContentManager;
import com.tokelon.toktales.core.content.ISpecialContent;
import com.tokelon.toktales.core.content.graphics.IBitmap;
import com.tokelon.toktales.core.content.manage.assets.IGraphicsAsset;
import com.tokelon.toktales.core.content.manage.texture.ITextureAsset;
import com.tokelon.toktales.core.content.sprite.SpriteAsset;
import com.tokelon.toktales.core.content.text.ITextureFont;
import com.tokelon.toktales.core.engine.content.AbstractContentService;
import com.tokelon.toktales.core.engine.content.ContentException;
import com.tokelon.toktales.core.engine.content.ContentLoadException;
import com.tokelon.toktales.core.engine.content.IContentService;
import com.tokelon.toktales.core.engine.content.IGraphicLoadingOptions;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.storage.IStorageService;
import com.tokelon.toktales.core.engine.storage.IStorageService.IStorageServiceFactory;
import com.tokelon.toktales.core.engine.storage.StorageException;
import com.tokelon.toktales.core.game.model.IRectangle2i;
import com.tokelon.toktales.core.render.ITexture;
import com.tokelon.toktales.core.render.Texture;
import com.tokelon.toktales.core.resources.IListing;
import com.tokelon.toktales.core.storage.IApplicationLocation;
import com.tokelon.toktales.core.values.TokelonEmbeddedGraphics;
import com.tokelon.toktales.desktop.engine.inject.annotation.AssetRoot;
import com.tokelon.toktales.desktop.lwjgl.LWJGLException;
import com.tokelon.toktales.desktop.lwjgl.data.ISTBBitmap;
import com.tokelon.toktales.desktop.lwjgl.data.LWJGLBufferUtils;
import com.tokelon.toktales.desktop.lwjgl.data.STBBitmap;
import com.tokelon.toktales.desktop.lwjgl.data.STBTextureFont;

public class DesktopContentService extends AbstractContentService implements IContentService {
	
	public static final String TAG = "DesktopContentService";
	
	
	private final ILogger logger;
	private final IStorageService assetStorageService;

	@Inject
	public DesktopContentService(ILogger logger, IStorageServiceFactory storageServiceFactory, @AssetRoot String assetRoot) {
		this.logger = logger;
		this.assetStorageService = storageServiceFactory.create(assetRoot);
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
	public IGraphicsAsset lookForGraphicAssetAndLoad(IApplicationLocation location, String fileName) {
		return lookForGraphicAssetAndLoad(location, fileName, null);
	}

	@Override
	public IGraphicsAsset lookForGraphicAssetAndLoad(IApplicationLocation location, String fileName, IGraphicLoadingOptions options) {

		InputStream assetInputStream = assetStorageService.tryReadAppFileOnExternal(location, fileName);
		if(assetInputStream == null) {
			return null;
		}

		
		try {
			return loadGraphicAssetFromSource(assetInputStream, options);
		} catch (ContentException e) {
			return null;
		}
		finally {
			try {
				assetInputStream.close();
			} catch (IOException e) {
				// Nothing to do here
			}
		}
		
	}

	
	@Override
	public IGraphicsAsset loadGraphicAsset(IApplicationLocation location, String fileName) throws ContentLoadException, ContentException {
		return loadGraphicAsset(location, fileName, null);
	}

	@Override
	public IGraphicsAsset loadGraphicAsset(IApplicationLocation location, String fileName, IGraphicLoadingOptions options) throws ContentLoadException, ContentException {
		InputStream assetInputStream;
		try {
			assetInputStream = assetStorageService.readAppFileOnExternal(location, fileName);
		} catch (StorageException e) {
			throw new ContentLoadException(e);
		}
		
		return loadGraphicAssetFromSource(assetInputStream, options);
	}

	
	@Override
	public IGraphicsAsset loadSpecialContent(ISpecialContent specialContent) throws ContentException {
		//TokTales.getLog().d(TAG, "Trying to resolve special asset");
		
		String dataString = null;
		if(specialContent == IContentManager.SpecialContent.SPRITE_EMPTY) {
			
		}
		else if(specialContent == IContentManager.SpecialContent.SPRITE_NOT_FOUND) {
			dataString = TokelonEmbeddedGraphics.GRAPHIC_SPRITE404;
		}
		else if(specialContent == IContentManager.SpecialContent.SPRITE_LOAD_ERROR) {

		}
		
		
		if(dataString == null) {
			return null;
		}
		
		try {
			byte[] data = DatatypeConverter.parseBase64Binary(dataString); // Maybe extract into helper class
			

			ByteBuffer buffer = LWJGLBufferUtils.getWrapper().createByteBuffer(data.length);
			buffer.put(data);
			buffer.flip();


			STBBitmap texImage;
			try {
				texImage = STBBitmap.createFromBuffer(buffer);
			} catch (LWJGLException e) {
				throw new ContentException(e);
			}
			
			
			return createGraphicAssetContainer(texImage, null);
		}
		catch(IllegalArgumentException iae) {
			return null;
		}
	}

	
	@Override
	public ITextureFont lookForFontAndLoad(IApplicationLocation location, String filename) {
		
		InputStream fontInputStream = assetStorageService.tryReadAppFileOnExternal(location, filename);
		if(fontInputStream == null) {
			return null;
		}
		
		try {
			ITextureFont font = loadFontFromSource(fontInputStream);
			return font;
		} catch (ContentException e) {
			logger.e(TAG, e.getMessage());
			return null;
		} finally {
			try {
				fontInputStream.close();
			} catch (IOException ex) { /* Nothing */ }
		}
	}
	
	@Override
	public ITextureFont loadFont(IApplicationLocation location, String filename) throws ContentException {
		InputStream fontInputStream;
		try {
			fontInputStream = assetStorageService.readAppFileOnExternal(location, filename);
		} catch (StorageException se) {
			throw new ContentLoadException(se);
		}
		
		try {
			return loadFontFromSource(fontInputStream);			
		} finally {
			try {
				fontInputStream.close();	
			} catch (IOException ioe) { /* Nothing */ }
		}
	}
	
	@Override
	public ITextureFont loadFontFromFile(File file) throws ContentException {
		
		InputStream fontInputStream;
		try {
			fontInputStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			throw new ContentException(e);
		}
		
		return loadFontFromSource(fontInputStream);
	}

	
	private ITextureFont loadFontFromSource(InputStream source) throws ContentException {
		
		// TODO: Important - Fixed size buffer
		ByteBuffer buffer = LWJGLBufferUtils.getWrapper().createByteBuffer(1000000);

		try {
			ContentHelper.readStreamIntoByteBuffer(source, buffer);	
		} catch (IOException e) {
			throw new ContentLoadException(e);
		}
		
		
		try {
			return STBTextureFont.create(buffer, 64);
		} catch (LWJGLException e) {
			throw new ContentException(e);
		}
	}
	
	
	
	@Override
	public IGraphicsAsset loadGraphicAssetFromSource(InputStream source) throws ContentLoadException, ContentException {
		return loadGraphicAssetFromSource(source, null);
	}

	
	@Override
	public IGraphicsAsset loadGraphicAssetFromSource(InputStream source, IGraphicLoadingOptions options) throws ContentLoadException, ContentException {
		
		// TODO: Important - Fixed size buffer
		ByteBuffer buffer = LWJGLBufferUtils.getWrapper().createByteBuffer(4 * 1 * 512 * 512);

		STBBitmap image = decodeImageStream(buffer, source, options);

		return createGraphicAssetContainer(image, options);
	}
	
	
	private IGraphicsAsset createGraphicAssetContainer(STBBitmap image, IGraphicLoadingOptions options) {
		// TODO: Use the options to pass settings for the texture - Mainly the texture filter (nearest or linear)
		Texture texture = new Texture(image);
		
		return new SpriteAsset(texture);
	}

	
	private STBBitmap decodeImageStream(ByteBuffer buffer, InputStream stream, IGraphicLoadingOptions options) throws ContentException {
		
		
		try(ReadableByteChannel rbc = Channels.newChannel(stream);) {
			
			int bytes;
			do {
				bytes = rbc.read(buffer);	// This returns 0 when its unable to write into the buffer because its full
				
				//if(buffer.remaining() == 0)
				// resize buffer
				if(bytes == 0) {
					throw new ContentLoadException("Unable to write more bytes: buffer is full");
				}
			}
			while(bytes != -1);
		}
		catch (IOException ioe) {
			throw new ContentLoadException(ioe);
		}
		finally {
			try {
				stream.close();
			} catch (IOException e) { /* No can do */ }
		}
		
		buffer.flip();
		
		
		
		STBBitmap texImage;
		try {
			texImage = STBBitmap.createFromBuffer(buffer);
		} catch (LWJGLException e) {
			throw new ContentException(e);
		}
		
		return texImage;
	}
	
	
	
	@Override
	public ITexture extractAssetTexture(IGraphicsAsset asset) {
		return extractTexture(asset);
	}
	
	private static ITexture extractTexture(IGraphicsAsset asset) {
		// Do type check ?

		if(asset == null) {
			return null;	// TODO: Remove and fix special assets
		}
		
		if(asset instanceof ITextureAsset) {
			return ((ITextureAsset) asset).getTexture();
		}
		else {
			return null; // TODO: Avoid returning null if possible
		}
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
		
		ByteBuffer cropBuffer = LWJGLBufferUtils.getWrapper().createByteBuffer(channels * bounds.width() * bounds.height());
		
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
		

		STBBitmap texImage = STBBitmap.create(cropBuffer, bounds.width(), bounds.height(), channels, bitmap.getSourceChannels());
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
