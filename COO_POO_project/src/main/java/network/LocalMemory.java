package network;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

public class LocalMemory 
{
	
	//On crée une Map, avec en Clé l'adresseIP, et en valeur le pseudo. 
	//Map <AdresseIP,Pseudo>
	
	Map<String,String> listOfActiveUsers = new LinkedHashMap<String,String>();

	
	//renvoi l'adresse IP de la machine locale sur le réseau -TESTOK
	public String getLocalIp() {

        String adresseIPLocale = null ;

        try{
           InetAddress inetadr = InetAddress.getLocalHost();
           //adresse ip sur le réseau
           adresseIPLocale = (String) inetadr.getHostAddress();
        } catch (UnknownHostException e) {
               e.printStackTrace();
        }
        
        return adresseIPLocale;
	}
	
	// the meaning... set Active = true, check if active is true
	public boolean isOnline(String pseudonyme) {
		return listOfActiveUsers.containsValue(pseudonyme);
			
	}
	
	//ATTENTION : TRUE Signifie que le pseudo est deja utilisé. -TESTOK 
	public boolean checkUnicity(String tempPseudo) {
		
		return listOfActiveUsers.containsValue(tempPseudo);
	}
	
	
	//renvoie le nombre d'élements contenus dans la collection -TESTOK
	public int checkActiveUserAmount() {
		return listOfActiveUsers.size();
	}
	
	
	public ArrayList<Message> getChatHistory(String distantId) {
		ArrayList<Message> chatHistory = new ArrayList<Message>();
		chatHistory = Account.getChatHistory(distantId);
		return chatHistory;
	}
	
	
	//Modification du pseudo d'un utilisateur
	public void modifyPseudonyme(Account account, String pseudonyme) {
		account.modifyPseudonyme(pseudonyme);
		
	}
	
	public void createAccount(String ipAddress, String pseudonyme) {
		Account account = new Account (ipAddress, pseudonyme);
	}
	
	
	//Ajouter et uploader la liste des utilisateurs connectés -TESTOK
	public void updateListConnectedBroadcast(String ipAddress, String pseudonyme)
	{
		if (listOfActiveUsers.containsValue(pseudonyme)){
			listOfActiveUsers.replace(ipAddress, pseudonyme);
		}else {
			listOfActiveUsers.put(ipAddress, pseudonyme);
		}
	}
	
	//remove. TESTOK
	public void deleteListConnectedBroadcast(String ipAddress)
	{
		listOfActiveUsers.remove(ipAddress);
	}
	
	public void addLocalUserListConnectedBroadcast(String pseudo)
	{
		listOfActiveUsers.put(getLocalIp(),pseudo);
	}

}
