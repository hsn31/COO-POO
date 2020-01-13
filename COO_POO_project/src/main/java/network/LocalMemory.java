package network;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;



public class LocalMemory 
{
	
	//On crée une Map, avec en Clé l'adresseIP, et en valeur le pseudo. 
	//Map <AdresseIP,Pseudo>
	
	Map<String,String> listOfActiveUsers = new LinkedHashMap<String,String>();
	
	//L'utilisateur local : qui contient toutes ses infos personnelles (ex: chats)
	Account local_account;
	
	//-------------------------------------------------------------------------------------
	
	//renvoi l'adresse IP de la machine locale sur le réseau -TESTOK
	public String getLocalIp() 
	{
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
	public boolean pseudoAlreadyUsed(String pseudonyme) 
	{
		return listOfActiveUsers.containsValue(pseudonyme);
	}
	
	
	//renvoie le nombre d'élements contenus dans la collection -TESTOK
	public int checkActiveUserAmount() 
	{
		return listOfActiveUsers.size();
	}
	
	
	//Modification du pseudo de l'utilisateur local
	public void modifyPseudonyme(String pseudonyme) {
		local_account.modifyPseudonyme(pseudonyme);
	}
	
	//----------------------------------------------------------------------------------------------
	
	//une des deux :: utilisée a chaque ouverture de l'application
	
	//crée un compte .......et le place dans la liste des Utilisateurs Actifs
	//si jamais créé: pseudo bidon
	public void createNewAccount(String ipAddress, String pseudonyme) 
	{
		local_account = new Account (false, ipAddress, pseudonyme);
		listOfActiveUsers.put(ipAddress, pseudonyme); // A mon avis il ne faut pas l'ajouter !!!!!!!!!!!!!!!!
	}
	
	public void createAccountFromDatabase(String ipAddress, String pseudonyme) 
	{
		local_account = new Account (true, ipAddress, pseudonyme);
		//ensuite: l'account sera rempli par le reste de la database si le reste existe
	}
	
	//----------------------------------------------------------------------------------------------
	
	//Ajouter et uploader la liste des utilisateurs connectés -TESTOK
	public void updateListConnectedBroadcast(String ipAddress, String pseudonyme)
	{

		if (listOfActiveUsers.containsKey(ipAddress)){
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

	public String getPseudo() 
	{
		return local_account.getPseudo();
	}
	
	
	//vvvvv Deux fonctions qui servent à l'initialisation de MainApplication vvvvvv
	
	//fonction utilisable que si les données de la database ont été récupérées
	public boolean accountIsAlreadyCreated()
	{
		return local_account.wasCreated();
	}
	
	//fonction utilisable que si les données de la database ont été récupérées 
	//ET que la liste des active users a été récupérée par le network
	public boolean lastPseudonymeIsOk()
	{
		return !(listOfActiveUsers.containsValue(local_account.getPseudo()));
	}
	
	//-------------------------------------------------------------------------------

}
