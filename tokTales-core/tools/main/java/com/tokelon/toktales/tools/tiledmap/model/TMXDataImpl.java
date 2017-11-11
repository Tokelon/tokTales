package com.tokelon.toktales.tools.tiledmap.model;

import java.io.ObjectStreamException;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import com.thoughtworks.xstream.converters.extended.ToAttributedValueConverter;
import com.tokelon.toktales.tools.tiledmap.TiledMapFormatException;
import com.tokelon.toktales.tools.tiledmap.XMLValuesTiledMap;
import com.tokelon.toktales.tools.tiledmap.marshal.TiledMapUnmarshalException;

@XStreamConverter(value=ToAttributedValueConverter.class, strings={"mValueData"})
public class TMXDataImpl implements ITMXData {


	
	@XStreamAsAttribute
	@XStreamAlias(XMLValuesTiledMap.ATTR_NAME_MAP_MULT_DATA_ENCODING)
	private String mAttrEncoding;
	
	@XStreamAsAttribute
	@XStreamAlias(XMLValuesTiledMap.ATTR_NAME_MAP_MULT_DATA_COMPRESSION)
	private String mAttrCompression;
	
	
	
	/* Cannot use both XmlValue and XmlElement
	 * TODO: Possibly fix?
	 */
	private String mValueData;
	
	/*
	@XStreamImplicit(itemFieldName=XMLValuesTiledMap.NODE_NAME_MAP_MULT_DATA_TILE)
	private List<TMXDataTileImpl> mElemListTile = new ArrayList<TMXDataTileImpl>();
	*/
	
	
	public TMXDataImpl() {
		// Default Ctor
	}
	
	
	
	@XStreamOmitField private int mEncoding;
	@XStreamOmitField private int mCompression;
	
	@XStreamOmitField private int[] mValues;

	
	
	private Object readResolve() throws ObjectStreamException {
		// Not called for base classes!
		
		try {
			processUnmarshal();
		} catch (TiledMapFormatException e) {
			throw new TiledMapUnmarshalException(e);
		}
		
		return this;
	}

	private Object writeReplace() throws ObjectStreamException {
		// Not called for base classes?
		
		prepareMarshal();
		
		return this;
	}
	
	
	
	/* Because of ToAttributedValueConverter,
	 * readResolve() will not be called automatically and must be called externally. 
	 * 
	 */
	public void publicProcessUnmarshal() throws TiledMapFormatException {
		processUnmarshal();
	}
	
	private void processUnmarshal() throws TiledMapFormatException {

		mEncoding = parseEncoding(mAttrEncoding);
		if(mEncoding == -1) {
			throw new TiledMapFormatException("Invalid data encoding value: " +mAttrEncoding);
		}
		
		mCompression = parseCompression(mAttrCompression);
		if(mCompression == -1) {
			throw new TiledMapFormatException("Invalid data compression value: " +mAttrCompression);
		}
		
		
		
		// Parse data and build
		
		if(mEncoding == DATA_ENCODING_CSV) {
			
			// Count comma occurrences in the data value
			int commaCount = mValueData.length() - mValueData.replace(",", "").length();

			mValues = new int[commaCount+1];	// Add one additional comma for the last value
			
			
			
			int start = 0, position = 0, count = 0;
			
			while(position < mValueData.length()) {

				char c = mValueData.charAt(position);
				if(c == ',') {
					mValues[count++] = parseDataInt(mValueData.substring(start, position));
					
					start = position+1;
				}
				else if(c == ' ' || c == '\n' || c=='\r') { 	//(!Character.isDigit(c) ) {
					// skip those characters
					
					if(count == mValues.length-1) {		// Last value with something appended
						mValues[count++] = parseDataInt(mValueData.substring(start, position));
					}
					
					start = position+1;
				}
				
				if(position == mValueData.length()-1 && count==mValues.length-1) {	// Last value with nothing appended
					mValues[count++] = parseDataInt(mValueData.substring(start, position));
				}
				
				
				position++;
			}
		}
		else {
			throw new TiledMapFormatException("Data encoding not supported");
		}
		
	}
	
	private int parseDataInt(String s) throws TiledMapFormatException {
		try {
			return Integer.parseInt(s);
		}
		catch(NumberFormatException nfe) {
			throw new TiledMapFormatException("Failed to parse integer: " +s);
		}
	}

	
	/* Because of ToAttributedValueConverter,
	 * writeReplace() will not be called automatically and must be called externally. 
	 * 
	 */
	public void publicPrepareMarshal() {
		prepareMarshal();
	}

	private void prepareMarshal() {
		
		//TiledMapMarshalTools.nullListIfEmpty(mElemListTile);
	}
	
	
	private int parseEncoding(String value) {
		if(value == null) {
			return DATA_ENCODING_NONE;
		}
		else if(XMLValuesTiledMap.ATTR_VALUE_ENCODING_BASE64.equals(value)) {
			return DATA_ENCODING_BASE64;
		}
		else if(XMLValuesTiledMap.ATTR_VALUE_ENCODING_CSV.equals(value)) {
			return DATA_ENCODING_CSV;
		}
		
		return -1;
	}
	
	private int parseCompression(String value) {
		if(value == null) {
			return DATA_COMPRESSION_NONE;
		}
		else if(XMLValuesTiledMap.ATTR_VALUE_COMPRESSION_GZIP.equals(value)) {
			return DATA_COMPRESSION_GZIP;
		}
		else if(XMLValuesTiledMap.ATTR_VALUE_COMPRESSION_ZLIB.equals(value)) {
			return DATA_COMPRESSION_ZLIB;
		}
		
		return -1;
	}
	
	
	

	@Override
	public int getValueForIndex(int index) {
		if(index >= mValues.length) {
			throw new IndexOutOfBoundsException();
		}
		
		return mValues[index]; 
	}


	@Override
	public int getValueCount() {
		return mValues.length;
	}
	
}
