package card;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.NoSuchFileException;

import javax.imageio.ImageIO;

public class CharacterSheet
{
	private String alignment1, alignment2;

	// Related card
	private BufferedImage card1, card2;

	private int width;
	private int height;

	private int[] stats;
	
	private String ability1Name, ability1Desc, ability2Name, ability2Desc;
	
	private String item1, item2;
	private int item1Rate, item2Rate;
	
	private int catchRate;
	
	private int stages = 1;
	
	private String lvlup1, lvlup2;
	
	private String condEvo;
	private int condEvoPos;
	
	private File gender;

	public CharacterSheet(int[] stats, String al1, String al2) throws IOException
	{
		alignment1 = al1;
		alignment2 = al2;

		this.stats = stats;

		card1 = ImageIO.read(new File("components/types/Bio" + al1 + ".png"));

		if (al2 != null)
			card2 = ImageIO.read(new File("components/types/Bio" + al2 + ".png"));


		width = card1.getWidth();
		height = card1.getHeight();
	}

	/**
	 * Creates a png with the generated stat block an pastes it onto a character sheet template that is colored
	 * accordingly to the type specified
	 * @param name - name of file without the extention
	 * @throws IOException
	 * @throws UnsupportedFlavorException 
	 */
	public void createCard(String name) throws IOException
	{
		BufferedImage cardTemplate;

		if (card2 == null)
			cardTemplate = card1;
		else
			cardTemplate = dualType();


		BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		
		Graphics2D graphics = (Graphics2D) result.getGraphics();
		graphics.setColor(Color.BLACK);
		
		graphics.drawImage(cardTemplate, 0, 0, null); // Add card background
		
		graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, 
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		//////////////////////////////
		// Drawing text on template //
		//////////////////////////////
		
		// Draw name
		graphics.setFont(new Font("Calibri", Font.BOLD, 61));
		graphics.drawString(name, 97, 51); 
		
		// Stat values and catch rate
		graphics.setFont(new Font("Calibri", Font.BOLD, 48));
		graphics.drawString("Catch Rate", 838, 311);
		graphics.drawString(catchRate + " / 255", 
				(838 + graphics.getFontMetrics().stringWidth("Catch Rate")/2) - 
				graphics.getFontMetrics().stringWidth(catchRate + " / 255")/2, 370);
		
		// General
		graphics.setFont(new Font("Calibri", Font.BOLD, 32));
		graphics.drawString(ability1Name, 88, 782);
		fDrawString(ability1Desc, 88, 782 + graphics.getFontMetrics().getHeight(), 304, graphics.getFontMetrics(), graphics);
		if (ability2Name != null)
		{
			graphics.drawString(ability2Name, 428, 782);
			fDrawString(ability2Desc, 428, 782 + graphics.getFontMetrics().getHeight(), 304, graphics.getFontMetrics(), graphics);
		}
		
		if (card2 == null) // Place type icons
		{
			graphics.drawImage(ImageIO.read(new File("components/type-icons/Icon" + alignment1 + ".png")), 809, 80, null);
		}
		else
		{
			graphics.drawImage(ImageIO.read(new File("components/type-icons/Icon" + alignment1 + ".png")), 809, 41, null);
			graphics.drawImage(ImageIO.read(new File("components/type-icons/Icon" + alignment2 + ".png")), 809, 119, null);
		}
		
		// Evo block
		if (stages > 1)
				graphics.drawImage(ImageIO.read(new File("components/arrows/Evo" + stages + ".png")), 0, 0, null);
		File f1 = new File("stage1.png"), f2 = new File("stage2.png"), f3 = new File("stage3.png");
		BufferedImage s1 = null, s2 = null, s3 = null;
		try { s1 = ImageIO.read(f1); }
		catch (Exception e) { System.out.println("No stage1.png found."); }
		try { s2 = ImageIO.read(f2); }
		catch (Exception e) { System.out.println("No stage2.png found."); }
		try { s3 = ImageIO.read(f3); }
		catch (Exception e) { System.out.println("No stage3.png found."); }
		switch (stages)
		{
			case 1:
				graphics.drawImage(s1, 1424, 51, null);
				break;
			case 2:
				graphics.drawImage(s1, 1283, 51, null);
				graphics.drawImage(s2, 1565, 51, null);
				graphics.drawImage(ImageIO.read(new File("evoicon1.png")), 1460, 51, null);
				graphics.drawString(lvlup1, 1484 - graphics.getFontMetrics().stringWidth(lvlup1)/2, 
						122 + graphics.getFontMetrics().getHeight());
//				if (condEvo != null && condEvoPos == 1)
//					graphics.drawString(condEvo, 1484 - graphics.getFontMetrics().stringWidth(condEvo)/2, 
//							185);
				break;
			case 3:
				graphics.drawImage(s1, 1142, 51, null);
				graphics.drawImage(s2, 1424, 51, null);
				graphics.drawImage(s3, 1706, 51, null);
				graphics.drawImage(ImageIO.read(new File("evoicon1.png")), 1319, 51, null);
//				graphics.drawString(lvlup1, 1343 - graphics.getFontMetrics().stringWidth(lvlup1)/2, 
//						122 + graphics.getFontMetrics().getHeight());
				fDrawString(lvlup1, 1343-graphics.getFontMetrics().stringWidth(lvlup1)/2, 
						122+graphics.getFontMetrics().getHeight(), 250, graphics.getFontMetrics(), graphics);
				try
				{
					graphics.drawImage(ImageIO.read(new File("evoicon2.png")), 1601, 51, null);
//					graphics.drawString(lvlup2, 1625 - graphics.getFontMetrics().stringWidth(lvlup2)/2, 
//							122 + graphics.getFontMetrics().getHeight());
					fDrawString(lvlup2, 1625-graphics.getFontMetrics().stringWidth(lvlup2)/2, 
							122+graphics.getFontMetrics().getHeight(), 250, graphics.getFontMetrics(), graphics);
				}
				catch(FileNotFoundException e)
				{
					graphics.drawImage(ImageIO.read(new File("evoicon1.png")), 1601, 51, null);
//					graphics.drawString(lvlup2, 1625 - graphics.getFontMetrics().stringWidth(lvlup2)/2, 
//							122 + graphics.getFontMetrics().getHeight());
				}
				if (condEvo != null && condEvoPos == 2)
					graphics.drawString(condEvo, 1625 - graphics.getFontMetrics().stringWidth(condEvo)/2, 
							185);
				break;
		}
		
		// Draw item 1
		if (item1 != null && !item1.equals(""))
		{
			graphics.drawImage(ImageIO.read(new File("item1.png")), 1519, 292, null);
			graphics.drawString(item1 + " (" + item1Rate + "%)", 1569,  338);
		}
		if (item2 != null && !item2.equals(""))
		{
			graphics.drawImage(ImageIO.read(new File("item2.png")), 1519, 345, null);
			graphics.drawString(item2 + " (" + item2Rate + "%)", 1569,  391);
		}
		
		graphics.drawImage(ImageIO.read(new File("profile.png")), 75, 75, null); // Add profile
		
		graphics.drawImage(ImageIO.read(gender), 0, 0, null);
		
		graphics.dispose();
		StatBlock.drawStats(result, 913, 464, stats);

		ImageIO.write(result, "png", new File(name + ".png"));
	}

