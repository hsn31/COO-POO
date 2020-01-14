package network;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import visual.ChatWindow;

//MISE A JOUR LE 06_01_2020.  OK.

public class Message 
{

	private String text;
	private Date date;
	
	// creates new message
	public Message(String textPresented) { 

		this.date = new Date();
		this.text= textPresented;
		
	}
	
	//pour reconstruire à partir d'une date précédente
	public Message(String textPresented, Date date) { 

		this.date = date;
		this.text= textPresented;
		
	}

	public Date getDate() {
		return date;
	}
	
	public String getText() {
		return text;
	}
	
	
	public String returnMessage() {
		String message = text;
		DateFormat dateFormat = new SimpleDateFormat("T D");  
        String strDate = dateFormat.format(date);  
		return message + " " + strDate;
	}


    
}
