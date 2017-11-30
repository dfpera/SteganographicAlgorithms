import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.media.jai.Histogram;
import javax.swing.JFrame;
import javax.swing.JPanel;


/**
 * The ScrollPanel class implements the scrollable UI panel,
 * which allows the user to view and compare the implementations of
 * the Least Significant Bit, Exploiting Modification Direction, 
 * and Optimal Pixel Adjustment Process algorithms. 
 */

public class ScrollPanel extends JPanel {
	private static final long serialVersionUID = 1733787764443127771L;
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private int windowWidth = (int)(screenSize.width * 0.9);
	private int windowHeight = (int)(screenSize.height * 2.0);
	
	private Font headingFont = new Font("Mononoki", Font.BOLD, 16);
	private Font bodyFont = new Font("Arial", Font.PLAIN, 13);
	private Font labelFont = new Font("Mononoki", Font.ITALIC, 13);
	public static boolean showModal;
	
	HistogramPanel[] histLSB;
	HistogramPanel[] histOPAP;
	HistogramPanel[] histEMD;
	
	public ScrollPanel(JFrame frame) {
		super();	
		this.setLayout(null);
		this.setPreferredSize(new Dimension(windowWidth, windowHeight));
				
		// Create Histograms
		histLSB = new HistogramPanel[] {
				addHistogram(StegoApp.coverImage, (int)(windowWidth * 0.05), (int)(windowHeight * 0.125), (int)(windowWidth * 0.19), (int)(windowWidth * 0.1)),
				addHistogram(StegoApp.coverImage, (int)(windowWidth * 0.26), (int)(windowHeight * 0.125), (int)(windowWidth * 0.19), (int)(windowWidth * 0.1)),
				addHistogram(StegoApp.coverImage, (int)(windowWidth * 0.05), (int)(windowHeight * 0.222), (int)(windowWidth * 0.19), (int)(windowWidth * 0.1))
		};
		histOPAP = new HistogramPanel[] {
				addHistogram(StegoApp.coverImage, (int)(windowWidth * 0.05), (int)(windowHeight * 0.455), (int)(windowWidth * 0.19), (int)(windowWidth * 0.1)),
				addHistogram(StegoApp.coverImage, (int)(windowWidth * 0.26), (int)(windowHeight * 0.455), (int)(windowWidth * 0.19), (int)(windowWidth * 0.1)),
				addHistogram(StegoApp.coverImage, (int)(windowWidth * 0.05), (int)(windowHeight * 0.545), (int)(windowWidth * 0.19), (int)(windowWidth * 0.1))
		};
		histEMD = new HistogramPanel[] {
				addHistogram(StegoApp.coverImage, (int)(windowWidth * 0.05), (int)(windowHeight * 0.780), (int)(windowWidth * 0.19), (int)(windowWidth * 0.1)),
				addHistogram(StegoApp.coverImage, (int)(windowWidth * 0.26), (int)(windowHeight * 0.780), (int)(windowWidth * 0.19), (int)(windowWidth * 0.1)),
				addHistogram(StegoApp.coverImage, (int)(windowWidth * 0.05), (int)(windowHeight * 0.877), (int)(windowWidth * 0.19), (int)(windowWidth * 0.1))
		};
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		// Draw all ScrollPanel graphics
		showAlgorithmList(g2);		
		
	} // end paint
	
