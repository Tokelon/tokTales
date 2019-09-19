package com.tokelon.toktales.core.engine.log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import java9.util.function.Consumer;

public class LoggingOutputStream extends OutputStream {


	private final ByteArrayOutputStream bufferStream;
	
	private Consumer<String> loggingConsumer;
	
	public LoggingOutputStream(Consumer<String> loggingConsumer) {
		this.loggingConsumer = loggingConsumer;
		this.bufferStream = new ByteArrayOutputStream(1024);
	}

	
	@Override
	public void write(int b) throws IOException {
		if(b == '\n') {
			loggingConsumer.accept(bufferStream.toString()); // TODO: Select charset?
			bufferStream.reset();
		}
		else {
			bufferStream.write(b);
		}
	}

}
