package network;

import java.util.ArrayList;

import network.Message.Origin;


public class Chat {
	
	//correspond à l'ID du correspondant (c'est l'adresse IP). 
	private String distantid;
	private Message message;

	
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
	
	public void AddMessage(Origin nature, String strDate, String text) {
		ListOfMessages.add(new Message(nature, strDate, text));
	}

}
