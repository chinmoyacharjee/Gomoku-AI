package asset;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageLoader {
	
	public static BufferedImage loadImage(String path, int width, int height) {

		try {
			BufferedImage im = ImageIO.read(ImageLoader.class.getResource(path));
			int type = im.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : im.getType();

			return ResizeImage.resizeImage(im, type, width, height);

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}

	public static BufferedImage loadImage(String path) {

		try {
			return ImageIO.read(ImageLoader.class.getResource(path));

		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}
}
