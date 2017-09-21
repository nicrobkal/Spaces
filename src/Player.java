import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class Player implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String playerName = "ADMIN_OVERRIDE";
	public Skill[] skillList;
	private Building currBuilding;
	private Client client;
	public PriorityQueue<Event> eventQueue = new PriorityQueue<Event>();
	private int health = 100;
	private int actionPoints = 0;
	private ArrayList<Event> finishedEvents = new ArrayList<Event>();
	//private Occupation occupation
	//private int communityRank

	public int playerID;
	
	public Player(String playerName, Building currBuilding, int playerID, Client client)
	{
		this.playerName = playerName; 
		
		this.currBuilding = currBuilding;
		
		this.playerID = playerID;
		
		this.client = client;
		
		this.skillList = new Skill[0];
		skillList = client.getSpaceStats().spaceSkills;
		
		for(int i = 0; i < skillList.length; i++)
		{
			skillList[i].setPlayer(this);
		}
	}
	
	public String toString()
	{
		return playerName;
	}
	
	public void improveSkill(String improvedSkill, int addedXP)
	{
		System.out.println("You've gained " + addedXP + " XP in the " + improvedSkill + " skill!! ");
		getSkill(improvedSkill).addXP(addedXP);
	}
	
	public Client getClient()
	{
		return client;
	}
	
	public void setClient(Client client)
	{
		this.client = client;
	}
	
	public void setCurrBuilding(Building building)
	{
		this.currBuilding = building;
	}
	
	public void takeDamage(int amountDamaged)
	{
		health -= amountDamaged;
		if(health <= 0)
		{
			client.printMessageToAll(playerName + " died! Watch out for zombies! ");
			client.getSpaceStats().removePlayer(playerName);
			client.getClientOutput().sendEnvelope(new Envelope(client.getSpaceStats(), "SpaceStats"));
			System.out.println("You died! Please restart the client to play again! ");
			client.clientCommandListener.isDead = true;
			eventQueue = null;
		}
	}
	
	public void heal(int amountHealed)
	{
		health += amountHealed;
		if(health > 100)
		{
			health = 100;
		}
	}
	
	public Skill getSkill(String skill)
	{
		for(int i = 0; i < skillList.length; i++)
		{
			if(skillList[i].toString().equals(skill))
			{
				return skillList[i];
			}
		}
		
		return null;
	}
	
	public void createWelcomeEvent()
	{
		String welcomeEventMessage = "Welcome to Farm Space, " + playerName + "! \n";
		welcomeEventMessage += "This is a magical land because it's full of choices, which don't really exist in the real world. \n";
		welcomeEventMessage += "We're firm believers in the idea that everyone loves tutorials, so here's how to get started. \n";
		welcomeEventMessage += "Your first choice in this new world is to decide what the hell you want to do with your life. \n";
		welcomeEventMessage += "Here's a list of choices. Just type in the number that corresponds to the choice you want to make. ";
		
		ArrayList<Choice> welcomeEventChoiceList = new ArrayList<Choice>();
		
		ArrayList<ChoiceEffect> welcomeEventChoiceEffectsOne = new ArrayList<ChoiceEffect>();
		welcomeEventChoiceEffectsOne.add(new ChoiceEffect(this, new Envelope(null, "Player"), 100, ChoiceEffect.SKILL_FARMING));
		
		ArrayList<ChoiceEffect> welcomeEventChoiceEffectsTwo = new ArrayList<ChoiceEffect>();
		welcomeEventChoiceEffectsTwo.add(new ChoiceEffect(this, new Envelope(null, "Player"), 100, ChoiceEffect.SKILL_THIEVERY));
		
		ArrayList<ChoiceEffect> welcomeEventChoiceEffectsThree = new ArrayList<ChoiceEffect>();
		welcomeEventChoiceEffectsThree.add(new ChoiceEffect(this, new Envelope(null, "Player"), 100, ChoiceEffect.SKILL_CHARISMA));
		
		ArrayList<ChoiceEffect> welcomeEventChoiceEffectsFour = new ArrayList<ChoiceEffect>();
		welcomeEventChoiceEffectsFour.add(new ChoiceEffect(this, new Envelope(null, "Player"), 100, ChoiceEffect.SKILL_COMBAT));
		
		ArrayList<ChoiceEffect> welcomeEventChoiceEffectsFive = new ArrayList<ChoiceEffect>();
		welcomeEventChoiceEffectsFive.add(new ChoiceEffect(this, new Envelope(null, "Player"), 100, ChoiceEffect.SKILL_MINING));
		
		ArrayList<ChoiceEffect> welcomeEventChoiceEffectsSix = new ArrayList<ChoiceEffect>();
		welcomeEventChoiceEffectsSix.add(new ChoiceEffect(this, new Envelope(null, "Player"), 100, ChoiceEffect.SKILL_COMPUTING));
		
		welcomeEventChoiceList.add(new Choice(this, "Farming", "You've chosen to improve your farming skill! ", welcomeEventChoiceEffectsOne));
		welcomeEventChoiceList.add(new Choice(this, "Thievery", "You've chosen to improve your thievery skill! ", welcomeEventChoiceEffectsTwo));
		welcomeEventChoiceList.add(new Choice(this, "Charisma", "You've chosen to improve your charisma skill! ", welcomeEventChoiceEffectsThree));
		welcomeEventChoiceList.add(new Choice(this, "Combat", "You've chosen to improve your combat skill! ", welcomeEventChoiceEffectsFour));
		welcomeEventChoiceList.add(new Choice(this, "Mining", "You've chosen to improve your mining skill! ", welcomeEventChoiceEffectsFive));
		welcomeEventChoiceList.add(new Choice(this, "Computing", "You've chosen to improve your computing skill! ", welcomeEventChoiceEffectsSix));
		
		eventQueue.add(new Event("First Steps", welcomeEventMessage, welcomeEventChoiceList));
	}
	
	public void finishEvent()
	{
		finishedEvents.add(eventQueue.peek());
		eventQueue.poll();
		client.getSpaceStats().updatePlayer(this);
		client.getClientOutput().sendEnvelope(new Envelope(client.getSpaceStats(), "SpaceStats"));
	}
}
