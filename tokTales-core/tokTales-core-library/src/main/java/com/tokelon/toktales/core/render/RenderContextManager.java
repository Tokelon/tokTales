package com.tokelon.toktales.core.render;

import com.tokelon.toktales.core.screen.view.IViewTransformer;

import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RenderContextManager implements IRenderContextManager {


	private boolean isCreated = false;
	private boolean isValid = false;

	private Matrix4f projectionMatrix;
	private IViewTransformer viewTransformer;

	private final List<IRenderContext> renderContextList;
	private final List<IRenderContext> renderContextListUnmodifiable;


	public RenderContextManager() {
		this.renderContextList = Collections.synchronizedList(new ArrayList<>());
		this.renderContextListUnmodifiable = Collections.unmodifiableList(renderContextList);
	}


	@Override
	public boolean isContextCreated() {
		return isCreated;
	}

	@Override
	public boolean isContextValid() {
		return isValid;
	}


	@Override
	public void addContext(IRenderContext context) {
		if(renderContextList.contains(context)) {
			return;
		}

		renderContextList.add(context);

		if(isCreated) {
			context.contextCreated();

			if(isValid) {
				context.contextChanged(viewTransformer, projectionMatrix);
			}
		}
	}

	@Override
	public boolean removeContext(IRenderContext context) {
		boolean contains = renderContextList.contains(context);
		if(contains && isCreated) {
			context.contextDestroyed();
		}

		renderContextList.remove(context);
		return contains;
	}

	@Override
	public boolean hasContext(IRenderContext context) {
		return renderContextList.contains(context);
	}

	@Override
	public List<IRenderContext> getContextList() {
		return renderContextListUnmodifiable;
	}


	@Override
	public void contextCreated() {
		this.isValid = false; // Do this?

		this.isCreated = true;

		synchronized (renderContextList) {
			for(IRenderContext context: renderContextList) {
				context.contextCreated();
			}
		}
	}

	@Override
	public void contextChanged(IViewTransformer viewTransformer, Matrix4f projectionMatrix) {
		this.viewTransformer = viewTransformer;
		this.projectionMatrix = projectionMatrix;
		this.isValid = true;

		// Iterate over the managed renderers
		synchronized (renderContextList) {
			for(IRenderContext context: renderContextList) {
				context.contextChanged(viewTransformer, projectionMatrix);
			}
		}
	}

	@Override
	public void contextDestroyed() {
		this.isCreated = false;
		this.isValid = false;

		synchronized (renderContextList) {
			for(IRenderContext context: renderContextList) {
				context.contextDestroyed();
			}
		}
	}

}
