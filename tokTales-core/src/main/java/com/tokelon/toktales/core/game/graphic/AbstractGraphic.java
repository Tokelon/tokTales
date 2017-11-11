package com.tokelon.toktales.core.game.graphic;

public abstract class AbstractGraphic implements IBaseGraphic {

	protected IGraphicType mType;
	
	public AbstractGraphic(IGraphicType type) {
		this.mType = type;
	}
	
	
	@Override
	public IGraphicType getGraphicType() {
		return mType;
	}

}
