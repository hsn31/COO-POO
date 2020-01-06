package network;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

//CLASSE CHAT TERMINEE LE 06_01_2020. TEST OK. 
public class Chat {

	//Pour savoir si le chat est actif, ou fermé
	private enum state {
		open,closed
	}
	
	//correspond à l'ID du correspondant. 
	private int distantid;

	private Message message;
	
	private state etat;
	
	private ArrayList<Message> ListOfMessages;
	
	public Chat (int distantId) { 
			this.distantid = distantId;
			this.ListOfMessages =new ArrayList<Message>();
			this.etat=state.open;
	}

	// to find a right list of messages with the distantid
	public ArrayList<Message> getListOfMessage(String distantid) {
		return ListOfMessages;
	}

	public int getDistantId() {
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





}
