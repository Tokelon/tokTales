package com.tokelon.toktales.desktop.lwjgl.render;

import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_LINE_STRIP;
import static org.lwjgl.opengl.GL11.GL_POINTS;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL11.glLineWidth;
import static org.lwjgl.opengl.GL11.glPointSize;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import java.nio.FloatBuffer;
import java.util.List;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

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
import com.tokelon.toktales.desktop.lwjgl.LWJGLException;
import com.tokelon.toktales.desktop.lwjgl.ShaderProgram;

public class GLShapeDriver implements IRenderDriver {

	public static final String TAG = "GLShapeDriver";

	// TODO: Optimize all object creation (look for "new")
	
	
	private static final String VS_Shape = 
			"#version 330\n" +
			"layout(location = 0) in vec3 vPosition;\n" +
			"uniform mat4 uMVPMatrix;\n" +
			"uniform mat4 uModelMatrix;\n" +
			"void main() {\n" +
			"  gl_Position = uMVPMatrix * uModelMatrix * vec4(vPosition, 1.0);\n" +
			"}\n";
	
	private static final String FS_Shape = 
			"#version 330\n" +
			//"precision mediump float;" +
			"uniform vec4 colorOver;\n" +
			"void main() {\n" +
			"  gl_FragColor = colorOver;\n" +
			"}\n";
	

	private static final String MATRIX_MVP_NAME = "uMVPMatrix";
	private static final String MATRIX_MODEL_NAME = "uModelMatrix";
	private static final String COLOR_OVER_NAME = "colorOver";
	
	private static final float Z_INDEX = -1.05f;	// TODO: Important - Fix static z
	
	
	private final FloatBuffer lineNativeCoordinateBuffer;
	private final FloatBuffer lineCoordinateBuffer;
	private final FloatBuffer pointCoordinateBuffer;
	private final FloatBuffer triangleCoordinateBuffer;
	private final FloatBuffer rectangleCoordinateBuffer;
	
	private GLMesh lineNativeMesh;
	private GLMesh lineMesh;
	private GLMesh pointMesh;
	private GLMesh triangleMesh;
	private GLMesh rectangleFillMesh;
	private GLMesh rectangleOutlineMesh;
	
	private final DrawLineStruct drawLineStruct = new DrawLineStruct(); 
	
	private final LineModel rectLineModel = new LineModel();
	
	private ShaderProgram mShader;
	
	
	public GLShapeDriver() {
		lineNativeCoordinateBuffer = BufferUtils.createFloatBuffer(6);
		lineCoordinateBuffer = BufferUtils.createFloatBuffer(12);
		pointCoordinateBuffer = BufferUtils.createFloatBuffer(3);
		triangleCoordinateBuffer = BufferUtils.createFloatBuffer(9);
		rectangleCoordinateBuffer = BufferUtils.createFloatBuffer(26); // 12
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
			
		} catch (LWJGLException e) {
			TokTales.getLog().e(TAG, "Failed to create shader program: " +e.getMessage());
			return;
		}
		
		
		
		int[] lineNativeIndices = new int[] {
			0, 1
		};
		
		lineNativeMesh = new GLMesh(lineNativeIndices);
	
		
		int[] lineIndices = new int[] {
			0, 1, 3, 3, 1, 2
		};
		
		lineMesh = new GLMesh(lineIndices);
		
		
		int[] pointIndices = new int[] {
			0
		};
		
		pointMesh = new GLMesh(pointIndices);
		
		
		int[] triangleIndices = new int[] {
			0, 1, 2	
		};
		
		triangleMesh = new GLMesh(triangleIndices);
		
		

		int[] rectangleFillIndices = new int[] {
			0, 1, 3, 3, 1, 2,
		};
		
		rectangleFillMesh = new GLMesh(rectangleFillIndices);
		
		
		int[] rectangleOutlineIndices = new int[] {
			0, 1, 2, 3, 4, 5, 6, 7
		};
		
