package com.tokelon.toktales.desktop.lwjgl.input;

public class GLFWInput {

	
	/* For values like KEY_RELEASE */
	
	public static int stateInputToGlfw(int stateInput) {
		return stateInput;
	}
	
	public static int stateGlfwToInput(int stateGlfw) {
		return stateGlfw;
	}
	
	
	/* For values like INPUT_MOD_SHIFT */
	
	public static int modInputToGlfw(int modInput) {
		return modInput;
	}
	
	public static int modGlfwToInput(int modGlfw) {
		return modGlfw;
	}
	
	
	
	/* For values like VK_A */
	
	public static int keyVkToGlfw(int vkKey) {
		return vkKey;
	}
	
	public static int keyGlfwToVk(int glfwKey) {
		return glfwKey;
	}
	
	
}
