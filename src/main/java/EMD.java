import java.awt.Color;
import java.awt.image.BufferedImage;

import com.google.zxing.common.BitMatrix;

public class EMD extends Steganography {
	private int n; // Used to calculate the (2n + 1)-ary digit
	private int position; // Used to determine the position in the cover image when embedding or extracting
	private int secretLength;
	private int numBits; // the number of bits that will be used per base-(2n+1)

	public EMD(BufferedImage coverImage, int n, String message, int payloadWidth, int payloadHeight) {
		super(coverImage, message, payloadWidth, payloadHeight);
		
		// Make sure the n is not negative or zero
		if (n < 1) {
			this.n = 1;
		} else {
			this.n = n;
		}
		
		/*
		 * Determine largest number of bits that can be stores into 
		 * a single digit with base-(2n+1)
		 */
		numBits = 0;
		while (Math.pow(2, numBits) < (2*this.n+1)) {
			numBits++;
		}
		numBits--;
		
		this.position = 0;
	}

	@Override
	public void embed() {
		// Step 1 convert payload to (2n+1)-ary digit
		String digits = bitMatrixToBase2NPlus1();
		int digitPosition = 0;
		
		// Set secret length for later extraction
		this.secretLength = digits.length();
		
		// Iterate through cover image
		for (position = 0; position < (getCoverImage().getHeight() * getCoverImage().getWidth()); position += n) {
			
			// Step 2 calculate extraction function
			int efNum = extractionFu(getCoverImage());
			
			// Step 3 shift bit according to result from extraction function
			embedDigit(digits, digitPosition, efNum);
			digitPosition++;
			
			// Reset digit position if all digits have been embedded
			if (digitPosition >= this.secretLength) {
				digitPosition = 0;
			}
		}
	}

	@Override
	public BitMatrix extract() {
		// Create BitMatrix for QR code
		BitMatrix extractedData = new BitMatrix(this.getPayload().getWidth(), this.getPayload().getHeight());
				
		// Initialize secret digit string
		String binaryDigits = "";
		int digitPosition = 0;
		
		// Iterate through stego image to extract image
		for (position = 0; position < (getStegoImage().getHeight() * getStegoImage().getWidth()); position += n) {
			// Extract secret digit
			binaryDigits += base10ToBinary(extractionFu(getStegoImage()));
			digitPosition++;
			
			if (digitPosition >= this.secretLength) {
				break;
			}
		}
		
		// Use binaryDigits to re-create BitMatrix
		int messageSize = this.getPayload().getWidth() * this.getPayload().getHeight();
		for (int i = 0; i < messageSize; i++) {
			// TODO: accomodate for overflow
			if (binaryDigits.charAt(i) == '1') {
				extractedData.set(i % this.getPayload().getWidth(), i / this.getPayload().getWidth());
			}
		}
			
		return extractedData;
	}
	
	/*
	 * Converts the BitMatrix payload into a binary string by scanning 
	 * the payload from left to right top to bottom. Then converts the 
	 * binary string into a base (2n+1)-ary string.
	 * 
	 * @return String - (2n+1)-ary string representation of a number.
	 */
	private String bitMatrixToBase2NPlus1() {
		BitMatrix payload = getPayload();
		String binaryNum = "";
		String curBits;
		String resultNum = "";
		
		// Get binary string
		for (int y = 0; y < payload.getHeight(); y++) {
			for (int x = 0; x < payload.getWidth(); x++) {
				binaryNum += payload.get(x, y) ? "1" : "0";
			}
		}
		
		// Convert binary string, one digit at a time
		// Get a subset of numBits bits from binaryNum
		for (int i = 0; i < binaryNum.length(); i += numBits) {
			curBits = "";
			for (int j = i; j < i + numBits; j++) {
				if (j < binaryNum.length()) {
					curBits += binaryNum.charAt(j);
				} 
			}
			
			// convert to number to base-(2n+1) and append to result
			resultNum += Integer.toString(Integer.parseInt(curBits, 2), 2*n+1);
		}
		
		return resultNum;
	}
	
	/*
	 * Convert (2n+1)-ary digit to binary
	 * 
	 * @param String - a 1 character secret digit
	 * @return String - 
	 */
	private String base10ToBinary(int digit) {
		String binary = Integer.toString(digit, 2);
		
		// Add padding 0's to accomodate lost bits
		if (binary.length() < numBits) {
			binary = "0" + binary;
		}
		
		return binary;
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
	private int extractionFu(BufferedImage image) {
		// Initialize sum
		int sum = 0;
		
		// Calculate sum
		for (int i = 0; i < n; i++) {
			// Get color pixel
			int colorChannel = image.getRGB((position+i) % image.getWidth(), (position+i) / image.getWidth()) & 0xff;
			
			// Add to the sum
			sum += colorChannel * (i+1);
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
	private void embedDigit(String secretDigits, int digitPosition, int extractionFuResult) {
		// Calculate r
		int r = (((Integer.parseInt(Character.toString(secretDigits.charAt(digitPosition)), 2*n+1)) - extractionFuResult) % (2*n+1));
		if (r < 0) {
			r += (2*n+1);
		}
		//System.out.println("R: " + r);
		
		// Compare r to condition
		int condition = (2*n + 1) / 2;
		boolean isIncrease = false;
		int coverPixel = -1;
		
		// If 0 do nothing
		if (r == 0) {
			// Do nothing
		// If smaller increase pixel r
		} else if (r <= condition) {
			isIncrease = true;
			coverPixel = r - 1;
		// If bigger decrease pixel (2n+1)-r
		} else if (r > condition) {
			isIncrease = false;
			coverPixel = (2*n+1) - r - 1;
		} 
			
		// Transfer pixels and modify pixel that requires modifying
		for (int i = 0; i < n; i++) {
			// Get pixel
			int colorChannel = this.getCoverImage().getRGB((position+i) % getCoverImage().getWidth(), (position+i) / getCoverImage().getWidth()) & 0xff;
			if ((position+i) % getStegoImage().getWidth() == 62 && (position+i) / getStegoImage().getWidth() == 261) {
				System.out.println("Color Before: " + colorChannel);
			}
			// If pixel matches condition result increase or decrease
			if (i == coverPixel) {
				if (isIncrease) {
					colorChannel++;
				} else {
					colorChannel--;
				}
			}
			
			if ((position+i) % getStegoImage().getWidth() == 62 && (position+i) / getStegoImage().getWidth() == 261) {
				System.out.println("Color After: " + colorChannel);
			}
			
			// Set the pixel value to the stegoImage
			System.out.println("Column x: " + (position+i) % getStegoImage().getWidth());
			System.out.println("Row y: " + (position+i) / getStegoImage().getWidth());
            this.getStegoImage().setRGB((position+i) % getStegoImage().getWidth(), (position+i) / getStegoImage().getWidth(), new Color(colorChannel, colorChannel, colorChannel).getRGB());
		}
	}
}
