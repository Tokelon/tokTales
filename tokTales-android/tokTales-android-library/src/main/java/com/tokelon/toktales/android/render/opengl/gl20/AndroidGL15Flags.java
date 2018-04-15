package com.tokelon.toktales.android.render.opengl.gl20;

import java.util.HashMap;
import java.util.Map;

import com.tokelon.toktales.core.render.opengl.IGLFlags;
import com.tokelon.toktales.core.render.opengl.gl20.IGL15;

import android.opengl.GLES20;

abstract class AndroidGL15Flags implements IGLFlags {
	
	
	protected static final Map<Integer, Integer> GL15_FLAG_MAP = new HashMap<>();

	static {
	    /**
	     * Accepted by the {@code target} parameters of BindBuffer, BufferData, BufferSubData, MapBuffer, UnmapBuffer, GetBufferSubData,
	     * GetBufferParameteriv, and GetBufferPointerv.
	     */
		GL15_FLAG_MAP.put(IGL15.GL_ARRAY_BUFFER, GLES20.GL_ARRAY_BUFFER);
		GL15_FLAG_MAP.put(IGL15.GL_ELEMENT_ARRAY_BUFFER, GLES20.GL_ELEMENT_ARRAY_BUFFER);
	    /** Accepted by the {@code pname} parameter of GetBooleanv, GetIntegerv, GetFloatv, and GetDoublev. */
		GL15_FLAG_MAP.put(IGL15.GL_ARRAY_BUFFER_BINDING, GLES20.GL_ARRAY_BUFFER_BINDING);
		GL15_FLAG_MAP.put(IGL15.GL_ELEMENT_ARRAY_BUFFER_BINDING, GLES20.GL_ELEMENT_ARRAY_BUFFER_BINDING);
	    /** Accepted by the {@code pname} parameter of GetVertexAttribiv. */
		GL15_FLAG_MAP.put(IGL15.GL_VERTEX_ATTRIB_ARRAY_BUFFER_BINDING, GLES20.GL_VERTEX_ATTRIB_ARRAY_BUFFER_BINDING);
	    /** Accepted by the {@code usage} parameter of BufferData. */
		GL15_FLAG_MAP.put(IGL15.GL_STREAM_DRAW, GLES20.GL_STREAM_DRAW);
		GL15_FLAG_MAP.put(IGL15.GL_STATIC_DRAW, GLES20.GL_STATIC_DRAW);
		GL15_FLAG_MAP.put(IGL15.GL_DYNAMIC_DRAW, GLES20.GL_DYNAMIC_DRAW);
	    /** Accepted by the {@code pname} parameter of GetBufferParameteriv. */
		GL15_FLAG_MAP.put(IGL15.GL_BUFFER_SIZE, GLES20.GL_BUFFER_SIZE);
		GL15_FLAG_MAP.put(IGL15.GL_BUFFER_USAGE, GLES20.GL_BUFFER_USAGE);
	}
	
}
