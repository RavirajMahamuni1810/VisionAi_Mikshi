package pw.utils;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class ImageCompareUtil {

	// Two pixels are treated as equal if every RGB channel is within this distance. A small tolerance
	// absorbs anti-aliasing / sub-pixel rendering differences without ignoring real changes.
	private static final int COLOR_TOLERANCE = 30;

	// Returns the fraction (0.0 - 1.0) of pixels that match between the expected and actual images.
	// If the two images differ in size, the actual image is scaled to the expected image's dimensions
	// before comparing, so a different screenshot resolution does not automatically fail the test.
	public static double getSimilarity(String expectedPath, String actualPath) {
		try {
			BufferedImage expected = ImageIO.read(new File(expectedPath));
			BufferedImage actual = ImageIO.read(new File(actualPath));
			if (expected == null) {
				System.out.println("❌ Could not read expected image: " + expectedPath);
				return 0.0;
			}
			if (actual == null) {
				System.out.println("❌ Could not read actual image: " + actualPath);
				return 0.0;
			}

			int width = expected.getWidth();
			int height = expected.getHeight();
			if (actual.getWidth() != width || actual.getHeight() != height) {
				actual = resize(actual, width, height);
			}

			long matching = 0;
			long total = (long) width * height;
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					if (pixelsClose(expected.getRGB(x, y), actual.getRGB(x, y))) {
						matching++;
					}
				}
			}
			return total == 0 ? 0.0 : (double) matching / total;

		} catch (Exception e) {
			e.printStackTrace();
			return 0.0;
		}
	}

	private static boolean pixelsClose(int rgb1, int rgb2) {
		int r1 = (rgb1 >> 16) & 0xff, g1 = (rgb1 >> 8) & 0xff, b1 = rgb1 & 0xff;
		int r2 = (rgb2 >> 16) & 0xff, g2 = (rgb2 >> 8) & 0xff, b2 = rgb2 & 0xff;
		return Math.abs(r1 - r2) <= COLOR_TOLERANCE
				&& Math.abs(g1 - g2) <= COLOR_TOLERANCE
				&& Math.abs(b1 - b2) <= COLOR_TOLERANCE;
	}

	private static BufferedImage resize(BufferedImage img, int width, int height) {
		BufferedImage out = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = out.createGraphics();
		g.drawImage(img, 0, 0, width, height, null);
		g.dispose();
		return out;
	}
}
