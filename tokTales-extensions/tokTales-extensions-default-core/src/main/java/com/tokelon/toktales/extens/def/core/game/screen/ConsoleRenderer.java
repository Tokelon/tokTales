package com.tokelon.toktales.extens.def.core.game.screen;

import org.joml.Matrix4f;
import org.joml.Vector4f;

import com.tokelon.toktales.core.content.text.ITextureFont;
import com.tokelon.toktales.core.engine.IEngine;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.render.IRenderService;
import com.tokelon.toktales.core.game.controller.IConsoleController;
import com.tokelon.toktales.core.game.model.IConsole;
import com.tokelon.toktales.core.game.model.Point2fImpl;
import com.tokelon.toktales.core.game.model.Point2iImpl;
import com.tokelon.toktales.core.game.model.Rectangle2fImpl;
import com.tokelon.toktales.core.game.model.Rectangle2iImpl;
import com.tokelon.toktales.core.game.screen.ISegmentRenderer;
import com.tokelon.toktales.core.game.screen.view.DefaultViewGridTransformer;
import com.tokelon.toktales.core.game.screen.view.IViewGridTransformer;
import com.tokelon.toktales.core.game.screen.view.IViewTransformer;
import com.tokelon.toktales.core.game.states.GameStateSuppliers;
import com.tokelon.toktales.core.game.states.IGameState;
import com.tokelon.toktales.core.game.world.IWorld;
import com.tokelon.toktales.core.render.IRenderDriver;
import com.tokelon.toktales.core.render.ITexture;
import com.tokelon.toktales.core.render.ITextureCoordinator;
import com.tokelon.toktales.core.render.RenderException;
import com.tokelon.toktales.core.render.model.ITextureFontModel;
import com.tokelon.toktales.core.render.model.TextureFontModel;
import com.tokelon.toktales.core.util.INamedOptions;
import com.tokelon.toktales.core.util.NamedOptionsImpl;
import com.tokelon.toktales.core.util.function.Supplier;
import com.tokelon.toktales.extens.def.core.values.ControllerExtensionsValues;

public class ConsoleRenderer implements ISegmentRenderer {

	public static final String TAG = "ConsoleRenderer";

	
	public static final float CHAR_HOR_DISTANCE_MODIFIER_BASED_ON_TILESIZE = 0;//- 0.25f;
	
	private final Matrix4f matrixProjection = new Matrix4f();
	private final Matrix4f matrixView = new Matrix4f();
	private final Matrix4f matrixProjectionAndView = new Matrix4f();

	private final Rectangle2fImpl cameraBounds = new Rectangle2fImpl();
	private final Rectangle2iImpl cameraGridBounds = new Rectangle2iImpl();
	
	private final DrawingMeta dmeta = new DrawingMeta();


	private final TextureFontModel fontModel = new TextureFontModel();
	
	private final NamedOptionsImpl drawingOptions = new NamedOptionsImpl();
	
	
	private IViewTransformer viewTransformer;
	private IViewGridTransformer gridTransformer;
	
	private IRenderDriver fontDriver;

	
	private final ILogger logger;
	private final IRenderService renderService;
	private final IWorld world;
	private final Supplier<ITextureCoordinator> textureCoordinatorSupplier;
	private final Supplier<IConsoleController> consoleControllerSupplier;
	
	public ConsoleRenderer(
			ILogger logger,
			IEngine engine,
			IWorld world,
			Supplier<ITextureCoordinator> textureCoordinatorSupplier,
			Supplier<IConsoleController> consoleControllerSupplier
	) {
		this.logger = logger;
		this.renderService = engine.getRenderService();
		this.world = world;
		this.textureCoordinatorSupplier = textureCoordinatorSupplier;
		this.consoleControllerSupplier = consoleControllerSupplier;

		
		fontModel.setInvertYAxis(true);
		fontModel.setTargetColor(new Vector4f(0.0f, 1.0f, 0.0f, 0.0f));
	}
	
	
	@Override
	public void contextCreated() {
		
		fontDriver = renderService.getRenderAccess().requestDriver(ITextureFontModel.class.getName());
		if(fontDriver == null) {
			throw new RenderException("No render driver found for: " +ITextureFontModel.class.getName());
		}
		
		fontDriver.create();
		
		
		fontModel.setTextureCoordinator(textureCoordinatorSupplier.get());
	}

	
	@Override
	public void contextChanged(IViewTransformer viewTransformer, Matrix4f projectionMatrix) {
		this.viewTransformer = viewTransformer;
		this.gridTransformer = new DefaultViewGridTransformer(world.getGrid(), viewTransformer);
		
		matrixProjection.set(projectionMatrix);
		
		matrixView.lookAt(
				0.0f, 0.0f, 0.0f,
				0.0f, 0.0f, -1.0f,
				0.0f, 1.0f, 0.0f);
		
		matrixProjection.mul(matrixView, matrixProjectionAndView);
	}

	
	@Override
	public void contextDestroyed() {
		if(fontDriver != null) {
			fontDriver.destroy();
			fontDriver = null;
		}
		
		fontModel.setTextureCoordinator(null);
		
		gridTransformer = null;
		viewTransformer = null;
	}
	
	
	
