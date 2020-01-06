package card;

public enum Type
{	
	/*
	 * List of all potential types with respective strings given
	 */
	NORMAL("Normal"), FIRE("Fire"), FIGHTING("Fighting"), WATER("Water"), FLYING("Flying"), GRASS("Grass"), POISON("Posion"), 
	ELECTRIC("Electric"), GROUND("Ground"), PSYCHIC("Psychic"), ROCK("Rock"), ICE("Ice"), BUG("Bug"), DRAGON("Dragon"), 
	GHOST("Ghost"), DARK("Dark"), STEEL("Steel"), FAIRY("Fairy"), NULL("Normal");
	
	/**
	 * String form of Type used to determine how the card should be colored
	 */
	private String typeName;
	
	/**
	 * Creates a Type enum with a String determining color usage for the card
	 * @param name - name of the types color set to be used
	 */
	private Type(String name)
	{
		typeName = name;
	}
	
	/**
	 * Returns the color sets type name
	 * @return a name for the related to the Type specified
	 */
	public String getType()
	{
		return typeName;
	}
	
	public static String[] getAllTypes()
	{
		String[] arr = {
			"Normal", "Fire", "Fighting", "Water", "Flying",
			"Grass", "Poison", "Electric", "Ground", "Psychic",
			"Rock", "Ice", "Bug", "Dragon", "Ghost", "Dark",
			"Steel", "Fairy"
		};
		return arr;
	}
}