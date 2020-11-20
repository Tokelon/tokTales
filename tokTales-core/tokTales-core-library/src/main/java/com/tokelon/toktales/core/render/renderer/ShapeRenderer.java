package com.tokelon.toktales.core.render.renderer;

import javax.inject.Inject;

import org.joml.Vector4f;

import com.tokelon.toktales.core.content.graphics.IRGBAColor;
import com.tokelon.toktales.core.engine.render.IRenderAccess;
import com.tokelon.toktales.core.render.IRenderDriver;
import com.tokelon.toktales.core.render.RenderException;
import com.tokelon.toktales.core.render.model.ILineModel;
import com.tokelon.toktales.core.render.model.IPointModel;
import com.tokelon.toktales.core.render.model.IRectangleModel;
import com.tokelon.toktales.core.render.model.ITriangleModel;
import com.tokelon.toktales.core.render.model.LineModel;
import com.tokelon.toktales.core.render.model.PointModel;
import com.tokelon.toktales.core.render.model.RectangleModel;
import com.tokelon.toktales.core.render.model.TriangleModel;
import com.tokelon.toktales.core.render.shapes.ILineShape;
import com.tokelon.toktales.core.render.shapes.IPointShape;
import com.tokelon.toktales.core.render.shapes.IRectangleShape;
import com.tokelon.toktales.core.render.shapes.ITriangleShape;
import com.tokelon.toktales.tools.core.objects.options.NamedOptionsImpl;

public class ShapeRenderer extends AbstractRenderer implements IShapeRenderer {


	private final ShapePaint paint = new ShapePaint();
	
	private final PointModel pointModel = new PointModel();
	private final LineModel lineModel = new LineModel();
	private final TriangleModel triangleModel = new TriangleModel();
	private final RectangleModel rectangleModel = new RectangleModel();
	
	private final Vector4f colorVector = new Vector4f();

	private final NamedOptionsImpl drawingOptions = new NamedOptionsImpl();
	

	private boolean isInBatchDraw = false;

	private IRenderDriver shapeDriver;
	
	
	private final IRenderAccess renderAccess;
	
	@Inject
	public ShapeRenderer(IRenderAccess renderAccess) {
		this.renderAccess = renderAccess;
	}
	
	
	
	@Override
	protected void onContextCreated() {
		shapeDriver = renderAccess.requestDriver(IPointModel.class.getName() + ILineModel.class.getName() + ITriangleModel.class.getName() + IRectangleModel.class.getName());
		if(shapeDriver == null) {
			throw new RenderException("No render driver found for: " + ILineModel.class.getName());
		}
		
		shapeDriver.create();
	}
	
	@Override
	protected void onContextChanged() {
		// Nothing
	}
	
	@Override
	protected void onContextDestroyed() {
		if(shapeDriver != null) {
			shapeDriver.destroy();
			shapeDriver = null;
		}
		
	}
	
	@Override
	public void startBatch() {
		shapeDriver.use(getMatrixProjectionAndView());
		
		isInBatchDraw = true;
	}
	
	@Override
	public void finishBatch() {
		isInBatchDraw = false;
		
		shapeDriver.release();
	}
	
	
	private boolean checkView() {
		if(!hasView()) {
			assert false : "No view";
			return false;
			//throw new RenderException("Renderer has no view yet");
		}
		
		return true;
	}
	
	
	/* Should widths/sized be converted to round numbers?
	 * Like pointSize, lineWidth etc
	 * 
	 */
	
	@Override
	public void drawPointShape(IPointShape pointShape) {
		if(!checkView()) return;
		
		pointModel.setTargetColor(colorVector);

		pointModel.setTargetCoordinates(
				getViewTransformer().cameraToScreenX(pointShape.getPositionX()),
				getViewTransformer().cameraToScreenY(pointShape.getPositionY())
		);
		
		// Convert to round number?
		float pointSizePixels = pointShape.getSize() * getViewTransformer().getCameraToViewportScale();
		pointModel.setTargetSize(pointSizePixels);
		
		if(isInBatchDraw) {
			shapeDriver.draw(pointModel, drawingOptions);	
		}
		else {
			shapeDriver.drawQuick(getMatrixProjectionAndView(), pointModel, drawingOptions);			
		}
	}
	
