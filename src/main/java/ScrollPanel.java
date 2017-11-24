import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 * The UI class implements the GUI, which enables the user to:
 * - Type their own secret message using the provided text box
 * - Embed their message as a QR code into the cover image using different algorithms
 * - Dynamically alter their message and view the resulting QR code and stego-images instantly
 * - (WIP) Randomize the cover image used to store the message
 * - (WIP) Enable the user to learn more about each algorithm and their 
 * 
 * @author ngmandyn
 *
 */

public class ScrollPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private int windowWidth = (int)(screenSize.width * 0.8);
	private int windowHeight = (int)(screenSize.height * 0.9);
	private static UIState state;
	
	private Font labelFont = new Font("Mononoki", Font.ITALIC, 13);
	private Font headingFont = new Font("Mononoki", Font.BOLD, 16);
	private Font bodyFont = new Font("Arial", Font.PLAIN, 13);
	
	private BufferedImage qrCode;
	
	public ScrollPanel(JFrame frame) {
		super();
		
		this.setLayout(null);
		this.setPreferredSize(new Dimension(windowWidth, windowHeight));
		state = UIState.MAIN;						
		
	} // end UI

	public void setState(UIState nextState) {
	    state = nextState;
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		showMainScreen(g2);		
	} // end paint
	
	private void showMainScreen(Graphics2D g2) {
		g2.setColor(new Color(240, 240, 240));
		g2.fillRect(0, 0, windowWidth, windowWidth);
	    
	    g2.setColor(new Color(200, 200, 200));	    
	    g2.fillRect((int)(windowWidth * 0.59), (int)(windowHeight * 0.01), windowWidth / 8, windowWidth / 8);
	    g2.fillRect((int)(windowWidth * 0.59), (int)(windowHeight * 0.23), windowWidth / 8, windowWidth / 8);
	    g2.fillRect((int)(windowWidth * 0.59), (int)(windowHeight * 0.45), windowWidth / 8, windowWidth / 8);
	    g2.fillRect((int)(windowWidth * 0.75), (int)(windowHeight * 0.01), windowWidth / 8, windowWidth / 8);
	    g2.fillRect((int)(windowWidth * 0.75), (int)(windowHeight * 0.23), windowWidth / 8, windowWidth / 8);
	    g2.fillRect((int)(windowWidth * 0.75), (int)(windowHeight * 0.45), windowWidth / 8, windowWidth / 8);
	    
	    if (UI.showQR == true) {
			g2.drawImage(qrCode, (int)(windowWidth * 0.59), (int)(windowHeight * 0.04), windowWidth / 8, windowWidth / 8, this);
			g2.drawImage(StegoApp.oneBitStegoImage, (int)(windowWidth * 0.59), (int)(windowHeight * 0.01), windowWidth / 8, windowWidth / 8, this);
			g2.drawImage(StegoApp.threeBitStegoImage, (int)(windowWidth * 0.59), (int)(windowHeight * 0.23), windowWidth / 8, windowWidth / 8, this);
			g2.drawImage(StegoApp.fiveBitStegoImage, (int)(windowWidth * 0.59), (int)(windowHeight * 0.45), windowWidth / 8, windowWidth / 8, this);
			g2.drawImage(StegoApp.qrCodeOneBit, (int)(windowWidth * 0.75), (int)(windowHeight * 0.01), windowWidth / 8, windowWidth / 8, this);
			g2.drawImage(StegoApp.qrCodeThreeBit, (int)(windowWidth * 0.75), (int)(windowHeight * 0.23), windowWidth / 8, windowWidth / 8, this);
			g2.drawImage(StegoApp.qrCodeFiveBit, (int)(windowWidth * 0.75), (int)(windowHeight * 0.45), windowWidth / 8, windowWidth / 8, this);
	    }
	    
	    g2.setColor(new Color(150, 150, 150));
		g2.setFont(labelFont);
		g2.drawString("Stego-image", (int)(windowWidth * 0.615), (int)(windowHeight * 0.21));
		g2.drawString("Stego-image", (int)(windowWidth * 0.615), (int)(windowHeight * 0.43));
		g2.drawString("Stego-image", (int)(windowWidth * 0.615), (int)(windowHeight * 0.65));
		g2.drawString("Extracted QR code", (int)(windowWidth * 0.755), (int)(windowHeight * 0.21));
		g2.drawString("Extracted QR code", (int)(windowWidth * 0.755), (int)(windowHeight * 0.43));
		g2.drawString("Extracted QR code", (int)(windowWidth * 0.755), (int)(windowHeight * 0.65));
	
		g2.setFont(headingFont);
		g2.drawString("Least Significant Bit (LSB) Substitution", (int)(windowWidth * 0.05), (int)(windowHeight * 0.04));
		g2.drawString("1-bit:", (int)(windowWidth * 0.535), (int)(windowHeight * 0.09));
		g2.drawString("3-bit:", (int)(windowWidth * 0.535), (int)(windowHeight * 0.31));
		g2.drawString("5-bit:", (int)(windowWidth * 0.535), (int)(windowHeight * 0.53));	
		
		g2.setFont(bodyFont);
		String lsbDescription = "Spatial domain steganographic methods such as Least Significant Bit (LSB) \n"
				              + "have high embedding capacities and easy implementation. Although, this \n"
				              + "method comes with a price of higher perceptibility and image distortion, \n"
				              + "which are undesired traits of any good steganography methods. The LSB \n"
				              + "method embeds messages within the least significant bits of the cover image \n"
				              + "and can use n of these bits to accomplish the embedding. The higher the \n"
				              + "n, the higher the embedding capacity and the lower the output image quality.";
	
		String [] lines = lsbDescription.split("\n");
		int lineHeight = g2.getFontMetrics().getHeight() * 6 / 5;
		for (int lineCount = 0; lineCount < lines.length; lineCount++) {
		    int x = (int)(windowWidth * 0.05);
		    int y = (int)(windowHeight * 0.08) + lineCount * lineHeight;
		    String line = lines[lineCount];
		    g2.drawString(line, x, y);
		}
	} // end showMainScreen
	
	private void showStegoInfo(Graphics2D g2) {
		g2.setColor(Color.gray);
		g2.fillRect(0, 0, windowWidth, windowHeight);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
//		if (e.getActionCommand() == "Start") {
//			setState(UIState.MAIN);
//		}			
//		if (e.getActionCommand() == "Embed") {
//			String secretMessage = textBox.getText().toString();
//			System.out.println("Embedded text: " + secretMessage);
//			showQR = true;
//		}						
		repaint();
	}
	
	/* 
	 * Create a button 
	 * 
	 * @param text - the text that appears on the button
	 * @param x - the x position of the top-left corner of the button
	 * @param y - the y position of the top-left corner of the button
	 * @param width - the width of the button
	 * @param height - the height of button
	 */
	private static JButton createButton(String text, int x, int y, int width, int height) {
		JButton button = new JButton(text);
		Color teal = new Color(22, 144, 129);
		button.setBounds(x, y, width, height);
		button.setOpaque(true);
		button.setForeground(teal); // color of text on button
		button.setBackground(Color.white); // background color of button
		Border line = new LineBorder(teal);
		Border padding = new EmptyBorder(0, 0, 0, 0);
		Border compound = new CompoundBorder(line, padding);
		button.setBorder(compound);
		button.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				button.setForeground(Color.white); // color of text on button
				button.setBackground(teal); // background color of button
				button.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			public void mouseExited(MouseEvent e) {
				button.setForeground(teal);
				button.setBackground(Color.white);
			}
		});

		return button;
	} // end createButton	
	
} //end UI class
