package visual;

import javax.swing.*;

import network.MainApplication;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.*;
import java.sql.Date;


public class ChatWindow
{

	private JPanel main_window; 
	private JTextField text_area;
	private JButton send_message;
	private JScrollPane scrollbar;
	private JPanel show_text;
	
	// do we need something like this if message is not fine?  
	// private static JLabel labelError;
	// ===> I don't think because we can display errors directly in the application window
	
	
	//+ JPanel JButton JLabel
	
	//DESIGN
	//palette couleurs ?
	
	public ChatWindow() throws FontFormatException, IOException
	{
		initialize_coding_parameters();
		
		creation_elements();
		
		esthetic_parameters();
		design_elements();
		
		main_window.setLocation(200, 0);
		main_window.setVisible(true);
	}
	
	//------------------------- DIVISION OF PSEUDONYME WINDOW CONSTRUCTOR -------------------------------------------
	
	
	
	
	
	private void initialize_coding_parameters()
	{
		
	}
	
	private void creation_elements() throws FontFormatException, IOException
	{
		main_window = new JPanel(); 
		
		text_area = new JTextField();
		send_message = new JButton("Send");
		scrollbar = new JScrollPane();
		show_text = new JPanel(); 
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
		main_window.setSize(new Dimension(1000, 700));
		
	}
	
	public static void main() throws FontFormatException, IOException
	{
		new MainApplication();
		// to view jpanel in main_window
	}
	
	private void add_and_layout()
	{
		/*
		JPanel.add(button / JLabel); , BorderLayout.WEST);
		
		//main window
		
		GridBagConstraints coord = new GridBagConstraints();
		coord.gridx = 0 ;
		coord.gridy = 0 ;
		main_window.add(JLabel/JPanel, coord);
		*/
		


		// 12.1 ADDED TEXT_AREA, SEND_MESSAGE, SCROLLBAR
		main_window.setLayout(new GridBagLayout());
		main_window.add(text_area);   
		main_window.add(send_message);
		main_window.add(scrollbar);
		main_window.add(show_text);
	}
	
	//--------------------------- Functions to manage the visual Interface / MainApplication ---------------------------------------
	
	
	public JButton getButton()
	{
		return send_message;
	}
	
	
	public void creation_listener_sendButton(MainApplication application)
	{
		send_message.addActionListener(application);
	}

	
	public JButton getSendMessageButton()
	{
		return send_message;
	}
	
	public String getMessageInTextField() {
		String message = text_area.getText();
		return message;
	}
	
	public void printMessage(String message) {
		JLabel label_show_text = new JLabel();
		
		label_show_text.setText(message);
		show_text.add(label_show_text);
		
	}
	
	public void cleanTextArea() {
		send_message.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	        	 // envoyer string to mainapp
	            text_area.setText("");
	         }
	});
	}
	
	public String getWrittenMessage()
	{
		return text_area.getText();
	}
}
