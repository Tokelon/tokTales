package com.tokelon.toktales.android.game.screen;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import org.joml.Matrix4f;

import com.tokelon.toktales.android.render.opengl.OpenGLException;
import com.tokelon.toktales.android.render.opengl.ShaderProgram;
import com.tokelon.toktales.android.render.tools.IButtonOverlayView;
import com.tokelon.toktales.android.render.tools.ICrossControlOverlayView;
import com.tokelon.toktales.android.render.tools.IUIOverlay;
import com.tokelon.toktales.android.render.tools.IUIOverlayProvider;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.log.ILogging;
import com.tokelon.toktales.core.game.screen.ISegmentRenderer;
import com.tokelon.toktales.core.game.screen.view.IViewTransformer;
import com.tokelon.toktales.core.render.RenderException;
import com.tokelon.toktales.tools.core.objects.options.INamedOptions;

import android.graphics.Color;
import android.graphics.Rect;
import android.opengl.GLES20;

public class UIRenderer implements ISegmentRenderer {


	private static final String COLOR_S = "#077570";
	private static final String COLOR_L = "#075075";
	private static final String COLOR_E = "#750735";
	private static final String COLOR_T = "#753307";
	private static final String COLOR_GN = "#077510";
	private static final String COLOR_RD = "#750707";
	private static final String COLOR_OR = "#FF6600";
	private static final String COLOR_BL = "#0066CC";
	
	
	
	private static final String VS_MVPPosition = 
			"uniform mat4 uMVPMatrix;" +
			"attribute vec4 vPosition;" +
			"void main() {" +
			"  gl_Position = uMVPMatrix * vPosition;" +
			"}";
	
	private static final String FS_VarColor = 
			"precision mediump float;" +
			"uniform vec4 vColor;" +
			"void main() {" +
			"  gl_FragColor = vColor;" +
			"}";
	
	
	
	private static final String VS_CircleOutlineColor =
			"uniform mat4 uMVPMatrix;" +
			"attribute vec4 vPosition;" +
			"varying vec2 vCoord;" +
			"void main() {" +
			"  gl_Position = uMVPMatrix * vPosition;" +
			"  vCoord = vec2(vPosition);" +
			"}";
	
	private static final String FS_CircleOutlineColor =
			"precision mediump float;" +
			"uniform vec4 vColor;" +
			"uniform vec3 vCenterAndRadius;" +
			"varying vec2 vCoord;" +
			"void main() {" +
			"  float dist = pow(vCenterAndRadius.z, 2.0) - (pow((vCoord.x - vCenterAndRadius.x), 2.0) + pow((vCoord.y - vCenterAndRadius.y), 2.0));" +
			"  if(dist >= 0.0 && dist <= 180.0) {" +	// 400.0 <- 2.47 * 162  // vCenterAndRadius.z * 1.5
			"    gl_FragColor = vColor;" +
			"  }" +
			"  else {" +
			"    gl_FragColor = vec4(vColor.r, vColor.g, vColor.b, 0.0);" +
			//"    gl_FragColor = vec4(0.8, 0.2, 0.8, 0.5);" +
			"  }" +
			"}";
	
	
	
	
	private ShaderProgram mVCShader;
	private ShaderProgram mCOCShader;
	
	
	private final Matrix4f matrixProjection = new Matrix4f();
	private final Matrix4f matrixView = new Matrix4f();
	private final Matrix4f matrixProjectionAndView = new Matrix4f();

	
	private FloatBuffer verticesBufferControlLeft;
	private FloatBuffer verticesBufferControlUp;
	private FloatBuffer verticesBufferControlRight;
	private FloatBuffer verticesBufferControlDown;
	
	private FloatBuffer verticesBufferButtonA;
	private FloatBuffer verticesBufferButtonB;
	private FloatBuffer verticesBufferButtonSP1;
	private FloatBuffer verticesBufferButtonSET;
	
	
	private final Rect rectControlLeft = new Rect();
	private final Rect rectControlUp = new Rect();
	private final Rect rectControlRight = new Rect();
	private final Rect rectControlDown = new Rect();
	
