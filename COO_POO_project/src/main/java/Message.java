import java.lang.Object;
import java.sql.Time;

/*To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author väinö
 */
public class Message 
{

	private Time timeShown;
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
	
	public Time getTimeShown() {
		//take time of the message from Agent : ClickSend()
		return timeShown;
	}
	
	public String getText() {
		//take time of the message from Agent : ClickSend()
		return text;
	}


    
}
