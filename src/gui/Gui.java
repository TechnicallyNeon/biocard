package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import card.BackgroundUtil;
import card.DrawMisc;
import card.EvoBlock;
import card.ImageTextWriter;
import card.StatBlockUtil;
import card.Type;

/**
 * Gui class that runs the application
 * @author Nynon
 *
 */
public class Gui extends JFrame
{
	/**
	 * Input panel for the gui. All input objects are added to here.
	 */
	private static JPanel input;

	/**
	 * Text fields used for gathering user input that is not from an arranged set
	 */
	private static JTextField name, id1, id2, id3, hp, atk, def, spAtk, spDef, spe, evoCount, catchRate, 
	item1, item2, itemRate1, itemRate2, lvlup1, lvlup2, evoItem1, evoItem2;

	/**
	 * JComboBoxes used for more restricted user selection
	 */
	private static JComboBox<card.Type> type1, type2;

	private static JComboBox<String> genderRatio, ability1, ability2; 

	/**
	 * JButton used for signaling to the application to create a card with the given information
	 */
	private static JButton submitButton, clearButton;

	/**
	 * JRadioButtons used for marking if an ability is a hidden ability or not
	 */
	private static JRadioButton curr1, curr2, curr3, hidden1, hidden2;
	
	private static Document abilityDoc;

	/**
	 * Private constructor for constructing class objects and filling in important data
	 * @throws ParserConfigurationException - thrown if a reading error occurs from DocumentBuilder
	 */
	private Gui() throws ParserConfigurationException
	{
		////////////////////
		// Read XML files //
		////////////////////

		DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

		try
		{
			abilityDoc = docBuilder.parse("abilities.xml");
		} catch (SAXException e)
		{
			System.out.println("Parsing error occurred when reading abilites.xml");
		} catch (IOException e)
		{
			System.out.println("Unable to find abilities.xml");
			return;
		}


		//////////////////////////
		// Constructing objects //
		//////////////////////////

		// JPanels
		input = new JPanel();

		// JTextFields
		name = new JTextField();
		id1 = new JTextField();
		id2 = new JTextField();
		id3 = new JTextField();
		hp = new JTextField();
		atk = new JTextField();
		def = new JTextField();
		spAtk = new JTextField();
		spDef = new JTextField();
		spe = new JTextField();
		evoCount = new JTextField();
		catchRate = new JTextField();
		item1 = new JTextField();
		item2 = new JTextField();
		itemRate1 = new JTextField();
		itemRate2 = new JTextField();
		evoItem1 = new JTextField();
		evoItem2 = new JTextField();
		lvlup1 = new JTextField();
		lvlup2 = new JTextField();

		// JComboBoxes
		card.Type[] types = card.Type.getAllTypes();
		type1 = new JComboBox<card.Type>(types); // Has all types
		type2 = new JComboBox<card.Type>(); // Has all types plus a "No second type" as the default
		type2.addItem(card.Type.NULL);
		int length = types.length;
		for(int i = 0; i < length; i++)
			type2.addItem(types[i]);
		final String[] GENDER_RATIO = 
			{
					"1 to 1", "1 to 3", "3 to 1", "7 to 1", "Female Only",
					"Male Only", "None"
			};
		genderRatio = new JComboBox<String>(GENDER_RATIO);
		ability1 = new JComboBox<String>();
		ability2 = new JComboBox<String>();

		// JButtons
		submitButton = new JButton("Submit");
		clearButton = new JButton("Clear");

		// JRadioButtons
		curr1 = new JRadioButton("Current stage?");
		curr2 = new JRadioButton("Current stage?");
		curr3 = new JRadioButton("Current stage?");
		hidden1 = new JRadioButton("Hidden Ability?");
		hidden2 = new JRadioButton("Hidden Ability?");

		// .addActionListeners
		submitButton.addActionListener(new SubmitButtonAction());
		clearButton.addActionListener(new ClearActionListener());


		//////////////////////
		// Add ability info //
		//////////////////////

		NodeList abilityList = abilityDoc.getElementsByTagName("ability");
		ability2.addItem("No second ability");
		for (int i = 0; i < abilityList.getLength(); i++)
		{
			Node abilityNode = abilityList.item(i);
			if (abilityNode.getNodeType() == Node.ELEMENT_NODE)
			{
				NodeList tags = abilityNode.getChildNodes();
				for (int j = 0; j < tags.getLength(); j++)
				{
					Node tagNode = tags.item(j);
					if (tagNode.getNodeType() == Node.ELEMENT_NODE && tagNode.getNodeName().equals("name"))
					{
						String abilityName = tagNode.getTextContent();
						ability1.addItem(abilityName);
						ability2.addItem(abilityName);
					}
				}
			}
		}
	}

