import java.awt.Color;
import java.awt.image.BufferedImage;

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
		
		// Iterate through each image and take the absolute difference
		for (int y = 0; y < cover.getHeight(); y++) {
			for (int x = 0; x < cover.getWidth(); x++) {
				// Compute the difference
				int difference = Math.abs((cover.getRGB(x, y) & 0xff) - (stego.getRGB(x, y) & 0xff));
				
				// Set the resulting pixel
				result.setRGB(x, y, new Color(difference, difference, difference).getRGB());
			}
		}
		
		return result;
	}
}
