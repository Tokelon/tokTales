package com.tokelon.toktales.core.engine.log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import java9.util.function.Consumer;

public class LoggingOutputStream extends OutputStream {


	public static final int DEFAULT_BUFFER_SIZE = 2048;


	private final ByteArrayOutputStream bufferStream;

	private final Consumer<String> loggingConsumer;

	public LoggingOutputStream(Consumer<String> loggingConsumer) {
		this(loggingConsumer, DEFAULT_BUFFER_SIZE);
	}

	public LoggingOutputStream(Consumer<String> loggingConsumer, int bufferSize) {
		this.loggingConsumer = loggingConsumer;
		this.bufferStream = new ByteArrayOutputStream(bufferSize);
	}


	protected void commitToLog() {
		String message = bufferStream.toString(); // TODO: Use UTF-8 charset?
		if(!message.isEmpty()) {
			loggingConsumer.accept(message);
		}

		bufferStream.reset();
	}

	@Override
	public void write(int b) throws IOException {
		if(b == '\n' || b == '\r') {
			commitToLog();
		}
		else {
			bufferStream.write(b);
		}
	}

}
