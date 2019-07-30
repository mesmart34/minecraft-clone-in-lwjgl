package com.me_smart.minecraft;

import org.lwjgl.glfw.GLFW;

public class Engine implements Runnable {
	
	private boolean running;
	private Thread gameLoop;
	private Renderer renderer;
	private AbstractGame game;
	
	public Engine()
	{
		gameLoop = new Thread(this, "GAME_LOOP_THREAD");
		gameLoop.start();
	}
	
	public void init()
	{
		GLFW.glfwInit();
		Window.createWindow(1270, 760, "Minecraft Clone", this);
		renderer = new Renderer();
		game = new Game();
		Input.createInput();
		GLFW.glfwMakeContextCurrent(Window.getNativeWindow());
		running = true;
	}
	
	public void handleInput()
	{
		Input.update();
		GLFW.glfwPollEvents();
	}
	
	@Override
	public void run() {
		init();
		game.onStart(this);
		renderer.setClearColor(0.1f, 0.5f, 0.8f, 1.0f);		
		double secsPerUpdate = 1.0d / 120.0d;
		double previous = GLFW.glfwGetTime();
		double steps = 0.0;
		while (running) {
		  double loopStartTime = GLFW.glfwGetTime();
		  double elapsed = loopStartTime - previous;
		  previous = loopStartTime;
		  steps += elapsed;
		  while (steps >= secsPerUpdate) {
			  handleInput();
			  game.onUpdate((float)elapsed, this);
		    steps -= secsPerUpdate;
		  }
		  renderer.clear();
		  game.onRender(this, renderer);
		  Window.SwapBuffers();
		  sync(loopStartTime);
		}
	}
	
	private void sync(double loopStartTime) {
		   float loopSlot = 1f / 60;
		   double endTime = loopStartTime + loopSlot; 
		   while(GLFW.glfwGetTime() < endTime) {
		       try {
		           Thread.sleep(1);
		       } catch (InterruptedException ie) {}
		   }
		}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public Renderer getRenderer() {
		return renderer;
	}
	
	public static void main(String[] args) {
		new Engine();
	}

}
