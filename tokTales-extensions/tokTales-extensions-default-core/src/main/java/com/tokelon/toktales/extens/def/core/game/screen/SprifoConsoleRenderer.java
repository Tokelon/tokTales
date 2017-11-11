package com.tokelon.toktales.extens.def.core.game.screen;

import org.joml.Matrix4f;
import org.joml.Vector4f;

import com.tokelon.toktales.core.content.sprite.ISprite;
import com.tokelon.toktales.core.content.sprite.ISpriteAsset;
import com.tokelon.toktales.core.content.sprite.ISpriteManager;
import com.tokelon.toktales.core.content.text.ISpriteFont;
import com.tokelon.toktales.core.game.model.IConsole;
import com.tokelon.toktales.core.game.model.Point2fImpl;
import com.tokelon.toktales.core.game.model.Point2iImpl;
import com.tokelon.toktales.core.game.model.Rectangle2fImpl;
import com.tokelon.toktales.core.game.model.Rectangle2iImpl;
import com.tokelon.toktales.core.game.screen.ISegmentRenderer;
import com.tokelon.toktales.core.game.screen.view.DefaultViewGridTransformer;
import com.tokelon.toktales.core.game.screen.view.IViewGridTransformer;
import com.tokelon.toktales.core.game.screen.view.IViewTransformer;
import com.tokelon.toktales.core.game.states.IGameState;
import com.tokelon.toktales.core.render.IKeyedTextureManager;
import com.tokelon.toktales.core.render.IRenderDriver;
import com.tokelon.toktales.core.render.IRenderTexture;
import com.tokelon.toktales.core.render.RenderException;
import com.tokelon.toktales.core.render.model.ISpriteFontModel;
import com.tokelon.toktales.core.render.model.SpriteFontModel;
import com.tokelon.toktales.core.util.INamedOptions;
import com.tokelon.toktales.core.util.NamedOptionsImpl;
import com.tokelon.toktales.extens.def.core.game.controller.ISprifoConsoleController;

public class SprifoConsoleRenderer implements ISegmentRenderer {

	
	public static final float CHAR_HOR_DISTANCE_MODIFIER_BASED_ON_TILESIZE = 0;//- 0.25f; 
	
	private final Matrix4f matrixProjection = new Matrix4f();
	private final Matrix4f matrixView = new Matrix4f();
	private final Matrix4f matrixProjectionAndView = new Matrix4f();

	private final Rectangle2fImpl cameraBounds = new Rectangle2fImpl();
	private final Rectangle2iImpl cameraGridBounds = new Rectangle2iImpl();
	
	private final DrawingMeta dmeta = new DrawingMeta();


	private IKeyedTextureManager<ISprite> mTextureManager;
	
	private IRenderDriver fontDriver;
	private final SpriteFontModel fontModel = new SpriteFontModel();
	
	private final NamedOptionsImpl drawingOptions = new NamedOptionsImpl();
	
	
	private IViewTransformer mViewTransformer;
	private IViewGridTransformer mGridTransformer;
	
	
	private final IGameState mGamestate;
	private final ISprifoConsoleController mConsoleController;
	private final ISpriteManager mSpriteManager;
	
	public SprifoConsoleRenderer(IGameState gamestate, ISprifoConsoleController consoleController) {
		this.mGamestate = gamestate;
		this.mConsoleController = consoleController;
		this.mSpriteManager = gamestate.getGame().getContentManager().getSpriteManager();
		
		fontModel.setInvertYAxis(true);
		fontModel.setTargetColor(new Vector4f(1.0f, 0.5f, 0.2f, 0.0f));
	}
	
	
	@Override
	public void contextCreated() {
		
		fontDriver = mGamestate.getEngine().getRenderService().getRenderAccess().requestDriver(ISpriteFontModel.class.getName());
		if(fontDriver == null) {
			throw new RenderException("No render driver found for: " +ISpriteFontModel.class.getName());
		}
		
		fontDriver.create();
		
		
		mTextureManager = mGamestate.getEngine().getRenderService().getRenderAccess().requestKeyedTextureManager(ISprite.class);
		if(mTextureManager == null) {
			throw new RenderException("No texture manager found");
		}

		
		fontModel.setTextureManager(mTextureManager);
		
	}

	
	@Override
	public void contextChanged(IViewTransformer viewTransformer, Matrix4f projectionMatrix) {
		this.mViewTransformer = viewTransformer;
		this.mGridTransformer = new DefaultViewGridTransformer(mGamestate.getGame().getWorld().getGrid(), viewTransformer);
		
		
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
		
		if(mTextureManager != null) {
			mTextureManager.clear();
			mTextureManager = null;
		}
		
		fontModel.setTextureManager(null);
		
		mGridTransformer = null;
		mViewTransformer = null;
	}

	
	@Override
	public void prepare(long currentTimeMillis) {
		if(mGridTransformer == null) {
			assert false : "Cannot prepare without view";
		}
		
		dmeta.tilePixeSize = mGridTransformer.getScreenTileSize();
	}

	
	@Override
	public void drawLayer(INamedOptions options, String layerName) {
		assert false : "Not supported";
	}

	
	@Override
	public void drawFull(INamedOptions options) {
		if(mGridTransformer == null) {
			assert false : "Cannot draw without view";
			return;
		}
		
		IConsole console = mConsoleController.getConsole();
		
		ISpriteFont font = mConsoleController.getFont();
		if(font == null) {
			return;		// Can't render without a font
		}
		
		
		fontDriver.use(matrixProjectionAndView);

		
		mViewTransformer.getCurrentCamera().getWorldBounds(cameraBounds);
		mGridTransformer.getGrid().worldToGrid(cameraBounds, cameraGridBounds);
		
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

				ISprite codepointSprite = font.getSpriteForCodepoint(targetCodepoint);
				if(codepointSprite == null) {
					continue;				// unknown codepoint
				}
				
				ISpriteAsset spriteAsset = mSpriteManager.getSpriteAsset(codepointSprite);
				if(spriteAsset == null) {
					// Asset has not been loaded yet
					continue;
				}
				
				boolean assetIsSpecial = mSpriteManager.assetIsSpecial(spriteAsset);
				if(assetIsSpecial) {
					/* TODO: 
					 * FontDriver does not support sprites that are not enclosed
					 * therefor it cannot render the special asset
					 * Fix ?
					 */
					continue;
				}
				
				IRenderTexture spriteTexture = mGamestate.getEngine().getContentService().extractAssetTexture(spriteAsset.getContent());
				if(spriteTexture == null) {
					// TODO: Implement special asset
					continue;
				}

				
				mGridTransformer.visibleScreenBoundsForTile(dmeta.gridPosition, dmeta.tileSourceBounds);

				dmeta.tileTranslation.set(mViewTransformer.getCurrentViewport().transformX(x * dmeta.tilePixeSize + x * dmeta.tilePixeSize * CHAR_HOR_DISTANCE_MODIFIER_BASED_ON_TILESIZE), mViewTransformer.getCurrentViewport().transformY(y * dmeta.tilePixeSize));

				configModel();
				
				
				fontModel.setTarget(codepointSprite, spriteTexture);

				
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

	
}
