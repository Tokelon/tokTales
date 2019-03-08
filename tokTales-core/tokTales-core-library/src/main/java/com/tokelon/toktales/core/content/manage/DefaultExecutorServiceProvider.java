package com.tokelon.toktales.core.content.manage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import javax.inject.Provider;

public class DefaultExecutorServiceProvider implements Provider<ExecutorService> {

	
	@Override
	public ExecutorService get() {
		// TODO: Use Guava executor ?
		//MoreExecutors.getExitingExecutorService(new ThreadPoolExecutor(1, 1, keepAliveTime, unit, workQueue), 0, TimeUnit.MILLISECONDS);
		return Executors.newSingleThreadExecutor(new ExecutorServiceDaemonThreadFactory());
	}


	protected static class ExecutorServiceDaemonThreadFactory implements ThreadFactory {
		private final ThreadFactory defaultThreadFactory = Executors.defaultThreadFactory();
		
		@Override
		public Thread newThread(Runnable runnable) {
			Thread newThread = defaultThreadFactory.newThread(runnable);
			newThread.setDaemon(true);
			
			return newThread;
		};
	}
	
}
