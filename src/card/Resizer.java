package card;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Resizer
{
	private Resizer()
	{
		
	}
	
	public static boolean isSize(BufferedImage image, int width, int height)
	{
		return image.getWidth() == width && image.getHeight() == height;
	}
	
	public static BufferedImage resize(BufferedImage image, int newWidth, int newHeight)
	{
		BufferedImage result = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) result.getGraphics();
		g.drawImage(image, 0, 0, 120, 120, null);
		g.dispose();
		return result;
	}
}
