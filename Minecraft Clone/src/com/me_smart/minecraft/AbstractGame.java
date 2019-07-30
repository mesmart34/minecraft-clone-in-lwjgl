package com.me_smart.minecraft;

public interface AbstractGame {

	public void onStart(Engine engine);
	
	public void onUpdate(float deltaTime, Engine engine);
	
	public void onRender(Engine engine, Renderer renderer);
	
}