	private final Rect rectButtonA = new Rect();
	private final Rect rectButtonB = new Rect();
	private final Rect rectButtonSP1 = new Rect();
	private final Rect rectButtonSET = new Rect();
	
	
	private IViewTransformer viewTransformer;
	
	private final ILogger logger;
	private final IUIOverlayProvider mOverlayProvider;
	
	public UIRenderer(ILogging logging, IUIOverlayProvider overlayProvider) {
		this.logger = logging.getLogger(getClass());
		this.mOverlayProvider = overlayProvider;
	}
	
	
	@Override
	public void contextCreated() {

		// Do OpenGL program stuff
		
		
		//Create the shaders and programs
		
		try {
			mVCShader = new ShaderProgram();
			
			mVCShader.createVertexShader(VS_MVPPosition);
			mVCShader.createFragmentShader(FS_VarColor);
			
			mVCShader.link();
			
			mVCShader.createAttribute("vPosition");
			mVCShader.createUniform("vColor");
			mVCShader.createUniform("uMVPMatrix");

			
			
			mCOCShader = new ShaderProgram();
			
			mCOCShader.createVertexShader(VS_CircleOutlineColor);
			mCOCShader.createFragmentShader(FS_CircleOutlineColor);
			
			mCOCShader.link();
			
			mCOCShader.createAttribute("vPosition");
			mCOCShader.createUniform("vColor");
			mCOCShader.createUniform("vCenterAndRadius");
			mCOCShader.createUniform("uMVPMatrix");
			
		} catch(OpenGLException oglex) {
			logger.error("Failed to create shader program:", oglex);
			return;
		}
		
		
	}
	
	
	@Override
	public void contextChanged(IViewTransformer viewTransformer, Matrix4f projectionMatrix) {
		this.viewTransformer = viewTransformer;
		

		IUIOverlay overlay = mOverlayProvider.getUIOverlay();
		
		
		ICrossControlOverlayView crossControl = overlay.getCrossControlView();
		
		crossControl.getControlBounds(ICrossControlOverlayView.CROSS_CONTROL_LEFT, rectControlLeft);
		crossControl.getControlBounds(ICrossControlOverlayView.CROSS_CONTROL_UP, rectControlUp);
		crossControl.getControlBounds(ICrossControlOverlayView.CROSS_CONTROL_RIGHT, rectControlRight);
		crossControl.getControlBounds(ICrossControlOverlayView.CROSS_CONTROL_DOWN, rectControlDown);
		
		
		IButtonOverlayView buttonA = overlay.getButtonView(IUIOverlay.BUTTON_A);
		buttonA.getButtonBounds(rectButtonA);
		
		IButtonOverlayView buttonB = overlay.getButtonView(IUIOverlay.BUTTON_B);
		buttonB.getButtonBounds(rectButtonB);
		
		IButtonOverlayView buttonSP1 = overlay.getButtonView(IUIOverlay.BUTTON_SP1);
		buttonSP1.getButtonBounds(rectButtonSP1);
		
		IButtonOverlayView buttonSET = overlay.getButtonView(IUIOverlay.BUTTON_SET);
		buttonSET.getButtonBounds(rectButtonSET);
		
		
		// Control values
		
		float[] faControlLeft = new float[]
				{
					rectControlLeft.left, rectControlLeft.top, 0.0f,
					rectControlLeft.left, rectControlLeft.bottom, 0.0f,
					rectControlLeft.right, rectControlLeft.bottom, 0.0f,
					rectControlLeft.right, rectControlLeft.top, 0.0f,
				};
		
		verticesBufferControlLeft = floatValuesAsBuffer(faControlLeft);

		
		float[] faControlUp = new float[]
				{
					rectControlUp.left, rectControlUp.top, 0.0f,
					rectControlUp.left, rectControlUp.bottom, 0.0f,
					rectControlUp.right, rectControlUp.bottom, 0.0f,
					rectControlUp.right, rectControlUp.top, 0.0f,
				};
		
		verticesBufferControlUp = floatValuesAsBuffer(faControlUp);
		
		
		float[] faControlRight = new float[]
				{
					rectControlRight.left, rectControlRight.top, 0.0f,
					rectControlRight.left, rectControlRight.bottom, 0.0f,
					rectControlRight.right, rectControlRight.bottom, 0.0f,
					rectControlRight.right, rectControlRight.top, 0.0f,
				};
		
		verticesBufferControlRight = floatValuesAsBuffer(faControlRight);
		
		
		float[] faControlDown = new float[]
				{
					rectControlDown.left, rectControlDown.top, 0.0f,
					rectControlDown.left, rectControlDown.bottom, 0.0f,
					rectControlDown.right, rectControlDown.bottom, 0.0f,
					rectControlDown.right, rectControlDown.top, 0.0f,
				};
		
		verticesBufferControlDown = floatValuesAsBuffer(faControlDown);
		
		
		
		// Button values
		
		float[] faButtonA = new float[]
				{
					rectButtonA.left, rectButtonA.top, 0.0f,
					rectButtonA.left, rectButtonA.bottom, 0.0f,
					rectButtonA.right, rectButtonA.bottom, 0.0f,
					rectButtonA.right, rectButtonA.top, 0.0f,
				};
		verticesBufferButtonA = floatValuesAsBuffer(faButtonA);
		
		
		float[] faButtonB = new float[]
				{
					rectButtonB.left, rectButtonB.top, 0.0f,
					rectButtonB.left, rectButtonB.bottom, 0.0f,
					rectButtonB.right, rectButtonB.bottom, 0.0f,
					rectButtonB.right, rectButtonB.top, 0.0f,
				};
		verticesBufferButtonB = floatValuesAsBuffer(faButtonB);
		
		
		float[] faButtonSP1 = new float[]
				{
					rectButtonSP1.left, rectButtonSP1.top, 0.0f,
					rectButtonSP1.left, rectButtonSP1.bottom, 0.0f,
					rectButtonSP1.right, rectButtonSP1.bottom, 0.0f,
					rectButtonSP1.right, rectButtonSP1.top, 0.0f,
				};
		verticesBufferButtonSP1 = floatValuesAsBuffer(faButtonSP1);
		
		
		float[] faButtonSET = new float[]
				{
					rectButtonSET.left, rectButtonSET.top, 0.0f,
					rectButtonSET.left, rectButtonSET.bottom, 0.0f,
					rectButtonSET.right, rectButtonSET.bottom, 0.0f,
					rectButtonSET.right, rectButtonSET.top, 0.0f,
				};
		verticesBufferButtonSET = floatValuesAsBuffer(faButtonSET);
		
		
		
		matrixProjection.set(projectionMatrix);
		
		matrixView.lookAt(
				0.0f, 0.0f, 0.0f,
				0.0f, 0.0f, -1.0f,
				0.0f, 1.0f, 0.0f);

		matrixProjection.mul(matrixView, matrixProjectionAndView);
		
	}
	

