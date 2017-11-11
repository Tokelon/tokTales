package com.tokelon.toktales.android.input;

public final class TokelonTypeAInputs {	// TODO: Rename to something else | TAInput ?

	private TokelonTypeAInputs() {}

	
	public static final int BUTTON_RELEASE = 0x0;
	public static final int BUTTON_PRESS = 0x1;
	public static final int BUTTON_REPEAT = 0x2;
	public static final int BUTTON_UNKNOWN = 0xFFFFFFFF;

	public static final int TOUCH_EVENT_DOWN = 101;
	public static final int TOUCH_EVENT_MOVE = 102;
	public static final int TOUCH_EVENT_POINTER_DOWN = 103;
	public static final int TOUCH_EVENT_UP = 104;
	public static final int TOUCH_EVENT_POINTER_UP = 105;
	public static final int TOUCH_EVENT_OUTSIDE = 106;
	public static final int TOUCH_EVENT_CANCEL = 107;
	
	
	public static final int VB_A = 11;
	public static final int VB_B = 12;
	public static final int VB_UP = 13;
	public static final int VB_DOWN = 14;
	public static final int VB_LEFT = 15;
	public static final int VB_RIGHT = 16;
	
	public static final int VB_SP1 = 21;
	public static final int VB_SET = 22;
	
	
	public static String inputToString(int input) {
		String result;
		switch (input) {
		case VB_A:
			result = "VB_A";
			break;
		case VB_B:
			result = "VB_B";
			break;
		case VB_UP:
			result = "VB_UP";
			break;
		case VB_DOWN:
			result = "VB_DOWN";
			break;
		case VB_LEFT:
			result = "VB_LEFT";
			break;
		case VB_RIGHT:
			result = "VB_RIGHT";
			break;
		case VB_SP1:
			result = "VB_SP1";
			break;
		case VB_SET:
			result = "VB_SET";
			break;
		default:
			result = null;
			break;
		}
		
		return result;
	}
	
}
