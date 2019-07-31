package com.me_smart.minecraft;

import java.util.HashMap;
import java.util.Map;

import org.joml.Vector2i;
import org.joml.Vector3f;
import org.joml.Vector3i;
import org.lwjgl.glfw.GLFW;

public class Game implements AbstractGame {
	
	private int viewDistance = 16;
	private int maxWorldSize = 512;
	private Vector2i cameraPosition = new Vector2i();
	private Skybox skybox;
	private Entity cube;
	private Chunk chunk;

	@Override
	public void onStart(Engine engine) {
		skybox = new Skybox("majesty");
		cube = Entity.createPrimitive(Primitives.CUBE);
		chunk = new Chunk(new Vector3i(0, 0, 0));
	}

	@Override
	public void onUpdate(float deltaTime, Engine engine) {
		if(Input.getKeyDown(GLFW.GLFW_KEY_ESCAPE))
		{
			engine.setRunning(false);
		}
		chunk.update(deltaTime);
	}

	@Override
	public void onRender(Engine engine, Renderer renderer) {
		renderer.drawSkybox(skybox);
		renderer.drawEntity(cube);
		if(chunk.isReadyToDraw())
		{
			Debug.log("ready!");
			renderer.drawChunk(chunk);
		}
	}

}
