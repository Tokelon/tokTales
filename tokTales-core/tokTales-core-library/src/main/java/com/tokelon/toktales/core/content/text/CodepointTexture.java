package com.tokelon.toktales.core.content.text;

import java.nio.ByteBuffer;

import com.tokelon.toktales.core.content.graphics.BitmapImpl;
import com.tokelon.toktales.core.content.graphics.IBitmap;
import com.tokelon.toktales.core.render.Texture;
import com.tokelon.toktales.core.render.opengl.gl20.IGL11;


public class CodepointTexture extends Texture implements ICodepointTexture {

	
	private final int originOffsetX;
	private final int originOffsetY;
	
	public CodepointTexture(IBitmap bitmap, int originOffsetX, int originOffsetY) {
		super(bitmap);
		
		this.originOffsetX = originOffsetX;
		this.originOffsetY = originOffsetY;
	}
	
	
	@Override
	public int getOriginOffsetX() {
		return originOffsetX;
	}
	
	@Override
	public int getOriginOffsetY() {
		return originOffsetY;
	}


	@Override
	public int getTextureFormat() {
		return IGL11.GL_ALPHA;
	}
	
	@Override
	public int getInternalFormat() {
		return IGL11.GL_ALPHA;
	}
	
	@Override
	public int getDataType() {
		return IGL11.GL_UNSIGNED_BYTE;
	}

	@Override
	public int getUnpackAlignment() {
		return 1;
	}
	
	
	public static CodepointTexture create(ByteBuffer data, int width, int height, int originOffsetX, int originOffsetY) {
		BitmapImpl bitmap = new BitmapImpl(data, width, height, IBitmap.FORMAT_ALPHA_8);
		CodepointTexture texture = new CodepointTexture(bitmap, originOffsetX, originOffsetY);
		return texture;
	}
	
}