		rectangleOutlineMesh = new GLMesh(rectangleOutlineIndices);
		
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
		lineMesh.bind();
		
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
		lineCoordinateBuffer.put(coordinates).position(0);
		
		lineMesh.setCoords(lineCoordinateBuffer);
		

		mShader.setUniform(MATRIX_MODEL_NAME, modelMatrix);
		mShader.setUniform(COLOR_OVER_NAME, lineModel.getTargetColor());
		
		glDrawElements(GL_TRIANGLES, lineMesh.getVertexCount(), GL_UNSIGNED_INT, 0);
	}
	

	private void drawLineVoodo(ILineModel lineModel, INamedOptions options) {
		lineMesh.bind();
		
		
		Matrix4f modelMatrix = lineModel.applyModelMatrix();
		
		float lineWidth = lineModel.getTargetWidth();
		
		
		
		float slope = (lineModel.getTargetY2() - lineModel.getTargetY1()) / (lineModel.getTargetX2() - lineModel.getTargetX1());
		
		float pslope = - 1 / slope;
		
		// eq
		//float y - lineModel.getTargetY1() = slope * (x + lineModel.getTargetX1());

		// p eq
		//float y - lineModel.getTargetY1() = pslope * (x + lineModel.getTargetX1());
		
		
		
		Vector2f v1 = new Vector2f(lineModel.getTargetX1(), lineModel.getTargetY1());
		Vector2f v2 = new Vector2f(lineModel.getTargetX2(), lineModel.getTargetY2());
		
		//Vector2f line = new Vector2f(lineModel.getTargetX2() - lineModel.getTargetX1(), lineModel.getTargetY2() - lineModel.getTargetY1());
		Vector2f line = v2.sub(v1);
		Vector2f normal = new Vector2f(- line.y, line.x).normalize();
		Vector2f normalMul = normal.mul(lineWidth);
		
		Vector2f a = v1.sub(normalMul, new Vector2f());
		Vector2f b = v1.add(normalMul, new Vector2f());
		Vector2f c = v2.sub(normalMul, new Vector2f());
		Vector2f d = v2.add(normalMul, new Vector2f());
		
		float[] coordinates = new float[] {
			a.x, a.y, Z_INDEX,
			c.x, c.y, Z_INDEX,
			d.x, d.y, Z_INDEX,
			b.x, b.y, Z_INDEX
		};
		lineCoordinateBuffer.put(coordinates).position(0);
		
		lineMesh.setCoords(lineCoordinateBuffer);
		

		mShader.setUniform(MATRIX_MODEL_NAME, modelMatrix);
		mShader.setUniform(COLOR_OVER_NAME, lineModel.getTargetColor());
		
		glDrawElements(GL_TRIANGLES, lineMesh.getVertexCount(), GL_UNSIGNED_INT, 0);
	}
	
	private void drawLineNative(ILineModel lineModel, INamedOptions options) {
		lineNativeMesh.bind();
		
		
		Matrix4f modelMatrix = lineModel.applyModelMatrix();

		
		float[] coordinates = new float[] {
				lineModel.getTargetX1(), lineModel.getTargetY1(), Z_INDEX,
				lineModel.getTargetX2(), lineModel.getTargetY2(), Z_INDEX
		};
		lineNativeCoordinateBuffer.put(coordinates).position(0);

		lineNativeMesh.setCoords(lineNativeCoordinateBuffer);

		
		mShader.setUniform(MATRIX_MODEL_NAME, modelMatrix);
		mShader.setUniform(COLOR_OVER_NAME, lineModel.getTargetColor());
		
		
		glLineWidth(lineModel.getTargetWidth());
		glDrawElements(GL_LINES, lineNativeMesh.getVertexCount(), GL_UNSIGNED_INT, 0);
	}

	
	private void drawPoint(IPointModel pointModel, INamedOptions options) {
		pointMesh.bind();

		Matrix4f modelMatrix = pointModel.applyModelMatrix();
		
		float[] coordinates = new float[] {
				pointModel.getTargetX() + 0.5f, pointModel.getTargetY() + 0.5f, Z_INDEX
		};
		pointCoordinateBuffer.put(coordinates).position(0);

		pointMesh.setCoords(pointCoordinateBuffer);
		
		
		mShader.setUniform(MATRIX_MODEL_NAME, modelMatrix);
		mShader.setUniform(COLOR_OVER_NAME, pointModel.getTargetColor());
		
		
		glPointSize(pointModel.getTargetSize());
		glDrawElements(GL_POINTS, pointMesh.getVertexCount(), GL_UNSIGNED_INT, 0);
	}
	
	
	private void drawTriangle(ITriangleModel triangleModel, INamedOptions options) {
		triangleMesh.bind();
		
		Matrix4f modelMatrix = triangleModel.applyModelMatrix();
		
		/*
		float[] coordinates = new float[] {
				triangleModel.getTargetX1() + 0.375f, triangleModel.getTargetY1() + 0.375f, Z_INDEX,
				triangleModel.getTargetX2() + 0.375f, triangleModel.getTargetY2() + 0.375f, Z_INDEX,
				triangleModel.getTargetX3() + 0.375f, triangleModel.getTargetY3() + 0.375f, Z_INDEX
		};
		*/
		
		// TODO: Fix y and x placement ?
		float[] coordinates = new float[] {
				triangleModel.getTargetX1(), triangleModel.getTargetY1(), Z_INDEX,
				triangleModel.getTargetX2(), triangleModel.getTargetY2(), Z_INDEX,
				triangleModel.getTargetX3(), triangleModel.getTargetY3(), Z_INDEX
		};
		triangleCoordinateBuffer.put(coordinates).position(0);

		triangleMesh.setCoords(triangleCoordinateBuffer);
		
		
		mShader.setUniform(MATRIX_MODEL_NAME, modelMatrix);
		mShader.setUniform(COLOR_OVER_NAME, triangleModel.getTargetColor());
		
		
		
		if(triangleModel.isFillSet()) {
			glDrawElements(GL_TRIANGLES, triangleMesh.getVertexCount(), GL_UNSIGNED_INT, 0);
		}
		else {
			// TODO: Refactor with custom line implementation!
			
			// Alternative way
			//GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
			
			
			//glGetFloatv(GL_LINE_WIDTH_RANGE, lineWidth);
			//GL11.glEnable(GL11.GL_LINE_SMOOTH);
			
			
			glLineWidth(triangleModel.getTargetOutlineWidth());
			
			// +1 vertex because line_strip
			glDrawElements(GL_LINE_STRIP, triangleMesh.getVertexCount() + 1, GL_UNSIGNED_INT, 0);
			
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
			rectangleCoordinateBuffer.put(coordinates).position(0);
			
			rectangleFillMesh.bind();
			rectangleFillMesh.setCoords(rectangleCoordinateBuffer);

			glDrawElements(GL_TRIANGLES, rectangleFillMesh.getVertexCount(), GL_UNSIGNED_INT, 0);
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
	
	private void drawRectangleNative(IRectangleModel rectangleModel, INamedOptions options) {
		
		Matrix4f modelMatrix = rectangleModel.applyModelMatrix();
		
		
		mShader.setUniform(MATRIX_MODEL_NAME, modelMatrix);
		mShader.setUniform(COLOR_OVER_NAME, rectangleModel.getTargetColor());

		
		if(rectangleModel.isFillSet()) {
			float[] coordinates = new float[] {
					rectangleModel.getTargetX1(), rectangleModel.getTargetY1(), Z_INDEX,
					rectangleModel.getTargetX2(), rectangleModel.getTargetY2(), Z_INDEX,
					rectangleModel.getTargetX3(), rectangleModel.getTargetY3(), Z_INDEX,
					rectangleModel.getTargetX4(), rectangleModel.getTargetY4(), Z_INDEX
			};
			rectangleCoordinateBuffer.put(coordinates).position(0);
			
			rectangleFillMesh.bind();
			rectangleFillMesh.setCoords(rectangleCoordinateBuffer);

			glDrawElements(GL_TRIANGLES, rectangleFillMesh.getVertexCount(), GL_UNSIGNED_INT, 0);
		}
		else {
			
			float scaleY = rectangleModel.getScale().y;
			float scaleX = rectangleModel.getScale().x;
			
			float maxY = Math.max(rectangleModel.getTargetY1(), Math.max(rectangleModel.getTargetY2(), Math.max(rectangleModel.getTargetY3(), rectangleModel.getTargetY4())));
			float maxX = Math.max(rectangleModel.getTargetX1(), Math.max(rectangleModel.getTargetX2(), Math.max(rectangleModel.getTargetX3(), rectangleModel.getTargetX4())));
			
			float yOffsetTop, yOffsetBottom, xOffset, hFill, vFill, lefBotFill;
			if(rectangleModel.getOutlineType() == IRectangleModel.OUTLINE_TYPE_INNER) {
				yOffsetTop = ((rectangleModel.getTargetOutlineWidth() / 2) - 1) / scaleY;
				yOffsetBottom = ((rectangleModel.getTargetOutlineWidth() / 2) + 1) / scaleY;
				vFill = ((rectangleModel.getTargetOutlineWidth() / 2)) / scaleY;
				lefBotFill = (rectangleModel.getTargetOutlineWidth() % 2 == 0 ? 0.0f : 1.0f) / scaleY;	// left bottom need +1 when outlineWidth is odd
				
				xOffset = ((rectangleModel.getTargetOutlineWidth() / 2)) / scaleX;
				hFill = xOffset;	// already scaled
			}
			else if(rectangleModel.getOutlineType() == IRectangleModel.OUTLINE_TYPE_OUTER) {
				yOffsetTop = - ((rectangleModel.getTargetOutlineWidth() / 2) + 1) / scaleY;
				yOffsetBottom = - ((rectangleModel.getTargetOutlineWidth() / 2) - 1) / scaleY;
				vFill = ((rectangleModel.getTargetOutlineWidth() / 2)) / scaleY;
				lefBotFill = (rectangleModel.getTargetOutlineWidth() % 2 == 0 ? 0.0f : 1.0f) / scaleY;	// left bottom need +1 when outlineWidth is odd

				
				xOffset = - ((rectangleModel.getTargetOutlineWidth() / 2)) / scaleX;
				hFill = - xOffset;	// already scaled
			}
			else {
				yOffsetTop = -1.0f	/ scaleY;
				yOffsetBottom = 1.0f / scaleY;
				xOffset = 0.0f / scaleX;
				
				vFill = ((rectangleModel.getTargetOutlineWidth() / 2)) / scaleY;
				hFill = ((rectangleModel.getTargetOutlineWidth() / 2)) / scaleX;
				lefBotFill = 0.0f / scaleY;
			}
			
			
			float[] coordinates = new float[] {
					// Line #1
					rectangleModel.getTargetX1() + (rectangleModel.getTargetX1() == maxX ? -xOffset : xOffset), rectangleModel.getTargetY1() + (rectangleModel.getTargetY1() == maxY ? -yOffsetBottom : yOffsetTop), Z_INDEX,
					// fixed
					rectangleModel.getTargetX2() + (rectangleModel.getTargetX2() == maxX ? -xOffset : xOffset)
						// Y1 == Y2: if max -> bottom ; else -> top
						+ (rectangleModel.getTargetY1() == rectangleModel.getTargetY2() ? (rectangleModel.getTargetY2() == maxY ? hFill : -hFill) : 0.0f),
					rectangleModel.getTargetY2() + (rectangleModel.getTargetY2() == maxY ? -yOffsetBottom: yOffsetTop)
						// X1 == X2: if max -> right ; else -> left
						+ (rectangleModel.getTargetX1() == rectangleModel.getTargetX2() ? (rectangleModel.getTargetX2() == maxX ? -vFill : vFill + lefBotFill) : 0.0f),
					Z_INDEX,
					
					// Line #2
					rectangleModel.getTargetX2() + (rectangleModel.getTargetX2() == maxX ? -xOffset : xOffset), rectangleModel.getTargetY2() + (rectangleModel.getTargetY2() == maxY ? -yOffsetBottom : yOffsetTop), Z_INDEX,
					// fixed
					rectangleModel.getTargetX3() + (rectangleModel.getTargetX3() == maxX ? -xOffset : xOffset)
						+ (rectangleModel.getTargetY2() == rectangleModel.getTargetY3() ? (rectangleModel.getTargetY3() == maxY ? hFill : -hFill) : 0.0f),
					rectangleModel.getTargetY3() + (rectangleModel.getTargetY3() == maxY ? -yOffsetBottom : yOffsetTop)
						+ (rectangleModel.getTargetX2() == rectangleModel.getTargetX3() ? (rectangleModel.getTargetX3() == maxX ? -vFill : vFill + lefBotFill) : 0.0f),
					Z_INDEX,
					
					// Line #3
					rectangleModel.getTargetX3() + (rectangleModel.getTargetX3() == maxX ? -xOffset : xOffset), rectangleModel.getTargetY3() + (rectangleModel.getTargetY3() == maxY ? -yOffsetBottom : yOffsetTop), Z_INDEX,
					// fixed
					rectangleModel.getTargetX4() + (rectangleModel.getTargetX4() == maxX ? -xOffset : xOffset)
						+ (rectangleModel.getTargetY3() == rectangleModel.getTargetY4() ? (rectangleModel.getTargetY4() == maxY ? hFill : -hFill) : 0.0f),
					rectangleModel.getTargetY4() + (rectangleModel.getTargetY4() == maxY ? -yOffsetBottom : yOffsetTop)
						+ (rectangleModel.getTargetX3() == rectangleModel.getTargetX4() ? (rectangleModel.getTargetX4() == maxX ? -vFill : vFill + lefBotFill) : 0.0f),
					Z_INDEX,
					
					// Line #4
					rectangleModel.getTargetX4() + (rectangleModel.getTargetX4() == maxX ? -xOffset : xOffset), rectangleModel.getTargetY4() + (rectangleModel.getTargetY4() == maxY ? -yOffsetBottom : yOffsetTop), Z_INDEX,
					// fixed
					rectangleModel.getTargetX1() + (rectangleModel.getTargetX1() == maxX ? -xOffset : xOffset)
						+ (rectangleModel.getTargetY4() == rectangleModel.getTargetY1() ? (rectangleModel.getTargetY1() == maxY ? hFill : -hFill) : 0.0f),
					rectangleModel.getTargetY1() + (rectangleModel.getTargetY1() == maxY ? -yOffsetBottom : yOffsetTop)
						+ (rectangleModel.getTargetX4() == rectangleModel.getTargetX1() ? (rectangleModel.getTargetX1() == maxX ? -vFill : vFill + lefBotFill) : 0.0f),
					Z_INDEX,
			};
			rectangleCoordinateBuffer.put(coordinates).position(0);
			
			rectangleOutlineMesh.bind();
			rectangleOutlineMesh.setCoords(rectangleCoordinateBuffer);
			
			glLineWidth(rectangleModel.getTargetOutlineWidth());
			//glDrawElements(GL_LINES, coordinates.length / 2, GL_UNSIGNED_INT, 0);
			glDrawElements(GL_LINES, rectangleOutlineMesh.getVertexCount(), GL_UNSIGNED_INT, 0);
		}
	}
	
	

	@Override
	public void drawBatch(List<IRenderModel> modelList, INamedOptions options) {
		for(IRenderModel model: modelList) {
			draw(model, options);
		}
	}

	@Override
	public void release() {
		glDisableVertexAttribArray(0);
		glBindVertexArray(0);
		
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

		@Override
		public boolean supports(String target) {
			return supportedTarget().contains(target);
		}

		@Override
		public IRenderDriver newDriver(IParams params) {
			return new GLShapeDriver();
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
