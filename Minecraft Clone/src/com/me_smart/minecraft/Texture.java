package com.me_smart.minecraft;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL30;
import org.lwjgl.stb.STBImage;

public class Texture {

	public static final int DEFAULT = 0;
	public static final int CUBEMAP = 1;
	private final int textureId;
	private int textureType;
	
	public Texture(String path, int textureType)
	{
		textureId = GL30.glGenTextures();
		load(path, textureType);
	}
	
	public void bind()
	{
		GL30.glActiveTexture(GL30.GL_TEXTURE0);
		if(textureType == DEFAULT)
		{
			GL30.glBindTexture(GL30.GL_TEXTURE_2D, textureId);
		}else {
			GL30.glBindTexture(GL30.GL_TEXTURE_CUBE_MAP, textureId);
		}
	}
	
	public void unbind()
	{
		if(textureType == DEFAULT)
		{
			GL30.glBindTexture(GL30.GL_TEXTURE_2D, 0);
		}else {
			GL30.glBindTexture(GL30.GL_TEXTURE_CUBE_MAP, 0);
		}
	}
	
	private void load(String path, int textureType)
	{
		this.textureType = textureType;
		switch (textureType) {
		case DEFAULT:
			IntBuffer w = BufferUtils.createIntBuffer(1);
			IntBuffer h = BufferUtils.createIntBuffer(1);
			IntBuffer c = BufferUtils.createIntBuffer(1);	
			ByteBuffer image = STBImage.stbi_load(path, w, h, c, 4);
			GL30.glBindTexture(GL30.GL_TEXTURE_2D, textureId);	
			GL30.glTexParameteri(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_WRAP_S, GL30.GL_CLAMP_TO_EDGE);
			GL30.glTexParameteri(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_WRAP_T, GL30.GL_CLAMP);
			GL30.glTexParameterf(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_MIN_FILTER, GL30.GL_NEAREST_MIPMAP_LINEAR);
			GL30.glTexParameterf(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_MAG_FILTER, GL30.GL_NEAREST);
			GL30.glTexImage2D(GL30.GL_TEXTURE_2D, 0, GL30.GL_RGBA, w.get(), h.get(), 0, GL30.GL_RGBA, GL30.GL_UNSIGNED_BYTE, image);
			GL30.glGenerateMipmap(GL30.GL_TEXTURE_2D);
			break;
		case CUBEMAP:
			GL30.glBindTexture(GL30.GL_TEXTURE_2D, textureId);
			ByteBuffer data;
			for(int i = 0; i < 6; i++)
			{
				IntBuffer _w = BufferUtils.createIntBuffer(1);
				IntBuffer _h = BufferUtils.createIntBuffer(1);
				IntBuffer _c = BufferUtils.createIntBuffer(1);
				String partPath = path + "_" + String.valueOf(i) + ".png";
				data = STBImage.stbi_load(partPath, _w, _h, _c, 4);
				GL30.glTexImage2D(GL30.GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 0, GL30.GL_RGBA, _w.get(), _h.get(), 0, GL30.GL_RGBA, GL30.GL_UNSIGNED_BYTE, data);
			}
			GL30.glTexParameteri(GL30.GL_TEXTURE_CUBE_MAP, GL30.GL_TEXTURE_MIN_FILTER, GL30.GL_LINEAR);
			GL30.glTexParameteri(GL30.GL_TEXTURE_CUBE_MAP, GL30.GL_TEXTURE_MAG_FILTER, GL30.GL_LINEAR);
		    GL30.glTexParameteri(GL30.GL_TEXTURE_CUBE_MAP, GL30.GL_TEXTURE_WRAP_S, GL30.GL_CLAMP_TO_EDGE);
		    GL30.glTexParameteri(GL30.GL_TEXTURE_CUBE_MAP, GL30.GL_TEXTURE_WRAP_T, GL30.GL_CLAMP_TO_EDGE);
		    GL30.glTexParameteri(GL30.GL_TEXTURE_CUBE_MAP, GL30.GL_TEXTURE_WRAP_R, GL30.GL_CLAMP_TO_EDGE);
			break;
		default:
			break;
		}
		
	}
	
}
