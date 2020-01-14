package network;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class ProcessingThread implements Runnable 
{
	private boolean interrupted=false;
	
	private MainApplication local_application;
	private ArrayList<String> listOfMessages;
	
	public ProcessingThread(MainApplication application)
	{
		local_application = application;
	}
	
	public void interrupt(){
	    interrupted = true;
	 }
	
	public void run() 
	{
		interrupted = false;
		
		while(!interrupted)
		{
			//refresh
			listOfMessages = local_application.getActualListOfMessages();
			try {
				processMessageReceived(listOfMessages.get(0));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			//supress message read
			local_application.deleteOneMessage();
			
			//Arreter la Thread de maniere propre. 
			try{
				Thread.sleep(1);
			}catch(InterruptedException a) {
				a.printStackTrace();
				Thread.currentThread().interrupt();
			}
			
		}
		
	}
	
	
	public void processMessageReceived(String message) throws UnknownHostException, IOException
	{
		String[] dataPacket = message.split("<>");
		
		String idPacketReceived = dataPacket[0];
		String naturePacketReceived = dataPacket[1];
		String ipSenderPacketReceived = dataPacket[2];
		String textPacketReceived = dataPacket[3];
		
		//int state = local
		if(idPacketReceived.equals("1"))
		{
			local_application.sendActiveUser(ipSenderPacketReceived);
		}
		else if(idPacketReceived.equals("2"))
		{
			local_application.addActiveUser(ipSenderPacketReceived, textPacketReceived);
		}
		else if(idPacketReceived.equals("3"))
		{
			local_application.modifyActiveUser(ipSenderPacketReceived, textPacketReceived);
		}
		else if(idPacketReceived.equals("4"))
		{
			local_application.deleteActiveUser(ipSenderPacketReceived, textPacketReceived);
		}
	}
}
