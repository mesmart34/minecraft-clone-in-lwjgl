package com.me_smart.minecraft;

import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

public class Camera {

	private Transform transform;
	private float fieldOfView;
	private float nearPlane;
	private float farPlane;
	
	public Camera(Transform transform, float fieldOfView, float nearPlane, float farPlane) {
		this.transform = transform;
		this.fieldOfView = fieldOfView;
		this.nearPlane = nearPlane;
		this.farPlane = farPlane;
	}
	
	public void update(float deltaTime)
	{
		float moveSpeed = 0.1f;
		Vector3f new_rotation = new Vector3f(transform.getRotation());
		new_rotation = new_rotation.add(new Vector3f(-Input.getDeltaMouseY() * 0.02f, -Input.getDeltaMouseX() * 0.02f, 0));
		new_rotation.x = Mathf.clamp(new_rotation.x, -90, 90);
		transform.setRotation(new_rotation);
		float climbing = 1.0f;
		float x = transform.getRotation().x;
		float y = transform.getRotation().y;
		Vector3f new_position = new Vector3f(transform.getPosition());
		if(Input.getKey(GLFW.GLFW_KEY_W)) {
			new_position.y += moveSpeed * Math.sin(Math.toRadians(x));
			new_position.x -= moveSpeed * (float) Math.cos(Math.toRadians(90-y));
			new_position.z += moveSpeed * (float) Math.sin(Math.toRadians(90-y));
		}else if(Input.getKey(GLFW.GLFW_KEY_S)) {
			new_position.y -= moveSpeed * Math.sin(Math.toRadians(x));
			new_position.x += moveSpeed * (float) Math.cos(Math.toRadians(90-y));
			new_position.z -= moveSpeed * (float) Math.sin(Math.toRadians(90-y));
		}
		if(Input.getKey(GLFW.GLFW_KEY_A)) {
			new_position.x -= moveSpeed * Math.cos(Math.toRadians(180-y));
			new_position.z += moveSpeed * Math.sin(Math.toRadians(180-y));
		}else if(Input.getKey(GLFW.GLFW_KEY_D)) {
			new_position.x += moveSpeed * Math.cos(Math.toRadians(180-y));
			new_position.z -= moveSpeed * Math.sin(Math.toRadians(180-y));
		}
		if(Input.getKey(GLFW.GLFW_KEY_Q)) {
			new_position.y -= moveSpeed * climbing;
		}else if(Input.getKey(GLFW.GLFW_KEY_E)) {
			new_position.y += moveSpeed * climbing;
		}
		transform.setPosition(new_position);
	}
	
	public Transform getTransform() {
		return transform;
	}
	
	public void setTransform(Transform transform) {
		this.transform = transform;
	}
	
	public float getFieldOfView() {
		return fieldOfView;
	}
	
	public void setFieldOfView(float fieldOfView) {
		this.fieldOfView = fieldOfView;
	}
	
	public float getNearPlane() {
		return nearPlane;
	}
	
	public void setNearPlane(float nearPlane) {
		this.nearPlane = nearPlane;
	}
	
	public float getFarPlane() {
		return farPlane;
	}
	
	public void setFarPlane(float farPlane) {
		this.farPlane = farPlane;
	}
	
	
	
}
