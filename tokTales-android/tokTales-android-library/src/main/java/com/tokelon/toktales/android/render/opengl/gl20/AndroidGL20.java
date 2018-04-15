package com.tokelon.toktales.android.render.opengl.gl20;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import com.tokelon.toktales.core.render.opengl.gl20.IGL20;

import android.opengl.GLES20;

/** Android OGL2 v2.0 implementation of OpenGL CompatWrapper.
 * <p>
 * Note that for Android, values of large size types (double, long), may be cast down for specific functions.
 */
public class AndroidGL20 implements IGL20 {


	@Override
	public int glCreateProgram() {
		return GLES20.glCreateProgram();
	}

	@Override
	public void glDeleteProgram(int program) {
		GLES20.glDeleteProgram(program);
	}

	@Override
	public boolean glIsProgram(int program) {
		return GLES20.glIsProgram(program);
	}

	@Override
	public int glCreateShader(int type) {
		return GLES20.glCreateShader(type);
	}

	@Override
	public void glDeleteShader(int shader) {
		GLES20.glDeleteShader(shader);
	}

	@Override
	public boolean glIsShader(int shader) {
		return GLES20.glIsShader(shader);
	}

	@Override
	public void glAttachShader(int program, int shader) {
		GLES20.glAttachShader(program, shader);
	}

	@Override
	public void glDetachShader(int program, int shader) {
		GLES20.glDetachShader(program, shader);
	}

	@Override
	public void glShaderSource(int shader, CharSequence string) {
		GLES20.glShaderSource(shader, string.toString());
	}

	@Override
	public void glCompileShader(int shader) {
		GLES20.glCompileShader(shader);
	}

	@Override
	public void glLinkProgram(int program) {
		GLES20.glLinkProgram(program);
	}

	@Override
	public void glUseProgram(int program) {
		GLES20.glUseProgram(program);
	}

	@Override
	public void glValidateProgram(int program) {
		GLES20.glValidateProgram(program);
	}

	@Override
	public void glUniform1f(int location, float v0) {
		GLES20.glUniform1f(location, v0);
	}

	@Override
	public void glUniform2f(int location, float v0, float v1) {
		GLES20.glUniform2f(location, v0, v1);
	}

	@Override
	public void glUniform3f(int location, float v0, float v1, float v2) {
		GLES20.glUniform3f(location, v0, v1, v2);
	}

	@Override
	public void glUniform4f(int location, float v0, float v1, float v2, float v3) {
		GLES20.glUniform4f(location, v0, v1, v2, v3);
	}

	@Override
	public void glUniform1i(int location, int v0) {
		GLES20.glUniform1i(location, v0);
	}

	@Override
	public void glUniform2i(int location, int v0, int v1) {
		GLES20.glUniform2i(location, v0, v1);
	}

	@Override
	public void glUniform3i(int location, int v0, int v1, int v2) {
		GLES20.glUniform3i(location, v0, v1, v2);
	}

	@Override
	public void glUniform4i(int location, int v0, int v1, int v2, int v3) {
		GLES20.glUniform4i(location, v0, v1, v2, v3);
	}

	@Override
	public void glUniform1fv(int location, FloatBuffer value) {
		GLES20.glUniform1fv(location, value.remaining(), value);
	}

	@Override
	public void glUniform2fv(int location, FloatBuffer value) {
		GLES20.glUniform2fv(location, value.remaining() / 2, value);
	}

	@Override
	public void glUniform3fv(int location, FloatBuffer value) {
		GLES20.glUniform3fv(location, value.remaining() / 3, value);
	}

	@Override
	public void glUniform4fv(int location, FloatBuffer value) {
		GLES20.glUniform4fv(location, value.remaining() / 4, value);
	}

	@Override
	public void glUniform1iv(int location, IntBuffer value) {
		GLES20.glUniform1iv(location, value.remaining(), value);
	}

	@Override
	public void glUniform2iv(int location, IntBuffer value) {
		GLES20.glUniform2iv(location, value.remaining() / 2, value);
	}

	@Override
	public void glUniform3iv(int location, IntBuffer value) {
		GLES20.glUniform3iv(location, value.remaining() / 3, value);
	}

	@Override
	public void glUniform4iv(int location, IntBuffer value) {
		GLES20.glUniform4iv(location, value.remaining() / 4, value);
	}

	@Override
	public void glUniformMatrix2fv(int location, boolean transpose, FloatBuffer value) {
		GLES20.glUniformMatrix2fv(location, value.remaining() / 4, transpose, value);
	}

	@Override
	public void glUniformMatrix3fv(int location, boolean transpose, FloatBuffer value) {
		GLES20.glUniformMatrix3fv(location, value.remaining() / 9, transpose, value);
	}

	@Override
	public void glUniformMatrix4fv(int location, boolean transpose, FloatBuffer value) {
		GLES20.glUniformMatrix4fv(location, value.remaining() / 16, transpose, value);
	}

