package com.tokelon.toktales.android.render.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.tokelon.toktales.core.screen.view.IScreenViewport;

import android.graphics.Rect;

public class UIOverlay implements IUIOverlay {

	
	private final CrossControlOverlayView mCrossControlView;
	
	private final List<IButtonOverlayView> mButtonViewsList;
	
	
	//private final OvalShape mButtonShapeA = new OvalShape();
	//private final ShapeDrawable mButtonShapeADrawable = new ShapeDrawable();
	
	//Picture picture;	// Maybe use Picture class to cache overlay for optimization reasons

	
	public UIOverlay(CrossControlOverlayView crossControlView, IButtonOverlayView[] buttonViews) {
		this.mCrossControlView = crossControlView;
		
		mButtonViewsList = new ArrayList<IButtonOverlayView>(buttonViews.length);
		mButtonViewsList.addAll(Arrays.asList(buttonViews));
	}
	

	
	@Override
	public Rect getButtonBounds(int button) {
		Rect res = new Rect();
		getButtonBounds(button, res);
		return res;
	}

	@Override
	public void getButtonBounds(int button, Rect result) {
		if(result == null) {
			throw new NullPointerException();
		}
		
		
		boolean buttonFound = false;
		for(IButtonOverlayView bv: mButtonViewsList) {
			if(bv.getButtonID() == button) {
				bv.getButtonBounds(result);
				buttonFound = true;
				break;
			}
		}
		
		if(!buttonFound) {
			throw new IllegalArgumentException("No button for value: " +button);
		}
	}


	@Override
	public boolean isInButtonBounds(int button, int x, int y) {
		return getButtonView(button).containsPoint(x, y);
	}


	@Override
	public int getButton(int x, int y) {
		int res = BUTTON_NONE;

		for(IButtonOverlayView bv: mButtonViewsList) {
			if(bv.containsPoint(x, y)) {
				res = bv.getButtonID();
				break;
			}
		}
		
		return res;
	}

	
	
	@Override
	public ICrossControlOverlayView getCrossControlView() {
		return mCrossControlView;
	}


	@Override
	public IButtonOverlayView getButtonView(int button) {
		IButtonOverlayView res = null;
		
		for(IButtonOverlayView bv: mButtonViewsList) {
			if(bv.getButtonID() == button) {
				res = bv;
				break;
			}
		}
		
		if(res == null) {
			throw new IllegalArgumentException("No button for value: " +button);
		}
		
		return res;
	}
	
	
	
