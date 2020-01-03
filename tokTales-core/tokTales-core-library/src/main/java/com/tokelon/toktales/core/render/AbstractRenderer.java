package com.tokelon.toktales.core.render;

import org.joml.Matrix4f;

import com.tokelon.toktales.core.game.screen.view.IViewTransformer;

public abstract class AbstractRenderer implements IRenderer {


	private boolean hasView = false;
	
	private IViewTransformer viewTransformer;

	private Matrix4f matrixProjection = new Matrix4f();
	private Matrix4f matrixView = new Matrix4f();
	private Matrix4f matrixProjectionAndView = new Matrix4f();
	
	
	
	protected abstract void onContextCreated();
	protected abstract void onContextChanged();
	protected abstract void onContextDestroyed();

	
	@Override
	public void contextCreated() {
		onContextCreated();
	}
	
	@Override
	public void contextChanged(IViewTransformer viewTransformer, Matrix4f projectionMatrix) {
		this.viewTransformer = viewTransformer;
		
		matrixProjection.set(projectionMatrix);
		
		matrixView.lookAt(
				0.0f, 0.0f, 0.0f,
				0.0f, 0.0f, -1.0f,
				0.0f, 1.0f, 0.0f);
		
		matrixProjection.mul(matrixView, matrixProjectionAndView);
		
		hasView = true;
		onContextChanged();
	}
	
	@Override
	public void contextDestroyed() {
		onContextDestroyed();
		hasView = false;
	}
	
	
	protected boolean hasView() {
		return hasView;
	}
	
	protected IViewTransformer getViewTransformer() {
		return hasView() ? viewTransformer : null;
	}
	
	protected Matrix4f getMatrixProjection() {
		return hasView() ? matrixProjection : null;
	}
	
	protected Matrix4f getMatrixView() {
		return hasView() ? matrixView : null;
	}
	
	protected Matrix4f getMatrixProjectionAndView() {
		return hasView() ? matrixProjectionAndView : null;
	}
	
}
