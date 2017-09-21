import java.io.Serializable;

public class Skill implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String skillName;
	private String skillDesc;
	
	private Player player;
	
	private Building associatedBuilding;
	
	public int level;
	private int XP;
	
	public Skill(String skillName, String skillDesc, Player player, Building associatedBuilding)
	{
		this(skillName, skillDesc, player, associatedBuilding, 0);
	}
	
	public Skill(String skillName, String skillDesc, Player player, Building associatedBuilding, int Level)
	{
		this.skillName = skillName;
		this.skillDesc = skillDesc;
		this.player = player;
		this.associatedBuilding = associatedBuilding;
		this.level = level;
	}
	
	public String toString()
	{
		return skillName;
	}
	
	public void setPlayer(Player newPlayer)
	{
		this.player = newPlayer;
	}
	
	public String getDesc()
	{
		return skillDesc;
	}
	
	public int getXP()
	{
		return XP;
	}
	
	public void addXP(int addedXP)
	{
		XP += addedXP;
		if(XP >= 100)
		{
			level += XP/100;
			XP = XP%100;
			
			LevelUp();
		}
	}
	
	public int LevelUp()
	{
		//Level Up Text
		player.getClient().printMessageToAll(player.toString() + " has improved their " + skillName + " skill to level " + level + "!! ");
		
		return level;
	}
}
