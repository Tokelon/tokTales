package com.tokelon.toktales.core.render.renderer;

import javax.inject.Inject;

import com.tokelon.toktales.core.engine.render.IRenderAccess;
import com.tokelon.toktales.core.render.IRenderDriver;
import com.tokelon.toktales.core.render.RenderException;
import com.tokelon.toktales.core.render.model.IManagedTextureModel;
import com.tokelon.toktales.core.render.model.ManagedTextureModel;
import com.tokelon.toktales.core.render.texture.ITexture;
import com.tokelon.toktales.core.render.texture.ITextureCoordinator;
import com.tokelon.toktales.tools.core.objects.options.NamedOptionsImpl;

public class ImageRenderer extends AbstractRenderer implements IImageRenderer {


	private final ManagedTextureModel model = new ManagedTextureModel();
	
	private final NamedOptionsImpl drawingOptions = new NamedOptionsImpl();


	private boolean isInBatchDraw = false;

	private IRenderDriver imageDriver;
	
	private final IRenderAccess renderAccess;
	
	@Inject
	public ImageRenderer(IRenderAccess renderAccess, ITextureCoordinator textureCoordinator) {
		this.renderAccess = renderAccess;
		
		model.setTextureCoordinator(textureCoordinator);

		model.setInvertYAxis(true);
	}
	

	@Override
	protected void onContextCreated() {
		imageDriver = renderAccess.requestDriver(IManagedTextureModel.class.getName());
		if(imageDriver == null) {
			throw new RenderException("No render driver found for: " + IManagedTextureModel.class.getName());
		}
		
		imageDriver.create();
	}

	@Override
	protected void onContextChanged() {
		// Nothing
	}

	@Override
	protected void onContextDestroyed() {
		if(imageDriver != null) {
			imageDriver.destroy();
			imageDriver = null;
		}
	}
	
	@Override
	public void startBatch() {
		imageDriver.use(getMatrixProjectionAndView());
		
		isInBatchDraw = true;
	}
	
	@Override
	public void finishBatch() {
		isInBatchDraw = false;
		
		imageDriver.release();
	}
	
	
	@Override
	public void drawImage(ITexture renderTexture, float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4) {
		if(!hasView()) {
			assert false : "Cannot draw without view";
			return;
		}
		
		float minX = Math.min(x1, Math.min(x2, Math.min(x3, x4)));
		float maxX = Math.max(x1, Math.max(x2, Math.max(x3, x4)));
		float minY = Math.min(y1, Math.min(y2, Math.min(y3, y4)));
		float maxY = Math.max(y1, Math.max(y2, Math.max(y3, y4)));
		

		model.setScaling2D(
				getViewTransformer().cameraToScreenX(maxX - minX),
				getViewTransformer().cameraToScreenY(maxY - minY)
		);

		model.setTranslation2D(minX, minY);
		
		model.setTargetTexture(renderTexture);
		
		if(isInBatchDraw) {
			imageDriver.draw(model, drawingOptions);
		}
		else {
			imageDriver.drawQuick(getMatrixProjectionAndView(), model, drawingOptions);	
		}
	}
	
}
