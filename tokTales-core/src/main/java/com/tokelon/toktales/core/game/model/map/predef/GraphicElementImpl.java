package com.tokelon.toktales.core.game.model.map.predef;

import com.tokelon.toktales.core.game.graphic.IBaseGraphic;
import com.tokelon.toktales.core.game.model.map.elements.IGraphicElement;
import com.tokelon.toktales.core.game.model.map.elements.AbstractMapElement.AbstractMutableMapElement;

public class GraphicElementImpl extends AbstractMutableMapElement implements IGraphicElement {

	/* Generic version */
	

	private IBaseGraphic graphic;
	

	public GraphicElementImpl(long id) {
		super(id, null);
	}
	
	public GraphicElementImpl(long id, IElementType elementType) {
		super(id, elementType);
	}
	
	
	
	public void setGraphic(IBaseGraphic graphic) {
		this.graphic = graphic;
	}
	
	@Override
	public IBaseGraphic getGraphic() {
		return graphic;
	}
	
}
