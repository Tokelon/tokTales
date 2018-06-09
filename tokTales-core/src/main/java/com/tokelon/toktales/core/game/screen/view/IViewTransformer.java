package com.tokelon.toktales.core.game.screen.view;

import com.tokelon.toktales.core.game.model.ICamera;
import com.tokelon.toktales.core.game.model.IPoint2f;
import com.tokelon.toktales.core.game.model.IPoint2f.IMutablePoint2f;
import com.tokelon.toktales.core.game.model.IRectangle2f;
import com.tokelon.toktales.core.game.model.IRectangle2f.IMutableRectangle2f;

/** Transforms coordinates through different layers.
 * All transformations are done using a viewport and a camera,
 * and include conversion from world units to screen pixels.
 * <br>
 * <br>
 * The layers are as follows (from top to bottom):<br><br>
 * <b>Screen</b> v [independent of all the below]
 * <br><br>
 * ^ [area on the screen]<br>
 * <b>Viewport</b> <br>
 * v [scaling between screen (pixels) and world (units) coordinates]
 * <br><br>
 * ^ [positioned on the viewport]<br>
 * <b>Camera</b> <br>
 * v [area in the world]
 * <br><br>
 * <b>World</b> ^ [independent of all the above]
 * <br>
 * 
 */
public interface IViewTransformer {

	
	public IScreenViewport getCurrentViewport();
	public ICamera getCurrentCamera();

	public void updateCamera(ICamera camera);
	public void updateViewport(IScreenViewport viewport);
	

	/** Use this to convert from world units to screen pixels.
	 * 
	 * @return The scale value from camera to viewport.
	 */
	public float getCameraToViewportScale();
	
	/** Use this to convert from screen pixels to world units.
	 * 
	 * @return The scale value from viewport to camera.
	 */
	public float getViewportToCameraScale();

	
	// Add transformation matrices? Matrix4f or Matrix3f ?
	/*
	public Matrix4f getProjectMatrix();
	public Matrix4f getUnprojectMatrix();
	
	public Matrix4f getCameraToScreenMatrix();
	public Matrix4f getScreenToCameraMatrix();
	
	public Matrix4f getCameraToViewportMatrix();
	public Matrix4f getViewportToCameraMatrix();
	*/
	
	
	
	/** Transforms the horizontal value of a world coordinate to it's value on the screen.
	 * 
	 * @param wx
	 * @return X screen value (pixels).
	 */
	public float projectX(float wx);
	
	/** Transforms the vertical value of a world coordinate to it's value on the screen.
	 * 
	 * @param wy
	 * @return Y screen value (pixels).
	 */
	public float projectY(float wy);
	
	/** Transforms a world coordinate to a screen coordinate.
	 * 
	 * @param wx
	 * @param wy
	 * @param sResult Returns a screen coordinate (pixels).
	 */
	public void project(float wx, float wy, IMutablePoint2f sResult);
	
	/** Same as {@link #project(float, float, IMutablePoint2f)} for a point.
	 * 
	 * @param wPoint
	 * @param sResult Returns a screen coordinate (pixels).
	 */
	public void project(IPoint2f wPoint, IMutablePoint2f sResult);
	
	/** Same as {@link #project(float, float, IMutablePoint2f)} for a rectangle.
	 * 
	 * @param wRect
	 * @param sResult Returns a screen area (pixels).
	 */
	public void project(IRectangle2f wRect, IMutableRectangle2f sResult);
	
	
	/** Transforms the horizontal value of a screen coordinate to it's value in the world.
	 * 
	 * @param sx
	 * @return X world value (units).
	 */
	public float unprojectX(float sx);
	
	/** Transforms the vertical value of a screen coordinate to it's value in the world.
	 * 
	 * @param sy
	 * @return Y world value (units).
	 */
	public float unprojectY(float sy);
	
	/** Transforms a screen coordinate to a world coordinate.
	 * 
	 * @param sx
	 * @param sy
	 * @param wResult Returns a world coordinate (units).
	 */
	public void unproject(float sx, float sy, IMutablePoint2f wResult);
	
