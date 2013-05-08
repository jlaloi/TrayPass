package traypass.tools;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import traypass.ressources.Factory;

public class TextReader {

	private static final Logger logger = LoggerFactory.getLogger(TextReader.class);

	private static final int imageType = BufferedImage.TYPE_INT_ARGB;

	public static final String alpha = "0123456789abcdefghijklmnopqrstuvwxyz&�#'{([-|�_\\��@)�])+=}$%�*!�:/;.,?<>��".toLowerCase();

	public String specialChar = "_y";

	private BufferedImage image;

	private Font font;

	private int fontColor = Color.BLACK.getRGB();

	public TextReader(BufferedImage image, Font font) {
		super();
		this.font = font;
		this.image = image;
	}

	public void setFontColor(int fontColor) {
		this.fontColor = fontColor;
	}

	public void setSpecialChar(String specialChar) {
		this.specialChar = specialChar;
	}

	public String read() {
		String result = "";
		createCache();
		HashMap<BufferedImage, String> cache = createCache();
		logger.info(cache.size() + " char(s) in cache");
		List<BufferedImage> lines = splitVerticaly(clearImage(image));
		logger.info(" > " + lines.size() + " line(s) identified");
		for (BufferedImage line : lines) {
			List<BufferedImage> chars = splitCharFromImage(prepareImage(line));
			logger.info("    > " + chars.size() + " char(s) found");
			String lineText = identifyChars(chars, cache);
			logger.info("        > " + lineText);
			result += lineText + Factory.lineSeparator;
		}
		return result;
	}

	private HashMap<BufferedImage, String> createCache() {
		HashMap<BufferedImage, String> cache = new HashMap<BufferedImage, String>();
		for (int i = 0; i < alpha.length(); i++) {
			String ch = alpha.charAt(i) + "";
			cache.put(createBufferedStringImage(ch, font), ch);
			if (!ch.equals(ch.toUpperCase())) {
				cache.put(createBufferedStringImage(ch.toUpperCase(), font), ch.toUpperCase());
			}
		}
		return cache;
	}

	private BufferedImage prepareImage(BufferedImage image) {
		BufferedImage result = clearImage(image);
		result = removeBackgroundImage(result);
		for (char str : specialChar.toCharArray()) {
			result = formatSpecialChar(result, str, font);
		}
		return result;
	}

	private BufferedImage formatSpecialChar(BufferedImage image, char str, Font font) {
		BufferedImage pattern = createBufferedStringImage(str + "", font);
		int i = 0;
		for (Point point : getImagePosition(image, pattern, font)) {
			image = addEmptyColumn(image, point.x + i);
			image = addEmptyColumn(image, point.x + pattern.getWidth() + i + 1);
			i += 2;
		}
		return image;
	}

	private BufferedImage addEmptyColumn(BufferedImage image, int x) {
		BufferedImage result = new BufferedImage(image.getWidth() + 1, image.getHeight(), imageType);
		Graphics g = result.getGraphics();
		g.drawImage(image, 0, 0, x, image.getHeight(), 0, 0, x, image.getHeight(), null);
		g.drawImage(image, x + 1, 0, image.getWidth() + 1, image.getHeight(), x, 0, image.getWidth(), image.getHeight(), null);
		g.dispose();
		return result;
	}

	private List<Point> getImagePosition(BufferedImage image, BufferedImage pattern, Font font) {
		List<Point> result = new ArrayList<Point>();
		for (int y = 0; y <= image.getHeight() - pattern.getHeight(); y++) {
			for (int x = 0; x <= image.getWidth() - pattern.getWidth(); x++) {
				if (sameImage(image, pattern, x, y)) {
					result.add(new Point(x, y));
				}
			}
		}
		return result;
	}

	private boolean sameImage(BufferedImage source, BufferedImage pattern, int startX, int startY) {
		if (source.getRGB(startX, startY) != pattern.getRGB(0, 0)) {
			return false;
		}
		for (int y = 0; y < pattern.getHeight(); y++) {
			for (int x = 0; x < pattern.getWidth(); x++) {
				if (source.getRGB(x + startX, y + startY) != pattern.getRGB(x, y)) {
					return false;
				}
			}
		}
		return true;
	}

	private BufferedImage createBufferedStringImage(String ch, Font font) {
		BufferedImage result = new BufferedImage(100, 100, imageType);
		Graphics2D g2 = result.createGraphics();
		g2.setColor(new Color(fontColor));
		g2.setFont(font);
		g2.drawString(ch, 0, 50);
		g2.dispose();
		return clearImage(result);
	}

