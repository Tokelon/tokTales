package com.tokelon.toktales.core.render;

import com.tokelon.toktales.core.content.graphics.IRGBAColor;
import com.tokelon.toktales.core.render.shapes.ILineShape;
import com.tokelon.toktales.core.render.shapes.IPointShape;
import com.tokelon.toktales.core.render.shapes.IRectangleShape;
import com.tokelon.toktales.core.render.shapes.ITriangleShape;

public interface IShapeRenderer extends IChunkRenderer {
	// TODO: Use IShapePaint object for properties below


	public void setColor(IRGBAColor color);
	
	public void setFill(boolean fill);
	
	public void setOutlineWidth(float width);

	public void setOutlineType(int type);
	
	
	
	//public void drawShape(IRenderShape shape);
	
	
	public void drawPointShape(IPointShape pointShape);
	
	public void drawPoint(float x, float y, float pointSize);
	

	public void setLineAlignment(int type);
	
	public void setLineCenterAlignment(int type);
	
	//public void setLineWidth(float width);	// Keep this in the method or add as separate (conflict with lineshape)
	
	public void drawLineShape(ILineShape lineShape);
	
	public void drawLine(float x1, float y1, float x2, float y2, float lineWidth);	// drawLineShape()
	//public void drawLine(float x1, float y1, float x2, float y2, IShapePaint paint);


	//public void setTriangleOutlineWidth(float width);
	//public void setTriangleFill(boolean fill);
	
	public void drawTriangleShape(ITriangleShape triangleShape);
	
	public void drawTriangle(float x1, float y1, float x2, float y2, float x3, float y3);
	//public void drawTriangle(Vector2f v0, Vector2f v1, Vector2f v2);
	//public void drawTriangle(IPoint2f p0, IPoint2f p1, IPoint2f p2);
	
	
	public void drawRectangleShape(IRectangleShape rectangleShape);
	
	public void drawRectangle(float x, float y, float hLength, float vLength);
	
	public void drawRectangle(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4);
	
}
