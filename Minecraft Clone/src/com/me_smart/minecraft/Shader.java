package com.me_smart.minecraft;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL30;

public class Shader {

	private final int programId;
	public final static String VERTEX_SHADER_PATH = "src/com/me_smart/minecraft/shaders/vertexShader.glsl";
	public final static String FRAGMENT_SHADER_PATH = "src/com/me_smart/minecraft/shaders/fragmentShader.glsl";
	
	public Shader(String vertex, String fragment)
	{
		programId = GL30.glCreateProgram();
		setupProgram(vertex, fragment);
	}
	
	public void start()
	{
		GL30.glUseProgram(programId);
	}
	
	public void stop()
	{
		GL30.glUseProgram(0);
	}
	
	public void loadInt(String name, int value)
	{
		int location = GL30.glGetUniformLocation(programId, name);
		GL30.glUniform1ui(location, value);
	}
	
	public void loadVector3f(String name, Vector3f value)
	{
		int location = GL30.glGetUniformLocation(programId, name);
		GL30.glUniform3f(location, value.x, value.y, value.z);
	}
	
	/*public void loadVector3f(String name, Vector3f value)
	{
		int location = GL30.glGetUniformLocation(programId, name);
		FloatBuffer buffer = BufferUtils.createFloatBuffer(3);
		value.set(buffer);
		GL30.glUniform3fv(location, buffer);
	}*/
	
	public void loadFloat(String name, float value)
	{
		int location = GL30.glGetUniformLocation(programId, name);
		GL30.glUniform1f(location, value);
	}
	
	public void loadMatrix(String name, Matrix4f matrix)
	{
		int location = GL30.glGetUniformLocation(programId, name);
		FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
		matrix.get(buffer);
		GL30.glUniformMatrix4fv(location, false, buffer);
	}
	
	private void setupProgram(String vertex, String fragment)
	{
		int vertex_shader = loadShader(GL30.GL_VERTEX_SHADER, vertex);
		int frament_shader = loadShader(GL30.GL_FRAGMENT_SHADER, fragment);
		GL30.glAttachShader(programId, vertex_shader);
		GL30.glAttachShader(programId, frament_shader);
		GL30.glLinkProgram(programId);
		if (GL30.glGetProgrami(programId, GL30.GL_LINK_STATUS) == GL30.GL_FALSE) 
		{
			throw new RuntimeException("could not link shader. Reason: " + GL30.glGetProgramInfoLog(programId, 1000));
		}
		GL30.glValidateProgram(programId);
		if (GL30.glGetProgrami(programId, GL30.GL_VALIDATE_STATUS) == GL30.GL_FALSE)
		{
			throw new RuntimeException("could not validate shader. Reason: " + GL30.glGetProgramInfoLog(programId, 1000));            
		}
		GL30.glDeleteShader(vertex_shader);
		GL30.glDeleteShader(frament_shader);
	}
	
	private int loadShader(int type, String path)
	{
		StringBuilder builder = new StringBuilder();
		FileInputStream in = null;
		try {
			in = new FileInputStream(path);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		String line = null;
		BufferedReader bufferReader = null;
		try {
			bufferReader = new BufferedReader(new InputStreamReader(in,"UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		try {
			while((line = bufferReader.readLine()) != null)
			{
				builder.append(line).append("\n");
			}
			bufferReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int shader = GL30.glCreateShader(type);
		GL30.glShaderSource(shader, builder.toString());
		GL30.glCompileShader(shader);
		if (GL30.glGetShaderi(shader, GL30.GL_COMPILE_STATUS) == GL30.GL_FALSE) 
		{
			throw new RuntimeException("could not compile " + path + " shader. Reason: " + GL30.glGetProgramInfoLog(shader, 1000));
		}
		return shader;
	}
	
}
