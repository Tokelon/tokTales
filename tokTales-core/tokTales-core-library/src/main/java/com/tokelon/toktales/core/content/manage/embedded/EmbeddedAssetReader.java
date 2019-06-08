package com.tokelon.toktales.core.content.manage.embedded;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import com.google.common.io.BaseEncoding;
import com.google.common.io.BaseEncoding.DecodingException;
import com.google.common.io.ByteStreams;
import com.tokelon.toktales.core.content.manage.keys.IReadDelegateAssetKey;
import com.tokelon.toktales.core.engine.content.ContentException;
import com.tokelon.toktales.core.util.options.IOptions;

public class EmbeddedAssetReader implements IEmbeddedAssetReader {
	// Use IOptions to pass sanitize flag and/or separator parameters?
	//String sanitizedContent = content.replaceAll("\\s+", "");
	//BaseEncoding.base64().withSeparator("\r\n", lineLength)
	
	
	@Override
	public boolean canRead(Object key, Object options) {
		return IReadDelegateAssetKey.getReadableKey(key) instanceof IEmbeddedAssetKey;
	}

	@Override
	public InputStream read(Object key, Object options) throws ContentException {
		Object readableKey = IReadDelegateAssetKey.getReadableKey(key);
		
		if(!(readableKey instanceof IEmbeddedAssetKey)) {
			throw new ContentException("Unsupported key: must be instance of " + IEmbeddedAssetKey.class.getName());
		}
		IEmbeddedAssetKey embeddedKey = (IEmbeddedAssetKey) readableKey;
		IOptions iOptions = options instanceof IOptions ? (IOptions) options : null;

		if(embeddedKey instanceof IEmbeddedStringAssetKey) {
			String content = ((IEmbeddedStringAssetKey)embeddedKey).getStringContent();
			return readAsset(content, iOptions);
		}
		else {
			return readAsset(embeddedKey.getContent(), iOptions);
		}
	}
	
	
	@Override
	public InputStream readAsset(byte[] content, IOptions options) throws ContentException {
		byte[] decodedContent = null;
		try(InputStream decodingStream = BaseEncoding.base64().decodingStream(new InputStreamReader(new ByteArrayInputStream(content), StandardCharsets.US_ASCII))) {
			try {
				decodedContent = ByteStreams.toByteArray(decodingStream);
			} catch (IOException e) {
				throw new ContentException(e);
			}
		} catch(DecodingException decodingException) {
			throw new ContentException(decodingException);
		} catch (IOException closeException) {
			// Ignore
		}
		
		return new ByteArrayInputStream(decodedContent);
	}
	
	@Override
	public InputStream readAsset(String content, IOptions options) throws ContentException {
		byte[] decodedContent;
		try {
			decodedContent = BaseEncoding.base64().decode(content);
		} catch(IllegalArgumentException decodingException) {
			throw new ContentException(decodingException);
		}
		
		return new ByteArrayInputStream(decodedContent);
	}

}