	public static UIOverlay createFromViewportAndConfiguration(IScreenViewport viewport, UIConfiguration config) {
		
		int horSize = (int) (viewport.getWidth() * config.configCrossSizeHorizontalViewportMult);
		int verSize = (int) (viewport.getHeight() * config.configCrossSizeVerticalViewportMult);
		
		// Adjust to rectangle
		if(viewport.getOrientation() == IScreenViewport.ORIENTATION_LANDSCAPE) {
			horSize = verSize;
		}
		else {
			verSize = horSize;
		}
		
		
		int viewportSpaceHor = (int) viewport.getWidth() - horSize;
		int viewportSpaceVer = (int) viewport.getHeight() - verSize;
		
		// Subtract -1 from the viewport sizes because our indexing starts at 0,0
		int horPos = config.configCrossPixelPositionHorizontal > viewportSpaceHor ? viewportSpaceHor - 1 : config.configCrossPixelPositionHorizontal;
		int verPos = config.configCrossPixelPositionVertical > viewportSpaceVer ? viewportSpaceVer - 1 : config.configCrossPixelPositionVertical;
		
		
		// TODO: Check the transform and cast !
		// Probably use Math.round() here
		Rect rectCross = new Rect();
		rectCross.left = (int) viewport.transformX(horPos);
		rectCross.right = rectCross.left + horSize;
		rectCross.top = (int) viewport.transformY(verPos);
		rectCross.bottom = rectCross.top + verSize;
		
		CrossControlOverlayView crossControl = new CrossControlOverlayView(rectCross);
		

		
		// Buttons
		
		int bAPosHor, bAPosVer;
		int bBPosHor, bBPosVer;
		int bSP1PosHor, bSP1PosVer;
		int bSETPosHor, bSETPosVer;
		
		int viewportSmallSize;
		if(viewport.getOrientation() == IScreenViewport.ORIENTATION_LANDSCAPE) {
			viewportSmallSize = (int) viewport.getHeight();
			
			bAPosHor = (int) (viewport.getWidth() * config.configButtonALandscapePositionViewportMultHorizontal);
			bAPosVer = (int) (viewport.getHeight() * config.configButtonALandscapePositionViewportMultVertical);
			
			bBPosHor = (int) (viewport.getWidth() * config.configButtonBLandscapePositionViewportMultHorizontal);
			bBPosVer = (int) (viewport.getHeight() * config.configButtonBLandscapePositionViewportMultVertical);
			
			bSP1PosHor = (int) (viewport.getWidth() * config.configButtonSP1LandscapePositionViewportMultHorizontal);
			bSP1PosVer = (int) (viewport.getHeight() * config.configButtonSP1LandscapePositionViewportMultVertical);
			
			bSETPosHor = (int) (viewport.getWidth() * config.configButtonSETLandscapePositionViewportMultHorizontal);
			bSETPosVer = (int) (viewport.getHeight() * config.configButtonSETLandscapePositionViewportMultVertical);
		}
		else {
			viewportSmallSize = (int) viewport.getWidth();
			
			bAPosHor = (int) (viewport.getWidth() * config.configButtonAPortraitPositionViewportMultHorizontal);
			bAPosVer = (int) (viewport.getHeight() * config.configButtonAPortraitPositionViewportMultVertical);
			
			bBPosHor = (int) (viewport.getWidth() * config.configButtonBPortraitPositionViewportMultHorizontal);
			bBPosVer = (int) (viewport.getHeight() * config.configButtonBPortraitPositionViewportMultVertical);
			
			bSP1PosHor = (int) (viewport.getWidth() * config.configButtonSP1PortraitPositionViewportMultHorizontal);
			bSP1PosVer = (int) (viewport.getHeight() * config.configButtonSP1PortraitPositionViewportMultVertical);
			
			bSETPosHor = (int) (viewport.getWidth() * config.configButtonSETPortraitPositionViewportMultHorizontal);
			bSETPosVer = (int) (viewport.getHeight() * config.configButtonSETPortraitPositionViewportMultVertical);
		}
		
		
		// TODO: Check tranform and cast !
		// Translate with viewport
		bAPosHor = (int) viewport.transformX(bAPosHor);
		bAPosVer = (int) viewport.transformY(bAPosVer);
		
		bBPosHor = (int) viewport.transformX(bBPosHor);
		bBPosVer = (int) viewport.transformY(bBPosVer);
		
		bSP1PosHor = (int) viewport.transformX(bSP1PosHor);
		bSP1PosVer = (int) viewport.transformY(bSP1PosVer);
		
		bSETPosHor = (int) viewport.transformX(bSETPosHor);
		bSETPosVer = (int) viewport.transformY(bSETPosVer);
		
		
		
		int bASize = (int) (viewportSmallSize * config.configButtonASizeViewportMult);
		int bARadius = bASize / 2;
		
		RoundButtonOverlayView buttonA = new RoundButtonOverlayView(BUTTON_A, bAPosHor + bARadius, bAPosVer + bARadius, bARadius);
		
				
		int bBSize = (int) (viewportSmallSize * config.configButtonBSizeViewportMult);
		int bBRadius = bBSize / 2;
		
		RoundButtonOverlayView buttonB = new RoundButtonOverlayView(BUTTON_B, bBPosHor + bBRadius, bBPosVer + bBRadius, bBRadius);
		
		
		int bSP1Size = (int) (viewportSmallSize * config.configButtonSP1SizeViewportMult);
		int bSP1Radius = bSP1Size / 2;
		
		RoundButtonOverlayView buttonSP1 = new RoundButtonOverlayView(BUTTON_SP1, bSP1PosHor + bSP1Radius, bSP1PosVer + bSP1Radius, bSP1Radius);
		
		int bSETSize = (int) (viewportSmallSize * config.configButtonSETSizeViewportMult);
		int bSETRadius = bSETSize / 2;
		
		RoundButtonOverlayView buttonSET = new RoundButtonOverlayView(BUTTON_SET, bSETPosHor + bSETRadius, bSETPosVer + bSETRadius, bSETRadius);
		
		
		return new UIOverlay(crossControl, new IButtonOverlayView[] { buttonA, buttonB, buttonSP1, buttonSET });
	}

	
}