	private BufferedImage dualType() throws IOException
	{
		double m = (double) height / width;
		BufferedImage template = card1;
		int blendWidth = 198;

		try 
		{
			for (int i = 0; i < template.getWidth(); i++)
				for (int j = 0; j < template.getHeight(); j++)
					if ((j - (m * i + blendWidth/2)) * (j - (m * i + blendWidth/2)) - (blendWidth * blendWidth) < 1e-3) // Blending based on distance to line of seperation
					{
						double degree = (j - (m * i + blendWidth/2)) / 200; // Change denominator such that distance is equivalent to proportion

						if (degree < 0)
						{
							degree = -degree;
							Color color1 = new Color(card1.getRGB(i, j));
							Color color2 = new Color(card2.getRGB(i, j));
							Color avgColor = new Color(
									(int) ((1 - degree) * color1.getRed() + degree * color2.getRed()),
									(int) ((1 - degree) * color1.getGreen() + degree * color2.getGreen()),
									(int) ((1 - degree) * color1.getBlue() + degree * color2.getBlue()),
									255
									);

							template.setRGB(i, j, (avgColor.getAlpha() << 24) + (avgColor.getRed() << 16)
									+ (avgColor.getGreen() << 8) + (avgColor.getBlue()));
						}
					}
					else if (j < (m * i + blendWidth/2)) // Makes rest of the card other type
					{
						Color color = new Color(card2.getRGB(i, j), true);
						template.setRGB(i, j, (color.getAlpha() << 24) + (color.getRed() << 16)
								+ (color.getGreen() << 8) + (color.getBlue()));
					}
			
			for (int i = 1485; i < 1879; i++)
				for (int j = 464; j < 1055; j++)
					template.setRGB(i, j, new Color(0, 0, 0, 0).getRGB());
		}
		catch (Exception e) // Check at what point the image processing failed
		{
			return template;
		}

		return template;
	}
	
	public void setGender(String fileName)
	{
		gender = new File("components/gender-ratios/Gender" + fileName + ".png");
	}
	
	public void setAbilities(String ab1Name, String ab1Desc, String ab2Name, String ab2Desc)
	{
		ability1Name = ab1Name;
		ability1Desc = ab1Desc;
		if (ab2Name != null)
		{
			ability2Name = ab2Name;
			ability2Desc = ab2Desc;
		}
	}
	
	public void setItems(String i1Name, int i1Rate, String i2Name, int i2Rate)
	{
		item1 = i1Name;
		item2 = i2Name;
		item1Rate = i1Rate;
		item2Rate = i2Rate;
	}
	
	public void setCatchRate(int r)
	{
		catchRate = r;
	}
	
	public void setStages(int s)
	{
		if (s > 3 || s < 1)
			System.out.println("Stages should be between 1 and 3");
		else
			stages = s;
	}
	
	public void setConditions(String cond1, String cond2)
	{
		lvlup1 = cond1;
		lvlup2 = cond2;
	}
	
	private static void fDrawString(String text, int x, int y, int w, FontMetrics fm, Graphics2D g2)
	{
		if (text == null)
			return;
		
		int lineH = fm.getHeight();
		String[] textArr = text.split(" ");
		int i = 0;
		while (i < textArr.length)
		{
			String line = textArr[i++];
			while ((i < textArr.length) && (fm.stringWidth(line + " " + textArr[i]) < w))
				line = line + " " + textArr[i++];
			g2.drawString(line, x, y);
			y += lineH;
		}
	}
}