	@Override
	public void glGetShaderiv(int shader, int pname, IntBuffer params) {
		GLES20.glGetShaderiv(shader, pname, params);
	}
	
	@Override
	public int glGetShaderi(int shader, int pname) {
		int[] params = new int[1];
		GLES20.glGetShaderiv(shader, pname, params, 0);
		return params[0];
	}

	@Override
	public void glGetProgramiv(int program, int pname, IntBuffer params) {
		GLES20.glGetProgramiv(program, pname, params);
	}
	
	@Override
	public int glGetProgrami(int program, int pname) {
		int[] params = new int[1];
		GLES20.glGetProgramiv(program, pname, params, 0);
		return params[0];
	}

	@Override
	public String glGetShaderInfoLog(int shader) {
		return GLES20.glGetShaderInfoLog(shader);
	}

	@Override
	public String glGetProgramInfoLog(int program) {
		return GLES20.glGetProgramInfoLog(program);
	}

	@Override
	public void glGetAttachedShaders(int program, IntBuffer count, IntBuffer shaders) {
		GLES20.glGetAttachedShaders(program, count.remaining(), count, shaders);
	}

	@Override
	public int glGetUniformLocation(int program, CharSequence name) {
		return GLES20.glGetUniformLocation(program, name.toString());
	}
	
	@Override
	public String glGetActiveUniform(int program, int index, IntBuffer size, IntBuffer type) {
		return GLES20.glGetActiveUniform(program, index, size, type);
	}

	@Override
	public void glGetUniformfv(int program, int location, FloatBuffer params) {
		GLES20.glGetUniformfv(program, location, params);
	}
	
	@Override
	public float glGetUniformf(int program, int location) {
		float[] params = new float[1];
		GLES20.glGetUniformfv(program, location, params, 0);
		return params[0];
	}

	@Override
	public void glGetUniformiv(int program, int location, IntBuffer params) {
		GLES20.glGetUniformiv(program, location, params);
	}
	
	@Override
	public int glGetUniformi(int program, int location) {
		int[] params = new int[1];
		GLES20.glGetUniformiv(program, location, params, 0);
		return params[0];
	}

	@Override
	public String glGetShaderSource(int shader) {
		return GLES20.glGetShaderSource(shader);
	}

	@Override
	public void glVertexAttrib1f(int index, float v0) {
		GLES20.glVertexAttrib1f(index, v0);
	}

	@Override
	public void glVertexAttrib2f(int index, float v0, float v1) {
		GLES20.glVertexAttrib2f(index, v0, v1);
	}


	@Override
	public void glVertexAttrib3f(int index, float v0, float v1, float v2) {
		GLES20.glVertexAttrib3f(index, v0, v1, v2);
	}

	@Override
	public void glVertexAttrib4f(int index, float v0, float v1, float v2, float v3) {
		GLES20.glVertexAttrib4f(index, v0, v1, v2, v3);
	}

	@Override
	public void glVertexAttrib1fv(int index, FloatBuffer v) {
		GLES20.glVertexAttrib1fv(index, v);
	}

	@Override
	public void glVertexAttrib2fv(int index, FloatBuffer v) {
		GLES20.glVertexAttrib2fv(index, v);
	}

	@Override
	public void glVertexAttrib3fv(int index, FloatBuffer v) {
		GLES20.glVertexAttrib3fv(index, v);
	}

	@Override
	public void glVertexAttrib4fv(int index, FloatBuffer v) {
		GLES20.glVertexAttrib4fv(index, v);
	}

	@Override
	public void glVertexAttribPointer(int index, int size, int type, boolean normalized, int stride, ByteBuffer pointer) {
		GLES20.glVertexAttribPointer(index, size, type, normalized, stride, pointer);
	}

	@Override
	public void glVertexAttribPointer(int index, int size, int type, boolean normalized, int stride, ShortBuffer pointer) {
		GLES20.glVertexAttribPointer(index, size, type, normalized, stride, pointer);
	}
	
	@Override
	public void glVertexAttribPointer(int index, int size, int type, boolean normalized, int stride, IntBuffer pointer) {
		GLES20.glVertexAttribPointer(index, size, type, normalized, stride, pointer);
	}
	
	@Override
	public void glVertexAttribPointer(int index, int size, int type, boolean normalized, int stride, FloatBuffer pointer) {
		GLES20.glVertexAttribPointer(index, size, type, normalized, stride, pointer);		
	}
	
	@Override
	public void glEnableVertexAttribArray(int index) {
		GLES20.glEnableVertexAttribArray(index);
	}

	@Override
	public void glDisableVertexAttribArray(int index) {
		GLES20.glDisableVertexAttribArray(index);
	}

	@Override
	public void glBindAttribLocation(int program, int index, CharSequence name) {
		GLES20.glBindAttribLocation(program, index, name.toString());
	}

