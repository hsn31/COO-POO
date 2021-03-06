package model;

import java.io.*;

import java.util.*;


/**
 *
 * @author peraire
 */
 
 
public class HistoryManager {
    

    private LinkedList<History> memoryConversation;
	
    public HistoryManager()
    {
    	memoryConversation = new LinkedList<History>();
    	new File("histories/").mkdir();
    }
	
	public void createHistory(String fileName)
	{
		memoryConversation.add(new History(fileName));
	}
    

	public boolean fileAccountExists()
	{
		return listAllHist().contains("LastPseudo");
	}
	
	public String getLastPseudoFromDataBase()
	{
		File fileHist = new File("histories/LastPseudo");
		BufferedReader lecteurAvecBuffer = null;
		String ligne = "";
			
		try
		{
			lecteurAvecBuffer = new BufferedReader(new FileReader(fileHist));
		
			ligne = lecteurAvecBuffer.readLine();
			
			if(ligne == null)
			{
				ligne = "";
			}
	  	}
		catch(IOException e)
		{
			System.out.println("Erreur readPseudoInHystory");
	  	}
		
		return ligne;
	}
	
	
	public ArrayList<String> listAllHist()
	{
		ArrayList<String> listHist = new ArrayList<String>();
		
		File repertoire = new File("histories/");
		System.out.println("HistoryManager listAllHist() : IsDirectory : " + repertoire.isDirectory() + "\n");
		
		String liste[] = repertoire.list(); 
		
		if (liste != null) {         
		    for (int i = 0; i < liste.length; i++) {
		            listHist.add(liste[i]);
		    }
		}
		return listHist;
	}
	
	public ArrayList<String> readHistory(String fileName)
	{
		ArrayList<String> listHist = new ArrayList<String>();
		
		File fileHist = new File("histories/"+fileName);
		BufferedReader lecteurAvecBuffer = null;
		String ligne;
			
		try{
			lecteurAvecBuffer = new BufferedReader(new FileReader(fileHist));
			
		while ((ligne = lecteurAvecBuffer.readLine()) != null){
		  	listHist.add(ligne);
			}

		  	} catch(IOException e){
			System.out.println("Erreur readHistory");
		  	}
   
		return listHist;
	}

	public boolean needNewHistory(String ip_address_history) 
	{
		boolean result = true;
		
		for(int i = 0 ; i < memoryConversation.size(); i++)
		{
			if(memoryConversation.get(i).getFileName().equals(ip_address_history))
			{
				result = false;
			}
		}
		
		return result;
	}

	public void updateHistory(String id, String messages_to_update_history) 
	{
		for(int i = 0 ; i < memoryConversation.size(); i++)
		{
			memoryConversation.get(i).updateHistory(messages_to_update_history);
		}
	}

	public void closeHistories() 
	{
		for(int i = 0 ; i < memoryConversation.size(); i++)
		{
			memoryConversation.get(i).closeHistory();
		}
	}
	
	public void updatePseudoHistory(String pseudo)
	{
		History.updatePseudoHistory(pseudo);
	}

}
