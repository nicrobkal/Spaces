import java.io.Serializable;
import java.util.ArrayList;

public class Choice implements Serializable
{
	private Player playerAffected;
	
	private String choiceName;
	
	private String choiceOutcome;
	
	private Skill choiceRequirement;
	
	private ArrayList<ChoiceEffect> choiceEffects;
	
	private int skillRequired = 0;
	
	
	public Choice(Player playerAffected, String choiceName, String choiceOutcome, ArrayList<ChoiceEffect> choiceEffects)
	{
		this(playerAffected, choiceName, choiceOutcome, choiceEffects, null, 0);
	}
	
	public Choice(Player playerAffected, String choiceName, String choiceOutcome, ArrayList<ChoiceEffect> choiceEffects, Skill choiceRequirement, int skillRequired)
	{
		this.playerAffected = playerAffected;
		
		this.choiceName = choiceName;
		
		this.choiceOutcome = choiceOutcome;
		
		this.choiceRequirement = choiceRequirement;
		
		this.choiceEffects = choiceEffects;
		
		this.skillRequired = skillRequired;
	}
	
	public String toString()
	{
		return choiceName;
	}
	
	public void addChoiceEffect(ChoiceEffect choiceEffect)
	{
		choiceEffects.add(choiceEffect);
	}
	
	//Method called when this was the choice selected
	public void chooseThis(Event event)
	{
		try
		{
			if(choiceRequirement.toString() != null)
			{
				if(playerAffected.getSkill(choiceRequirement.toString()).level >= skillRequired)
				{
					System.out.println(choiceOutcome);
					for(int i = 0; i < choiceEffects.size(); i++)
					{
						choiceEffects.get(i).chooseThis();
						playerAffected.finishEvent();
					}
				}
				else
				{
					playerAffected.getClient().printMessage("You've chosen poorly! You don't have a high enough " + choiceRequirement.toString() + " skill to accomplish your task! ");
					playerAffected.getClient().printMessage("You've lost " + ((skillRequired - playerAffected.getSkill(choiceRequirement.toString()).level)*10) + " health in the process! ");
				}
			}
		}
		catch(Exception ex)
		{
			System.out.println(choiceOutcome);
			for(int i = 0; i < choiceEffects.size(); i++)
			{
				choiceEffects.get(i).chooseThis();
			}
		}
	}
}
