package com.tokelon.toktales.android.render.opengl;

import java.nio.FloatBuffer;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import com.tokelon.toktales.android.render.opengl.program.OpenGLException;
import com.tokelon.toktales.android.render.opengl.program.ShaderProgram;
import com.tokelon.toktales.core.engine.TokTales;
import com.tokelon.toktales.core.render.IRenderDriver;
import com.tokelon.toktales.core.render.IRenderDriverFactory;
import com.tokelon.toktales.core.render.RenderException;
import com.tokelon.toktales.core.render.model.ILineModel;
import com.tokelon.toktales.core.render.model.IPointModel;
import com.tokelon.toktales.core.render.model.IRectangleModel;
import com.tokelon.toktales.core.render.model.IRenderModel;
import com.tokelon.toktales.core.render.model.ITriangleModel;
import com.tokelon.toktales.core.render.model.LineModel;
import com.tokelon.toktales.core.util.INamedOptions;
import com.tokelon.toktales.core.util.IParams;

import android.opengl.GLES20;

public class GLShapeDriver implements IRenderDriver {

	public static final String TAG = "GLShapeDriver";

	// TODO: Optimize all object creation (look for "new")

	
	private static final String VS_Shape = 
			"uniform mat4 uMVPMatrix;" +
			"uniform mat4 uModelMatrix;" +
			"attribute vec4 vPosition;" +
			"void main() {" +
			"  gl_Position = uMVPMatrix * uModelMatrix * vPosition;" +
			"}";


	private static final String FS_Shape = 
			"precision mediump float;" +
			"uniform vec4 colorOver;" +
			"void main() {" +
			"  gl_FragColor = colorOver;" +
			"}";
	
	
	private static final String MATRIX_MVP_NAME = "uMVPMatrix";
	private static final String MATRIX_MODEL_NAME = "uModelMatrix";
	private static final String COLOR_OVER_NAME = "colorOver";
	private static final String POSITION_NAME = "vPosition";
	
	private static final float Z_INDEX = -1.05f;	// TODO: Important - Fix static z
	

	private GLMeshlike lineMesh;
	private GLMeshlike pointMesh;
	private GLMeshlike triangleMesh;
	private GLMeshlike rectangleFillMesh;
	
	private final DrawLineStruct drawLineStruct = new DrawLineStruct();
	
	private final LineModel rectLineModel = new LineModel();
	
	private ShaderProgram mShader;
	
	
	@Inject
	public GLShapeDriver() {
		//lineNativeCoordinateBuffer = BufferUtils.createFloatBuffer(6);
		//lineCoordinateBuffer = BufferUtils.createFloatBuffer(12);
		//pointCoordinateBuffer = BufferUtils.createFloatBuffer(3);
		//triangleCoordinateBuffer = BufferUtils.createFloatBuffer(9);
		//rectangleCoordinateBuffer = BufferUtils.createFloatBuffer(26); // 12
		

		short[] lineIndices = new short[] {
			0, 1, 3, 3, 1, 2
		};
		
		lineMesh = new GLMeshlike(lineIndices, 12);
		
		
		short[] pointIndices = new short[] {
			0
		};
		
		pointMesh = new GLMeshlike(pointIndices, 3);
		
		
		short[] triangleIndices = new short[] {
			0, 1, 2	
		};
		
		triangleMesh = new GLMeshlike(triangleIndices, 9);
		
		

		short[] rectangleFillIndices = new short[] {
			0, 1, 3, 3, 1, 2,
		};
		
		rectangleFillMesh = new GLMeshlike(rectangleFillIndices, 26);
	}
	
	
	@Override
	public void create() {

		try {
			mShader = new ShaderProgram();
			
			mShader.createVertexShader(VS_Shape);
			mShader.createFragmentShader(FS_Shape);
			
			mShader.link();
			
			
			mShader.createUniform(MATRIX_MVP_NAME);
			mShader.createUniform(MATRIX_MODEL_NAME);
			mShader.createUniform(COLOR_OVER_NAME);
			
			mShader.createAttribute(POSITION_NAME);
			
		} catch (OpenGLException e) {
			TokTales.getLog().e(TAG, "Failed to create shader program: " +e.getMessage());
			return;
		}
	}

	@Override
	public void destroy() {
		mShader.cleanup();
		mShader = null;
	}
	

