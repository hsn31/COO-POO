package network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class ProcessingThread implements Runnable 
{
	private boolean interrupted=false;
	
	private MainApplication local_application;
	private String stringlocal_address;
	
	private ArrayList<String> listOfMessages = new ArrayList<String>();
	
	public ProcessingThread(MainApplication application, String local_address)
	{
		local_application = application;
		stringlocal_address = local_address;
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
			
			if (!(listOfMessages.isEmpty())){
	        System.out.println("************************TEST/ getActualListOfMessages()********************* /n");
			System.out.println("TEST/ getActualListOfMessages() : " + listOfMessages);
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++/n");
			}
			//test
			
			if(listOfMessages !=null && (listOfMessages.size() > 0))
			{
				try {
					processMessageReceived(listOfMessages.get(0));
				      System.out.println("************************inside the processingThread********************* /n");
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				//supress message read
				local_application.deleteOneMessage();
				
				//petite pause
				try{
					Thread.sleep(1);
				}catch(InterruptedException a) {
					//Arreter la Thread de maniere propre.
					a.printStackTrace();
					Thread.currentThread().interrupt();
				}
			}
		
		}
		
		
	}
	
	
	public void processMessageReceived(String message) throws UnknownHostException, IOException
	{
		String[] dataPacket = message.split("<>");
		System.out.print("ProcessingThread :=> processMessageReceived + Message =START  " + message + " END" + "/n");
		
		//String idPacketReceived = dataPacket[0];
		//System.out.print("test " + idPacketReceived);
		
		if (dataPacket.length >= 2) {
			
		System.out.print("ProcessingThread :=> processMessageReceived"+ "/n");
		String idPacketReceived = dataPacket[0];
		//String naturePacketReceived = dataPacket[1]; (not useful)
		String ipSenderPacketReceived = dataPacket[2];
		String textPacketReceived = dataPacket[3];
		
		System.out.print("-------------------------------------------------------------------------------------------------");
		System.out.print(ipSenderPacketReceived + "\n");
		System.out.print(idPacketReceived + "\n");
		System.out.print(idPacketReceived.equals("2") + "\n");
		System.out.print(stringlocal_address);
		System.out.print("---------------------------------------------------------------------------------------------------");
		
		//int state = local
		// !
		if(idPacketReceived.equals("1") && !(ipSenderPacketReceived.equals(stringlocal_address)) )
		{
			//if on est actif !!
			local_application.sendActiveUser(ipSenderPacketReceived);
		} //!
		else if((idPacketReceived.equals("2") || idPacketReceived.equals("11") )&& !(ipSenderPacketReceived.equals(stringlocal_address)))
		{
			local_application.addActiveUser(ipSenderPacketReceived, textPacketReceived);
			System.out.print("ProcessingThread :=> processMessageReceived + Process"+ "idPacketReceived.equals(\"2\")");
			System.out.print(stringlocal_address);
		} //!
		else if(idPacketReceived.equals("3") && !(ipSenderPacketReceived.equals(stringlocal_address)))
		{
			System.out.print("ProcessingThread :=> processMessageReceived =====3"+ "/n");
			local_application.modifyActiveUser(ipSenderPacketReceived, textPacketReceived);
		}// !
		else if(idPacketReceived.equals("4") && !(ipSenderPacketReceived.equals(stringlocal_address)))
		{
			local_application.deleteActiveUser(ipSenderPacketReceived, textPacketReceived);
		}
		else if(idPacketReceived.equals("5"))
		{
			System.out.print("Process_5_Start"+"/n");
			local_application.addReceivedMessage(ipSenderPacketReceived, textPacketReceived);
			System.out.print("Process_5_END"+"/n");
		}
		}
	}
}
