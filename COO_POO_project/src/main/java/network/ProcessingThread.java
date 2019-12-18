package network;

import java.io.IOException;
import java.util.ArrayList;

public class ProcessingThread implements Runnable 
{
	private MainApplication local_application;
	private ArrayList<String> listOfMessages;
	
	public ProcessingThread(MainApplication application)
	{
		local_application = application;
	}
	
	public void run() 
	{
		while(true)
		{
			//refresh
			listOfMessages = local_application.getActualListOfMessages();
			processMessageReceived(listOfMessages.get(0));
			
			//supress message read
			//local_application.deleteOneMessage();
		}
		
	}
	
	
	public void processMessageReceived(String message)
	{
		String[] dataPacket = message.split("<>");
		
		String idPacketReceived = dataPacket[0];
		String naturePacketReceived = dataPacket[1];
		String ipSenderPacketReceived = dataPacket[2];
		String textPacketReceived = dataPacket[3];
		
		//int state = local
		if(idPacketReceived.equals("1"))
		{
			local_application.sendListActiveUser(ipSenderPacketReceived);
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
