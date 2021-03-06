package com.tokelon.toktales.android.render.opengl.gl20;

import com.tokelon.toktales.core.render.opengl.IGLFlags;
import com.tokelon.toktales.core.render.opengl.gl20.IGL20;

import android.opengl.GLES20;
import gnu.trove.map.hash.TIntIntHashMap;

abstract class AndroidGL20Flags implements IGLFlags {

	
	protected static final TIntIntHashMap GL20_FLAG_MAP = new TIntIntHashMap(100, 1f, Integer.MAX_VALUE, Integer.MIN_VALUE);

	static {
		/** Accepted by the {@code name} parameter of GetString. */
		GL20_FLAG_MAP.put(IGL20.GL_SHADING_LANGUAGE_VERSION, GLES20.GL_SHADING_LANGUAGE_VERSION);
		/** Accepted by the {@code pname} parameter of GetInteger. */
		GL20_FLAG_MAP.put(IGL20.GL_CURRENT_PROGRAM, GLES20.GL_CURRENT_PROGRAM);
		/** Accepted by the {@code pname} parameter of GetShaderiv. */
		GL20_FLAG_MAP.put(IGL20.GL_SHADER_TYPE, GLES20.GL_SHADER_TYPE);
		GL20_FLAG_MAP.put(IGL20.GL_DELETE_STATUS, GLES20.GL_DELETE_STATUS);
		GL20_FLAG_MAP.put(IGL20.GL_COMPILE_STATUS, GLES20.GL_COMPILE_STATUS);
		GL20_FLAG_MAP.put(IGL20.GL_LINK_STATUS, GLES20.GL_LINK_STATUS);
		GL20_FLAG_MAP.put(IGL20.GL_VALIDATE_STATUS, GLES20.GL_VALIDATE_STATUS);
		GL20_FLAG_MAP.put(IGL20.GL_INFO_LOG_LENGTH, GLES20.GL_INFO_LOG_LENGTH);
		GL20_FLAG_MAP.put(IGL20.GL_ATTACHED_SHADERS, GLES20.GL_ATTACHED_SHADERS);
		GL20_FLAG_MAP.put(IGL20.GL_ACTIVE_UNIFORMS, GLES20.GL_ACTIVE_UNIFORMS);
		GL20_FLAG_MAP.put(IGL20.GL_ACTIVE_UNIFORM_MAX_LENGTH, GLES20.GL_ACTIVE_UNIFORM_MAX_LENGTH);
		GL20_FLAG_MAP.put(IGL20.GL_ACTIVE_ATTRIBUTES, GLES20.GL_ACTIVE_ATTRIBUTES);
		GL20_FLAG_MAP.put(IGL20.GL_ACTIVE_ATTRIBUTE_MAX_LENGTH, GLES20.GL_ACTIVE_ATTRIBUTE_MAX_LENGTH);
		GL20_FLAG_MAP.put(IGL20.GL_SHADER_SOURCE_LENGTH, GLES20.GL_SHADER_SOURCE_LENGTH);
		/** Returned by the {@code type} parameter of GetActiveUniform. */
		GL20_FLAG_MAP.put(IGL20.GL_FLOAT_VEC2, GLES20.GL_FLOAT_VEC2);
		GL20_FLAG_MAP.put(IGL20.GL_FLOAT_VEC3, GLES20.GL_FLOAT_VEC3);
		GL20_FLAG_MAP.put(IGL20.GL_FLOAT_VEC4, GLES20.GL_FLOAT_VEC4);
		GL20_FLAG_MAP.put(IGL20.GL_INT_VEC2, GLES20.GL_INT_VEC2);
		GL20_FLAG_MAP.put(IGL20.GL_INT_VEC3, GLES20.GL_INT_VEC3);
		GL20_FLAG_MAP.put(IGL20.GL_INT_VEC4, GLES20.GL_INT_VEC4);
		GL20_FLAG_MAP.put(IGL20.GL_BOOL, GLES20.GL_BOOL);
		GL20_FLAG_MAP.put(IGL20.GL_BOOL_VEC2, GLES20.GL_BOOL_VEC2);
		GL20_FLAG_MAP.put(IGL20.GL_BOOL_VEC3, GLES20.GL_BOOL_VEC3);
		GL20_FLAG_MAP.put(IGL20.GL_BOOL_VEC4, GLES20.GL_BOOL_VEC4);
		GL20_FLAG_MAP.put(IGL20.GL_FLOAT_MAT2, GLES20.GL_FLOAT_MAT2);
		GL20_FLAG_MAP.put(IGL20.GL_FLOAT_MAT3, GLES20.GL_FLOAT_MAT3);
		GL20_FLAG_MAP.put(IGL20.GL_FLOAT_MAT4, GLES20.GL_FLOAT_MAT4);
		GL20_FLAG_MAP.put(IGL20.GL_SAMPLER_2D, GLES20.GL_SAMPLER_2D);
		GL20_FLAG_MAP.put(IGL20.GL_SAMPLER_CUBE, GLES20.GL_SAMPLER_CUBE);
		/** Accepted by the {@code type} argument of CreateShader and returned by the {@code params} parameter of GetShaderiv. */
		GL20_FLAG_MAP.put(IGL20.GL_VERTEX_SHADER, GLES20.GL_VERTEX_SHADER);
		/** Accepted by the {@code pname} parameter of GetBooleanv, GetIntegerv, GetFloatv, and GetDoublev. */
		GL20_FLAG_MAP.put(IGL20.GL_MAX_VERTEX_ATTRIBS, GLES20.GL_MAX_VERTEX_ATTRIBS);
		GL20_FLAG_MAP.put(IGL20.GL_MAX_TEXTURE_IMAGE_UNITS, GLES20.GL_MAX_TEXTURE_IMAGE_UNITS);
		GL20_FLAG_MAP.put(IGL20.GL_MAX_VERTEX_TEXTURE_IMAGE_UNITS, GLES20.GL_MAX_VERTEX_TEXTURE_IMAGE_UNITS);
		GL20_FLAG_MAP.put(IGL20.GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS, GLES20.GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS);
		/** Accepted by the {@code pname} parameter of GetVertexAttrib{dfi}v. */
		GL20_FLAG_MAP.put(IGL20.GL_VERTEX_ATTRIB_ARRAY_ENABLED, GLES20.GL_VERTEX_ATTRIB_ARRAY_ENABLED);
		GL20_FLAG_MAP.put(IGL20.GL_VERTEX_ATTRIB_ARRAY_SIZE, GLES20.GL_VERTEX_ATTRIB_ARRAY_SIZE);
		GL20_FLAG_MAP.put(IGL20.GL_VERTEX_ATTRIB_ARRAY_STRIDE, GLES20.GL_VERTEX_ATTRIB_ARRAY_STRIDE);
		GL20_FLAG_MAP.put(IGL20.GL_VERTEX_ATTRIB_ARRAY_TYPE, GLES20.GL_VERTEX_ATTRIB_ARRAY_TYPE);
		GL20_FLAG_MAP.put(IGL20.GL_VERTEX_ATTRIB_ARRAY_NORMALIZED, GLES20.GL_VERTEX_ATTRIB_ARRAY_NORMALIZED);
		GL20_FLAG_MAP.put(IGL20.GL_CURRENT_VERTEX_ATTRIB, GLES20.GL_CURRENT_VERTEX_ATTRIB);
		/** Accepted by the {@code pname} parameter of GetVertexAttribPointerv. */
		GL20_FLAG_MAP.put(IGL20.GL_VERTEX_ATTRIB_ARRAY_POINTER, GLES20.GL_VERTEX_ATTRIB_ARRAY_POINTER);
		/** Accepted by the {@code type} argument of CreateShader and returned by the {@code params} parameter of GetShaderiv. */
		GL20_FLAG_MAP.put(IGL20.GL_FRAGMENT_SHADER, GLES20.GL_FRAGMENT_SHADER);
		/** Accepted by the {@code pname} parameter of GetBooleanv, GetIntegerv, GetFloatv, and GetDoublev. */
		GL20_FLAG_MAP.put(IGL20.GL_BLEND_EQUATION_RGB, GLES20.GL_BLEND_EQUATION_RGB);
		GL20_FLAG_MAP.put(IGL20.GL_BLEND_EQUATION_ALPHA, GLES20.GL_BLEND_EQUATION_ALPHA);
		/** Accepted by the {@code pname} parameter of GetIntegerv. */
		GL20_FLAG_MAP.put(IGL20.GL_STENCIL_BACK_FUNC, GLES20.GL_STENCIL_BACK_FUNC);
		GL20_FLAG_MAP.put(IGL20.GL_STENCIL_BACK_FAIL, GLES20.GL_STENCIL_BACK_FAIL);
		GL20_FLAG_MAP.put(IGL20.GL_STENCIL_BACK_PASS_DEPTH_FAIL, GLES20.GL_STENCIL_BACK_PASS_DEPTH_FAIL);
		GL20_FLAG_MAP.put(IGL20.GL_STENCIL_BACK_PASS_DEPTH_PASS, GLES20.GL_STENCIL_BACK_PASS_DEPTH_PASS);
		GL20_FLAG_MAP.put(IGL20.GL_STENCIL_BACK_REF, GLES20.GL_STENCIL_BACK_REF);
		GL20_FLAG_MAP.put(IGL20.GL_STENCIL_BACK_VALUE_MASK, GLES20.GL_STENCIL_BACK_VALUE_MASK);
		GL20_FLAG_MAP.put(IGL20.GL_STENCIL_BACK_WRITEMASK, GLES20.GL_STENCIL_BACK_WRITEMASK);
	}
	
}
