package com.tokelon.toktales.android.render.opengl.gl20;

import com.tokelon.toktales.core.render.opengl.IGLFlags;
import com.tokelon.toktales.core.render.opengl.gl20.IGL12;

import android.opengl.GLES20;
import gnu.trove.map.hash.TIntIntHashMap;

abstract class AndroidGL12Flags implements IGLFlags {

	
	protected static final TIntIntHashMap GL12_FLAG_MAP = new TIntIntHashMap(10, 1f, Integer.MAX_VALUE, Integer.MIN_VALUE);

	static {
		/** Aliases for smooth points and lines. */
		GL12_FLAG_MAP.put(IGL12.GL_ALIASED_POINT_SIZE_RANGE, GLES20.GL_ALIASED_POINT_SIZE_RANGE);
		GL12_FLAG_MAP.put(IGL12.GL_ALIASED_LINE_WIDTH_RANGE, GLES20.GL_ALIASED_LINE_WIDTH_RANGE);
		/**
		 * Accepted by the {@code type} parameter of DrawPixels, ReadPixels, TexImage1D, TexImage2D, GetTexImage, TexImage3D, TexSubImage1D, TexSubImage2D,
		 * TexSubImage3D, GetHistogram, GetMinmax, ConvolutionFilter1D, ConvolutionFilter2D, ConvolutionFilter3D, GetConvolutionFilter, SeparableFilter2D,
		 * SeparableFilter3D, GetSeparableFilter, ColorTable, GetColorTable, TexImage4D, and TexSubImage4D.
		 */
		GL12_FLAG_MAP.put(IGL12.GL_UNSIGNED_SHORT_5_6_5, GLES20.GL_UNSIGNED_SHORT_5_6_5);
		GL12_FLAG_MAP.put(IGL12.GL_UNSIGNED_SHORT_4_4_4_4, GLES20.GL_UNSIGNED_SHORT_4_4_4_4);
		/**
		 * Accepted by the {@code param} parameter of TexParameteri and TexParameterf, and by the {@code params} parameter of TexParameteriv and TexParameterfv,
		 * when their {@code pname} parameter is TEXTURE_WRAP_S, TEXTURE_WRAP_T, or TEXTURE_WRAP_R.
		 */
		GL12_FLAG_MAP.put(IGL12.GL_CLAMP_TO_EDGE, GLES20.GL_CLAMP_TO_EDGE);
	}

}
