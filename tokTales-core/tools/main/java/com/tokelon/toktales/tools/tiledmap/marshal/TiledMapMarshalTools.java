package com.tokelon.toktales.tools.tiledmap.marshal;

import java.util.List;

import com.tokelon.toktales.tools.tiledmap.TiledMapFormatException;
import com.tokelon.toktales.tools.tiledmap.XMLValuesTiledMap;
import com.tokelon.toktales.tools.tiledmap.model.ITiledMapProperties;

public final class TiledMapMarshalTools {

	private TiledMapMarshalTools() {}
	
	
	
	
	
	public static int parseIntAttrNonNegative(String attrValue, String attrDescription) throws TiledMapFormatException {
		int res = parseIntAttr(attrValue, attrDescription);
		if(res < 0) {
			throw new TiledMapFormatException("Invalid " +attrDescription +" value: " +attrValue +" (< 0)");
		}
		
		return res;
	}
	
	public static int parseIntAttrPositive(String attrValue, String attrDescription) throws TiledMapFormatException {
		int res = parseIntAttr(attrValue, attrDescription);
		if(res <= 0) {
			throw new TiledMapFormatException("Invalid " +attrDescription +" value: " +attrValue +" (<= 0)");
		}
		
		return res;
	}
	
	public static int parseIntAttr(String attrValue, String attrDescription) throws TiledMapFormatException {
		if(attrValue == null) {
			throw new TiledMapFormatException("Missing attribute: " +attrDescription);
		}
		
		int attr;
		try {
			attr = Integer.parseInt(attrValue);
		}
		catch(NumberFormatException nfe) {
			throw new TiledMapFormatException("Invalid " +attrDescription +" value: " +attrValue);
		}
		
		return attr;
	}
	
	

	
	public static boolean parseVisibleAttr(String attrValue, String attrDescription) throws TiledMapFormatException {
		if(XMLValuesTiledMap.ATTR_VALUE_VISIBLE_SHOWN.equals(attrValue)) {
			return true;
		}
		else if(XMLValuesTiledMap.ATTR_VALUE_VISIBLE_HIDDEN.equals(attrValue)) {
			return false;
		}
		else {
			throw new TiledMapFormatException("Illegal " +attrDescription +" value: " +attrValue);
		}
		
	}
	
	
	public static float parseOpacityAttr(String attrValue, String attrDescription) throws TiledMapFormatException {
		
		float res;
		try {
			res = Float.parseFloat(attrValue);
			
			if(res < 0) {
				throw new TiledMapFormatException("Illegal " +attrDescription +" value: " +attrValue);
			}
		}
		catch(NumberFormatException nfe) {
			throw new TiledMapFormatException("Illegal " +attrDescription +" value: " +attrValue);
		}
		
		return res;
	}
	
	
	public static float[][] parsePointsAttr(String attrValue, String attrDescription) throws TiledMapFormatException {
		String[] points = attrValue.split(" ");
		
		float[] pointsX = new float[points.length];
		float[] pointsY = new float[points.length];
		for(int i = 0; i < points.length; i++) {
			String point = points[i];
			try {
				int commaIdx = point.indexOf(',');
				if(commaIdx < 0) {
					throw new TiledMapFormatException("Illegal " +attrDescription +" value: " +attrValue);
				}
				
				float px = Float.parseFloat(point.substring(0, commaIdx));
				float py = Float.parseFloat(point.substring(commaIdx + 1));
				pointsX[i] = px;
				pointsY[i] = py;
			}
			catch(NumberFormatException nfe) {
				throw new TiledMapFormatException("Illegal " +attrDescription +" value: " +attrValue);
			}
		}

		return new float[][] { pointsX, pointsY };
	}
	
	
	
	public static void nullListIfEmpty(List<?> list) {
		if(list.isEmpty()) {
			list = null;
		}
	}
	
	public static void nullPropertiesIfEmpty(ITiledMapProperties properties) {
		if(properties.getPropertyCount() == 0) {
			properties = null;
		}
	}
	
	
}
