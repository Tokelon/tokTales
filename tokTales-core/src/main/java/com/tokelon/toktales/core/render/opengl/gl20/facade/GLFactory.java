package com.tokelon.toktales.core.render.opengl.gl20.facade;

import javax.inject.Inject;
import javax.inject.Provider;

import com.tokelon.toktales.core.render.opengl.OpenGLException;
import com.tokelon.toktales.core.render.opengl.gl20.IGL11;
import com.tokelon.toktales.core.render.opengl.gl20.IGL13;
import com.tokelon.toktales.core.render.opengl.gl20.IGL14;
import com.tokelon.toktales.core.render.opengl.gl20.IGL15;
import com.tokelon.toktales.core.render.opengl.gl20.IGL20;
import com.tokelon.toktales.core.render.opengl.gl20.facade.IGLFacade.ShaderType;

public class GLFactory implements IGLFactory {
	
	
	private final Provider<IGLProgram> glProgramProvider;
	private final Provider<IGLShader> glShaderProvider;
	private final Provider<IGL11> gl11Provider;
	private final Provider<IGL13> gl13Provider;
	private final Provider<IGL14> gl14Provider;
	private final Provider<IGL15> gl15Provider;
	private final Provider<IGL20> gl20Provider;

	@Inject
	public GLFactory(
			Provider<IGLProgram> glProgramProvider,
			Provider<IGLShader> glShaderProvider,
			Provider<IGL11> gl11Provider,
			Provider<IGL13> gl13Provider,
			Provider<IGL14> gl14Provider,
			Provider<IGL15> gl15Provider,
			Provider<IGL20> gl20Provider
	) {
		this.glProgramProvider = glProgramProvider;
		this.glShaderProvider = glShaderProvider;
		this.gl11Provider = gl11Provider;
		this.gl13Provider = gl13Provider;
		this.gl14Provider = gl14Provider;
		this.gl15Provider = gl15Provider;
		this.gl20Provider = gl20Provider;
	}
	

	@Override
	public IGL11 createGL11() {
		return gl11Provider.get();
	}

	@Override
	public IGL13 createGL13() {
		return gl13Provider.get();
	}
	
	@Override
	public IGL14 createGL14() {
		return gl14Provider.get();
	}
	
	@Override
	public IGL15 createGL15() {
		return gl15Provider.get();
	}
	
	@Override
	public IGL20 createGL20() {
		return gl20Provider.get();
	}
	
	
	@Override
	public IGLProgram createProgram(String vertexShaderCode, String fragmentShaderCode) throws OpenGLException {
		return createProgram(new GLStringScript(vertexShaderCode), new GLStringScript(fragmentShaderCode));
	}
	
	@Override
	public IGLProgram createProgram(IGLScript vertexShaderScript, IGLScript fragmentShaderScript) throws OpenGLException {
		IGLProgram glProgram = glProgramProvider.get();
		glProgram.create();
		
		IGLShader vertexShader = createShader(vertexShaderScript, ShaderType.VERTEX_SHADER);
		glProgram.attachShader(vertexShader);
		
		IGLShader fragmentShader = createShader(fragmentShaderScript, ShaderType.FRAGMENT_SHADER);
		glProgram.attachShader(fragmentShader);
		
		
		glProgram.link();
		glProgram.validate();
		
		return glProgram;
	}


	@Override
	public IGLShader createShader(String shaderCode, ShaderType type) throws OpenGLException {
		return createShader(new GLStringScript(shaderCode), type);
	}

	@Override
	public IGLShader createShader(IGLScript shaderScript, ShaderType type) throws OpenGLException {
		IGLShader glShader = glShaderProvider.get();
		glShader.create(type);
		glShader.setSource(shaderScript);
		glShader.compile();
		
		return glShader;
	}

}
