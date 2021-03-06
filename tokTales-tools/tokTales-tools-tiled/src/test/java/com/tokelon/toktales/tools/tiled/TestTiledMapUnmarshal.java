package com.tokelon.toktales.tools.tiled;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.reflection.FieldDictionary;
import com.thoughtworks.xstream.converters.reflection.PureJavaReflectionProvider;
import com.thoughtworks.xstream.converters.reflection.SortableFieldKeySorter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.tokelon.toktales.tools.tiled.marshal.ReflectionConverterWrapper;
import com.tokelon.toktales.tools.tiled.marshal.TiledMapLevelHolderConverterListener;
import com.tokelon.toktales.tools.tiled.model.ITiledMap;
import com.tokelon.toktales.tools.tiled.model.TMXObjectImpl;
import com.tokelon.toktales.tools.tiled.model.TiledMapImagelayerImpl;
import com.tokelon.toktales.tools.tiled.model.TiledMapLayerImpl;
import com.tokelon.toktales.tools.tiled.model.TiledMapObjectgroupImpl;
import com.tokelon.toktales.tools.tiled.model.TiledMapTilesetImpl;
import com.tokelon.toktales.tools.tiled.model.TiledMapXML;

public class TestTiledMapUnmarshal {
	
	
	public static final String FILE_PATH = "T:\\Tokelon\\Workspace\\Tiled Editor\\Pokemon\\Maps\\test_map.tmx";
	
	
	public static void main(String[] args) throws FileNotFoundException  {
		
		
		SortableFieldKeySorter sorter = new SortableFieldKeySorter();
		sorter.registerFieldOrder(TiledMapXML.class, TiledMapXML.FIELD_ORDER);
		sorter.registerFieldOrder(TiledMapTilesetImpl.class, TiledMapTilesetImpl.FIELD_ORDER);
		sorter.registerFieldOrder(TMXObjectImpl.class, TMXObjectImpl.FIELD_ORDER);
		
		FieldDictionary fieldDictionary = new FieldDictionary(sorter);
		
		PureJavaReflectionProvider reflectionProvider = new PureJavaReflectionProvider(fieldDictionary);
		
		//SunLimitedUnsafeReflectionProvider		// Uses undocumented features (that might not be available)
		//JVM.newReflectionProvider(fieldDictionary)

		
		
		XStream xstream = new XStream(reflectionProvider, new DomDriver());
		xstream.processAnnotations(TiledMapXML.class);
		
		// Add listener for layer, imagelayer, objectgroup
		TiledMapLevelHolderConverterListener levelHolderListener = new TiledMapLevelHolderConverterListener();
		
		ReflectionConverterWrapper layerConverter = new ReflectionConverterWrapper(xstream.getMapper(), xstream.getReflectionProvider(), TiledMapLayerImpl.class);
		layerConverter.setListener(levelHolderListener);
		xstream.registerConverter(layerConverter);
		
		ReflectionConverterWrapper imagelayerConverter = new ReflectionConverterWrapper(xstream.getMapper(), xstream.getReflectionProvider(), TiledMapImagelayerImpl.class);
		imagelayerConverter.setListener(levelHolderListener);
		xstream.registerConverter(imagelayerConverter);
		
		ReflectionConverterWrapper objectgroupConverter = new ReflectionConverterWrapper(xstream.getMapper(), xstream.getReflectionProvider(), TiledMapObjectgroupImpl.class);
		objectgroupConverter.setListener(levelHolderListener);
		xstream.registerConverter(objectgroupConverter);
		
		
		
		
		
		InputStream in = new FileInputStream(FILE_PATH);
		Object res = xstream.fromXML(in);
		
		if(!(res instanceof ITiledMap)) {
			throw new RuntimeException("Wrong object type");
		}
		ITiledMap tiledMap = (ITiledMap) res;
		
		
		System.out.println("Unmarshal complete");
	}
	
	
	
	
}
