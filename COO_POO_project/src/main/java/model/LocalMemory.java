package model;

import java.util.*;

import model.Message.Origin;

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
		
		//récupérer l'ancien pseudo dans database
		boolean database_say_account_exists = local_history.fileAccountExists();
		
		if(database_say_account_exists)
		{
			String last_pseudo = local_history.getLastPseudoFromDataBase();
			createAccountFromDatabase(stringlocal_address, last_pseudo);
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
		LinkedHashMap<String,String> list_ActiveUsers_withoutLocal = new LinkedHashMap<String,String>();
		list_ActiveUsers_withoutLocal.putAll(listOfActiveUsers);
		list_ActiveUsers_withoutLocal.remove(stringlocal_address);

		return !(list_ActiveUsers_withoutLocal.containsValue(local_account.getPseudo()));
	}
	
	
	//-------------------------------------------------------------------------------
	
	public void addMessage(Origin nature, String distantAddress, String strDate, String message)
	{
		System.out.println(" TEST/ addMessage LocalMemory distantAdress= " + distantAddress + "\n");
		local_account.registerMessage(nature, distantAddress, strDate, message);
	}


	public void saveHistory() 
	{
		ArrayList<String> list = local_account.getDistantChat();
		
		for (int i =0; i< local_account.getDistantChat().size(); i++ ) 
		{
			String id = list.get(i);
			
			if (local_history.needNewHistory(id)) 
			{
				local_history.createHistory(id);
			}
			
			String messages_to_update_history = local_account.getChatHistory(id);
			
			local_history.updateHistory(id, messages_to_update_history);
		}
		
		local_history.closeHistories();
		
		String current_pseudo = local_account.getPseudo();
		
		local_history.updatePseudoHistory(current_pseudo);
	}


	public String downloadChatHTMLHistory(String ipAddress) 
	{
		System.out.print("LOCALMEMORY HISTORY" + "\n");
		
		String precedent_history = download_PrecedentChatHTMLHistory(ipAddress);
		String recent_history = download_RecentChatHTMLHistory(ipAddress);
		
		System.out.print("LOCALMEMORY HISTORY precedent_History " + precedent_history + " FIN" + "\n");
		System.out.print("LOCALMEMORY HISTORY recent_History " + recent_history + "\n");
		
		return precedent_history + recent_history;
	}
	
	
	private String download_PrecedentChatHTMLHistory(String ipAddress)
	{
		String precedent_history = "";
		
		System.out.print(" INSIDE download_PrecedentChatHTMLHistory "+ "\n");
		
		ArrayList<String> filesExisting = local_history.listAllHist();
		
		System.out.print(" INSIDE download_PrecedentChatHTMLHistory FILEEXISTING ="+ filesExisting + "\n");
			
		if((ipAddress.equals(ipAddress)))
		{
			
			
			//if (!local_history.needNewHistory(id)) 
			//{
				System.out.print(" download_PrecedentChatHTMLHistory AFTER Value"+ local_history.needNewHistory(ipAddress) + "\n");
				
				ArrayList<String> history_from_doc = local_history.readHistory(ipAddress);
				
				System.out.print(" download_PrecedentChatHTMLHistory : history_from_doc = "+ history_from_doc+ "\n");
				
				for(int j = 0 ; j < history_from_doc.size() ; j++)
				{
					String line = history_from_doc.get(j);
					String beginning = line.split(":")[0];
					
					if(!line.equals("") && !beginning.equals("Date of the previous conversation ") && !beginning.equals("End of the conversation "))
					{
						String htmlLine = historyLine_toHtml(line);
						
						System.out.print(" download_PrecedentChatHTMLHistory : INSIDE THIRD IF ="+ "\n");

						precedent_history = precedent_history + "<br>" + htmlLine;
					}
				//}
			}
		}
		
		System.out.print(" END download_PrecedentChatHTMLHistory VALEUR = "+ precedent_history +"\n");
		
		return precedent_history;

	}
	
	private String historyLine_toHtml(String line)
	{
		String [] values = line.split("<>");
		String strOrigin = values[0];
		String strDate = values[1];
		String message = values[2];
		
		String balise = "<p>";
		
		if(strOrigin.equals("R"))
		{
			//blanc : #FFFFFF
			balise = "<p color =#FFFFFF>";
		}
		else if(strOrigin.equals("S"))
		{
			//bleu : #0066FF
			balise = "<p color =#0066FF>";
		}
		
		String txtDate = balise + strDate + "</p>";
		String txtMessage = balise + message + "</p>";
		
		return txtDate + txtMessage;
	}
	
	private String download_RecentChatHTMLHistory(String ipAddress)
	{
		String recent_history = "";
		
		if(local_account.chatIsCreated(ipAddress))
		{
			recent_history = local_account.getChatHTMLHistory(ipAddress);
		}
		
		return recent_history;
	}
}
