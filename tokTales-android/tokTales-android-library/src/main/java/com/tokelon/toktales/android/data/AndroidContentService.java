package com.tokelon.toktales.android.data;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.inject.Inject;

import com.tokelon.toktales.android.R;
import com.tokelon.toktales.android.render.opengl.AndroidBitmapTexture;
import com.tokelon.toktales.core.content.IAssetContainer;
import com.tokelon.toktales.core.content.IContentManager;
import com.tokelon.toktales.core.content.ISpecialContent;
import com.tokelon.toktales.core.content.sprite.ISpriteAsset;
import com.tokelon.toktales.core.content.text.ITextureFont;
import com.tokelon.toktales.core.engine.AbstractEngineService;
import com.tokelon.toktales.core.engine.content.ContentException;
import com.tokelon.toktales.core.engine.content.ContentLoadException;
import com.tokelon.toktales.core.engine.content.IContentService;
import com.tokelon.toktales.core.engine.content.IGraphicLoadingOptions;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.render.IRenderTexture;
import com.tokelon.toktales.core.resources.IListing;
import com.tokelon.toktales.core.resources.Listing;
import com.tokelon.toktales.core.storage.IApplicationLocation;
import com.tokelon.toktales.core.storage.LocationPrefix;
import com.tokelon.toktales.core.storage.utils.StructuredLocation;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Typeface;

public class AndroidContentService extends AbstractEngineService implements IContentService {
	
	public static final String TAG = "AndroidContentService";

	
	private Context globalContext;
	
	private final ILogger logger;
	
	@Inject
	public AndroidContentService(ILogger logger, Context globalContext) {
		this.logger = logger;
	}
	
	
	public Context getGlobalContext() {
		return globalContext;
	}

	
	@Override
	public String[] listAssetDir(IApplicationLocation location) throws ContentException {
		String[] list;
		try {
			list = globalContext.getAssets().list(location.getLocationPath().getLocation());
		} catch (IOException e) {
			throw new ContentException(e);
		}
		
		return list;
	}
	
	
	@Override
	public IListing createAssetListing(IApplicationLocation location) throws ContentException {
		// Currently only lists files at location and does not follow directories
		
		String[] fileList = listAssetDir(location);
		
		String locationPath = location.getLocationPath().getPath();
		Listing listing = new Listing(locationPath);
		List<IListing.FileDescriptor> listingFileList = listing.getFileList();
		
		
		/* Note that the location will be the same object for all file descriptors.
		 * This is fine as long as they all are in the same location and because StructuredLocation is immutable. 
		 */
		StructuredLocation singleLocation = new StructuredLocation(LocationPrefix.ASSET, location.getLocationPath().getLocation());
		for(String fileName: fileList) {
			IListing.FileDescriptor fd = new Listing.FileDescriptorImpl(fileName, singleLocation);
			listingFileList.add(fd);
		}
		
		return listing;
	}
	
	
	
	
	//TODO: Maybe return only the inputstream and do the decoding in a different method?
	@Override
	public IAssetContainer<?> lookForGraphicAssetAndLoad(IApplicationLocation location, String fileName) {
		
		return lookForGraphicAssetAndLoad(location, fileName, null);
	}
	
	
	@Override
	public IAssetContainer<?> lookForGraphicAssetAndLoad(IApplicationLocation location, String fileName, IGraphicLoadingOptions options) {
			
		InputStream is = null;
		try {
			is = globalContext.getAssets().open(location.getLocationPath().getPath() + fileName);
			Bitmap sb = decodeBitmapStream(is, options);
			
			if(sb == null) {	// Bitmap could not be decoded
				return null;
			}
			else {
				return new BitmapContainer(sb);
			}
		}
		catch(IOException ioe) {
			return null;
		}
		catch(ContentException cle) {
			return null;
		}
		finally {
			if(is != null) {
				try {
					is.close();
				} catch (IOException e) {
					// Ignore?
				}
			}
		}
	}
	
	
	
	
	@Override
	public IAssetContainer<?> loadGraphicAsset(IApplicationLocation location, String fileName) throws ContentLoadException, ContentException {
		
		return loadGraphicAsset(location, fileName, null);
	}
	
