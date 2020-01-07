package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

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
	private static JComboBox<String> type1, type2, genderRatio, ability1, ability2;

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
		String[] types = card.Type.getAllTypes();
		type1 = new JComboBox<String>(types); // Has all types
		type2 = new JComboBox<String>(); // Has all types plus a "No second type" as the default
		type2.addItem("No second type");
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
			// TODO Create needed JTextAreas
		}

		@Override
		public void actionPerformed(ActionEvent e)
		{
			// TODO Ability to add abilities
		}
	}

	private class SubmitButtonAction implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			// TODO Create card
		}
	}
}