	public static void main(String[] args) throws ParserConfigurationException
	{
		Gui gui = new Gui();
		gui.setDefaultCloseOperation(EXIT_ON_CLOSE);
		gui.setSize(450, 600);
		gui.setTitle("Bio Card Creation");
		gui.setVisible(true);
		gui.setLocation(500, 300);


		////////////////////////////////
		// Adding elements to the GUI //
		////////////////////////////////

		gui.setLayout(new GridLayout(1, 2));

		input.setLayout(new GridLayout(0, 2));
		gui.add(input);

		input.add(new JLabel("Name: "));
		input.add(name);
		input.add(new JLabel("Id (Stage 1)"));
		input.add(id1);
		input.add(new JLabel());
		input.add(curr1);
		input.add(new JLabel("Id (Stage 2)"));
		input.add(id2);
		input.add(new JLabel());
		input.add(curr2);
		input.add(new JLabel("Id (Stage 3)"));
		input.add(id3);
		input.add(new JLabel());
		input.add(curr3);
		input.add(new JLabel("HP: "));
		input.add(hp);
		input.add(new JLabel("Attack: "));
		input.add(atk);
		input.add(new JLabel("Defence: "));
		input.add(def);
		input.add(new JLabel("Special Attack: "));
		input.add(spAtk);
		input.add(new JLabel("Special Defence: "));
		input.add(spDef);
		input.add(new JLabel("Speed: "));
		input.add(spe);
		input.add(clearButton);
		input.add(new JLabel(""));

		// Buffer
		input.add(new JLabel(""));
		input.add(new JLabel(""));

		// Labels for Typing
		input.add(new JLabel("First Type"));
		input.add(new JLabel("Second Type"));
		input.add(type1);
		input.add(type2);

		// Evo Block
		input.add(new JLabel("Number of Stages:"));
		input.add(new JLabel(""));
		input.add(evoCount);
		input.add(new JLabel(""));
		input.add(new JLabel("First Evolution Item:"));
		input.add(new JLabel("Second Evolution Item:"));
		input.add(evoItem1);
		input.add(evoItem2);
		input.add(new JLabel("First Evolution Condition:"));
		input.add(new JLabel("Second Evolution Condition:"));
		input.add(lvlup1);
		input.add(lvlup2);

		// Gender Ratios
		input.add(new JLabel("Catch Rate"));
		input.add(new JLabel("Gender Ratio"));
		input.add(catchRate);
		input.add(genderRatio);

		input.add(new JLabel("Item 1 Name | Drop Rate"));
		input.add(new JLabel("Item 2 Name | Drop Rate"));
		input.add(item1);
		input.add(item2);
		input.add(itemRate1);
		input.add(itemRate2);

		// Buffer
		input.add(new JLabel("Ability 1"));
		input.add(new JLabel("Ability 2"));

		// Add inputs for abilities
		input.add(ability1);
		input.add(ability2);
		input.add(hidden1);
		input.add(hidden2);

		// Buffer
		input.add(new JLabel(""));
		input.add(new JLabel(""));

		// Adding buttons for save data and submitting card data
		input.add(submitButton);

		/////////////////////////
		// Miscellaneous setup //
		/////////////////////////
		
	}

