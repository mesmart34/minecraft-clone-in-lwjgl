package com.me_smart.minecraft;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Player implements Serializable {

	private static final long serialVersionUID = 1L;
	private Transform transform;
	private String name;
	private int score;
	private transient int someTempNumber = 256;
	
	public Player(Transform transform, String name) {
		super();
		this.transform = transform;
		this.name = name;
		this.score = 0;
	}

	public static void loadFromFile(Player player, String fileName)
	{
		try {
			FileInputStream fs = new FileInputStream(fileName);
			try {
				ObjectInputStream os = new ObjectInputStream(fs);
				try {
					player = (Player)os.readObject();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally {
					os.close();
					fs.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				Debug.log("Player data was loaded!");
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public static void saveToFile(Player player, String fileName)
	{
		try {
			FileOutputStream fs = new FileOutputStream(fileName);
			try {
				ObjectOutputStream os = new ObjectOutputStream(fs);
				os.writeObject(player);
				os.close();
				fs.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				Debug.log("Player data was saved!");
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Transform getTransform() {
		return transform;
	}

	public void setTransform(Transform transform) {
		this.transform = transform;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getScore() {
		return score;
	}

	public void addScore(int score) {
		this.score += score;
	}

	public int getSomeTempNumber() {
		return someTempNumber;
	}

	public void setSomeTempNumber(int someTempNumber) {
		this.someTempNumber = someTempNumber;
	}
	
	
	
}
