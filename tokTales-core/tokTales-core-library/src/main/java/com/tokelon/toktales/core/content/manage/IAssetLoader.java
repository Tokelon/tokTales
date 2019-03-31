package com.tokelon.toktales.core.content.manage;

import com.tokelon.toktales.core.engine.content.ContentException;

import java9.util.concurrent.CompletableFuture;

public interface IAssetLoader<T, K, O> {
	// TODO: What to do with decoder parameter? Allow throwing exceptions for unsupported?
	// TODO: Change return type of T to <? extends T> ?
	
	// If we're gonna have the decoder as params, the we need the default decoder as well
	public IAssetDecoder<? extends T, K, O> getDefaultDecoder();
	
	public IAssetReaderManager getReaderManager();
	
	
	public void start();
	public void stop();
	
	
	public CompletableFuture<T> enqueue(K key);
	public CompletableFuture<T> enqueue(K key, O options);
	public CompletableFuture<T> enqueue(K key, IAssetReader reader);
	public CompletableFuture<T> enqueue(K key, IAssetDecoder<? extends T, K, O> decoder);
	public CompletableFuture<T> enqueue(K key, O options, IAssetReader reader);	
	public CompletableFuture<T> enqueue(K key, O options, IAssetDecoder<? extends T, K, O> decoder);
	public CompletableFuture<T> enqueue(K key, O options, IAssetReader reader, IAssetDecoder<? extends T, K, O> decoder);
	
	
	// Document that these throw ContentNotFoundException, ContentLoadException ?
	public T load(K key) throws ContentException;
	public T load(K key, O options) throws ContentException;
	public T load(K key, IAssetReader reader) throws ContentException;
	public T load(K key, IAssetDecoder<? extends T, K, O> decoder) throws ContentException;
	public T load(K key, O options, IAssetReader reader) throws ContentException;
	public T load(K key, O options, IAssetDecoder<? extends T, K, O> decoder) throws ContentException;
	public T load(K key, O options, IAssetReader reader, IAssetDecoder<? extends T, K, O> decoder) throws ContentException;
	
	
	
	public interface IAssetLoaderFactory {

		public <T, K, O> DefaultAssetLoader<T, K, O> create(IAssetDecoder<? extends T, K, O> decoder);
		public <T, K, O> DefaultAssetLoader<T, K, O> create(IAssetDecoder<? extends T, K, O> decoder, IAssetReaderManager readerManager);
	}
	
}
