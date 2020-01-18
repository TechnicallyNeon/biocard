package card;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class EvoBlock
{
	private EvoBlock()
	{
		
	}
	
	public static void create(Graphics2D g2, int id1, int id2, int id3, int stages, String evo1, String evo2)
	{
		File[] stageFiles = new File[stages]; // box icon files
		// verify
		try { stageFiles[0] = new File("components/box_icon/" + itoa(id1) + "MS.png"); }
		catch (Exception e) { System.out.println("Could not read box icon for id " + id1); }
		try { stageFiles[1] = new File("components/box_icon/" + itoa(id2) + "MS.png"); }
		catch (Exception e) { System.out.println("Could not read box icon for id " + id2); }
		try { stageFiles[2] = new File("components/box_icon/" + itoa(id3) + "MS.png"); }
		catch (Exception e) { System.out.println("Could not read box icon for id " + id3); }
		BufferedImage[] stageImages = new BufferedImage[stages]; // box icon images
		for (int i = 0; i < stages; i++)
		{
			BufferedImage curr = null;
			// read image file
			try { curr = ImageIO.read(stageFiles[i]); } 
			catch (Exception e) { System.out.println("Problems reading file " + stageFiles[i].getName()); }
			// resize if necessary
//			if (!Resizer.isSize(stageImages[i], 120, 120))
//				Resizer.resize(curr, 120, 120);
			stageImages[i] = curr;
			
		}
		switch (stages)
		{
			case 1:
				g2.drawImage(stageImages[0], 1424, 51, null);
				break;
			case 2:
				try { g2.drawImage(ImageIO.read(new File("components/evo_icon/Evo2.png")), 0, 0, null); }
				catch (Exception e) { System.out.println("Problems reading evo arrow image"); }
				File evoIcon = new File("components/evo_icon/Evo"+evo1+".png");
				g2.drawImage(stageImages[0], 1283, 51, null);
				g2.drawImage(stageImages[1], 1565, 51, null);
				try { g2.drawImage(ImageIO.read(evoIcon), 1460, 51, null); }
				catch (Exception e) { System.out.println("Problems reading " + evoIcon.getName()); }
				break;
			case 3:
				try { g2.drawImage(ImageIO.read(new File("components/evo_icon/Evo3.png")), 0, 0, null); }
				catch (Exception e) { System.out.println("Problems reading evo arrow image"); }
				File evoIcon1 = new File("components/evo_icon/Evo"+evo1+".png"),
					evoIcon2 = new File("components/evo_icon/Evo"+evo2+".png");
				g2.drawImage(stageImages[0], 1142, 51, null);
				g2.drawImage(stageImages[1], 1424, 51, null);
				g2.drawImage(stageImages[2], 1706, 51, null);
				try { g2.drawImage(ImageIO.read(evoIcon1), 1319, 51, null); }
				catch (Exception e) { System.out.println("Problems reading " + evoIcon1.getName()); }
				try { g2.drawImage(ImageIO.read(evoIcon2), 1601, 51, null); }
				catch (Exception e) { System.out.println("Problems reading " + evoIcon2.getName()); }
				break;
		}
	}
	
	public static String itoa(int i)
	{
		String tempId = String.valueOf(i);
		switch (tempId.length())
		{
			case 1:
				tempId = "00" + tempId;
				break;
			case 2:
				tempId = "0" + tempId;
				break;
			default:
				break;
		}
		return tempId;
	}
}
