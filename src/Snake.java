import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;

/*
 * Things to add: Menu:(Includes) About page Difficulty (speed) Highscores (Text
 * file)
 */

/*			Dynamic Data Structures:
 * 	Dynamic data structures are used in this program in a way that could not be done with finite data structures, or else 
 * would be incredibly time consuming and inefficient.
 * 	In this program dynamic data structures are used for the snake itself. Because the snake is variable length, and each 
 * piece contains data, a dynamic data structure (such as a LinkedList or arrayList), is really the only way to go. Using
 * a finite array is possible, but it would be very inefficient as an array of the maximum size would need to be kept in memory,
 * regardless of the size of the snake.
 * 
 *  		Dynamic Data Types:
 *  Of the available dynamic data structures ArrayLists seemed to be the best choice. They are interchangeable with LinkedLists 
 * in their effect, but are supposedly more efficient. For this scenario Data Trees were obviously out, since there isn't any data 
 * that needs comparing. If a multi-person scoreboard were to be added, a Binary Data Tree could be used.
 */

// For planning See github: https://github.com/MaxNiebergall/Snake




/**
 * @author( name= "Max Niebergall" date= "Febuary 19th 2015" )
 * @version v0.1.01 Beta
 * 
 * @link https://github.com/MaxNiebergall/Snake.git
 */
public class Snake {

	public static void main(String[] args) {
		new Snake();
	}

	boolean paused = false;
	int waitTime = 166;
	int highscore = 0;

	// GUI Object Declarations
	JFrame frame = new JFrame();
	JPanel basePane = new JPanel(new BorderLayout());
	JPanel playingFeildPane = new JPanel(new GridLayout(20, 20));
	Point food = new Point();
	JLabel comList[][] = new JLabel[20][20];// X, Y
	JLabel highscoreLabel = new JLabel("Highscore: " + highscore);

	// JPanel buttonPane = new JPanel();
	// JComboBox<String> dificulty = new JComboBox<String>();
	// JButton about = new JButton("About");
	// JButton highScores = new JButton("High Scores");
	// JButton start = new JButton("Start");

	/**
	 * 
	 */
	ArrayList<SnakeObject> snake = new ArrayList<SnakeObject>();

