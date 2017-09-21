import java.io.Serializable;

public class Building implements Serializable
{
	private String buildingName;
	private String buildingDesc;
	
	public int health = 100;
	
	public int level;
	public int MAX_LEVEL = 0;
	
	public Building(String buildingName, String buildingDesc)
	{
		this(buildingName, buildingDesc, 0);
	}
	
	public Building(String buildingName, String buildingDesc, int buildingLevel)
	{
		this(buildingName, buildingDesc, 0, 5);
	}
	
	public Building(String buildingName, String buildingDesc, int buildingLevel, int MAX_LEVEL)
	{
		this.buildingName = buildingName;
		this.buildingDesc = buildingDesc;
		this.level = buildingLevel;
		this.MAX_LEVEL = MAX_LEVEL;
	}
	
	public String toString()
	{
		return buildingName;
	}
	
	public String getDesc()
	{
		return buildingDesc;
	}
	
	public void takeDamage(Player damagingPlayer, int amount)
	{
		damagingPlayer.getClient().printMessageToAll(damagingPlayer.toString() + " has damaged the " + buildingName + " by " + amount + "percent!! ");
		health -= amount;
		
		if(health <= 0)
		{
			level = Math.abs(health)%100;
			
			health = 100;
			downgrade(damagingPlayer);
		}
	}
	
	public void restore(Player restoringPlayer, int amount)
	{	
		restoringPlayer.getClient().printMessageToAll(restoringPlayer.toString() + " has restored the " + buildingName + " by " + amount + "percent!! ");
		health += amount;
		if(health > 100)
		{
			int currLevel = level;
			level += health/100;
			health = 100;
			
			if(level >= MAX_LEVEL)
			{
				level = MAX_LEVEL;
			}
			
			if(currLevel < MAX_LEVEL)
			{
				upgrade(restoringPlayer);
			}
		}
	}
	
	public void downgrade(Player downgradingPlayer)
	{
		downgradingPlayer.getClient().printMessageToAll(downgradingPlayer.toString() + " has downgraded the " + buildingName + " to level " + level + "!! ");
	}
	
	public void upgrade(Player upgradingPlayer)
	{
		upgradingPlayer.getClient().printMessageToAll("The " + buildingName + " in town has upgraded to level " + level + "!! ");
	}
}
