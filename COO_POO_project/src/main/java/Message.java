import java.lang.Object;
import java.sql.Time;
import java.util.Date;


/**
 *
 * @author väinö
 */
public class Message 
{

	private Time timeShown;
	private String text;
	
	private String userID;
	private int chatID;
	private Date date;
	private String text;
	
	
	
	
	public Message(Time timeWhenClicked, String textPresented) { // creates new message
		// we get here from clickSend() (Application-class?)
		// time
		// 11.12: when we want to add a new message from Chat-class,
		// we get here. We give values for attributes time and string and
		// return it. How?
		timeWhenClicked =  getTimeShown();
		textPresented = getText();
		
	}
	
	/* Il faut se mettre d'accord sur le constructeur. 
	public Message(String userID,int chatID, String text) {
		this.userID = userID;
		this.chatID = chatID;
		this.date = new Date();
		this.text = text;
	}
	*/

	public int getChatID() {
		return chatID;
	}

	public String getUserID() {
		return userID;
	}

	public Date getDate() {
		return date;
	}
	
	public Time getTimeShown() {
		//take time of the message from Agent : ClickSend()
		return timeShown;
	}
	
	public String getText() {
		//take time of the message from Agent : ClickSend()
		return text;
	}


    
}
