package card;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class StatBlock 
{
	private static BufferedImage image;
	
    private static final int[] BORDER = 
        {
            10878976, 10244895, 10585887, 4480668, 5145140, 10565977
        };

    private static final int[] CENTER = 
        {
            16711680, 15761456, 16306224, 6852856, 7915600, 16275592
        };
    
    private static final int STRING_XCOORD = 1400;
    private static final int[] STRING_YCOORD =
    	{
    			519, 604, 689, 774, 859, 944, 1029
    	};

    private StatBlock() throws IOException
    {
        
    }

    public static void drawStats(BufferedImage img, int x, int y, int[] stats) throws IOException
    {
    	// Draw stat bars
    	image = img;
    	
        Graphics2D graphics = (Graphics2D) img.getGraphics();
		graphics.setColor(Color.BLACK);
		graphics.setFont(new Font("Calibri", Font.BOLD, 48));
		FontMetrics fm = graphics.getFontMetrics();
		graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, 
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int sum = 0;
        for (int i = 0; i < 6; i++)
        {
            drawRec(BORDER[i], stats[i], x, y);
            drawInnerRec(CENTER[i], stats[i] - 3, x + 3, y + 3);
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
    private static void drawRec(int rgb, int stat, int x, int y) 
    {
        for (int i = y; i < y + 80; i++)
            for (int j = x; j < x + stat*2; j++)
            {
            	Color temp = new Color(rgb);
            	Color color = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 255);
				image.setRGB(j, i, (color.getAlpha() << 24) + (color.getRed() << 16)
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
    private static void drawInnerRec(int rgb, int stat, int x, int y) 
    {
        for (int i = y; i < y + 74; i++)
            for (int j = x; j < x + stat*2; j++)
            {
            	Color temp = new Color(rgb);
            	Color color = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 255);
				image.setRGB(j, i, (color.getAlpha() << 24) + (color.getRed() << 16)
						+ (color.getGreen() << 8) + (color.getBlue()));
            }
    }
}