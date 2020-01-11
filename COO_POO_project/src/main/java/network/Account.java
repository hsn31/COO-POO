package network;

import java.util.*;
 
//TEST OK le 08_01_2020
 /*
 * Convention pour les messages du Broadcast...
 * 		ID == <1> | on veut se connecter => demande du tableau (?)
 * 		ID == <2> | connected 
 *      ID == <3> | update_pseudo 
 * 		ID == <4> | disconnected 
 */
 
public class Account {
	
	private String pseudonyme;
	private String ipAdress;
	
	private ArrayList<Chat> ListOfChat;
	
	public Account(String ipAdress, String pseudonyme) {
		this.ListOfChat =new ArrayList<Chat>();
		this.ipAdress=ipAdress;
		this.pseudonyme = pseudonyme;	
	}
	 	
	public String getPseudo() {
		return this.pseudonyme;
	}
	
	//Retourne le Chat en lien avec ce DistantID
	public Chat getChatHistory(String distant) {
		Chat temp= null;
	
		for (int i=0; i<this.ListOfChat.size(); i++) {
			if ((this.ListOfChat.get(i).getDistantId()).equals(distant)) {
				temp =this.ListOfChat.get(i);
			}
		}
		return temp;
	}
	
	//retourne la liste des messages en lien avec ce DistantID
	public ArrayList<Message> getChatMessageHistory(String distant) {
		ArrayList<Message> temp=new ArrayList<Message>();
		
		for (int i=0; i<this.ListOfChat.size(); i++) {
			if ((this.ListOfChat.get(i).getDistantId()).equals(distant)) {
				temp =this.ListOfChat.get(i).getListOfMessage();
			}
		}

		return temp;
	}
	
	
	public String getIp() {
		return this.ipAdress;
	}
	public void modifyPseudonyme(String pseudo) {
		this.pseudonyme = pseudo;
	}
}

