package card;

import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Used for drawing miscellaneous items onto a given BufferedImage
 * @author TechnicallyNeon
 *
 */
public class DrawMisc
{
	private DrawMisc()
	{
		
	}
	
	/**
	 * Draws an icon image at given position and of given type
	 * @param g2 - graphics object to be drawn onto
	 * @param x - x-coordinate to be drawn at
	 * @param y- y-coordinate to be drawn at
	 * @param type - type of icon to be drawn
	 */
	public static void drawIcon(Graphics2D g2, int x, int y, Type type)
	{
		File file = new File("components/type_icon/" + type.getType() + ".png");
		try
		{
			g2.drawImage(ImageIO.read(file), x, y, null);
		} catch (IOException e)
		{
			System.out.println("Could not read icon file " + file.getName());
		}
	}
	
	/**
	 * Draws a given item image at the given position
	 * @param g2 - graphics object to be drawn onto
	 * @param x - x-coordinate to be drawn at
	 * @param y- y-coordinate to be drawn at
	 * @param item - name of item to be drawn
	 */
	public static void drawItem(Graphics2D g2, int x, int y, String item)
	{
		File file = new File("components/item/" + item + ".png");
		try
		{
			g2.drawImage(ImageIO.read(file), x, y, null);
		} catch (IOException e)
		{
			System.out.println("Could not read item file " + file.getName());
		}
	}
	
	/**
	 * Draws an image representing the gender ratio for the card at a given position
	 * @param g2 - graphics object to be drawn onto
	 * @param x - x-coordinate to be drawn at
	 * @param y- y-coordinate to be drawn at
	 * @param ratio - String that tells which ratio image is to be drawn
	 */
	public static void drawGenderRatio(Graphics2D g2, int x, int y, String ratio)
	{
		File file = new File("components/gender_ratio/" + ratio + ".png");
		try
		{
			g2.drawImage(ImageIO.read(file), x, y, null);
		} catch (IOException e)
		{
			System.out.println("Could not read gender file " + file.getName());
		}
	}
	
	public static void drawAvatar(Graphics2D g2, int x, int y, int id) 
	{
		File file = new File("components/pokemon/" + EvoBlock.itoa(id) + ".png");
		try
		{
			g2.drawImage(ImageIO.read(file), 75, 75, null);
		}
		catch (Exception e)
		{
			System.out.println("Could not read pokemon file " + file.getName());
		}
	}
}
