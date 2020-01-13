package network;

import java.awt.FontFormatException;
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
		LOGIN,
		CHATTING,
	}
	
	private AppState local_state;
	
	//------------------ CONSTRUCTOR AND TEST ------------------------------------------
	
	public MainApplication() throws FontFormatException, IOException
	{
		local_memory = new LocalMemory();
		local_interface = new VisualInterface();
		local_manager = new NetworkManager();
		
		//A FAIRE
		//récupérer les données de la database dans le constructeur de localMemory
		//récupérer la liste des active users (ou ?)
		
		local_state = AppState.LOGIN;
		
		if(local_memory.accountIsAlreadyCreated())
		{
			if(local_memory.lastPseudonymeIsOk())
			{
				//a écrire: connexion avec l'ancien pseudonyme > envoie d'un broadcast...
				
				//Donc on peut direct afficher l'applicationWindow:
				local_state = AppState.CHATTING;
			}
		}
		
		//parameters to make run the application
		creation_listeners_VisualInterface();
		processor_messages = new Thread(new ProcessingThread(this));
		processor_messages.start();
		
		//open interface
		if(local_state == AppState.LOGIN)
		{
			local_interface.openCreatingPseudonymeWindow();
		}
		else if(local_state == AppState.CHATTING)
		{
			//miss refresh the display with informations from local_memory
			local_interface.openApplicationWindow();
		}
	}
	
	public static void main() throws FontFormatException, IOException
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
	//format "11<>unicast<>IPSENDER<>PSEUDO"
	public void sendActiveUser(String destAddress) throws UnknownHostException, IOException
	{
		InetAddress ipsender = local_manager.get_local_address();
		int inPort = local_manager.get_inPort();
		InetAddress ipreceiver = InetAddress.getByName(destAddress);
		local_manager.sendMessage("11<>unicast<>" + ipsender.toString() + "<>" + local_memory.getPseudo(), ipreceiver, inPort);
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
	
	//a packet in the buffer
	public void deleteOneMessage()
	{
		local_manager.getListOfMessages().remove(0);
	}
	
	public ArrayList<String> getActualListOfMessages()
	{
		return local_manager.getListOfMessages();
	}

	
	//------------------- SUB METHODS : INTERACTIONS WITH USER --------------
	
	private void process_login(String validatedPseudo)
	{
		/*
		 * local_manager :
		 * - send broadcast update connexion/pseudo
		 * 
		 * local_memory :
		 * - update pseudo
		 * 
		 * local_interface :
		 * - fermer pseudowindow
		 * - changer create_pseudowindow en modify_pseudowindow
		 * - changer notre pseudo dans ApplicationWindow
		 * - open ApplicationWindow
		 * 
		 * ici:
		 * - changer state en CHATTING
		 */
		
		local_manager.broadcastConnected(validatedPseudo);
		
		local_memory.modifyPseudonyme(validatedPseudo);
		
		local_interface.process_login(validatedPseudo);
		
		local_state = AppState.CHATTING;
	}
	
	private void process_modifyPseudo(String validatedPseudo)
	{
		local_interface.clean_pseudoWindow();
	}
	
	//------------------- INTERACTIONS WITH USER -----------------------------
	
	private void click_on_validate_pseudonyme_button()
	{
		String wantedPseudo = local_interface.getWrittenPseudonyme();
		String currentError = "";
		if(wantedPseudo.equals("")) 
		{
			currentError = "Impossible to login with an empty pseudo !";
		} 
		else if(wantedPseudo.contains(" "))
		{
			currentError = "Impossible to login with a pseudo which contains a space !";
		} 
		else
		{
			if(local_memory.pseudoAlreadyUsed(wantedPseudo)) 
			{
				currentError = "Impossible to use the pseudonyme <" + wantedPseudo + "> because it is already Online.";
			}
		}
		
		if(currentError.equals(""))
		{
			if(local_state == AppState.LOGIN)
			{
				process_login(wantedPseudo);
				
			}
			else if(local_state == AppState.CHATTING)
			{
				process_modifyPseudo(wantedPseudo);
			}
		}
		else
		{
			local_interface.process_ErrorPseudo(currentError);
		}
	}
	
	
	public void actionPerformed(ActionEvent e){
		
		if(e.getSource().equals(local_interface.getValidatePseudoButton())) 
		{
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
