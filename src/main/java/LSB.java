import java.awt.image.BufferedImage;

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
		// TODO Implement the LSB function.
		
	}

}
