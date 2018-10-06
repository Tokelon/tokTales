package com.tokelon.toktales.core.render.opengl.gl20;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.render.opengl.GLErrorCheckingEnabled;
import com.tokelon.toktales.core.render.opengl.IGLErrorUtils;
import com.tokelon.toktales.core.render.opengl.OpenGLErrorException;

public class GLErrorUtils implements IGLErrorUtils {
	
	public static final String TAG = "GLErrorUtils";

	
	private static final List<IGLError> emptyErrorList = Collections.unmodifiableList(new ArrayList<>());
	
	private boolean enableErrorChecking = false; // Implementation default must be false
	
	private final ILogger logger;
	private final IGL11 gl11;

	@Inject
	public GLErrorUtils(ILogger logger, IGL11 gl11, @GLErrorCheckingEnabled boolean enabled) {
		this.logger = logger;
		this.gl11 = gl11;
		
		this.enableErrorChecking = enabled;
	}
	
	
	@Override
	public void enableErrorChecking(boolean enabled) {
		this.enableErrorChecking = enabled;
	}
	
	@Override
	public boolean isErrorCheckingEnabled() {
		return enableErrorChecking;
	}
	

	@Override
	public void assertNoGLErrors() throws OpenGLErrorException {
		if(!enableErrorChecking) {
			return;
		}
		
		List<IGLError> errorList = getGLErrors();
		if(errorList.size() > 0) {
			throw new OpenGLErrorException(errorList);
		}
	}
	
	@Override
	public int logGLErrors(String context) {
		if(!enableErrorChecking) {
			return 0;
		}
		
		StringBuilder strBuilder = null;
		
		int lastError, errorCount = 0;
		while((lastError = gl11.glGetError()) != IGL11.GL_NO_ERROR) {
			// Initialize
			if(errorCount == 0) {
				strBuilder = new StringBuilder();
				strBuilder.append("Getting GL errors | Context: " + context + "\n");
			}
			
			strBuilder.append(createGLErrorMessage(lastError));
			strBuilder.append("\n");
			errorCount++;
		}
		
		if(errorCount > 0) {
			strBuilder.append("Stacktrace:\n");

			StringWriter sw = new StringWriter();
			new Throwable().printStackTrace(new PrintWriter(sw));
			
			strBuilder.append(sw.toString());
			logger.e(TAG, strBuilder.toString());
		}
		
		
		return errorCount;
	}

	@Override
	public List<IGLError> getGLErrors() {
		if(!enableErrorChecking) {
			return emptyErrorList;
		}
		
		ArrayList<IGLError> result = null;
		
		int lastError;
		while((lastError = gl11.glGetError()) != IGL11.GL_NO_ERROR) {
			String errorMessage = createGLErrorMessage(lastError);
			
			if(result == null) {
				result = new ArrayList<>();
			}
			result.add(new GLError(lastError, errorMessage));
		}
		
		return result == null ? emptyErrorList : result;
	}
	
	
	private static String createGLErrorMessage(int glErrorCode) {
		String name;
		switch (glErrorCode) {
		case IGL11.GL_INVALID_ENUM:
			name = "GL_INVALID_ENUM";
			break;
		case IGL11.GL_INVALID_VALUE:
			name = "GL_INVALID_VALUE";
			break;
		case IGL11.GL_INVALID_OPERATION:
			name = "GL_INVALID_OPERATION";
			break;
		case IGL11.GL_OUT_OF_MEMORY:
			name = "GL_OUT_OF_MEMORY";
			break;
		default:
			name = "ERROR CODE";
			break;
		}
		
		return String.format("%s (%d)", name, glErrorCode);
	}
	
	
	
	private static class GLError implements IGLError {
		private final int errorCode;
		private final String errorMessage;
		
		public GLError(int errorCode, String errorMessage) {
			this.errorCode = errorCode;
			this.errorMessage = errorMessage;
		}
		
		@Override
		public int getErrorCode() {
			return errorCode;
		}

		@Override
		public String getErrorMessage() {
			return errorMessage;
		}
	}

}
