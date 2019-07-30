package com.me_smart.minecraft;

import org.joml.Vector3f;

public class Entity {
	
	private Mesh mesh;
	private Transform transform;
	private Texture texture;
	
	public Entity(Vector3f position, Vector3f scale, Vector3f rotation, Mesh mesh, Texture texture) {
		transform = new Transform(position, scale, rotation);
		this.texture = texture;
		this.mesh = mesh;
	}
	
	public static Entity createPrimitive(Primitives prim)
	{
		Mesh mesh = new Mesh();	
		switch (prim) {
		case CUBE:
			mesh.apply(new float[] {
					-1.0f, -1.0f,  1.0f,
				    1.0f, -1.0f,  1.0f,
				    -1.0f,  1.0f,  1.0f,
				    1.0f,  1.0f,  1.0f,
				    -1.0f, -1.0f, -1.0f,
				    1.0f, -1.0f, -1.0f,
				    -1.0f,  1.0f, -1.0f,
				    1.0f,  1.0f, -1.0f},
					new float[] {
						0, 0,
						1, 0,
						1, 1,
						0, 1,
						1, 0,
						1, 1,
						0, 0
			}, 
					new int[] {0, 1, 2, 3, 7, 1, 5, 4, 7, 6, 2, 4, 0, 1}, 
					
					Mesh.FULL_MODEL);
			break;
		default:
			break;
		}		
		mesh.setup();
		Texture texture = new Texture("src/com/me_smart/minecraft/textures/eGuAvD.jpg", Texture.DEFAULT);
		Entity entity = new Entity(new Vector3f(), new Vector3f(1, 1, 1), new Vector3f(), mesh, texture);
		return entity;
	}
	
	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	public Entity(Transform transform, Mesh mesh) {
		this.transform = transform;
		this.mesh = mesh;
	}

	public Mesh getMesh() {
		return mesh;
	}

	public void setMesh(Mesh mesh) {
		this.mesh = mesh;
	}

	public Transform getTransform() {
		return transform;
	}

	public void setTransform(Transform transform) {
		this.transform = transform;
	}
		
}
