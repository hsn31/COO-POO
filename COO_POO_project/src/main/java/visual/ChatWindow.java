package visual;

import javax.swing.*;

import network.MainApplication;

import java.awt.*;
import java.io.IOException;


@SuppressWarnings("serial")
public class ChatWindow extends JPanel
{
	private JScrollPane scrollbar;
	private JPanel showTextPanel;
	private JLabel textLabel;
	
	private JPanel southPanel;
	private JLabel labelError;
	private JTextField text_area;
	private JButton send_message;
	
	//DESIGN
	//palette couleurs ?
	
	public ChatWindow() throws FontFormatException, IOException
	{
		initialize_coding_parameters();
		
		creation_elements();
		
		esthetic_parameters();
		design_elements();
	}
	
	//------------------------- DIVISION OF PSEUDONYME WINDOW CONSTRUCTOR -------------------------------------------
	
	
	private void initialize_coding_parameters()
	{
		
	}
	
	private void creation_elements() throws FontFormatException, IOException
	{
		scrollbar = new JScrollPane();
		showTextPanel = new JPanel();
		textLabel = new JLabel("");
		
		southPanel = new JPanel(); 
		labelError = new JLabel(""); 
		text_area = new JTextField();
		send_message = new JButton("Send");
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
		scrollbar.setPreferredSize(new Dimension(130, 40));
		showTextPanel.setPreferredSize(new Dimension(130, 40));
		textLabel
		
		labelError.setPreferredSize(new Dimension(130, 40));
		text_area.setPreferredSize(new Dimension(130, 40));
		send_message.setPreferredSize(new Dimension(130, 40));
		
		//main window
		this.setPreferredSize(new Dimension(130, 40));
		*/
	}
	
	private void add_and_layout()
	{		
		this.setLayout(new BorderLayout());
		
		this.add(scrollbar, BorderLayout.CENTER);
		scrollbar.add(showTextPanel);
		showTextPanel.add(textLabel);
		
		this.add(southPanel, BorderLayout.SOUTH);
		southPanel.setLayout(new BorderLayout());
		
		southPanel.add(labelError, BorderLayout.NORTH);
		southPanel.add(text_area, BorderLayout.CENTER);
		southPanel.add(send_message, BorderLayout.EAST);
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
	
	public String getWrittenMessage()
	{
		return text_area.getText();
	}
	
	//----------------------- ACTIONS ON DISPLAY -----------------------------------
	
	public void clean_TextArea() 
	{
		text_area.setText("");
	}
	
	public void clean_errorMessage()
	{
		labelError.setText("");
	}
	
	public void display_errorMessage(String errorMessage)
	{
		labelError.setText(errorMessage);
	}
	
	public void process_exitCurrentChat()
	{
		clean_TextArea();
		clean_errorMessage();
		text_area.setEnabled(false);
	}
	
	public void showConversation(String totalText)
	{
		textLabel.setText(totalText);
		text_area.setEnabled(true);
	}
	
}
