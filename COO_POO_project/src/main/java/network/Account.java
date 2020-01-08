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
	
	public Account(String ipAdress, String pseudonyme) {
		
		this.ipAdress=ipAdress;
		this.pseudonyme = pseudonyme;	
	}
	 	
	public String getPseudo() {
		return this.pseudonyme;
	}
	
	public String getIp() {
		return this.ipAdress;
	}
	public void modifyPseudonyme(String pseudo) {
		this.pseudonyme = pseudo;
	}
}

