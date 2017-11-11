package com.tokelon.toktales.core.logic.process;

public interface IPauseableProcess extends IProcess {


	public void pause();
	
	public void unpause();
	
	
	
	public class EmptyPauseableProcess implements IPauseableProcess {
		@Override
		public void startProcess() { }

		@Override
		public void endProcess() { }

		@Override
		public void pause() { }

		@Override
		public void unpause() { }
	}
	
}
