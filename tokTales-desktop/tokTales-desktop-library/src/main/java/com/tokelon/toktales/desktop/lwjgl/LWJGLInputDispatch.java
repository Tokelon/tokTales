package com.tokelon.toktales.desktop.lwjgl;

import javax.inject.Inject;

import com.tokelon.toktales.desktop.input.dispatch.DesktopInputDispatch;
import com.tokelon.toktales.desktop.input.dispatch.IDesktopInputConsumer.IDesktopInputConsumerFactory;
import com.tokelon.toktales.desktop.input.dispatch.IDesktopInputProducer.IDesktopInputProducerFactory;
import com.tokelon.toktales.desktop.lwjgl.input.IGLFWInputConsumer;
import com.tokelon.toktales.desktop.lwjgl.input.IGLFWInputConsumer.IGLFWInputConsumerFactory;

public class LWJGLInputDispatch extends DesktopInputDispatch implements ILWJGLInputDispatch {


	private final IGLFWInputConsumer inputConsumerFactory;

	@Inject
	public LWJGLInputDispatch(IDesktopInputProducerFactory producerFactory, IDesktopInputConsumerFactory consumerFactory, IGLFWInputConsumerFactory inputConsumerFactory) {
		super(producerFactory, consumerFactory);

		this.inputConsumerFactory = inputConsumerFactory.create(this);
	}


	@Override
	public IGLFWInputConsumer getGLFWInputConsumer() {
		return inputConsumerFactory;
	}

}