	private class SubmitButtonAction implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			// Get values from input and verify types
			// integers will send an error message if failed and default to zero
			String nameStr = name.getText();
			int idNum1 = 0, idNum2 = 0, idNum3 = 0, idCurr = 0;
			try { idNum1 = Integer.valueOf(id1.getText()); } // retrieve and verify
			catch (Exception e) { System.out.println("Please provide a valid id number. Put in zero if it is custom."); }
			if (!id2.getText().equals(""))
				idNum2 = Integer.valueOf(id2.getText());
			if (!id3.getText().equals(""))
				idNum3 = Integer.valueOf(id3.getText());
			if (curr1.isSelected() || curr2.isSelected() || curr3.isSelected())
				if (curr1.isSelected())
					idCurr = idNum1;
				else if (curr2.isSelected())
					idCurr = idNum2;
				else if (curr3.isSelected())
					idCurr = idNum3;
			else
				System.out.println("No selected current stage.");
			int[] stats = new int[6];
			try { stats[0] = Integer.valueOf(hp.getText()); }    catch (Exception e) { System.out.println("Invalid hp stat value"); }
			try { stats[1] = Integer.valueOf(atk.getText()); }   catch (Exception e) { System.out.println("Invalid attack stat value"); }
			try { stats[2] = Integer.valueOf(def.getText()); }   catch (Exception e) { System.out.println("Invalid defense stat value"); }
			try { stats[3] = Integer.valueOf(spAtk.getText()); } catch (Exception e) { System.out.println("Invalid special attack stat value"); }
			try { stats[4] = Integer.valueOf(spDef.getText()); } catch (Exception e) { System.out.println("Invalid special defense stat value"); }
			try { stats[5] = Integer.valueOf(spe.getText()); }   catch (Exception e) { System.out.println("Invalid speed stat value"); }
			int numStages = 0;
			try { numStages = Integer.valueOf(evoCount.getText()); }
			catch (NumberFormatException e) { System.out.println("Please provide how many stages there are. "); }
			String firstlvlup = lvlup1.getText(), secondlvlup = lvlup2.getText();
			int cr = 0;
			try { cr = Integer.valueOf(catchRate.getText()); } // retrieve and verify
			catch (Exception e) { System.out.println("Please provide a valid catch rate."); }
			String i1 = item1.getText(), i2 = item2.getText();
			int d1 = 0, d2 = 0;
			try { d1 = Integer.valueOf(itemRate1.getText()); d2 = Integer.valueOf(itemRate2.getText()); } // retrieve and verify
			catch (Exception e) { System.out.println("Please provide a valid drop rate."); }
			String abil1 = (String) ability1.getSelectedItem(), abil2 = (String) ability2.getSelectedItem();
			boolean h1 = hidden1.isSelected(), h2 = hidden2.isSelected();
			System.out.println(h1);
			System.out.println(abil1);
			if (h1)
				abil1 = "(Hidden) " + abil1;
			System.out.println(abil1);
			if (h2)
				abil2 = "(Hidden) " + abil2;
			String abilityDesc1 = "", abilityDesc2 = "";
			
					
			// create card
			// card background
			BufferedImage result = null;
			card.Type t1 = (card.Type) type1.getSelectedItem(), t2 = (card.Type) type2.getSelectedItem();
			if ((t2).getType().equals(card.Type.NULL.getType()))
				result = BackgroundUtil.create(t1);
			else
				result = BackgroundUtil.create(t1, t2);
			Graphics2D g2 = (Graphics2D) result.getGraphics();
			g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON); // antialiasing on text
			g2.setColor(Color.BLACK); // black text

			// Write name
			g2.setFont(new Font("Calibri", Font.BOLD, 48));
			ImageTextWriter.writeText(g2, 97, 51, 500, nameStr, true);
			
			// draw type icon
			if ((t2).getType().equals(card.Type.NULL.getType()))
				DrawMisc.drawIcon(g2, 809, 80, t1);
			else
			{
				DrawMisc.drawIcon(g2, 809, 41, t1);
				DrawMisc.drawIcon(g2, 809, 119, t2);
			}
				
			// draw evolution block
			EvoBlock.create(g2, idNum1, idNum2, idNum3, numStages, evoItem1.getText(), evoItem2.getText());
			
			// draw avatar
			DrawMisc.drawAvatar(g2, 75, 75, idCurr);
			
			// write catch rate
			g2.setFont(new Font("Calibri", Font.BOLD, 48));
			ImageTextWriter.writeText(g2, 838, 311, 500, "Catch Rate", true);
			g2.drawString(cr + " / 255", 
					(838 + g2.getFontMetrics().stringWidth("Catch Rate")/2) - 
					g2.getFontMetrics().stringWidth(cr + " / 255")/2, 370);
			
			// draw gender ratio
			DrawMisc.drawGenderRatio(g2, 0, 0, (String) genderRatio.getSelectedItem());

			// draw items
			g2.setFont(new Font("Calibri", Font.BOLD, 32));
			if (!i1.equals(""))
			{
				DrawMisc.drawItem(g2, 1519, 292, i1);
				ImageTextWriter.writeText(g2, 1569, 338, 500, " (" + d1 + "%)", true);
			}
			if (!i2.equals(""))
			{
				DrawMisc.drawItem(g2, 1519, 345, i2);
				ImageTextWriter.writeText(g2, 1569, 391, 500, " (" + d2 + "%)", true);
			}
			
			// draw stats
			StatBlockUtil.drawStats(result, 913, 464, stats);
			
			// write ability names
			ImageTextWriter.writeText(g2, 88, 782, 304, abil1, true);
			ImageTextWriter.writeText(g2, 428, 782, 304, abil2, true);
			
			// write ability descriptions
			NodeList abilityList = abilityDoc.getElementsByTagName("ability");
			for (int i = 0; i < abilityList.getLength(); i++)
			{
				Node abilityNode = abilityList.item(i);
				if (abilityNode.getNodeType() == Node.ELEMENT_NODE)
				{
					NodeList tags = abilityNode.getChildNodes();
					for (int j = 0; j < tags.getLength(); j++)
					{
						Node tagNode = tags.item(j);
						if (tagNode.getNodeType() == Node.ELEMENT_NODE && tagNode.getNodeName().equals("name"))
						{
							if (tagNode.getTextContent().equals(abil1))
								abilityDesc1 = tags.item(j+2).getTextContent();
							else if (tagNode.getTextContent().equals(abil2))
								abilityDesc2 = tags.item(j+2).getTextContent();
						}
					}
				}
			}
			ImageTextWriter.writeText(g2, 88, 782 + g2.getFontMetrics().getHeight(), 304, abilityDesc1, true);
			ImageTextWriter.writeText(g2, 428, 782 + g2.getFontMetrics().getHeight(), 304, abilityDesc2, true);
			
			// write evo conditions
			if (numStages == 2)
				ImageTextWriter.writeText(g2, 1484-g2.getFontMetrics().stringWidth(secondlvlup)/2, 
						122, 500, firstlvlup, false);
			else if (numStages == 3)
			{
				ImageTextWriter.writeText(g2, 1343-g2.getFontMetrics().stringWidth(secondlvlup)/2, 
						122, 500, firstlvlup, false);
				ImageTextWriter.writeText(g2, 1625 - g2.getFontMetrics().stringWidth(secondlvlup)/2, 
						122, 500, secondlvlup, false);
			}
				
			try
			{
				if (ImageIO.write(result, "png", new File("user/"+EvoBlock.itoa(idCurr)+nameStr+".png")))
					System.out.println("Finished making card.");
				else
					System.out.println("Image writing error occured. No valid image writer.");
			} catch (IOException e)
			{
				System.out.println("Image writing error occured.");
			}
		}
	}
	
	private class ClearActionListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			name.setText("");
			id1.setText("");
			id2.setText("");
			id3.setText("");
			curr1.setSelected(false);
			curr2.setSelected(false);
			curr3.setSelected(false);
			hp.setText("");
			atk.setText("");
			def.setText("");
			spAtk.setText("");
			spDef.setText("");
			spe.setText("");
		}
		
	}
}
