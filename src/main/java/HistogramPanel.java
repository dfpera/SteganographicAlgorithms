
import javax.swing.*;
import java.awt.*;

public class HistogramPanel extends JPanel {
    private int[] redBins;
    private int[] greenBins;
    private int[] blueBins;
    private boolean plotRed = false;
    private boolean plotGreen = false;
    private boolean plotBlue = false;
    final int maxHeight = 150;
    final int maxWidth = 250;

    HistogramPanel(int[][] bins, boolean plotRed, boolean plotGreen, boolean plotBlue) {
        redBins = bins[0];
        greenBins = bins[0];
        blueBins = bins[0];
        this.plotRed = plotRed;
        this.plotGreen = plotGreen;
        this.plotBlue = plotBlue;
        this.setPreferredSize(new Dimension(maxWidth, maxHeight));
    }
    
    public void repaint() {
    	this.setVisible(true);
    }

    protected void paintComponent(Graphics g) {
        g.setColor(Color.white);
        g.fill3DRect(0, 0, maxWidth, maxHeight, true);
        int initX = 40;
        int initY = maxHeight;
        int width = 2;
        int heightScale = 20;
        int widthScale = 3;
        int opacity = 70;
        for (int i = 0; i < 256; i++) {
            //draw red bars
            if (plotRed) {
                g.setColor(new Color(255, 0, 0, opacity));
                g.fill3DRect(initX + (width * i) / widthScale, initY - redBins[i] / heightScale, width, redBins[i] / heightScale, true);
            }

            //draw green bars
            if (plotGreen) {
                g.setColor(new Color(0, 255, 0, opacity));
                g.fill3DRect(initX + (width * i) / widthScale, initY - greenBins[i] / heightScale, width, greenBins[i] / heightScale, true);
            }

            //draw blue bars
            if (plotBlue) {
                g.setColor(new Color(0, 0, 255, opacity));
                g.fill3DRect(initX + (width * i) / widthScale, initY - blueBins[i] / heightScale, width, blueBins[i] / heightScale, true);
            }
        }
    }
}