	@Override
	public void drawPoint(float x, float y, float pointSize) {
		if(!checkView()) return;
		
		pointModel.setTargetColor(colorVector);

		pointModel.setTargetCoordinates(
				getViewTransformer().cameraToScreenX(x),
				getViewTransformer().cameraToScreenY(y)
		);

		
		// Convert to round number? 
		float pointSizePixels = pointSize * getViewTransformer().getCameraToViewportScale();
		pointModel.setTargetSize(pointSizePixels);
		
		if(isInBatchDraw) {
			shapeDriver.draw(pointModel, drawingOptions);
		}
		else {
			shapeDriver.drawQuick(getMatrixProjectionAndView(), pointModel, drawingOptions);			
		}
	}

	
	@Override
	public void drawLineShape(ILineShape lineShape) {
		if(!checkView()) return;

		
		lineModel.setTargetCoordinates(
				lineShape.getShapeX1(),
				lineShape.getShapeY1(),
				lineShape.getShapeX2(),
				lineShape.getShapeY2()
		);
		
		lineModel.setScaling2D(
				getViewTransformer().cameraToViewportX(lineShape.getSize()),
				getViewTransformer().cameraToViewportY(lineShape.getSize())
		);
		lineModel.setTranslation2D(
				getViewTransformer().cameraToScreenX(lineShape.getPositionX()),
				getViewTransformer().cameraToScreenY(lineShape.getPositionY())
		);
		
		// Scaling is going to be applied separately through the model
		lineModel.setTargetWidth(lineShape.getWidth());

		lineModel.setTargetColor(colorVector);
		lineModel.setTargetAlignment(paint.lineAlignment);
		lineModel.setTargetCenterAlignment(paint.lineCenterAlignment);

		if(isInBatchDraw) {
			shapeDriver.draw(lineModel, drawingOptions);
		}
		else {
			shapeDriver.drawQuick(getMatrixProjectionAndView(), lineModel, drawingOptions);	
		}
	}
	
	@Override
	public void drawLine(float x1, float y1, float x2, float y2, float lineWidth) {
		if(!checkView()) return;

		lineModel.setTargetCoordinates(
				getViewTransformer().cameraToScreenX(x1),
				getViewTransformer().cameraToScreenY(y1),
				getViewTransformer().cameraToScreenX(x2),
				getViewTransformer().cameraToScreenY(y2)
		);
		
		lineModel.setScaling2D(1.0f, 1.0f);
		lineModel.setTranslation2D(0.0f, 0.0f);
		
		// Scale from world to view
		float lineWidthPixels = lineWidth * getViewTransformer().getCameraToViewportScale();
		lineModel.setTargetWidth(lineWidthPixels);

		lineModel.setTargetColor(colorVector);
		lineModel.setTargetAlignment(paint.lineAlignment);
		lineModel.setTargetCenterAlignment(paint.lineCenterAlignment);
		
		if(isInBatchDraw) {
			shapeDriver.draw(lineModel, drawingOptions);	
		}
		else {
			shapeDriver.drawQuick(getMatrixProjectionAndView(), lineModel, drawingOptions);	
		}
	}

	
	@Override
	public void drawTriangleShape(ITriangleShape triangleShape) {
		if(!checkView()) return;

		// Values do not need converting since the scaling and translation is applied separately
		triangleModel.setTargetCoordinates(
				triangleShape.getShapeX1(),
				triangleShape.getShapeY1(),
				triangleShape.getShapeX2(),
				triangleShape.getShapeY2(),
				triangleShape.getShapeX3(),
				triangleShape.getShapeY3()
		);
		
		triangleModel.setScaling2D(
				getViewTransformer().cameraToViewportX(triangleShape.getSize()),
				getViewTransformer().cameraToViewportY(triangleShape.getSize())
		);
		triangleModel.setTranslation2D(
				getViewTransformer().cameraToScreenX(triangleShape.getPositionX()),
				getViewTransformer().cameraToScreenY(triangleShape.getPositionY())
		);
		
		// OutlineWidth will be scaled with the model
		triangleModel.setTargetOutlineWidth(paint.outlineWidth);
		triangleModel.setFill(paint.fill);
		triangleModel.setTargetColor(colorVector);

		
		if(isInBatchDraw) {
			shapeDriver.draw(triangleModel, drawingOptions);	
		}
		else {
			shapeDriver.drawQuick(getMatrixProjectionAndView(), triangleModel, drawingOptions);	
		}
	}
	