	@Override
	public IAssetContainer<?> loadGraphicAsset(IApplicationLocation location, String fileName, IGraphicLoadingOptions options) throws ContentLoadException, ContentException {
		
		InputStream is = null;
		try {
			is = globalContext.getAssets().open(location.getLocationPath().getPath() + fileName);
			Bitmap sb = decodeBitmapStream(is, options);
			
			if(sb == null) {
				throw new ContentLoadException("Failed to decode bitmap");
			}
			else {
				return new BitmapContainer(sb);
			}
		}
		catch(IOException e) {
			throw new ContentException(e);
		}
		finally {
			if(is != null) {
				try {
					is.close();
				} catch (IOException e) {
					// Nothing to do
				}
			}
		}
	}
	
	
	
	@Override
	public IAssetContainer<?> loadSpecialContent(ISpecialContent specialContent) throws ContentException {
		BitmapContainer container = null;
		
		if(specialContent == IContentManager.SpecialContent.SPRITE_EMPTY) {
			Bitmap b1 = decodeBitmapResource(globalContext.getResources(), R.drawable.sp_special_empty);
			
			if(b1 == null) {
				throw new ContentLoadException("Failed to decode bitmap");
			} else {
				container = new BitmapContainer(b1);
			}
		}
		else if(specialContent == IContentManager.SpecialContent.SPRITE_NOT_FOUND) {
			Bitmap b2 = decodeBitmapResource(globalContext.getResources(), R.drawable.sp_special_notfound);
			
			if(b2 == null) {
				throw new ContentLoadException("Failed to decode bitmap");
			} else {
				container = new BitmapContainer(b2);
			}
		}
		else if(specialContent == IContentManager.SpecialContent.SPRITE_LOAD_ERROR) {
			Bitmap b3 = decodeBitmapResource(globalContext.getResources(), R.drawable.sp_special_loaderror);
			
			if(b3 == null) {
				throw new ContentLoadException("Failed to decode bitmap");
			} else {
				container = new BitmapContainer(b3);
			}
		}
		
		return container;
	}
	
	
	
	@Override
	public IAssetContainer<?> loadGraphicAssetFromSource(InputStream source) throws ContentLoadException, ContentException {
		return loadGraphicAssetFromSource(source, null);
	}
	
	@Override
	public IAssetContainer<?> loadGraphicAssetFromSource(InputStream source, IGraphicLoadingOptions options) throws ContentLoadException, ContentException {
		Bitmap sb = decodeBitmapStream(source, options);
		
		if(sb == null) {
			throw new ContentLoadException("Failed to decode bitmap");
		}
		else {
			return new BitmapContainer(sb);
		}
	}
	
	
	private Bitmap decodeBitmapStream(InputStream stream, IGraphicLoadingOptions options) throws ContentException {
		Bitmap bitmap = BitmapFactory.decodeStream(stream);
		
		if(bitmap != null) {

			if(options != null) {
				bitmap = bitmapPostEdit(bitmap, options);
			}
		}
		
		return bitmap;
	}
	
