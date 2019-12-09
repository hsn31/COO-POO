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

	private Time time;
	private String text;
	
	
	
	
	public Message(Time timeWhenClicked, String textWhenPresented) { // creates new message
		// we get here from clickSend() (Application-class?)
		Message message = new Message(timeWhenClicked, textWhenPresented);
		Chat.setNewMessage(message); //sets message to the chat history
	}
	
	
    
}
