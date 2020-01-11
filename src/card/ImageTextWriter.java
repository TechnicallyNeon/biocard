package card;

import java.awt.FontMetrics;
import java.awt.Graphics2D;

/**
 * Ulity class for writing text of a given Graphics2D object
 * @author TechnicallyNeon
 *
 */
public class ImageTextWriter
{
	private ImageTextWriter()
	{
		
	}
	
	/**
	 * Draws a string of characters at a given position. This method uses the FontMetrics and font given by the graphics object provided.
	 * @param graphics - Graphics2D object that contains FontMetrics and font information for writing and what will be written onto.
	 * @param x - x-coordinate that will be written to
	 * @param y - y-coordinate that will be written to
	 * @param width - the width that the text will be wrapped around
	 * @param text - the given string of characters to be written to the Graphics2D object
	 * @param align - will write left-aligned if true, center-aligned if false
	 */
	public static void writeText(Graphics2D graphics, int x, int y, int width, String text, boolean align)
	{
		if (align)
			writeLeft(graphics, x, y, width, text);
		else
			writeCenter(graphics, x, y, width, text);
	}
	
	private static void writeLeft(Graphics2D graphics, int x, int y, int w, String text)
	{
		if (text == null)
			return;
		
		FontMetrics fm = graphics.getFontMetrics();
		int lineH = fm.getHeight();
		String[] textArr = text.split(" ");
		int i = 0;
		while (i < textArr.length)
		{
			String line = textArr[i++];
			while ((i < textArr.length) && (fm.stringWidth(line + " " + textArr[i]) < w))
				line = line + " " + textArr[i++];
			graphics.drawString(line, x, y);
			y += lineH;
		}
	}
	
	private static void writeCenter(Graphics2D graphics, int x, int y, int w, String text)
	{
		writeLeft(graphics, x-graphics.getFontMetrics().stringWidth(text)/2, 
				y+graphics.getFontMetrics().getHeight(), w, text);
	}
}
