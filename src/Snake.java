import java.awt.Component;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author(
 * 	name= "Max Niebergall"
 * 	date= "Febuary 19th 2015"
 * )
 * @version v0.0.1
 *
 */
public class Snake {

	public static void main(String[] args) {
		new Snake();
	}
	
	
	//GUI Object Declarations
	JFrame frame = new JFrame();
	JPanel pane = new JPanel();
	Component comList[][] = new Component[20][20];
	

	Snake() {
		
		//GUI set up ---------------------------------------------------------------------
		
		
		
		
		
		// add it all up
		frame.add(pane);

		// frame window closer
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		// frame settings
		frame.setTitle("Snake "/* PUT VERSION STUFF HERE*/);
		
		frame.setSize(610, 450);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.validate();
		frame.repaint();
		//End GUI set up ---------------------------------------------------------------------
	}
	
	//https://github.com/MaxNiebergall/Snake.git
	
	

}
