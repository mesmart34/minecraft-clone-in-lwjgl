package com.me_smart.minecraft;

import org.joml.Vector2f;

public class TextureController {

	public static Vector2f[] getBlockTexture(Block type, int direction)
	{
		Vector2f a = new Vector2f();
		Vector2f b = new Vector2f();
		Vector2f c = new Vector2f();
		Vector2f d = new Vector2f();
		if(type == Block.GRASS)
		{
			if(direction == Direction.Top) {
				a = getCellCoords(0, 1);
				b = getCellCoords(1, 1);
				c = getCellCoords(1, 2);
				d = getCellCoords(0, 2);
			}if(direction == Direction.Bottom) {
				a = getCellCoords(1, 0);
				b = getCellCoords(2, 0);
				c = getCellCoords(2, 1);
				d = getCellCoords(1, 1);
			}else {
				a = getCellCoords(0, 0);
				b = getCellCoords(1, 0);
				c = getCellCoords(1, 1);
				d = getCellCoords(0, 1);
			}
		}else if(type == Block.DIRT)
		{
			a = getCellCoords(1, 0);
			b = getCellCoords(2, 0);
			c = getCellCoords(2, 1);
			d = getCellCoords(1, 1);
		}else if(type == Block.STONE)
		{
			a = getCellCoords(2, 0);
			b = getCellCoords(3, 0);
			c = getCellCoords(3, 1);
			d = getCellCoords(2, 1);
		}else if(type == Block.WATER)
		{
			a = getCellCoords(3, 0);
			b = getCellCoords(4, 0);
			c = getCellCoords(4, 1);
			d = getCellCoords(3, 1);
		}else if(type == Block.SAND)
		{
			a = getCellCoords(4, 0);
			b = getCellCoords(5, 0);
			c = getCellCoords(5, 1);
			d = getCellCoords(4, 1);
		}else if(type == Block.WOOD)
		{
			a = getCellCoords(5, 0);
			b = getCellCoords(6, 0);
			c = getCellCoords(6, 1);
			d = getCellCoords(5, 1);
		}
		
		return new Vector2f[] { b, a, c, d };
	}
	
	public static Vector2f getCellCoords(int x, int y)
	{
		return new Vector2f(1.0f / 6.0f * x, 1.0f / 3.0f * y);
	}
	
}
