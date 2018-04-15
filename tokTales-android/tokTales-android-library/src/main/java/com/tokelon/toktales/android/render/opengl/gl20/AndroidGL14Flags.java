package com.tokelon.toktales.android.render.opengl.gl20;

import java.util.HashMap;
import java.util.Map;

import com.tokelon.toktales.core.render.opengl.IGLFlags;
import com.tokelon.toktales.core.render.opengl.gl20.IGL14;

import android.opengl.GLES20;

abstract class AndroidGL14Flags implements IGLFlags {


	protected static final Map<Integer, Integer> GL14_FLAG_MAP = new HashMap<>();

	static {
	    /** Accepted by the {@code target} parameter of Hint, and by the {@code pname} parameter of GetBooleanv, GetIntegerv, GetFloatv, and GetDoublev. */
		GL14_FLAG_MAP.put(IGL14.GL_GENERATE_MIPMAP_HINT, GLES20.GL_GENERATE_MIPMAP_HINT);
	    /** Accepted by the {@code sfactor} and {@code dfactor} parameters of BlendFunc. */
		GL14_FLAG_MAP.put(IGL14.GL_CONSTANT_COLOR, GLES20.GL_CONSTANT_COLOR);
		GL14_FLAG_MAP.put(IGL14.GL_ONE_MINUS_CONSTANT_COLOR, GLES20.GL_ONE_MINUS_CONSTANT_COLOR);
		GL14_FLAG_MAP.put(IGL14.GL_CONSTANT_ALPHA, GLES20.GL_CONSTANT_ALPHA);
		GL14_FLAG_MAP.put(IGL14.GL_ONE_MINUS_CONSTANT_ALPHA, GLES20.GL_ONE_MINUS_CONSTANT_ALPHA);
	    /** Accepted by the {@code mode} parameter of BlendEquation. */
		GL14_FLAG_MAP.put(IGL14.GL_FUNC_ADD, GLES20.GL_FUNC_ADD);
	    /** Accepted by the {@code mode} parameter of BlendEquation. */
		GL14_FLAG_MAP.put(IGL14.GL_FUNC_SUBTRACT, GLES20.GL_FUNC_SUBTRACT);
		GL14_FLAG_MAP.put(IGL14.GL_FUNC_REVERSE_SUBTRACT, GLES20.GL_FUNC_REVERSE_SUBTRACT);
	    /** Accepted by the {@code internalFormat} parameter of TexImage1D, TexImage2D, CopyTexImage1D and CopyTexImage2D. */
		GL14_FLAG_MAP.put(IGL14.GL_DEPTH_COMPONENT16, GLES20.GL_DEPTH_COMPONENT16);
	    /** Accepted by the {@code pname} parameter of GetBooleanv, GetIntegerv, GetFloatv, and GetDoublev. */
		GL14_FLAG_MAP.put(IGL14.GL_BLEND_DST_RGB, GLES20.GL_BLEND_DST_RGB);
		GL14_FLAG_MAP.put(IGL14.GL_BLEND_SRC_RGB, GLES20.GL_BLEND_SRC_RGB);
		GL14_FLAG_MAP.put(IGL14.GL_BLEND_DST_ALPHA, GLES20.GL_BLEND_DST_ALPHA);
		GL14_FLAG_MAP.put(IGL14.GL_BLEND_SRC_ALPHA, GLES20.GL_BLEND_SRC_ALPHA);
	    /** Accepted by the {@code sfail}, {@code dpfail}, and {@code dppass} parameter of StencilOp. */
		GL14_FLAG_MAP.put(IGL14.GL_INCR_WRAP, GLES20.GL_INCR_WRAP);
		GL14_FLAG_MAP.put(IGL14.GL_DECR_WRAP, GLES20.GL_DECR_WRAP);
	    /**
	     * Accepted by the {@code param} parameter of TexParameteri and TexParameterf, and by the {@code params} parameter of TexParameteriv and TexParameterfv,
	     * when their {@code pname} parameter is TEXTURE_WRAP_S, TEXTURE_WRAP_T, or TEXTURE_WRAP_R.
	     */
		GL14_FLAG_MAP.put(IGL14.GL_MIRRORED_REPEAT, GLES20.GL_MIRRORED_REPEAT);
	}
	
}
