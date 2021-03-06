package controller;

import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JComboBox;

import model.LocalMemory;
import model.Message.Origin;
import view.VisualInterface;
import view.ApplicationWindow.CoordUser;

public class MainApplication implements ActionListener
{
	private final long Delta = 1000;
	private NetworkManager local_manager;
	private LocalMemory local_memory;
	private VisualInterface local_interface;
	private boolean changingChat = false;
	
	private Thread processor_messages;
	
	private enum AppState
	{
		LOGIN,
		CHATTING,
	}
	
	private AppState local_state;
	
	private String stringlocal_address;
	
	//------------------ CONSTRUCTOR AND TEST ------------------------------------------
	
	public MainApplication() throws FontFormatException, IOException
	{
		local_manager = new NetworkManager();
		
		//transmission de l'adresse locale dans toute l'application
		//en meme temps que la création des classes
		stringlocal_address = local_manager.get_stringlocal_address();
		
		local_memory = new LocalMemory(stringlocal_address);
		local_interface = new VisualInterface(stringlocal_address);
		processor_messages = new Thread(new ProcessingThread(this, stringlocal_address));
		
		//end transmission
		
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
		processor_messages.start();
		
		//Envoi du broadcast d'ID <1>
		local_manager.broadcastGetActiveUser();
		
		//Attention, on fait une pause de 1000 millisecondes pour recevoir les broadcasts 
		while(System.currentTimeMillis()<t1+Delta);
		
		//Test de la liste des utilisateurs. 
		System.out.println("TEST/ NOMBRE D'UTILISATEURS : "+Integer.toString(local_memory.checkActiveUserAmount())+ "\n");
		
		local_state = AppState.LOGIN;
		
		if(local_memory.accountIsAlreadyCreated())
		{
			if(local_memory.lastPseudonymeIsOk())
			{
				local_manager.broadcastConnected(local_memory.getPseudo());
				
				local_interface.showNewActiveUser(stringlocal_address, local_memory.getPseudo());
				local_interface.process_login(local_memory.getPseudo());
				
				//Donc on peut direct afficher l'applicationWindow:
				local_state = AppState.CHATTING;
				
				System.out.println("TEST/ Dans la boucle LastPseudoOK : " + "\n");
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
			System.out.println("TEST/ local_state = login " + "\n");
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
			String ipsender = local_manager.get_stringlocal_address();
			int inPort = local_manager.get_inPort();
			InetAddress ipreceiver = InetAddress.getByName(destAddress);
			local_manager.sendMessage("11<>unicast<>" + ipsender + "<>" + local_memory.getPseudo(), ipreceiver, inPort);
		}
	}
	
	public void addActiveUser(String ipAddress, String pseudonyme)
	{
		local_memory.updateListConnectedBroadcast(ipAddress, pseudonyme);
		local_interface.showNewActiveUser(ipAddress, pseudonyme);
	}
	
	public void modifyActiveUser(String ipAddress, String pseudonyme)
	{
		System.out.print("MAIN APPLICATION UPDATE"+ "\n");
		
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
		
		System.out.println("************************TEST/ addReceivedMessage MainApplication********************* " + "\n");
		
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
	
	private void process_login(String validatedPseudo) throws UnknownHostException

	{
		System.out.println("************************TEST/ process_login********************* " + "\n");
		local_manager.broadcastConnected(validatedPseudo);
		
		local_memory.modifyPseudonyme(validatedPseudo);
		
		local_interface.process_login(validatedPseudo);
		
		local_state = AppState.CHATTING;
	}
	
	private void process_modifyPseudo(String validatedPseudo) throws UnknownHostException
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
		if(local_state == AppState.CHATTING)
		{
			//sauvegarde dans la base de donnée !!
			local_memory.saveHistory();
			
			//envoyer le message de déconnexion 
			local_manager.broadcastDisconnected();
		}
		
		local_manager.closeServer();
		
		//fermer les threads
		local_manager.stop();
		processor_messages.interrupt();

		System.exit(0);
	}
	
	private void process_applySendMessage(String distantAddress, String strDate, String wantedMessage) throws IOException
	{
		String datedMessage = strDate + "<s>" + wantedMessage;
		
		local_interface.process_applyMessage(Origin.SENT, distantAddress, strDate, wantedMessage);
		
		local_memory.addMessage(Origin.SENT, distantAddress, strDate, wantedMessage);
		
		local_manager.unicastSendChatMessage(datedMessage, distantAddress);
	}
	
	//------------------- INTERACTIONS WITH USER -----------------------------
	
	private void click_on_validate_pseudonyme_button() throws UnknownHostException
	{
		String wantedPseudo = local_interface.getWrittenPseudonyme();
		String currentError = "";
		if(wantedPseudo.equals("")) 
		{
			currentError = "Impossible to login with an empty pseudo !";
			System.out.println("TEST/ VisualInterface: " + "\n");
		} 
		else if(wantedPseudo.contains(" "))
		{
			currentError = "<html>Impossible to login with a pseudo <br> which contains a space !</html>";
		} 
		else
		{
			if(local_memory.pseudoAlreadyUsed(wantedPseudo)) 
			{
				currentError = "<html>Impossible to use the pseudonyme '" + wantedPseudo + "' <br>because it is already Online.</html>";
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
	
	private synchronized void process_selectionActiveUser(JComboBox<CoordUser> object)
	{
		if (((CoordUser)object.getSelectedItem())!=null) 
		{
			String ipAddress = ((CoordUser)object.getSelectedItem()).ip;
			//object.getSelectedIndex();
			
			
			if(!local_interface.chatAlreadyDownloaded(ipAddress))
			{
				String htmlHistory = local_memory.downloadChatHTMLHistory(ipAddress);
				local_interface.create_openChat(ipAddress, htmlHistory);
			}
			
			local_interface.openChatExisting(ipAddress);
		}
	}
	
	
	public void actionPerformed(ActionEvent e){
		
		if(e.getSource().equals(local_interface.getValidatePseudoButton())) 
		{
			try 
			{
				click_on_validate_pseudonyme_button();
			} 
			catch (UnknownHostException e1) 
			{
				e1.printStackTrace();
			}
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
			//attention
			click_on_ExitCurrentChatButton();
		}
		else if(e.getSource().equals(local_interface.getExitApplicationButton()))
		{
			process_shutDown();
			
		}
		else if(e.getSource().equals(local_interface.getObjectListActiveUsers()))
		{
			changingChat=true;
			
			process_selectionActiveUser(local_interface.getObjectListActiveUsers());
			
			changingChat=false;
			
			//notify();
		}
		
	}

	public boolean isChangingChat() {
		// TODO Auto-generated method stub
		return changingChat;
	}
}
