package com.me_smart.minecraft;

import org.joml.Vector3f;

public class Transform {

	private Vector3f position;
	private Vector3f scale;
	private Vector3f rotation;
	
	public Transform()
	{
		this.position = new Vector3f();
		this.scale = new Vector3f();
		this.rotation = new Vector3f();
	}
	
	public Transform(Vector3f position, Vector3f scale, Vector3f rotation) {
		this.position = position;
		this.scale = scale;
		this.rotation = rotation;
	}
	
	public void move(Vector3f v)
	{
		position.x += v.x;
		position.y += v.y;
		position.z += v.z;
	}
	
	public void rotate(Vector3f v)
	{
		rotation = rotation.add(v);
	}
	
	public Vector3f getPosition() {
		return position;
	}
	
	public void setPosition(Vector3f position) {
		this.position = position;
	}
	
	public Vector3f getScale() {
		return scale;
	}
	
	public void setScale(Vector3f scale) {
		this.scale = scale;
	}
	
	public Vector3f getRotation() {
		return rotation;
	}
	
	public void setRotation(Vector3f rotation) {
		this.rotation = rotation;
	}
	
}
