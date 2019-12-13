import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author peraire
 */
public class Account {
	
	int id;
	boolean created;
	
	// to modifyPseudonyme its static
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
	 
	 public void accountCreated(int id, String pseudonyme) {
		 this.id = id;
		 this.pseudonyme = pseudonyme;
		 this.Active = true;	 
		 created = true;
	 }
	 
	 public static void modifyPseudonyme(String pseudo) {
		 pseudonyme = pseudo;
	 }
}

