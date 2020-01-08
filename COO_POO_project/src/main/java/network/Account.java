package network;

//Il faudrait se coordonner sur les noms de packages. 

import java.util.*;
import java.lang.String;

/**
 *
 * @author peraire
 *
 */
 
 /*
 * Convention pour les messages du Broadcast...
 * 		ID == <1> | on veut se connecter => demande du tableau (?)
 * 		ID == <2> | connected 
 *      ID == <3> | update_pseudo 
 * 		ID == <4> | disconnected 
 */
 
public class Account {
	
	int id;
	boolean created;
	
	// to modifyPseudonyme it's static
	private String pseudonyme;
	
	boolean Active;
	private ArrayList<Chat> listOfChat = new ArrayList<Chat>(); 
	
	
	// kokeilu
	private ArrayList<Message> chatHistory;
    
	
	
	public ArrayList<Message> getChatHistory(int distantId) {
		 for (int i = 0; i < listOfChat.size(); i++) {
				 if (listOfChat.get(i).getDistantId() == distantId) {
					 chatHistory = listOfChat.get(i).getListOfMessage(distantId);
					 return chatHistory;
				 }		 
				 
	     }
		return null;
    }
	 	
	
	public void accountCreated(int id, String pseudonyme) {
		//A VERIFIER
		
		this.id = id;
		this.pseudonyme = pseudonyme;
		this.Active = true;	
		this.created = true;
	}
	
	// the meaning... set Active = true, check if active is true
	public boolean isOnline(String pseudonyme) {
			return Active;
	}


	 
	public void modifyPseudonyme(String pseudo) {
		pseudonyme = pseudo;
	}
}

