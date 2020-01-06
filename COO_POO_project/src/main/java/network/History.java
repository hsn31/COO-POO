package network;

import java.io.*;
import java.util.Date;


//ATTENTION LocalDateTime !!!!!!

public class History {
	
	//persistence des données avec un fichier txt 
	
	//--Pour commencer, on verra apres pour JDBC et Cie
	
	private FileWriter file;
	private File fileHistory;
	private Date sessionDate;
	private String id;
	private String fileName;
	
	public History(String id, String fileName, Date connectDate)
	{
		this.sessionDate = connectDate;
		this.id = id;
		this.fileName = fileName;
		
		// persistance des données avec fichiers texte
		
		this.fileHistory = new File("History/"+fileName);

		try{
			file = new FileWriter(fileHistory, true);
			file.write("Pseudo :" + id + "\n");
			file.write("Date of the previous conversation :" + sessionDate.toString() + "\n");
			file.flush();
			
		} catch (IOException e){
			System.err.println("Error History.java");
			e.printStackTrace();
		}
	}
	
	public synchronized void updateHistory(String message) {
		
		try{
			file.write(message+"\n");
			file.flush();
			
		} catch (IOException e){
			System.err.println("Error update History");
			e.printStackTrace();
		}	
	}

	public void closeHistory() {
		
		try{
			file.write("End of the conversation :" + (new Date()).toString() + "\n");
			file.close();
			
		} catch (IOException e){
			System.err.println("Error Close History");
			e.printStackTrace();
			}
		}	
}
