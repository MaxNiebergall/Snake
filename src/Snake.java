import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import com.sun.glass.events.KeyEvent;

/**
 * @author( name= "Max Niebergall" date= "Febuary 19th 2015" )
 * @version v0.0.1
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
	JButton comList[][] = new JButton[20][20];// X, Y
	ArrayList<SnakeObject> snake = new ArrayList<SnakeObject>();

	Snake() {

		// GUI set up
		// ---------------------------------------------------------------------
		snake.add(new SnakeObject(new Point(comList.length / 2,
				comList[0].length / 2), Direction.NORTH));
		extendSnake();
		extendSnake();

		for (int x = 0; x < comList.length; x++) {
			for (int y = 0; y < comList[x].length; y++) {
				comList[x][y] = new JButton();
				comList[x][y].setMinimumSize(new Dimension(10, 10));
				comList[x][y].setMaximumSize(new Dimension(10, 10));
			}
		}

		// HERE ARE THE KEY BINDINGS
		pane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_W, 0), "forward");
		pane.getActionMap().put("forward", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Forward");

				for (SnakeObject snk : snake) {
					snk.setDirection(Direction.NORTH);
				}

			}
		});
		pane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), "left");
		pane.getActionMap().put("left", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Left");
				for (SnakeObject snk : snake) {
					snk.setDirection(Direction.WEST);
				}

			}
		});
		pane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), "right");
		pane.getActionMap().put("right", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Right");
				for (SnakeObject snk : snake) {
					snk.setDirection(Direction.EAST);
				}
			}
		});
		pane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), "down");
		pane.getActionMap().put("down", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Down");
				for (SnakeObject snk : snake) {
					snk.setDirection(Direction.SOUTH);
				}
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
		frame.setTitle("Snake "/* TODO PUT VERSION STUFF HERE */);

		frame.setSize(750, 700);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.validate();
		frame.repaint();
		// End GUI set up
		// ---------------------------------------------------------------------

		// Infinite loop to update board state
		// ----------------------------------------------------------------------
		while (true) {
			System.out.println("While True");
			long startTime = System.currentTimeMillis();
			refresh();
			long endTime = System.currentTimeMillis();
			try {
				Thread.sleep(166 - (endTime - startTime));
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		// End infinite loop to update board state
		// ----------------------------------------------------------------------
	}

	private void extendSnake() {
		snake.add(new SnakeObject(snake.get(snake.size() - 1)));
	}

	protected void updateComList() {
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
	}

	// TODO Implement refresh method
	// Use timer to find the time taken by each refresh and run the next one
	// once the timer reaches 1/60 seconds (60Hz)
	// Attempt Use of additional thread

	private void refresh() {
		// Update Snake
		for (SnakeObject snk : snake) {
			System.out.println("Snk forward");
			snk.forward();
		}

		// Update GUI
		updateComList();
		frame.validate();
		frame.repaint();
	}

}
