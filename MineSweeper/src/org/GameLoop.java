package org;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import java.util.HashMap;

import java.awt.*;

import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.TitlePaneLayout;

public class GameLoop extends JFrame implements ActionListener{
	private JFrame frame;
	private JPanel panel;
	
	JPanel pagePanel;
	JPanel gameBorder;
	
	private JButton restartBtn;
	private JLabel bombLabel;
	
	
	Integer safeSpaces;
	
	int[][] gameArr;
	MineSweeper gameField;
	
	private int rows;
	private int columns;
	private int bombCount;
	
	private HashMap<Integer, JLabel> tileMap;
	
	private int flagsLeft;
	
	private TimerTask task;
	
	String currDir = System.getProperty("user.dir");
	private final String[] imgDirs = {"/org/images/zero.png", "/org/images/one.png", 
			"/org/images/two.png", "/org/images/three.png", 
			"/org/images/four.png", "/org/images/five.png", 
			"/org/images/six.png", "/org/images/seven.png", 
			"/org/images/eight.png", "/org/images/explodedBomb.png", "/org/images/bomb.png"};
	
	//private frameWidth = frame.get
	
	JLabel timeLabel = new JLabel();
	
	
	private long elapsedSeconds;
	
	public GameLoop() {
		rows = 16;
		columns = 30;
		bombCount = 99;
		frame = new JFrame();
		tileMap = new HashMap<Integer, JLabel>();
		
		createWindow();
		checkWindow();
		
		

		initializeGame();
	}
	
