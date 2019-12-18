package network;

import java.io.*;

import java.util.*;


/**
 *
 * @author peraire
 */
 
 //Je propose d'utiliser cette classe pour le history d'un utilisateur... Mais il faut modifier le nom
 
public class HistoryManager {
    
    private String id;
	private Date connectDate;
	private String fileName;
	
	public History createHistory(String id, String fileName, Date connectDate)
	{
		return new History(this.id, this.fileName, this.connectDate);
	}
    
	public void run() {
		System.out.println("History Messages Started");
	}
	
	public ArrayList<String> listAllHist()
	{
		ArrayList<String> listHist = new ArrayList<String>();
		
		File repertoire = new File("histories");
		
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
		
    private LinkedList<String> memoryMessages;
    
    HistoryManager(String new_id)
    {
        id = new_id;
        memoryMessages = new LinkedList<String>();
    }
    
    public void registerMessage(String message)
    {
        memoryMessages.add(message);
    }
    
    public String getId()
    {
        return id;
    }
}
