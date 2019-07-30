package com.me_smart.minecraft;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector3i;

public class Mathf {

	
	public static float clamp(float target, float min, float max)
	{
		if(target > max) return max;
		if(target < min) return min;
		return target;
	}
	
	public static Matrix4f getProjectionMatrix(float fieldOfView, float nearPlane, float farPlane, float aspectRatio)
	{
		Matrix4f matrix = new Matrix4f();
		matrix.identity();
		matrix.perspective((float)Math.toRadians(fieldOfView), aspectRatio, nearPlane, farPlane);
		return matrix;
	}
	
	public static Matrix4f getViewMatrix(Camera camera)
	{
		Matrix4f matrix = new Matrix4f();
		matrix.identity();
		matrix.rotate((float)Math.toRadians(camera.getTransform().getRotation().x), new Vector3f(1, 0, 0));
		matrix.rotate((float)Math.toRadians(camera.getTransform().getRotation().y), new Vector3f(0, 1, 0));
		matrix.translate(camera.getTransform().getPosition());
		return matrix;
	}
	
	public static Matrix4f getTransformMatrix(Entity entity)
	{
		Matrix4f matrix = new Matrix4f();
		matrix.identity();
		matrix.translate(entity.getTransform().getPosition());
		matrix.rotateX((float)Math.toRadians(entity.getTransform().getRotation().x));
		matrix.rotateY((float)Math.toRadians(entity.getTransform().getRotation().y));
		matrix.rotateZ((float)Math.toRadians(entity.getTransform().getRotation().z));
		matrix.scale(entity.getTransform().getScale());
		return matrix;
	}
	
	public static Matrix4f getTransformMatrix(Vector3i pos)
	{
		Matrix4f matrix = new Matrix4f();
		matrix.identity();
		matrix.translate(new Vector3f(pos));
		return matrix;
	}
	
}
