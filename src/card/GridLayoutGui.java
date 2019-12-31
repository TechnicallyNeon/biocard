package card;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

@SuppressWarnings("serial")
public class GridLayoutGui extends JFrame
{
	private static JPanel input;
	
	// "HP", "Attack", "Defense", "Mana", "Mana Defense", "Speed"
	private static JTextField name, hp, atk, def, spAtk, spDef, spe, evoCount, catchRate, 
	item1, item2, itemRate1, itemRate2, lvl1, lvl2;
	
	private static JTextArea sumText1, sumText2, sumText3;
	
	private static final String[] TYPELIST = 
		{
				"Normal", "Fire", "Fighting", "Water", "Flying",
				"Grass", "Poison", "Electric", "Ground", "Psychic",
				"Rock", "Ice", "Bug", "Dragon", "Ghost", "Dark",
				"Steel", "Fairy"
		};
	
	private static final String[] GENDER_RATIO = 
		{
			"1 to 1", "1 to 3", "3 to 1", "7 to 1", "Only Female",
			"Only Male", "None"
		};
	
	private static ArrayList<String> abilityName, abilityDesc;
	
	private static JComboBox<String> type1, type2, genderRatio;
	private static JComboBox<Object> ability1, ability2;
	
	private static JButton openFileButton, submitButton, summary;
	
	private static JRadioButton hidden1, hidden2;
	
	private static int[] stats = new int[6];
	
	private static String absFilePath = "Desktop";
	
	public GridLayoutGui() throws FileNotFoundException
	{
		//////////////////////
		// Get ability data //
		//////////////////////
		
		abilityName = new ArrayList<String>();
		abilityDesc = new ArrayList<String>();
		try
		{
			Scanner abScan = new Scanner(new File("components/abilities.txt"));

			while (abScan.hasNext())
			{
				abScan.useDelimiter(";");
				abilityName.add(abScan.next());
				abilityDesc.add(abScan.nextLine().substring(2 ));				
			}
			abScan.close();
		}
		catch(FileNotFoundException e)
		{
			throw new FileNotFoundException("Could not read abilities.txt");
		}
		
		//////////////////////////
		// Constructing objects //
		//////////////////////////
		
		input = new JPanel();

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
		lvl1 = new JTextField();
		lvl2 = new JTextField();
		
		type1 = new JComboBox<String>(TYPELIST);
		type2 = new JComboBox<String>();
		genderRatio = new JComboBox<String>(GENDER_RATIO);
		
		ability1 = new JComboBox<Object>(abilityName.toArray());
		abilityName.add(0, "None");
		ability2 = new JComboBox<Object>(abilityName.toArray());
		
		summary = new JButton("Text");
		openFileButton = new JButton("Save location");
		submitButton = new JButton("Submit");
		
		hidden1 = new JRadioButton("Hidden Ability?");
		hidden2 = new JRadioButton("Hidden Ability?");
		
		summary.addActionListener(new SummaryButtonAction());
		openFileButton.addActionListener(new OpenFileButtonAction());
		submitButton.addActionListener(new SubmitButtonAction());
	}
	
	public static void main(String[] args) throws FileNotFoundException
	{
		GridLayoutGui gui = new GridLayoutGui();
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
		input.add(lvl1);
		input.add(lvl2);
		
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
		
		// Button to open text prompts
		input.add(summary);
		input.add(new JLabel(""));
		input.add(new JLabel(""));
		input.add(new JLabel(""));
		
		// Adding buttons for save data and submitting card data
		input.add(openFileButton);
		input.add(submitButton);
		
		/////////////////////////
		// Miscellaneous setup //
		/////////////////////////
		
		// Defining ComboBox content		
		type2.addItem("No second type");
		for(int i = 0; i < TYPELIST.length; i++)
		type2.addItem(TYPELIST[i]);
	}
	
	private class OpenFileButtonAction implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
//			FileChooserGui fileChooser = new FileChooserGui();

