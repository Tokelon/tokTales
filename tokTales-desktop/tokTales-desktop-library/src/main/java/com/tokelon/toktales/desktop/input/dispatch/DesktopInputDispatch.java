package com.tokelon.toktales.desktop.input.dispatch;

import javax.inject.Inject;

import com.tokelon.toktales.desktop.input.dispatch.IDesktopInputConsumer.IDesktopInputConsumerFactory;
import com.tokelon.toktales.desktop.input.dispatch.IDesktopInputProducer.IDesktopInputProducerFactory;

public class DesktopInputDispatch implements IDesktopInputDispatch {

	
	private final IDesktopInputProducer producer;
	private final IDesktopInputConsumer consumer;
	
	@Inject
	public DesktopInputDispatch(IDesktopInputProducerFactory producerFactory, IDesktopInputConsumerFactory consumerFactory) {
		this.producer = producerFactory.create(this);
		this.consumer = consumerFactory.create(this);
	}
	
	
	@Override
	public IDesktopInputProducer getInputProducer() {
		return producer;
	}

	@Override
	public IDesktopInputConsumer getInputConsumer() {
		return consumer;
	}
	
}
