package network;

import java.util.ArrayList;

/*To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author väinö
 */
public class LocalMemory 
{
	static int localId;
	private ArrayList<Account> listOfActiveUsers = new ArrayList<Account>();
	private ArrayList<Account> listOfAccountsCreated = new ArrayList<Account>();
	private ArrayList<String> listOfPseudonymes = new ArrayList<String>();
	
	
	public void checkUnicity() {
		
	}
	
	public void checkActiveUserAmount() {
		
	}
	
	public ArrayList<Message> getChatHistory(String distantId) {
		ArrayList<Message> chatHistory = new ArrayList<Message>();
		chatHistory = Account.getChatHistory(distantId);
		return chatHistory;
	}
	
	public void modifyPseudonyme(String pseudonyme) {
		Account.modifyPseudonyme(pseudonyme);
		
	}
	
	public void createAccount(int localId, String pseudonyme) {
		// this method to bring the details of account from application to account
		Account.accountCreated(localId, pseudonyme); // static thing thats in all the classes
	}
	
	public void updateListConnectedBroadcast(String ipAddress, String pseudonyme)
	{
		//if already exist => modify pseudo
		//else add
	}
	
	public void deleteListConnectedBroadcast(String ipAddress)
	{
		
	}

}
