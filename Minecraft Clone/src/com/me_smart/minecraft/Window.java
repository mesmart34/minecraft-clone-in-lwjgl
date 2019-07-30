package com.me_smart.minecraft;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWWindowCloseCallback;
import org.lwjgl.glfw.GLFWWindowSizeCallbackI;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL30;

public class Window {
	
	private static long nativeWindow;
	private static int width, height;
	private static Window window = null;
	
	private Window(int width, int height, String title, Engine engine)
	{
		nativeWindow = GLFW.glfwCreateWindow(width, height, title, 0, 0);
		GLFW.glfwMakeContextCurrent(nativeWindow);
		GL.createCapabilities();
		resize(width, height);
		GLFW.glfwSetWindowSizeCallback(nativeWindow, new GLFWWindowSizeCallbackI() {			
			@Override
			public void invoke(long window, int w, int h) {
				resize(w, h);
			}
		});
		GLFW.glfwSetWindowCloseCallback(nativeWindow, new GLFWWindowCloseCallback() {			
			@Override
			public void invoke(long window) {
				engine.setRunning(false);
			}
		});
		GLFW.glfwSwapInterval(1);
	}
	
	public static void createWindow(int width, int height, String title, Engine engine)
	{
		if(window == null)
			window = new Window(width, height, title, engine);
	}
	
	public static void SwapBuffers()
	{
		GLFW.glfwSwapBuffers(nativeWindow);
	}
	
	public static int getWidth() {
		return width;
	}

	public static int getHeight() {
		return height;
	}

	public static void resize(int w, int h)
	{
		width = w;
		height = h;
		GL30.glViewport(0, 0, width, height);
	}
	
	public static float getAspectRatio()
	{
		return (float)width / height;
	}
	
	public static long getNativeWindow()
	{
		return nativeWindow;
	}
	
}
