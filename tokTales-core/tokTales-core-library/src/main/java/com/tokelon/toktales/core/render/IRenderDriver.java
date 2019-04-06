package com.tokelon.toktales.core.render;

import java.util.List;

import org.joml.Matrix4f;

import com.tokelon.toktales.core.render.model.IRenderModel;
import com.tokelon.toktales.core.util.options.INamedOptions;

public interface IRenderDriver {

	
	public void create();
	
	public void destroy();
	
	public boolean supports(String target);
	
	
	
	public void drawQuick(Matrix4f matrixProjectionView, IRenderModel renderModel, INamedOptions options);

	public void use(Matrix4f matrixProjectionView);
	public void draw(IRenderModel renderModel, INamedOptions options);
	public void drawBatch(List<IRenderModel> modelList, INamedOptions options);
	public void release();
	
	
}
