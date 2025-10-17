package org;

public class MineSweeperMain {

	public static void main(String[] args) {
		System.out.print("jdioejdwe");
		System.out.println("Hello world");
		System.out.println();
		System.out.println();
		MineSweeper level = new MineSweeper(200, 20, 28);
		
		System.out.println("Bomb count: " + level.getBombCount() + "\nLevel length: " + level.getLevelLen() + "\nLevel Height: " + level.getLevelHeight());
		
		level.getGameMap();
	}

}
