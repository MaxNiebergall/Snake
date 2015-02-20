import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
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
	JPanel pane = new JPanel(new GridLayout(20,20));
	JButton comList[][] = new JButton[20][20];// X, Y
	ArrayList<SnakeObject> snake = new ArrayList<SnakeObject>();

	Snake() {

		// GUI set up
		// ---------------------------------------------------------------------
		snake.add(new SnakeObject(new Point (comList.length/2, comList[0].length/2), Direction.NORTH));
		extendSnake();
		extendSnake();
		
		for (int x = 0; x < comList.length; x++) {
			for (int y = 0; y < comList[x].length; y++) {
				comList[x][y] = new JButton();
				comList[x][y].setMinimumSize(new Dimension(10,10));
				comList[x][y].setMaximumSize(new Dimension(10,10));
			}
		}
		
		
		// add it all up
		for (int x = 0; x < comList.length; x++) {
			for (int y = 0; y < comList[x].length; y++) {
				pane.add(comList[x][y]);
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
		frame.setTitle("Snake "/* PUT VERSION STUFF HERE */);

		frame.setSize(750, 300);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.validate();
		frame.repaint();
		// End GUI set up
		// ---------------------------------------------------------------------
	}
	private void extendSnake(){
		snake.add(new SnakeObject(snake.get(snake.size()-1)));
	}
	
	private void updateComList(){
		for(int x=0; x<comList.length; x++){
			for(int y=0; y<comList[x].length; y++){
				comList[x][y].setBackground(Color.WHITE);
			}
		}
		for(SnakeObject snk : snake){
			comList[snk.getPoint().x][snk.getPoint().y].setBackground(Color.BLACK);
		}
	}
	
	//TODO Implement refresh method
	// Use timer to find the time taken by each refresh and run the next one once the timer reaches 1/60 seconds (60Hz)

}