	private boolean isSameImage(BufferedImage imageA, BufferedImage imageB) {
		if (imageA.getWidth() != imageB.getWidth() || imageA.getHeight() != imageB.getHeight()) {
			return false;
		}
		for (int x = 0; x < imageA.getWidth(); x++) {
			for (int y = 0; y < imageA.getHeight(); y++) {
				int rgba = imageA.getRGB(x, y);
				int rgbb = imageB.getRGB(x, y);
				if (rgba == fontColor && rgbb != fontColor || rgbb == fontColor && rgba != fontColor) {
					return false;
				}
			}
		}
		return true;
	}

	private String identifyChar(BufferedImage image, HashMap<BufferedImage, String> cache) {
		String result = " ";
		for (BufferedImage cached : cache.keySet()) {
			if (isSameImage(image, cached)) {
				result = cache.get(cached);
				break;
			}
		}
		return result;
	}

	private String identifyChars(List<BufferedImage> chars, HashMap<BufferedImage, String> cache) {
		String result = "";
		for (BufferedImage ch : chars) {
			result += identifyChar(ch, cache);
		}
		return result;
	}

	private List<BufferedImage> splitCharFromImage(BufferedImage image) {
		List<BufferedImage> result = new ArrayList<BufferedImage>();
		int startPos = 0;
		boolean stop;
		for (int x = 0; x < image.getWidth(); x++) {
			stop = true;
			for (int y = 0; y < image.getHeight(); y++) {
				if (image.getRGB(x, y) == fontColor) {
					stop = false;
					break;
				}
			}
			if ((stop && x - startPos >= 1) || image.getWidth() - 1 == x) {
				BufferedImage newChar = getSubimageHorizontal(image, startPos, x + 1);
				x++;
				startPos = x;
				result.add(clearImage(newChar));
			}
		}
		return result;
	}

	private BufferedImage clearImage(BufferedImage image) {
		int x = -1;
		int y = -1;
		int maxX = -1;
		int maxY = -1;
		for (int i = 0; i < image.getWidth(); i++) {
			for (int j = 0; j < image.getHeight(); j++) {
				int current = image.getRGB(i, j);
				if (current == fontColor) {
					if (x == -1 || i < x) {
						x = i;
					}
					if (y == -1 || j < y) {
						y = j;
					}
					if (i > maxX) {
						maxX = i;
					}
					if (j > maxY) {
						maxY = j;
					}
				}
			}
		}
		maxX++;
		maxY++;
		return cropImage(image, x, y, maxX, maxY);
	}

	private BufferedImage removeBackgroundImage(BufferedImage image) {
		BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), imageType);
		Graphics2D g2 = result.createGraphics();
		g2.setColor(new Color(fontColor));
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				if (image.getRGB(x, y) == fontColor) {
					g2.fillRect(x, y, 1, 1);
				}
			}
		}
		g2.dispose();
		return result;
	}

	public static BufferedImage getSubimageHorizontal(BufferedImage image, int x, int maxX) {
		return cropImage(image, x, 0, maxX, image.getHeight());
	}

	public static BufferedImage getSubimageVertical(BufferedImage image, int y, int maxY) {
		return cropImage(image, 0, y, image.getWidth(), maxY);
	}

	public static BufferedImage cropImage(BufferedImage image, int x, int y, int maxX, int maxY) {
		int width = maxX - x;
		int height = maxY - y;
		BufferedImage result = new BufferedImage(width, height, imageType);
		Graphics g = result.getGraphics();
		g.drawImage(image, 0, 0, width, height, x, y, maxX, maxY, null);
		g.dispose();
		return result;
	}

	private List<BufferedImage> splitVerticaly(BufferedImage image) {
		List<BufferedImage> result = new ArrayList<BufferedImage>();
		int currentSpace = 0;
		int lastPos = 0;
		for (int y = 0; y < image.getHeight(); y++) {
			int x = 0;
			for (; x < image.getWidth(); x++) {
				if (image.getRGB(x, y) == fontColor) {
					break;
				}
			}
			if (x == image.getWidth()) {
				currentSpace++;
				if (currentSpace > 2) {
					result.add(getSubimageVertical(image, lastPos, y));
					lastPos = y;
					currentSpace = 0;
				}
			} else {
				currentSpace = 0;
			}
		}
		result.add(getSubimageVertical(image, lastPos, image.getHeight()));
		return result;
	}

}
