package com.tokelon.toktales.core.engine;

public class SleepingEngineLooper implements IEngineLooper {


	private boolean isStopped;
	
	private final Object waitObject = new Object();
	
	
	@Override
	public void loop(IEngineContext engineContext) throws EngineException {
		synchronized (waitObject) {
			isStopped = false;
			
			while(!isStopped) {
				try {
					waitObject.wait();
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
		}
	}
	
	
	@Override
	public void stop() {
		synchronized (waitObject) {
			isStopped = true;
			waitObject.notify();
		}
	}

}
