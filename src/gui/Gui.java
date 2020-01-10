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
	private static JTextField name, hp, atk, def, spAtk, spDef, spe, evoCount, catchRate, 
	item1, item2, itemRate1, itemRate2, lvlup1, lvlup2;

	/**
	 * JComboBoxes used for more restricted user selection
	 */
	private static JComboBox<card.Type> type1, type2;

	private static JComboBox<String> genderRatio, ability1, ability2; 

	/**
	 * JButton used for signaling to the application to create a card with the given information
	 */
	private static JButton submitButton;

	/**
	 * JRadioButtons used for marking if an ability is a hidden ability or not
	 */
	private static JRadioButton hidden1, hidden2;

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

		Document abilityDoc = null;
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

		// JRadioButtons
		hidden1 = new JRadioButton("Hidden Ability?");
		hidden2 = new JRadioButton("Hidden Ability?");

		// .addActionListeners
		submitButton.addActionListener(new SubmitButtonAction());


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
		gui.setSize(450, 500);
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

	// ActionListeners
	private class SummaryButtonAction extends JFrame implements ActionListener
	{
		private SummaryButtonAction()
		{
		}

		@Override
		public void actionPerformed(ActionEvent e)
		{
		}
	}

	private class SubmitButtonAction implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			// Get values from input
			String nameStr = name.getText();
			int[] stats = {
					Integer.valueOf(hp.getText()), Integer.valueOf(atk.getText()), Integer.valueOf(def.getText()),
					Integer.valueOf(spAtk.getText()), Integer.valueOf(spDef.getText()), Integer.valueOf(spe.getText())
			};
			try { int numStages = Integer.valueOf(evoCount.getText()); }
			catch (NumberFormatException e) { System.out.println("Please provide how many stages there are. "); }
			String firstlvlup = lvlup1.getText(), secondlvlup = lvlup2.getText();
			int cr = Integer.valueOf(catchRate.getText());
			String i1 = item1.getText(), i2 = item2.getText();
			int d1 = Integer.valueOf(itemRate1.getText()), d2 = Integer.valueOf(itemRate2.getText());
			String abil1 = (String) ability1.getSelectedItem(), abil2 = (String) ability2.getSelectedItem();
			boolean h1 = hidden1.isSelected(), h2 = hidden2.isSelected();
			
					
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

			// draw type icon
			if ((t2).getType().equals(card.Type.NULL.getType()))
				DrawMisc.drawIcon(g2, 809, 80, t1);
			else
			{
				DrawMisc.drawIcon(g2, 809, 41, t1);
				DrawMisc.drawIcon(g2, 809, 119, t2);
			}

			// draw items
			g2.setFont(new Font("Calibri", Font.BOLD, 32));
			if (!i1.equals(""))
			{
				DrawMisc.drawItem(g2, 1519, 292, i1);
				ImageTextWriter.writeText(g2, 1569, 338, 500, " (" + d1 + "%)", true);
			}
			if (!i2.equals(""))
				DrawMisc.drawItem(g2, 1519, 345, i2);
				ImageTextWriter.writeText(g2, 1569, 391, 500, " (" + d2 + "%)", true);
			
			// draw gender ratio
			DrawMisc.drawGenderRatio(g2, 0, 0, (String) genderRatio.getSelectedItem());
			
			// draw stats
			StatBlockUtil.drawStats(result, 913, 464, stats);
			
			
			

			// Writing text
			// Write name
			g2.setFont(new Font("Calibri", Font.BOLD, 48));
			ImageTextWriter.writeText(g2, 97, 51, 500, nameStr, true);
			// Write ability name
			
			
			
			try
			{
				System.out.println(ImageIO.write(result, "png", new File(nameStr+".png")));
			} catch (IOException e)
			{
				System.out.println(false);
			}
		}
	}
}
