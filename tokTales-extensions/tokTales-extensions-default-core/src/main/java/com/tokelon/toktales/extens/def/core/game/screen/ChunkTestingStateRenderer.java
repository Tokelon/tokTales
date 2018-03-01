package com.tokelon.toktales.extens.def.core.game.screen;

import java.io.File;

import com.tokelon.toktales.core.content.IAssetContainer;
import com.tokelon.toktales.core.content.RGBAColorImpl;
import com.tokelon.toktales.core.content.text.ITextureFont;
import com.tokelon.toktales.core.engine.content.ContentException;
import com.tokelon.toktales.core.engine.content.ContentLoadException;
import com.tokelon.toktales.core.engine.content.IContentService;
import com.tokelon.toktales.core.engine.render.ISurfaceHandler;
import com.tokelon.toktales.core.game.screen.AbstractStateRender;
import com.tokelon.toktales.core.game.screen.order.IRenderOrder;
import com.tokelon.toktales.core.game.states.IGameState;
import com.tokelon.toktales.core.render.CharRenderer;
import com.tokelon.toktales.core.render.IRenderTexture;
import com.tokelon.toktales.core.render.ImageRenderer;
import com.tokelon.toktales.core.render.ShapeRenderer;
import com.tokelon.toktales.core.render.model.ILineModel;
import com.tokelon.toktales.core.render.model.IRectangleModel;
import com.tokelon.toktales.core.render.shapes.LineShape;
import com.tokelon.toktales.core.render.shapes.PointShape;
import com.tokelon.toktales.core.render.shapes.RectangleShape;
import com.tokelon.toktales.core.render.shapes.TriangleShape;
import com.tokelon.toktales.core.storage.utils.LocationImpl;

public class ChunkTestingStateRenderer extends AbstractStateRender {
	
	private static final double CALLBACK_RENDER = 0d;

	private static final String RENDER_DESCRIPTION = "ChunkTestingStateRenderer";
	
	private static final RGBAColorImpl COLOR_RED = RGBAColorImpl.createFromCode("FF0000");
	private static final RGBAColorImpl COLOR_GREEN = RGBAColorImpl.createFromCode("00FF00");
	private static final RGBAColorImpl COLOR_BLUE = RGBAColorImpl.createFromCode("0000FF");
	private static final RGBAColorImpl COLOR_WHITE = RGBAColorImpl.createFromCode("FFFFFF");
	private static final RGBAColorImpl COLOR_YELLOW = RGBAColorImpl.createFromCode("F4D942");
	private static final RGBAColorImpl COLOR_PINK = RGBAColorImpl.createFromCode("F442F4");
	private static final RGBAColorImpl COLOR_CYAN = RGBAColorImpl.createFromCode("42F4F4");
	
	
	private final CharRenderer charRenderer;
	private final ShapeRenderer shapeRenderer;
	private final ImageRenderer imageRenderer;
	
	private ITextureFont font;
	
	private IRenderTexture renderTexture01;
	
	
	private final IGameState gamestate;
	
	public ChunkTestingStateRenderer(ISurfaceHandler surfaceHandler, IGameState gamestate) {
		super(surfaceHandler);
		
		this.gamestate = gamestate;
		
		charRenderer = new CharRenderer(gamestate.getEngine().getRenderService().getRenderAccess());
		shapeRenderer = new ShapeRenderer(gamestate.getEngine().getRenderService().getRenderAccess());
		imageRenderer = new ImageRenderer(gamestate.getEngine().getRenderService().getRenderAccess());
		
		load();
		
		IRenderOrder renderOrder = gamestate.getRenderOrder();
		renderOrder.getStackForLayer(IRenderOrder.LAYER_BOTTOM).addCallbackAt(CALLBACK_RENDER, this);
	}

