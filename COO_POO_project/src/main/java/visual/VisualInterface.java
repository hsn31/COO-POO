package visual;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import network.*;
import network.Message.Origin;

import java.io.IOException;
import java.text.DateFormat;
import java.util.*;


public class VisualInterface implements ActionListener
{
	
	ApplicationWindow main_window;
	//une seule fenêtre pour deux utilisation
	PseudonymeWindow pseudo_window;
	
	public VisualInterface() throws FontFormatException, IOException
	{
		pseudo_window = new PseudonymeWindow();
		main_window = new ApplicationWindow();
	}
	
	public void download_listOfActiveUsers(LinkedHashMap<String,String> initialList)
	{
		main_window.download_listOfActiveUsers(initialList);
	}
	
	//---------------Methods pas sur de les garder -----------------------------------------
	
	
	public void openPseudonymeWindow()
	{
		pseudo_window.showWindow();
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
	
	public void process_login(String validatedPseudo)
	{
		pseudo_window.hideWindow();
		pseudo_window.changeTo_ModifyWindow();
		
		main_window.modifyPseudo(validatedPseudo);
		//main_window sera afficher après avoir enregistré l'utilisateur local dans la liste des active users
	}
	
	public void process_modifyPseudo(String validatedPseudo)
	{
		pseudo_window.hideWindow();
		pseudo_window.clean_window();
		
		main_window.modifyPseudo(validatedPseudo);
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
		main_window.process_applyErrorSending(errorMessage);
	}
	
	public void showNewActiveUser(String ipAddress, String pseudonyme)
	{
		
	}
	
	public void showModificationActiveUser(String ipAddress, String pseudonyme)
	{
		
	}
	
	public void removeActiveUser(String ipAddress)
	{
		//suppress in window application
		//close windows chat
	}
	
	public void click_on_ExitCurrentChatButton()
	{
		main_window.process_exitCurrentChat();
	}

	//------------------------------------------------------------------------------------
	
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource().equals(main_window.get_modifyPseudoButton()))
		{
			pseudo_window.showWindow();
			main_window.disable_modifyButton();
		}
	}
		
}
