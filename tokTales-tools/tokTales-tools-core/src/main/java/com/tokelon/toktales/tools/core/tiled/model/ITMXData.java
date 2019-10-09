package com.tokelon.toktales.tools.core.tiled.model;

public interface ITMXData {

	
	public static final int DATA_ENCODING_NONE = 1;
	public static final int DATA_ENCODING_BASE64 = 2;
	public static final int DATA_ENCODING_CSV = 3;
	
	public static final int DATA_COMPRESSION_NONE = 1;
	public static final int DATA_COMPRESSION_GZIP = 2;
	public static final int DATA_COMPRESSION_ZLIB = 3;
	
	
	public int getValueForIndex(int index);

	public int getValueCount();
}
