package com.tokelon.toktales.core.screen.surface;

import java9.util.function.Consumer;

public class SurfaceController implements ISurfaceController {


	private final Consumer<Runnable> eventConsumer;

	public SurfaceController(Consumer<Runnable> eventConsumer) {
		this.eventConsumer = eventConsumer;
	}
	
	
	@Override
	public void queueEvent(Runnable event) {
		eventConsumer.accept(event);
	}

}
