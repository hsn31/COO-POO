package visual;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import network.*;
import java.io.IOException;
import java.text.DateFormat;
import java.util.*;


public class VisualInterface implements ActionListener
{
	
	ApplicationWindow main_window;
	//une seule fenêtre pour deux utilisation
	PseudonymeWindow pseudo_window;
	ChatWindow chat_window;
	
	public VisualInterface() throws FontFormatException, IOException
	{
		pseudo_window = new PseudonymeWindow();
		main_window = new ApplicationWindow();
		chat_window = new ChatWindow();
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
		main_window.creation_listeners_appliWindow(application);
	}
	
	public JButton getValidatePseudoButton()
	{
		return pseudo_window.getValidateButton();
	}
	
	public JButton getExitPseudoButton()
	{
		return pseudo_window.getExitButton();
	}
	
	public JButton getSendMessageButton()
	{
		return chat_window.getButton();
	}
	
	//--------------- OTHER INFORMATIONS ---------------------------------------------
	
	public String getWrittenPseudonyme()
	{
		return pseudo_window.getWrittenPseudonyme();
	}
	
	public String getWrittenMessage()
	{
		return chat_window.getWrittenMessage();
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
		main_window.showWindow();
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
	}
	
	public void process_applyMessage()
	{
		chat_window.cleanTextArea();
		// need for the parameter of a message?
	}

	
	//est-ce que ces 3 fonctions ont besoin de ce parametre en plus ? : String ipAddress, 
	public void showNewActiveUser(String pseudonyme)
	{
		
	}
	
	public void showModificationActiveUser(String pseudonyme)
	{
		
	}
	
	public void removeActiveUser(String pseudonyme)
	{
		//suppress in window application
		//close windows chat
	}


	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource().equals(main_window.get_modifyPseudobutton()))
		{
			
		}
		
	}
		
}
