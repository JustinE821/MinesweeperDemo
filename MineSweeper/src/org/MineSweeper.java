package org;
//import java.util.HashMap;
import java.util.Arrays;
import java.math.*;

public class MineSweeper {
	private int bombCount;
	private int levelLen;
	private int levelHeight;
	private int[][] gameMap;
	
	
	public MineSweeper(int bombCount, int levelLen, int levelHeight) {
		this.bombCount = bombCount;
		this.levelLen = levelLen;
		this.levelHeight = levelHeight;
		this.gameMap = levelGeneration();
	}
	
	
	public int[][] getGameMap() {
		//for(int[] arr: this.gameMap) {
		//	for(int i = 0; i < arr.length; i++) {
		//		System.out.print(arr[i] + " ");
		//	}
		//	System.out.println();
		//}
		return gameMap;
	}


	//public void setGameMap(int[][] gameMap) {
	//	this.gameMap = gameMap;
	//}


	public int getBombCount() {
		
		return bombCount;
	}


	public void setBombCount(int bombCount) {
		this.bombCount = bombCount;
	}


	public int getLevelLen() {
		return levelLen;
	}


	public void setLevelLen(int levelLen) {
		this.levelLen = levelLen;
	}


	public int getLevelHeight() {
		return levelHeight;
	}


	public void setLevelHeight(int levelHeight) {
		this.levelHeight = levelHeight;
	}


	private int[][] levelGeneration() {
		
		if(this.levelHeight < 4 || this.levelLen < 4 || this.bombCount >= this.levelHeight * this.levelLen) {
			System.out.println("Input Length and height greater than 4");
			
		} else {
			
			int[][] gameMap = new int[this.levelHeight][this.levelLen];
			
			for(int[] arr: gameMap) {
				Arrays.fill(arr, 0);
			}
			
			int bombsNotPlaced = this.bombCount;
			while(bombsNotPlaced > 0) {
				int col = (int)(Math.random() * this.levelLen);
				int row = (int)(Math.random() * this.levelHeight);
				if(gameMap[row][col] == 0) {
					gameMap[row][col] = 1;
					--bombsNotPlaced;
				}
			}
			
			return gameMap;
			
		}
		

		return new int[0][0];
		
	}
	
	
	
}
