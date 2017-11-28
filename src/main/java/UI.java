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

import com.google.zxing.client.j2se.MatrixToImageWriter;

/**
 * The UI class implements the main GUI, which enables the user to:
 * - Type their own secret message using the provided text box
 * - Embed their message as a QR code into the cover image using different algorithms
 * - Dynamically alter their message and view the resulting QR code and stego-images instantly
 * - (WIP) Randomize the cover image used to store the message
 * - (WIP) Enable the user to learn more about each algorithm and their 
 */

public class UI extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private int windowWidth = (int)(screenSize.width * 0.8);
	private int windowHeight = (int)(screenSize.height * 0.9);
	private static UIState state;
	
	private JButton startButton, embedButton, randomizeButton;
	private JTextField textBox;
	private JLabel inputLabel;
	private Font titleFont = new Font("Mononoki", Font.BOLD, 65);
	private Font labelFont = new Font("Mononoki", Font.ITALIC, 13);
	private Font headingFont = new Font("Mononoki", Font.BOLD, 16);
	public static boolean showQR, showModal;
	public static String secretMessage = "Enter your secret message";
	ScrollPanel scrollUI;
	JScrollPane scroll;
    HistogramCreator histogramCreator;

	
	public UI(JFrame frame) {
		super();
		
		this.setLayout(null);
		this.setPreferredSize(new Dimension(windowWidth, windowHeight));
		state = UIState.HOME;
		
		textBox = new JTextField("Enter your secret message", 10);
		add(textBox);
		textBox.setVisible(false);
		
		inputLabel = new JLabel("Type and embed a secret message:");
		add(inputLabel);
		inputLabel.setVisible(false);	
		
		startButton = createButton("Start",(int)(windowWidth * 0.75), (int)(windowHeight * 0.8), windowWidth / 6, windowHeight / 10);
		add(startButton);
		startButton.addActionListener(this);
		startButton.setVisible(true);	
		
		embedButton = createButton("Embed Text", (int)(windowWidth * 0.36), (int)(windowHeight * 0.09), windowWidth / 8, windowHeight / 12);
		embedButton.addActionListener(this);
		embedButton.setVisible(false);	
		
		randomizeButton = createButton("Randomize Image", (int)(windowWidth * 0.495), (int)(windowHeight * 0.09), windowWidth / 8, windowHeight / 12);
		randomizeButton.addActionListener(this);
		randomizeButton.setVisible(false);	

        histogramCreator = new HistogramCreator();
		
	} // end UI

	public static void setState(UIState nextState) {
	    state = nextState;
	}
	
	public static UIState getState() {
	    return state;
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		// Logic for switching between UI states, different screens shown
		switch (state) {
			case HOME: // First screen user sees
				showHomeScreen(g2);
				break;
			case MAIN: // Main screen after clicking Start
				showMainScreen(g2);
				add(embedButton);
				add(randomizeButton);
				scrollUI = new ScrollPanel(null);
				scroll = new JScrollPane(scrollUI);
		        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		        scroll.setBounds(0, (int)(windowHeight * 0.281), windowWidth, (int)(windowHeight * 0.719));
		        scroll.getVerticalScrollBar().setUnitIncrement(16);
		        scroll.setBorder(null);
				this.add(scroll);				
				break;
		}
		
	} // end paint
	
	private void showHomeScreen(Graphics2D g2) {	
		for (int i = 0; i < windowWidth; i += 10) {
			for (int j = 0; j < windowHeight; j += 10) {
				if (Math.random() < 0.5) {
					g2.setColor(new Color(220, 220, 220));
					g2.fillRect(i, j, 10, 10);
				}
			}
		}
		GradientPaint background = new GradientPaint(0, windowHeight / 4, new Color(200, 200, 200, 0), 0, windowHeight, Color.white);
		g2.setPaint(background);
		g2.fillRect(0, windowHeight / 4, windowWidth, windowHeight * 3 / 4);
		g2.setColor(new Color(22, 144, 129));
		g2.setFont(titleFont);
		g2.drawString("StegoCompare", (int)(windowWidth * 0.3), windowHeight / 2);	        
	}
	
	private void showMainScreen(Graphics2D g2) {
		
	    remove(startButton);
		textBox.setBounds((int)(windowWidth * 0.05), (int)(windowHeight * 0.1), (int)(windowWidth * 0.3), windowHeight / 15);
		inputLabel.setBounds((int)(windowWidth * 0.05), (int)(windowHeight * 0.05), windowWidth / 3, windowHeight / 15);
		embedButton.setVisible(true);
		randomizeButton.setVisible(true);
		textBox.setVisible(true);
		inputLabel.setVisible(true);
		
		g2.setColor(new Color(230, 230, 230));
		g2.fillRect(0, 0, windowWidth, windowWidth);
		
		GradientPaint background = new GradientPaint(0, (int)(windowHeight * 0.22), new Color(230, 230, 230, 255), 0, (int)(windowHeight * 0.28), Color.white);
		g2.setPaint(background);
		g2.fillRect(0, (int)(windowHeight * 0.22), windowWidth, (int)(windowHeight * 0.28));
		
	    g2.setColor(new Color(200, 200, 200)); // draw underline
	    g2.fillRect((int)(windowWidth * 0.64), (int)(windowHeight * 0.04), windowWidth / 8, windowWidth / 8);
	    
		g2.drawImage(StegoApp.coverImage, (int)(windowWidth * 0.80), (int)(windowHeight * 0.04), windowWidth / 8, windowWidth / 8, this);
		
	    if (showQR == true) {	
			LSB oneBit = new LSB(StegoApp.coverImage, 1, secretMessage, 512, 512);
			oneBit.embed();			
			g2.drawImage(StegoApp.coverImage, (int)(windowWidth * 0.80), (int)(windowHeight * 0.04), windowWidth / 8, windowWidth / 8, this);
	    	StegoApp.qrCode = MatrixToImageWriter.toBufferedImage(oneBit.getPayload());
			g2.drawImage(StegoApp.qrCode, (int)(windowWidth * 0.64), (int)(windowHeight * 0.04), windowWidth / 8, windowWidth / 8, this);
	    }
	    	    
	    g2.setColor(new Color(150, 150, 150));
		g2.setFont(headingFont);
		g2.drawString("Algorithms", (int)(windowWidth * 0.05), (int)(windowHeight * 0.26));
	    
		g2.setFont(labelFont);
		g2.drawString("QR code", (int)(windowWidth * 0.68), (int)(windowHeight * 0.24));
		g2.drawString("Cover image", (int)(windowWidth * 0.825), (int)(windowHeight * 0.24));
		
		//underline
		g2.setColor(new Color(200, 200, 200));
		g2.fillRect(0, (int)(windowHeight * 0.28), windowWidth, 1);


	} // end showMainScreen
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "Start") {
			setState(UIState.MAIN);
		}			
		if (e.getActionCommand() == "Embed Text") {
			secretMessage = textBox.getText().toString();
			System.out.println("Embedded text: " + secretMessage);
			showQR = true;
			updateImages();
		}	
		if (e.getActionCommand() == "Randomize Image") {
			try {
				StegoApp.coverImage = StegoApp.getNewImage();
			} catch (IOException e1) {
				System.out.println("Could not load new image, IOException :: " + e1.getMessage());
			}
			secretMessage = textBox.getText().toString();
			System.out.println("Embedded text: " + secretMessage);
			showQR = true;
			updateImages();
		}	
		repaint();
	}
	
	private void updateImages() {  
		
		// Display a histogram (currently shows up in wrong place for testing purposes)
//		Histogram histogramGrayscale = histogramCreator.createHistogram(StegoApp.coverImage);
//	    this.addHistogram(StegoApp.coverImage, histogramGrayscale, 0, 0, 240, 150);
		
		LSB oneBit = new LSB(StegoApp.coverImage, 1, secretMessage, 512, 512);
		oneBit.embed();
		StegoApp.oneBitStegoImage = oneBit.getStegoImage();
		StegoApp.qrCodeOneBit = MatrixToImageWriter.toBufferedImage(oneBit.extract());

		LSB threeBit = new LSB(StegoApp.coverImage, 3, secretMessage, 512, 512);
		threeBit.embed();
		StegoApp.threeBitStegoImage = threeBit.getStegoImage();
		StegoApp.qrCodeThreeBit = MatrixToImageWriter.toBufferedImage(threeBit.extract());
		
		LSB fiveBit = new LSB(StegoApp.coverImage, 5, secretMessage, 512, 512);
		fiveBit.embed();
		StegoApp.fiveBitStegoImage = fiveBit.getStegoImage();
		StegoApp.qrCodeFiveBit = MatrixToImageWriter.toBufferedImage(fiveBit.extract());
		
		EMD oneN = new EMD(StegoApp.coverImage, 1, secretMessage, 35, 35);
		oneN.embed();
		StegoApp.oneNStegoImage = oneN.getStegoImage();
		StegoApp.qrCodeOneN = MatrixToImageWriter.toBufferedImage(oneN.extract());
		
		EMD twoN = new EMD(StegoApp.coverImage, 2, secretMessage, 35, 35);
		twoN.embed();
		StegoApp.twoNStegoImage = twoN.getStegoImage();
		StegoApp.qrCodeTwoN = MatrixToImageWriter.toBufferedImage(twoN.extract());
		
		EMD threeN = new EMD(StegoApp.coverImage, 3, secretMessage, 35, 35);
		threeN.embed();
		StegoApp.threeNStegoImage = threeN.getStegoImage();
		StegoApp.qrCodeThreeN = MatrixToImageWriter.toBufferedImage(threeN.extract());
		
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
    
    public void addHistogram(BufferedImage image, Histogram histogram, int x, int y, int w, int h) {
    	addHistogram(image, histogram, true, true, true, x, y, w, h);
    }
    
    public void addHistogram(BufferedImage image, Histogram histogram, 
    		                 boolean plotRed, boolean plotGreen, boolean plotBlue,
    		                 int x, int y, int w, int h) {
    	HistogramPanel hp = new HistogramPanel(histogram.getBins(), plotRed, plotGreen, plotBlue);
    	hp.setBounds(x, y, w, h);
    	this.add(hp);
    	hp.repaint();
    	
    }
	
} //end UI class
