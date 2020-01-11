package network;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;


//TEST OK LE 11_01_2020

public class LocalMemory 
{
	
	//On crée une Map, avec en Clé l'adresseIP, et en valeur le pseudo. 
	//Map <AdresseIP,Pseudo>
	
	Map<String,String> listOfActiveUsers = new LinkedHashMap<String,String>();
	
	//L'utilisateur local : qui contient toutes ses infos personnelles (ex: chats)
	Account local_account;
	
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
	
	
	//Modification du pseudo d'un utilisateur
	public void modifyPseudonyme(Account account, String pseudonyme) {
		account.modifyPseudonyme(pseudonyme);
		
	}
	
	
	//utilisée seulement lors de la première connexion sur un ordi
	//crée un compte et le place dans la liste des Utilisateurs Actifs
	public void createAccount(String ipAddress, String pseudonyme) {
		local_account = new Account (ipAddress, pseudonyme);
		listOfActiveUsers.put(ipAddress, pseudonyme); // pas sur qu'on ait besoin de l'ajouter
	}
	
	
	//Ajouter et uploader la liste des utilisateurs connectés -TESTOK
	public void updateListConnectedBroadcast(String ipAddress, String pseudonyme)
	{
		//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		//ce serait pas plutot : containsKey(ipAddress) ??????????
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

	//A FAIRE
	public String getPseudo() 
	{
		return local_account.getPseudo();
	}

}