	@Override
	public void prepare(long currentTimeMillis) {
		if(gridTransformer == null) {
			assert false : "Cannot prepare without view";
			return;
		}
		
		// Those are equal
		//dmeta.tilePixeSize = gridTransformer.getGridTileSize() * viewTransformer.getCameraToViewportScale();
		dmeta.tilePixeSize = gridTransformer.getScreenTileSize();
	}

	
	@Override
	public void drawLayer(INamedOptions options, String layerName) {
		// Nothing
	}

	
	@Override
	public void drawFull(INamedOptions options) {
		if(gridTransformer == null) {
			assert false : "Cannot draw without view";
			return;
		}
		
		
		IConsoleController consoleController = consoleControllerSupplier.get();
		if(consoleController == null) {
			logger.i(TAG, "Draw was called but no console is available");
			return;
		}
		
		
		IConsole console = consoleController.getConsole();
		ITextureFont font = consoleController.getFont();
		if(font == null) {
			return; // Can't render without a font
		}
		
		
		fontDriver.use(matrixProjectionAndView);

		
		gridTransformer.getGrid().worldToGrid(viewTransformer.getCurrentCamera().getWorldBounds(cameraBounds), cameraGridBounds);
		
		int blocksVertical = cameraGridBounds.height() == 0 ? 0 : Math.abs(cameraGridBounds.bottom() - cameraGridBounds.top()) + 1;
		int blocksHorizontal = cameraGridBounds.width() == 0 ? 0 : Math.abs(cameraGridBounds.right() - cameraGridBounds.left()) + 1;
		
		
		
		
		String consolePrompt = console.getPrompt();
		
		int promptCodepointCount = consolePrompt.codePointCount(0, consolePrompt.length());
		
		int currentHistoryIndex = cameraGridBounds.top();
		int currentHistoryCodepointIndex = 0;
		int currentCodepointIndex = 0;
		
		boolean end = false;
		for(int y = 0; y < blocksVertical && !end; y++) {
			for(int x = 0; x < blocksHorizontal && !end; x++) {
				
				
				dmeta.gridPosition.x = cameraGridBounds.left() + x;
				dmeta.gridPosition.y = cameraGridBounds.top() + y;
				
				
				int targetCodepoint;

				if(currentHistoryIndex == console.getHistoryCount()) {	// We are at the input line
					
					if(currentCodepointIndex >= console.getCodepointCount() + promptCodepointCount) {
						end = true;
						continue;
					}
					else if(currentCodepointIndex < promptCodepointCount) {
						// Draw the prompt char
						
						targetCodepoint = consolePrompt.codePointAt(currentCodepointIndex);
					}
					else {
						int codepointPos = currentCodepointIndex - promptCodepointCount; //x - promptCodepointCount;

						if(codepointPos < console.getCodepointCount()) {
							targetCodepoint = console.getCodepointAt(codepointPos);
						}
						else {
							// We finished the current input
							end = true;
							continue;
						}
					}
					
					currentCodepointIndex++;
				}
				else {

					if(currentHistoryIndex < 0) {
						currentHistoryIndex++;
						continue;
					}
					else if(currentHistoryIndex >= console.getHistoryCount()) {
						end = true;
						continue;
					}
					else {
						
						String historyAt = console.getHistoryAt(currentHistoryIndex);

						if(currentHistoryCodepointIndex >= historyAt.codePointCount(0, historyAt.length())) {
							currentHistoryIndex++;
							currentHistoryCodepointIndex = 0;
							break;
						}
						
						
						targetCodepoint = console.getHistoryCodepoint(currentHistoryIndex, currentHistoryCodepointIndex);
						
						currentHistoryCodepointIndex++;
					}
				}

				
				ITexture texture = font.getCodepointTexture(targetCodepoint);
				
				
				
				// bottom is yoffset
				// right is xoffset ?
				// top - bottom = height
				// right - left = width
				int textureOffsetX = font.getCodepointBitmapOffsetX(targetCodepoint);
				int textureOffsetY = font.getCodepointBitmapOffsetY(targetCodepoint);
				
				// fontheight - desc + yoff
				//int posTop = asc + codepointyoff;
				
				int bitmapTop = textureOffsetY;
				int bitmapLeft = textureOffsetX;
				
				
				int fontPixelHeight = font.getFontPixelHeight();
				float texToTileScale = dmeta.tilePixeSize / (float)fontPixelHeight;
				
				float tileTop = texToTileScale * (float)bitmapTop;
				float tileLeft = texToTileScale * (float)bitmapLeft;
				
				gridTransformer.visibleScreenBoundsForTile(dmeta.gridPosition, dmeta.tileSourceBounds);
				//dmeta.tileSourceBounds.set(0, tileTop, (texToTileScale * texture.getWidth()), tileTop + (texToTileScale * texture.getHeight()));
				//dmeta.tileSourceBounds.set(0, 0, texToTileScale * texture.getWidth(), texToTileScale * texture.getHeight());
				
				dmeta.tileTranslation.set(viewTransformer.getCurrentViewport().transformX(x * dmeta.tilePixeSize + x * dmeta.tilePixeSize * CHAR_HOR_DISTANCE_MODIFIER_BASED_ON_TILESIZE), viewTransformer.getCurrentViewport().transformY(y * dmeta.tilePixeSize));
				
				configModel();

				
				int texWidth = font.getCodepointPixelWidth(targetCodepoint);
				int textHeight = font.getCodepointPixelHeight(targetCodepoint);
				
				// Scale to the actual texture size we need
				// To we need to scale to the viewport as well ?
				fontModel.setScaling2D(texToTileScale * texWidth, texToTileScale * textHeight);
				fontModel.translate2D(tileLeft, tileTop);	// Translate the codepoint glyph to the right y offset
				
				
				fontModel.setTargetTexture(texture);

				
				//drawingOptions.set(RenderDriverOptions.DRAWING_OPTION_IGNORE_SPRITESET, false);
				
				fontDriver.draw(fontModel, drawingOptions);
				
			}
		}
		
		
		fontDriver.release();
	}
	
	
	private void configModel() {
		// If only a part of the sprite should be rendered
		if(dmeta.tileSourceBounds.width() != dmeta.tilePixeSize || dmeta.tileSourceBounds.height() != dmeta.tilePixeSize) {
			
			fontModel.setScaling2D(dmeta.tileSourceBounds.width(), dmeta.tileSourceBounds.height());
			
			fontModel.setTranslation(
					dmeta.tileTranslation.x + dmeta.tileSourceBounds.left(),
					dmeta.tileTranslation.y + dmeta.tileSourceBounds.top(),
					//0.0f);
					(float)dmeta.layerDepth);
			
			// Normalize by dividing	// TODO: Replace with setTextureScaling instead of getTextureScaling and then setting the values
			fontModel.getTextureScaling().set(
					dmeta.tileSourceBounds.width() / dmeta.tilePixeSize,
					dmeta.tileSourceBounds.height() / dmeta.tilePixeSize);
			
			fontModel.getTextureTranslation().set(
					dmeta.tileSourceBounds.left() / dmeta.tilePixeSize,	//dmeta.tileTranslation.x / dmeta.tilePixeSize, WRONG
					dmeta.tileSourceBounds.top() / dmeta.tilePixeSize);	//dmeta.tileTranslation.y / dmeta.tilePixeSize);

		}
		else {
			fontModel.setScaling2D(dmeta.tilePixeSize, dmeta.tilePixeSize);
			fontModel.setTranslation(dmeta.tileTranslation.x, dmeta.tileTranslation.y, (float)dmeta.layerDepth);
			
			fontModel.setTextureScaling(1.0f, 1.0f);
			fontModel.setTextureTranslation(0.0f, 0.0f);
		}
	}
	
	
	private class DrawingMeta {
		
