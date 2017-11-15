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

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class UI extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private int windowWidth = (int)(screenSize.width * 0.8);
	private int windowHeight = (int)(screenSize.height * 0.8);
	private static UIState state;
	
	private JButton startButton, embedButton;
	private JTextField textBox;
	private JLabel inputLabel;
	private Font title = new Font("Mononoki", Font.BOLD, 65);
	
	public UI(JFrame frame) {
		super();
		this.setPreferredSize(new Dimension(windowWidth, windowHeight));
		this.setLayout(null);
		state = UIState.HOME;
	}

	public void setState(UIState nextState) {
	    state = nextState;
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			
		switch (state) {
			case HOME:
				showHomeScreen(g2);
				break;
			case MAIN:
				showMainScreen(g2);
				break;
			case STEGO_INFO:
				showStegoInfo(g2);
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
		g2.setFont(title);
		g2.drawString("StegoCompare", (int)(windowWidth * 0.3), windowHeight / 2);
	        
		startButton = createButton("Start",(int)(windowWidth * 0.75), (int)(windowHeight * 0.8),
								   windowWidth / 6, windowHeight / 10);
		add(startButton);
		startButton.addActionListener(this);
	}
	
	private void showMainScreen(Graphics2D g2) {
		
		// Issue: Can't getText() from textBox. See method call in ActionPerformed below.
		textBox = new JTextField(10);
		textBox.setBounds((int)(windowWidth * 0.05), (int)(windowHeight * 0.1), windowWidth / 3, windowHeight / 15);
		add(textBox);
		
		inputLabel = new JLabel("Enter your secret message: ");
		inputLabel.setBounds((int)(windowWidth * 0.05), (int)(windowHeight * 0.05), windowWidth / 3, windowHeight / 15);
		add(inputLabel);
		
		embedButton = createButton("Embed as QR Code", (int)(windowWidth * 0.4), (int)(windowHeight * 0.08),
				   				 		   windowWidth / 6, windowHeight / 10);
		add(embedButton);
		embedButton.addActionListener(this);

		setVisible(true);
		
	    remove(startButton);
	    repaint();
	    g2.fillRect((int)(windowWidth * 0.59), (int)(windowHeight * 0.04), windowWidth / 8, windowWidth / 8);
	}
	
	private void showStegoInfo(Graphics2D g2) {
		g2.setColor(Color.gray);
		g2.fillRect(0, 0, windowWidth, windowHeight);
	}
	
    private static JButton createButton(String text, int x, int y, int width, int height) {
  	  JButton button = new JButton(text);
      button.setBounds(x, y, width, height);
  	  button.setOpaque(true);
  	  button.setForeground(new Color(15, 179, 159));
  	  Border line = new LineBorder(new Color(15, 179, 159));
  	  Border margin = new EmptyBorder(5, 15, 5, 15);
  	  Border compound = new CompoundBorder(line, margin);
  	  button.setBorder(compound);
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
          	  button.setForeground(Color.white);
          	  button.setCursor(new Cursor(Cursor.HAND_CURSOR));
          	  button.setBackground(new Color(22, 144, 129));
          	  Border line = new LineBorder(new Color(22, 144, 129));
          	  Border compound = new CompoundBorder(line, margin);
          	  button.setBorder(compound);
            }
            public void mouseExited(MouseEvent e) {
          	  button.setForeground(new Color(15, 179, 159));
          	  Border line = new LineBorder(new Color(15, 179, 159));
          	  button.setBackground(Color.white);
          	  Border compound = new CompoundBorder(line, margin);
          	  button.setBorder(compound);
            }
        });
  	  
  	  return button;
  	} // end createButton	

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "Start") {
			setState(UIState.MAIN);
		}			
		if (e.getActionCommand() == "Embed as QR Code") {
			String text = textBox.getText();
			System.out.println(text);
		}			
		repaint();
	}
	
} //end UI class
