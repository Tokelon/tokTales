package com.tokelon.toktales.android.render.opengl.gl20;

import java.util.HashMap;
import java.util.Map;

import com.tokelon.toktales.core.render.opengl.IGLFlags;
import com.tokelon.toktales.core.render.opengl.gl20.IGL11;

import android.opengl.GLES20;

@SuppressWarnings("deprecation")
abstract class AndroidGL11Flags implements IGLFlags {

	/* TODO: Is flag mapping needed for Android?
	 * If yes then implement usage in api calls.
	 * 
	 */
	
	//private static final TIntIntHashMap GL11_FLAG_MAP = new TIntIntHashMap(150, 1f, Integer.MAX_VALUE, Integer.MIN_VALUE);
	protected static final Map<Integer, Integer> GL11_FLAG_MAP = new HashMap<>(); 
	
	static {
	    /** AlphaFunction */
		GL11_FLAG_MAP.put(IGL11.GL_NEVER, GLES20.GL_NEVER);
		GL11_FLAG_MAP.put(IGL11.GL_LESS, GLES20.GL_LESS);
		GL11_FLAG_MAP.put(IGL11.GL_EQUAL, GLES20.GL_EQUAL);
		GL11_FLAG_MAP.put(IGL11.GL_LEQUAL, GLES20.GL_LEQUAL);
		GL11_FLAG_MAP.put(IGL11.GL_GREATER, GLES20.GL_GREATER);
		GL11_FLAG_MAP.put(IGL11.GL_NOTEQUAL, GLES20.GL_NOTEQUAL);
		GL11_FLAG_MAP.put(IGL11.GL_GEQUAL, GLES20.GL_GEQUAL);
		GL11_FLAG_MAP.put(IGL11.GL_ALWAYS, GLES20.GL_ALWAYS);
	    /** AttribMask */
		GL11_FLAG_MAP.put(IGL11.GL_DEPTH_BUFFER_BIT, GLES20.GL_DEPTH_BUFFER_BIT);
		GL11_FLAG_MAP.put(IGL11.GL_STENCIL_BUFFER_BIT, GLES20.GL_STENCIL_BUFFER_BIT);
		GL11_FLAG_MAP.put(IGL11.GL_COLOR_BUFFER_BIT, GLES20.GL_COLOR_BUFFER_BIT);
	    /** BeginMode */
		GL11_FLAG_MAP.put(IGL11.GL_POINTS, GLES20.GL_POINTS);
		GL11_FLAG_MAP.put(IGL11.GL_LINES, GLES20.GL_LINES);
		GL11_FLAG_MAP.put(IGL11.GL_LINE_LOOP, GLES20.GL_LINE_LOOP);
		GL11_FLAG_MAP.put(IGL11.GL_LINE_STRIP, GLES20.GL_LINE_STRIP);
		GL11_FLAG_MAP.put(IGL11.GL_TRIANGLES, GLES20.GL_TRIANGLES);
		GL11_FLAG_MAP.put(IGL11.GL_TRIANGLE_STRIP, GLES20.GL_TRIANGLE_STRIP);
		GL11_FLAG_MAP.put(IGL11.GL_TRIANGLE_FAN, GLES20.GL_TRIANGLE_FAN);
	    /** BlendingFactorDest */
		GL11_FLAG_MAP.put(IGL11.GL_ZERO, GLES20.GL_ZERO);
		GL11_FLAG_MAP.put(IGL11.GL_ONE, GLES20.GL_ONE);
		GL11_FLAG_MAP.put(IGL11.GL_SRC_COLOR, GLES20.GL_SRC_COLOR);
		GL11_FLAG_MAP.put(IGL11.GL_ONE_MINUS_SRC_COLOR, GLES20.GL_ONE_MINUS_SRC_COLOR);
		GL11_FLAG_MAP.put(IGL11.GL_SRC_ALPHA, GLES20.GL_SRC_ALPHA);
		GL11_FLAG_MAP.put(IGL11.GL_ONE_MINUS_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		GL11_FLAG_MAP.put(IGL11.GL_DST_ALPHA, GLES20.GL_DST_ALPHA);
		GL11_FLAG_MAP.put(IGL11.GL_ONE_MINUS_DST_ALPHA, GLES20.GL_ONE_MINUS_DST_ALPHA);
	    /** BlendingFactorSrc */
		GL11_FLAG_MAP.put(IGL11.GL_DST_COLOR, GLES20.GL_DST_COLOR);
		GL11_FLAG_MAP.put(IGL11.GL_ONE_MINUS_DST_COLOR, GLES20.GL_ONE_MINUS_DST_COLOR);
		GL11_FLAG_MAP.put(IGL11.GL_SRC_ALPHA_SATURATE, GLES20.GL_SRC_ALPHA_SATURATE);
	    /** Boolean */
		GL11_FLAG_MAP.put(IGL11.GL_TRUE, GLES20.GL_TRUE);
		GL11_FLAG_MAP.put(IGL11.GL_FALSE, GLES20.GL_FALSE);
	    /** DataType */
		GL11_FLAG_MAP.put(IGL11.GL_BYTE, GLES20.GL_BYTE);
		GL11_FLAG_MAP.put(IGL11.GL_UNSIGNED_BYTE, GLES20.GL_UNSIGNED_BYTE);
		GL11_FLAG_MAP.put(IGL11.GL_SHORT, GLES20.GL_SHORT);
		GL11_FLAG_MAP.put(IGL11.GL_UNSIGNED_SHORT, GLES20.GL_UNSIGNED_SHORT);
		GL11_FLAG_MAP.put(IGL11.GL_INT, GLES20.GL_INT);
		GL11_FLAG_MAP.put(IGL11.GL_UNSIGNED_INT, GLES20.GL_UNSIGNED_INT);
		GL11_FLAG_MAP.put(IGL11.GL_FLOAT, GLES20.GL_FLOAT);
	    /** DrawBufferMode */
		GL11_FLAG_MAP.put(IGL11.GL_NONE, GLES20.GL_NONE);
		GL11_FLAG_MAP.put(IGL11.GL_FRONT, GLES20.GL_FRONT);
		GL11_FLAG_MAP.put(IGL11.GL_BACK, GLES20.GL_BACK);
		GL11_FLAG_MAP.put(IGL11.GL_FRONT_AND_BACK, GLES20.GL_FRONT_AND_BACK);
	    /** ErrorCode */
		GL11_FLAG_MAP.put(IGL11.GL_NO_ERROR, GLES20.GL_NO_ERROR);
		GL11_FLAG_MAP.put(IGL11.GL_INVALID_ENUM, GLES20.GL_INVALID_ENUM);
		GL11_FLAG_MAP.put(IGL11.GL_INVALID_VALUE, GLES20.GL_INVALID_VALUE);
		GL11_FLAG_MAP.put(IGL11.GL_INVALID_OPERATION, GLES20.GL_INVALID_OPERATION);
		GL11_FLAG_MAP.put(IGL11.GL_OUT_OF_MEMORY, GLES20.GL_OUT_OF_MEMORY);
	    /** FrontFaceDirection */
		GL11_FLAG_MAP.put(IGL11.GL_CW, GLES20.GL_CW);
		GL11_FLAG_MAP.put(IGL11.GL_CCW, GLES20.GL_CCW);
	    /** GetTarget */
		GL11_FLAG_MAP.put(IGL11.GL_LINE_WIDTH, GLES20.GL_LINE_WIDTH);
		GL11_FLAG_MAP.put(IGL11.GL_CULL_FACE, GLES20.GL_CULL_FACE);
		GL11_FLAG_MAP.put(IGL11.GL_CULL_FACE_MODE, GLES20.GL_CULL_FACE_MODE);
		GL11_FLAG_MAP.put(IGL11.GL_FRONT_FACE, GLES20.GL_FRONT_FACE);
		GL11_FLAG_MAP.put(IGL11.GL_DEPTH_RANGE, GLES20.GL_DEPTH_RANGE);
		GL11_FLAG_MAP.put(IGL11.GL_DEPTH_TEST, GLES20.GL_DEPTH_TEST);
		GL11_FLAG_MAP.put(IGL11.GL_DEPTH_WRITEMASK, GLES20.GL_DEPTH_WRITEMASK);
		GL11_FLAG_MAP.put(IGL11.GL_DEPTH_CLEAR_VALUE, GLES20.GL_DEPTH_CLEAR_VALUE);
		GL11_FLAG_MAP.put(IGL11.GL_DEPTH_FUNC, GLES20.GL_DEPTH_FUNC);
		GL11_FLAG_MAP.put(IGL11.GL_STENCIL_TEST, GLES20.GL_STENCIL_TEST);
		GL11_FLAG_MAP.put(IGL11.GL_STENCIL_CLEAR_VALUE, GLES20.GL_STENCIL_CLEAR_VALUE);
		GL11_FLAG_MAP.put(IGL11.GL_STENCIL_FUNC, GLES20.GL_STENCIL_FUNC);
		GL11_FLAG_MAP.put(IGL11.GL_STENCIL_VALUE_MASK, GLES20.GL_STENCIL_VALUE_MASK);
		GL11_FLAG_MAP.put(IGL11.GL_STENCIL_FAIL, GLES20.GL_STENCIL_FAIL);
		GL11_FLAG_MAP.put(IGL11.GL_STENCIL_PASS_DEPTH_FAIL, GLES20.GL_STENCIL_PASS_DEPTH_FAIL);
		GL11_FLAG_MAP.put(IGL11.GL_STENCIL_PASS_DEPTH_PASS, GLES20.GL_STENCIL_PASS_DEPTH_PASS);
		GL11_FLAG_MAP.put(IGL11.GL_STENCIL_REF, GLES20.GL_STENCIL_REF);
		GL11_FLAG_MAP.put(IGL11.GL_STENCIL_WRITEMASK, GLES20.GL_STENCIL_WRITEMASK);
		GL11_FLAG_MAP.put(IGL11.GL_VIEWPORT, GLES20.GL_VIEWPORT);
		GL11_FLAG_MAP.put(IGL11.GL_DITHER, GLES20.GL_DITHER);
		GL11_FLAG_MAP.put(IGL11.GL_BLEND, GLES20.GL_BLEND);
		GL11_FLAG_MAP.put(IGL11.GL_SCISSOR_BOX, GLES20.GL_SCISSOR_BOX);
		GL11_FLAG_MAP.put(IGL11.GL_SCISSOR_TEST, GLES20.GL_SCISSOR_TEST);
		GL11_FLAG_MAP.put(IGL11.GL_COLOR_CLEAR_VALUE, GLES20.GL_COLOR_CLEAR_VALUE);
		GL11_FLAG_MAP.put(IGL11.GL_COLOR_WRITEMASK, GLES20.GL_COLOR_WRITEMASK);
		GL11_FLAG_MAP.put(IGL11.GL_UNPACK_ALIGNMENT, GLES20.GL_UNPACK_ALIGNMENT);
		GL11_FLAG_MAP.put(IGL11.GL_PACK_ALIGNMENT, GLES20.GL_PACK_ALIGNMENT);
		GL11_FLAG_MAP.put(IGL11.GL_MAX_TEXTURE_SIZE, GLES20.GL_MAX_TEXTURE_SIZE);
		GL11_FLAG_MAP.put(IGL11.GL_MAX_VIEWPORT_DIMS, GLES20.GL_MAX_VIEWPORT_DIMS);
		GL11_FLAG_MAP.put(IGL11.GL_SUBPIXEL_BITS, GLES20.GL_SUBPIXEL_BITS);
		GL11_FLAG_MAP.put(IGL11.GL_RED_BITS, GLES20.GL_RED_BITS);
		GL11_FLAG_MAP.put(IGL11.GL_GREEN_BITS, GLES20.GL_GREEN_BITS);
		GL11_FLAG_MAP.put(IGL11.GL_BLUE_BITS, GLES20.GL_BLUE_BITS);
		GL11_FLAG_MAP.put(IGL11.GL_ALPHA_BITS, GLES20.GL_ALPHA_BITS);
		GL11_FLAG_MAP.put(IGL11.GL_DEPTH_BITS, GLES20.GL_DEPTH_BITS);
		GL11_FLAG_MAP.put(IGL11.GL_STENCIL_BITS, GLES20.GL_STENCIL_BITS);
		GL11_FLAG_MAP.put(IGL11.GL_TEXTURE_2D, GLES20.GL_TEXTURE_2D);
	    /** HintMode */
		GL11_FLAG_MAP.put(IGL11.GL_DONT_CARE, GLES20.GL_DONT_CARE);
		GL11_FLAG_MAP.put(IGL11.GL_FASTEST, GLES20.GL_FASTEST);
		GL11_FLAG_MAP.put(IGL11.GL_NICEST, GLES20.GL_NICEST);
	    /** LogicOp */
		GL11_FLAG_MAP.put(IGL11.GL_INVERT, GLES20.GL_INVERT);
	    /** PixelFormat */
		GL11_FLAG_MAP.put(IGL11.GL_STENCIL_INDEX, GLES20.GL_STENCIL_INDEX);
		GL11_FLAG_MAP.put(IGL11.GL_DEPTH_COMPONENT, GLES20.GL_DEPTH_COMPONENT);
		GL11_FLAG_MAP.put(IGL11.GL_ALPHA, GLES20.GL_ALPHA);
		GL11_FLAG_MAP.put(IGL11.GL_RGB, GLES20.GL_RGB);
		GL11_FLAG_MAP.put(IGL11.GL_RGBA, GLES20.GL_RGBA);
		GL11_FLAG_MAP.put(IGL11.GL_LUMINANCE, GLES20.GL_LUMINANCE);
		GL11_FLAG_MAP.put(IGL11.GL_LUMINANCE_ALPHA, GLES20.GL_LUMINANCE_ALPHA);
	    /** StencilOp */
		GL11_FLAG_MAP.put(IGL11.GL_KEEP, GLES20.GL_KEEP);
		GL11_FLAG_MAP.put(IGL11.GL_REPLACE, GLES20.GL_REPLACE);
		GL11_FLAG_MAP.put(IGL11.GL_INCR, GLES20.GL_INCR);
		GL11_FLAG_MAP.put(IGL11.GL_DECR, GLES20.GL_DECR);
	    /** StringName */
		GL11_FLAG_MAP.put(IGL11.GL_VENDOR, GLES20.GL_VENDOR);
		GL11_FLAG_MAP.put(IGL11.GL_RENDERER, GLES20.GL_RENDERER);
		GL11_FLAG_MAP.put(IGL11.GL_VERSION, GLES20.GL_VERSION);
		GL11_FLAG_MAP.put(IGL11.GL_EXTENSIONS, GLES20.GL_EXTENSIONS);
	    /** TextureMagFilter */
		GL11_FLAG_MAP.put(IGL11.GL_NEAREST, GLES20.GL_NEAREST);
		GL11_FLAG_MAP.put(IGL11.GL_LINEAR, GLES20.GL_LINEAR);
	    /** TextureMinFilter */
		GL11_FLAG_MAP.put(IGL11.GL_NEAREST_MIPMAP_NEAREST, GLES20.GL_NEAREST_MIPMAP_NEAREST);
		GL11_FLAG_MAP.put(IGL11.GL_LINEAR_MIPMAP_NEAREST, GLES20.GL_LINEAR_MIPMAP_NEAREST);
		GL11_FLAG_MAP.put(IGL11.GL_NEAREST_MIPMAP_LINEAR, GLES20.GL_NEAREST_MIPMAP_LINEAR);
		GL11_FLAG_MAP.put(IGL11.GL_LINEAR_MIPMAP_LINEAR, GLES20.GL_LINEAR_MIPMAP_LINEAR);
	    /** TextureParameterName */
		GL11_FLAG_MAP.put(IGL11.GL_TEXTURE_MAG_FILTER, GLES20.GL_TEXTURE_MAG_FILTER);
		GL11_FLAG_MAP.put(IGL11.GL_TEXTURE_MIN_FILTER, GLES20.GL_TEXTURE_MIN_FILTER);
		GL11_FLAG_MAP.put(IGL11.GL_TEXTURE_WRAP_S, GLES20.GL_TEXTURE_WRAP_S);
		GL11_FLAG_MAP.put(IGL11.GL_TEXTURE_WRAP_T, GLES20.GL_TEXTURE_WRAP_T);
		/** TextureWrapMode */
		GL11_FLAG_MAP.put(IGL11.GL_REPEAT, GLES20.GL_REPEAT);
		GL11_FLAG_MAP.put(IGL11.GL_POLYGON_OFFSET_FACTOR, GLES20.GL_POLYGON_OFFSET_FACTOR);
		GL11_FLAG_MAP.put(IGL11.GL_POLYGON_OFFSET_UNITS, GLES20.GL_POLYGON_OFFSET_UNITS);
		GL11_FLAG_MAP.put(IGL11.GL_POLYGON_OFFSET_FILL, GLES20.GL_POLYGON_OFFSET_FILL);
		GL11_FLAG_MAP.put(IGL11.GL_RGBA4, GLES20.GL_RGBA4);
		GL11_FLAG_MAP.put(IGL11.GL_RGB5_A1, GLES20.GL_RGB5_A1);
	    /** texture_object */
		GL11_FLAG_MAP.put(IGL11.GL_TEXTURE_BINDING_2D, GLES20.GL_TEXTURE_BINDING_2D);
	}
	
}