package com.tokelon.toktales.core.content.manage;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;

import javax.inject.Provider;

import org.slf4j.ILoggerFactory;
import org.slf4j.LoggerFactory;

import com.tokelon.toktales.tools.assets.loader.AbstractExecutorServiceAssetLoader;
import com.tokelon.toktales.tools.assets.loader.IAssetDecoder;
import com.tokelon.toktales.tools.assets.loader.IAssetLoader;
import com.tokelon.toktales.tools.assets.reader.DefaultAssetReaderManager;
import com.tokelon.toktales.tools.assets.reader.IAssetReader;
import com.tokelon.toktales.tools.assets.reader.IAssetReaderManager;

import java9.util.concurrent.CompletableFuture;
import java9.util.function.IntFunction;
import java9.util.stream.Collectors;
import java9.util.stream.IntStream;
import java9.util.stream.Stream;

class TestExecutorServiceAssetLoader {


	public static void main(String[] args) {
		System.out.println("TestExecutorServiceAssetLoader started");
		
		TestDecoder testDecoder = new TestDecoder();
		TestLoader testLoader = new TestLoader(LoggerFactory.getILoggerFactory(), new DefaultAssetReaderManager(new HashMap<>()), testDecoder);
		
		
		int tasks = 10;
		List<AssetObject> results;
		
		testLoader.start();
		try {
			
			Stream<CompletableFuture<AssetObject>> futureStream = IntStream.rangeClosed(1, tasks).mapToObj((i) -> enqueue(testLoader, i));
			results = futureStream.map(CompletableFuture::join).collect(Collectors.toList());
		}
		finally {
			testLoader.stop();
		}
		
		System.out.println("Result count was:" + results.size());
		
		System.out.println("TestExecutorServiceAssetLoader terminated");
	}
	
	private static CompletableFuture<AssetObject> enqueue(IAssetLoader<AssetObject, AssetKey, AssetOptions> loader, int i) {
		IntFunction<String> keyFunc = (counter) -> String.format("0x%08X", counter);

		AssetKey assetKey = new AssetKey(keyFunc.apply(i));
		CompletableFuture<AssetObject> enqueue = loader.enqueue(assetKey);
		
		enqueue.thenAccept((result) -> System.out.println(String.format("Result of key=%s is value=%s", assetKey, result)));
		return enqueue;
	}

	
	private static class AssetKey {
		private final String name;

		public AssetKey(String name) {
			this.name = name;
		}
		
		@Override
		public String toString() {
			return String.format("AssetKey name=%s", name);
		}
	}
	
	private static class AssetObject {
		private final String name;
		private final String value;

		public AssetObject(String name, String value) {
			this.name = name;
			this.value = value;
		}
		
		@Override
		public String toString() {
			return String.format("AssetObject name=%s value=%s", name, value);
		}
	}

	private static class AssetOptions {	}
	
	
	private static class TestDecoder implements IAssetDecoder<AssetObject, AssetKey, AssetOptions> {

		@Override
		public AssetObject decode(InputStream inputstream, AssetKey key) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public AssetObject decode(InputStream inputstream, AssetKey key, AssetOptions options) {
			// TODO Auto-generated method stub
			return null;
		}
	}
	
	
	private static class TestLoader extends AbstractExecutorServiceAssetLoader<AssetObject, AssetKey, AssetOptions> {
		
		public static final boolean ENABLE_LOG_DEBUG = false;
		
		private static final AssetOptions EMPTY_OPTIONS = new AssetOptions();
		
		
		private int counter = 0;
		
		public TestLoader(ILoggerFactory loggerFactory, IAssetReaderManager readerManager, IAssetDecoder<AssetObject, AssetKey, AssetOptions> decoder) {
			super(loggerFactory, readerManager, decoder);
			
			setLogDebugEnabled(ENABLE_LOG_DEBUG);
		}
		
		public TestLoader(ILoggerFactory loggerFactory, IAssetReaderManager readerManager, IAssetDecoder<AssetObject, AssetKey, AssetOptions> decoder, Provider<ExecutorService> executorServiceProvider) {
			super(loggerFactory, readerManager, decoder, executorServiceProvider);
			
			setLogDebugEnabled(ENABLE_LOG_DEBUG);
		}

		
		@Override
		protected AssetOptions getDefaultOptions() {
			return EMPTY_OPTIONS;
		}
		
		
		@Override
		public AssetObject load(AssetKey key, AssetOptions options, IAssetReader assetReader, IAssetDecoder<? extends AssetObject, AssetKey, AssetOptions> decoder) {
			System.out.println(String.format("Load start asset [%s] with [%s, %s, %s]", key, options, assetReader, decoder));
			
			try {
				// Emulate loading
				Thread.sleep(500);
			} catch (InterruptedException e) { }
			
			AssetObject assetObject = new AssetObject(key.name, String.valueOf(++counter));
			
			System.out.println(String.format("Load stop asset [%s] with [%s, %s, %s]", key, options, assetReader, decoder));
			return assetObject;
		}
	}
	
}
