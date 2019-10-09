package com.tokelon.toktales.tools.core.tiled;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.reflection.FieldDictionary;
import com.thoughtworks.xstream.converters.reflection.PureJavaReflectionProvider;
import com.thoughtworks.xstream.converters.reflection.SortableFieldKeySorter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.tokelon.toktales.tools.core.tiled.marshal.ReflectionConverterWrapper;
import com.tokelon.toktales.tools.core.tiled.marshal.TiledMapLevelHolderConverterListener;
import com.tokelon.toktales.tools.core.tiled.model.ITiledMap;
import com.tokelon.toktales.tools.core.tiled.model.TMXObjectImpl;
import com.tokelon.toktales.tools.core.tiled.model.TiledMapImagelayerImpl;
import com.tokelon.toktales.tools.core.tiled.model.TiledMapLayerImpl;
import com.tokelon.toktales.tools.core.tiled.model.TiledMapObjectgroupImpl;
import com.tokelon.toktales.tools.core.tiled.model.TiledMapTilesetImpl;
import com.tokelon.toktales.tools.core.tiled.model.TiledMapXML;

public class TestTiledMapMarshal {
	
	
	public static final String FILE_PATH = "T:\\Tokelon\\Workspace\\Tiled Editor\\Pokemon\\Maps\\test_map.tmx";
	public static final String FILE_PATH_OUTPUT = "T:\\Tokelon\\Workspace\\Tiled Editor\\Pokemon\\Maps\\test_map_output.tmx";
	
	
	public static void main(String[] args) throws FileNotFoundException {
		
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
		
	

		OutputStream out = new FileOutputStream(FILE_PATH_OUTPUT);
		xstream.toXML(tiledMap, out);
		
		
		System.out.println("Marshal complete");
		

	}
	

}
