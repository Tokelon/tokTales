package com.tokelon.toktales.core.util;


public class FrameTool {

	private static final int SECOND_MILLIS = 1000;
	private static final int FPS_MATCH = 60;//30;
	private static final int FPS_DT = SECOND_MILLIS / FPS_MATCH;
	
	// FPS Match
	private long timeSinceLastPost = 0;
	
	// FPS Counter
	private long time = 0;
	private int counter = 0;
	private int fps = 0;

	
	/** Call this function last, before unlocking the canvas.
	 * 
	 */
	public void matchFps() {
		long dt = System.currentTimeMillis() - timeSinceLastPost;
		if (dt < FPS_DT) {
			try {
				Thread.sleep(FPS_DT - dt);
			} catch (InterruptedException e) {
				// Ignore
			}
		}
		timeSinceLastPost = System.currentTimeMillis();
	}
	
	
	public int countFps() {
		long ct = System.currentTimeMillis();
		if(ct - time > SECOND_MILLIS) {
			//Log.d("MapRenderer", "MILLIS: " +(ct-time));
			//Log.d("MapRenderer", "FPS REDRAW: " +counter);
			
			time = ct;
			fps = counter;
			counter = 0;
			
			return fps;
		}
		else {
			counter++;
			return -1;
		}
	}

	
}
