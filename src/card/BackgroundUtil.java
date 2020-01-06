package card;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BackgroundUtil
{
	private BackgroundUtil()
	{
	}
	
	/**
	 * Creates a monotype BufferedImage background of a specified type
	 * @param type - type given for deciding background color
	 * @return a BufferedImage for a monotype background or null if the file was not found.
	 */
	public static BufferedImage create(Type type)
	{
		try
		{
			return ImageIO.read(new File(type.getType() + "_bg.png"));
		} 
		catch (IOException e)
		{
			System.out.println("Could not find file \"" + type.getType() + "_bg.png\"");
			return null;
		}
	}
	
	/**
	 * Creates a dual type BufferedImage background based on the two specified types
	 * @param type1 - the first type of the dual type set. Its color scheme will be on the bottom-left section of the card.
	 * @param type2 - the second type of the dual type set. Its color shceme will be on the top-right section of the card.
	 * @return a BufferedImage with the two given types faded into each other.
	 */
	public static BufferedImage create(Type type1, Type type2)
	{
		BufferedImage type1img, type2img, result;
		// Try to read the first given types asset file
		try
		{
			type1img = ImageIO.read(new File(type1.getType() + "_bg.png"));
		}
		catch (IOException e)
		{
			System.out.println("Could not find file \"" + type1.getType() + "_bg.png\"");
			return null;
		}
		// Try to read the second given types asset file
		try
		{
			type2img = ImageIO.read(new File(type2.getType() + "_bg.png"));
		}
		catch (IOException e)
		{
			System.out.println("Could not find file \"" + type2.getType() + "_bg.png\"");
			return null;
		}
		// Create new BufferedImage obj that is the be drawn onto
		result = new BufferedImage(1920, 1080, BufferedImage.TYPE_INT_ARGB);
		
		
		int height = type1img.getHeight(), width =  type1img.getWidth();
		/*
		 *  "line of separation" (LoS) is an imaginary line drawn from the top left corner to the top right corner of the image
		 *  and a pixels distance from it is used to determine how it should be fading into another type color set
		 */
		double m = (double) height / width; // slope used for a "line of separation"

		try 
		{
			// Iterate throughout the result image
			for (int i = 0; i < width; i++)
				for (int j = 0; j < height; j++)
				{
					double dist = (j - m * i); // horizontal distance from LoS
					// "(m * i + blendWidth/2)" is the "line of separation" depicting where the center of the card blend occurs
					if (-100 <= dist && dist <= 100)
					{
						// Determines the proportions of the fading depending on distance from line and on what side it is on
						double degree = dist / 200.0 + .5;
						Color color1 = new Color(type1img.getRGB(i, j)), 
								color2 = new Color(type2img.getRGB(i, j)), avgColor = null;
						
						
						// Colors are blended depending on which side of the LoS they are on and how far from it they are
						
						if (degree >= 0)
						{
							avgColor = new Color(
									(int) (degree * color1.getRed() + (1 - degree) * color2.getRed()),
									(int) (degree * color1.getGreen() + (1 - degree) * color2.getGreen()),
									(int) (degree * color1.getBlue() + (1 - degree) * color2.getBlue()),
									255
									);
						}
						else if (degree < 0)
						{
							degree = -degree;
							avgColor = new Color(
									(int) ((1 - degree) * color1.getRed() + degree * color2.getRed()),
									(int) ((1 - degree) * color1.getGreen() + degree * color2.getGreen()),
									(int) ((1 - degree) * color1.getBlue() + degree * color2.getBlue()),
									255
									);
						}
						
						// Draw blended colors onto result
						result.setRGB(i, j, (avgColor.getAlpha() << 24) + (avgColor.getRed() << 16)
								+ (avgColor.getGreen() << 8) + (avgColor.getBlue()));
					}
					// Anything far enough in the positive direction will become type1img
					else if (dist > 100)
					{
						result.setRGB(i, j, type1img.getRGB(i, j));
					}
					// Anything far enough in the positive direction will become type2img
					else
					{
						result.setRGB(i, j, type2img.getRGB(i, j));
					}
				}
			
			for (int i = 1485; i < 1879; i++)
				for (int j = 464; j < 1055; j++)
					result.setRGB(i, j, 0);
			
			return result;
		}
		catch (Exception e) // Check at what point the image processing failed
		{
			System.out.println("Error with background blending.");
			e.printStackTrace();
			return result;
		}
	}
}
