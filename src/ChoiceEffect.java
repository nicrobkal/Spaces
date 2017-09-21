import java.io.Serializable;

public class ChoiceEffect implements Serializable
{
	public static final String TRAIT_DAMAGED = "Damaged";
	
	public static final String TRAIT_FIXED = "Fixed";
	
	public static final String SKILL_FARMING = "Farming";
	public static final String SKILL_THIEVERY = "Thievery";
	public static final String SKILL_CHARISMA = "Charisma";
	public static final String SKILL_COMBAT = "Combat";
	public static final String SKILL_MINING = "Mining";
	public static final String SKILL_COMPUTING = "Computing";
	
	private Envelope objectAffected;
	private int amountAffected;
	private String trait;
	private Player playerChoosing;
	
	public ChoiceEffect(Player playerChoosing, Envelope objectAffected, int amountAffected, String trait)
	{
		this.playerChoosing = playerChoosing;
		this.objectAffected = objectAffected;
		this.amountAffected = amountAffected;
		this.trait = trait;
	}
	
	public void chooseThis()
	{
		if(objectAffected.getType().equals("Player") || objectAffected.getType().equals("Skill"))
		{
			switch(trait)
			{
			case TRAIT_DAMAGED:
				playerChoosing.takeDamage(amountAffected);
				break;
			case TRAIT_FIXED:
				playerChoosing.heal(amountAffected);
				break;
			case SKILL_FARMING:
				playerChoosing.improveSkill("Farming", amountAffected);
				break;
			case SKILL_THIEVERY:
				playerChoosing.improveSkill("Thievery", amountAffected);
				break;
			case SKILL_CHARISMA:
				playerChoosing.improveSkill("Charisma", amountAffected);
				break;
			case SKILL_COMBAT:
				playerChoosing.improveSkill("Combat", amountAffected);
				break;
			case SKILL_MINING:
				playerChoosing.improveSkill("Mining", amountAffected);
				break;
			case SKILL_COMPUTING:
				playerChoosing.improveSkill("Computing", amountAffected);
				break;
			default:
				break;
			}
		}
		else if(objectAffected.getType().equals("Building"))
		{
			
			Building building = (Building)objectAffected.getContents(Building.class);
			switch(trait)
			{
			case TRAIT_DAMAGED:
				building.takeDamage(playerChoosing, amountAffected);
				break;
			case TRAIT_FIXED:
				building.restore(playerChoosing, amountAffected);
				break;
			default:
				break;
			}
		}
	}
}