	@Override
	public void drawQuick(Matrix4f matrixProjectionView, IRenderModel renderModel, INamedOptions options) {
		use(matrixProjectionView);
		
		draw(renderModel, options);
		
		release();
	}

	
	@Override
	public void use(Matrix4f matrixProjectionView) {
		mShader.bind();
		
		mShader.setUniform(MATRIX_MVP_NAME, matrixProjectionView);
		
		GLES20.glEnableVertexAttribArray(mShader.getAttributeLocation(POSITION_NAME));

	}

	
	@Override
	public void draw(IRenderModel renderModel, INamedOptions options) {

		if(renderModel instanceof ILineModel) {
			ILineModel lineModel = (ILineModel) renderModel;
			drawLine(lineModel, options);
		}
		else if(renderModel instanceof IPointModel) {
			IPointModel pointModel = (IPointModel) renderModel;
			drawPoint(pointModel, options);
		}
		else if(renderModel instanceof ITriangleModel) {
			ITriangleModel triangleModel = (ITriangleModel) renderModel;
			drawTriangle(triangleModel, options);
		}
		else if(renderModel instanceof IRectangleModel) {
			IRectangleModel rectangleModel = (IRectangleModel) renderModel;
			drawRectangle(rectangleModel, options);
		}
		else {
			throw new RenderException("Unsupported model type: " + renderModel.getClass());
		}

	}
	
	
	
