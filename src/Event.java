import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class Event implements Comparable<Event>, Serializable
{
	private ArrayList<Choice> choiceList = new ArrayList<Choice>();
	
	private String uniqueTitle;
	
	private String eventDesc;
	
	private int priority;
	
	private transient Scanner sc = new Scanner(System.in);
	
	public Event(String uniqueTitle, String eventDesc, ArrayList<Choice> choiceList)
	{
		this(uniqueTitle, eventDesc, choiceList, 0);
	}
	
	public Event(String uniqueTitle, String eventDesc, ArrayList<Choice> choiceList, int priority)
	{
		this.uniqueTitle = uniqueTitle;
		this.eventDesc = eventDesc;
		this.choiceList = choiceList;
		this.priority = priority;
	}
	
	public String toString()
	{
		return eventDesc;
	}
	
	public void startEvent()
	{
		System.out.println(eventDesc);
		
		for(int i = 0; i < choiceList.size(); i+=2)
		{
			System.out.print(i + 1 + ": " + choiceList.get(i) + "\t");
			if(i+1 < choiceList.size())
			{
				System.out.print(i + 2 + ": " + choiceList.get(i+1));
			}
			System.out.println();
		}
		
		int choiceMade = 0;
		
		boolean choiceWasMade = false;
		
		try
		{
			choiceMade = sc.nextInt();
			if(choiceMade >= 1 && choiceMade <= choiceList.size() + 1)
			{
				choiceWasMade = true;
			}
			else
			{
				throw new Exception();
			}
		}
		catch(Exception ex)
		{
			choiceMade = 0;
		}
		
		while(!choiceWasMade || choiceMade < 1 || choiceMade > choiceList.size() + 1)
		{
			try
			{
				if(choiceMade >= 1 && choiceMade <= choiceList.size() + 1)
				{
					choiceWasMade = true;
				}
				else
				{
					System.out.println("Snap!");
					throw new Exception();		
				}
			}
			catch(Exception ex)
			{
				System.out.println("Choice invalid. Please try again. ");
				choiceMade = sc.nextInt();
			}
		}
		
		//System.out.println("WAIT A SECOND. ");
		choiceList.get(choiceMade-1).chooseThis(this);
	}

	public int compareTo(Event event) 
	{
		if(this.priority > event.priority)
		{
			return 1;
		}
		if(this.priority < event.priority)
		{
			return -1;
		}
		
		return 0;
	}
}
