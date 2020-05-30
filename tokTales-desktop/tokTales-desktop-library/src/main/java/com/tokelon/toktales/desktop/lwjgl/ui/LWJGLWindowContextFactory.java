package com.tokelon.toktales.desktop.lwjgl.ui;

import com.google.inject.ConfigurationException;
import com.google.inject.ProvisionException;
import com.tokelon.toktales.core.content.manage.bitmap.IBitmapAssetManager;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.desktop.input.IDesktopInputDriver;
import com.tokelon.toktales.desktop.lwjgl.ILWJGLInputService;
import com.tokelon.toktales.desktop.lwjgl.input.GLFWInputDriver;
import com.tokelon.toktales.desktop.render.IWindowRenderer;
import com.tokelon.toktales.desktop.ui.window.FileWindowIconSetter;
import com.tokelon.toktales.desktop.ui.window.IWindowContext;
import com.tokelon.toktales.desktop.ui.window.IWindowContextBuilder;
import com.tokelon.toktales.desktop.ui.window.IWindowIconSetter;
import com.tokelon.toktales.tools.core.objects.pools.IObjectPool.IObjectPoolFactory;

/** LWJGL implementation of {@link IWindowContextFactory}.
 */
public class LWJGLWindowContextFactory implements IWindowContextFactory {


	public static final String DEFAULT_ICON_PATH = "Data/Assets/icon.png";


	private final String defaultIconPath;

	/** Default constructor.
	 * <p>
	 * {@link #DEFAULT_ICON_PATH} will be used for the default icon path.
	 *
	 */
	public LWJGLWindowContextFactory() {
		this(DEFAULT_ICON_PATH);
	}

	/** Constructor with a default icon path.
	 *
	 * @param defaultIconPath
	 */
	public LWJGLWindowContextFactory(String defaultIconPath) {
		this.defaultIconPath = defaultIconPath;
	}


	@Override
	public IWindowContext createDefault() {
		return createDefaultBuilder().build();
	}

	@Override
	public IWindowContextBuilder createDefaultBuilder() {
		return new LWJGLWindowContextBuilder(
				new LWJGLWindowFactory(),
				new LWJGLWindowToolkit(),
				new LWJGLWindowFactory().createDefaultBuilder(),
				new LWJGLWindowFactory().getEmptyConfigurator(),
				createDefaultIconSetterFactory(),
				createDefaultRendererFactory(),
				createDefaultInputDriverFactory()
		);
	}


	@Override
	public IWindowContextIconSetterFactory createDefaultIconSetterFactory() {
		return createIconSetterFactory(defaultIconPath);
	}

	@Override
	public IWindowContextIconSetterFactory createIconSetterFactory(String iconPath) {
		return new LWJGLWindowContextIconSetterFactory(iconPath);
	}

	@Override
	public IWindowContextRendererFactory createDefaultRendererFactory() {
		return new LWJGLWindowContextRendererFactory();
	}

	@Override
	public IWindowContextInputDriverFactory createDefaultInputDriverFactory() {
		return new LWJGLWindowContextInputDriverFactory();
	}


	/** LWJGL implementation of {@link IWindowContextIconSetterFactory}.
	 */
	public static class LWJGLWindowContextIconSetterFactory implements IWindowContextIconSetterFactory {
		private final String iconPath;

		public LWJGLWindowContextIconSetterFactory(String iconPath) {
			this.iconPath = iconPath;
		}

		@Override
		public IWindowIconSetter create(IEngineContext engineContext) {
			try {
				// We assume the instance will be resolved, otherwise we cannot use this icon setter
				IBitmapAssetManager bitmapAssetManager = engineContext.getInjector().getInstance(IBitmapAssetManager.class);

				return new FileWindowIconSetter(engineContext.getLogging(), bitmapAssetManager, iconPath);
			}
			catch(ConfigurationException | ProvisionException e) {
				// TODO: Log an informative error message. Then re-throw exception?
				throw e;
			}
		}
	}

	/** LWJGL implementation of {@link IWindowContextInputDriverFactory}.
	 */
	public static class LWJGLWindowContextInputDriverFactory implements IWindowContextInputDriverFactory {

		@Override
		public IDesktopInputDriver create(IEngineContext engineContext) {
			try {
				// We assume the instance will be resolved, otherwise we cannot use this input driver
				ILWJGLInputService inputService = engineContext.getInjector().getInstance(ILWJGLInputService.class);

				return new GLFWInputDriver(
						inputService.getMainInputDispatch().getGLFWInputConsumer(),
						inputService.getMainInputDispatch().getInputProducer(),
						engineContext.getInjector().getInstance(IObjectPoolFactory.class)
				);
			}
			catch (ConfigurationException | ProvisionException e) {
				// TODO: Log an informative error message. Then re-throw exception?
				throw e;
			}
		}
	}

	/** LWJGL implementation of {@link IWindowContextRendererFactory}.
	 */
	public static class LWJGLWindowContextRendererFactory implements IWindowContextRendererFactory {

		@Override
		public IWindowRenderer create(IEngineContext engineContext) {
			try {
				// We assume the instance will be resolved, otherwise we cannot use this renderer
				ILWJGLInputService inputService = engineContext.getInjector().getInstance(ILWJGLInputService.class);

				return new DefaultGameWindowRenderer(
						engineContext.getEngine().getRenderService().getSurfaceManager(),
						inputService.getMainInputDispatch().getGLFWInputConsumer(),
						engineContext.getEngine().getEngineDriver());
			}
			catch(ConfigurationException | ProvisionException e) {
				// TODO: Log an informative error message. Then re-throw exception?
				throw e;
			}
		}
	}

}
