
import javax.media.jai.Histogram;
import javax.swing.*;
import java.awt.*;

public class HistogramPanel extends JPanel {
	private static final long serialVersionUID = -3769277940852635936L;
	public Histogram histogram;
    final int maxHeight;
    final int maxWidth;
    final int graphHeight;
    final int graphWidth;

    /*
     * TODO: WRITE COMMENT
     */
    HistogramPanel(Histogram histogram, int x, int y, int width, int height) {
    	this.histogram = histogram;
    	this.maxHeight = height;
        this.maxWidth = width;
        this.graphHeight = (int)(maxHeight * 0.9);
        this.graphWidth = (int)(maxWidth * 0.9);
        this.setBounds(x, y, width, height);
        this.setPreferredSize(new Dimension(maxWidth, maxHeight));
    }

    /*
     * (non-Javadoc)
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     * TODO: WRITE COMMENT
     */
    protected void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	int[] bins = histogram.getBins()[0];
        int initX = maxWidth / 20;
        int initY = maxHeight - (maxHeight / 20);
        int width = graphWidth / 256;
        
        // Get max value
        double heightScale = 0;
        for (int i = 1; i < bins.length; i++) {
            if (bins[i] > heightScale) {
                heightScale = bins[i];
            }
        }
        
        // Generate histogram graphic
        for (int i = 0; i < 256; i++) {
            g.setColor(new Color(22, 144, 129));
            double heightRatio = (double) bins[i] / heightScale;
            g.fill3DRect(
            		initX + (width * i), 
            		initY - (int)(heightRatio * graphHeight), 
            		width, 
            		(int)(heightRatio * graphHeight), 
            		false
            		);
        }
    }
}
