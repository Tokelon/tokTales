package com.tokelon.toktales.core.content.graphics;

import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;

import com.tokelon.toktales.tools.core.dispose.IDisposable;

/** Contains pixel data and meta information of an image.
 *
 */
public interface IBitmap extends IDisposable {
	// Maybe rename to IImageBitmap to avoid confusion with Android Bitmap


	public static final int FORMAT_UNKNOWN = 1;
	public static final int FORMAT_OTHER = 2;
	
	public static final int FORMAT_ALPHA_8 = 5;
	public static final int FORMAT_GREY_ALPHA_88 = 6;
	public static final int FORMAT_RGB_888 = 7;
	public static final int FORMAT_RGBA_8888 = 8;
	
	
	/**
	 * @return The width of this bitmap.
	 */
	public int getWidth();
	
	/**
	 * @return The height of this bitmap.
	 */
	public int getHeight();
	
	
	/** Returns the color format of this bitmap.
	 * <p>
	 * This can be one of the included formats (ex. {@link #FORMAT_RGBA_8888}),
	 * or a completely different one.
	 * 
	 * @return A flag indicating the format.
	 */
	public int getFormat();
	
	
	/** Returns the bitmap data.
	 * <p>
	 * Note that while some implementations may return the backing buffer directly,
	 * others may allocate a new one for each bitmap, or even for each call.
	 * Therefore prefer using {@link #getData(ByteBuffer)}.
	 * 
	 * @return A buffer containing the bitmap data.
	 * @see #getData(ByteBuffer)
	 */
	public ByteBuffer getData();
	
	/** Copies the bitmap data into the given buffer.
	 * <p>
	 * The buffer's position will be incremented by the number of bytes written.
	 * 
	 * @param buffer
	 * @throws BufferOverflowException If for buffer {@link ByteBuffer#remaining()} < {@link #getByteCount()}.
	 * @see #getByteCount()
	 */
	public void getData(ByteBuffer buffer);
	
	
	/** Returns the number of bytes this bitmap's data uses.
	 * <p>
	 * This is also the minimum capacity a buffer needs to contain the data.
	 * 
	 * @return The data byte count of this bitmap.
	 */
	public int getByteCount();
	
	
	//public int getDataOffset();
	
	// Implement copying?
	//public default IBitmap copy()
	//public default IBitmap copyDeep()
	
}
