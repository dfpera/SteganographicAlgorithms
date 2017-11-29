import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Helper {
	/*
	 * Helper method to the function psnr which calculates the 
	 * mean square difference required for psnr values.
	 * The formula is as follows:
	 * 
	 * (1/(width*height)) * (SUM(x = 0 to width-1, SUM(y = 0 to height-1, [cover(x, y) - stego(x, y)]^2)))
	 * 
	 * @param BufferedImage - the original cover image
	 * @param BufferedImage - the stego image with the qrcode embedded
	 * @return double - the mean square difference
	 */
	private static double mse(BufferedImage cover, BufferedImage stego) {
		double mse = 0.0;
		
		// Iterate through each image and take the squared difference of each
		for (int y = 0; y < cover.getHeight(); y++) {
			for (int x = 0; x < cover.getWidth(); x++) {
				int difference = (cover.getRGB(x, y) & 0xff) - (stego.getRGB(x, y) & 0xff);
				mse += Math.pow((double) difference, 2);
			}
		}
		
		// Return the squared difference multiplied by 1/(width*height)
		mse *= 1.0/(cover.getWidth()*cover.getHeight());
		return mse;
	}
	
	/*
	 * Calculates the peak signal to noise ratio (PSNR) between a cover and stego image.
	 * The formula is as follows:
	 * 
	 * 20 * log_10(MAX) - 10 * log_10(MSE)
	 * 
	 * Where MAX is the max value of any pixel, and MSE is the mean square difference.
	 * 
	 * @param BufferedImage - the original cover image
	 * @param BufferedImage - the stego image with the qrcode embedded 
	 * @return double - the PSNR value
	 */
	public static double psnr(BufferedImage cover, BufferedImage stego) {
		return 20 * Math.log10(255) - 10 * Math.log10(mse(cover, stego));
	}
	
	/*
	 * Computes the absolute difference between two images.
	 * 
	 * @param BufferedImage - the original cover image
	 * @param BufferedImage - the stego image with the qrcode embedded 
	 * @param BufferedImage - the difference image where the brighter pixels show bigger differences
	 */
	public static BufferedImage differenceImage(BufferedImage cover, BufferedImage stego) {
		BufferedImage result = new BufferedImage(cover.getWidth(), cover.getHeight(), stego.getType());
		double power = 1.0 / 5.0;
		
		// Iterate through each image and take the absolute difference
		for (int y = 0; y < cover.getHeight(); y++) {
			for (int x = 0; x < cover.getWidth(); x++) {
//				// Compute the difference
//				double difference = (double) Math.abs((cover.getRGB(x, y) & 0xff) - (stego.getRGB(x, y) & 0xff)) / 255.0;
//				
//				// Gamma correct to brighten colors
//				difference = Math.pow(difference, power);
//				int val = (int) (difference * 255.0);
//				if (val > 255) val = 255;
				
				int val = Math.abs((cover.getRGB(x, y) & 0xff) - (stego.getRGB(x, y) & 0xff));
				val*= 70;
				if (val > 255) val = 255;
				
				// Set the resulting pixel
				result.setRGB(x, y, new Color(val, val, val).getRGB());
			}
		}
		
		return result;
	}
	
	/*
	 * 
	 */
	public static void generateBackgroundImageToFile(int width, int height, int squareSize) {
		BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		for (int y = 0; y < height; y += squareSize) {
			for (int x = 0; x < width; x += squareSize) {
				if (Math.random() < 0.5) {
					for (int i = 0; i < squareSize; i++) {
						for (int j = 0; j < squareSize; j++) {
							if (y+i < height && x+i < width) {
								result.setRGB(x+j, y+i, new Color(0, 0, 0).getRGB());
							}
						}
					}
				} else {
					for (int i = 0; i < squareSize; i++) {
						for (int j = 0; j < squareSize; j++) {
							if (y+i < height && x+i < width) {
								result.setRGB(x+j, i, new Color(255, 255, 255).getRGB());
							}
						}
					}
				}
			}
		}
		
		// Output to file
		File file = new File("OUTPUT/background-" + width + "x" + height + "-" + squareSize + ".png");
		try {
			ImageIO.write(result, "png", file);
		} catch (IOException e) {
            System.out.println("Could not create background Image: " + e.getMessage());
        }
	}
}
