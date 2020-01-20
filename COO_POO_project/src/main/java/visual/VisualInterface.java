package visual;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import network.*;
import network.Message.Origin;
import visual.ApplicationWindow.CoordUser;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;


public class VisualInterface implements ActionListener
{
	
	ApplicationWindow main_window;
	PseudonymeWindow pseudo_window; //une seule fenêtre pour deux utilisations
	
	public VisualInterface() throws FontFormatException, IOException
	{
		pseudo_window = new PseudonymeWindow();
		main_window = new ApplicationWindow();
	}
	
	public void download_listOfActiveUsers(LinkedHashMap<String,String> initialList)
	{
		main_window.download_listOfActiveUsers(initialList);
	}
	
	//--------------- open and close windows -----------------------------------------
	
	
	public void openPseudonymeWindow()
	{
		pseudo_window.showWindow();
		System.out.println("TEST/ VisualInterface: ");
	}
	
	public void closePseudonymeWindow()
	{
		pseudo_window.hideWindow();
	}
	
	public void openApplicationWindow()
	{
		main_window.showWindow();
		pseudo_window.changeTo_ModifyWindow();
	}
	
	public void closeApplicationWindow()
	{
		main_window.hideWindow();
	}
	
	//----------------- BUTTONS AND LISTENERS ------------------------------------------------
	
	public void creation_listeners(MainApplication application)
	{
		pseudo_window.creation_listeners_pseudoWindow(application);
		main_window.creation_listeners_appliWindow(application, this);
	}
	
	//-----
	
	public JButton getValidatePseudoButton()
	{
		return pseudo_window.getValidateButton();
	}
	
	public JButton getExitPseudoButton()
	{
		return pseudo_window.getExitButton();
	}
	
	//-----
	
	public JButton getSendMessageButton()
	{
		return main_window.get_sendButton();
	}
	
	public JButton getExitApplicationButton()
	{
		return main_window.get_exitButton();
	}
	
	public JButton getExitCurrentChatButton()
	{
		return main_window.get_exitCurrentChatButton();
	}
	
	public JComboBox<CoordUser> getObjectListActiveUsers()
	{
		return main_window.getObjectListActiveUsers();
	}
	
	//--------------- OTHER INFORMATIONS ---------------------------------------------
	
	public String getWrittenPseudonyme()
	{
		return pseudo_window.getWrittenPseudonyme();
	}
	
	public String getWrittenMessage()
	{
		return main_window.getWrittenMessage();
	}
	
	public String get_currentChatAddress()
	{
		return main_window.get_currentChatAddress();
	}
	
	//--------------- ACTIONS --------------------------------------------------------
	
	
	public void process_ErrorPseudo(String errorMessage)
	{
		pseudo_window.display_error_message(errorMessage);
		pseudo_window.clean_areaEnterPseudonyme();
	}
	
	public void process_login(String validatedPseudo) throws UnknownHostException
	{
		pseudo_window.hideWindow();
		pseudo_window.changeTo_ModifyWindow();
		
		main_window.modifyPseudo(validatedPseudo);
		//main_window sera afficher après avoir enregistré l'utilisateur local 
		//dans la liste des active users
	}
	
	public void process_modifyPseudo(String validatedPseudo) throws UnknownHostException
	{
		pseudo_window.hideWindow();
		pseudo_window.clean_window();
		
		main_window.enable_modifyButton();
		main_window.modifyPseudo(validatedPseudo);
		main_window.showModificationActiveUser(InetAddress.getLocalHost().getHostAddress().toString(), validatedPseudo);
	}
	
	public void process_cancelModifyPseudo()
	{
		pseudo_window.hideWindow();
		pseudo_window.clean_window();
		main_window.enable_modifyButton();
	}
	
	public void process_applyMessage(Origin nature, String distantAddress, String strDate, String message)
	{
		main_window.process_applyMessage(nature, distantAddress, strDate, message);
	}
	
	public void process_applyErrorSending(String errorMessage)
	{
		main_window.display_errorMessage(errorMessage);
	}
	
	//---------------Modifications from received messages among network --------------------
	
	public void showNewActiveUser(String ipAddress, String pseudonyme)
	{
		main_window.showNewActiveUser(ipAddress, pseudonyme);
	}
	
	public void showModificationActiveUser(String ipAddress, String pseudonyme)
	{
		main_window.showModificationActiveUser(ipAddress, pseudonyme);
	}
	
	public void removeActiveUser(String ipAddress)
	{
		main_window.removeActiveUser(ipAddress);
	}
	
	//---------------Actions from events on visual ---------------------------------------------
	
	public void click_on_ExitCurrentChatButton()
	{
		main_window.process_exitCurrentChat();
	}

	
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource().equals(main_window.get_modifyPseudoButton()))
		{
			pseudo_window.showWindow();
			main_window.disable_modifyButton();
		}
	}
	
	//-------------When click on active user list
	
	public boolean chatAlreadyDownloaded(String ipAddress)
	{
		return main_window.chatAlreadyDownloaded(ipAddress);
	}
	
	public void create_openChat(String ipAddress, String history)
	{
		main_window.create_openChat(ipAddress, history);
	}
	
	public void openChatExisting(String ipAddress)
	{
		main_window.showChatSelected(ipAddress);
	}
		
}
