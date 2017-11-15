import java.awt.Color;
import java.awt.image.BufferedImage;

import com.google.zxing.common.BitMatrix;

public class LSB extends Steganography {
	private int bitDepth = 1;

	public LSB(BufferedImage coverImage, int bitDepth, String message, int payloadWidth, int payloadHeight) {
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
		final int ONLY_LSB = (int) Math.pow(2, bitDepth-1);
		
		// Create Mask to clear all bits except LSBs.
		final int CLEAR_MSB = ONLY_LSB ^ 0xFF;
		
		// Track position for embedding data
		int embedPosition = 0;
		final int EMBED_SIZE = this.getPayloadSize();
//		System.out.println(	"Payload Height: " + this.getPayload().getHeight() + "\n" +
//							"Payload Width: " + this.getPayload().getWidth() + "\n" +
//							"Payload Size: " + this.getPayloadSize());
		
		for (int x = 0; x < this.getCoverImage().getWidth(); x++) {
			for (int y = 0; y < this.getCoverImage().getHeight(); y++) {
				// Get color pixel
				int colorChannel = this.getCoverImage().getRGB(x, y) & 0xff;
				
				// Get enough QR code bits to embed into one pixel
				int embeddingBits = 0;
				for (int i = 0; i < bitDepth; i++) {
//					System.out.println(	"\nPOSITION: " + embedPosition + "\n" +
//										"Payload x: " + embedPosition % this.getPayload().getWidth() + "\n" +
//										"Payload y: " + embedPosition / (this.getPayload().getHeight()-1) + "\n");
					// Retrieve bit value from QR code
					embeddingBits = ((this.getPayload().get(embedPosition % this.getPayload().getWidth(), embedPosition / this.getPayload().getHeight()) ? 1 : 0) >> i);
							
					// Ensure embed pointer doesn't overflow QR code
					if (embedPosition < EMBED_SIZE-1) {
						embedPosition++;
					} else {
						embedPosition = 0;
					}
				}
				
				// Embed the bits into the pixel
				embeddingBits = embeddingBits & ONLY_LSB;
				colorChannel = (colorChannel & CLEAR_MSB) | embeddingBits;
				
				// Set the new pixel value to the stegoImage
				this.getStegoImage().setRGB(x, y, new Color(colorChannel, colorChannel, colorChannel).getRGB());
			}
		}
	}
	
	@Override
	public BitMatrix extract() {
		// TODO Implement the extraction LSB function
		return new BitMatrix(0); // Remove this once function is complete
	}

}