	Timer timer = new Timer("Game time");
	
	
	private void initializeGame() {
		

		
		trackTime();

		gameField = new MineSweeper(99, 30, 16);
		gameArr = gameField.getGameMap();
		
		
		elapsedSeconds = 0;
		flagsLeft = bombCount;
		timeLabel.setText(String.valueOf(elapsedSeconds));
		bombLabel.setText(String.valueOf(bombCount));

		

		
		safeSpaces = columns * rows - bombCount;
		
		//frame.setPreferredSize(new Dimension(1000, 1000));
		GridLayout grid = new GridLayout(rows, columns, 0, 0);
		panel = new JPanel(grid);
		
		int index = 0;
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				
				int row = i;
				int column = j;
				
				String imgDir = currDir + "/src/org/images/tile.png";
				//ImageIcon image = new ImageIcon(imgDir);
				ImageIcon image = new ImageIcon(getClass().getResource("/org/images/tile.png"));
				
				JLabel label = new JLabel();

				label.setPreferredSize(new Dimension(45,45));
				label.setHorizontalAlignment(SwingConstants.LEFT);
				Image img = image.getImage().getScaledInstance(45, 45, Image.SCALE_DEFAULT);
				label.setIcon(new ImageIcon(img));
				label.setBackground(Color.WHITE);
				
				//frame.addPropertyChangeListener(new PropertyChangeListener() {
					
				//	@Override
				//	public void propertyChange(PropertyChangeEvent evt) {
						
				//		System.out.println(evt.getPropertyName());
						
				//	}
				//});
				
				tileMap.put((Integer)index, (JLabel)label);
				mouseListener(label, row, column, index);
				

				
				panel.add(label);
				++index;
			}

		}
		
		gameBorder = new JPanel();
		gameBorder.setLayout(new FlowLayout());
		gameBorder.add(panel);
		
		pagePanel.add(gameBorder);
		
		
		frame.add(pagePanel);
		
		

	}
	
	@Override
	public void actionPerformed(ActionEvent e) {

		
	}
	
	
	public void trackTime() {
		
		if(this.task != null) {
			task.cancel();
		}
		
		
		task = new TimerTask() {
			
			@Override
			public void run() {
				if(timeLabel.getText() == "-1") {
					task.cancel();
				}
				
				timeLabel.setText(Long.toString(elapsedSeconds++));
				System.out.println(elapsedSeconds);

				
			}
		};
		
		timer.scheduleAtFixedRate(task, 1000, 1000);
	}
	
	public void paint(Graphics g, Image img) {
		super.paint(g);
		
		g.drawImage(img, 0, 0, this);
	}
	
	
	public void mouseListener(JLabel label, int row, int column, int index) {
		label.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				if(e.getButton() == 1) {
					
					int localBombs = 0;
					//if this condition is true, the player clicked a bomb
					if(gameArr[row][column] == 1 && label.getBackground() == Color.WHITE) {
						
						//Displays exploded bomb
						ImageIcon tempImage = new ImageIcon(getClass().getResource(imgDirs[9]));
						Image tempImg = tempImage.getImage().getScaledInstance(45, 45, Image.SCALE_AREA_AVERAGING);
						label.setBackground(Color.RED);
						task.cancel();
						label.setIcon(new ImageIcon(tempImg));
						disableTiles();
						
					//if this condition is met, the player did not click a bomb
					} else if(gameArr[row][column] == 0 && label.getBackground() == Color.WHITE){
						
						for(int k = row -1; k < row + 2; k++) {
							for(int m = column - 1; m < column + 2; m++) {
								if(m >= 0 && m < columns && k >= 0 && k < rows && gameArr[k][m] == 1) {
									localBombs++;
								}
							}
						}
						
						ImageIcon tempImage = new ImageIcon(getClass().getResource(imgDirs[localBombs]));
						Image tempImg = tempImage.getImage().getScaledInstance(45, 45, Image.SCALE_AREA_AVERAGING);
						label.setBackground(Color.GRAY);
						label.setIcon(new ImageIcon(tempImg));
						safeSpaces--;
						if(safeSpaces == 0) {
							restartBtn.setText(":)");
						}
						
						//Code below allows safe areas to automatically be clicked
						if(localBombs == 0) {
							for(int k = index - columns; k < index + columns * 2;) {
								for(int m = -1; m < 2; m++) {
									
									if((k + m) % columns == 0 && index % columns == columns - 1 || (k + m) % columns == columns - 1 && index % columns == 0) {
										
									} else if(tileMap.containsKey(k + m)) {
										JLabel adjacentTile = tileMap.get(k + m);
										MouseListener tileListener = adjacentTile.getMouseListeners()[0];
										tileListener.mouseReleased(e);
									}
								}
								k += columns;
							}
							
							//System.out.println(tem + " " + e);
							
							//for(int k = index + columns;) {
								
								
							//}
						}
						
					} else if(gameArr[row][column] == 0 && label.getBackground() == Color.GRAY) {
						
						int pinnedBombs = 0;
						
						
						for(int k = row -1; k < row + 2; k++) {
							for(int m = column - 1; m < column + 2; m++) {
								if(m >= 0 && m < columns && k >= 0 && k < rows && gameArr[k][m] == 1) {
									localBombs++;
								}
							}
						}
						
						
						
						
						for(int k = index - columns; k < index + columns * 2;) {
							for(int m = -1; m < 2; m++) {
								
								if((k + m) % columns == 0 && index % columns == columns - 1 || (k + m) % columns == columns - 1 && index % columns == 0) {
									
								} else if(tileMap.containsKey(k + m)) {
									JLabel adjacentTile = tileMap.get(k + m);
									if(adjacentTile.getBackground() == Color.BLACK) {
										pinnedBombs++;
									}
									

									//MouseListener tileListener = adjacentTile.getMouseListeners()[0];
									//tileListener.mouseReleased(e);
								}
							}
							k += columns;
						}
						
						if(localBombs == pinnedBombs && localBombs > 0) {
							for(int k = index - columns; k < index + columns * 2;) {
								for(int m = -1; m < 2; m++) {
									JLabel adjacentTile = tileMap.get(k + m);
									if((k + m) % columns == 0 && index % columns == columns - 1 || (k + m) % columns == columns - 1 && index % columns == 0) {
										
									} else if(tileMap.containsKey(k + m) && adjacentTile.getBackground() == Color.WHITE) {
										
										MouseListener tileListener = adjacentTile.getMouseListeners()[0];
										tileListener.mouseReleased(e);
									}
								}
								k += columns;
							}
						}
						
					}
				}
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				//System.out.println(e.getButton());
				
				if(e.getButton() == 3) {
					//System.out.println(label.getBackground());
					if(label.getBackground() == Color.WHITE) {
						ImageIcon tempImage = new ImageIcon(getClass().getResource("/org/images/flag.png"));
						Image tempImg = tempImage.getImage().getScaledInstance(45, 45, Image.SCALE_DEFAULT);
						label.setBackground(Color.BLACK);
						label.setIcon(new ImageIcon(tempImg));
						bombLabel.setText(Integer.toString(--flagsLeft));
					
					} else if (label.getBackground() == Color.BLACK){
						ImageIcon tempImage = new ImageIcon(getClass().getResource("/org/images/tile.png"));
						Image tempImg = tempImage.getImage().getScaledInstance(45, 45, Image.SCALE_DEFAULT);
						label.setBackground(Color.WHITE);
						label.setIcon(new ImageIcon(tempImg));
						bombLabel.setText(Integer.toString(++flagsLeft));
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
	}
	
	
	private void checkWindow() {
		frame.addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				
				if(task != null) {
					timer.cancel();
				}
				
				
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	private void createWindow() {
		frame.setTitle("MineSweeper Demo");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(800, 500);
		frame.setLocationRelativeTo(null);
		
		restartBtn = new JButton();
		
		restartBtn.setText(">:)");
		restartBtn.setMaximumSize(new Dimension(25,25));
		
		pagePanel = new JPanel();
		//pagePanel.setLayout(new GridBagLayout());
		pagePanel.setLayout(new BoxLayout(pagePanel, BoxLayout.Y_AXIS));
		
		
		bombLabel = new JLabel();
		
		bombLabel.setText(String.valueOf(bombCount));
		bombLabel.setMaximumSize(new Dimension(25,25));
		

		timeLabel.setText("0");
		timeLabel.setMaximumSize(new Dimension(25,25));
		

		
		
		JPanel headerBox = new JPanel();
		
		
		//panel below allows btns to have their max sizes respected
		JPanel middleHeaderBox = new JPanel();
		middleHeaderBox.setLayout(new FlowLayout());
		middleHeaderBox.add(restartBtn);
		
		
		
		
		headerBox.setLayout(new BorderLayout());
		headerBox.setMaximumSize(new Dimension(5000, 25));
		
		headerBox.add(middleHeaderBox, BorderLayout.CENTER);
		headerBox.add(bombLabel, BorderLayout.LINE_START);
		
		headerBox.add(timeLabel, BorderLayout.LINE_END);
		
		headerBox.setBounds(new Rectangle());
		
		
		//Allows header to have fixed height
		JPanel outerHeaderBox = new JPanel();
		outerHeaderBox.setLayout(new FlowLayout());
		outerHeaderBox.add(headerBox);
		
		pagePanel.add(headerBox);
		
		
		restartBtn.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.getButton() == 1) {
					System.out.println("test");
					
					//resets timer for each game
					pagePanel.remove(gameBorder);
					tileMap.clear();

					initializeGame();
				}
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
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
		
		


		frame.pack();
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
		
		
		
	}
	
	private void disableTiles() {
		tileMap.values();
		for(JLabel tile: tileMap.values()) {
			MouseListener tileListener = tile.getMouseListeners()[0];
			tile.removeMouseListener(tileListener);
		}
	}
	
	
}