	private static FloatBuffer floatValuesAsBuffer(float[] values) {
		
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(values.length * 4);
		byteBuffer.order(ByteOrder.nativeOrder());
		
		FloatBuffer floatBuffer = byteBuffer.asFloatBuffer();
		floatBuffer.put(values);
		floatBuffer.position(0);
		
		return floatBuffer;
	}
	
	
	@Override
	public void contextDestroyed() {
		mVCShader.cleanup();
		mVCShader = null;
		
		mCOCShader.cleanup();
		mCOCShader = null;
		
		viewTransformer = null;
	}
	
	
	

	
	@Override
	public void prepare(long currentTimeMillis) {
		// if !view
		
		// Nothing
	}
	
	@Override
	public void drawLayer(INamedOptions options, String layerName) {
		//assert false : "Not supported";
		throw new UnsupportedOperationException("Unsupported draw call. Use drawAll() for this renderer");
	}
	

	@Override
	public void drawFull(INamedOptions options) {
		if(viewTransformer == null) {
			//assert false : "Cannot draw without view";
			throw new RenderException("Cannot draw without view");
		}
		
		
		// Draw UI
		
		/* Draw cross control */
		
		mVCShader.bind();
		
		
		// Before drawing

		GLES20.glEnableVertexAttribArray(mVCShader.getAttributeLocation("vPosition"));
		
		mVCShader.setUniform("uMVPMatrix", matrixProjectionAndView);
		
		
		int colorLocationVC = mVCShader.getUniformLocation("vColor");
		
		
		// Drawing
		
		int colorControlLeft = Color.parseColor(COLOR_S);
		drawControl("vPosition", colorLocationVC, verticesBufferControlLeft, colorControlLeft);
		
		int colorControlUp = Color.parseColor(COLOR_L);
		drawControl("vPosition", colorLocationVC, verticesBufferControlUp, colorControlUp);
		
		int colorControlRight = Color.parseColor(COLOR_E);
		drawControl("vPosition", colorLocationVC, verticesBufferControlRight, colorControlRight);
		
		int colorControlDown = Color.parseColor(COLOR_T);
		drawControl("vPosition", colorLocationVC, verticesBufferControlDown, colorControlDown);
		

		// After drawing
		
		GLES20.glDisableVertexAttribArray(mVCShader.getAttributeLocation("vPosition"));
		
		
		
		
		/* Draw buttons */

		mCOCShader.bind();

		GLES20.glEnableVertexAttribArray(mCOCShader.getAttributeLocation("vPosition"));
		
		mCOCShader.setUniform("uMVPMatrix", matrixProjectionAndView);
		
		
		int colorLocationCOC = mCOCShader.getUniformLocation("vColor");
		int centerRadiusLocationCOC = mCOCShader.getUniformLocation("vCenterAndRadius");
		
		int colorButtonA = Color.parseColor(COLOR_GN);
		drawButton("vPosition", colorLocationCOC, centerRadiusLocationCOC, verticesBufferButtonA, colorButtonA, rectButtonA);
		
		int colorButtonB = Color.parseColor(COLOR_RD);
		drawButton("vPosition", colorLocationCOC, centerRadiusLocationCOC, verticesBufferButtonB, colorButtonB, rectButtonB);
		
		int colorButtonSP1 = Color.parseColor(COLOR_OR);
		drawButton("vPosition", colorLocationCOC, centerRadiusLocationCOC, verticesBufferButtonSP1, colorButtonSP1, rectButtonSP1);
		
		int colorButtonSET = Color.parseColor(COLOR_BL);
		drawButton("vPosition", colorLocationCOC, centerRadiusLocationCOC, verticesBufferButtonSET, colorButtonSET, rectButtonSET);
		
		
		GLES20.glDisableVertexAttribArray(mCOCShader.getAttributeLocation("vPosition"));
		
	}
	
	
	private void drawControl(String positionAttrName, int colorHdl, FloatBuffer verticesBuffer, int color) {
		
		mVCShader.setAttribute(positionAttrName, 3, verticesBuffer);
		
		GLES20.glUniform4f(colorHdl, Color.red(color) / 255.0f, Color.green(color) / 255.0f, Color.blue(color) / 255.0f, Color.alpha(color) / 255.0f);
		
		
		GLES20.glDrawArrays(GLES20.GL_LINE_LOOP, 0, 4);
		
	}
	
	
	private void drawButton(String positionAttrName, int colorHdl, int centerRadiusHdl, FloatBuffer verticesBuffer, int color, Rect buttonRect) {
		
		mCOCShader.setAttribute(positionAttrName, 3, verticesBuffer);
		
		GLES20.glUniform4f(colorHdl, Color.red(color) / 255.0f, Color.green(color) / 255.0f, Color.blue(color) / 255.0f, Color.alpha(color) / 255.0f);
		
		// Use exact values or not ?
		GLES20.glUniform3f(centerRadiusHdl, (float)buttonRect.centerX(), (float)buttonRect.centerY(), (float)(buttonRect.width() / 2));
		//GLES20.glUniform3f(centerRadiusHdl, buttonRect.exactCenterX(), buttonRect.exactCenterY(), buttonRect.width() / (float)2);
		
		
		GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 4);
		
	}

}
