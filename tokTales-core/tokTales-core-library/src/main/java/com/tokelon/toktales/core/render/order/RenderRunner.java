package com.tokelon.toktales.core.render.order;

import com.tokelon.toktales.core.render.order.IRenderOrder.IOrderNavigator;

public class RenderRunner {


	private final IRenderOrder renderOrder;
	private final IOrderNavigator navigator;
	
	public RenderRunner(IRenderOrder renderOrder) {
		this.renderOrder = renderOrder;
		this.navigator = renderOrder.navigator();
	}
	
	
	public void run() {
		synchronized (renderOrder) {
			if(navigator.navigateToFirstValidPosition()) {
				do {
					//int currentIndex = navigator.getCurrentIndex();
					//IRenderLayerStack currentStack = navigator.getCurrentStack();
					String currentLayer = navigator.getCurrentLayer();
					
					double currentPosition = navigator.getCurrentPosition();
					IRenderCallback callback = navigator.getCurrentCallback();
					
					//System.out.println(String.format("%d : %s | ", currentIndex, currentPosition));
					callback.renderCall(currentLayer, currentPosition);
				}
				while (navigator.navigateToNextValidPosition());
			}
		}
	}
	
}
