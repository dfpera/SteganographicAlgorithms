import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
	
public abstract class Steganography {
	private BufferedImage coverImage;
	private BufferedImage stegoImage;
	private BitMatrix payload;
	private int payloadSize;
	
	/*
	 * Default constructor
	 * 
	 * @param coverImage - the image in which the data will be embedded into
	 * @param message - the message to be embedded into the coverImage
	 * @param payloadWidth - the width of the payload to carry the message
	 * @param payloadHeight - the height of the payload to carry the message
	 */
	public Steganography(BufferedImage coverImage, String message, int payloadWidth, int payloadHeight) {
		try {
			this.stegoImage = new BufferedImage(coverImage.getWidth(), coverImage.getHeight(), BufferedImage.TYPE_USHORT_GRAY);
			this.coverImage = adjustCoverImage(coverImage);
			this.payload = Payload.getQRCodeImage(message, payloadWidth, payloadHeight);
			this.payloadSize =  payloadWidth * payloadHeight;
		} catch (WriterException e) {
            System.out.println("Could not generate QR Code, WriterException :: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Could not generate QR Code, IOException :: " + e.getMessage());
        }
	}
	
	/*
	 * This function is simply to make the implementation of steganography easier.
	 * Gets rid of edge cases at 0 and 255 rgb values.
	 * 
	 * @param BufferedImage - the image that will have it's max and min values adjusted by 1 respectively
	 * @return BufferedImage - the adjusted image
	 */
	private BufferedImage adjustCoverImage(BufferedImage image) {
		BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_USHORT_GRAY);
		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				// Get rgb value
				int color = image.getRGB(x, y) & 0xff;

				// If max value minus 1
				if (color == 255) {
					color--;
				// If min value add 1
				} else if (color == 0) {
					color++;
				}
				
				// Set new rgb value
				result.setRGB(x, y, new Color(color, color, color).getRGB());
			}
		}
		return result;
	}
	
	/*
	 * Override the Method embed() to implement steganographic algorithm. Then set the result to the stegoImage property which now has the secret media embedded into it.
	 */
	public abstract void embed();
	
	/*
	 * Override the Method extract() to implement steganographic extraction algorithm. Returning the BitMatrix of the QR code.
	 */
	public abstract BitMatrix extract();
	
	/*
	 * Set the image to be used as the cover image for steganography.
	 * 
	 * @param coverImage - an image which will then be used to embed a secret media into it
	 */
	public void setCoverImage(BufferedImage coverImage) {
		this.coverImage = coverImage;
	}
	
	/*
	 * Get the original image with no embedded data
	 * 
	 * @return BufferedImage - the original unmodified cover image
	 */
	public BufferedImage getCoverImage() {
		return coverImage;
	}
	
	/*
	 * Get the stego image with the embedded data.
	 * 
	 * @return BufferedImage - 	The stego-image which has the secret media embedded into it. 
	 * 							If embed() has not been used yet, then a blank image is returned.
	 */
	public BufferedImage getStegoImage() {
		return stegoImage;
	}
	
	/*
	 * Get the size of the payload binary image.
	 * 
	 * @return int - size of the binary image payload
	 */
	public int getPayloadSize() {
		return payloadSize;
	}
	
	/*
	 * Decodes the payload to retrieve the original String message.
	 * 
	 * @return String - Message that is to be embedded into coverImage
	 */
	public String getMessage() {
		try {
			return Payload.decodeQRCode(MatrixToImageWriter.toBufferedImage(payload));
        } catch (IOException e) {
            System.out.println("Could not generate QR Code, IOException :: " + e.getMessage());
            return "";
        }
	}
	
	/*
	 * Set a new payload with new height and width
	 * 
	 * @param message - the new message to be embedded into coverImage
	 * @param payloadWidth - the width of the payload to carry the message
	 * @param payloadHeight - the height of the payload to carry the message
	 */
	public void setPayload(String message, int payloadWidth, int payloadHeight) {
		try {
			this.payloadSize =  payloadWidth * payloadHeight;
			this.payload = Payload.getQRCodeImage(message, payloadWidth, payloadHeight);
		} catch (WriterException e) {
            System.out.println("Could not generate QR Code, WriterException :: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Could not generate QR Code, IOException :: " + e.getMessage());
        }
	}
	
	/*
	 * Get the payload as a binary image.
	 * 
	 * @return BitMatrix - the payload as a binary image
	 */
	public BitMatrix getPayload() {
		return payload;
	}
}
