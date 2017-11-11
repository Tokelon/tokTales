package com.tokelon.toktales.android.render.opengl;

import org.joml.Matrix4f;

import com.tokelon.toktales.core.game.screen.view.IScreenViewport;

public class AndroidGLSurface implements IAndroidGLSurface {
	
	
	private IScreenViewport mViewport;
	private Matrix4f mMatrix;

	
	public void update(IScreenViewport masterViewport, Matrix4f matrixProjection) {
		this.mViewport = masterViewport;
		this.mMatrix = matrixProjection;	
	}


	@Override
	public IScreenViewport getViewport() {
		return mViewport;
	}

	@Override
	public Matrix4f getProjectionMatrix() {
		return mMatrix;
	}

	
}
