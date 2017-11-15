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
			this.coverImage = coverImage;
			this.stegoImage = new BufferedImage(coverImage.getWidth(), coverImage.getHeight(), coverImage.getType());
			this.payload = Payload.getQRCodeImage(message, payloadWidth, payloadHeight);
			this.payloadSize =  payloadWidth * payloadHeight;
		} catch (WriterException e) {
            System.out.println("Could not generate QR Code, WriterException :: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Could not generate QR Code, IOException :: " + e.getMessage());
        }
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
