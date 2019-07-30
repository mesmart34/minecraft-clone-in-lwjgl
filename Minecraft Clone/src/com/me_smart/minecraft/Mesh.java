package com.me_smart.minecraft;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL30;

public class Mesh {
	
	private int vao;
	private int ebo;
	private ArrayList<Integer> vbos;
	private int vertexCount;
	private float vertices[], uvs[];
	private int indices[];
	private int meshType;
	public static final int FULL_MODEL = 0;
	public static final int CUBEMAP = 1;
	public static final int VERTICES = 2;
	
	public Mesh()
	{
		vbos = new ArrayList<Integer>();
	}
	
	public void apply(float vertices[], float uvs[], int indices[], int type)
	{
		this.meshType = type;
		this.vertices = vertices;
		this.uvs = uvs;
		this.indices = indices;
	}
	
	public void apply(Vector3f vertices[], Vector2f uvs[], int indices[], int type)
	{
		this.vertices = Vector3fToArray(vertices);
		this.uvs = Vector2fToArray(uvs);
		this.indices = indices;
		this.meshType = type;
	}
	
	public void apply(float vertices[], int type)
	{
		this.meshType = type;
		this.vertices = vertices;
	}
	
	public void setup()
	{
		if(meshType == FULL_MODEL)
		{
			vao = GL30.glGenVertexArrays();
			ebo = GL30.glGenBuffers();
			GL30.glBindVertexArray(vao);
			storeDataInVBO(0, 3, vertices);
			storeDataInVBO(1, 2, uvs);
			storeIndices(indices);
			GL30.glBindVertexArray(0);
		}else if(meshType == CUBEMAP) {
			vao = GL30.glGenVertexArrays();
			GL30.glBindVertexArray(vao);
			storeDataInVBO(0, 3, vertices);
			GL30.glBindVertexArray(0);
		}
	}
	
	private float[] Vector2fToArray(Vector2f[] v)
	{
		float[] array = new float[v.length * 2];
		int index = 0;
		for (Vector2f vector : v) {
			if(vector == null) continue;
			array[index + 0] = vector.x;
			array[index + 1] = vector.y;
			index += 2;
		}
		return array;
	}
	
	private float[] Vector3fToArray(Vector3f[] v)
	{
		float[] array = new float[v.length * 3];
		int index = 0;
		for (Vector3f vector : v) {
			if(vector == null) continue;
			array[index + 0] = vector.x;
			array[index + 1] = vector.y;
			array[index + 2] = vector.z;
			index += 3;
		}
		return array;
	}
	
	public void bind()
	{
		GL30.glBindVertexArray(vao);
		for(int i = 0; i < vbos.size(); i++)
		{
			GL30.glEnableVertexAttribArray(i);
		}
	}
	
	public void unbind()
	{
		for(int i = 0; i < vbos.size(); i++)
		{
			GL30.glDisableVertexAttribArray(i);
		}
		GL30.glBindVertexArray(0);
	}
	
	public void clear()
	{
		GL30.glBindVertexArray(vao);
		GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, 0);
		for(int i = 0; i < vbos.size(); i++)
		{
			GL30.glDisableVertexAttribArray(i);
			GL30.glDeleteBuffers(i);
		}
		GL30.glBindBuffer(GL30.GL_ELEMENT_ARRAY_BUFFER, 0);
		GL30.glDeleteBuffers(ebo);
		GL30.glBindVertexArray(0);
		GL30.glDeleteVertexArrays(vao);
	}
	
	public void storeDataInVBO(int location, int dimension, float data[])
	{
		int vbo = GL30.glGenBuffers();
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, vbo);
		GL30.glBufferData(GL30.GL_ARRAY_BUFFER, buffer, GL30.GL_STATIC_DRAW);	
		GL30.glVertexAttribPointer(location, dimension, GL30.GL_FLOAT, false, 0, 0);
		vbos.add(vbo);
	}
	
	public void storeIndices(int indices[])
	{
		IntBuffer buffer = BufferUtils.createIntBuffer(indices.length);
		buffer.put(indices);
		buffer.flip();
		GL30.glBindBuffer(GL30.GL_ELEMENT_ARRAY_BUFFER, ebo);
		GL30.glBufferData(GL30.GL_ELEMENT_ARRAY_BUFFER, buffer, GL30.GL_STATIC_DRAW);
		vertexCount = indices.length;
	}

	public int getVertexCount() {
		return vertexCount;
	}
	
}