	@Override
	public String glGetActiveAttrib(int program, int index, IntBuffer size, IntBuffer type) {
		return GLES20.glGetActiveAttrib(program, index, size, type);
	}

	@Override
	public int glGetAttribLocation(int program, CharSequence name) {
		return GLES20.glGetAttribLocation(program, name.toString());
	}

	@Override
	public void glGetVertexAttribiv(int index, int pname, IntBuffer params) {
		GLES20.glGetVertexAttribiv(index, pname, params);
	}
	
	@Override
	public int glGetVertexAttribi(int index, int pname) {
		int[] params = new int[1];
		GLES20.glGetVertexAttribiv(index, pname, params, 0);
		return params[0];
	}

	@Override
	public void glGetVertexAttribfv(int index, int pname, FloatBuffer params) {
		GLES20.glGetVertexAttribfv(index, pname, params);
	}

	@Override
	public void glBlendEquationSeparate(int modeRGB, int modeAlpha) {
		GLES20.glBlendEquationSeparate(modeRGB, modeAlpha);
	}

	@Override
	public void glStencilOpSeparate(int face, int sfail, int dpfail, int dppass) {
		GLES20.glStencilOpSeparate(face, sfail, dpfail, dppass);
	}

	@Override
	public void glStencilFuncSeparate(int face, int func, int ref, int mask) {
		GLES20.glStencilFuncSeparate(face, func, ref, mask);
	}

	@Override
	public void glStencilMaskSeparate(int face, int mask) {
		GLES20.glStencilMaskSeparate(face, mask);
	}

	@Override
	public void glUniform1fv(int location, float[] value) {
		GLES20.glUniform1fv(location, value.length, value, 0);
	}

	@Override
	public void glUniform2fv(int location, float[] value) {
		GLES20.glUniform2fv(location, value.length, value, 0);
	}

	@Override
	public void glUniform3fv(int location, float[] value) {
		GLES20.glUniform3fv(location, value.length, value, 0);
	}

	@Override
	public void glUniform4fv(int location, float[] value) {
		GLES20.glUniform4fv(location, value.length, value, 0);
	}

	@Override
	public void glUniform1iv(int location, int[] value) {
		GLES20.glUniform1iv(location, value.length, value, 0);
	}

	@Override
	public void glUniform2iv(int location, int[] value) {
		GLES20.glUniform2iv(location, value.length, value, 0);
	}

	@Override
	public void glUniform3iv(int location, int[] value) {
		GLES20.glUniform3iv(location, value.length, value, 0);
	}

	@Override
	public void glUniform4iv(int location, int[] value) {
		GLES20.glUniform4iv(location, value.length, value, 0);
	}

	@Override
	public void glUniformMatrix2fv(int location, boolean transpose, float[] value) {
		GLES20.glUniformMatrix2fv(location, value.length, transpose, value, 0);
	}

	@Override
	public void glUniformMatrix3fv(int location, boolean transpose, float[] value) {
		GLES20.glUniformMatrix3fv(location, value.length, transpose, value, 0);
	}

	@Override
	public void glUniformMatrix4fv(int location, boolean transpose, float[] value) {
		GLES20.glUniformMatrix4fv(location, value.length, transpose, value, 0);
	}

	@Override
	public void glGetShaderiv(int shader, int pname, int[] params) {
		GLES20.glGetShaderiv(shader, pname, params, 0);
	}

	@Override
	public void glGetProgramiv(int program, int pname, int[] params) {
		GLES20.glGetProgramiv(program, pname, params, 0);
	}

	@Override
	public void glGetAttachedShaders(int program, int[] count, int[] shaders) {
		GLES20.glGetAttachedShaders(program, count.length, count, 0, shaders, 0);
	}

	@Override
	public void glGetUniformfv(int program, int location, float[] params) {
		GLES20.glGetUniformfv(program, location, params, 0);
	}

	@Override
	public void glGetUniformiv(int program, int location, int[] params) {
		GLES20.glGetUniformiv(program, location, params, 0);
	}

	@Override
	public void glVertexAttrib1fv(int index, float[] v) {
		GLES20.glVertexAttrib1fv(index, v, 0);
	}

	@Override
	public void glVertexAttrib2fv(int index, float[] v) {
		GLES20.glVertexAttrib2fv(index, v, 0);
	}

	@Override
	public void glVertexAttrib3fv(int index, float[] v) {
		GLES20.glVertexAttrib3fv(index, v, 0);
	}

	@Override
	public void glVertexAttrib4fv(int index, float[] v) {
		GLES20.glVertexAttrib4fv(index, v, 0);
	}

	@Override
	public void glGetVertexAttribiv(int index, int pname, int[] params) {
		GLES20.glGetVertexAttribiv(index, pname, params, 0);
	}

	@Override
	public void glGetVertexAttribfv(int index, int pname, float[] params) {
		GLES20.glGetVertexAttribfv(index, pname, params, 0);
	}

}
