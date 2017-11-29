import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.media.jai.Histogram;
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
 * The ScrollPanel class implements the scrollable UI panel,
 * which allows the user to view and compare the implementations of
 * the Least Significant Bit, Exploiting Modification Direction, 
 * and Optimal Pixel Adjustment Process algorithms. 
 */

public class ScrollPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private int windowWidth = (int)(screenSize.width * 0.9);
	private int windowHeight = (int)(screenSize.height * 2.0);
	
	private Font labelFont = new Font("Mononoki", Font.ITALIC, 13);
	private Font headingFont = new Font("Mononoki", Font.BOLD, 16);
	private Font bodyFont = new Font("Arial", Font.PLAIN, 13);
	public static boolean showModal;
    HistogramCreator histogramCreator;
	
	public ScrollPanel(JFrame frame) {
		super();	
		this.setLayout(null);
		this.setPreferredSize(new Dimension(windowWidth, windowHeight));
		
        histogramCreator = new HistogramCreator();
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		showAlgorithmList(g2);		
		
	} // end paint
	
	private void showAlgorithmList(Graphics2D g2) {
		g2.setColor(new Color(255, 255, 255));
		g2.fillRect(0, 0, windowWidth, windowHeight * 2);
		
		GradientPaint background = new GradientPaint(0, 0, new Color(200, 200, 200, 0), 0, windowHeight, Color.white);
		g2.setPaint(background);
		g2.fillRect(0, 0, windowWidth, windowHeight);
	    
	    g2.setColor(new Color(200, 200, 200));	    
	    g2.fillRect((int)(windowWidth * 0.54), (int)(windowHeight * 0.010), windowWidth / 8, windowWidth / 8);
	    g2.fillRect((int)(windowWidth * 0.54), (int)(windowHeight * 0.110), windowWidth / 8, windowWidth / 8);
	    g2.fillRect((int)(windowWidth * 0.54), (int)(windowHeight * 0.210), windowWidth / 8, windowWidth / 8);
	    g2.fillRect((int)(windowWidth * 0.54), (int)(windowHeight * 0.330), windowWidth / 8, windowWidth / 8);
	    g2.fillRect((int)(windowWidth * 0.54), (int)(windowHeight * 0.430), windowWidth / 8, windowWidth / 8);
	    g2.fillRect((int)(windowWidth * 0.54), (int)(windowHeight * 0.530), windowWidth / 8, windowWidth / 8);
	    g2.fillRect((int)(windowWidth * 0.54), (int)(windowHeight * 0.650), windowWidth / 8, windowWidth / 8);
	    g2.fillRect((int)(windowWidth * 0.54), (int)(windowHeight * 0.750), windowWidth / 8, windowWidth / 8);
	    g2.fillRect((int)(windowWidth * 0.54), (int)(windowHeight * 0.850), windowWidth / 8, windowWidth / 8);
	    g2.fillRect((int)(windowWidth * 0.68), (int)(windowHeight * 0.010), windowWidth / 8, windowWidth / 8);
	    g2.fillRect((int)(windowWidth * 0.68), (int)(windowHeight * 0.110), windowWidth / 8, windowWidth / 8);
	    g2.fillRect((int)(windowWidth * 0.68), (int)(windowHeight * 0.210), windowWidth / 8, windowWidth / 8);
	    g2.fillRect((int)(windowWidth * 0.68), (int)(windowHeight * 0.330), windowWidth / 8, windowWidth / 8);
	    g2.fillRect((int)(windowWidth * 0.68), (int)(windowHeight * 0.430), windowWidth / 8, windowWidth / 8);
	    g2.fillRect((int)(windowWidth * 0.68), (int)(windowHeight * 0.530), windowWidth / 8, windowWidth / 8);
	    g2.fillRect((int)(windowWidth * 0.68), (int)(windowHeight * 0.650), windowWidth / 8, windowWidth / 8);
	    g2.fillRect((int)(windowWidth * 0.68), (int)(windowHeight * 0.750), windowWidth / 8, windowWidth / 8);
	    g2.fillRect((int)(windowWidth * 0.68), (int)(windowHeight * 0.850), windowWidth / 8, windowWidth / 8);
	    g2.fillRect((int)(windowWidth * 0.82), (int)(windowHeight * 0.010), windowWidth / 8, windowWidth / 8);
	    g2.fillRect((int)(windowWidth * 0.82), (int)(windowHeight * 0.110), windowWidth / 8, windowWidth / 8);
	    g2.fillRect((int)(windowWidth * 0.82), (int)(windowHeight * 0.210), windowWidth / 8, windowWidth / 8);
	    g2.fillRect((int)(windowWidth * 0.82), (int)(windowHeight * 0.330), windowWidth / 8, windowWidth / 8);
	    g2.fillRect((int)(windowWidth * 0.82), (int)(windowHeight * 0.430), windowWidth / 8, windowWidth / 8);
	    g2.fillRect((int)(windowWidth * 0.82), (int)(windowHeight * 0.530), windowWidth / 8, windowWidth / 8);
	    g2.fillRect((int)(windowWidth * 0.82), (int)(windowHeight * 0.650), windowWidth / 8, windowWidth / 8);
	    g2.fillRect((int)(windowWidth * 0.82), (int)(windowHeight * 0.750), windowWidth / 8, windowWidth / 8);
	    g2.fillRect((int)(windowWidth * 0.82), (int)(windowHeight * 0.850), windowWidth / 8, windowWidth / 8);
	    
	    if (UI.showQR == true) {	
	    		    	
			g2.drawImage(StegoApp.oneBitStegoImage, (int)(windowWidth * 0.54), (int)(windowHeight * 0.01), windowWidth / 8, windowWidth / 8, this);
			g2.drawImage(StegoApp.threeBitStegoImage, (int)(windowWidth * 0.54), (int)(windowHeight * 0.11), windowWidth / 8, windowWidth / 8, this);
			g2.drawImage(StegoApp.fiveBitStegoImage, (int)(windowWidth * 0.54), (int)(windowHeight * 0.21), windowWidth / 8, windowWidth / 8, this);
			g2.drawImage(StegoApp.qrCodeOneBit, (int)(windowWidth * 0.68), (int)(windowHeight * 0.01), windowWidth / 8, windowWidth / 8, this);
			g2.drawImage(StegoApp.qrCodeThreeBit, (int)(windowWidth * 0.68), (int)(windowHeight * 0.11), windowWidth / 8, windowWidth / 8, this);
			g2.drawImage(StegoApp.qrCodeFiveBit, (int)(windowWidth * 0.68), (int)(windowHeight * 0.21), windowWidth / 8, windowWidth / 8, this);
			g2.drawImage(StegoApp.oneBitDifference, (int)(windowWidth * 0.82), (int)(windowHeight * 0.01), windowWidth / 8, windowWidth / 8, this);
			g2.drawImage(StegoApp.threeBitDifference, (int)(windowWidth * 0.82), (int)(windowHeight * 0.11), windowWidth / 8, windowWidth / 8, this);
			g2.drawImage(StegoApp.fiveBitDifference, (int)(windowWidth * 0.82), (int)(windowHeight * 0.21), windowWidth / 8, windowWidth / 8, this);
			
			g2.drawImage(StegoApp.oneBitOPAPStegoImage, (int)(windowWidth * 0.54), (int)(windowHeight * 0.330), windowWidth / 8, windowWidth / 8, this);
			g2.drawImage(StegoApp.threeBitOPAPStegoImage, (int)(windowWidth * 0.54), (int)(windowHeight * 0.430), windowWidth / 8, windowWidth / 8, this);
			g2.drawImage(StegoApp.fiveBitOPAPStegoImage, (int)(windowWidth * 0.54), (int)(windowHeight * 0.530), windowWidth / 8, windowWidth / 8, this);
			g2.drawImage(StegoApp.qrCodeOneBitOPAP, (int)(windowWidth * 0.68), (int)(windowHeight * 0.330), windowWidth / 8, windowWidth / 8, this);
			g2.drawImage(StegoApp.qrCodeThreeBitOPAP, (int)(windowWidth * 0.68), (int)(windowHeight * 0.430), windowWidth / 8, windowWidth / 8, this);
			g2.drawImage(StegoApp.qrCodeFiveBitOPAP, (int)(windowWidth * 0.68), (int)(windowHeight * 0.530), windowWidth / 8, windowWidth / 8, this);
			g2.drawImage(StegoApp.oneBitOPAPDifference, (int)(windowWidth * 0.82), (int)(windowHeight * 0.330), windowWidth / 8, windowWidth / 8, this);
			g2.drawImage(StegoApp.threeBitOPAPDifference, (int)(windowWidth * 0.82), (int)(windowHeight * 0.430), windowWidth / 8, windowWidth / 8, this);
			g2.drawImage(StegoApp.fiveBitOPAPDifference, (int)(windowWidth * 0.82), (int)(windowHeight * 0.530), windowWidth / 8, windowWidth / 8, this);

			g2.drawImage(StegoApp.oneNStegoImage, (int)(windowWidth * 0.54), (int)(windowHeight * 0.650), windowWidth / 8, windowWidth / 8, this);
			g2.drawImage(StegoApp.twoNStegoImage, (int)(windowWidth * 0.54), (int)(windowHeight * 0.750), windowWidth / 8, windowWidth / 8, this);
			g2.drawImage(StegoApp.threeNStegoImage, (int)(windowWidth * 0.54), (int)(windowHeight * 0.850), windowWidth / 8, windowWidth / 8, this);
			g2.drawImage(StegoApp.qrCodeOneN, (int)(windowWidth * 0.68), (int)(windowHeight * 0.650), windowWidth / 8, windowWidth / 8, this);
			g2.drawImage(StegoApp.qrCodeTwoN, (int)(windowWidth * 0.68), (int)(windowHeight * 0.750), windowWidth / 8, windowWidth / 8, this);
			g2.drawImage(StegoApp.qrCodeThreeN, (int)(windowWidth * 0.68), (int)(windowHeight * 0.850), windowWidth / 8, windowWidth / 8, this);
			g2.drawImage(StegoApp.oneNDifference, (int)(windowWidth * 0.82), (int)(windowHeight * 0.650), windowWidth / 8, windowWidth / 8, this);
			g2.drawImage(StegoApp.twoNDifference, (int)(windowWidth * 0.82), (int)(windowHeight * 0.750), windowWidth / 8, windowWidth / 8, this);
			g2.drawImage(StegoApp.threeNDifference, (int)(windowWidth * 0.82), (int)(windowHeight * 0.850), windowWidth / 8, windowWidth / 8, this);
			
	    }
	    
	    g2.setColor(new Color(150, 150, 150));
		g2.setFont(labelFont);
	
		g2.setFont(headingFont);
		g2.drawString("Least Significant Bit (LSB) Substitution", (int)(windowWidth * 0.05), (int)(windowHeight * 0.02));
		g2.drawString("1-bit:", (int)(windowWidth * 0.48), (int)(windowHeight * 0.052));
		g2.drawString("3-bit:", (int)(windowWidth * 0.48), (int)(windowHeight * 0.152));
		g2.drawString("5-bit:", (int)(windowWidth * 0.48), (int)(windowHeight * 0.252));
		g2.drawString("Optimal Pixel Adjustment Process (OPAP)", (int)(windowWidth * 0.05), (int)(windowHeight * 0.34));
		g2.drawString("1-bit:", (int)(windowWidth * 0.48), (int)(windowHeight * 0.372));
		g2.drawString("3-bit:", (int)(windowWidth * 0.48), (int)(windowHeight * 0.472));
		g2.drawString("5-bit:", (int)(windowWidth * 0.48), (int)(windowHeight * 0.572));
		g2.drawString("Exploiting Modification Direction (EMD)", (int)(windowWidth * 0.05), (int)(windowHeight * 0.66));
		g2.drawString("n = 1:", (int)(windowWidth * 0.48), (int)(windowHeight * 0.692));
		g2.drawString("n = 2:", (int)(windowWidth * 0.48), (int)(windowHeight * 0.792));
		g2.drawString("n = 3:", (int)(windowWidth * 0.48), (int)(windowHeight * 0.892));

		g2.setFont(bodyFont);
		int lineHeight = g2.getFontMetrics().getHeight() * 6 / 5;
		
		String lsbDescription = "Spatial domain steganographic methods such as Least Significant Bit \n"
				              + "(LSB) have high embedding capacities and easy implementation. Although, \n"
				              + "this method comes with a price of higher perceptibility and image distortion, \n"
				              + "which are undesired traits of any good steganography methods. The LSB \n"
				              + "method embeds messages within the least significant bits of the cover image \n"
				              + "and can use n of these bits (\"n-bit(s)\") to accomplish the embedding. The higher \n"
				              + "the n, the higher the embedding capacity and the lower the output image quality.";
	
		String [] linesLSB = lsbDescription.split("\n");
		for (int lineCount = 0; lineCount < linesLSB.length; lineCount++) {
		    int x = (int)(windowWidth * 0.05);
		    int y = (int)(windowHeight * 0.04) + lineCount * lineHeight;
		    String line = linesLSB[lineCount];
		    g2.drawString(line, x, y);
		}
		
		String opapDescription = "Optimal Pixel Adjustment Process (OPAP) is an extension of LSB. Bits are \n"
							   + "first embedded using LSB to the required bit depth, then OPAP is used to \n"
							   + "adjust the error introduced from LSB. For example, if an 8-bit pixel value is \n"
							   + "00010000 (decimal 16), and a secret message 1111 is to be embedded using \n"
							   + "4-bit LSB, then the new pixel value would be 00011111 (decimal 31) with an \n"
							   + "embedding error of 15. Applying OPAP would then adjust the fifth bit to from a \n"
							   + "1 to a 0 without damaging the embedded data. This leaves the OPAP pixel \n" 
							   + "value at 00001111 (decimal 15) with an embedding error of 1.";

		String [] linesOPAP = opapDescription.split("\n");
		for (int lineCount = 0; lineCount < linesOPAP.length; lineCount++) {
			int x = (int)(windowWidth * 0.05);
			int y = (int)(windowHeight * 0.36) + lineCount * lineHeight;
			String line = linesOPAP[lineCount];
			g2.drawString(line, x, y);
		}
		
		String emdDescription = "The idea behind Exploiting Modification Direction (EMD) is that n cover pixels \n"
							  + "are capable of carrying a base-(2n+1) secret digit and only one pixel value \n"
							  + "may be increased or decreased by 1 at most. EMD is a much stronger \n"
						      + "steganographic algorithm compared to LSB and OPAP. A simple breakdown \n"
							  + "of the process is as follows: 1) Convert the secret data into base-(2n+1). \n"
							  + "2) Calculate the extraction function on n cover pixels. 3) Compare the extraction \n"
							  + "function result with EMD conditions to determine which pixel is increased by 1, \n" 
							  + "decreased by 1, or no change is made.";

		String [] linesEMD = emdDescription.split("\n");
			for (int lineCount = 0; lineCount < linesEMD.length; lineCount++) {
			int x = (int)(windowWidth * 0.05);
			int y = (int)(windowHeight * 0.68) + lineCount * lineHeight;
			String line = linesEMD[lineCount];
			g2.drawString(line, x, y);
		}
        
		// underlines
		g2.setColor(new Color(200, 200, 200));
		g2.fillRect(0, (int)(windowHeight * 0.320), windowWidth, 1);
		g2.fillRect(0, (int)(windowHeight * 0.64), windowWidth, 1);
		
	} // end showMainScreen

	@Override
	public void actionPerformed(ActionEvent e) {

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
	
	
	
//    public void repaint() {
//        this.setVisible(true);
//    }
//	
//    public void addHistogram(BufferedImage image, Histogram histogram, int x, int y, int w, int h) {
//    	addHistogram(image, histogram, true, true, true, x, y, w, h);
//    }
//    
//    public void addHistogram(BufferedImage image, Histogram histogram, 
//    		                 boolean plotRed, boolean plotGreen, boolean plotBlue,
//    		                 int x, int y, int w, int h) {
//    	HistogramPanel hp = new HistogramPanel(histogram.getBins(), plotRed, plotGreen, plotBlue);
//    	hp.setBounds(x, y, w, h);
//    	this.add(hp); 	
//    }
    
    

	
} //end ScrollPanel class
