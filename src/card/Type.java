package card;

public enum Type
{	
	/*
	 * List of all potential types with respective strings given
	 */
	NORMAL("Normal"), FIRE("Fire"), FIGHTING("Fighting"), WATER("Water"), FLYING("Flying"), GRASS("Grass"), POISON("Poison"), 
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
	
	/**
	 * Returns an array of all Type objects except null
	 * @return - an array of all Type objects except null
	 */
	public static Type[] getAllTypes()
	{
		Type[] arr = {
			NORMAL, FIRE, FIGHTING, WATER, FLYING, GRASS, POISON, ELECTRIC, GROUND, PSYCHIC, ROCK, ICE, BUG, DRAGON, 
			GHOST, DARK, STEEL, FAIRY
		};
		return arr;
	}
}