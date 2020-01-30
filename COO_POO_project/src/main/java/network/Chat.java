package network;

import java.util.ArrayList;

import network.Message.Origin;


public class Chat {
	
	//correspond à l'ID du correspondant (c'est l'adresse IP). 
	private String distantid;
	//private Message message;

	private ArrayList<Message> ListOfMessages;
	
	public Chat (String distantId) { 
			this.distantid = distantId;
			this.ListOfMessages =new ArrayList<Message>();
	}

	public ArrayList<Message> getListOfMessage() {
		return ListOfMessages;
	}

	public String getDistantId() {
		return this.distantid;
	}
	
	public Chat AddMessage(Origin nature, String strDate, String text) {
		ListOfMessages.add(new Message(nature, strDate, text));
		return this;
	}
	
	public String getHistory()
	{
		//gros string qui contient un message par ligne, donc chaque ligne termine par \n
		String result = "";
		
		for(int i=0; i < ListOfMessages.size(); i++)
		{
			result = result + ListOfMessages.get(i).getHistory() + "\n";
		}
		
		return result;
	}

	public String getHTMLHistory() 
	{
		String local_history = "";
		
		for(int i=0; i < ListOfMessages.size(); i++)
		{
			local_history = local_history + ListOfMessages.get(i).getHTMLHistory();
		}
		
		return local_history;
	}

}
