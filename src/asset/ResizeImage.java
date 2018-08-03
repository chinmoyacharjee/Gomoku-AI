package asset;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ResizeImage {
	public static BufferedImage resizeImage(BufferedImage originalImage, int type, int width, int height) {
		BufferedImage resizedImage = new BufferedImage(width, height, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, width, height, null);
		g.dispose();
		return resizedImage;
	}
}
