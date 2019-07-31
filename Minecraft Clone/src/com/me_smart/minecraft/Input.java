package com.me_smart.minecraft;

import java.nio.DoubleBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallbackI;

public class Input {

	private static DoubleBuffer mouseBufferX, mouseBufferY;
	private static float deltaMouseX, deltaMouseY;
	private static float oldMouseX = 0, oldMouseY = 0, mouseX = 0, mouseY = 0;
	private static boolean keys[] = new boolean[512];
	private static boolean lastKeys[] = new boolean[512];
	private static Input input = null;
	
	private Input()
	{
		mouseBufferX = BufferUtils.createDoubleBuffer(1);
		mouseBufferY = BufferUtils.createDoubleBuffer(1);
		GLFW.glfwSetKeyCallback(Window.getNativeWindow(), new GLFWKeyCallbackI() {			
			@Override
			public void invoke(long window, int key, int scancode, int action, int mods) {
				lastKeys[key] = keys[key];
				if(action == GLFW.GLFW_PRESS)
				{
					keys[key] = true;
				}else
				if(action == GLFW.GLFW_RELEASE)
				{
					keys[key] = false;
				}
			}
		});
		GLFW.glfwSetCursorPos(Window.getNativeWindow(), (double)Window.getWidth() / 2, (double)Window.getHeight() / 2);
		GLFW.glfwSetInputMode(Window.getNativeWindow(), GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED);
		
	}
	
	public static void createInput()
	{
		input = new Input();
	}
	
	public static void update()
	{
		updateMouse();
	}
	
	public static boolean getKey(int keyCode)
	{
		return keys[keyCode];
	}
	
	public static boolean getKeyUp(int keyCode)
	{
		return !keys[keyCode] && lastKeys[keyCode];
	}
	
	public static boolean getKeyDown(int keyCode)
	{
		return keys[keyCode] && !lastKeys[keyCode];
	}
	
	private static void updateMouse()
	{	
		GLFW.glfwGetCursorPos(Window.getNativeWindow(), mouseBufferX, mouseBufferY);
		mouseX = (float)mouseBufferX.get(0);
		mouseY = (float)mouseBufferY.get(0);
		deltaMouseX = oldMouseX - mouseX;
		deltaMouseY = oldMouseY - mouseY;
		oldMouseX = mouseX;
		oldMouseY = mouseY;
	}

	public static float getDeltaMouseX() {
		return deltaMouseX;
	}
	
	public static float getDeltaMouseY() {
		return deltaMouseY;
	}
	
	public static float getMouseScreenX()
	{
		return getDeltaMouseX();	
	}
	
	public static float getMouseScreenY()
	{
		return getMouseScreenY();
	}
	
}