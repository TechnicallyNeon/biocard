package card;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class StatBlockUtil 
{
	/**
	 * Set of colors for the borders of the stat bars
	 */
    private static final int[] BORDER_COLOR = 
        {
            10878976, 10244895, 10585887, 4480668, 5145140, 10565977
        };

    /**
     * Set of colors for the stat bars
     */
    private static final int[] CENTER_COLOR = 
        {
            16711680, 15761456, 16306224, 6852856, 7915600, 16275592
        };
    
    /**
     * x-coordinate for drawing display text that indicates the stat value relating to the stat bar
     */
    private static final int STRING_XCOORD = 1400;
    
    /**
     * Set of y-coordinates for drawing display text that indicates the stat value relating to the stat bar
     */
    private static final int[] STRING_YCOORD =
    	{
    			519, 604, 689, 774, 859, 944, 1029
    	};

    /**
     * Private constructor
     */
    private StatBlockUtil()
    {
    }

    /**
     * Static method for drawing a stat block onto a given image
     * @param image - BufferedImage on which the stat block will be drawn onto
     * @param x - int representing the x-coordinate for where the block will be drawn
     * @param y - int representing the y-coordinate for where the block will be drawn
     * @param stats - a given int array for how the stat bars will be drawn. It is given in the format [hp, attack, defense, special attack, special defense, speed].
     * @throws IOException
     */
    public static void drawStats(BufferedImage image, int x, int y, int[] stats) throws IOException
    {
        Graphics2D graphics = (Graphics2D) image.getGraphics();
		graphics.setColor(Color.BLACK);
		graphics.setFont(new Font("Calibri", Font.BOLD, 48));
		FontMetrics fm = graphics.getFontMetrics();
		graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, 
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int sum = 0;
        for (int i = 0; i < 6; i++)
        {
            drawBorderRectangle(image, BORDER_COLOR[i], stats[i], x, y);
            drawInnerRectangle(image, CENTER_COLOR[i], stats[i] - 3, x + 3, y + 3);
            y += 85;
            
        	String temp = String.valueOf(stats[i]);
	        graphics.drawString(temp, STRING_XCOORD - fm.stringWidth(temp), STRING_YCOORD[i]);
        	sum += stats[i];
        }
        
        // Get stat sum
        String sumStr = String.valueOf(sum);
        graphics.drawString(sumStr, STRING_XCOORD - fm.stringWidth(sumStr), STRING_YCOORD[6]);
    }
    /**
     * Draws a rectangle with top left corner given by (x, y). The length of the rectangle is twice the stat
     * value given. The height is a fixed 80. This is meant to be a border for drawInnerRec().
     * @param rgb - color of border
     * @param stat - stat value length is to represent
     * @param x - point of origin of x
     * @param y - point of origin of y
     */
    private static void drawBorderRectangle(BufferedImage img, int rgb, int stat, int x, int y) 
    {
        for (int i = y; i < y + 80; i++)
            for (int j = x; j < x + stat*2; j++)
            {
            	Color temp = new Color(rgb);
            	Color color = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 255);
				img.setRGB(j, i, (color.getAlpha() << 24) + (color.getRed() << 16)
						+ (color.getGreen() << 8) + (color.getBlue()));
            }
    }
    
    /**
     * Draws a rectangle with top left corner given by (x, y). The length of the rectangle is twice the stat
     * value given minus six. The height is a fixed 74. Meant to fill drawRec().
     * @param rgb - color of border
     * @param stat - stat value length is to represent
     * @param x - point of origin of x
     * @param y - point of origin of y
     */
    private static void drawInnerRectangle(BufferedImage img, int rgb, int stat, int x, int y) 
    {
        for (int i = y; i < y + 74; i++)
            for (int j = x; j < x + stat*2; j++)
            {
            	Color temp = new Color(rgb);
            	Color color = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 255);
				img.setRGB(j, i, (color.getAlpha() << 24) + (color.getRed() << 16)
						+ (color.getGreen() << 8) + (color.getBlue()));
            }
    }
}