	/**
	 * 
	 */
	Snake() {
		splash();

		try {
			Scanner fsc = new Scanner(new FileReader("highScores.txt"));
			if (fsc.hasNextLine())
				highscoreLabel.setText("Highscore: " + (highscore = fsc.nextInt()));

			fsc.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		for (int x = 0; x < comList.length; x++) {
			for (int y = 0; y < comList[x].length; y++) {
				comList[x][y] = new JLabel();
				comList[x][y].setPreferredSize(new Dimension(5, 5));
				comList[x][y].setOpaque(true);
			}
		}
		System.out.println("comList set up");

		// test placeFood
		for (int i = 0; i < 1000; i++) {
			placeFood();
		}

		reset(true);
		System.out.println("reset(true)");

		// HERE ARE THE KEY BINDINGS
		playingFeildPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0), "up");
		playingFeildPane.getActionMap().put("up", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Up");
				snake.get(0).setDirection(Direction.NORTH);
			}
		});
		playingFeildPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), "left");
		playingFeildPane.getActionMap().put("left", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Left");
				snake.get(0).setDirection(Direction.WEST);
			}
		});
		playingFeildPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), "right");
		playingFeildPane.getActionMap().put("right", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Right");
				snake.get(0).setDirection(Direction.EAST);
			}
		});
		playingFeildPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), "down");
		playingFeildPane.getActionMap().put("down", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Down");
				snake.get(0).setDirection(Direction.SOUTH);
			}
		});
		playingFeildPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"), "UP");
		playingFeildPane.getActionMap().put("UP", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Up-arrow");
				snake.get(0).setDirection(Direction.NORTH);
			}
		});
		playingFeildPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("LEFT"), "LEFT");
		playingFeildPane.getActionMap().put("LEFT", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Left-arrow");
				snake.get(0).setDirection(Direction.WEST);
			}
		});
		playingFeildPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("RIGHT"), "RIGHT");
		playingFeildPane.getActionMap().put("RIGHT", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Right-arrow");
				snake.get(0).setDirection(Direction.EAST);
			}
		});
		playingFeildPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DOWN"), "DOWN");
		playingFeildPane.getActionMap().put("DOWN", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Down-arrow");
				snake.get(0).setDirection(Direction.SOUTH);
			}
		});
		playingFeildPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_P, 0), "pause");
		playingFeildPane.getActionMap().put("pause", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Pause/Play");
				paused = !paused;
				infiniteRefresh();

			}
		});
		// END OF KEY BINDINGS
		/*
		 * about.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) {
		 * 
		 * 
		 * }// end of action performed });// end of about
		 * 
		 * highScores.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { LinkedList<String> names = new LinkedList(); LinkedList<Integer> scores = new LinkedList(); JLabel[] nameLabels = new JLabel[10]; JLabel[] scoreLabels = new JLabel[10];
		 * 
		 * try { Scanner fsc = new Scanner(new FileReader("highScores.txt")); while(fsc.hasNextLine()&&fsc.hasNextInt()){ names.add(fsc.nextLine()); scores.add(Integer.parseInt(fsc.nextLine())); } fsc.close(); } catch (FileNotFoundException e1) { e1.printStackTrace(); } frame.setVisible(false);
		 * 
		 * final JFrame highScoreFrame = new JFrame(); JButton goBack = new JButton("Go Back"); JPanel highScorePane = new JPanel(new BorderLayout());
		 * 
		 * 
		 * for(int i=0; i<10 && i<nameLabels.length; i++){ nameLabels[i] = new JLabel(names.get(i)); scoreLabels[i] = new JLabel(""+scores.get(i)); }
		 * 
		 * for(int i=0; i<10 && i<nameLabels.length; i++){ highScoreFrame.add(nameLabels[i], BorderLayout.WEST); highScoreFrame.add(scoreLabels[i], BorderLayout.EAST); }
		 * 
		 * p1 123 p2 31 p3 41 p4 13 p5 132 p6 1 p7 42 p8 11 p9 0 p10 5
		 * 
		 * 
		 * 
		 * highScorePane.add(goBack, BorderLayout.NORTH);
		 * 
		 * highScoreFrame.add(highScorePane); highScoreFrame.setSize(450, 500); highScoreFrame.setLocationRelativeTo(null); highScoreFrame.setVisible(true);
		 * 
		 * 
		 * goBack.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { highScoreFrame.setVisible(false); frame.setVisible(true); }// end of action performed });// end of about
		 * 
		 * 
		 * 
		 * try { BufferedWriter bfw = new BufferedWriter(new FileWriter("highScores.txt")); while(!names.isEmpty()){ bfw.write(names.removeFirst()); bfw.write(scores.removeFirst()); } bfw.close(); } catch (IOException e1) { e1.printStackTrace(); }
		 * 
		 * }// end of action performed });// end of highScores
		 * 
		 * dificulty.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { String level = (String) dificulty.getSelectedItem();
		 * 
		 * if(level.equals("Level 1")){ waitTime=166; } else if(level.equals("Level 2")){ waitTime=100; } else if(level.equals("Level 4")){ waitTime=80; }
		 * 
		 * 
		 * 
		 * }// end of action performed });// end of dificulty
		 * 
		 * start.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { reset(false);
		 * 
		 * }// end of action performed });// end of start
		 */

		// add it all up
		// buttonPane.setSize(300, 50);
		playingFeildPane.setSize(300, 250);
		// about.setSize(30, 25);
		// highScores.setFont(new Font("Arial Black", 0, 11));
		// about.setFont(new Font("Arial Black", 0, 11));
		// start.setFont(new Font("Arial Black", 0, 11));

		// dificulty.addItem("Level 1");
		// dificulty.addItem("Level 2");
		// dificulty.addItem("Level 4");

		// buttonPane.add(about);
		// buttonPane.add(highScores);
		// buttonPane.add(start);
		// buttonPane.add(dificulty);

		// basePane.add(buttonPane, BorderLayout.NORTH);
		basePane.add(highscoreLabel, BorderLayout.NORTH);
		basePane.add(playingFeildPane, BorderLayout.CENTER);

		for (int x = 0; x < comList.length; x++) {
			for (int y = 0; y < comList[x].length; y++) {
				playingFeildPane.add(comList[y][x]);
			}
		}
		frame.add(basePane);

		// frame window closer
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		// frame settings
		frame.setTitle("Max Nieberall's Snake Game v0.1.01 Beta"/*
																 * TODO PUT VERSION STUFF HERE
																 */);

		frame.setSize(450, 500);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.validate();
		frame.repaint();
		// End GUI set up
		// ---------------------------------------------------------------------

		infiniteRefresh();
	}

	/**
	 * extendSnake() adds a new snake object to the snake linked list, that is a copy of the last element of snake
	 */
	private void extendSnake() {
		snake.add(new SnakeObject(snake.get(snake.size() - 1)));
	}

	/**
	 * updateComList() updates the component list have the correct color placement for the next frame
	 */
	private void updateComList() {
		System.out.println("updateComList");
		for (int x = 0; x < comList.length; x++) {
			for (int y = 0; y < comList[x].length; y++) {
				comList[x][y].setBackground(Color.WHITE);
			}
		}
		for (SnakeObject snk : snake) {
			System.out.println("Update comList snk: " + snk.getPoint());
			if (snk.getPoint().getX() >= 0 && snk.getPoint().getY() >= 0 && snk.getPoint().getX() < comList.length && snk.getPoint().getY() < comList[0].length) {
				comList[snk.getPoint().x][snk.getPoint().y].setBackground(Color.BLACK);
			}

		}
		comList[(int) food.getX()][(int) food.getY()].setBackground(Color.GREEN);
	}

	/**
	 * refresh() calls all the methods that move and change the snake calls the methods to update the visuals for each frame one call of refresh is equivalent to one frame
	 * 
	 * @return boolean
	 */
	private boolean refresh() {
		System.out.println("Refresh()");
		if (paused)
			return false;
		// Update Snake
		Direction dir0 = snake.get(0).getDirection(), dir1;
		if ((snake.get(0).getPoint().x + ((snake.get(0).getDirection() == Direction.EAST || snake.get(0).getDirection() == Direction.WEST) ? snake.get(0).direction() : 0) < 0 || snake.get(0).getPoint().x + ((snake.get(0).getDirection() == Direction.EAST || snake.get(0).getDirection() == Direction.WEST) ? snake.get(0).direction() : 0) >= comList.length || snake.get(0).getPoint().y + ((snake.get(0).getDirection() == Direction.NORTH || snake.get(0).getDirection() == Direction.SOUTH) ? snake.get(0).direction() : 0) < 0 || snake.get(0).getPoint().y + ((snake.get(0).getDirection() == Direction.NORTH || snake.get(0).getDirection() == Direction.SOUTH) ? snake.get(0).direction() : 0) >= comList[0].length)) {
			youLose();
			return false;
		}
		System.out.println("snake.get(0).point: " + snake.get(0).getPoint().toString());
		if (comList[snake.get(0).getPoint().x + ((snake.get(0).getDirection() == Direction.EAST || snake.get(0).getDirection() == Direction.WEST) ? snake.get(0).direction() : 0)][snake.get(0).getPoint().y + ((snake.get(0).getDirection() == Direction.NORTH || snake.get(0).getDirection() == Direction.SOUTH) ? snake.get(0).direction() : 0)].getBackground() == Color.GREEN) {
			extendSnake();
			placeFood();
		}
		for (SnakeObject snk : snake) {

			System.out.println("Snk forward");
			snk.forward();

			System.out.println(" SnkPoint: " + snk.getPoint() + " SnakePoint: " + snake.get(0).getPoint());
			System.out.println(snk != snake.get(0) && (snake.get(0).getPoint().equals(snk.getPoint()) || (snk.getPoint().x < 0 || snk.getPoint().x >= comList.length || snk.getPoint().y < 0 || snk.getPoint().y >= comList[0].length)));
			if ((snk != snake.get(0) && snake.get(0).getPoint().equals(snk.getPoint())) || (snk.getPoint().x < 0 || snk.getPoint().x >= comList.length || snk.getPoint().y < 0 || snk.getPoint().y >= comList[0].length)) {

				youLose();
				return false;

			}

			dir1 = snk.getDirection();
			snk.setDirection(dir0);
			dir0 = dir1;

		}

		// Update GUI
		updateComList();
		frame.validate();
		frame.repaint();

		return true;
	}

	private void youLose() {

		System.out.println(snake.size());
		System.out.println(highscore);
		System.out.println(snake.size()>highscore);
		if (snake.size() > highscore) {
			highscore = snake.size();
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter("highScores.txt"));
				bw.write("" + highscore);
				bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			highscoreLabel.setText("Highscore: " + highscore);
			frame.validate();
			frame.repaint();
		}

		int option = JOptionPane.showOptionDialog((Component) basePane, (Object) "You Lose :-(\nwould you like to play again?", "You Lose", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, (Icon) null, (Object[]) new String[] { "Yes", "No", "Close Game" }, (Object) null);
		if (option == JOptionPane.YES_OPTION) {
			reset(false);

		} else if (option == JOptionPane.NO_OPTION) {
			return;

		} else if (option == JOptionPane.CANCEL_OPTION) {
			System.exit(0);

		}
	}

	/**
	 * reset() resets the snake if first is false, it re-initiates the inifiniteRefresh loop
	 * 
	 * @param Boolean
	 *            first
	 */
	private void reset(Boolean first) {
		// Restart the board state
		snake.clear();
		snake.add(new SnakeObject(new Point(comList.length / 2, comList[0].length / 2), Direction.NORTH));
		extendSnake();
		extendSnake();
		System.out.println("extendSnake2");
		placeFood();
		System.out.println("placeFood()");

		if (!first)
			infiniteRefresh();

		// End restarting the board state
	}

	private void infiniteRefresh() {

		// Infinite loop to update board state
		// ----------------------------------------------------------------------
		boolean refresh;
		do {
			System.out.println("While refresh");
			long startTime = System.currentTimeMillis();
			refresh = refresh();
			long endTime = System.currentTimeMillis();

			try {
				Thread.sleep((waitTime - (endTime - startTime) > 0) ? waitTime - (endTime - startTime) : 0);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		} while (refresh);
		// End infinite loop to update board state
		// ----------------------------------------------------------------------
	}

	/**
	 * placeFood() changes the background of an element of comList to green to represent a food the element selected is a random integer between 0 and (comList.length * comList[0].length)
	 */
	private void placeFood() {
		// Find random position
		int x = (int) (Math.random() * (comList.length - 1)), y = (int) (Math.random() * (comList[0].length - 1));

		// find next empty position
		boolean empty = true;
		do {
			do {
				for (SnakeObject snk : snake) {
					if (snk.getPoint().distance(x, y) == 0) {
						System.out.println((char) 27 + "[31mThis text would show up red" + (char) 27 + "[0m");
						System.out.println((char) 27 + "distance" + snk.getPoint().distance(x, y) + (char) 27 + "[0m");
						empty = false;
					}
				}
				System.out.println("empty y: |" + y + "| x: |" + x + "|");
				++y;
			} while (y < 20 && !empty);
			if (y >= 20)
				y = 1;
			++x;
		} while (x < 20 && !empty);
		x -= 1;
		y -= 1;
		System.out.println("x " + x + " y " + y);
		food.setLocation(x, y);
		System.out.println("Food Placed: x:" + x + " y: " + y);
		updateComList();

	}

	private void splash() {
		JFrame splashFrame = new JFrame();
		JPanel splashPanel = new JPanel();
		JLabel splashLabel = new JLabel("<HTML><h1>The Game of Snake</h1><br/><br/><br/><br/><br/><br/><h3>Created By:</h3> <h1>Max Niebergall</h1><hr/><h2>Contact at:</h2> <h1>Max@MaxNiebergall.com</h1></HTML>");

		splashFrame.add(splashPanel.add(splashLabel));
		splashFrame.setSize(450, 500);
		splashFrame.setResizable(false);
		splashFrame.setLocationRelativeTo(null);
		splashFrame.setVisible(true);
		splashFrame.validate();
		splashFrame.repaint();

		try {
			Thread.sleep(4000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		splashFrame.setVisible(false);
	}
	// for (x = 0; count > 0; ++x) {
	// System.out.println("Random count: |" + count + "|");
	// if (x == 19) {
	// x = 0;
	// System.out.println("x=20");
	// } else {
	// for (y = 0; (y < 19) && (count > 0); ++y) {
	// System.out.println("for y: |" + y + "| x: |" + x + "|");
	// count--;
	// }
	// }
	// }
	//
}
