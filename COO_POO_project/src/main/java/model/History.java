package model;

import java.io.*;
import java.util.Date;


//ATTENTION LocalDateTime !!!!!!

public class History {
	
	//persistence des données avec un fichier txt 
	
	private FileWriter file;
	private File fileHistory;
	private String fileName;
	
	
	//ID = PSEUDO DU DISTANT -  FILENAME ->ADRESSE IP DU DISTANT
	public History(String fileName)
	{
		this.fileName = fileName;
		
		// persistance des données avec fichiers texte
		
		this.fileHistory = new File("histories/"+fileName);

		try{
			file = new FileWriter(fileHistory, true);
			file.write("Date of the previous conversation :" + new Date() + "\n");
			file.flush();
			
		} catch (IOException e){
			System.err.println("Error History.java");
			e.printStackTrace();
		}
	}
	

	public static void updatePseudoHistory(String pseudo)
	{
		File filePseudoHistory = new File("histories/LastPseudo");
		
		try
		{
			PrintWriter printwriter = new PrintWriter(new FileOutputStream(filePseudoHistory));
			printwriter.print("");
			printwriter.close(); 
			
			FileWriter writer = new FileWriter(filePseudoHistory, false);
			writer.write(pseudo);
			writer.flush();
			writer.close();
			
		} catch (IOException e){
			System.err.println("Error History.java updating pseudo file");
			e.printStackTrace();
		}
	}
	
	public String getFileName()
	{
		return fileName;
	}
	
	
	//Un message est du format 	"S <>strDate <> message \n" OU "R<>strDate <> message \n"
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
