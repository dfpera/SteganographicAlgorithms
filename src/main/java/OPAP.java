import java.awt.Color;
import java.awt.image.BufferedImage;

import com.google.zxing.common.BitMatrix;

public class OPAP extends Steganography {
	private int bitDepth = 1;

	public OPAP(BufferedImage coverImage, int bitDepth, String message, int payloadWidth, int payloadHeight) {
		super(coverImage, message, payloadWidth, payloadHeight);
		
		// Make sure the bitDepth is not negative, zero or exceed the number of bits available per pixel value (between 1 - 8 bits)
		if (bitDepth > 8) {
			this.bitDepth = 8;
		} else if (bitDepth < 1) {
			this.bitDepth = 1;
		} else {
			this.bitDepth = bitDepth;
		}
	}

	@Override
	public void embed() {
		// Create Mask to clear LSBs.
		int tempMask = 0;
		for (int i = 0; i < bitDepth; i++) {
			tempMask += (int) Math.pow(2, i);
		}
		final int ONLY_LSB = tempMask;
		
		// Create Mask to clear all bits except LSBs.
		final int CLEAR_MSB = ONLY_LSB ^ 0xFF;
		
		// Track position for embedding data
		int embedPosition = 0;
		final int EMBED_SIZE = this.getPayloadSize();
		
		for (int y = 0; y < this.getCoverImage().getHeight(); y++) {
			for (int x = 0; x < this.getCoverImage().getWidth(); x++) {
				// Get color pixel
				int colorChannel = this.getCoverImage().getRGB(x, y) & 0xff;
				
				// Get enough QR code bits to embed into one pixel
				int embeddingBits = 0;
				for (int i = 0; i < bitDepth; i++) {
					// Retrieve bit value from QR code
					embeddingBits = (embeddingBits << 1) | ((this.getPayload().get(embedPosition % this.getPayload().getWidth(), embedPosition / this.getPayload().getWidth()) ? 0x1 : 0x0));
							
					// Ensure embed pointer doesn't overflow QR code
					if (embedPosition < EMBED_SIZE-1) {
						embedPosition++;
					} else {
						embedPosition = 0;
					}
				}
				
				// Embed the bits into the pixel using LSB
				int stegoChannel = (colorChannel & CLEAR_MSB) | embeddingBits;
				
				// Calculate embedding error between stego-pixel and cover pixel
				int error = stegoChannel - colorChannel; 
							
				// Case 1: If error is within (-2)^k and 2^k, where k = bit depth of LSB, 
		        //         and stego-pixel is >= 2^k, then reduce the error
				if (error > Math.pow(-2, bitDepth) && error < Math.pow(2, bitDepth) && stegoChannel >= Math.pow(2, bitDepth)) {	
					stegoChannel = (int)(stegoChannel - Math.pow(2, bitDepth));
				} else {
					stegoChannel = stegoChannel;
				}
				
				// Case 2: If error is within (-2)^(k-1) and 2^(k-1),
				//	       leave the stego-pixel as is
				if (error >= Math.pow(-2, bitDepth-1) && error <= Math.pow(2, bitDepth-1)) {
					stegoChannel = stegoChannel;
				}
				
				// Case 3: If error is within (-2)^k and (-2)^(k-1), 
				// 		   and stego-pixel is less than 256 - 2^k
				if (error > Math.pow(-2, bitDepth) && error < Math.pow(-2, bitDepth-1) && stegoChannel < 256 - Math.pow(2, bitDepth)) {
					stegoChannel = (int)(stegoChannel + Math.pow(2, bitDepth));
				} else { 
					stegoChannel = stegoChannel;
				}
				
                // Set the new pixel value to the stegoImage
                this.getStegoImage().setRGB(x, y, new Color(stegoChannel, stegoChannel, stegoChannel).getRGB());
			}
		}
		
	}

	@Override
	public BitMatrix extract() {
		// Track position for embedding data
		int embedPosition = 0;
		final int EMBED_SIZE = this.getPayloadSize();
		
		// Create BitMatrix for QR code
		BitMatrix extractedData = new BitMatrix(this.getPayload().getWidth(), this.getPayload().getHeight());
		
		STEGO_LOOP:	// Used to break out of loop once data is extracted
			for (int y = 0; y < this.getStegoImage().getHeight(); y++) {
				for (int x = 0; x < this.getStegoImage().getWidth(); x++) {
					// Get color pixel
					int colorChannel = this.getStegoImage().getRGB(x, y) & 0xff;
					
					// Extract enough QR code bits before moving to next pixel
					for (int i = 0; i < bitDepth; i++) {
						// Shift bits to extract each bit one by one from MSB to LSB
						if (((colorChannel >> (bitDepth-i-1)) & 0x1) == 0x1) {
							extractedData.set(embedPosition % this.getPayload().getWidth(), embedPosition / this.getPayload().getWidth());
						} 
								
						// Ensure embed pointer doesn't overflow QR code
						if (embedPosition < EMBED_SIZE-1) {
							embedPosition++;
						} else {
							break STEGO_LOOP;
						}
					}
				}
			}
		return extractedData;
	}
	
} // end OPAP
