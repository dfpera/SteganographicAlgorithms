import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class UI extends JPanel {

	private static final long serialVersionUID = 1L; //default serializable UID
	private JFrame frame; //parent frame
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	int windowWidth = (int)(screenSize.width * 0.8);
	int windowHeight = (int)(screenSize.height * 0.8);
	
	public UI(JFrame frame) {
		super();
		this.frame = frame;
		this.setPreferredSize(new Dimension(windowWidth, windowHeight));

		this.setLayout(null);
	}
	
	private static UIState state = UIState.HOME;

	public static UIState getState() {
	    return state;
	}

	public static void setState(UIState nextState) {
	    state = nextState;
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		switch (getState()) {
			case HOME:
				g.setColor(Color.blue);
				g.fillRect(0, 0, windowWidth, windowHeight);
				break;
			case INTRO:
				g.setColor(Color.pink);
				g.fillRect(0, 0, windowWidth, windowHeight);
				break;
			case MAIN:
				break;
			case STEGO_INFO:
				break;
			default:
				break;
		}
		
	}
}
