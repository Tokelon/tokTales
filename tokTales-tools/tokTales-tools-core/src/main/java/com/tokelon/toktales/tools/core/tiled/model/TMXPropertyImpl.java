package com.tokelon.toktales.tools.core.tiled.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import com.thoughtworks.xstream.converters.extended.ToAttributedValueConverter;
import com.tokelon.toktales.tools.core.tiled.XMLValuesTiledMap;

@XStreamConverter(value=ToAttributedValueConverter.class, strings={"mElementValue"})
public class TMXPropertyImpl implements ITMXProperty {

	
	@XStreamAsAttribute
	@XStreamAlias(XMLValuesTiledMap.ATTR_NAME_MAP_MULT_PROPERTIES_PROPERTY_NAME)
	private String mAttrName;
	
	@XStreamAsAttribute
	@XStreamAlias(XMLValuesTiledMap.ATTR_NAME_MAP_MULT_PROPERTIES_PROPERTY_VALUE)
	private String mAttrValue;

	
	/* Not used right now
	 * Might be added in later versions
	 * 
	 */
	@XStreamOmitField private String mElementValue;
	
	

	public TMXPropertyImpl() {
		// Default Ctor
	}
	
	
	public TMXPropertyImpl(String name, String value) {
		this.mAttrName = name;
		this.mAttrValue = value;
	}

	
	
	public void setElementValue(String elementValue) {
		this.mElementValue = elementValue;
	}
	
	public void setName(String name) {
		this.mAttrName = name;
	}
	
	public void setValue(String value) {
		this.mAttrValue = value;
	}
	
	
	@Override
	public String getName() {
		return mAttrName;
	}

	@Override
	public String getValue() {
		return mAttrValue;
	}
	
}