	public void setCharFont(ITextureFont font) {
		this.font = font;
	}
	
	
	private void load() {
		IContentService contentService = gamestate.getEngine().getContentService();
		
		LocationImpl location = new LocationImpl("graphics" + File.separator + "special" + File.separator);
		try {
			
			IAssetContainer<?> assetCont = contentService.loadGraphicAsset(location, "#spritenotfound.png");
			renderTexture01 = contentService.extractAssetTexture(assetCont);
		} catch (ContentLoadException e) {
			e.printStackTrace();
		} catch (ContentException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void renderCall(String layerName, double stackPosition) {
		//if(stackPosition == CALLBACK_RENDER)
		renderFrame();
	}
	
	@Override
	public String getDescription() {
		return RENDER_DESCRIPTION;
	}
	
	
	
	private void renderFrame() {
		/*
		charRenderer.setFont(font);
		charRenderer.setColor(COLOR_RED);
		charRenderer.setSize(32, 32);

		float position = 0;
		
		String str = ">load chunk_test";
		String str2 = "This was a triumph.";
		
		String text = str;
		for(int i=0; i < text.length(); i++) {
			charRenderer.setPosition(position, 0);
			charRenderer.drawChar(text.charAt(i));
			
			position += 32;
		}
		*/
		
		//drawLines();

		//drawPoints();

		//drawTriangles();
		
		//drawRectangles();
		
		/*
		drawRectangles();
		drawTriangles();
		drawLines();
		drawPoints();
		*/
		
		
		//testRectOuter();
		//testRectInner();

		//testRect();
		//testLine();

		//bottomLeftTest();
		
		//drawLines();
		
		//lineTest();
		
		
		//imageTest();
		
		/*
		ICamera camera = getGamestate().getCameraController().getCamera();
		
		shapeRenderer.setFill(false);
		shapeRenderer.setColor(COLOR_GREEN);
		shapeRenderer.setOutlineType(IRectangleModel.OUTLINE_TYPE_INNER);
		shapeRenderer.setOutlineWidth(4f);
		
		shapeRenderer.drawRectangle(
				0f, 0f,
				0f, camera.getHeight(),
				camera.getWidth(), camera.getHeight(),
				camera.getWidth(), 0f
				);
		*/
		
		/*
		testSmallRects();
		drawColorLiner();
		triangleTest();
		testLines();
		testRectCenter();
		rectTest();
		*/
	}
	
	
	private void imageTest() {
		
		if(renderTexture01 != null) {
			float p = 0f;
			float w = 32f;
			imageRenderer.drawImage(renderTexture01, p, p, p, w, w, w, w, p);
		}
	}
	
	
	private void lineTest() {
		
		

		shapeRenderer.setColor(COLOR_GREEN);
		shapeRenderer.setLineAlignment(ILineModel.ALIGNMENT_INNER);
		//shapeRenderer.setLineAlignment(ILineModel.ALIGNMENT_OUTER);
		
		//shapeRenderer.setLineAlignment(ILineModel.ALIGNMENT_CENTER);
		//shapeRenderer.setLineCenterAlignment(ILineModel.ALIGN_CENTER_IN);
		//shapeRenderer.setLineCenterAlignment(ILineModel.ALIGN_CENTER_OUT);
		
		shapeRenderer.drawLine(5f, 0f, 5f, 720f, 3.0f);
		//shapeRenderer.drawLine(5f, 0f, 1279f, 719f);
		
		

		shapeRenderer.setLineAlignment(ILineModel.ALIGNMENT_INNER);


		LineShape line = new LineShape();
		line.setShape(0f, 0f, 0f, 720f);
		line.setPosition(5f, 0f);
		line.setWidth(3f);
		
		shapeRenderer.setColor(COLOR_RED);
		shapeRenderer.drawLineShape(line);
		
		

		drawColorLiner();
	}
	
	private void rectTest() {
		shapeRenderer.setFill(false);
		float outline = 1.0f;
		shapeRenderer.setOutlineWidth(outline);
		shapeRenderer.setOutlineType(IRectangleModel.OUTLINE_TYPE_INNER);
		
		float w = 640f;//1280f;
		float h = 360f;//720f;
		

		shapeRenderer.setColor(COLOR_GREEN);
		shapeRenderer.drawRectangle(outline, outline, w - outline - outline, h - outline - outline);
		//shapeRenderer.drawRectangle(0f, 0f, 1280.0f, 720.0f);
		//shapeRenderer.drawRectangle(0f, 0f, 0f, 720f, 1280f, 720f, 1280f, 0f);

		

		RectangleShape rect = new RectangleShape();
		rect.setPosition(outline, outline);
		rect.setShape(w - outline - outline, h - outline - outline);
		
		shapeRenderer.setColor(COLOR_RED);
		shapeRenderer.drawRectangleShape(rect);
		
		
		
		shapeRenderer.setColor(COLOR_WHITE);
		shapeRenderer.drawPoint(0.0f, 0.0f, 1.0f);
	}
	
	private void triangleTest() {
		
		shapeRenderer.setFill(true);


		shapeRenderer.setColor(COLOR_WHITE);
		shapeRenderer.drawPoint(0.0f, 0.0f, 1.0f);
		
		float w = 360f;
		float o = 1f;
		

		TriangleShape triangle = new TriangleShape();
		triangle.setShape(0f, 0f, 0f, 1f, 1f, 1f);
		triangle.setSize(w - o - o);
		triangle.setPosition(0f +o, 0f +o);
		
		
		shapeRenderer.setColor(COLOR_RED);
		//shapeRenderer.drawTriangleShape(triangle);
		

		TriangleShape triangleAbs = new TriangleShape();
		triangleAbs.setShape(0f +o, 0f +o, 0f+o, w-o, w-o, w-o);
		
		
		shapeRenderer.setColor(COLOR_GREEN);
		shapeRenderer.drawTriangle(0f +o, 0f +o, 0f+o, w-o, w-o, w-o);


		shapeRenderer.setColor(COLOR_CYAN);
		shapeRenderer.drawTriangleShape(triangle);



	}
	
	
	private void drawColorLiner() {

		shapeRenderer.setColor(COLOR_RED);
		shapeRenderer.drawPoint(5f, 0f, 1.0f);
		
		shapeRenderer.setColor(COLOR_CYAN);
		shapeRenderer.drawPoint(4f, 0f, 1.0f);
		shapeRenderer.drawPoint(6f, 0f, 1.0f);
		
		shapeRenderer.setColor(COLOR_BLUE);
		shapeRenderer.drawPoint(3f, 0f, 1.0f);
		shapeRenderer.drawPoint(7f, 0f, 1.0f);
		
		shapeRenderer.setColor(COLOR_YELLOW);
		shapeRenderer.drawPoint(2f, 0f, 1.0f);
		shapeRenderer.drawPoint(8f, 0f, 1.0f);
		
		shapeRenderer.setColor(COLOR_PINK);
		shapeRenderer.drawPoint(1f, 0f, 1.0f);
		shapeRenderer.drawPoint(9f, 0f, 1.0f);

		shapeRenderer.setColor(COLOR_WHITE);
		shapeRenderer.drawPoint(0.0f, 0.0f, 1.0f);
		shapeRenderer.drawPoint(10.0f, 0.0f, 1.0f);
		
	}
	
	
	private void topLeftTest() {
		shapeRenderer.setColor(COLOR_BLUE);
		shapeRenderer.drawTriangle(0.0f, 0.0f, 0.0f, 10.0f, 10.0f, 0.0f);

		shapeRenderer.setColor(COLOR_GREEN);
		shapeRenderer.drawRectangle(0.0f, 0.0f, 2.0f, 2.0f);
		
		shapeRenderer.setColor(COLOR_RED);
		shapeRenderer.drawPoint(0.0f, 0.0f, 1.0f);
	}
	
	private void topRightTest() {
		shapeRenderer.setColor(COLOR_BLUE);
		shapeRenderer.drawTriangle(1280f - 10f, 0f, 1280f, 10f, 1280f, 0.0f);

		shapeRenderer.setColor(COLOR_GREEN);
		shapeRenderer.drawRectangle(1280f - 2f, 0f, 2.0f, 2.0f);
		
		shapeRenderer.setColor(COLOR_RED);
		shapeRenderer.drawPoint(1279f, 0f, 1.0f);
	}
	
	private void bottomRightTest() {
		shapeRenderer.setColor(COLOR_BLUE);
		shapeRenderer.drawTriangle(1280f - 10f, 720f, 1280f, 720f, 1280f, 720f - 10f);

		shapeRenderer.setColor(COLOR_GREEN);
		shapeRenderer.drawRectangle(1280.0f - 2.0f, 720.0f - 2.0f, 2.0f, 2.0f);
		
		shapeRenderer.setColor(COLOR_RED);
		shapeRenderer.drawPoint(1279.0f, 719.0f, 1.0f);
	}
	
	private void bottomLeftTest() {
		shapeRenderer.setColor(COLOR_BLUE);
		shapeRenderer.drawTriangle(0f, 720f - 10f, 0f, 720f, 10f, 720f);

		shapeRenderer.setColor(COLOR_GREEN);
		shapeRenderer.drawRectangle(0f, 720.0f - 2.0f, 2.0f, 2.0f);
		
		shapeRenderer.setColor(COLOR_RED);
		shapeRenderer.drawPoint(0f, 719.0f, 1.0f);
		
	}
	
	
	private void drawPoints() {

		shapeRenderer.setColor(COLOR_RED);

		PointShape point = new PointShape();
		point.setSize(1);
		point.setPosition(100.0f, 100.0f);
		
		shapeRenderer.drawPointShape(point);

		
		point.setRelativePosition(30.0f, 0.0f);
		point.setRelativeSize(2.0f);
		shapeRenderer.drawPointShape(point);
	}
	
	private void drawLines() {
		
		shapeRenderer.setColor(COLOR_RED);
		
		/*
		shapeRenderer.drawLine(50.0f, 50.0f, 500.0f, 50.0f);
		shapeRenderer.drawLine(500.0f, 50.0f - 1, 500.0f, 500.0f + 1);
		shapeRenderer.drawLine(500.0f, 500.0f, 50.0f, 500.0f);
		shapeRenderer.drawLine(50.0f, 500.0f + 1, 50.0f, 50.0f - 1);
		*/
		
		
		shapeRenderer.drawLine(100.0f, 100.0f, 500.0f, 500.0f, 2.0f);
		
		
		shapeRenderer.setColor(COLOR_GREEN);

		LineShape line = new LineShape();
		line.setShape(0.0f, 0.0f, 1.0f, 1.0f);
		line.setSize(400.0f);
		line.setPosition(100.0f, 100.0f);
		
		shapeRenderer.drawLineShape(line);


		shapeRenderer.setColor(COLOR_BLUE);

		line.setRelativePosition(30.0f, 0.0f);
		line.setRelativeSize(50.0f);
		shapeRenderer.drawLineShape(line);
	}
	
	private void drawTriangles() {

		shapeRenderer.setFill(false);
		shapeRenderer.setOutlineWidth(5.0f);
		
		shapeRenderer.setColor(COLOR_RED);
		
		shapeRenderer.drawTriangle(300.0f, 50.0f, 50.0f, 550.0f, 550.0f, 550.0f);
		
		
		shapeRenderer.setColor(COLOR_GREEN);

		TriangleShape triangle = new TriangleShape();
		//triangle.setShape(0.5f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
		triangle.setSize(500.0f);
		triangle.setPosition(50.0f, 50.0f);
		
		shapeRenderer.drawTriangleShape(triangle);

		
		shapeRenderer.setColor(COLOR_BLUE);

		triangle.setRelativePosition(30.0f, 0.0f);
		triangle.setRelativeSize(50.0f);
		shapeRenderer.drawTriangleShape(triangle);
	}
	
	private void drawRectangles() {
		
		shapeRenderer.setFill(false);
		shapeRenderer.setOutlineWidth(5.0f);
		
		shapeRenderer.setColor(COLOR_RED);

		shapeRenderer.drawRectangle(50.0f, 50.0f, 400.0f, 400.0f);
		
		
		shapeRenderer.setColor(COLOR_GREEN);
		
		
		RectangleShape rectangle = new RectangleShape();
		rectangle.setShape(1.0f, 1.0f);
		rectangle.setSize(400.0f);
		rectangle.setPosition(50.0f, 50.0f);
		
		shapeRenderer.drawRectangleShape(rectangle);
		
		
		shapeRenderer.setColor(COLOR_BLUE);
		
		rectangle.setRelativePosition(30.0f, 0.0f);
		rectangle.setRelativeSize(50.0f);
		shapeRenderer.drawRectangleShape(rectangle);
	}
	
	private void testLine() {
		shapeRenderer.setColor(COLOR_GREEN);
		
		shapeRenderer.drawLine(0.0f, 0.0f, 0.0f, 100.0f, 1.0f);
		//shapeRenderer.drawLine(1279.1f, 0.0f, 1279.1f, 100.0f);
		//shapeRenderer.drawLine(1279.5f, 0.0f, 1279.5f, 100.0f);
		

		shapeRenderer.setColor(COLOR_RED);
		shapeRenderer.drawPoint(0.0f, 0.0f, 1.0f);
	}
	
	private void testLineShape() {
		
		shapeRenderer.setColor(COLOR_GREEN);

		LineShape line = new LineShape();
		line.setShape(0f, 0f, 1f, 1f);
		line.setPosition(10.0f, 10.0f);
		line.setSize(1);
		
		
		shapeRenderer.drawLineShape(line);
	}
	
	private void testLines() {

		shapeRenderer.setColor(COLOR_GREEN);
		
		shapeRenderer.setLineAlignment(ILineModel.ALIGNMENT_INNER);
		
		//shapeRenderer.drawLine(0.0f, 0.0f, 1280.0f, 720.0f);
		shapeRenderer.drawLine(0.0f, 0.0f, 1279.0f, 0.0f, 1.0f);
		
		float t = 0.0f;
		//shapeRenderer.drawLine(0.0f + t, 0.0f + t, 0.0f + t, 719.0f - t);
		
		shapeRenderer.setLineAlignment(ILineModel.ALIGNMENT_OUTER);
		//shapeRenderer.drawLine(0.0f + t, 720.0f - t, 1280.0f - t, 720.0f - t);
		//shapeRenderer.drawLine(1280.0f - t, 720.0f - t, 1280.0f - t, 0.0f + t);
		//shapeRenderer.drawLine(1280.0f - t, 0.0f + t, 0.0f + t, 0.0f + t);
		
		
		shapeRenderer.setColor(COLOR_RED);
		shapeRenderer.drawPoint(0.0f, 0.0f, 1.0f);
		shapeRenderer.drawPoint(1279, 719, 1);
		
	}
	
	
	private void testRect() {
		shapeRenderer.setFill(false);
		float outline = 10.0f;
		shapeRenderer.setOutlineWidth(outline);
		
		shapeRenderer.setOutlineType(IRectangleModel.OUTLINE_TYPE_INNER);
		shapeRenderer.setColor(COLOR_GREEN);
		shapeRenderer.drawRectangle(outline, outline, 1280.0f - outline - outline, 720.0f - outline - outline);
		
		shapeRenderer.setOutlineType(IRectangleModel.OUTLINE_TYPE_OUTER);
		shapeRenderer.setColor(COLOR_BLUE);
		shapeRenderer.drawRectangle(outline, outline, 1280.0f - outline - outline, 720.0f - outline - outline);

		shapeRenderer.setColor(COLOR_RED);
		shapeRenderer.drawPoint(0.0f, 0.0f, 1.0f);
		
		//shapeRenderer.setColor(COLOR_BLUE);
		//shapeRenderer.drawRectangle(32.0f, 0.0f, 32.0f, 32.0f);

		//shapeRenderer.setColor(COLOR_WHITE);
		//shapeRenderer.drawPoint(0.0f, 0.0f, 1.0f);
	}
	
	private void testRectOuter() {
		shapeRenderer.setFill(false);
		float outline = 15.0f;
		shapeRenderer.setOutlineWidth(10.0f);
		
		shapeRenderer.setOutlineType(IRectangleModel.OUTLINE_TYPE_INNER);
		shapeRenderer.setColor(COLOR_GREEN);
		shapeRenderer.drawRectangle(0.0f, 0.0f, 1280.0f, 720.0f);
		
		shapeRenderer.setOutlineType(IRectangleModel.OUTLINE_TYPE_OUTER);
		shapeRenderer.setColor(COLOR_BLUE);
		shapeRenderer.drawRectangle(outline, outline, 1280.0f - outline - outline, 720.0f - outline - outline);

		shapeRenderer.setColor(COLOR_RED);
		shapeRenderer.drawPoint(5.0f, 5.0f, 1.0f);
	}
	
	private void testRectInner() {
		shapeRenderer.setFill(false);
		float outline = 15.0f;
		shapeRenderer.setOutlineWidth(9.0f);
		
		shapeRenderer.setOutlineType(IRectangleModel.OUTLINE_TYPE_INNER);
		shapeRenderer.setColor(COLOR_GREEN);
		shapeRenderer.drawRectangle(0.0f, 0.0f, 1280.0f, 720.0f);
		
		shapeRenderer.setOutlineType(IRectangleModel.OUTLINE_TYPE_INNER);
		shapeRenderer.setColor(COLOR_BLUE);
		shapeRenderer.drawRectangle(outline, outline, 1280.0f - outline - outline, 720.0f - outline - outline);

		shapeRenderer.setColor(COLOR_RED);
		shapeRenderer.drawPoint(5.0f, 5.0f, 1.0f);
	}
	
	private void testRectCenter() {

		shapeRenderer.setFill(false);
		float outline = 10.0f;
		shapeRenderer.setOutlineWidth(outline);
		
		shapeRenderer.setOutlineType(IRectangleModel.OUTLINE_TYPE_OUTER);
		shapeRenderer.setColor(COLOR_GREEN);
		shapeRenderer.drawRectangle(outline, outline, 1280.0f - outline - outline, 720.0f - outline - outline);
		
		shapeRenderer.setOutlineType(IRectangleModel.OUTLINE_TYPE_CENTER);
		shapeRenderer.setColor(COLOR_BLUE);
		shapeRenderer.drawRectangle(outline, outline, 1280.0f - outline - outline, 720.0f - outline - outline);

		
		// Verify rect
		shapeRenderer.setOutlineWidth(10.0f);
		outline = 5.0f;
		shapeRenderer.setOutlineType(IRectangleModel.OUTLINE_TYPE_INNER);
		shapeRenderer.setColor(COLOR_RED);
		//shapeRenderer.drawRectangle(outline, outline, 1280.0f - outline - outline, 720.0f - outline - outline);

		
		shapeRenderer.setColor(COLOR_WHITE);
		shapeRenderer.drawPoint(5.0f, 5.0f, 1.0f);
	}
	
	private void testRectShape() {
		shapeRenderer.setFill(false);
		float outline = 10.0f;
		shapeRenderer.setOutlineWidth(outline);
		
		RectangleShape rect = new RectangleShape();
		//rect.setShape(1280.0f - outline - outline, 720.0f - outline - outline);	// Absolute coordinates
		
		float normalize = 1280.0f - outline - outline;
		rect.setShape((1280.0f - outline - outline) / normalize, (720.0f - outline - outline) / normalize);
		rect.setSize(1280 - outline - outline);
		rect.setPosition(outline, outline);
		
		
		shapeRenderer.setOutlineType(IRectangleModel.OUTLINE_TYPE_INNER);
		shapeRenderer.setColor(COLOR_GREEN);
		shapeRenderer.drawRectangleShape(rect);
		
		shapeRenderer.setOutlineType(IRectangleModel.OUTLINE_TYPE_OUTER);
		shapeRenderer.setColor(COLOR_BLUE);
		shapeRenderer.drawRectangleShape(rect);

		
		shapeRenderer.setColor(COLOR_RED);
		shapeRenderer.drawPoint(0.0f, 0.0f, 1.0f);
	}
	
	private void testSmallRects() {

		shapeRenderer.setFill(false);
		shapeRenderer.setOutlineWidth(1.0f);
		shapeRenderer.setOutlineType(IRectangleModel.OUTLINE_TYPE_OUTER);

		
		RectangleShape rect = new RectangleShape();
		rect.setShape(32f, 32f);
		// Can't use set size here, because it will create a 1x1 rect then scale it by scaling the line width as well,
		// and that results in a filled rect
		//rect.setSize(32f);
		
		shapeRenderer.setColor(COLOR_RED);
		shapeRenderer.drawRectangleShape(rect);

		
		rect.setPosition(32.0f, 0.0f);
		shapeRenderer.setColor(COLOR_BLUE);
		shapeRenderer.drawRectangleShape(rect);
		
		shapeRenderer.setColor(COLOR_RED);
		shapeRenderer.drawPoint(0.0f, 0.0f, 1.0f);
	}
	

	@Override
	protected void onSurfaceCreated() {
		charRenderer.contextCreated();
		shapeRenderer.contextCreated();
		imageRenderer.contextCreated();
	}

	@Override
	protected void onSurfaceChanged() {
		charRenderer.contextChanged(getViewTransformer(), getProjectionMatrix());
		shapeRenderer.contextChanged(getViewTransformer(), getProjectionMatrix());
		imageRenderer.contextChanged(getViewTransformer(), getProjectionMatrix());
	}

	@Override
	protected void onSurfaceDestroyed() {
		// Nothing
	}

	
}