	private void drawLine(ILineModel lineModel, INamedOptions options) {
		
		Matrix4f modelMatrix = lineModel.applyModelMatrix();

		
		int lineWidth = (int)lineModel.getTargetWidth();
		float dPositive, dNegative;
		if(lineModel.getTargetAlignment() == ILineModel.ALIGNMENT_INNER) {
			dPositive = lineWidth;
			dNegative = 0f;
		}
		else if(lineModel.getTargetAlignment() == ILineModel.ALIGNMENT_OUTER) {
			dPositive = 1f;
			dNegative = lineWidth - 1f;
		}
		else {
			// When lineWidth=1 then always dPositive=1 (0/origin point)
			int prefered = (lineWidth / 2) + 1; 							// + (lineWidth % 2 == 0 ? 0 : 1);
			int notPrefered = lineWidth - prefered; 						//(lineWidth / 2);
			
			if(lineModel.getTargetCenterAlignment() == ILineModel.ALIGN_CENTER_OUT) {
				// When lineWidth=1 then the point should be on 0 relative (which is positive technically)
				dPositive = notPrefered + 1;
				dNegative = prefered - 1;
			}
			else {
				// When lineWidth = 1, prefered = 1 & notPrefered = 0
				dPositive = prefered;
				dNegative = notPrefered;
			}
		}
		
		float x1 = lineModel.getTargetX1();
		float y1 = lineModel.getTargetY1();
		float x2 = lineModel.getTargetX2();
		float y2 = lineModel.getTargetY2();
		

		DrawLineStruct drawLine = drawLineStruct;
		
		Vector2f a, b, c, d;
		if(Math.abs(x2 - x1) < 1.0f) {	// vertical line
			float smallerX = x1 < x2 ? x1 : x2;
			a = drawLine.a.set(smallerX - dNegative, y1);
			b = drawLine.b.set(smallerX + dPositive, y1);
			c = drawLine.c.set(smallerX - dNegative, y2);
			d = drawLine.d.set(smallerX + dPositive, y2);
		}
		else if(Math.abs(y2 - y1) < 1.0f) {	// horizontal line
			float smallerY = y1 < y2 ? y1 : y2;
			a = drawLine.a.set(x1, smallerY - dNegative);
			b = drawLine.b.set(x1, smallerY + dPositive);
			c = drawLine.c.set(x2, smallerY - dNegative);
			d = drawLine.d.set(x2, smallerY + dPositive);
		}
		else {

			// If one of the Xs if 0, then the calculation through b does not work and perp always equals 0,0
			float fixX = x1 == 0f || x2 == 0f ? 1f : 0f;
			x1 += fixX;
			x2 += fixX;
			
			
			// y = mx + b
			float lineM = (y2 - y1) / (x2 - x1);	// m = slope
			float lineB = y1 - (lineM * x1);		// b = y-intercept
			
			float perpM = - 1 / lineM;
			
			// perp = perpendicular
			float perp1B = y1 - (perpM * x1);					// Calculate b of perp1
			Vector2f perp1 = drawLine.perp1.set(x1 - 0, y1 - perp1B);	// Use b to find perp1
			float perp1Mag = perp1.length(); 					// mag = magnitude (vector)
			
			// U = normal of vector
			Vector2f perp1U = perp1.normalize(drawLine.perp1U); 
			
			// less = negative side
			Vector2f perp1less = perp1U.mul(perp1Mag - dNegative, drawLine.perp1less);
			a = perp1less.add(-fixX, perp1B, drawLine.a);
			
			// more = positive side
			Vector2f perp1more = perp1U.mul(perp1Mag + dPositive, drawLine.perp1more);
			b = perp1more.add(-fixX, perp1B, drawLine.b);
			
			
			// Same for the perp of point 2
			float perp2B = y2 - (perpM * x2);
			Vector2f perp2 = drawLine.perp2.set(x2 - 0, y2 - perp2B);
			float perp2Mag = perp2.length();
			
			Vector2f perp2U = perp2.normalize(drawLine.perp2U);
			
			Vector2f perp2less = perp2U.mul(perp2Mag - dNegative, drawLine.perp2less);
			c = perp2less.add(-fixX, perp2B, drawLine.c);
			
			Vector2f perp2more = perp2U.mul(perp2Mag + dPositive, drawLine.perp2more);
			d = perp2more.add(-fixX, perp2B, drawLine.d);
			
		}
		
		
		float[] coordinates = new float[] {
			a.x, a.y, Z_INDEX,
			c.x, c.y, Z_INDEX,
			d.x, d.y, Z_INDEX,
			b.x, b.y, Z_INDEX
		};
		
		FloatBuffer posBuffer = lineMesh.setPositions(coordinates);
		mShader.setAttribute(POSITION_NAME, 3, posBuffer);
		

		mShader.setUniform(MATRIX_MODEL_NAME, modelMatrix);
		mShader.setUniform(COLOR_OVER_NAME, lineModel.getTargetColor());
		

		//glDrawElements(GL_TRIANGLES, coordinates.length, GL_UNSIGNED_INT, 0);
		GLES20.glDrawElements(GLES20.GL_TRIANGLES, lineMesh.getIndicesCount(), GLES20.GL_UNSIGNED_SHORT, lineMesh.getIndices());
		
	}
	

	
	private void drawPoint(IPointModel pointModel, INamedOptions options) {

		Matrix4f modelMatrix = pointModel.applyModelMatrix();
		
		float[] coordinates = new float[] {
				pointModel.getTargetX() + 0.5f, pointModel.getTargetY() + 0.5f, Z_INDEX
		};
		
		FloatBuffer posBuffer = pointMesh.setPositions(coordinates);
		mShader.setAttribute(POSITION_NAME, 3, posBuffer);
		
		
		mShader.setUniform(MATRIX_MODEL_NAME, modelMatrix);
		mShader.setUniform(COLOR_OVER_NAME, pointModel.getTargetColor());
		
		
		//glPointSize(pointModel.getTargetSize());
		//glDrawElements(GL_POINTS, coordinates.length, GL_UNSIGNED_INT, 0);
		GLES20.glDrawElements(GLES20.GL_POINTS, pointMesh.getIndicesCount(), GLES20.GL_UNSIGNED_SHORT, pointMesh.getIndices());
	}
	
	
	private void drawTriangle(ITriangleModel triangleModel, INamedOptions options) {
		
		Matrix4f modelMatrix = triangleModel.applyModelMatrix();
		
		
		// TODO: Fix y and x placement ?
		float[] coordinates = new float[] {
				triangleModel.getTargetX1(), triangleModel.getTargetY1(), Z_INDEX,
				triangleModel.getTargetX2(), triangleModel.getTargetY2(), Z_INDEX,
				triangleModel.getTargetX3(), triangleModel.getTargetY3(), Z_INDEX
		};
		
		FloatBuffer posBuffer = triangleMesh.setPositions(coordinates);
		mShader.setAttribute(POSITION_NAME, 3, posBuffer);
		
		
		mShader.setUniform(MATRIX_MODEL_NAME, modelMatrix);
		mShader.setUniform(COLOR_OVER_NAME, triangleModel.getTargetColor());
		
		
		
		if(triangleModel.isFillSet()) {
			GLES20.glDrawElements(GLES20.GL_TRIANGLES, triangleMesh.getIndicesCount(), GLES20.GL_UNSIGNED_SHORT, triangleMesh.getIndices());
			//glDrawElements(GL_TRIANGLES, coordinates.length, GL_UNSIGNED_INT, 0);
		}
		else {
			// TODO: Refactor with custom line implementation!
			
			// Alternative way
			//GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
			
			
			//glGetFloatv(GL_LINE_WIDTH_RANGE, lineWidth);
			//GL11.glEnable(GL11.GL_LINE_SMOOTH);
			
			
			//glLineWidth(triangleModel.getTargetOutlineWidth());
			GLES20.glLineWidth(triangleModel.getTargetOutlineWidth());
			
			//glDrawElements(GL_LINE_STRIP, coordinates.length, GL_UNSIGNED_INT, 0);
			GLES20.glDrawElements(GLES20.GL_LINE_STRIP, triangleMesh.getIndicesCount(), GLES20.GL_UNSIGNED_SHORT, triangleMesh.getIndices());
		}
	}
	
	