	private void showAlgorithmList(Graphics2D g2) {
		// Set background of the scrolling panel
		g2.setColor(new Color(255, 255, 255));
		g2.fillRect(0, 0, windowWidth, windowHeight * 2);
		
		// Setting the front color
		g2.setColor(new Color(150, 150, 150));	
		
		// Drawing headings
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

		// Change styling for descriptions
		g2.setFont(bodyFont);
		int lineHeight = g2.getFontMetrics().getHeight() * 6 / 5;
		
		String lsbDescription = "Spatial domain steganographic methods such as Least Significant Bit \n"
				              + "(LSB) have high embedding capacities and easy implementation. Although, \n"
				              + "this method comes with a price of higher perceptibility and image distortion, \n"
				              + "which are undesired traits of any good steganography methods. The LSB \n"
				              + "method embeds messages within the least significant bits of the cover image \n"
				              + "and can use n of these bits (\"n-bit(s)\") to accomplish the embedding. The higher \n"
				              + "the n, the higher the embedding capacity and the lower the output image quality.";

		// Setting line height and breaks
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

		// Setting line height and breaks
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

		// Setting line height and breaks
		String [] linesEMD = emdDescription.split("\n");
			for (int lineCount = 0; lineCount < linesEMD.length; lineCount++) {
			int x = (int)(windowWidth * 0.05);
			int y = (int)(windowHeight * 0.68) + lineCount * lineHeight;
			String line = linesEMD[lineCount];
			g2.drawString(line, x, y);
		}
        
		// Underlines
		g2.setColor(new Color(200, 200, 200));
		g2.fillRect(0, (int)(windowHeight * 0.320), windowWidth, 1);
		g2.fillRect(0, (int)(windowHeight * 0.64), windowWidth, 1);
		
		// Histogram titles
		g2.setColor(new Color(150, 150, 150));
		g2.setFont(labelFont);
		
		// Draw labels for the histograms
		g2.drawString("Histogram: Cover image", (int)(windowWidth * 0.075), (int)(windowHeight * 0.207));
		g2.drawString("Histogram: After 3-bit LSB", (int)(windowWidth * 0.275), (int)(windowHeight * 0.207));
		g2.drawString("Histogram: Difference image", (int)(windowWidth * 0.063), (int)(windowHeight * 0.304));
		
		g2.drawString("Histogram: Cover image", (int)(windowWidth * 0.075), (int)(windowHeight * 0.536));
		g2.drawString("Histogram: After 3-bit OPAP", (int)(windowWidth * 0.275), (int)(windowHeight * 0.536));
		g2.drawString("Histogram: Difference image", (int)(windowWidth * 0.063), (int)(windowHeight * 0.625));
		
		g2.drawString("Histogram: Cover image", (int)(windowWidth * 0.075), (int)(windowHeight * 0.862));
		g2.drawString("Histogram: After EMD (n=2)", (int)(windowWidth * 0.275), (int)(windowHeight * 0.862));
		g2.drawString("Histogram: Difference image", (int)(windowWidth * 0.063), (int)(windowHeight * 0.959));
		
	    if (!UI.showQR) {
	    	// Placeholder Image Squares
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
	    	
	    	
	    } else {
	    	// Draw images for LSB
			g2.drawImage(StegoApp.oneBitStegoImage, (int)(windowWidth * 0.54), (int)(windowHeight * 0.01), windowWidth / 8, windowWidth / 8, this);
			g2.drawImage(StegoApp.threeBitStegoImage, (int)(windowWidth * 0.54), (int)(windowHeight * 0.11), windowWidth / 8, windowWidth / 8, this);
			g2.drawImage(StegoApp.fiveBitStegoImage, (int)(windowWidth * 0.54), (int)(windowHeight * 0.21), windowWidth / 8, windowWidth / 8, this);
			g2.drawImage(StegoApp.qrCodeOneBit, (int)(windowWidth * 0.68), (int)(windowHeight * 0.01), windowWidth / 8, windowWidth / 8, this);
			g2.drawImage(StegoApp.qrCodeThreeBit, (int)(windowWidth * 0.68), (int)(windowHeight * 0.11), windowWidth / 8, windowWidth / 8, this);
			g2.drawImage(StegoApp.qrCodeFiveBit, (int)(windowWidth * 0.68), (int)(windowHeight * 0.21), windowWidth / 8, windowWidth / 8, this);
			g2.drawImage(StegoApp.oneBitDifference, (int)(windowWidth * 0.82), (int)(windowHeight * 0.01), windowWidth / 8, windowWidth / 8, this);
			g2.drawImage(StegoApp.threeBitDifference, (int)(windowWidth * 0.82), (int)(windowHeight * 0.11), windowWidth / 8, windowWidth / 8, this);
			g2.drawImage(StegoApp.fiveBitDifference, (int)(windowWidth * 0.82), (int)(windowHeight * 0.21), windowWidth / 8, windowWidth / 8, this);
			
			// Update LSB Histograms
			updateHistogram(histLSB[0], StegoApp.coverImage);
			updateHistogram(histLSB[1], StegoApp.histoStegoLSB);
			updateHistogram(histLSB[2], StegoApp.histoDifLSB);
			
			// Draw images for OPAP
			g2.drawImage(StegoApp.oneBitOPAPStegoImage, (int)(windowWidth * 0.54), (int)(windowHeight * 0.330), windowWidth / 8, windowWidth / 8, this);
			g2.drawImage(StegoApp.threeBitOPAPStegoImage, (int)(windowWidth * 0.54), (int)(windowHeight * 0.430), windowWidth / 8, windowWidth / 8, this);
			g2.drawImage(StegoApp.fiveBitOPAPStegoImage, (int)(windowWidth * 0.54), (int)(windowHeight * 0.530), windowWidth / 8, windowWidth / 8, this);
			g2.drawImage(StegoApp.qrCodeOneBitOPAP, (int)(windowWidth * 0.68), (int)(windowHeight * 0.330), windowWidth / 8, windowWidth / 8, this);
			g2.drawImage(StegoApp.qrCodeThreeBitOPAP, (int)(windowWidth * 0.68), (int)(windowHeight * 0.430), windowWidth / 8, windowWidth / 8, this);
			g2.drawImage(StegoApp.qrCodeFiveBitOPAP, (int)(windowWidth * 0.68), (int)(windowHeight * 0.530), windowWidth / 8, windowWidth / 8, this);
			g2.drawImage(StegoApp.oneBitOPAPDifference, (int)(windowWidth * 0.82), (int)(windowHeight * 0.330), windowWidth / 8, windowWidth / 8, this);
			g2.drawImage(StegoApp.threeBitOPAPDifference, (int)(windowWidth * 0.82), (int)(windowHeight * 0.430), windowWidth / 8, windowWidth / 8, this);
			g2.drawImage(StegoApp.fiveBitOPAPDifference, (int)(windowWidth * 0.82), (int)(windowHeight * 0.530), windowWidth / 8, windowWidth / 8, this);
			
			// Update OPAP Histograms
			updateHistogram(histOPAP[0], StegoApp.coverImage);
			updateHistogram(histOPAP[1], StegoApp.histoStegoOPAP);
			updateHistogram(histOPAP[2], StegoApp.histoDifOPAP);

			// Draw images for EMD
			g2.drawImage(StegoApp.oneNStegoImage, (int)(windowWidth * 0.54), (int)(windowHeight * 0.650), windowWidth / 8, windowWidth / 8, this);
			g2.drawImage(StegoApp.twoNStegoImage, (int)(windowWidth * 0.54), (int)(windowHeight * 0.750), windowWidth / 8, windowWidth / 8, this);
			g2.drawImage(StegoApp.threeNStegoImage, (int)(windowWidth * 0.54), (int)(windowHeight * 0.850), windowWidth / 8, windowWidth / 8, this);
			g2.drawImage(StegoApp.qrCodeOneN, (int)(windowWidth * 0.68), (int)(windowHeight * 0.650), windowWidth / 8, windowWidth / 8, this);
			g2.drawImage(StegoApp.qrCodeTwoN, (int)(windowWidth * 0.68), (int)(windowHeight * 0.750), windowWidth / 8, windowWidth / 8, this);
			g2.drawImage(StegoApp.qrCodeThreeN, (int)(windowWidth * 0.68), (int)(windowHeight * 0.850), windowWidth / 8, windowWidth / 8, this);
			g2.drawImage(StegoApp.oneNDifference, (int)(windowWidth * 0.82), (int)(windowHeight * 0.650), windowWidth / 8, windowWidth / 8, this);
			g2.drawImage(StegoApp.twoNDifference, (int)(windowWidth * 0.82), (int)(windowHeight * 0.750), windowWidth / 8, windowWidth / 8, this);
			g2.drawImage(StegoApp.threeNDifference, (int)(windowWidth * 0.82), (int)(windowHeight * 0.850), windowWidth / 8, windowWidth / 8, this);
			
			// Update EMD Histograms
			updateHistogram(histEMD[0], StegoApp.coverImage);
			updateHistogram(histEMD[1], StegoApp.histoStegoEMD);
			updateHistogram(histEMD[2], StegoApp.histoDifEMD);
	    } 
	} // end showMainScreen
    
	/*
	 * TODO: Add comment
	 */
    public HistogramPanel addHistogram(BufferedImage image, int x, int y, int width, int height) {
    	// Create histogram
    	Histogram histLSB = new Histogram(new int[] {256}, new double[] {0.0}, new double[] {256.0});
		histLSB.countPixels(image.getRaster(), null, 0, 0, 1, 1);
		
		// Create HistogramPanel
		HistogramPanel hp = new HistogramPanel(histLSB, x, y, width, height);
		this.add(hp);
		
		// Return HistogramPanel to be saved
		return hp;
    }
    
    /*
     * TODO: Add comment
     */
    public void updateHistogram(HistogramPanel hp, BufferedImage image) {
    	hp.histogram.clearHistogram();
    	hp.histogram.countPixels(image.getRaster(), null, 0, 0, 1, 1);
    	((HistogramPanel) hp).repaint();
    }
} //end ScrollPanel class
