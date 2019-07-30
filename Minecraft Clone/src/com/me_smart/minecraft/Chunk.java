package com.me_smart.minecraft;

import java.util.HashMap;

import org.joml.Random;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector3f;
import org.joml.Vector3i;

public class Chunk implements Runnable {

	public final static int SIZE = 16;
	public final static int HEIGHT = 32;
	private Block[][][] blocks = new Block[SIZE][HEIGHT][SIZE];
	private Vector3i position;
	private boolean isReadyToDraw = false;
	private Mesh mesh;
	private Thread chunkBuilder;
	private HashMap<Vector3i, Chunk> world;
	
	public Chunk(Vector3i position, HashMap<Vector3i, Chunk> world)
	{
		this.position = position;
		clear();
		fillRandomly();
		chunkBuilder = new Thread(this, "Chunk: " + position.x + ", " + position.z);
		this.world = world;
		chunkBuilder.start();
	}
	
	public void update(float deltaTime)
	{
		if(!chunkBuilder.isAlive() && isReadyToDraw == false)
		{
			isReadyToDraw = true;
			getMesh().setup();
			//Debug.log("generated in thread");
		}
	}
	
	public void fillRandomly()
	{
		Random random = new Random(12345);
		for(int x = 0; x < SIZE; x++)
		{
			for(int y = 0; y < HEIGHT; y++)
			{
				for(int z = 0; z < SIZE; z++)
				{
					if(random.nextInt(10) < 5)
						blocks[x][y][z] = Block.AIR;
					else {
						blocks[x][y][z] = Block.STONE;
					}
				}
			}
		}
	}
	
	public void clear()
	{
		for(int x = 0; x < SIZE; x++)
		{
			for(int y = 0; y < HEIGHT; y++)
			{
				for(int z = 0; z < SIZE; z++)
				{
					blocks[x][y][z] = Block.AIR;
				}
			}
		}
	}
	
	public void setBlock(int x, int y, int z, Block block)
	{
		blocks[x][y][z] = block;
	}
	
	public Block getBlock(int x, int y, int z)
	{
		return blocks[x][y][z];
	}
	
	public boolean isInBounds(int x, int y, int z)
	{
		if(x >= 0 && y >= 0 && z >= 0 && x < SIZE && y < HEIGHT && z < SIZE)
		{
			return true;
		}else {
			return false;
		}
	}

	public Vector3i getPosition() {
		return position;
	}

	public void setPosition(Vector3i position) {
		this.position = position;
	}

	public boolean isReadyToDraw() {
		return isReadyToDraw;
	}

	public void setReadyToDraw(boolean isReadyToDraw) {
		this.isReadyToDraw = isReadyToDraw;
	}

	public Mesh getMesh() {
		return mesh;
	}

	public void setMesh(Mesh mesh) {
		this.mesh = mesh;
	}