	/** Same as {@link #unproject(float, float, IMutablePoint2f)} for a point.
	 * 
	 * @param sPoint
	 * @param wResult Returns a world coordinate (units).
	 */
	public void unproject(IPoint2f sPoint, IMutablePoint2f wResult);
	
	/** Same as {@link #unproject(float, float, IMutablePoint2f)} for a rectangle.
	 * 
	 * @param sRect
	 * @param wResult Returns a world area (units).
	 */
	public void unproject(IRectangle2f sRect, IMutableRectangle2f wResult);
	
	
	
	// TODO: cameraToScreen and cameraToViewport should return int values?
	
	/** Transforms the horizontal value of a camera coordinate to it's value on the screen.
	 * 
	 * @param wx
	 * @return X screen value (pixels).
	 */
	public float cameraToScreenX(float cx);
	
	/** Transforms the vertical value of a camera coordinate to it's value on the screen.
	 * 
	 * @param cy
	 * @return Y screen value (pixels).
	 */
	public float cameraToScreenY(float cy);
	
	/** Transforms a camera coordinate to a screen coordinate.
	 * 
	 * @param cx
	 * @param cy
	 * @param sResult Returns a screen coordinate (pixels).
	 */
	public void cameraToScreen(float cx, float cy, IMutablePoint2f sResult);
	
	/** Same as {@link #cameraToScreen(float, float, IMutablePoint2f)} for a point.
	 * 
	 * @param cPoint
	 * @param sResult Returns a screen coordinate (pixels).
	 */
	public void cameraToScreen(IPoint2f cPoint, IMutablePoint2f sResult);
	
	/** Same as {@link #cameraToScreen(float, float, IMutablePoint2f)} for a rectangle.
	 * 
	 * @param cRect
	 * @param sResult Returns a screen area (pixels).
	 */
	public void cameraToScreen(IRectangle2f cRect, IMutableRectangle2f sResult);
	
	// From screen to camera, omitted for now
	//public void screenToCamera(float sx, float sy, IMutablePoint2f cResult);
	
	
	
	/** Transforms the horizontal value of a camera coordinate to it's value on the viewport.
	 * 
	 * @param cx
	 * @return X viewport value (pixels).
	 */
	public float cameraToViewportX(float cx);
	
	/** Transforms the vertical value of a camera coordinate to it's value on the viewport.
	 * 
	 * @param cy
	 * @return Y viewport value (pixels).
	 */
	public float cameraToViewportY(float cy);
	
	/** Transforms a camera coordinate to a viewport coordinate.
	 * 
	 * @param cx
	 * @param cy
	 * @param vResult Returns a viewport coordinate (pixels).
	 */
	public void cameraToViewport(float cx, float cy, IMutablePoint2f vResult);
	
	/** Same as {@link #cameraToViewport(float, float, IMutablePoint2f)} for a point.
	 * 
	 * @param cPoint
	 * @param vResult Returns a viewport coordinate (pixels).
	 */
	public void cameraToViewport(IPoint2f cPoint, IMutablePoint2f vResult);
	
	/** Same as {@link #cameraToViewport(float, float, IMutablePoint2f)} for a rectangle.
	 * 
	 * @param cRect
	 * @param vResult Returns a viewport area (pixels).
	 */
	public void cameraToViewport(IRectangle2f cRect, IMutableRectangle2f vResult);
	
	
	// From viewport to camera, omitted for now
	//public void viewportToCamera(float vx, float vy, IMutablePoint2f cResult);
	
	
	
	/* Could add for convenience */
	
	// From world to camera, use camera methods
	//public void worldToCamera(float wx, float wy, IMutablePoint2f cResult);
	
	// From camera to world, use camera methods
	//public void cameraToWorld(float cx, float cy, IMutablePoint2f wResult);
	
	
	
	/* Could add but are they useful? */
	
	// From world to viewport, omitted
	//public void worldToViewport(float wx, float wy, IMutablePoint2f vResult);
	
	// From viewport to world, omitted
	//public void viewportToWorld(float vx, float vy, IMutablePoint2f wResult);
	
}
