import java.util.ArrayList;
import java.util.List;

public class Chat {
	
 private boolean open;
 private int distantid;
 private List<Message> ListOfMessages = new ArrayList<Message>();
 
 public List<Message> getListOfMessage(int distantid) {
	 this.distantid = distantid;
     // with distantid find the right 
	 // ListOfMessages (with right account)
	 // then we can return the RIGHT list of messages 
	 // for who wants it
	 return ListOfMessages;
 }
 
 public void getDistantId() {
	 
 }
 
 public void StartChat() {
	 open = true; // when chat is opened, open = true;
 }

public static void setNewMessage(Message message) {
	// set message  to ListOfMessages
	
}


 
}
