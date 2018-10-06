package com.tokelon.toktales.core.game.model.map;

import javax.inject.Inject;

import com.tokelon.toktales.core.game.world.IObjectContainer;
import com.tokelon.toktales.core.game.world.ObjectContainer;
import com.tokelon.toktales.core.resources.IResourceSet;
import com.tokelon.toktales.core.resources.ResourceSet;

public class EmptyBlockMap implements IBlockMap {
	

	private final ObjectContainer<IMapObject> objectContainer = new ObjectContainer<>();

	private final IBlockMapConfig config;
	private final IResourceSet resourceSet;
	private final ILevelReference levelReference;
	private final IMapLayer emptyLayer;
	private final IBlock emptyBlock;
	
	@Inject
	public EmptyBlockMap() {
		this.config = new IBlockMapConfig.EmptyBlockMapConfig();
		this.resourceSet = new ResourceSet();
		this.levelReference = Level10Reference.getInstance();
		this.emptyLayer = new MapLayerImpl("");
		this.emptyBlock = new Level10Block();
	}
	

	@Override
	public IObjectContainer<IMapObject> getObjectContainer() {
		return objectContainer;
	}

	@Override
	public IBlockMapConfig getConfig() {
		return config;
	}

	@Override
	public String getName() {
		return "";
	}

	@Override
	public boolean hasFileNameAttached() {
		return false;
	}

	@Override
	public String getFileName() {
		return null;
	}

	@Override
	public int getVerticalSize() {
		return 0;
	}

	@Override
	public int getHorizontalSize() {
		return 0;
	}

	@Override
	public IResourceSet getResources() {
		return resourceSet;
	}

	@Override
	public ILevelReference getLevelReference() {
		return levelReference;
	}

	@Override
	public IBlock getBlockAt(int posx, int posy) {
		return emptyBlock;
	}

	@Override
	public IBlock getBlockAt(IMapPosition position) {
		return emptyBlock;
	}

	@Override
	public IMapLayer getLayerOnLevel(int level) {
		return emptyLayer;
	}

	@Override
	public IMapLayer getLayerForName(String name) {
		return emptyLayer;
	}

	@Override
	public int getLevelForLayer(String name) {
		return 0;
	}

}
