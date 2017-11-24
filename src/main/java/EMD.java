import java.awt.Color;
import java.awt.image.BufferedImage;

import com.google.zxing.common.BitMatrix;

public class EMD extends Steganography {
	private int n; // Used to calculate the (2n + 1)-ary digit
	private int position; // Used to determine the position in the cover image when embedding or extracting

	public EMD(BufferedImage coverImage, int n, String message, int payloadWidth, int payloadHeight) {
		super(coverImage, message, payloadWidth, payloadHeight);
		
		// Make sure the n is not negative or zero
		if (n < 1) {
			this.n = 1;
		} else {
			this.n = n;
		}
		
		this.position = 0;
	}

	@Override
	public void embed() {
		// Reset position property
		position = 0;
		
		// Step 1 convert payload to (2n+1)-ary digit
		String digits = bitMatrixToBase2NPlus1();
		int digitPosition = 0;
		
		// Iterate through cover image
		for (position = 0; position < (getCoverImage().getHeight() * getCoverImage().getWidth()); position += n) {
			
			// Step 2 calculate extraction function
			int efNum = extractionFu();
			
			// Step 3 shift bit according to result from extraction function
			embedDigit(digits, digitPosition);
			digitPosition++;
			
			// Reset digit position if all digits have been embedded
			if (digitPosition >= digits.length()) {
				digitPosition = 0;
			}
		}
	}

	@Override
	public BitMatrix extract() {
		// TODO implement extract
		return null;
	}
	
	/*
	 * Converts the BitMatrix payload into a binary string by scanning 
	 * the payload from left to right top to bottom. Then converts the 
	 * binary string into a base (2n+1)-ary string.
	 * 
	 * @return String - (2n+1)-ary string representation of a number.
	 */
	private String bitMatrixToBase2NPlus1() {
		String binaryNum = "";
		BitMatrix payload = getPayload();
		for (int y = 0; y < payload.getHeight(); y++) {
			for (int x = 0; x < payload.getWidth(); x++) {
				binaryNum += payload.get(x, y) ? "1" : "0";
			}
		}
		return Integer.toString(Integer.parseInt(binaryNum, 2), 2*n+1);
	}
	
	/*
	 * Calculates the extraction function for EMD where
	 * n = number of cover pixels
	 * p_i = a cover pixel
	 * 
	 * f(p_1, p_2, ... , p_n) = SUM(i=1 to n, p_i * i) mod (2n+1)
	 * 
	 * @return int - SUM(i=1 to n, p_i * i) mod (2n+1)
	 */
	private int extractionFu() {
		// Initialize sum
		int sum = 0;
		
		// Calculate sum
		for (int i = 0; i < n; i++) {
			// Get color pixel
			int colorChannel = this.getCoverImage().getRGB((position+i) % getCoverImage().getWidth(), (position+i) / getCoverImage().getWidth()) & 0xff;
			
			// Add to the sum
			sum += colorChannel * (n+1);
		}
		
		// Return the sum modulus (2n+1)
		return sum % (2*n+1);
	}
	
	/*
	 * Determine which pixel to change by at most 1 by using the following conditions:
	 * s = (2n+1)-ary secret digit
	 * f(p_1, p_2, ... , p_n) = extraction function result
	 * r is the result used to determine how we modify the pixels
	 * 
	 * If (s-f(p_1, p_2, ... , p_n)) mod (2n+1) = r and r = 0 then no modification is needed
	 * because extraction function f can decrypt the correct secret data.
	 * 
	 * Else if (s-f(p_1, p_2, ... , p_n)) mod (2n+1) = r and r < floor((2n+1)/2)), then
	 * increase the pixel value p_r by 1.
	 * 
	 * Else if (s-f(p_1, p_2, ... , p_n)) mod (2n+1) = r and r > floor((2n+1)/2)), then
	 * decrease the pixel value p_((2n+1)-r) by 1.
	 * 
	 * This function will both transfer the unneeded pixels from cover image to stego image
	 * and adjust the one pixel that will be modified during the transfer of n cover pixels.
	 * 
	 * @param int - the position of the digit that needs to be embedded
	 */
	private void embedDigit(String secretDigits, int digitPosition) {
		// Calculate r
		int r = (Integer.parseInt(Character.toString(secretDigits.charAt(digitPosition)), 2*n+1));
		
		// Compare r to condition
		int condition = (2*n + 1) / 2;
		boolean isIncrease = false;
		int coverPixel;
		
		// If smaller increase pixel r
		if (r < condition) {
			isIncrease = true;
			coverPixel = r - 1;
		// If bigger decrease pixel (2n+1)-r
		} else if (r > condition) {
			isIncrease = false;
			coverPixel = (2*n+1) - r - 1;
		// If 0 do nothing 
		} else {
			coverPixel = -1;
		}
			
		// Transfer pixels and modify pixel that requires modifying
		for (int i = 0; i < n; i++) {
			// Get pixel
			int colorChannel = this.getCoverImage().getRGB((position+i) % getCoverImage().getWidth(), (position+i) / getCoverImage().getWidth()) & 0xff;
			
			// If pixel matches condition result increase or decrease
			if (i == coverPixel) {
				if (isIncrease) {
					colorChannel++;
				} else {
					colorChannel--;
				}
			}
			
			// Set the pixel value to the stegoImage
            this.getStegoImage().setRGB((position+i) % getCoverImage().getWidth(), (position+i) / getCoverImage().getWidth(), new Color(colorChannel, colorChannel, colorChannel).getRGB());
		}
	}
}
