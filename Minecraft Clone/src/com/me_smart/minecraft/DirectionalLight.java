package com.me_smart.minecraft;

import org.joml.Vector3f;

public class DirectionalLight {

	private Vector3f color;
	private Transform transform;
	public float intensity;
	
	public DirectionalLight(Vector3f color, Transform transform, float intensity) {
		super();
		this.color = color;
		this.transform = transform;
		this.intensity = intensity;
	}

	public Vector3f getColor() {
		return color;
	}

	public void setColor(Vector3f color) {
		this.color = color;
	}

	public Transform getTransform() {
		return transform;
	}

	public void setTransform(Transform transform) {
		this.transform = transform;
	}

	public float getIntensity() {
		return intensity;
	}

	public void setIntensity(float intensity) {
		this.intensity = intensity;
	}
	
	
	
}
