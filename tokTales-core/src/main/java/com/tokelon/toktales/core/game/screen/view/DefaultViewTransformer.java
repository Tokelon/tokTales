package com.tokelon.toktales.core.game.screen.view;

import com.tokelon.toktales.core.game.model.Camera;
import com.tokelon.toktales.core.game.model.ICamera;
import com.tokelon.toktales.core.game.model.IPoint2f;
import com.tokelon.toktales.core.game.model.IPoint2f.IMutablePoint2f;
import com.tokelon.toktales.core.game.model.IRectangle2f;
import com.tokelon.toktales.core.game.model.IRectangle2f.IMutableRectangle2f;

public class DefaultViewTransformer implements IViewTransformer {

	private ICamera camera;
	private final AccurateViewport viewport;
	
	
	public DefaultViewTransformer() {
		this.camera = new Camera();
		this.viewport = new AccurateViewport();
	}
	
	public DefaultViewTransformer(ICamera camera) {
		this.camera = camera;
		this.viewport = new AccurateViewport();
	}
	
	public DefaultViewTransformer(ICamera camera, IScreenViewport viewport) {
		this.camera = camera;
		this.viewport = new AccurateViewport();
		
		updateViewport(viewport);
	}
	
	
	
	@Override
	public IScreenViewport getCurrentViewport() {
		return viewport;
	}

	@Override
	public ICamera getCurrentCamera() {
		return camera;
	}

	
	@Override
	public void updateCamera(ICamera camera) {
		this.camera = camera;
	}
	
	@Override
	public void updateViewport(IScreenViewport viewport) {
		// TODO: assign the viewport to currentExternalViewport and to currentViewport
		// Same as with camera really
		
		applyViewport(viewport);
	}
	
	private void applyViewport(IScreenViewport viewport) {
		// TODO: Make this public?
		// Also make this set the current viewport to the currentInternalViewport
		
		this.viewport.applyMasterViewport(viewport);
	}
	
	
	@Override
	public float getCameraToViewportScale() {
		float scaleVertical = viewport.getHeight() / camera.getHeight();
		float scaleHorizontal = viewport.getWidth() / camera.getWidth();
		
		// Take the smaller value
		float viewScale = scaleHorizontal <= scaleVertical ? scaleHorizontal : scaleVertical;
		
		return viewScale;
	}

	@Override
	public float getViewportToCameraScale() {
		float scaleVertical = camera.getHeight() / viewport.getHeight();
		float scaleHorizontal = camera.getWidth() / viewport.getWidth();
		
		// Take the bigger value
		float cameraScale = scaleHorizontal >= scaleVertical ? scaleHorizontal : scaleVertical;
				
		return cameraScale;
	}

	
	
	@Override
	public float projectX(float wx) {
		float px = viewport.transformX(getCameraToViewportScale() * camera.worldToCameraX(wx));
		return px;
	}

	@Override
	public float projectY(float wy) {
		float py = viewport.transformY(getCameraToViewportScale() * camera.worldToCameraY(wy));
		return py;
	}

	@Override
	public void project(float wx, float wy, IMutablePoint2f sResult) {
		sResult.set(projectX(wx), projectY(wy));
	}

	@Override
	public void project(IPoint2f wPoint, IMutablePoint2f sResult) {
		sResult.set(projectX(wPoint.x()), projectY(wPoint.y()));
	}

	@Override
	public void project(IRectangle2f wRect, IMutableRectangle2f sResult) {
		sResult.set(
				projectX(wRect.left()),
				projectY(wRect.top()),
				projectX(wRect.right()),
				projectY(wRect.bottom())
				);
	}

	
	@Override
	public float unprojectX(float sx) {
		// TODO: Test this
		float ux = camera.cameraToWorldX(viewport.resolveX(sx) * getViewportToCameraScale());
		return ux;
	}

	@Override
	public float unprojectY(float sy) {
		float uy = camera.cameraToWorldY(viewport.resolveY(sy) * getViewportToCameraScale());
		return uy;
	}

	@Override
	public void unproject(float sx, float sy, IMutablePoint2f wResult) {
		wResult.set(unprojectX(sx), unprojectY(sy));
	}

	@Override
	public void unproject(IPoint2f sPoint, IMutablePoint2f wResult) {
		wResult.set(unprojectX(sPoint.x()), unprojectY(sPoint.y()));
	}

	@Override
	public void unproject(IRectangle2f sRect, IMutableRectangle2f wResult) {
		wResult.set(
				unprojectX(sRect.left()),
				unprojectY(sRect.top()),
				unprojectX(sRect.right()),
				unprojectY(sRect.bottom())
				);
	}

	
	
	@Override
	public float cameraToScreenX(float cx) {
		float tsx = viewport.transformX(cx * getCameraToViewportScale());
		return tsx;
	}

	@Override
	public float cameraToScreenY(float cy) {
		float tsy = viewport.transformY(cy * getCameraToViewportScale());
		return tsy;
	}

	@Override
	public void cameraToScreen(float cx, float cy, IMutablePoint2f sResult) {
		sResult.set(cameraToScreenX(cx), cameraToScreenY(cy));
	}

	@Override
	public void cameraToScreen(IPoint2f cPoint, IMutablePoint2f sResult) {
		sResult.set(cameraToScreenX(cPoint.x()), cameraToScreenY(cPoint.y()));
	}

	@Override
	public void cameraToScreen(IRectangle2f cRect, IMutableRectangle2f sResult) {
		sResult.set(
				cameraToScreenX(cRect.left()),
				cameraToScreenY(cRect.top()),
				cameraToScreenX(cRect.right()),
				cameraToScreenY(cRect.bottom())
				);
	}
	

	
	@Override
	public float cameraToViewportX(float cx) {
		float sx = cx * getCameraToViewportScale();
		return sx;
	}

	@Override
	public float cameraToViewportY(float cy) {
		float sy = cy * getCameraToViewportScale();
		return sy;
	}

	@Override
	public void cameraToViewport(float cx, float cy, IMutablePoint2f vResult) {
		vResult.set(cameraToViewportX(cx), cameraToViewportY(cy));
	}

	@Override
	public void cameraToViewport(IPoint2f cPoint, IMutablePoint2f vResult) {
		vResult.set(cameraToViewportX(cPoint.x()), cameraToViewportY(cPoint.y()));
	}

	@Override
	public void cameraToViewport(IRectangle2f cRect, IMutableRectangle2f vResult) {
		vResult.set(
				cameraToViewportX(cRect.left()),
				cameraToViewportY(cRect.top()),
				cameraToViewportX(cRect.right()),
				cameraToViewportY(cRect.bottom())
				);
	}
	
}
