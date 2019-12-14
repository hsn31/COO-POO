package network;

import java.io.IOException;

public class ReceiverThread implements Runnable
{
	private NetworkManager local_manager;
	
	public ReceiverThread(NetworkManager manager)
	{
		local_manager = manager;
	}
	
	public void run() 
	{
		while(true)
		{
			try 
			{
				local_manager.receiveMessage();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		
	}
}
