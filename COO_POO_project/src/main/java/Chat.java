import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class Chat {
	
 private boolean open;
 private int distantid;
 private static List<Message> ListOfMessages = new ArrayList<Message>();
 // do we need a method for this as well? list of message needs 
 // an id to know which chat it belongs to
 
 
 
 // to find a right list of messages with the distantid
 public static List<Message> getListOfMessage(int distantid) {
	 return ListOfMessages;
	  
 }
 
 public void getDistantId() {
	 
 }
 
 public void StartChat() {
	 open = true; // when chat is opened, open = true;
 }
 
 public void CloseChat() {
	 open = false; // when chat is closed, open = false;
 }

public void AddMessage(Time ti, String te) {
	// add message  to ListOfMessages
	Message m = new Message(ti, te); // create message
	ListOfMessages.add(m);
}


 
}
