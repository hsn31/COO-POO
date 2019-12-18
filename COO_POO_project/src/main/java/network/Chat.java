package network;

import java.sql.Time;
import java.util.ArrayList;


public class Chat {
	
 private String distantid;
 private static ArrayList<Message> ListOfMessages = new ArrayList<Message>();
 // do we need a method for this as well? list of message needs 
 // an id to know which chat it belongs to
 
 
 
 // to find a right list of messages with the distantid
 public static ArrayList<Message> getListOfMessage(String distantid) {
	 return ListOfMessages;
	  
 }
 
 public String getDistantId() {
	 return this.distantid;
 }
 
 //A FAIRE
 public void StartChat() {
 }
 
 public void CloseChat() {
 }

public void AddMessage(Time ti, String te) {
	// add message  to ListOfMessages
	Message m = new Message(ti, te); // create message
	ListOfMessages.add(m);
}


 
}
