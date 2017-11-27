import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class ModalPanel extends UI implements ActionListener {
	private static final long serialVersionUID = 1L;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private int windowWidth = (int)(screenSize.width * 0.8);
	private int windowHeight = (int)(screenSize.height * 0.9);

	public ModalPanel(JFrame frame) {
		super(frame);
		this.setLayout(null);
		this.setPreferredSize(new Dimension(windowWidth, windowHeight));
		
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		showModal(g2);		
		
	} // end paint
	
	private void showModal(Graphics2D g2) {
		g2.setColor(new Color(255, 0, 0, 130));
		g2.fillRect(0, 0, windowWidth, windowWidth);
		g2.setColor(new Color(255, 255, 255));
		g2.fillRect(200, 200, 400, 200);
	}
	
}
