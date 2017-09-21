import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Scanner;

public class ClientCommandListener implements Runnable, KeyListener
{
	Client client;
	Scanner sc;
	public boolean isDead = false;
	
	public ClientCommandListener(Client client)
	{
		this.client = client;
		
		sc = new Scanner(System.in);
		
		Thread commandListener = new Thread(this);
		commandListener.start();
	}
	
	public void run()
	{
		while(!isDead)
		{
			String fullCommand = sc.nextLine();
			
			if(!isDead)
			{
				String[] commandList = fullCommand.split(" ");
				
				String args = "";
				
				for(int i = 1; i < commandList.length; i++)
				{
					args += commandList[i] + " ";
				}
				
				String firstCommand = commandList[0].toLowerCase();
				
				switch(firstCommand)
				{
				case "/t":
					client.getClientOutput().sendEnvelope(new Envelope(client.getClientPlayer().toString() + ": " + args, "String"));
					break;
					
				case "/exit":
					client.getClientOutput().sendEnvelope(new Envelope(client.getClientPlayer().toString() + " disconnected! ", "String"));
	        		
	            	client.getClientOutput().sendEnvelope(new Envelope(client.getSpaceStats(), "SpaceStats"));
	            	
	            	System.exit(0);
					break;
					
				case "/go":
					try
					{
						if(new Integer(commandList[1]) < client.getSpaceStats().buildingList.length)
						{
							client.getClientPlayer().setCurrBuilding(client.getSpaceStats().buildingList[(int)new Integer(commandList[1])]);
							client.printMessageToAll(client.getClientPlayer() + " has traveled to the " + client.getSpaceStats().buildingList[(int)new Integer(commandList[1])].toString().toLowerCase() + ". ");
						}
						else
						{
							throw new Exception();
						}
					}
					catch(Exception ex)
					{
						System.out.println("Building not valid! Try \"/buildings\". ");
					}
					break;
					
				case "/buildings":
					for(int i = 0; i < client.getSpaceStats().buildingList.length; i+=2)
					{
						System.out.print(i + ": " + client.getSpaceStats().buildingList[i].toString());
						
						if(i+1 < client.getSpaceStats().buildingList.length)
						{
							System.out.print("\t\t" + new Integer(i+1).toString() + ": " + client.getSpaceStats().buildingList[i+1].toString());
						}
						
						System.out.println("\n");
					}
					break;
					
				case "/skills":
					for(int i = 0; i < client.getSpaceStats().spaceSkills.length; i++)
					{
						System.out.println(client.getSpaceStats().spaceSkills[i].toString() + ": (Level " + client.getSpaceStats().spaceSkills[i].level + ") " + client.getSpaceStats().spaceSkills[i].getDesc());
					}
					break;
					
				case "/help":
					System.out.println("\nCommand List:");
					System.out.println("/t Sends a message. ");
					System.out.println("/skills Shows all your current skills and what they affect. ");
					System.out.println("/buildings Views all current buildings in the Space able to be traveled to. ");
					System.out.println("/go Uses a turn to go to the selected building. See \"/buildings\". ");
					System.out.println("/exit Leaves the game. ");
					
					System.out.println();
					break;
				}
			}
		}
	}
	
	public void keyPressed(KeyEvent arg0) 
	{
		int key = arg0.getKeyCode();
		
		if(key == KeyEvent.VK_ESCAPE)
		{
			client.getClientOutput().sendEnvelope(new Envelope(client.getClientPlayer().toString() + " has left the server! ", "String"));
			//client.getClientOutput().sendEnvelope(new Envelope(client.getClientPlayer().toString() + " has left the server! " ,"String"));
			client.getClientOutput().sendEnvelope(new Envelope(client.getSpaceStats(), "SpaceStats"));
		}
	}

	public void keyReleased(KeyEvent arg0) 
	{
	}

	public void keyTyped(KeyEvent arg0) 
	{
		
	}
}
