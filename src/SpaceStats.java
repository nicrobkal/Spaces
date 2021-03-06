

import java.io.Serializable;

public class SpaceStats implements Serializable
{
	public String spaceName;
	public Skill[] spaceSkills = new Skill[0];
	public Player[] playerList = new Player[0];
	
	public Building[] buildingList;
	public Building startBuilding;
	
	static final long serialVersionUID = 1L;
	
	public SpaceStats(String spaceName)
	{
		this.spaceName = spaceName;
	}
	
	public String toString()
	{
		return spaceName;
	}
	
	public void addSkill(Skill newSkill)
	{
		Skill[] tempSkills = new Skill[spaceSkills.length + 1];
		
		for(int i = 0; i < spaceSkills.length; i++)
		{
			tempSkills[i] = spaceSkills[i];
		}
		
		tempSkills[tempSkills.length - 1] = newSkill;
		
		spaceSkills = tempSkills;
	}
	
	public void addPlayer(Player newPlayer)
	{
		Player[] tempPlayers = new Player[playerList.length + 1];
		
		for(int i = 0; i < playerList.length; i++)
		{
			tempPlayers[i] = playerList[i];
		}
		
		tempPlayers[tempPlayers.length - 1] = newPlayer;
		
		playerList = tempPlayers;
	}
	
	public Player getPlayer(String playerName)
	{
		for(int i = 0; i < playerList.length; i++)
		{
			if(playerList[i].toString().equals(playerName))
			{
				return playerList[i];
			}
		}
		return null;
	}
	
	public void updatePlayer(Player updatedPlayer)
	{
		for(int i = 0; i < playerList.length; i++)
		{
			if(playerList[i].toString().equals(updatedPlayer.toString()))
			{
				playerList[i] = updatedPlayer;
			}
		}
	}
	
	public void removePlayer(String playerName)
	{
		for(int i = 0; i < playerList.length; i++)
		{
			if(playerList[i].toString().equals(playerName))
			{
				playerList[i] = null;
				Player[] tempList = new Player[playerList.length - 1];
				int counter = 0;
				for(int j = 0; j < playerList.length - 1; j++)
				{
					if(playerList[j] != null)
					{
						tempList[counter] = playerList[j];
						counter++;
					}
					
					break;
				}
				playerList = tempList;
				break;
			}
		}
	}
	
	public boolean clientOnline(String playerName)
	{
		for(int i = 0; i < playerList.length; i++)
		{
			try
			{
				//System.out.println(playerList[i].getClient());
				if(playerList[i].getClient() != null)
				{
					//System.out.println("One not null. ");
					if(playerList[i].toString().equals(playerName))
					{
						return true;
					}
				}
			}
			catch(Exception ex)
			{
				System.out.println("Client not associated with Player. ");
			}
		}
		
		return false;
	}
	
	public void setBuildingList(Building[] buildings)
	{
		this.buildingList = buildings;
	}
}
