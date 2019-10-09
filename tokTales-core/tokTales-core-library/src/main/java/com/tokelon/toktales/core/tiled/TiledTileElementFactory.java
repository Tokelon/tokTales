package com.tokelon.toktales.core.tiled;

import java.util.HashMap;
import java.util.Map;

import com.tokelon.toktales.core.content.sprite.ISpriteset;
import com.tokelon.toktales.core.storage.utils.MutablePathImpl;
import com.tokelon.toktales.tools.tiledmap.model.ITMXImage;
import com.tokelon.toktales.tools.tiledmap.model.ITMXTile;
import com.tokelon.toktales.tools.tiledmap.model.ITiledMapTileset;

public class TiledTileElementFactory {
	
	private final Map<ITiledMapTileset, ISpriteset> spritesetMap = new HashMap<ITiledMapTileset, ISpriteset>();


	public TiledTileElement makeElement(ITMXTile elementTile) {
		
		TiledTileSprite sprite;
		
		if(elementTile.hasImage()) {
			ITMXImage tileImage = elementTile.getImage();

			sprite = new TiledTileSprite(getNameFromImageSource(tileImage));
		}
		else {
			ITiledMapTileset tileset = elementTile.getParentTileset();
			
			ISpriteset spriteset = spritesetMap.get(tileset);
			if(spriteset == null) {
				ITMXImage tilesetImage = tileset.getImage();
				spriteset = new TiledSpriteset(getNameFromImageSource(tilesetImage), tileset);
				
				spritesetMap.put(tileset, spriteset);
			}
			
			sprite = new TiledTileSprite(elementTile.getID(), spriteset);
		}
		
		return new TiledTileElement(elementTile, sprite);
		
	}
	
	
	
	private String getNameFromImageSource(ITMXImage img) {
		String imgSource = img.getSource();
		String srcName;
		if(imgSource.contains("/")) {
			MutablePathImpl path = new MutablePathImpl(imgSource, "/");
			srcName = path.getLastPlace();
		}
		else if(imgSource.contains("\\")) {
			MutablePathImpl path = new MutablePathImpl(imgSource, "\\");
			srcName = path.getLastPlace();
		}
		else {
			srcName = imgSource;
		}
		
		return srcName;
	}
	
}
