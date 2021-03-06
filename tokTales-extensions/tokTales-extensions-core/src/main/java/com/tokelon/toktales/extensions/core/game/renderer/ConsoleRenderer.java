package com.tokelon.toktales.extensions.core.game.renderer;

import org.joml.Matrix4f;
import org.joml.Vector4f;

import com.tokelon.toktales.core.content.manage.codepoint.ICodepointAsset;
import com.tokelon.toktales.core.content.manage.codepoint.ICodepointAssetManager;
import com.tokelon.toktales.core.content.text.ICodepoint;
import com.tokelon.toktales.core.content.text.IFont;
import com.tokelon.toktales.core.engine.IEngine;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.log.ILogging;
import com.tokelon.toktales.core.engine.render.IRenderService;
import com.tokelon.toktales.core.game.controller.IConsoleController;
import com.tokelon.toktales.core.game.model.IConsole;
import com.tokelon.toktales.core.game.model.Point2fImpl;
import com.tokelon.toktales.core.game.model.Point2iImpl;
import com.tokelon.toktales.core.game.model.Rectangle2fImpl;
import com.tokelon.toktales.core.game.model.Rectangle2iImpl;
import com.tokelon.toktales.core.game.state.GameStateSuppliers;
import com.tokelon.toktales.core.game.state.IGameState;
import com.tokelon.toktales.core.game.world.IWorld;
import com.tokelon.toktales.core.render.FontTextSizeHelper;
import com.tokelon.toktales.core.render.IRenderDriver;
import com.tokelon.toktales.core.render.RenderException;
import com.tokelon.toktales.core.render.model.FontModel;
import com.tokelon.toktales.core.render.model.IFontModel;
import com.tokelon.toktales.core.render.renderer.ISingleRenderer;
import com.tokelon.toktales.core.render.texture.ITextureCoordinator;
import com.tokelon.toktales.core.screen.view.DefaultViewGridTransformer;
import com.tokelon.toktales.core.screen.view.IViewGridTransformer;
import com.tokelon.toktales.core.screen.view.IViewTransformer;
import com.tokelon.toktales.extensions.core.values.ControllerExtensionsValues;
import com.tokelon.toktales.tools.core.inject.ISupplier;
import com.tokelon.toktales.tools.core.objects.options.INamedOptions;
import com.tokelon.toktales.tools.core.objects.options.NamedOptionsImpl;

public class ConsoleRenderer implements ISingleRenderer {


	public static final float CHAR_HOR_DISTANCE_MODIFIER_BASED_ON_TILESIZE = 0;//- 0.25f;
	
	private final Matrix4f matrixProjection = new Matrix4f();
	private final Matrix4f matrixView = new Matrix4f();
	private final Matrix4f matrixProjectionAndView = new Matrix4f();

	private final Rectangle2fImpl cameraBounds = new Rectangle2fImpl();
	private final Rectangle2iImpl cameraGridBounds = new Rectangle2iImpl();
	
	private final DrawingMeta dmeta = new DrawingMeta();


	private final FontModel fontModel = new FontModel();
	
	private final NamedOptionsImpl drawingOptions = new NamedOptionsImpl();
	
	
	private IViewTransformer viewTransformer;
	private IViewGridTransformer gridTransformer;
	
	private IRenderDriver fontDriver;

	
	private final ILogger logger;
	private final IRenderService renderService;
	private final IWorld world;
	private final ICodepointAssetManager codepointAssetManager;
	private final ISupplier<ITextureCoordinator> textureCoordinatorSupplier;
	private final ISupplier<IConsoleController> consoleControllerSupplier;
	
