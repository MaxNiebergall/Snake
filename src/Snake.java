import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

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

	// GUI Object Declarations
	JFrame frame = new JFrame();
	JPanel pane = new JPanel(new GridLayout(20, 20));
	Point food = new Point();
	JButton comList[][] = new JButton[20][20];// X, Y

	ArrayList<SnakeObject> snake = new ArrayList<SnakeObject>();

	Snake() {		

		for (int x = 0; x < comList.length; x++) {
			for (int y = 0; y < comList[x].length; y++) {
				comList[x][y] = new JButton();
				comList[x][y].setMinimumSize(new Dimension(10, 10));
				comList[x][y].setMaximumSize(new Dimension(10, 10));
			}
		}
		System.out.println("comList set up");
		
		reset(true);
		System.out.println("reset(true)");

		// HERE ARE THE KEY BINDINGS
		pane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_W, 0), "up");
		pane.getActionMap().put("up", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Up");
				snake.get(0).setDirection(Direction.NORTH);
			}
		});
		pane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), "left");
		pane.getActionMap().put("left", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Left");
				snake.get(0).setDirection(Direction.WEST);
			}
		});
		pane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), "right");
		pane.getActionMap().put("right", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Right");
				snake.get(0).setDirection(Direction.EAST);
			}
		});
		pane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), "down");
		pane.getActionMap().put("down", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Down");
				snake.get(0).setDirection(Direction.SOUTH);
			}
		});
		pane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke("UP"), "UP");
		pane.getActionMap().put("UP", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Up-arrow");
				snake.get(0).setDirection(Direction.NORTH);
			}
		});
		pane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke("LEFT"), "LEFT");
		pane.getActionMap().put("LEFT", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Left-arrow");
				snake.get(0).setDirection(Direction.WEST);
			}
		});
		pane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke("RIGHT"), "RIGHT");
		pane.getActionMap().put("RIGHT", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Right-arrow");
				snake.get(0).setDirection(Direction.EAST);
			}
		});
		pane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke("DOWN"), "DOWN");
		pane.getActionMap().put("DOWN", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Down-arrow");
				snake.get(0).setDirection(Direction.SOUTH);
			}
		});
		// END OF KEY BINDINGS

		// add it all up
		for (int x = 0; x < comList.length; x++) {
			for (int y = 0; y < comList[x].length; y++) {
				pane.add(comList[y][x]);
			}
		}
		frame.add(pane);

		// frame window closer
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		// frame settings
		frame.setTitle("Max Nieberall's Snake Game v0.1.01 Beta"/*
														 * TODO PUT VERSION
														 * STUFF HERE
														 */);

		frame.setSize(750, 700);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.validate();
		frame.repaint();
		// End GUI set up
		// ---------------------------------------------------------------------

		infiniteRefresh();
	}

	private void extendSnake() {
		snake.add(new SnakeObject(snake.get(snake.size() - 1)));
	}

	private void updateComList() {
		System.out.println("updateComList");
		for (int x = 0; x < comList.length; x++) {
			for (int y = 0; y < comList[x].length; y++) {
				comList[x][y].setBackground(Color.WHITE);
			}
		}
		for (SnakeObject snk : snake) {
			comList[snk.getPoint().x][snk.getPoint().y]
					.setBackground(Color.BLACK);
		}
		comList[(int) food.getX()][(int) food.getY()]
				.setBackground(Color.GREEN);
	}

	private boolean refresh() {
		System.out.println("Refresh()");
		// Update Snake
		Direction dir0 = snake.get(0).getDirection(), dir1;
		if ((snake.get(0).getPoint().x
				+ ((snake.get(0).getDirection() == Direction.EAST || snake.get(
						0).getDirection() == Direction.WEST) ? snake.get(0)
						.direction() : 0) < 0
				|| snake.get(0).getPoint().x
						+ ((snake.get(0).getDirection() == Direction.EAST || snake
								.get(0).getDirection() == Direction.WEST) ? snake
								.get(0).direction() : 0) >= comList.length
				|| snake.get(0).getPoint().y
						+ ((snake.get(0).getDirection() == Direction.NORTH || snake
								.get(0).getDirection() == Direction.SOUTH) ? snake
								.get(0).direction() : 0) < 0 || snake.get(0)
				.getPoint().y
				+ ((snake.get(0).getDirection() == Direction.NORTH || snake
						.get(0).getDirection() == Direction.SOUTH) ? snake.get(
						0).direction() : 0) >= comList[0].length)) {

			youLose();
			return false;

		}
		System.out.println("snake.get(0).point: "
				+ snake.get(0).getPoint().toString());
		if (comList[snake.get(0).getPoint().x
				+ ((snake.get(0).getDirection() == Direction.EAST || snake.get(
						0).getDirection() == Direction.WEST) ? snake.get(0)
						.direction() : 0)][snake.get(0).getPoint().y
				+ ((snake.get(0).getDirection() == Direction.NORTH || snake
						.get(0).getDirection() == Direction.SOUTH) ? snake.get(
						0).direction() : 0)].getBackground() == Color.GREEN) {
			extendSnake();
			placeFood();
		}
		for (SnakeObject snk : snake) {

			System.out.println("Snk forward");
			snk.forward();

			System.out.println(" SnkPoint: " + snk.getPoint() + " SnakePoint: "
					+ snake.get(0).getPoint());// TODO Delete this line when the
												// problem is resolved
			System.out
					.println(snk != snake.get(0)
							&& (snake.get(0).getPoint().equals(snk.getPoint()) || (snk
									.getPoint().x < 0
									|| snk.getPoint().x >= comList.length
									|| snk.getPoint().y < 0 || snk.getPoint().y >= comList[0].length)));
			if ((snk != snake.get(0) && snake.get(0).getPoint()
					.equals(snk.getPoint()))
					|| (snk.getPoint().x < 0
							|| snk.getPoint().x >= comList.length
							|| snk.getPoint().y < 0 || snk.getPoint().y >= comList[0].length)) {

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
		int option = JOptionPane.showConfirmDialog(pane,
				"You Lose :-(\nwould you like to play again?");
		if (option == JOptionPane.YES_OPTION) {
			reset(false);

		} else {
			System.exit(0);
		}
	}

	/**
	 * reset() resets the snake if first is false, it re-initiates the
	 * inifiniteRefresh loop
	 * 
	 * @param Boolean
	 *            first
	 */
	private void reset(Boolean first) {
		// Restart the board state
		snake.clear();
		snake.add(new SnakeObject(new Point(comList.length / 2,
				comList[0].length / 2), Direction.NORTH));
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
			System.out.println("While True");
			long startTime = System.currentTimeMillis();
			refresh = refresh();
			long endTime = System.currentTimeMillis(); // 166 milliseconds is
														// 1/60 seconds, or 60Hz
			try {
				Thread.sleep((166 - (endTime - startTime) > 0) ? 166 - (endTime - startTime)
						: 0);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		} while (refresh);
		// End infinite loop to update board state
		// ----------------------------------------------------------------------
	}

	/**
	 * placeFood() changes the background of an element of comList to green to
	 * represent a food the element selected is a random integer between 0 and
	 * (comList.length * comList[0].length)
	 */
	private void placeFood() {
		int x = 0, y = 0, count = (int) (Math.random() * (comList.length * comList[0].length));
		for (x = 0; count > 0; ++x) {
			System.out.println("Random count: |" + count + "|");
			if (x == 19) {
				x = 0;
				System.out.println("x=20");
			} else {
				for (y = 0; (y < 19) && (count > 0); ++y) {
					System.out.println("for y: |" + y + "| x: |" + x + "|");
					if (comList[x][y].getBackground() != Color.BLACK)
						count--;
				}
			}
		}

		food.move(x, y);
		System.out.println("Food Placed: x:" + x + " y: " + y);
		updateComList();

	}

}