	private Bitmap decodeBitmapResource(Resources resources, int id) {		// Right now only used for special content
		Bitmap bitmap = BitmapFactory.decodeResource(resources, id);
		
		if(bitmap == null) {
			// Error ?
			logger.e(TAG, "Failed to decode bitmap resource!");
		}
		else {

			Bitmap resizedBitmap;
			
			// TODO: Important - Pass ideal size as parameters ?
			// This is for specials though
			resizedBitmap = getResizedBitmap(bitmap, 48, 48);
			
			
			// TODO: Bitmap Recycle - Probably does not need to be called.
			bitmap.recycle();		// Recycle bitmap here
			bitmap = resizedBitmap;
		}
		
		return bitmap;
	}
	
	
	private Bitmap bitmapPostEdit(Bitmap original, IGraphicLoadingOptions options) throws ContentException {

		Bitmap bitmapEdited = original;
		
		// TODO: Add check to ignore scaling if already the correct size
		// Scaling
		int scaling = options.getScalingOption();
		switch (scaling) {
		case IGraphicLoadingOptions.SCALING_FIXED:
			
			try {
				
				bitmapEdited = getResizedBitmap(bitmapEdited, options.getHorizontalScaling(), options.getVerticalScaling());
				
				
				// TODO: Bitmap Recycle - Probably does not need to be called.
				original.recycle();
			}
			catch(OutOfMemoryError memerr) {
				throw new ContentException("Out of memory! Failed to resize Bitmap to " +options.getHorizontalScaling() +" x " +options.getVerticalScaling());
			}
			
			break;
		case IGraphicLoadingOptions.SCALING_MULTIPLIER:
			
			// TODO: implement?
			break;
		default:
			break;
		}
		

		
		return bitmapEdited;
	}
	

	private static Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
	    int width = bm.getWidth();
	    int height = bm.getHeight();
	    
	    float scaleWidth = ((float) newWidth) / width;
	    float scaleHeight = ((float) newHeight) / height;
	    
	    // CREATE A MATRIX FOR THE MANIPULATION
	    Matrix matrix = new Matrix();
	    
	    // RESIZE THE BIT MAP
	    matrix.postScale(scaleWidth, scaleHeight);

	    // "RECREATE" THE NEW BITMAP
	    Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);		// Can throw an outofmemoryexception
	    
	    return resizedBitmap;
	}
	
	
	
	@Override
	public IRenderTexture extractAssetTexture(IAssetContainer<?> container) {
		// Do type check ?
		
		if(container == null) {
			return null;	// TODO: Remove and fix special assets
		}
		
		BitmapContainer bitmapContainer = (BitmapContainer) container;
		
		return new AndroidBitmapTexture(bitmapContainer.getAsset());
	}
	
	
	@Override
	public ITextureFont lookForFontAndLoad(IApplicationLocation location, String filename) {
		try {
			return loadFontFromSource(location.getLocationPath().getPath());
		} catch (ContentException ce) {
			logger.e(TAG, ce.getMessage());
			return null;
		}
	}
	
	
	@Override
	public ITextureFont loadFont(IApplicationLocation location, String filename) throws ContentException {
		return loadFontFromSource(location.getLocationPath().getPath() + filename);
	}
	

	private ITextureFont loadFontFromSource(String filePath) throws ContentException {
		Typeface typeface = Typeface.createFromAsset(globalContext.getAssets(), filePath);
		return fontFromTypeface(typeface);
	}
	

	@Override
	public ITextureFont loadFontFromFile(File file) throws ContentException {
		Typeface typeface;
		try {
			 typeface = Typeface.createFromFile(file);
		} catch(Exception ex) {
		    // Wrap any exception into content exception
			throw new ContentException(ex);
		}

		return fontFromTypeface(typeface);
	}
	
	
	private ITextureFont fontFromTypeface(Typeface typeface) throws ContentException {
		if(typeface == null) {
			throw new ContentException("Failed to read typeface");
		}
		
		AndroidTextureFont font = new AndroidTextureFont(32);
		font.initialize(typeface);
		
		return font;
	}
	

	
	/**
	 * 
	 * @param spriteAsset
	 * @return
	 * @throws NullPointerException If spriteAsset is null.
	 */
	@SuppressWarnings("unchecked")
	public static Bitmap extractAssetSprite(ISpriteAsset spriteAsset) {		// TODO: Delete or make private
		IAssetContainer<?> ac = spriteAsset.getContent();
		
		// TODO: Possibly remove this (the check) for performance reasons
		if(!(ac.getAsset() instanceof Bitmap)) {
			throw new RuntimeException("Sprite Asset is not a Bitmap container!");
		}
		
		BitmapContainer acb = (BitmapContainer) spriteAsset.getContent();
		return acb.getAsset();
	}
	
}
