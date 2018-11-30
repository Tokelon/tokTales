package com.tokelon.toktales.android.input.dispatch;

import javax.inject.Inject;

import com.tokelon.toktales.android.input.dispatch.IAndroidInputConsumer.IAndroidInputConsumerFactory;
import com.tokelon.toktales.android.input.dispatch.IAndroidInputProducer.IAndroidInputProducerFactory;

public class AndroidInputDispatch implements IAndroidInputDispatch {

	
	private final IAndroidInputProducer producer;
	private final IAndroidInputConsumer consumer;

	@Inject
	public AndroidInputDispatch(IAndroidInputProducerFactory producerFactory, IAndroidInputConsumerFactory consumerFactory) {
		this.producer = producerFactory.create(this);
		this.consumer = consumerFactory.create(this);
	}
	
	
	@Override
	public IAndroidInputProducer getInputProducer() {
		return producer;
	}

	@Override
	public IAndroidInputConsumer getInputConsumer() {
		return consumer;
	}

}
