package org;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.*;

public class GameLoop implements ActionListener{
	private JFrame frame;
	private JPanel panel;
	
	public GameLoop() {
		initializeGame();
	}
	
	private void initializeGame() {
		frame = new JFrame();
		frame.setTitle("MineSweeper Demo");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(800, 500);
		frame.setLocationRelativeTo(null);
		String currDir = System.getProperty("user.dir");
		//File file = new File(System.getProperty("user.dir") + "/src/org/images/tile.png");
		//System.out.println(System.getProperty("user.dir"));
		int rows = 16;
		int columns = 30;
		int bombCount = 99;
		MineSweeper gameField = new MineSweeper(99, 30, 16);
		
		//frame.setPreferredSize(new Dimension(1000, 1000));
		GridLayout grid = new GridLayout(rows, columns, 0, 0);
		panel = new JPanel(grid);
		//panel.setPreferredSize(new Dimension(1000,1000));
		int[][] gameArr = gameField.getGameMap();
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				
				int row = i;
				int column = j;
				
				JButton button = new JButton();
				
				String[] imgDirs = {currDir + "/src/org/images/zero.png", currDir + "/src/org/images/one.png", 
						currDir + "/src/org/images/two.png", currDir + "/src/org/images/three.png", 
						currDir + "/src/org/images/four.png", currDir + "/src/org/images/five.png", 
						currDir + "/src/org/images/six.png", currDir + "/src/org/images/seven.png", 
						currDir + "/src/org/images/eight.png"};
				
				String imgDir = currDir + "/src/org/images/tile.png";
				ImageIcon image = new ImageIcon(imgDir);
				JLabel label = new JLabel();

				label.setPreferredSize(new Dimension(25,25));
				label.setHorizontalAlignment(SwingConstants.LEFT);
				Image img = image.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
				label.setIcon(new ImageIcon(img));
				label.setBackground(Color.WHITE);
				
				label.addMouseListener(new MouseListener() {
					
					@Override
					public void mouseReleased(MouseEvent e) {
						if(e.getButton() == 1) {
							if(gameArr[row][column] == 1 && label.getBackground() == Color.WHITE) {
								frame.dispose();
							} else if(gameArr[row][column] == 0 && label.getBackground() == Color.WHITE){
								int localBombs = 0;
								for(int k = row -1; k < row + 2; k++) {
									for(int m = column - 1; m < column + 2; m++) {
										if(m >= 0 && m < columns && k >= 0 && k < rows && gameArr[k][m] == 1) {
											localBombs++;
										}
									}
								}
								//System.out.println("Num of bombs around this pos: " + localBombs);
								
								String tempS = imgDirs[localBombs];
								Image tempImg = new ImageIcon(tempS).getImage().getScaledInstance(25, 25, Image.SCALE_AREA_AVERAGING);
								label.setBackground(Color.GRAY);
								label.setIcon(new ImageIcon(tempImg));
							}
						}
						
					}
					
					@Override
					public void mousePressed(MouseEvent e) {
						//System.out.println(e.getButton());
						
						if(e.getButton() == 3) {
							//System.out.println(label.getBackground());
							if(label.getBackground() == Color.WHITE) {
								String tempS = currDir + "/src/org/images/flag.png";
								Image tempImg = new ImageIcon(tempS).getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
								label.setBackground(Color.BLACK);
								label.setIcon(new ImageIcon(tempImg));
							
							} else if (label.getBackground() == Color.BLACK){
								String tempS = currDir + "/src/org/images/tile.png";
								Image tempImg = new ImageIcon(tempS).getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
								label.setBackground(Color.WHITE);
								label.setIcon(new ImageIcon(tempImg));
							}
							
						}
						
						

						
						
					}
					
					@Override
					public void mouseExited(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseEntered(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseClicked(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
				});
				panel.add(label);
			}

		}
		
		frame.add(panel);
		
		
		frame.pack();
		frame.setVisible(true);
	}

	//private JButton createButton() {
	//	
	//	JButton button = new JButton();
	//	
	//	return button;
	//}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {

		
	}
}
