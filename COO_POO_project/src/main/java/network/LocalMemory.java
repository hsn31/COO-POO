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
	
	
	//ATTENTION : TRUE Signifie que le pseudo est deja utilisé. -TESTOK 
	public boolean checkUnicity(String tempPseudo) {
		
		if (listOfActiveUsers.containsValue(tempPseudo)){
			return true;
		}else {
			return false;
		}
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
	
	
	//Modification du pseudo de l'utilisateur local
	public void modifyPseudonyme(String pseudonyme) {
		Account.modifyPseudonyme(pseudonyme);
		
	}
	
	public void createAccount(int localId, String pseudonyme) {
		// this method to bring the details of account from application to account
		Account.accountCreated(localId, pseudonyme); // static thing thats in all the classes
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

}
