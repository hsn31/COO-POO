package network;

import java.util.ArrayList;

//CLASSE CHAT TERMINEE LE 06_01_2020. TEST OK. 
public class Chat {

	//Pour savoir si le chat est actif, ou fermé
	private enum state {
		open,closed
	}
	
	//correspond à l'ID du correspondant (c'est l'adresse IP). 
	private String distantid;

	private Message message;
	
	private state etat;
	
	private ArrayList<Message> ListOfMessages;
	
	public Chat (String distantId) { 
			this.distantid = distantId;
			this.ListOfMessages =new ArrayList<Message>();
			this.etat=state.open;
	}

	public ArrayList<Message> getListOfMessage() {
		return ListOfMessages;
	}

	public String getDistantId() {
		return this.distantid;
	}
	
	public void AddMessage(Message text) {
		ListOfMessages.add(text);
	}

	public void StartChat() {
		this.etat = state.open;
	}

	public void CloseChat() {
		this.etat=state.closed;
	}

	// return message to account
	public String returnMessage(String message) {
		return message;
	}

}
