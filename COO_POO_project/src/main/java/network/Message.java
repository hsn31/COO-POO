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

	public String getHistory() 
	{
		String deb = "";
		
		if(nature == Origin.SENT)
		{
			deb = "S";
		}
		else if(nature == Origin.RECEIVED)
		{
			deb = "R";
		}
		
		return deb + "<>" + date + "<>" + text;
	}

	public String getHTMLHistory() 
	{
		String balise = "<p>";
		
		if(nature.equals(Origin.RECEIVED))
		{
			//blanc : #FFFFFF
			balise = "<p color =#FFFFFF>";
		}
		else if(nature.equals(Origin.SENT))
		{
			//bleu : #0066FF
			balise = "<p color =#0066FF>";
		}
		
		String txtDate = balise + date + "</p>";
		String txtMessage = balise + text + "</p>";
		
		return "<br>" + txtDate + txtMessage;
	}
    
}
