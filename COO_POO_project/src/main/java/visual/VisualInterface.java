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
	PseudonymeWindow pseudo_window;
	
	public VisualInterface()
	{
		
	}
	
	//-----------------------------------------------------------------------------------------
	
	//!!! penser aux listeners !!!
	public void openCreatingPseudonymeWindow() throws FontFormatException, IOException
	{
		pseudo_window = new PseudonymeWindow(PseudoAction.CREATE);
	}
	
	public void openModifyingPseudonymeWindow() throws FontFormatException, IOException
	{
		pseudo_window = new PseudonymeWindow(PseudoAction.MODIFY);
	}
	
	public void openApplicationWindow()
	{
		
	}
	
	//-----------------------------------------------------------------------------------------
	
	public void creation_listeners(MainApplication application)
	{
		pseudo_window.creation_listeners_pseudoWindow(application);
	}
	
	public JButton getValidatePseudoButton()
	{
		return pseudo_window.getValidateButton();
	}
	
	//-----------------------------------------------------------------------------------------
	
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