	private void drawRectangle(IRectangleModel rectangleModel, INamedOptions options) {
		
		if(rectangleModel.isFillSet()) {

			Matrix4f modelMatrix = rectangleModel.applyModelMatrix();
			
			mShader.setUniform(MATRIX_MODEL_NAME, modelMatrix);
			mShader.setUniform(COLOR_OVER_NAME, rectangleModel.getTargetColor());

			
			float[] coordinates = new float[] {
					rectangleModel.getTargetX1(), rectangleModel.getTargetY1(), Z_INDEX,
					rectangleModel.getTargetX2(), rectangleModel.getTargetY2(), Z_INDEX,
					rectangleModel.getTargetX3(), rectangleModel.getTargetY3(), Z_INDEX,
					rectangleModel.getTargetX4(), rectangleModel.getTargetY4(), Z_INDEX
			};
			FloatBuffer posBuffer = rectangleFillMesh.setPositions(coordinates);
			mShader.setAttribute(POSITION_NAME, 3, posBuffer);
			
			//glDrawElements(GL_TRIANGLES, coordinates.length, GL_UNSIGNED_INT, 0);
			GLES20.glDrawElements(GLES20.GL_TRIANGLES, rectangleFillMesh.getIndicesCount(), GLES20.GL_UNSIGNED_SHORT, rectangleFillMesh.getIndices());
		}
		else {

			Vector3f scale = rectangleModel.getScale();
			rectLineModel.setScaling(scale.x, scale.y, scale.z);
			
			Vector3f translation = rectangleModel.getTranslation();
			rectLineModel.setTranslation(translation.x, translation.y, translation.z);
			
			
			float outlineWidth = rectangleModel.getTargetOutlineWidth();	// round down to int?
			
			rectLineModel.setTargetColor(rectangleModel.getTargetColor());
			rectLineModel.setTargetWidth(outlineWidth);

			
			int hFill, vFill;
			int targetLineAlignment;
			if(rectangleModel.getOutlineType() == IRectangleModel.OUTLINE_TYPE_INNER) {
				targetLineAlignment = ILineModel.ALIGNMENT_INNER;
				hFill = vFill = 0;
			}
			else if(rectangleModel.getOutlineType() == IRectangleModel.OUTLINE_TYPE_OUTER) {
				targetLineAlignment = ILineModel.ALIGNMENT_OUTER;
				hFill = vFill = (int)outlineWidth - 1;
			}
			else {
				targetLineAlignment = ILineModel.ALIGNMENT_CENTER;
				hFill = vFill = (int)outlineWidth / 2 - (outlineWidth != 0 && outlineWidth % 2 == 0 ? 1 : 0);
			}
			rectLineModel.setTargetAlignment(targetLineAlignment);
			
			
			float x1 = rectangleModel.getTargetX1();
			float y1 = rectangleModel.getTargetY1();
			float x2 = rectangleModel.getTargetX2();
			float y2 = rectangleModel.getTargetY2();
			float x3 = rectangleModel.getTargetX3();
			float y3 = rectangleModel.getTargetY3();
			float x4 = rectangleModel.getTargetX4();
			float y4 = rectangleModel.getTargetY4();
			
			
			float adjustMax = -1.0f; // Adjusts max values to start at the 0 index
			
			float maxY = Math.max(y1, Math.max(y2, Math.max(y3, y4)));
			float maxX = Math.max(x1, Math.max(x2, Math.max(x3, x4)));
			
			
			rectTargetAlignment(rectLineModel, x1, y1, x2, y2, maxX, maxY, targetLineAlignment);
			rectTargetCoordinates(rectLineModel, x1, y1, x2, y2, maxX, maxY, adjustMax, hFill, vFill);
			drawLine(rectLineModel, options);

			rectTargetAlignment(rectLineModel, x2, y2, x3, y3, maxX, maxY, targetLineAlignment);
			rectTargetCoordinates(rectLineModel, x2, y2, x3, y3, maxX, maxY, adjustMax, hFill, vFill);
			drawLine(rectLineModel, options);

			rectTargetAlignment(rectLineModel, x3, y3, x4, y4, maxX, maxY, targetLineAlignment);
			rectTargetCoordinates(rectLineModel, x3, y3, x4, y4, maxX, maxY, adjustMax, hFill, vFill);
			drawLine(rectLineModel, options);

			rectTargetAlignment(rectLineModel, x4, y4, x1, y1, maxX, maxY, targetLineAlignment);
			rectTargetCoordinates(rectLineModel, x4, y4, x1, y1, maxX, maxY, adjustMax, hFill, vFill);
			drawLine(rectLineModel, options);
			
		}
	}
	
