package com.tokelon.toktales.desktop.ui.window;

public interface IWindowFactory {


	public IWindowFactory withDefaultHints();

	public IWindow createDefault();
	public IWindowBuilder createDefaultBuilder();
	
	public IWindow create(int width, int height, String title);
	public IWindow create(int width, int height, String title, long monitor);
	public IWindow create(int width, int height, String title, long monitor, long share);

	public IWindowBuilder createBuilder(int width, int height, String title);
	public IWindowBuilder createBuilder(int width, int height, String title, long monitor);
	public IWindowBuilder createBuilder(int width, int height, String title, long monitor, long share);

	
	public IWindow createFullscreen(String title);
	public IWindowBuilder createFullscreenBuilder(String title);
	
	public IWindow createBorderless(String title);
	public IWindowBuilder createBorderlessBuilder(String title);
	
}
