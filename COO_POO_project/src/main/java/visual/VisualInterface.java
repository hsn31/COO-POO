package visual;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import network.*;
import visual.PseudonymeWindow.PseudoAction;

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
		pseudo_window = new PseudonymeWindow(PseudoAction.CREATE);
	}
	
	//---------------Methods pas sur de les garder -----------------------------------------
	
	
	public void openCreatingPseudonymeWindow()
	{
		pseudo_window.showWindow();
	}
	
	public void openModifyingPseudonymeWindow()
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
	
	//--------------- OTHER INFORMATIONS ---------------------------------------------
	
	public String getWrittenPseudonyme()
	{
		return pseudo_window.getWrittenPseudonyme();
	}
	
	//--------------- ACTIONS --------------------------------------------------------
	
	
	
	
	
	
	//est-ce que ces 3 fonctions ont besoin de ce parametre ? : String ipAddress, 
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

	
	//-----------------------------------------------------------------------------------------

	public void actionPerformed(ActionEvent e) 
	{
		
		
	}
		
}
