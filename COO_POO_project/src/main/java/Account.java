import java.util.ArrayList;
import java.util.List;

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
	String pseudonyme;
	boolean Active;
	List<Chat> listOfChat = new ArrayList<Chat>();
    
	
	
	 public List<Message> getChatHistory(int distantId) {
		 for (int i = 0; i < listOfChat.size(); i++) {
				 if (i == distantId) {
					 List<Message> chatHistory = Chat.getListOfMessage(distantId);
					 return chatHistory;
				 }		 
				 
	     }
		return null;
     }
	 
	 public void accountCreated() {
		 created = true;
	 }
	 
	 public void modifyPseudonyme(String pseudonyme) {
		 this.pseudonyme = pseudonyme;
	 }
}

