package com.tokelon.toktales.core.content.graphics;

import com.tokelon.toktales.core.content.graphics.IRGBAColor.IMutableRGBAColor;

/** Creating format:<br>
 * [#](red)[red](green)[green](blue)[blue][alpha][alpha]
 * <br>
 * () = required<br>
 * [] = optional<br>
 * <br>
 *  Examples:
 * <br>
 * - #CC2300FF<br>
 * - #FFF<br>
 * - CCE<br>
 * - FF224C<br>
 * - BB2A<br>
 * 
 *
 */
public class RGBAColor implements IMutableRGBAColor {


	private float red;
	private float green;
	private float blue;
	private float alpha;
	
	public RGBAColor() {
		this.red = 1.0f;
		this.green = 1.0f;
		this.blue = 1.0f;
		this.alpha = 1.0f;
	}
	
	public RGBAColor(float red, float green, float blue, float alpha) {
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = alpha;
	}
	



	@Override
	public IMutableRGBAColor set(float red, float green, float blue, float alpha) {
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = alpha;
		return this;
	}

	@Override
	public IMutableRGBAColor setRed(float red) {
		this.red = red;
		return this;
	}

	@Override
	public IMutableRGBAColor setGreen(float green) {
		this.green = green;
		return this;
	}

	@Override
	public IMutableRGBAColor setBlue(float blue) {
		this.blue = blue;
		return this;
	}

	@Override
	public IMutableRGBAColor setAlpha(float alpha) {
		this.alpha = alpha;
		return this;
	}
	
	
	
	@Override
	public float getRed() {
		return red;
	}

	@Override
	public float getGreen() {
		return green;
	}

	@Override
	public float getBlue() {
		return blue;
	}

	@Override
	public float getAlpha() {
		return alpha;
	}

	
	/** If the given code contains an alpha value it will be applied, if not 1.0 will be set as default.
	 * 
	 * @param code The hex code with 3 (4) or 6 (8) component values (respective with alpha). Optional hash (#) as first character.
	 * @return
	 * @throws IllegalArgumentException If the given code could not be parsed.
	 */
	public static RGBAColor createFromCode(String code) throws IllegalArgumentException {
		return createFromCodeInternal(code, 1.0f, false);
	}
	
	/** If the given code contains an alpha value it will be applied, if not the given value will be set as default.
	 * 
	 * @param code The hex code with 3 (4) or 6 (8) component values (respective with alpha). Optional hash (#) as first character.
	 * @param defaultAlpha A value in range [0.0, 1.0].
	 * @return
	 * @throws IllegalArgumentException If the given code could not be parsed.
	 */
	public static RGBAColor createFromCode(String code, float defaultAlpha) throws IllegalArgumentException {
		return createFromCodeInternal(code, defaultAlpha, false);
	}

	/** If the given code contains an alpha value, it will be overridden by the given value.
	 * 
	 * @param code The hex code with 3 (4) or 6 (8) component values (respective with alpha). Optional hash (#) as first character.
	 * @param alpha A value in range [0.0, 1.0].
	 * @return
	 * @throws IllegalArgumentException If the given code could not be parsed.
	 */
	public static RGBAColor createFromCodeWithAlpha(String code, float alpha) throws IllegalArgumentException {
		return createFromCodeInternal(code, alpha, true);
	}
	
	
	private static RGBAColor createFromCodeInternal(String code, float alpha, boolean overrideGivenAlpha) {
		if(code == null) {
			throw new IllegalArgumentException("code must not be null");
		}
		if(code.length() == 0) {
			throw new IllegalArgumentException("code length must be > 0");
		}
		
		String codeValue = code;
		if(code.charAt(0) == '#') {
			codeValue = codeValue.substring(1);
		}
		
		String redVal, greenVal, blueVal, alphaVal = null;
		switch (codeValue.length()) {
		case 4:
			alphaVal = subIndex(codeValue, 3, 1);
			alphaVal += alphaVal;
			// no break
		case 3:
			redVal = subIndex(codeValue, 0, 1);
			redVal += redVal;
			greenVal = subIndex(codeValue, 1, 1);
			greenVal += greenVal;
			blueVal = subIndex(codeValue, 2, 1);
			blueVal += blueVal;
			break;
		case 8:
			alphaVal = subIndex(codeValue, 3, 2);
			// no break
		case 6:
			redVal = subIndex(codeValue, 0, 2);
			greenVal = subIndex(codeValue, 1, 2);
			blueVal = subIndex(codeValue, 2, 2);
			break;
		default:
			throw new IllegalArgumentException("Invalid number of components");
		}
		
		float colRed = convertIntToFloat(parseHex(redVal));
		float colGreen = convertIntToFloat(parseHex(greenVal));
		float colBlue = convertIntToFloat(parseHex(blueVal));
		float colAlpha = alphaVal == null ? alpha : convertIntToFloat(parseHex(alphaVal)); 
		
		if(overrideGivenAlpha) {
			colAlpha = alpha;
		}

		RGBAColor color = new RGBAColor(colRed, colGreen, colBlue, colAlpha);
		return color;
	}
	

	private static int tryParseHex(String hex) {
		try {
			return Integer.parseInt(hex, 16);
		}
		catch (NumberFormatException nfe) {
			return -1;
		}
	}
	
	private static int parseHex(String hex) {
		try {
			return Integer.parseInt(hex, 16);
		}
		catch (NumberFormatException nfe) {
			throw new IllegalArgumentException("not a valid integer value: " +hex);
		}
	}
	

	private static float convertIntToFloat(int value) {
		return value / 255.0f;
	}

	private static int convertFloatToInt(float value) {
		return Math.round(value * 255.0f);
	}
	
	
	private static String subIndex(String value, int index, int count) {
		return value.substring(index * count, index * count + count);
	}
	
}
