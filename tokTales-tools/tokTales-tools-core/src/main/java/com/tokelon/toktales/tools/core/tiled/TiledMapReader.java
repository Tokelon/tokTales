package com.tokelon.toktales.tools.core.tiled;

import java.io.InputStream;

import com.thoughtworks.xstream.InitializationException;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.XStreamException;
import com.thoughtworks.xstream.converters.reflection.FieldDictionary;
import com.thoughtworks.xstream.converters.reflection.PureJavaReflectionProvider;
import com.thoughtworks.xstream.converters.reflection.SortableFieldKeySorter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.tokelon.toktales.tools.core.tiled.marshal.ReflectionConverterWrapper;
import com.tokelon.toktales.tools.core.tiled.marshal.TiledMapLevelHolderConverterListener;
import com.tokelon.toktales.tools.core.tiled.marshal.TiledMapUnmarshalException;
import com.tokelon.toktales.tools.core.tiled.model.ITiledMap;
import com.tokelon.toktales.tools.core.tiled.model.TMXObjectImpl;
import com.tokelon.toktales.tools.core.tiled.model.TiledMapImagelayerImpl;
import com.tokelon.toktales.tools.core.tiled.model.TiledMapLayerImpl;
import com.tokelon.toktales.tools.core.tiled.model.TiledMapObjectgroupImpl;
import com.tokelon.toktales.tools.core.tiled.model.TiledMapTilesetImpl;
import com.tokelon.toktales.tools.core.tiled.model.TiledMapXML;

public class TiledMapReader {

	
	public ITiledMap readMap(InputStream in, String fileName) throws TiledMapFormatException {
	
		
		// Setup field order for classes where it matters
		SortableFieldKeySorter sorter = new SortableFieldKeySorter();
		
		sorter.registerFieldOrder(TiledMapXML.class, TiledMapXML.FIELD_ORDER);
		sorter.registerFieldOrder(TiledMapTilesetImpl.class, TiledMapTilesetImpl.FIELD_ORDER);
		sorter.registerFieldOrder(TMXObjectImpl.class, TMXObjectImpl.FIELD_ORDER);
		
		
		// Additional classes needed
		FieldDictionary fieldDictionary = new FieldDictionary(sorter);
		PureJavaReflectionProvider reflectionProvider = new PureJavaReflectionProvider(fieldDictionary);
		
		//SunLimitedUnsafeReflectionProvider		// Uses undocumented features (that might not be available)
		//JVM.newReflectionProvider(fieldDictionary)

		
		
		
		
		XStream xstream;
		try {
			xstream = new XStream(reflectionProvider);	//new DomDriver()
		}
		catch(InitializationException ine) {
			throw new TiledMapFormatException(ine);
		}
		
		// Process annotations
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


		
		
		// Unmarshal
		Object result;
		try {
			result = xstream.fromXML(in);
		}
		catch(XStreamException xe) {
			throw new TiledMapFormatException(xe);
		}
		catch(TiledMapUnmarshalException ume) {
			throw new TiledMapFormatException(ume);
		}
		
		
		
		
		if(!(result instanceof TiledMapXML)) {
			throw new TiledMapFormatException("File is not the expected type");
		}
		
		TiledMapXML map = (TiledMapXML) result;
		map.setFilename(fileName);
		
		return map;
	}
	
	
}
