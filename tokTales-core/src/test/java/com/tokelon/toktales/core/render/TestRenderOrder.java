package com.tokelon.toktales.core.render;

import java.util.Collection;
import java.util.Iterator;

import com.tokelon.toktales.core.game.screen.order.IRenderCallback;
import com.tokelon.toktales.core.game.screen.order.IRenderLayerStack;
import com.tokelon.toktales.core.game.screen.order.IRenderOrder;
import com.tokelon.toktales.core.game.screen.order.RenderOrder;
import com.tokelon.toktales.core.game.screen.order.RenderRunner;

public class TestRenderOrder {

	public static void main(String[] args) {
		
		RenderOrder renderOrder = new RenderOrder();
		

		renderOrder.insertLayerAt(3, "layer3");
		renderOrder.insertLayerAt(1, "layer1");
		renderOrder.insertLayerAt(4, "layer4");
		renderOrder.insertLayerAt(2, "layer2");
		
		renderOrder.insertLayerAt(renderOrder.getTopIndex(), "layer5");

		
		System.out.println("*** Layer list ***");
		for(String layer: renderOrder.getLayers()) {
			System.out.println(layer);
		}
		
		
		System.out.println("\n*** Callbacks ***");
		
		IRenderLayerStack stack1 = renderOrder.getStackForLayer("layer1");
		IRenderLayerStack stack2 = renderOrder.getStackForLayer("layer2");
		IRenderLayerStack stack3 = renderOrder.getStackForLayer("layer3");
		IRenderLayerStack stack4 = renderOrder.getStackForLayer("layer4");
		IRenderLayerStack stack5 = renderOrder.getStackForLayer("layer5");
		
		stack1.addCallbackAt(0d, new TestRenderCallback("base render layer1"));
		stack2.addCallbackAt(0d, new TestRenderCallback("base render layer2"));
		stack3.addCallbackAt(0d, new TestRenderCallback("base render layer3"));
		stack4.addCallbackAt(0d, new TestRenderCallback("base render layer4"));
		stack5.addCallbackAt(0d, new TestRenderCallback("base render layer5"));
		
		
		stack1.addCallbackAt(1d, new TestRenderCallback("callback 1.0 layer1"));
		stack1.addCallbackAt(0.5d, new TestRenderCallback("callback 0.5 layer1"));
		stack1.addCallbackAt(-2d, new TestRenderCallback("callback -2.0 layer1"));
		stack1.addCallbackAt(-1, new TestRenderCallback("callback -1.0 layer1"));
		stack1.addCallbackAt(-0.25, new TestRenderCallback("callback -0.25 layer1"));
		
		stack1.removeCallbackAt(0.5d);
		
		callRenderOrder(renderOrder);
		System.out.println();
		runRenderOrder(renderOrder);
		
		
		
		System.out.println("\n*** Copy Render order ***");
		
		RenderOrder renderOrderCopy = RenderOrder.CreateFromBase(renderOrder);
		callRenderOrder(renderOrderCopy);
		System.out.println();
		runRenderOrder(renderOrderCopy);
		
	}
	
	private static void runRenderOrder(IRenderOrder renderOrder) {
		
		RenderRunner runner = new RenderRunner(renderOrder);
		
		runner.run();
		
	}
	
	private static void callRenderOrder(RenderOrder renderOrder) {
		synchronized(renderOrder) {
			Collection<IRenderLayerStack> layerStacks = renderOrder.getLayerStacks();
			Iterator<IRenderLayerStack> layerStacksIterator = layerStacks.iterator();

			Collection<String> layerNames = renderOrder.getLayers();
			Iterator<String> layerNamesIterator = layerNames.iterator();

			for(int i=0; i < renderOrder.getFullLayerCount(); i++) {
				//ILayerStack stack = layerStacksIterator.next();
				//String layerName = layerNamesIterator.next();
				IRenderLayerStack stack = renderOrder.getStackForIndex(i);
				String layerName = renderOrder.getLayerForIndex(i);

				if(stack == null) {
					continue;	// empty index
				}


				synchronized(stack) {
					Iterator<IRenderCallback> renderCallbackIterator = stack.getCallbacks().iterator();
					Iterator<Double> positionIterator = stack.getPositions().iterator();
					while(renderCallbackIterator.hasNext()) {
						IRenderCallback callback = renderCallbackIterator.next();
						double position = positionIterator.next();

						callback.renderCall(layerName, position);
					}
				}


				IRenderLayerStack idxLayerStack = renderOrder.getStackForIndex(i);
				String idxLayerName = renderOrder.getLayerForIndex(i);
				if(idxLayerStack != stack) {
					throw new IllegalStateException(String.format("Expected stack %s but was %s", stack, idxLayerStack));
				}
				if(idxLayerName != layerName) {
					throw new IllegalStateException(String.format("Expected name %s but was %s", layerName, idxLayerName));
				}

			}
		}
	}
	
	
	private static class TestRenderCallback implements IRenderCallback {
		
		private final String description;
		
		public TestRenderCallback(String description) {
			this.description = description;
		}

		
		@Override
		public void renderCall(String layerName, double stackPosition) {
			System.out.println(String.format("Callback for layer: %s with position: %s", layerName, stackPosition));
		}

		@Override
		public String getDescription() {
			return description;
		}
	}
	
	
}
