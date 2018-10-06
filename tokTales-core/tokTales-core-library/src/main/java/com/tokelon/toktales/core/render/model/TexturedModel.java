package com.tokelon.toktales.core.render.model;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class TexturedModel extends RenderModel implements ITexturedModel {

	
	private final Matrix4f textureMatrix;
	
	private final Vector2f textureTranslation;
	private final Vector2f textureRotation;
	private final Vector2f textureScaling;
	
	
	private final Vector3f textureTopLeft;
	private final Vector3f textureTopRight;
	private final Vector3f textureBottomRight;
	private final Vector3f textureBottomLeft;
	
	private final Vector3f textureTopLeftTransformed;
	private final Vector3f textureTopRightTransformed;
	private final Vector3f textureBottomRightTransformed;
	private final Vector3f textureBottomLeftTransformed;
	
	private final float[] textureCoordinates; 
	
	
	public TexturedModel() {
		
		textureMatrix = new Matrix4f();
		
		textureTranslation = new Vector2f(0.0f, 0.0f);
		textureRotation = new Vector2f(0.0f, 0.0f);
		textureScaling = new Vector2f(1.0f, 1.0f);
		
		
		textureTopLeft = new Vector3f(0.0f, 1.0f, 0.0f);
		textureTopRight = new Vector3f(1.0f, 1.0f, 0.0f);
		textureBottomRight = new Vector3f(1.0f, 0.0f, 0.0f);
		textureBottomLeft = new Vector3f(0.0f, 0.0f, 0.0f);
		
		textureTopLeftTransformed = new Vector3f();
		textureTopRightTransformed = new Vector3f();
		textureBottomRightTransformed = new Vector3f();
		textureBottomLeftTransformed = new Vector3f();
		
		textureCoordinates = new float[8];
		
	}
	
	public TexturedModel(boolean invertYAxis) {
		this();
		
		setInvertYAxis(invertYAxis);
	}

	
	public Vector2f getTextureTranslation() {
		return textureTranslation;
	}
	
	public Vector2f getTextureRotation() {
		return textureRotation;
	}
	
	public Vector2f getTextureScaling() {
		return textureScaling;
	}
	

	
	public void setInvertYAxis(boolean enabled) {
		if(enabled) {
			textureTopLeft.set(0.0f, 0.0f, 0.0f);
			textureTopRight.set(1.0f, 0.0f, 0.0f);
			textureBottomRight.set(1.0f, 1.0f, 0.0f);
			textureBottomLeft.set(0.0f, 1.0f, 0.0f);
		}
		else {
			textureTopLeft.set(0.0f, 1.0f, 0.0f);
			textureTopRight.set(1.0f, 1.0f, 0.0f);
			textureBottomRight.set(1.0f, 0.0f, 0.0f);
			textureBottomLeft.set(0.0f, 0.0f, 0.0f);
		}
	}
	
	/* Maybe make getTextureMatrix() and applyTextureMatrix() public
	 * so that you can apply and get the matrix from outside
	 * 
	 */
	
	private Matrix4f getTextureMatrix() {
		return textureMatrix;
	}
	
	private Matrix4f applyTextureMatrix() {
		textureMatrix.identity()
		.translate(textureTranslation.x, textureTranslation.y, 0.0f)
		.rotateXYZ((float) Math.toRadians(textureRotation.x), (float) Math.toRadians(textureRotation.y), (float) Math.toRadians(0.0f))
		.scale(textureScaling.x, textureScaling.y, 1.0f);

		return textureMatrix;
	}
	
	
	public float[] applyTextureCoordinates() {
		
		Matrix4f textureMat = applyTextureMatrix();
		
		textureMat.transformPosition(textureTopLeft, textureTopLeftTransformed);
		textureMat.transformPosition(textureTopRight, textureTopRightTransformed);
		textureMat.transformPosition(textureBottomRight, textureBottomRightTransformed);
		textureMat.transformPosition(textureBottomLeft, textureBottomLeftTransformed);
		
		
		textureCoordinates[0] = textureBottomLeftTransformed.x;
		textureCoordinates[1] = textureBottomLeftTransformed.y;
		textureCoordinates[2] = textureTopLeftTransformed.x;
		textureCoordinates[3] = textureTopLeftTransformed.y;
		textureCoordinates[4] = textureTopRightTransformed.x;
		textureCoordinates[5] = textureTopRightTransformed.y;
		textureCoordinates[6] = textureBottomRightTransformed.x;
		textureCoordinates[7] = textureBottomRightTransformed.y;

		
		return textureCoordinates;
	}

	
	@Override
	public void setTextureTranslation(float translationX, float translationY) {
		textureTranslation.set(translationX, translationY);
	}

	@Override
	public void translateTexture(float translateX, float translateY) {
		textureTranslation.add(translateX, translateY);
	}

	
	@Override
	public void setTextureRotation(float rotationX, float rotationY) {
		textureRotation.set(rotationX, rotationY);
	}

	@Override
	public void rotateTexture(float rotateX, float rotateY) {
		textureRotation.add(rotateX, rotateY);
	}

	
	@Override
	public void setTextureScaling(float scalingX, float scalingY) {
		textureScaling.set(scalingX, scalingY);
	}

	@Override
	public void scaleTexture(float scaleX, float scaleY) {
		textureScaling.mul(scaleX, scaleY);		// If the current scale is 0, it will stay 0
	}
	
	
}
