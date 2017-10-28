package main;
import java.awt.image.BufferedImage;

public abstract class Steganography {
	private BufferedImage coverImage;
	private BufferedImage stegoImage;
	
	public Steganography(BufferedImage coverImage) {
		this.coverImage = coverImage;
		this.stegoImage = new BufferedImage(coverImage.getWidth(), coverImage.getHeight(), coverImage.getType());
	}
	
	/*
	 * Override the Method embed() to implement steganographic algorithm. Then set the result to the stegoImage property which now has the secret media embedded into it.
	 */
	public abstract void embed(BufferedImage secretMedia);
	
	/*
	 * Params: BufferedImage - an image which will then be used to embed a secret media into it
	 */
	public void setCoverImage(BufferedImage coverImage) {
		this.coverImage = coverImage;
	}
	
	/*
	 * Returns: BufferedImage - the original unmodified cover image
	 */
	public BufferedImage getCoverImage() {
		return coverImage;
	}
	
	/*
	 * Returns: BufferedImage - 	The stego-image which has the secret media embedded into it. 
	 * 							If embed() has not been used yet, then a blank image is returned.
	 */
	public BufferedImage getStegoImage() {
		return stegoImage;
	}
}
