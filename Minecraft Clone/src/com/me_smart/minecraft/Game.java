package com.me_smart.minecraft;

import java.util.HashMap;
import java.util.Map;

import org.joml.Vector2i;
import org.joml.Vector3f;
import org.joml.Vector3i;
import org.lwjgl.glfw.GLFW;

public class Game implements AbstractGame {
	
	private int viewDistance = 16;
	private Vector2i cameraPosition = new Vector2i();
	private Skybox skybox;
	private Entity cube;
	private boolean readyToMesh = false;
	private DirectionalLight light;

	@Override
	public void onStart(Engine engine) {
		Noise.seed = 124215;
		light = new DirectionalLight(new Vector3f(0.4f, 0.2f, 0.7f), new Transform(new Vector3f(10, 10, 10), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1)), 1.0f);
		skybox = new Skybox("majesty");
		cube = Entity.createPrimitive(Primitives.CUBE);
		for(int x = 0; x < Chunk.maxWorldSize; x++)
		{
			for(int z = 0; z < Chunk.maxWorldSize; z++)
			{
				Chunk chunk = new Chunk(new Vector3i(x * Chunk.SIZE, 0, z * Chunk.SIZE));
				Chunk.world.put(chunk.getPosition(), chunk);
			}
		}
		readyToMesh = true;
	}

	@Override
	public void onUpdate(float deltaTime, Engine engine) {
		if(Input.getKeyDown(GLFW.GLFW_KEY_ESCAPE))
		{
			engine.setRunning(false);
		}
		if(readyToMesh)
		{
			for(int x = 0; x < Chunk.maxWorldSize; x++)
			{
				for(int z = 0; z < Chunk.maxWorldSize; z++)
				{
					Chunk chunk = Chunk.world.get(new Vector3i(x * Chunk.SIZE, 0, z * Chunk.SIZE));
					chunk.update(deltaTime);
				}
			}
		}
	}

	@Override
	public void onRender(Engine engine, Renderer renderer) {
		renderer.drawSkybox(skybox);
		renderer.drawEntity(cube);
		Chunk.world.forEach((v, c) -> {
			if(c.isReadyToDraw())
			{
				renderer.drawChunk(c, light);
			}
		});
		
	}

}