	private void rectTargetAlignment(LineModel model, float xf, float yf, float xs, float ys, float maxX, float maxY, int targetAlignment) {
		if((xf == xs && xf == maxX) || (yf == ys && yf == maxY)) {
			int oppsAlign = LineModel.oppositeAlignmentFrom(targetAlignment);
			model.setTargetAlignment(oppsAlign);
			
			model.setTargetCenterAlignment(ILineModel.ALIGN_CENTER_OUT);
		}
		else {
			model.setTargetAlignment(targetAlignment);
			model.setTargetCenterAlignment(ILineModel.ALIGN_CENTER_IN);
		}
	}
	
	private void rectTargetCoordinates(LineModel model, float xf, float yf, float xs, float ys, float maxX, float maxY, float adjustMax, float hFill, float vFill) {
		model.setTargetCoordinates(
				xf + adjustmentForOffsetMax(xf, xs, maxX, adjustMax),
				yf + adjustmentForOffsetMax(yf, ys, maxY, adjustMax),
				xs + adjustmentForOffsetMax(xf, xs, maxX, adjustMax)
				// Y1 == Y2: if max -> bottom ; else -> top
				   + adjustmentForFill(yf, ys, maxY, hFill),
				ys + adjustmentForOffsetMax(yf, ys, maxY, adjustMax)
				   + adjustmentForFill(xf, xs, maxX, -vFill)
		);
	}
	
	private float adjustmentForOffsetMax(float v1, float v2, float vmax, float adjustMax) {
		return v1 == v2 ? (v1 == vmax ? adjustMax : 0f) : 0f;
	}
	
	private float adjustmentForFill(float v1, float v2, float vmax, float vfill) {
		return v1 == v2 ? (v1 == vmax ? vfill : -vfill) : 0f;
	}
	
	
	

	@Override
	public void drawBatch(List<IRenderModel> modelList, INamedOptions options) {
		for(IRenderModel model: modelList) {
			draw(model, options);
		}
	}

	@Override
	public void release() {
		GLES20.glDisableVertexAttribArray(mShader.getAttributeLocation(POSITION_NAME));
		
		mShader.unbind();
	}


	@Override
	public boolean supports(String target) {
		return supportedTarget().contains(target);
	}
	
	private static String supportedTarget() {
		return IPointModel.class.getName()
				+ ILineModel.class.getName()
				+ ITriangleModel.class.getName()
				+ IRectangleModel.class.getName();
	}
	
	
	public static class GLShapeDriverFactory implements IRenderDriverFactory {
		private final Provider<GLShapeDriver> driverProvider;

		@Inject
		public GLShapeDriverFactory(Provider<GLShapeDriver> driverProvider) {
			this.driverProvider = driverProvider;
		}
		
		@Override
		public boolean supports(String target) {
			return supportedTarget().contains(target);
		}

		@Override
		public IRenderDriver newDriver(IParams params) {
			return driverProvider.get();
		}
	}

	
	private class DrawLineStruct {
		
		public Vector2f a = new Vector2f();
		public Vector2f b = new Vector2f();
		public Vector2f c = new Vector2f();
		public Vector2f d = new Vector2f();
		
		public Vector2f perp1 = new Vector2f();
		public Vector2f perp1U = new Vector2f();
		public Vector2f perp1less = new Vector2f();
		public Vector2f perp1more = new Vector2f();
		
		public Vector2f perp2 = new Vector2f();
		public Vector2f perp2U = new Vector2f();
		public Vector2f perp2less = new Vector2f();
		public Vector2f perp2more = new Vector2f();
		
	}
	
}
