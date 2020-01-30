package network;

import java.util.*;

import network.Message.Origin;
 
//TEST OK le 08_01_2020
 /*
 * Convention pour les messages du Broadcast...
 * 		ID == <1> | on veut se connecter => demande de tous les active users (pas de tableau !!)
 * 		ID == <2> | connected 
 *      ID == <3> | update_pseudo 
 * 		ID == <4> | disconnected
 */
 
public class Account {
	
	private boolean createdLastConnection; //pour savoir comment démarrer l'application
	private String pseudonyme="";
	private String ipAdress ="";
	
	private LinkedHashMap<String, Chat> ListOfChat; //string = distantIpAddress
	
	public Account(boolean alreadyCreated, String ipAdress, String pseudonyme) {
		this.ListOfChat =new LinkedHashMap<String, Chat>();
		this.createdLastConnection = alreadyCreated;
		this.ipAdress=ipAdress;
		this.pseudonyme = pseudonyme;	
	}
	 	
	public String getPseudo() {
		return this.pseudonyme;
	}
	
	public ArrayList<String> getDistantChat(){
		
		ArrayList<String> addresses_chats = new ArrayList<String>();
		
		ListOfChat.forEach((k,v)->
		{
			addresses_chats.add(k);
		});
		
		return addresses_chats;
	}
	
	//Retourne le Chat en lien avec ce DistantID
	public String getChatHistory(String distant) 
	{
		String temp= "";
		
		System.out.println("*******getChatHistory ACCOUNT*************************"+ ListOfChat + "\n");
		
		for (int i=0; i<ListOfChat.size(); i++) 
		{
			System.out.println("*******getChatHistory ACCOUNT.... distant = "+  distant);
			
			if ((ListOfChat.get(i).getDistantId()).equals(distant)) 
			{
				temp = ListOfChat.get(i).getHistory();
			}
		}
		
		return temp;
	}
	
	public String getChatHTMLHistory(String id) 
	{
		String temp= "";
		
		for (int i=0; i<ListOfChat.size(); i++) 
		{
			if ((ListOfChat.get(i).getDistantId()).equals(id)) 
			{
				temp = ListOfChat.get(i).getHTMLHistory();
			}
		}
		
		return temp;
	}
	
	public boolean chatIsCreated(String id)
	{
		return ListOfChat.containsKey(id);
	}
	
	
	public String getIp() 
	{
		return this.ipAdress;
	}
	
	public boolean wasCreated()
	{
		return this.createdLastConnection;
	}
	
	public void modifyPseudonyme(String pseudo) 
	{
		this.pseudonyme = pseudo;
	}
	
	public void registerMessage(Origin nature, String distantAddress, String strDate, String message)
	{
		System.out.println("*******RegisterMessage ACCOUNT*************************"+ ListOfChat);
		
		//Pour éviter les erreurs. 
		if((ListOfChat.isEmpty() || !ListOfChat.containsKey(distantAddress)))
		{	
			System.out.println("INSIDE IF \n");
			//test
			if (ListOfChat.isEmpty() ) {
			System.out.println("INSIDE IF second if \n");
			}
			
			ListOfChat.put(distantAddress, new Chat(distantAddress));
			ListOfChat.put(distantAddress, ListOfChat.get(distantAddress).AddMessage(nature, strDate, message));
			
			System.out.println("les messages : \n " + ListOfChat.get(distantAddress).getHistory());
			
		}else {
			
			//ListOfChat.get(distantAddress).AddMessage(nature, strDate, message);
			ListOfChat.put(distantAddress, ListOfChat.get(distantAddress).AddMessage(nature, strDate, message));
			
		}
	}
	
}