	@Override
	public void drawTriangle(float x1, float y1, float x2, float y2, float x3, float y3) {
		if(!checkView()) return;
		
		triangleModel.setTargetCoordinates(
				getViewTransformer().cameraToScreenX(x1),
				getViewTransformer().cameraToScreenY(y1),
				getViewTransformer().cameraToScreenX(x2),
				getViewTransformer().cameraToScreenY(y2),
				getViewTransformer().cameraToScreenX(x3),
				getViewTransformer().cameraToScreenY(y3)
		);
		
		triangleModel.setScaling2D(1.0f, 1.0f);
		triangleModel.setTranslation2D(0.0f, 0.0f);
		
		// Scale from world to view
		float outlineWidthScaled = paint.outlineWidth * getViewTransformer().getCameraToViewportScale();
		triangleModel.setTargetOutlineWidth(outlineWidthScaled);
		triangleModel.setFill(paint.fill);
		triangleModel.setTargetColor(colorVector);
		
		
		if(isInBatchDraw) {
			shapeDriver.draw(triangleModel, drawingOptions);	
		}
		else {
			shapeDriver.drawQuick(getMatrixProjectionAndView(), triangleModel, drawingOptions);
		}
	}

	
	@Override
	public void drawRectangleShape(IRectangleShape rectangleShape) {
		if(!checkView()) return;
		
		rectangleModel.setTargetCoordinates(
				0.0f,
				0.0f,
				0.0f,
				rectangleShape.getShapeLengthV(),
				rectangleShape.getShapeLengthH(),
				rectangleShape.getShapeLengthV(),
				rectangleShape.getShapeLengthH(),
				0.0f
		);
		
		rectangleModel.setScaling2D(
				getViewTransformer().cameraToViewportX(rectangleShape.getSize()),
				getViewTransformer().cameraToViewportY(rectangleShape.getSize())
		);
		rectangleModel.setTranslation2D(
				getViewTransformer().cameraToScreenX(rectangleShape.getPositionX()),
				getViewTransformer().cameraToScreenY(rectangleShape.getPositionY())
		);
		
		// Outline width will be scaled with the model
		rectangleModel.setTargetOutlineWidth(paint.outlineWidth);

		rectangleModel.setFill(paint.fill);
		rectangleModel.setOutlineType(paint.outlineType);
		
		rectangleModel.setTargetColor(colorVector);
		
		
		if(isInBatchDraw) {
			shapeDriver.draw(rectangleModel, drawingOptions);
		}
		else {
			shapeDriver.drawQuick(getMatrixProjectionAndView(), rectangleModel, drawingOptions);	
		}
	}
	
	@Override
	public void drawRectangle(float x, float y, float hLength, float vLength) {
		// Does outline need to adjust the length ?
		// Right now lengths can change depending on the outline
		
		drawRectangle(
				x, y,
				x, y + vLength,
				x + hLength, y + vLength,
				x + hLength, y
		);
	}
	
	@Override
	public void drawRectangle(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4) {
		if(!checkView()) return;
		
		rectangleModel.setTargetCoordinates(
				getViewTransformer().cameraToScreenX(x1),
				getViewTransformer().cameraToScreenY(y1),
				getViewTransformer().cameraToScreenX(x2),
				getViewTransformer().cameraToScreenY(y2),
				getViewTransformer().cameraToScreenX(x3),
				getViewTransformer().cameraToScreenY(y3),
				getViewTransformer().cameraToScreenX(x4),
				getViewTransformer().cameraToScreenY(y4)
		);
		
		rectangleModel.setScaling2D(1.0f, 1.0f);
		rectangleModel.setTranslation2D(0.0f, 0.0f);
		

		// Scale from world to view
		float outlineWidthScaled = paint.outlineWidth * getViewTransformer().getCameraToViewportScale();
		rectangleModel.setTargetOutlineWidth(outlineWidthScaled);

		rectangleModel.setFill(paint.fill);
		rectangleModel.setOutlineType(paint.outlineType);
		
		rectangleModel.setTargetColor(colorVector);
		
		
		if(isInBatchDraw) {
			shapeDriver.draw(rectangleModel, drawingOptions);	
		}
		else {
			shapeDriver.drawQuick(getMatrixProjectionAndView(), rectangleModel, drawingOptions);	
		}
	}
	
	

	@Override
	public void setColor(IRGBAColor color) {
		this.paint.color = color;
		this.colorVector.set(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
	}
	
	@Override
	public void setFill(boolean fill) {
		this.paint.fill = fill;
	}
	
	@Override
	public void setOutlineWidth(float width) {
		this.paint.outlineWidth = width;
	}

	@Override
	public void setOutlineType(int type) {
		this.paint.outlineType = type;
	}
		
	@Override
	public void setLineAlignment(int type) {
		this.paint.lineAlignment = type;
	}
	
	@Override
	public void setLineCenterAlignment(int type) {
		this.paint.lineCenterAlignment = type;
	}
	
	
	private class ShapePaint {

		public boolean fill = true;
		public int outlineType = 0;
		
		public IRGBAColor color;
		public float outlineWidth = 1.0f;
		public int lineAlignment = 0;
		public int lineCenterAlignment = 0;
		
	}
	
}
