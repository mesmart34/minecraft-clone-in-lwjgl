package com.me_smart.minecraft;

public class Skybox {

	private Texture cubemap;
	private Shader cubemapShader;
	private Mesh mesh;
	private final static float SIZE = 500;
	private final static float[] skyboxVertices = new float[]{
			-SIZE,  SIZE, -SIZE,
	        -SIZE, -SIZE, -SIZE,
	         SIZE, -SIZE, -SIZE,
	         SIZE, -SIZE, -SIZE,
	         SIZE,  SIZE, -SIZE,
	        -SIZE,  SIZE, -SIZE,

	        -SIZE, -SIZE,  SIZE,
	        -SIZE, -SIZE, -SIZE,
	        -SIZE,  SIZE, -SIZE,
	        -SIZE,  SIZE, -SIZE,
	        -SIZE,  SIZE,  SIZE,
	        -SIZE, -SIZE,  SIZE,

	        SIZE, -SIZE, -SIZE,
	        SIZE, -SIZE,  SIZE,
	        SIZE,  SIZE,  SIZE,
	        SIZE,  SIZE,  SIZE,
	        SIZE,  SIZE, -SIZE,
	        SIZE, -SIZE, -SIZE,

	        -SIZE, -SIZE,  SIZE,
	        -SIZE,  SIZE,  SIZE,
	         SIZE,  SIZE,  SIZE,
	         SIZE,  SIZE,  SIZE,
	         SIZE, -SIZE,  SIZE,
	        -SIZE, -SIZE,  SIZE,

	        -SIZE,  SIZE, -SIZE,
	         SIZE,  SIZE, -SIZE,
	         SIZE,  SIZE,  SIZE,
	         SIZE,  SIZE,  SIZE,
	        -SIZE,  SIZE,  SIZE,
	        -SIZE,  SIZE, -SIZE,

	        -SIZE, -SIZE, -SIZE,
	        -SIZE, -SIZE,  SIZE,
	         SIZE, -SIZE, -SIZE,
	         SIZE, -SIZE, -SIZE,
	        -SIZE, -SIZE,  SIZE,
	         SIZE, -SIZE,  SIZE
	};
	
	public Skybox(String name)
	{	
		cubemap = new Texture("src/com/me_smart/minecraft/textures/skybox/" + name, Texture.CUBEMAP);
		cubemapShader = new Shader("src/com/me_smart/minecraft/shaders/skyboxVertex.glsl", "src/com/me_smart/minecraft/shaders/skyboxFragment.glsl");
		mesh = new Mesh();
		mesh.apply(skyboxVertices, Mesh.CUBEMAP);
		mesh.setup();
	}
	
	public Skybox(Mesh mesh, Texture cubemap, Shader cubemapShader) {
		this.mesh = mesh;
		this.cubemap = cubemap;
		this.cubemapShader = cubemapShader;
	}
	
	public Mesh getMesh() {
		return mesh;
	}
	
	public void setMesh(Mesh mesh) {
		this.mesh = mesh;
	}
	
	public Texture getCubemap() {
		return cubemap;
	}
	
	public void setCubemap(Texture cubemap) {
		this.cubemap = cubemap;
	}
	
	public Shader getCubemapShader() {
		return cubemapShader;
	}
	
	public void setCubemapShader(Shader cubemapShader) {
		this.cubemapShader = cubemapShader;
	}
		
}