	@Override
	public void run() {
		byte[] faces = new byte[Chunk.SIZE * Chunk.SIZE * Chunk.HEIGHT];
		boolean exists[] = new boolean[4];
		exists[0] = world.containsKey(getPosition().add(new Vector3i(0, 0, 1)));
		exists[1] = world.containsKey(getPosition().add(new Vector3i(1, 0, 0)));
		exists[2] = world.containsKey(getPosition().add(new Vector3i(0, 0, -1)));
		exists[3] = world.containsKey(getPosition().add(new Vector3i(-1, 0, 0)));
		Chunk neighbours[] = new Chunk[4];
		neighbours[0] = world.get(getPosition().add(new Vector3i(0, 0, 1)));
		neighbours[1] = world.get(getPosition().add(new Vector3i(1, 0, 0)));
		neighbours[2] = world.get(getPosition().add(new Vector3i(0, 0, -1)));
		neighbours[3] = world.get(getPosition().add(new Vector3i(-1, 0, 0)));
		int faceIndex = 0;
		int sizeEstimate = 0;
		for(int x = 0; x < Chunk.SIZE; x++)
		{
			for(int y = 0; y < Chunk.HEIGHT; y++)
			{
				for(int z = 0; z < Chunk.SIZE; z++)
				{
					if(x == 0 && exists[0])
					{
						faces[faceIndex] |= Direction.West;
						sizeEstimate += 4;
					}else
					if(x == Chunk.SIZE - 1 && exists[2])
					{
						faces[faceIndex] |= Direction.East;
						sizeEstimate += 4;
					}
					if(z == 0 && (!exists[1] || neighbours[3].getBlock(position.x + x, position.y + y, position.z + z - 1) == Block.AIR))
					{
						faces[faceIndex] |= Direction.South;
						sizeEstimate += 4;
					}else
					if(z == Chunk.SIZE - 1 && exists[3])
					{
						faces[faceIndex] |= Direction.North;
						sizeEstimate += 4;
					}
					if(y == 0)
					{
						faces[faceIndex] |= Direction.Bottom;
						sizeEstimate += 4;
					}else
					if(y == Chunk.HEIGHT - 1)
					{
						faces[faceIndex] |= Direction.Top;
						sizeEstimate += 4;
					}
					faceIndex++;
				}
			}
		}
		faceIndex = 0;
		int indicesIndex = 0;
		int vertexIndex = 0;
		Vector3f vertices[] = new Vector3f[sizeEstimate];
		Vector2f uvs[] = new Vector2f[sizeEstimate];
		Vector3f normals[] = new Vector3f[sizeEstimate];
		int indices[] = new int[(int) (sizeEstimate * 1.5)];
		for(int x = 0; x < Chunk.SIZE; x++)
		{
			for(int y = 0; y < Chunk.HEIGHT; y++)
			{
				for(int z = 0; z < Chunk.SIZE; z++)
				{
					if(faces[faceIndex] == 0)
					{
						faceIndex++;
						continue;
					}
					Block block = getBlock(x, y, z);
					if((faces[faceIndex] & Direction.North) != 0)
					{
						vertices[vertexIndex + 0] = new Vector3f(x + getPosition().x, y + getPosition().y, z + getPosition().z + 1);
						vertices[vertexIndex + 1] = new Vector3f(x + getPosition().x + 1, y + getPosition().y, z + getPosition().z + 1);
						vertices[vertexIndex + 2] = new Vector3f(x + getPosition().x, y + getPosition().y + 1, z + getPosition().z + 1);
						vertices[vertexIndex + 3] = new Vector3f(x + getPosition().x + 1, y + getPosition().y + 1, z + getPosition().z + 1);
						indices[indicesIndex + 0] = vertexIndex + 1;
						indices[indicesIndex + 1] = vertexIndex + 2;
						indices[indicesIndex + 2] = vertexIndex;
						indices[indicesIndex + 3] = vertexIndex + 1;
						indices[indicesIndex + 4] = vertexIndex + 3;
						indices[indicesIndex + 5] = vertexIndex + 2;
						Vector2f[] uvs_temp = TextureController.getBlockTexture(block, Direction.North);
						uvs[vertexIndex + 0] = uvs_temp[0];
						uvs[vertexIndex + 1] = uvs_temp[1];
						uvs[vertexIndex + 2] = uvs_temp[2];
						uvs[vertexIndex + 3] = uvs_temp[3];
						vertexIndex += 4;
						indicesIndex += 6;
					}
					if((faces[faceIndex] & Direction.South) != 0)
					{
						vertices[vertexIndex + 0] = new Vector3f(x + getPosition().x, y + getPosition().y, z + getPosition().z);
						vertices[vertexIndex + 1] = new Vector3f(x + getPosition().x + 1, y + getPosition().y, z + getPosition().z);
						vertices[vertexIndex + 2] = new Vector3f(x + getPosition().x, y + getPosition().y + 1, z + getPosition().z);
						vertices[vertexIndex + 3] = new Vector3f(x + getPosition().x + 1, y + getPosition().y + 1, z + getPosition().z);
						indices[indicesIndex + 0] = vertexIndex + 1;
						indices[indicesIndex + 1] = vertexIndex + 2;
						indices[indicesIndex + 2] = vertexIndex;
						indices[indicesIndex + 3] = vertexIndex + 1;
						indices[indicesIndex + 4] = vertexIndex + 3;
						indices[indicesIndex + 5] = vertexIndex + 2;
						Vector2f[] uvs_temp = TextureController.getBlockTexture(block, Direction.South);
						uvs[vertexIndex + 0] = uvs_temp[0];
						uvs[vertexIndex + 1] = uvs_temp[1];
						uvs[vertexIndex + 2] = uvs_temp[2];
						uvs[vertexIndex + 3] = uvs_temp[3];
						vertexIndex += 4;
						indicesIndex += 6;
					}
					if((faces[faceIndex] & Direction.East) != 0)
					{
						vertices[vertexIndex + 0] = new Vector3f(x + getPosition().x + 1, y + getPosition().y, z + getPosition().z);
						vertices[vertexIndex + 1] = new Vector3f(x + getPosition().x + 1, y + getPosition().y, z + getPosition().z + 1);
						vertices[vertexIndex + 2] = new Vector3f(x + getPosition().x + 1, y + getPosition().y + 1, z + getPosition().z);
						vertices[vertexIndex + 3] = new Vector3f(x + getPosition().x + 1, y + getPosition().y + 1, z + getPosition().z + 1);
						indices[indicesIndex + 0] = vertexIndex + 1;
						indices[indicesIndex + 1] = vertexIndex + 2;
						indices[indicesIndex + 2] = vertexIndex;
						indices[indicesIndex + 3] = vertexIndex + 1;
						indices[indicesIndex + 4] = vertexIndex + 3;
						indices[indicesIndex + 5] = vertexIndex + 2;
						Vector2f[] uvs_temp = TextureController.getBlockTexture(block, Direction.East);
						uvs[vertexIndex + 0] = uvs_temp[0];
						uvs[vertexIndex + 1] = uvs_temp[1];
						uvs[vertexIndex + 2] = uvs_temp[2];
						uvs[vertexIndex + 3] = uvs_temp[3];
						vertexIndex += 4;
						indicesIndex += 6;
					}
					if((faces[faceIndex] & Direction.West) != 0)
					{
						vertices[vertexIndex + 0] = new Vector3f(x + getPosition().x, y + getPosition().y, z + getPosition().z);
						vertices[vertexIndex + 1] = new Vector3f(x + getPosition().x, y + getPosition().y, z + getPosition().z + 1);
						vertices[vertexIndex + 2] = new Vector3f(x + getPosition().x, y + getPosition().y + 1, z + getPosition().z);
						vertices[vertexIndex + 3] = new Vector3f(x + getPosition().x, y + getPosition().y + 1, z + getPosition().z + 1);
						indices[indicesIndex + 0] = vertexIndex + 1;
						indices[indicesIndex + 1] = vertexIndex + 2;
						indices[indicesIndex + 2] = vertexIndex;
						indices[indicesIndex + 3] = vertexIndex + 1;
						indices[indicesIndex + 4] = vertexIndex + 3;
						indices[indicesIndex + 5] = vertexIndex + 2;
						Vector2f[] uvs_temp = TextureController.getBlockTexture(block, Direction.West);
						uvs[vertexIndex + 0] = uvs_temp[0];
						uvs[vertexIndex + 1] = uvs_temp[1];
						uvs[vertexIndex + 2] = uvs_temp[2];
						uvs[vertexIndex + 3] = uvs_temp[3];
						vertexIndex += 4;
						indicesIndex += 6;					
					}
					if((faces[faceIndex] & Direction.Bottom) != 0)
					{
						vertices[vertexIndex + 0] = new Vector3f(x + getPosition().x, y + getPosition().y, z + getPosition().z);
						vertices[vertexIndex + 1] = new Vector3f(x + getPosition().x, y + getPosition().y, z + getPosition().z + 1);
						vertices[vertexIndex + 2] = new Vector3f(x + getPosition().x + 1, y + getPosition().y, z + getPosition().z);
						vertices[vertexIndex + 3] = new Vector3f(x + getPosition().x + 1, y + getPosition().y, z + getPosition().z + 1);
						indices[indicesIndex + 0] = vertexIndex + 1;
						indices[indicesIndex + 1] = vertexIndex + 2;
						indices[indicesIndex + 2] = vertexIndex;
						indices[indicesIndex + 3] = vertexIndex + 1;
						indices[indicesIndex + 4] = vertexIndex + 3;
						indices[indicesIndex + 5] = vertexIndex + 2;
						Vector2f[] uvs_temp = TextureController.getBlockTexture(block, Direction.Bottom);
						uvs[vertexIndex + 0] = uvs_temp[0];
						uvs[vertexIndex + 1] = uvs_temp[1];
						uvs[vertexIndex + 2] = uvs_temp[2];
						uvs[vertexIndex + 3] = uvs_temp[3];
						vertexIndex += 4;
						indicesIndex += 6;
					}
					if((faces[faceIndex] & Direction.Top) != 0)
					{
						vertices[vertexIndex + 0] = new Vector3f(x + getPosition().x, y + getPosition().y + 1, z + getPosition().z);
						vertices[vertexIndex + 1] = new Vector3f(x + getPosition().x, y + getPosition().y + 1, z + getPosition().z + 1);
						vertices[vertexIndex + 2] = new Vector3f(x + getPosition().x + 1, y + getPosition().y + 1, z + getPosition().z);
						vertices[vertexIndex + 3] = new Vector3f(x + getPosition().x + 1, y + getPosition().y + 1, z + getPosition().z + 1);
						indices[indicesIndex + 0] = vertexIndex + 1;
						indices[indicesIndex + 1] = vertexIndex + 2;
						indices[indicesIndex + 2] = vertexIndex;
						indices[indicesIndex + 3] = vertexIndex + 1;
						indices[indicesIndex + 4] = vertexIndex + 3;
						indices[indicesIndex + 5] = vertexIndex + 2;
						Vector2f[] uvs_temp = TextureController.getBlockTexture(block, Direction.Top);
						uvs[vertexIndex + 0] = uvs_temp[0];
						uvs[vertexIndex + 1] = uvs_temp[1];
						uvs[vertexIndex + 2] = uvs_temp[2];
						uvs[vertexIndex + 3] = uvs_temp[3];
						vertexIndex += 4;
						indicesIndex += 6;
					}
					faceIndex++;
				}
			}
		}
		mesh = new Mesh();
		mesh.apply(vertices, uvs, indices, Mesh.FULL_MODEL);
	}
	
}
