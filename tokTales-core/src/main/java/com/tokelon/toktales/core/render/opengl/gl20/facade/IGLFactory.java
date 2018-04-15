package com.tokelon.toktales.core.render.opengl.gl20.facade;

import com.tokelon.toktales.core.render.opengl.OpenGLException;
import com.tokelon.toktales.core.render.opengl.gl20.IGL11;
import com.tokelon.toktales.core.render.opengl.gl20.IGL13;
import com.tokelon.toktales.core.render.opengl.gl20.IGL14;
import com.tokelon.toktales.core.render.opengl.gl20.IGL15;
import com.tokelon.toktales.core.render.opengl.gl20.IGL20;
import com.tokelon.toktales.core.render.opengl.gl20.facade.IGLUtils.ShaderType;

public interface IGLFactory {


	public IGL11 createGL11();
	public IGL13 createGL13();
	public IGL14 createGL14();
	public IGL15 createGL15();
	public IGL20 createGL20();
	
	
	public IGLProgram createProgram(String vertexShaderCode, String fragmentShaderCode) throws OpenGLException;
	public IGLProgram createProgram(IGLScript vertexShaderScript, IGLScript fragmentShaderScript) throws OpenGLException;
	
	public IGLShader createShader(String shaderCode, ShaderType type) throws OpenGLException;
	public IGLShader createShader(IGLScript shaderScript, ShaderType type) throws OpenGLException;
	
}
