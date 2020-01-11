package network;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import visual.VisualInterface;

public class MainApplication implements ActionListener
{
	private NetworkManager local_manager;
	private LocalMemory local_memory;
	private VisualInterface local_interface;
	
	private Thread processor_messages;
	
	private enum AppState
	{
		CREATING_PSEUDO,
		CHATTING,
	}
	
	private AppState local_state;
	
	//------------------ CONSTRUCTOR AND TEST ------------------------------------------
	
	public MainApplication() throws SocketException, UnknownHostException
	{
		local_memory = new LocalMemory();
		local_interface = new VisualInterface();
		local_manager = new NetworkManager();
		
		//récupérer les données de la database dans le constructeur de localMemory
		
		if(local_memory.)
		{
			local_state = AppState.CREATING_PSEUDO;
		}
		local_state = AppState.CREATING_PSEUDO;
		
		processor_messages = new Thread(new ProcessingThread(this));
		processor_messages.start();
	}
	
	public static void main() throws SocketException, UnknownHostException
	{
		new MainApplication();
	}
	
	//------------------- LISTENERS -----------------------------------------------------
	
	private void creation_listeners_VisualInterface()
	{
		local_interface.creation_listeners(this);
	}
	
	
	//------------------- ACTIONS -------------------------------------------------------
	
	//Envoi le pseudo en retour d'un appel broadcast d'ID n°1
	//format 11<>unicast<>IPSENDER<>PSEUDO
	public void sendActiveUser(String destAddress) throws UnknownHostException, IOException
	{
		InetAddress test = local_manager.get_local_address();
		local_manager.sendMessage("11<>unicast<>"+test+"<>"+local_memory.getPseudo(),InetAddress.getByName(destAddress),local_manager.get_inPort());
	}
	
	public void addActiveUser(String ipAddress, String pseudonyme)
	{
		local_memory.updateListConnectedBroadcast(ipAddress, pseudonyme);
		local_interface.showNewActiveUser(pseudonyme);
	}
	
	public void modifyActiveUser(String ipAddress, String pseudonyme)
	{
		local_memory.updateListConnectedBroadcast(ipAddress, pseudonyme);
		local_interface.showModificationActiveUser(pseudonyme);
	}
	
	public void deleteActiveUser(String ipAddress, String pseudonyme)
	{
		local_memory.deleteListConnectedBroadcast(ipAddress);
		local_interface.removeActiveUser(pseudonyme);
	}
	
	public void deleteOneMessage()
	{
		local_manager.getListOfMessages().remove(0);
	}
	
	public ArrayList<String> getActualListOfMessages()
	{
		return local_manager.getListOfMessages();
	}

	//------------------- INTERACTIONS WITH USER -------------------------------------------------------
	
	private void click_on_validate_pseudonyme_button()
	{
		
	}
	
	
	public void actionPerformed(ActionEvent e){
		
		if(e.getSource().equals(local_interface.getValidatePseudoButton())) {
			try 
			{
				click_on_validate_pseudonyme_button();
			} 
			catch (Exception e1) 
			{
				e1.printStackTrace();
			}
		}
		
	}
}
