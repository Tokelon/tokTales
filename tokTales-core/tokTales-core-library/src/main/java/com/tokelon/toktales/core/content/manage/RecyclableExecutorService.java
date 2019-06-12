package com.tokelon.toktales.core.content.manage;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.inject.Inject;
import javax.inject.Provider;

import com.google.common.util.concurrent.MoreExecutors;
import com.tokelon.toktales.core.engine.inject.annotation.GlobalExecutorServiceImplementation;

public class RecyclableExecutorService implements ExecutorService {


	private volatile ExecutorService currentExecutorService;
	
	private Provider<ExecutorService> executorServiceProvider;
	
	@Inject
	public RecyclableExecutorService(@GlobalExecutorServiceImplementation Provider<ExecutorService> executorServiceProvider) {
		this.executorServiceProvider = executorServiceProvider;
		this.currentExecutorService = createExecutorService();
	}
	
	
	private synchronized ExecutorService createExecutorService() {
		ExecutorService executorService = executorServiceProvider.get();
		
		// Add shutdown hook to make sure the service will be shutdown when the program exits
		MoreExecutors.addDelayedShutdownHook(executorService, 200, TimeUnit.MILLISECONDS);
		return executorService;
	}
	
	private synchronized ExecutorService getExecutorService() {
		if(currentExecutorService.isShutdown()) {
			this.currentExecutorService = createExecutorService();
		}
		
		return currentExecutorService;
	}
	
	
	@Override
	public synchronized void execute(Runnable command) {
		getExecutorService().execute(command);
	}

	@Override
	public synchronized void shutdown() {
		getExecutorService().shutdown();
	}

	@Override
	public synchronized List<Runnable> shutdownNow() {
		return getExecutorService().shutdownNow();
	}

	@Override
	public synchronized boolean isShutdown() {
		return getExecutorService().isShutdown();
	}

	@Override
	public synchronized boolean isTerminated() {
		return getExecutorService().isTerminated();
	}

	@Override
	public synchronized boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
		return getExecutorService().awaitTermination(timeout, unit);
	}

	@Override
	public synchronized <T> Future<T> submit(Callable<T> task) {
		return getExecutorService().submit(task);
	}

	@Override
	public synchronized <T> Future<T> submit(Runnable task, T result) {
		return getExecutorService().submit(task, result);
	}

	@Override
	public synchronized Future<?> submit(Runnable task) {
		return getExecutorService().submit(task);
	}

	@Override
	public synchronized <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
		return getExecutorService().invokeAll(tasks);
	}

	@Override
	public synchronized <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
		return getExecutorService().invokeAll(tasks, timeout, unit);
	}

	@Override
	public synchronized <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
		return getExecutorService().invokeAny(tasks);
	}

	@Override
	public synchronized <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
		return getExecutorService().invokeAny(tasks, timeout, unit);
	}
	
}