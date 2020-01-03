package card;

public enum Type
{	
	/*
	 * List of all potential types with respective strings given
	 */
	NORMAL("normal"), FIRE("fire"), FIGHTING("fighting"), WATER("water"), FLYING("flying"), GRASS("grass"), POISON("posion"), ELECTRIC("electric"),	
	GROUND("ground"), PSYCHIC("psychic"), ROCK("rock"), ICE("ice"), BUG("bug"), DRAGON("dragon"), GHOST("ghost"), DARK("dark"), STEEL("steel"), 
	FAIRY("fairy"), NULL("normal");
	
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
}