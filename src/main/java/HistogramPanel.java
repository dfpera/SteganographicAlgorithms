
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

    HistogramPanel(Histogram histogram, int x, int y, int width, int height) {
    	this.histogram = histogram;
    	this.maxHeight = height;
        this.maxWidth = width;
        this.graphHeight = (int)(maxHeight * 0.9);
        this.graphWidth = (int)(maxWidth * 0.9);
        this.setBounds(x, y, width, height);
        this.setPreferredSize(new Dimension(maxWidth, maxHeight));
    }

    protected void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	int[] bins = histogram.getBins()[0];
        g.setColor(new Color(0, 0, 0, 125));
        g.fill3DRect(maxWidth / 20, maxHeight / 20, graphWidth, graphHeight, true);
        int initX = maxWidth / 20;
        int initY = maxHeight - (maxHeight / 20);
        int width = 2;
        int heightScale = 20;
        int widthScale = 3;
        for (int i = 0; i < 256; i++) {
            g.setColor(new Color(22, 144, 129));
            g.fill3DRect(initX + (width * i) / widthScale, initY - bins[i] / heightScale, width, bins[i] / heightScale, false);
        }
    }
}
