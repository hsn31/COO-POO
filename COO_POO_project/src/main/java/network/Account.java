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
 * <1> pseudo_already_used_?
 * <2> connected
 * <3> update_pseudo
 * <4> disconnected
 */
 
public class Account {
	
	int id;
	boolean created;
	
	// to modifyPseudonyme it's static
	static String pseudonyme;
	
	boolean Active;
	static ArrayList<Chat> listOfChat = new ArrayList<Chat>();
    
	
	
	public static ArrayList<Message> getChatHistory(String distantId) {
		 for (int i = 0; i < listOfChat.size(); i++) {
				 if (listOfChat.get(i).getDistantId() == distantId) {
					 ArrayList<Message> chatHistory = Chat.getListOfMessage(distantId);
					 return chatHistory;
				 }		 
				 
	     }
		return null;
    }
	 
	
	//A FAIRE. 
	//A VERIFIER -On le fait dans account ou NetworkManager
	
	public int broadcastPseudoAlreadyUsed() {
		return 0;
	}
	
	public int broadcastConnected() {
		return 0;
	}
	 
	public int broadcastUpdatePseudo() {
		return 0;
	}

	public int broadcastDisconnected() {
		return 0;
	}
	
	
	public static void accountCreated(int id, String pseudonyme) {
		//A VERIFIER
		
		/*	this.id = id;
		this.pseudonyme = pseudonyme;
		this.Active = true;	*/ 
	}
	
	//A faire
	public boolean isOnline(String pseudonyme) {
		return false;
	}
	 
	public static void modifyPseudonyme(String pseudo) {
		pseudonyme = pseudo;
	}
}

