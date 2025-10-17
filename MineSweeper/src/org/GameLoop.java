package org;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
		
		int rows = 16;
		int columns = 30;
		int bombCount = 99;
		MineSweeper gameField = new MineSweeper(99, 30, 16);
		
		//frame.setPreferredSize(new Dimension(1000, 1000));
		panel = new JPanel(new GridLayout(rows, columns, 0, 0));
		//panel.setPreferredSize(new Dimension(1000,1000));
		int[][] gameArr = gameField.getGameMap();
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				
				int row = i;
				int column = j;
				
				JButton button = new JButton();
				button.setPreferredSize(new Dimension(25,25));
				button.addMouseListener(new MouseListener() {
					
					@Override
					public void mouseReleased(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mousePressed(MouseEvent e) {
						System.out.println(e.getButton());
						
						if(e.getButton() == 3) {
							if(button.getText() == "F") {
								button.setText("");
							} else {
								button.setText("F");
							}
							
						} else if(e.getButton() == 1) {
							if(gameArr[row][column] == 1) {
								frame.dispose();
							} else {
								int localBombs = 0;
								for(int k = row -1; k < row + 2; k++) {
									for(int m = column - 1; m < column + 2; m++) {
										if(m >= 0 && m < columns && k >= 0 && k < rows && gameArr[k][m] == 1) {
											localBombs++;
										}
									}
								}
								System.out.println("Num of bombs around this pos: " + localBombs);
								button.setText(Integer.toString(localBombs));
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
				panel.add(button);
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
