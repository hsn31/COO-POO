package network;


public class Message 
{

	private String text ="";
	private String date ="";
	private Origin nature;
	
	public enum Origin
	{
		SENT,
		RECEIVED,
	}
	
	// creates new message
	public Message(Origin naturePresented, String dateCalculated, String textPresented) {

		this.nature = naturePresented;
		this.date = dateCalculated;
		this.text= textPresented;
		
	}

	public String getDate() {
		return date;
	}
	
	public String getText() {
		return text;
	}
	
	
	public String returnMessage() {
		return date + "<s>" + text;
	}
    
}
