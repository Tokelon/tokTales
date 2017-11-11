package com.tokelon.toktales.tools.tiledmap;

import java.io.InputStream;

import com.thoughtworks.xstream.InitializationException;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.XStreamException;
import com.thoughtworks.xstream.converters.reflection.FieldDictionary;
import com.thoughtworks.xstream.converters.reflection.PureJavaReflectionProvider;
import com.thoughtworks.xstream.converters.reflection.SortableFieldKeySorter;
import com.tokelon.toktales.tools.tiledmap.marshal.TiledMapUnmarshalException;
import com.tokelon.toktales.tools.tiledmap.model.TiledMapTilesetImpl;

public class TiledMapTilesetReader {

	private XStream xstream;	
	
	public void setup() throws TiledMapFormatException {

		// Setup field order for classes where it matters
		SortableFieldKeySorter sorter = new SortableFieldKeySorter();
		sorter.registerFieldOrder(TiledMapTilesetImpl.class, TiledMapTilesetImpl.FIELD_ORDER);
		
		// Additional classes needed
		FieldDictionary fieldDictionary = new FieldDictionary(sorter);
		PureJavaReflectionProvider reflectionProvider = new PureJavaReflectionProvider(fieldDictionary);
		
		
		try {
			xstream = new XStream(reflectionProvider);	//new DomDriver()
		}
		catch(InitializationException ine) {
			throw new TiledMapFormatException(ine);
		}
		
		// Process annotations
		xstream.processAnnotations(TiledMapTilesetImpl.class);
	}
	
	
	public TiledMapTilesetImpl readTileset(InputStream in) throws TiledMapFormatException {
		
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
		
		
		if(!(result instanceof TiledMapTilesetImpl)) {
			throw new TiledMapFormatException("File is not the expected type");
		}
		
		TiledMapTilesetImpl tileset = (TiledMapTilesetImpl) result;
		
		return tileset;
	}
	
}
