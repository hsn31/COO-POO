package controller;

import java.io.IOException;

public class ReceiverThread implements Runnable
{
	private boolean interrupted=false;
	
	private NetworkManager local_manager;
	
	public ReceiverThread(NetworkManager manager)
	{
		local_manager = manager;
	}
	
	public void interrupt(){
	    interrupted = true;
	 }
	
	public void run() 
	{
		interrupted = false;
		
		while(!interrupted)
		{
			try{
					local_manager.receiveMessage();
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
			
			//Arreter la Thread de maniere propre. 
			try{
				Thread.sleep(1);
			}catch(InterruptedException a) {
				a.printStackTrace();
				Thread.currentThread().interrupt();
			}
		}
		
	}
}
