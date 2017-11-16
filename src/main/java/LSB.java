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
		int tempMask = 0;
		for (int i = 0; i < bitDepth; i++) {
			tempMask += (int) Math.pow(2, i);
			System.out.println(	"tempMask: " + tempMask + "\n");
		}
		final int ONLY_LSB = tempMask;
		
		// Create Mask to clear all bits except LSBs.
		final int CLEAR_MSB = ONLY_LSB ^ 0xFF;
		System.out.println(	"LSB Mask: " + ONLY_LSB + "\n" +
							"MSB Mask: " + CLEAR_MSB + "\n");
		
		// Track position for embedding data
		int embedPosition = 0;
		final int EMBED_SIZE = this.getPayloadSize();
		System.out.println(	"Payload Height: " + this.getPayload().getHeight() + "\n" +
							"Payload Width: " + this.getPayload().getWidth() + "\n" +
							"Payload Size: " + this.getPayloadSize());
		
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
					embeddingBits = embeddingBits << 1;
					embeddingBits = embeddingBits | ((this.getPayload().get(embedPosition % this.getPayload().getWidth(), embedPosition / this.getPayload().getWidth()) ? 0x1 : 0x0));
							
					// Ensure embed pointer doesn't overflow QR code
					if (embedPosition < EMBED_SIZE-1) {
						embedPosition++;
					} else {
						embedPosition = 0;
					}
				}
				System.out.println(	"\nembeddingBits: " + embeddingBits + "\n" +
						"embedPosition: " + embedPosition + "\n");
				
				System.out.println(	"\ncolorChannel BEFORE: " + embeddingBits + "\n");
				// Embed the bits into the pixel
				//embeddingBits = embeddingBits & ONLY_LSB;
				colorChannel = (colorChannel & CLEAR_MSB) | embeddingBits;
				System.out.println(	"\ncolorChannel AFTER: " + embeddingBits + "\n");
				
				// Set the new pixel value to the stegoImage
				this.getStegoImage().setRGB(x, y, new Color(colorChannel, colorChannel, colorChannel).getRGB());
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
		//extractedData.setRegion(0, 0, this.getPayload().getWidth(), this.getPayload().getHeight());
		
		STEGO_LOOP:
			for (int x = 0; x < this.getStegoImage().getWidth(); x++) {
				for (int y = 0; y < this.getStegoImage().getHeight(); y++) {
					// Get color pixel
					int colorChannel = this.getStegoImage().getRGB(x, y) & 0xff;
					
					// Extract enough QR code bits before moving to next pixel
					for (int i = 0; i < bitDepth; i++) {
						
						if (((colorChannel >> (bitDepth-i-1)) & 0x01) == 1) {
							extractedData.set(embedPosition % this.getPayload().getWidth(), embedPosition / this.getPayload().getWidth());
						} else {
							extractedData.unset(embedPosition % this.getPayload().getWidth(), embedPosition / this.getPayload().getWidth());
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
}
