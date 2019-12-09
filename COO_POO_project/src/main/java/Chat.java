import java.util.ArrayList;
import java.util.List;

public class Chat {
	
 private boolean open;
 private int distantid;
 private List<String> ListOfMessages = new ArrayList<String>();
 
 public List<String> getListOfMessage(int distantid) {
	 distantid = this.distantid;
     // with distantid find the right 
	 // ListOfMessages (with right account)
	 return ListOfMessages;
 }
 
 public void getDistantId() {
	 
 }
 
 public void StartChat() {
	 open = true;
 }
 
}
