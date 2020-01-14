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
	private static final long Delta = 1000;
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
		
		long t1 = System.currentTimeMillis();
		 
		//A FAIRE
		//récupérer les données de la database dans le constructeur de localMemory
		//récupérer la liste des active users (ou ?)
		
		//Lancement des Threads
		processor_messages = new Thread(new ProcessingThread(this));
		processor_messages.start();
		
		//Envoi du broadcast d'ID <1>
		local_manager.broadcastGetActiveUser();
		
		//Attention, on fait une pause de 1000 millisecondes pour recevoir les broadcasts 
		while(System.currentTimeMillis()<t1+Delta);
		
		local_state = AppState.LOGIN;
		
		if(local_memory.accountIsAlreadyCreated())
		{
			if(local_memory.lastPseudonymeIsOk())
			{
				local_manager.broadcastConnected(local_memory.getPseudo());
				
				//Donc on peut direct afficher l'applicationWindow:
				local_state = AppState.CHATTING;
			}
		}
		
		//parameters to make run the application
		creation_listeners_VisualInterface();
		
		//open interface
		if(local_state == AppState.LOGIN)
		{
			local_interface.openPseudonymeWindow();
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
		local_manager.broadcastUpdatePseudo(validatedPseudo);
		
		local_memory.modifyPseudonyme(validatedPseudo);
		
		local_interface.process_modifyPseudo(validatedPseudo);
	}
	
	private void process_cancelModifyPseudo()
	{
		local_interface.process_cancelModifyPseudo();
	}
	
	private void process_shutDown()
	{
		//penser a sauvegarder dans la base de donnée !!
		//et a eteindre le server ! les servers ?
	}
	
	private void process_applyMessage(String wantedMessage, String distantAddress)
	{
		local_interface.process_applyMessage();
		
		local_manager.unicastSendChatMessage(wantedMessage, distantAddress);
		
		
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
	
	private void click_on_exit_pseudonyme_button()
	{
		if(local_state == AppState.LOGIN)
		{
			process_shutDown();
		}
		else if(local_state == AppState.CHATTING)
		{
			process_cancelModifyPseudo();
		}
	}
	
	private void click_on_send_message_button(String distantAddress)
	{
		String wantedMessage = local_interface.getWrittenMessage();
		String currentError = "";
		int length = wantedMessage.length();
		if(wantedMessage.equals("")) 
		{
			currentError = "Impossible to send an empty message";
		} 
		else if(length > 500)
		{
			currentError = "Message too long";
		}
		
		// next if the text is right, other faults
		
		if(currentError.equals(""))
		{ 
			process_applyMessage(wantedMessage, distantAddress);
		}
		
	}
	
	
	public void actionPerformed(ActionEvent e){
		
		if(e.getSource().equals(local_interface.getValidatePseudoButton())) 
		{
			click_on_validate_pseudonyme_button();
		}
		else if(e.getSource().equals(local_interface.getExitPseudoButton())) 
		{
			click_on_exit_pseudonyme_button();
		}
		else if(e.getSource().equals(local_interface.getSendMessageButton())) 
		{
			String distantAddress = "has to be worked on";
			click_on_send_message_button(distantAddress);
		}
		
	}
}