			JFileChooser fileChooser = new JFileChooser(); 
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Select a Directory", "txt");
	        fileChooser.setFileFilter(filter);
	        int returnVal = fileChooser.showOpenDialog(getParent());
	        if(returnVal == JFileChooser.APPROVE_OPTION)
	           System.out.println("You chose to open this file: " +
	                fileChooser.getSelectedFile().getName());
		}
	}
	
	private class SummaryButtonAction extends JFrame implements ActionListener
	{
		private SummaryButtonAction()
		{
			sumText1 = new JTextArea();
			sumText2 = new JTextArea();
			sumText3 = new JTextArea();
		}
		
		@Override
		public void actionPerformed(ActionEvent e)
		{
			SummaryButtonAction sba = new SummaryButtonAction();
			sba.setDefaultCloseOperation(EXIT_ON_CLOSE);
			sba.setSize(500, 500);
			sba.setVisible(true);
			sba.setTitle("Text Inputs");
			sba.setLocation(520, 320);
			
			sba.setLayout(new GridLayout(0, 1));
			
			sba.add(sumText1);
			sba.add(sumText2);
			sba.add(sumText3);
		}
	}
	
	private class SubmitButtonAction implements ActionListener
	{
		private File data = new File("components/data.txt");
		
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			// Check for new stats to overwrite prev data
			try 
			{
				stats[0] = Integer.valueOf(hp.getText()); 
				stats[1] = Integer.valueOf(atk.getText());
				stats[2] = Integer.valueOf(def.getText()); 
				stats[3] = Integer.valueOf(spAtk.getText()); 
				stats[4] = Integer.valueOf(spDef.getText());
				stats[5] = Integer.valueOf(spe.getText());
				
				String saveData = "stats";
				for (int i = 0; i < 6; i++)
					saveData += " " + stats[i];
				saveData += System.lineSeparator() + "folder " + absFilePath;
				
				FileWriter fileWriter = new FileWriter(data);
				fileWriter.write(saveData);
				fileWriter.flush();
				fileWriter.close();
			}
			// if no new stat data, read from save file
			catch(Exception e)
			{
				System.out.println("Missing stat value. Using previously used stats.");
				
				try
				{
					Scanner statScan = new Scanner(data);
					statScan.next("stats");
					
					for (int i = 0; i < 6; i++)
						stats[i] = statScan.nextInt();
					statScan.close();
				} 
				catch (IOException e1)
				{
					System.out.println("||Error|| Stat data not found.");
				}
			}
			
			CharacterSheet charSheet;
			
			try
			{
				String typ1 = (String) type1.getSelectedItem(), 
						typ2 = (String) type2.getSelectedItem(),
						ab1Name = abilityName.get(ability1.getSelectedIndex() + 1) + ";", // "+ 1" since "None" was added to list
						ab2Name = null,
						ab1Desc = abilityDesc.get(ability1.getSelectedIndex()), 
						ab2Desc = null;
				if (typ2.equals("No second type"))
					typ2 = null;
				if (hidden1.isSelected())
					ab1Name = "(Hidden) " + ab1Name;
				if (!ability2.getSelectedItem().equals("None"))
				{
					ab2Name = abilityName.get(ability2.getSelectedIndex()) + ";";
					ab2Desc = abilityDesc.get(ability2.getSelectedIndex() - 1); // "- 1" since "None" was added to list
					if (hidden2.isSelected())
						ab2Name = "(Hidden) " + ab2Name;
				}
				
				charSheet = new CharacterSheet(stats, typ1, typ2);
				charSheet.setAbilities(ab1Name, ab1Desc, ab2Name, ab2Desc);
				charSheet.setGender((String) genderRatio.getSelectedItem());
				
				if (!item1.getText().equals("") && !item2.getText().equals(""))
				{
					charSheet.setItems(item1.getText(), Integer.valueOf(itemRate1.getText()), 
							item2.getText(), Integer.valueOf(itemRate2.getText()));
				}
				else if (!item1.getText().equals("") && item2.getText().equals(""))
				{
					charSheet.setItems(item1.getText(), Integer.valueOf(itemRate1.getText()), 
							item2.getText(), 0);
				}
				
				if (catchRate.getText().equals(""))
				{
					System.out.print("Please provide a catch rate.");
				}
				else
				{
					int cr = Integer.valueOf(catchRate.getText());
					if (cr < 0 || cr > 255)
						System.out.println("Catch rate must be between 0 and 255.");
					charSheet.setCatchRate(cr);
				}
				
				if (!evoCount.getText().equals(""))
					charSheet.setStages(Integer.valueOf(evoCount.getText()));
				
				charSheet.setConditions(lvl1.getText(), lvl2.getText());
				
				charSheet.createCard(name.getText());
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}
