package com.tokelon.toktales.android.content;

import java.io.File;
import java.io.InputStream;
import java.util.Set;

import javax.inject.Inject;

import com.tokelon.toktales.core.content.manage.contents.IContentAssetReader;
import com.tokelon.toktales.core.location.ApplicationLocation;
import com.tokelon.toktales.core.location.IApplicationLocation;
import com.tokelon.toktales.tools.assets.annotation.ParentResolvers;
import com.tokelon.toktales.tools.assets.exception.AssetException;
import com.tokelon.toktales.tools.assets.files.IParentResolver;
import com.tokelon.toktales.tools.assets.files.RelativeFileAssetReader;
import com.tokelon.toktales.tools.core.objects.options.IOptions;

public class AndroidRelativeFileAssetReader extends RelativeFileAssetReader implements IAndroidRelativeFileAssetReader {


	private final Set<IParentResolver<IApplicationLocation>> applicationLocationParentResolvers;
	private final IContentAssetReader contentAssetReader;

	@Inject
	public AndroidRelativeFileAssetReader(
			@ParentResolvers Set<IParentResolver<File>> fileParentResolvers,
			@ParentResolvers Set<IParentResolver<IApplicationLocation>> applicationLocationParentResolvers,
			IContentAssetReader contentAssetReader
	) {
		super(fileParentResolvers);

		this.applicationLocationParentResolvers = applicationLocationParentResolvers;
		this.contentAssetReader = contentAssetReader;
	}


	@Override
	public InputStream readAsset(File file, Object parentIdentifier, IOptions options) throws AssetException {
		String name = file.getName();
		String parent = file.getParent();
		ApplicationLocation location = new ApplicationLocation(parent == null ? "" : parent);

		IApplicationLocation readableLocation = null;
		for (IParentResolver<IApplicationLocation> applicationLocationIParentResolver : applicationLocationParentResolvers) {
			readableLocation = applicationLocationIParentResolver.resolve(location, parentIdentifier);
			if(readableLocation != null) {
				break;
			}
		}

		if(readableLocation != null) {
			return contentAssetReader.readAsset(name, readableLocation, options);
		}

		return super.readAsset(file, parentIdentifier, options);
	}

}
