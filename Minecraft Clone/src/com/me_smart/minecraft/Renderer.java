package com.me_smart.minecraft;

import java.nio.IntBuffer;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector3i;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL30;

public class Renderer {

	private final Shader defaultShader;
	private final Texture texture;
	private Camera camera;
	
	public Renderer() {
		camera = new Camera(new Transform(), 90.0f, 0.1f, 1000.0f);
		defaultShader = new Shader(Shader.VERTEX_SHADER_PATH, Shader.FRAGMENT_SHADER_PATH);
		texture = new Texture("src/com/me_smart/minecraft/textures/terrain_4392360.jpg", Texture.DEFAULT);
		
	}
	
	public void drawEntity(Entity entity)
	{
		defaultShader.start();
		defaultShader.loadMatrix("transform_matrix", Mathf.getTransformMatrix(entity));
		defaultShader.loadMatrix("view_matrix", Mathf.getViewMatrix(camera));
		defaultShader.loadMatrix("projection_matrix", Mathf.getProjectionMatrix(camera.getFieldOfView(), camera.getNearPlane(), camera.getFarPlane(), Window.getAspectRatio()));
		entity.getTexture().bind();
		drawMesh(entity.getMesh());
		entity.getTexture().unbind();
		defaultShader.stop();
	}
	
	public void drawSkybox(Skybox skybox)
	{
		skybox.getCubemapShader().start();
		GL30.glDisable(GL30.GL_DEPTH_TEST);
		skybox.getCubemap().bind();
		skybox.getMesh().bind();
		Matrix4f view = Mathf.getViewMatrix(camera);
		view._m30(0);
		view._m31(0);
		view._m32(0);
		skybox.getCubemapShader().loadMatrix("view_matrix", view);
		skybox.getCubemapShader().loadMatrix("projection_matrix", Mathf.getProjectionMatrix(camera.getFieldOfView(), camera.getNearPlane(), camera.getFarPlane(), Window.getAspectRatio()));
		GL30.glDrawArrays(GL30.GL_TRIANGLES, 0, 36);
		skybox.getMesh().unbind();
		skybox.getCubemap().unbind();
		GL30.glEnable(GL30.GL_DEPTH_TEST);
		skybox.getCubemapShader().stop();
	}
	
	public void drawChunk(Chunk chunk)
	{
		defaultShader.start();
		Vector3i v = new Vector3i(chunk.getPosition());
		v.x *= Chunk.SIZE - 1;
		v.z *= Chunk.SIZE - 1;
		defaultShader.loadMatrix("transform_matrix", Mathf.getTransformMatrix(v));
		defaultShader.loadMatrix("view_matrix", Mathf.getViewMatrix(camera));
		defaultShader.loadMatrix("projection_matrix", Mathf.getProjectionMatrix(camera.getFieldOfView(), camera.getNearPlane(), camera.getFarPlane(), Window.getAspectRatio()));
		texture.bind();
		drawMesh(chunk.getMesh());
		texture.unbind();
		defaultShader.stop();
	}
	
	public void drawMesh(Mesh mesh)
	{	
		mesh.bind();		
		GL30.glDrawElements(GL30.GL_TRIANGLES, mesh.getVertexCount(), GL30.GL_UNSIGNED_INT, 0);
		mesh.unbind();		
	}
	
	public Camera getCamera() {
		return camera;
	}

	public void clear()
	{
		GL30.glEnable(GL30.GL_DEPTH_TEST);
		//GL30.glEnable(GL30.GL_CULL_FACE);
		GL30.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT);
	}
	
	public void setClearColor(float r, float g, float b, float a)
	{
		GL30.glClearColor(r, g, b, a);
	}
	
}
