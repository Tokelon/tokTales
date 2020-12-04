package com.tokelon.toktales.core.game.state.render;

import org.joml.Matrix4f;

import com.tokelon.toktales.core.game.model.ICamera;
import com.tokelon.toktales.core.render.IRenderCall;
import com.tokelon.toktales.core.render.IRenderContextManager;
import com.tokelon.toktales.core.render.RenderContextManager;
import com.tokelon.toktales.core.render.order.IRenderOrder;
import com.tokelon.toktales.core.render.order.RenderOrder;
import com.tokelon.toktales.core.render.order.RenderRunner;
import com.tokelon.toktales.core.render.texture.ITextureCoordinator;
import com.tokelon.toktales.core.screen.surface.ISurface;
import com.tokelon.toktales.core.screen.surface.ISurfaceManager;
import com.tokelon.toktales.core.screen.view.AccurateViewport;
import com.tokelon.toktales.core.screen.view.DefaultViewTransformer;
import com.tokelon.toktales.core.screen.view.IScreenViewport;
import com.tokelon.toktales.core.screen.view.IViewTransformer;
import com.tokelon.toktales.tools.core.objects.options.INamedOptions;

public abstract class AbstractGameStateRenderer implements IGameStateRenderer, ISurfaceManager.ISurfaceCallback {


	private final AccurateViewport contextViewport = new AccurateViewport();
	private final Matrix4f projectionMatrix = new Matrix4f();
	private final IViewTransformer viewTransformer;

	private boolean hasSurface = false;
	private boolean surfaceIsValid = false;

	private ISurface currentSurface;

	private final IRenderOrder renderOrder;
	private final RenderRunner renderRunner;

	private final IRenderContextManager renderContextManager;
	private final ITextureCoordinator textureCoordinator;
	
	public AbstractGameStateRenderer(ITextureCoordinator textureCoordinator) {
		this.textureCoordinator = textureCoordinator;

		this.renderContextManager = new RenderContextManager();
		this.viewTransformer = new DefaultViewTransformer();
		
		this.renderOrder = new RenderOrder();
		this.renderRunner = new RenderRunner(renderOrder);
	}

	
	protected IScreenViewport getContextViewport() {
		return surfaceIsValid ? contextViewport : null;
	}
	
	protected Matrix4f getProjectionMatrix() {
		return surfaceIsValid ? projectionMatrix : null;
	}
	
	
	protected abstract void onSurfaceCreated();
	protected abstract void onSurfaceChanged();
	protected abstract void onSurfaceDestroyed();


	@Override
	public void updateCamera(ICamera camera) {
		getViewTransformer().updateCamera(camera);
	}
	
	@Override
	public ICamera getCurrentCamera() {
		return getViewTransformer().getCurrentCamera();
	}
	
	@Override
	public IViewTransformer getViewTransformer() {
		return viewTransformer;
	}
	
	@Override
	public ITextureCoordinator getTextureCoordinator() {
		return textureCoordinator;
	}

	@Override
	public IRenderContextManager getContextManager() {
		return renderContextManager;
	}

	@Override
	public ISurfaceManager.ISurfaceCallback getSurfaceCallback() {
		return this;
	}
	
	@Override
	public IRenderOrder getRenderOrder() {
		return renderOrder;
	}


	@Override
	public boolean hasSurface() {
		return hasSurface;
	}

	@Override
	public ISurface getSurface() {
		return currentSurface;
	}
	
	
	@Override
	public IRenderCall getRenderCall(String contentName, INamedOptions renderOptions) {
		return () -> {};
	}
	
	@Override
	public void renderState() {
		renderRunner.run();
	}
	

	@Override
	public void surfaceCreated(ISurface surface) {
		this.surfaceIsValid = false;
		this.currentSurface = surface;
		this.hasSurface = true;

		onSurfaceCreated();

		getContextManager().contextCreated();
	}

	@Override
	public void surfaceChanged(ISurface surface) {
		IScreenViewport masterViewport = surface.getViewport();
		
		contextViewport.setSize(masterViewport.getWidth(), masterViewport.getHeight());
		contextViewport.setOffset(masterViewport.getHorizontalOffset(), masterViewport.getVerticalOffset());
		
		projectionMatrix.set(surface.getProjectionMatrix());
		
		viewTransformer.updateViewport(contextViewport);

		this.surfaceIsValid = true;
		onSurfaceChanged();	// Call the state renderer first

		getContextManager().contextChanged(getViewTransformer(), getProjectionMatrix());

	}

	@Override
	public void surfaceDestroyed(ISurface surface) {
		this.hasSurface = false;
		this.currentSurface = null;
		this.surfaceIsValid = false;

		onSurfaceDestroyed();

		getContextManager().contextDestroyed();
	}

}
