package com.tokelon.toktales.desktop.engine.inject;

import com.tokelon.toktales.core.content.manage.PathAssetReadersInjectModule;
import com.tokelon.toktales.desktop.content.DesktopParentIdentifiersInjectModule;
import com.tokelon.toktales.desktop.lwjgl.LWJGLInjectModule;
import com.tokelon.toktales.desktop.lwjgl.render.LWJGLRenderDriversInjectModule;
import com.tokelon.toktales.desktop.lwjgl.render.opengl.gl20.DesktopGLInjectModule;
import com.tokelon.toktales.tools.core.sub.inject.config.HierarchicalInjectConfig;

public class DesktopInjectConfig extends HierarchicalInjectConfig {


	@Override
	public void configure() {
		super.configure();

		extend(new DesktopInjectModule());

		extend(new DesktopValuesInjectModule());

		extend(new DesktopParentIdentifiersInjectModule());
		extend(new PathAssetReadersInjectModule());

		extend(new DesktopGLInjectModule());

		extend(new LWJGLInjectModule());
		extend(new LWJGLRenderDriversInjectModule());


		extend(new DesktopToolsInjectModule());
	}

}
