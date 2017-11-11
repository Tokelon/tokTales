package com.tokelon.toktales.desktop.input;

public class TInput {

	private TInput() {}



	/** The key or button was released. */
	public static final int KEY_RELEASE = 0x0;

	/** The key or button was pressed. */
	public static final int KEY_PRESS = 0x1;

	/** The key was held down until it repeated. */
	public static final int KEY_REPEAT = 0x2;

	/** The unknown key. */
	public static final int KEY_UNKNOWN = 0xFFFFFFFF;
	
	


	/** If this bit is set one or more Shift keys were held down. */
	public static final int INPUT_MOD_SHIFT = 0x1;

	/** If this bit is set one or more Control keys were held down. */
	public static final int INPUT_MOD_CONTROL = 0x2;

	/** If this bit is set one or more Alt keys were held down. */
	public static final int INPUT_MOD_ALT = 0x4;

	/** If this bit is set one or more Super keys were held down. */
	public static final int INPUT_MOD_SUPER = 0x8;
	
	

	/** Printable keys. */
	public static final int
	VK_SPACE         = 0x20,
	VK_APOSTROPHE    = 0x27,
	VK_COMMA         = 0x2C,
	VK_MINUS         = 0x2D,
	VK_PERIOD        = 0x2E,
	VK_SLASH         = 0x2F,
	VK_0             = 0x30,
	VK_1             = 0x31,
	VK_2             = 0x32,
	VK_3             = 0x33,
	VK_4             = 0x34,
	VK_5             = 0x35,
	VK_6             = 0x36,
	VK_7             = 0x37,
	VK_8             = 0x38,
	VK_9             = 0x39,
	VK_SEMICOLON     = 0x3B,
	VK_EQUAL         = 0x3D,
	VK_A             = 0x41,
	VK_B             = 0x42,
	VK_C             = 0x43,
	VK_D             = 0x44,
	VK_E             = 0x45,
	VK_F             = 0x46,
	VK_G             = 0x47,
	VK_H             = 0x48,
	VK_I             = 0x49,
	VK_J             = 0x4A,
	VK_K             = 0x4B,
	VK_L             = 0x4C,
	VK_M             = 0x4D,
	VK_N             = 0x4E,
	VK_O             = 0x4F,
	VK_P             = 0x50,
	VK_Q             = 0x51,
	VK_R             = 0x52,
	VK_S             = 0x53,
	VK_T             = 0x54,
	VK_U             = 0x55,
	VK_V             = 0x56,
	VK_W             = 0x57,
	VK_X             = 0x58,
	VK_Y             = 0x59,
	VK_Z             = 0x5A,
	VK_LEFT_BRACKET  = 0x5B,
	VK_BACKSLASH     = 0x5C,
	VK_RIGHT_BRACKET = 0x5D,
	VK_GRAVE_ACCENT  = 0x60,
	VK_WORLD_1       = 0xA1,
	VK_WORLD_2       = 0xA2;

