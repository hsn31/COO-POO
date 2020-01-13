package visual;

import java.awt.*;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.net.*;

import network.*;



public class PseudonymeWindow
{
	
	private String titleWindow;
	private JFrame main_window;
	private JLabel wallpaper_area;
	
	//+ JPanel JButton JLabel par zone dans la frame
	private static JLabel labelInfo;
	private static JLabel labelError;
	private JTextField areaEnterPseudonyme; //box
	private JButton validateButton;
	private JButton exitButton;
	
	//DESIGN
	private ImageIcon wallpaper;
	//palette couleurs ?
	
	public PseudonymeWindow() throws FontFormatException, IOException
	{
		creation_elements();
		
		esthetic_parameters();
		design_elements();
		
		main_window.setLocation(200, 0);
		main_window.setVisible(false);
		main_window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}
	
	//------------------------- DIVISION OF PSEUDONYME WINDOW CONSTRUCTOR ------------------
	
	private void creation_elements() throws FontFormatException, IOException
	{
		//DESIGN
		wallpaper = new ImageIcon("wallpaper_pseudonyme.jpg");
		
		//MAIN WINDOW
		titleWindow = "Chat Application - Login step";
		
		main_window = new JFrame(titleWindow);
		wallpaper_area = new JLabel(wallpaper);
		
		//new : JPanel JButton JLabel par zone dans la frame
		
        labelInfo = new JLabel("Welcome. Please enter your pseudonyme", JLabel.CENTER);
        labelError = new JLabel("");
        areaEnterPseudonyme = new JTextField();
        validateButton = new JButton("Validate Pseudonyme");
        exitButton = new JButton("Exit");
	}
	
	private void esthetic_parameters() throws FontFormatException, IOException
	{
		//design_palette = general_palette;
		
		change_colors();
		text_design();
	}
	
	private void change_colors()
	{
		//JLabel.setOpaque(false);
	
		//button.setBackground(design_palette.get_color(CreatedColor.WHITE));
		
		//JLabel.setForeground(design_palette.get_color(CreatedColor.DARK_BROWN_INTENSE));
		//JLabel.setBackground(design_palette.get_color(CreatedColor.GOLD_YELLOW_INTENSE));
		
		//background window
		main_window.setContentPane(wallpaper_area);
	}
	
	private void text_design() throws FontFormatException, IOException
	{
		//button.setFont(design_palette.get_font(CreatedFont.BUTTON_GAME));
		//JLabel.setFont(design_palette.get_font(CreatedFont.BASIC_SIMPLE));
		
		/*
		Border raisedbevel = BorderFactory.createRaisedBevelBorder();
		Border loweredbevel = BorderFactory.createLoweredBevelBorder();
		Border compound = BorderFactory.createCompoundBorder(raisedbevel, loweredbevel);
		TitledBorder legend = BorderFactory.createTitledBorder(compound, legend_result_area, TitledBorder.LEFT,TitledBorder.TOP, design_palette.get_font(CreatedFont.LITTLE_SIMPLE), design_palette.get_color(CreatedColor.DARK_BROWN_INTENSE));
		JLabel.setBorder(legend);
		*/
	}
	
	private void design_elements()
	{
		fix_dimensions();
		add_and_layout();
	}
	
	private void fix_dimensions()
	{
		/*
		JLabel.setPreferredSize(new Dimension(main_width, 100));
		button.setPreferredSize(new Dimension(130, 40));
		*/
		
		//main window
		main_window.setSize(new Dimension(850, 400));
		main_window.setResizable(false); //??????
	}
	
	private void add_and_layout()
	{
		main_window.setLayout(new GridLayout(0, 1));
		
		main_window.add(labelInfo);   
		main_window.add(labelError);
		main_window.add(areaEnterPseudonyme);
		main_window.add(validateButton);
		main_window.add(exitButton);
	
		main_window.pack(); //to keep all the size of the wallpaper
	}
	
	//--------------------------- Functions to manage the visual Interface / MainApplication ---------------------------------------
	
	
	public JButton getValidateButton()
	{
		return validateButton;
	}
	
	public JButton getExitButton()
	{
		return exitButton;
	}
	
	public void creation_listeners_pseudoWindow(MainApplication application)
	{
		validateButton.addActionListener(application);
		exitButton.addActionListener(application);
	}
	
	public String getWrittenPseudonyme()
	{
		return areaEnterPseudonyme.getText();
	}
	
	//---------------------------------------------------//
	
	public void showWindow()
	{
		main_window.setVisible(true);
	}
	
	public void hideWindow()
	{
		main_window.setVisible(false);
	}
	

	//---------------------------Functions to manage the interaction with the user------------------------------
	
	public void changeTo_ModifyWindow()
	{
		main_window.setTitle("Modify your pseudonyme");
		clean_window();
	}
	
	public void display_error_message(String message)
	{
		labelError.setText(message);
	}
	
	public void clean_window()
	{
		labelError.setText("");
		areaEnterPseudonyme.setText("");
	}
	
	public void clean_error_message()
	{
		labelError.setText("");
	}
	
	public void clean_areaEnterPseudonyme()
	{
		areaEnterPseudonyme.setText("");
	}
	
}