		private float tilePixeSize = 0.0f;
		private int layerDepth = 0;

		
		private final Point2iImpl gridPosition = new Point2iImpl();
		private final Rectangle2fImpl tileSourceBounds = new Rectangle2fImpl();
		private final Point2fImpl tileTranslation = new Point2fImpl();
	}
	
	
	public static class ConsoleRendererFactory {
		
		public ConsoleRenderer create(
				IEngineContext engineContext,
				Supplier<ITextureCoordinator> textureCoordinatorSupplier,
				Supplier<IConsoleController> consoleControllerSupplier
		) {
			return new ConsoleRenderer(
					engineContext.getLog(),
					engineContext.getEngine(),
					engineContext.getGame().getWorld(),
					textureCoordinatorSupplier,
					consoleControllerSupplier
			);
		}
		
		public ConsoleRenderer createForGamestate(IGameState gamestate) {
			return new ConsoleRenderer(
					gamestate.getLog(),
					gamestate.getEngine(),
					gamestate.getGame().getWorld(),
					() -> gamestate.getStateRender().getTextureCoordinator(),
					GameStateSuppliers.ofControllerFromManager(gamestate, ControllerExtensionsValues.CONTROLLER_CONSOLE, IConsoleController.class)
			);
		}
		
		public ConsoleRenderer createForGamestate(IGameState gamestate, Supplier<IConsoleController> consoleControllerSupplier) {
			return new ConsoleRenderer(
					gamestate.getLog(),
					gamestate.getEngine(),
					gamestate.getGame().getWorld(),
					() -> gamestate.getStateRender().getTextureCoordinator(),
					consoleControllerSupplier
			);
		}
	}
	
}