	/** Function keys. */
	public static final int
	VK_ESCAPE        = 0x100,
	VK_ENTER         = 0x101,
	VK_TAB           = 0x102,
	VK_BACKSPACE     = 0x103,
	VK_INSERT        = 0x104,
	VK_DELETE        = 0x105,
	VK_RIGHT         = 0x106,
	VK_LEFT          = 0x107,
	VK_DOWN          = 0x108,
	VK_UP            = 0x109,
	VK_PAGE_UP       = 0x10A,
	VK_PAGE_DOWN     = 0x10B,
	VK_HOME          = 0x10C,
	VK_END           = 0x10D,
	VK_CAPS_LOCK     = 0x118,
	VK_SCROLL_LOCK   = 0x119,
	VK_NUM_LOCK      = 0x11A,
	VK_PRINT_SCREEN  = 0x11B,
	VK_PAUSE         = 0x11C,
	VK_F1            = 0x122,
	VK_F2            = 0x123,
	VK_F3            = 0x124,
	VK_F4            = 0x125,
	VK_F5            = 0x126,
	VK_F6            = 0x127,
	VK_F7            = 0x128,
	VK_F8            = 0x129,
	VK_F9            = 0x12A,
	VK_F10           = 0x12B,
	VK_F11           = 0x12C,
	VK_F12           = 0x12D,
	VK_F13           = 0x12E,
	VK_F14           = 0x12F,
	VK_F15           = 0x130,
	VK_F16           = 0x131,
	VK_F17           = 0x132,
	VK_F18           = 0x133,
	VK_F19           = 0x134,
	VK_F20           = 0x135,
	VK_F21           = 0x136,
	VK_F22           = 0x137,
	VK_F23           = 0x138,
	VK_F24           = 0x139,
	VK_F25           = 0x13A,
	VK_KP_0          = 0x140,
	VK_KP_1          = 0x141,
	VK_KP_2          = 0x142,
	VK_KP_3          = 0x143,
	VK_KP_4          = 0x144,
	VK_KP_5          = 0x145,
	VK_KP_6          = 0x146,
	VK_KP_7          = 0x147,
	VK_KP_8          = 0x148,
	VK_KP_9          = 0x149,
	VK_KP_DECIMAL    = 0x14A,
	VK_KP_DIVIDE     = 0x14B,
	VK_KP_MULTIPLY   = 0x14C,
	VK_KP_SUBTRACT   = 0x14D,
	VK_KP_ADD        = 0x14E,
	VK_KP_ENTER      = 0x14F,
	VK_KP_EQUAL      = 0x150,
	VK_LEFT_SHIFT    = 0x154,
	VK_LEFT_CONTROL  = 0x155,
	VK_LEFT_ALT      = 0x156,
	VK_LEFT_SUPER    = 0x157,
	VK_RIGHT_SHIFT   = 0x158,
	VK_RIGHT_CONTROL = 0x159,
	VK_RIGHT_ALT     = 0x15A,
	VK_RIGHT_SUPER   = 0x15B,
	VK_MENU          = 0x15C,
	VK_LAST          = VK_MENU;
	
	


	
	/** Mouse buttons. */
	public static final int
	VB_MOUSE_BUTTON_1      = 0x0,
	VB_MOUSE_BUTTON_2      = 0x1,
	VB_MOUSE_BUTTON_3      = 0x2,
	VB_MOUSE_BUTTON_4      = 0x3,
	VB_MOUSE_BUTTON_5      = 0x4,
	VB_MOUSE_BUTTON_6      = 0x5,
	VB_MOUSE_BUTTON_7      = 0x6,
	VB_MOUSE_BUTTON_8      = 0x7,
	VB_MOUSE_BUTTON_LAST   = VB_MOUSE_BUTTON_8,
	VB_MOUSE_BUTTON_LEFT   = VB_MOUSE_BUTTON_1,
	VB_MOUSE_BUTTON_RIGHT  = VB_MOUSE_BUTTON_2,
	VB_MOUSE_BUTTON_MIDDLE = VB_MOUSE_BUTTON_3;

	
	/** Joysticks. */
	public static final int
	VB_JOYSTICK_1    = 0x0,
	VB_JOYSTICK_2    = 0x1,
	VB_JOYSTICK_3    = 0x2,
	VB_JOYSTICK_4    = 0x3,
	VB_JOYSTICK_5    = 0x4,
	VB_JOYSTICK_6    = 0x5,
	VB_JOYSTICK_7    = 0x6,
	VB_JOYSTICK_8    = 0x7,
	VB_JOYSTICK_9    = 0x8,
	VB_JOYSTICK_10   = 0x9,
	VB_JOYSTICK_11   = 0xA,
	VB_JOYSTICK_12   = 0xB,
	VB_JOYSTICK_13   = 0xC,
	VB_JOYSTICK_14   = 0xD,
	VB_JOYSTICK_15   = 0xE,
	VB_JOYSTICK_16   = 0xF,
	VB_JOYSTICK_LAST = VB_JOYSTICK_16;




}
