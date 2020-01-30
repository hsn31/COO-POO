package network;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

import network.Message.Origin;

//Mettre ici l'histoire des historiques  + 

public class LocalMemory 
{
	
	//On crée une Map, avec en Clé l'adresseIP, et en valeur le pseudo. 
	//Map <AdresseIP,Pseudo>
	
	private LinkedHashMap<String,String> listOfActiveUsers;
	
	//L'utilisateur local : qui contient toutes ses infos personnelles (ex: chats)
	private Account local_account;
	private String stringlocal_address;
	
	private HistoryManager local_history;
	
	public LocalMemory(String local_address)
	{
		listOfActiveUsers = new LinkedHashMap<String,String>();
		stringlocal_address = local_address;
		
		local_history = new HistoryManager();
		
		//récupérer dans database données pour:
		boolean database_say_account_exists = false;
		
		if(database_say_account_exists)
		{
			//String last_pseudo = getLastPseudoFromDataBase();
			createAccountFromDatabase(stringlocal_address, "last_pseudo");
		}
		else
		{
			createNewAccount(stringlocal_address, "sera_changé_apres");
		}
	}
	
	
	//-------------------------------------------------------------------------------------
	
	
	public LinkedHashMap<String,String> getListOfActiveUsers()
	{
		return listOfActiveUsers;
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
		listOfActiveUsers.put(ipAddress, pseudonyme); //on peut disvuter avec soit meme
	}
	
	public void createAccountFromDatabase(String ipAddress, String pseudonyme) 
	{
		local_account = new Account (true, ipAddress, pseudonyme);
		//ensuite: l'account sera rempli par le reste de la database si le reste existe
		listOfActiveUsers.put(ipAddress, pseudonyme); //on peut disvuter avec soit meme
	}
	
	//----------------------------------------------------------------------------------------------
	
	//Ajouter et uploader la liste des utilisateurs connectés -TESTOK
	public void updateListConnectedBroadcast(String ipAddress, String pseudonyme)
	{
		System.out.print("LOCALMEMORY UPDATE"+ listOfActiveUsers+ "\n");

		System.out.print("LOCALMEMORY UPDATE"+ listOfActiveUsers+"\n");
		
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
		LinkedHashMap<String,String> list_ActiveUsers_withoutLocal = listOfActiveUsers;
		list_ActiveUsers_withoutLocal.remove(stringlocal_address);

		return !(list_ActiveUsers_withoutLocal.containsValue(local_account.getPseudo()));
	}
	
	
	//-------------------------------------------------------------------------------
	
	public void addMessage(Origin nature, String distantAddress, String strDate, String message)
	{
		local_account.registerMessage(nature, distantAddress, strDate, message);
	}
}
