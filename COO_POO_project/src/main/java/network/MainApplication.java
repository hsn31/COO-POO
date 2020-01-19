package network;

import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import network.Message.Origin;
import visual.VisualInterface;

public class MainApplication implements ActionListener, ListSelectionListener
{
	private final long Delta = 1000;
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
		//=> tout enregistrer dans account !
		
		
		/*************
		*1- récupération la liste des active users
		*				-Activation des threads
		*				-Envoi du broadcast
		*				-Attente de 1 seconde
		*2- Si le compte existe deja, et si le pseudo est ok alors parfait
		*3- Pour tous les autres cas, on ouvre openPseudonymeWindow()
		* pour (re)demander le pseudo
		*/
		
		//Lancement des Threads
		processor_messages = new Thread(new ProcessingThread(this));
		processor_messages.start();
		
		//Envoi du broadcast d'ID <1> ATTENTION ATTENTION
		local_manager.broadcastGetActiveUser();
		
		//Attention, on fait une pause de 1000 millisecondes pour recevoir les broadcasts 
		while(System.currentTimeMillis()<t1+Delta);
		
		//Test de la liste des utilisateurs. 
		System.out.println("TEST/ NOMBRE D'UTILISATEURS : "+Integer.toString(local_memory.checkActiveUserAmount()));
		
		local_state = AppState.LOGIN;
		
		if(local_memory.accountIsAlreadyCreated())
		{
			if(local_memory.lastPseudonymeIsOk())
			{
				local_manager.broadcastConnected(local_memory.getPseudo());

				local_interface.process_login(local_memory.getPseudo());
				
				//Donc on peut direct afficher l'applicationWindow:
				local_state = AppState.CHATTING;
				
				System.out.println("TEST/ Dans la boucle LastPseudoOK : ");
			}
		}
		
		//Now, the local_user could be checked as Active
		local_interface.download_listOfActiveUsers(local_memory.getListOfActiveUsers());
		
		//parameters to make run the application
		creation_listeners_VisualInterface();
		
		//open interface
		if(local_state == AppState.LOGIN)
		{
			local_interface.openPseudonymeWindow();
			System.out.println("TEST/ local_state = login ");
		}
		else if(local_state == AppState.CHATTING)
		{
			//miss refresh the display with informations from local_memory
			local_interface.openApplicationWindow();
		}
	}
	
	public static void main(String[] args) throws FontFormatException, IOException
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
		if(local_state == AppState.CHATTING)
		{
			InetAddress ipsender = local_manager.get_local_address();
			int inPort = local_manager.get_inPort();
			InetAddress ipreceiver = InetAddress.getByName(destAddress);
			local_manager.sendMessage("11<>unicast<>" + ipsender.toString() + "<>" + local_memory.getPseudo(), ipreceiver, inPort);
		}
	}
	
	public void addActiveUser(String ipAddress, String pseudonyme)
	{
		local_memory.updateListConnectedBroadcast(ipAddress, pseudonyme);
		local_interface.showNewActiveUser(ipAddress, pseudonyme);
	}
	
	public void modifyActiveUser(String ipAddress, String pseudonyme)
	{
		local_memory.updateListConnectedBroadcast(ipAddress, pseudonyme);
		local_interface.showModificationActiveUser(ipAddress, pseudonyme);
	}
	
	public void deleteActiveUser(String ipAddress, String pseudonyme)
	{
		local_memory.deleteListConnectedBroadcast(ipAddress);
		local_interface.removeActiveUser(ipAddress);
	}
	
	public void addReceivedMessage(String ipDistantAddress, String message)
	{
		String[] textInfo = message.split("<s>");
		String strDate = textInfo[0];
		String text = textInfo[1];
		
		for(int i = 2 ; i < textInfo.length ; i++)
		{
			text = text + "<s>" + textInfo[i];
		}
		
		local_memory.addMessage(Origin.RECEIVED, ipDistantAddress, strDate, text);
		local_interface.process_applyMessage(Origin.RECEIVED, ipDistantAddress, strDate, text);
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
		//ATTENTION ATTENTION ATTENTION ATTETION
		//penser a sauvegarder dans la base de donnée !!
		
		//envoyer le message de déconnexion 
		local_manager.broadcastDisconnected();
		
		local_manager.closeServer();
		
		//fermer les threads
		local_manager.stop();
		processor_messages.interrupt();

		System.exit(0);
	}
	
	private void process_applySendMessage(String distantAddress, String strDate, String wantedMessage) throws IOException
	{
		String datedMessage = strDate + "<s>" + wantedMessage;
		
		local_manager.unicastSendChatMessage(datedMessage, distantAddress);
		
		local_interface.process_applyMessage(Origin.SENT, distantAddress, strDate, wantedMessage);
		
		local_memory.addMessage(Origin.SENT, distantAddress, strDate, wantedMessage);
		 
	}
	
	//------------------- INTERACTIONS WITH USER -----------------------------
	
	private void click_on_validate_pseudonyme_button()
	{
		String wantedPseudo = local_interface.getWrittenPseudonyme();
		String currentError = "";
		if(wantedPseudo.equals("")) 
		{
			currentError = "Impossible to login with an empty pseudo !";
			System.out.println("TEST/ VisualInterface: ");
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
				
				local_interface.openApplicationWindow();
				
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
	
	private void click_on_send_message_button(String distantAddress) throws IOException
	{
		Date now = new Date();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy 'at' HH:mm:ss");  
        String strDate = dateFormat.format(now); 
		
		String wantedMessage = local_interface.getWrittenMessage();
		int length = wantedMessage.length();
		
		if(wantedMessage.equals("")) 
		{
			local_interface.process_applyErrorSending("Impossible to send an empty message");
		}
		else if(length > 500)
		{
			local_interface.process_applyErrorSending("Message too long");
		}		
		else
		{ 
			process_applySendMessage(distantAddress, strDate, wantedMessage);
		}
	}
	
	private void click_on_ExitCurrentChatButton()
	{
		local_interface.click_on_ExitCurrentChatButton();
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
			String distantAddress = local_interface.get_currentChatAddress();
			try 
			{
				click_on_send_message_button(distantAddress);
			} 
			catch (IOException e1) 
			{
				e1.printStackTrace();
			}
		}
		else if(e.getSource().equals(local_interface.getExitCurrentChatButton()))
		{
			click_on_ExitCurrentChatButton();
		}
		else if(e.getSource().equals(local_interface.getExitApplicationButton()))
		{
			process_shutDown();
		}
		
	}

	
	//Appelé lors de la sélection des active users
	public void valueChanged(ListSelectionEvent e) 
	{
		String pseudo_selected = local_interface.getObjectListActiveUsers().getSelectedValue();
		
		
		
	}
}
