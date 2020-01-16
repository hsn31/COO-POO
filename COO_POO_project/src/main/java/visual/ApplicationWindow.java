package visual;

import java.awt.*;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import java.net.*;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import network.*;

public class ApplicationWindow implements ActionListener
{

	private JFrame main_window;
	private JLabel wallpaper_area;
	
	//+ JPanel JButton JLabel par zone dans la frame
	
	private JPanel northPanel;
	private JPanel loginPanel;
	private JLabel pseudoLabel;
	private JLabel logoLabel;
	private JButton modifyPseudoButton;
	private JButton exitButton;
	
	private JPanel eastPanel;
	private JScrollPane listScroller;
	private JList<String> areaListActiveUsers;
	private ListModel<String> listActiveUsers;
	
	private JPanel centralChatsPanel;
	private JButton exitCurrentChatButton;
	private ChatWindow currentChatPanel;
	
	//DESIGN
	private ImageIcon wallpaper;
	private ImageIcon logo;
	//palette couleurs ?

	
	//coding_parameters :

	//<Key : AdresseIP, Value : Pseudo> => same index than in DefaultListMdodel
	private LinkedHashMap<String,String> listOfActiveUsers;
	//<Key : AdresseIP, Value : total_conversation> => all the chat Panels open : max 50, 1000 on the network
	private LinkedHashMap<String,String> listOfChats;
	
	public ApplicationWindow(LinkedHashMap<String,String> initialUsersList) throws FontFormatException, IOException
	{
		initialize_coding_parameters(initialUsersList);
		
		creation_elements();
		creation_listeners();
		
		esthetic_parameters();
		design_elements();
		
		exitCurrentChatButton.setVisible(false);
		
		main_window.setLocation(200, 0); //!!
		main_window.setVisible(false);
		main_window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); 
	}
	
	//------------------------- DIVISION OF PSEUDONYME WINDOW CONSTRUCTOR -------------------------------------------
	
	private void initialize_coding_parameters(LinkedHashMap<String,String> initialList)
	{
		listOfActiveUsers = new LinkedHashMap<String,String>();
		listOfActiveUsers.putAll(initialList);
		
		listOfChats = new LinkedHashMap<String,String>();
	}
	
	private void creation_elements() throws FontFormatException, IOException
	{
		//DESIGN
		wallpaper = new ImageIcon("wallpaper_application.jpg");
		logo = new ImageIcon("logo_local_user.jpg");
		
		//MAIN WINDOW
		main_window = new JFrame("Zone de clavardage");
		wallpaper_area = new JLabel(wallpaper);
		
		northPanel = new JPanel();
		loginPanel = new JPanel();
		pseudoLabel = new JLabel();
		logoLabel = new JLabel(logo);
		modifyPseudoButton = new JButton("Modify your pseudo");
		exitButton = new JButton("Exit");
		
		eastPanel = new JPanel();
		listActiveUsers = new DefaultListModel<String>();
		areaListActiveUsers = new JList<String>(listActiveUsers);
		listScroller = new JScrollPane(areaListActiveUsers);
		areaListActiveUsers.setAutoscrolls(true); //??????????????????????????????????????????????????
		//ou ca ??? listScroller.setAutoscrolls(true); 
		
		centralChatsPanel = new JPanel();
		exitCurrentChatButton = new JButton("\u00d7"); //croix du multiplié
		currentChatPanel = new ChatWindow();
	}
	
	private void creation_listeners()
	{
		
		//listScroller ??
		//button.addActionListener(this);
		//sub_area.creation_listeners(this);
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
		int x = 3;
		
		northPanel.setPreferredSize(new Dimension(x, x));
		loginPanel.setPreferredSize(new Dimension(x, x));
		pseudoLabel.setPreferredSize(new Dimension(x, x));
		logoLabel.setPreferredSize(new Dimension(x, x));
		modifyPseudoButton.setPreferredSize(new Dimension(x, x));
		exitButton.setPreferredSize(new Dimension(x, x));
		
		eastPanel.setPreferredSize(new Dimension(x, x));
		listScroller.setPreferredSize(new Dimension(x, x));
		areaListActiveUsers.setPreferredSize(new Dimension(x, x));
		
		centralChatsPanel.setPreferredSize(new Dimension(x, x));
		exitCurrentChatButton.setPreferredSize(new Dimension(x, x));
		//currentChatPanel already done in constructor
		
		//main window
		main_window.setSize(new Dimension(x, x)); //500 SUR 500 ???
		main_window.setResizable(false); //??????
	}
	
	private void add_and_layout()
	{
		main_window.setLayout(new BorderLayout());
		
		main_window.add(northPanel, BorderLayout.NORTH);
		northPanel.add(loginPanel);
		
		loginPanel.setLayout(new BorderLayout());
		loginPanel.add(pseudoLabel, BorderLayout.CENTER);
		loginPanel.add(logoLabel, BorderLayout.WEST);
		loginPanel.add(modifyPseudoButton, BorderLayout.SOUTH);
		
		northPanel.add(exitButton);
		
		main_window.add(eastPanel, BorderLayout.EAST);
		eastPanel.add(listScroller);
		listScroller.add(areaListActiveUsers);
		
		main_window.add(centralChatsPanel, BorderLayout.CENTER);
		centralChatsPanel.add(exitCurrentChatButton);

		main_window.pack(); //to keep all the size of the wallpaper
	}
	
	//--------------------------- Functions to manage the visual Interface / MainApplication ---------------------------------------
	
	
	public JButton get_modifyPseudoButton()
	{
		return modifyPseudoButton;
	}
	
	public JButton get_exitButton()
	{
		return exitButton;
	}
	
	public JButton get_sendButton()
	{
		return currentChatPanel.getSendMessageButton();
	}
	
	public JButton get_exitCurrentChatButton()
	{
		return exitCurrentChatButton;
	}
	
	
	public void creation_listeners_appliWindow(MainApplication application, VisualInterface local_interface)
	{
		exitButton.addActionListener(application);
		areaListActiveUsers.addListSelectionListener(application);
		currentChatPanel.creation_listener_sendButton(application);
		exitCurrentChatButton.addActionListener(application);
		
		modifyPseudoButton.addActionListener(local_interface);
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
	
	//--------------------------- REFRESHING ACTIONS ------------------------------------------
	
	public void modifyPseudo(String newPseudo)
	{
		//JLabel.setText(newPseudo);
		//aussi dans la list et dans le chat
	}
	
	public void showChatSelected(String ipAddress)
	{
		if(listOfChats.containsKey(ipAddress))
		{
			
		}
		else
		{
			
		}
	}
	
	
	//---------------------------Functions to manage the interaction with the user------------------------------
	
	
	
	private void clean_alert_message()
	{
		
	}
	
	
	//------------------Management of interaction with the User and Listeners---------------------------------
	
	
	public void actionPerformed(ActionEvent e) 
	{
		/*
		if(e.getSource().equals(button))
		{
			try 
			{
				click_on_button();
			} 
			catch (InterruptedException e1) 
			{
				e1.printStackTrace();
			}
		}
		*/
	}
	
	
	
	
	//////////////////////////////////////////////////////////////////////////////////////
	
	/*
	
		jListConnectedUser.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				jListConnectedUser.setSelectionForeground(Color.BLACK);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}
		});
		


	}

	*/
}