	public ConsoleRenderer(
			ILogging logging,
			IEngine engine,
			IWorld world,
			ICodepointAssetManager codepointAssetManager,
			ISupplier<ITextureCoordinator> textureCoordinatorSupplier,
			ISupplier<IConsoleController> consoleControllerSupplier
	) {
		this.logger = logging.getLogger(getClass());
		this.codepointAssetManager = codepointAssetManager;
		this.renderService = engine.getRenderService();
		this.world = world;
		this.textureCoordinatorSupplier = textureCoordinatorSupplier;
		this.consoleControllerSupplier = consoleControllerSupplier;

		
		fontModel.setInvertYAxis(true);
		fontModel.setTargetColor(new Vector4f(0.0f, 1.0f, 0.0f, 0.0f));
	}
	
	
	@Override
	public void contextCreated() {
		
		fontDriver = renderService.getRenderAccess().requestDriver(IFontModel.class.getName());
		if(fontDriver == null) {
			throw new RenderException("No render driver found for: " +IFontModel.class.getName());
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
	public void renderContents(INamedOptions renderOptions) {
		if(gridTransformer == null) {
			assert false : "Cannot draw without view";
			return;
		}
		
		
		IConsoleController consoleController = consoleControllerSupplier.get();
		if(consoleController == null) {
			logger.info("Draw was called but no console is available");
			return;
		}
		
		
		IConsole console = consoleController.getConsole();
		IFont font = consoleController.getFont();
		if(font == null) {
			return; // Can't render without a font
		}
		

		// Those are equal
		//dmeta.tilePixeSize = gridTransformer.getGridTileSize() * viewTransformer.getCameraToViewportScale();
		dmeta.tilePixeSize = gridTransformer.getScreenTileSize();

		
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

				
				// TODO: Use dmeta.tilePixeSize for fontTargetPixelHeight ? What about scaling logic in here?
				float textBoxTextPixelHeight = viewTransformer.cameraToViewportY(dmeta.tilePixeSize);
				float targetFontPixelHeight = FontTextSizeHelper.getBestFontPixelHeight(font, textBoxTextPixelHeight);
				ICodepointAsset codepointAsset = codepointAssetManager.getCodepointAsset(font, targetCodepoint, targetFontPixelHeight); 
				if(!codepointAssetManager.isAssetValid(codepointAsset)) {
					continue;
				}
				
				ICodepoint codepoint = codepointAsset.getCodepoint();
				
				
				// bottom is yoffset
				// right is xoffset ?
				// top - bottom = height
				// right - left = width
				int textureOffsetX = codepoint.getBitmapOffsetX();
				int textureOffsetY = codepoint.getBitmapOffsetY();
				
				// fontheight - desc + yoff
				//int posTop = asc + codepointyoff;
				
				int bitmapTop = textureOffsetY;
				int bitmapLeft = textureOffsetX;
				
				
				float fontPixelHeight = codepoint.getFontPixelHeight();
				float texToTileScale = dmeta.tilePixeSize / fontPixelHeight;
				
				float tileTop = texToTileScale * (float)bitmapTop;
				float tileLeft = texToTileScale * (float)bitmapLeft;
				
				gridTransformer.visibleScreenBoundsForTile(dmeta.gridPosition, dmeta.tileSourceBounds);
				//dmeta.tileSourceBounds.set(0, tileTop, (texToTileScale * texture.getWidth()), tileTop + (texToTileScale * texture.getHeight()));
				//dmeta.tileSourceBounds.set(0, 0, texToTileScale * texture.getWidth(), texToTileScale * texture.getHeight());
				
				dmeta.tileTranslation.set(viewTransformer.getCurrentViewport().transformX(x * dmeta.tilePixeSize + x * dmeta.tilePixeSize * CHAR_HOR_DISTANCE_MODIFIER_BASED_ON_TILESIZE), viewTransformer.getCurrentViewport().transformY(y * dmeta.tilePixeSize));
				
				configModel();

				
				int texWidth = codepoint.getPixelWidth();
				int textHeight = codepoint.getPixelHeight();
				
				// Scale to the actual texture size we need
				// To we need to scale to the viewport as well ?
				fontModel.setScaling2D(texToTileScale * texWidth, texToTileScale * textHeight);
				fontModel.translate2D(tileLeft, tileTop);	// Translate the codepoint glyph to the right y offset
				
				
				fontModel.setTargetTexture(codepoint.getTexture());

				
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
				ISupplier<ITextureCoordinator> textureCoordinatorSupplier,
				ISupplier<IConsoleController> consoleControllerSupplier
		) {
			return new ConsoleRenderer(
					engineContext.getLogging(),
					engineContext.getEngine(),
					engineContext.getGame().getWorld(),
					engineContext.getGame().getContentManager().getCodepointAssetManager(),
					textureCoordinatorSupplier,
					consoleControllerSupplier
			);
		}
		
		public ConsoleRenderer createForGamestate(IGameState gamestate) {
			return new ConsoleRenderer(
					gamestate.getLogging(),
					gamestate.getEngine(),
					gamestate.getGame().getWorld(),
					gamestate.getGame().getContentManager().getCodepointAssetManager(),
					() -> gamestate.getStateRenderer().getTextureCoordinator(),
					GameStateSuppliers.ofControllerFromManager(gamestate, ControllerExtensionsValues.CONTROLLER_CONSOLE, IConsoleController.class)
			);
		}
		
		public ConsoleRenderer createForGamestate(IGameState gamestate, ISupplier<IConsoleController> consoleControllerSupplier) {
			return new ConsoleRenderer(
					gamestate.getLogging(),
					gamestate.getEngine(),
					gamestate.getGame().getWorld(),
					gamestate.getGame().getContentManager().getCodepointAssetManager(),
					() -> gamestate.getStateRenderer().getTextureCoordinator(),
					consoleControllerSupplier
			);
		}
	}
	
}
