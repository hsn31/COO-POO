package visual;

import java.awt.*;
import java.io.IOException;
import javax.swing.*;
import java.net.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import network.*;
import network.Message.Origin;

public class ApplicationWindow
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
	private DefaultListModel<String> listActiveUsers;
	
	private JPanel centralChatsPanel;
	private JPanel topChatPanel;
	private JButton exitCurrentChatButton;
	private JLabel chatWithLabel;
	private ChatWindow currentChatPanel;
	
	//DESIGN
	private ImageIcon wallpaper;
	private ImageIcon logo;
	//palette couleurs ?

	
	//Coding_parameters :

	//<Key : AdresseIP, Value : Pseudo> => same index than in DefaultListModel
	private LinkedHashMap<String,String> listOfActiveUsers;
	//<Key : AdresseIP, Value : total_conversation> => all the chat Panels open : max 50, 1000 on the network
	private LinkedHashMap<String,String> listOfChats;
	
	private String local_ipAddress;
	private String currentChatVisibleAddress;
	
	public ApplicationWindow() throws FontFormatException, IOException
	{
		initialize_coding_parameters();
		
		creation_elements();
		
		esthetic_parameters();
		design_elements();
		
		exitCurrentChatButton.setVisible(false);
		
		main_window.setLocation(200, 0); //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		main_window.setVisible(false);
		main_window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); 
	}
	
	//------------------------- DIVISION OF PSEUDONYME WINDOW CONSTRUCTOR -------------------------------------------
	
	private void initialize_coding_parameters() throws UnknownHostException
	{
		listOfActiveUsers = new LinkedHashMap<String,String>();		
		listOfChats = new LinkedHashMap<String,String>();
		
		local_ipAddress = InetAddress.getLocalHost().toString();
		currentChatVisibleAddress = "";
	}
	
	private void creation_elements() throws FontFormatException, IOException
	{
		//DESIGN
		java.net.URL imgUrlWallpaper = getClass().getResource("wallpaper_application.jpg");
		wallpaper = new ImageIcon(imgUrlWallpaper);
		java.net.URL imgUrlLogo = getClass().getResource("logo_local_user.jpg");
		logo = new ImageIcon(imgUrlLogo);
		
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
		areaListActiveUsers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		areaListActiveUsers.setLayoutOrientation(JList.VERTICAL);
		areaListActiveUsers.setVisibleRowCount(-1);
		listScroller = new JScrollPane(areaListActiveUsers);
		
		centralChatsPanel = new JPanel();
		topChatPanel = new JPanel();
		exitCurrentChatButton = new JButton("\u00d7"); //croix du multiplié
		chatWithLabel = new JLabel("You are chatting with: ");
		currentChatPanel = new ChatWindow();
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
		
		exitCurrentChatButton.setVisible(false);
		currentChatPanel.setVisible(false);
		
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
		
		//northPanel.setPreferredSize(new Dimension(x, x));
		//loginPanel.setPreferredSize(new Dimension(x, x));
		//pseudoLabel.setPreferredSize(new Dimension(x, x));
		logoLabel.setPreferredSize(new Dimension(70, 70));
		//modifyPseudoButton.setPreferredSize(new Dimension(x, x));
		//exitButton.setPreferredSize(new Dimension(x, x));
		
		//eastPanel.setPreferredSize(new Dimension(x, x));
		//listScroller.setPreferredSize(new Dimension(x, x));
		//areaListActiveUsers.setPreferredSize(new Dimension(x, x));
		
		//centralChatsPanel.setPreferredSize(new Dimension(x, x));
		//topChatPanel.setPreferredSize(new Dimension(x, x));
		exitCurrentChatButton.setPreferredSize(new Dimension(40, 40));
		//chatWithLabel.setPreferredSize(new Dimension(x, x));
		//currentChatPanel already done in constructor
		
		//main window
		main_window.setPreferredSize(new Dimension(500, 500)); //500 SUR 500 ???!!!!!!!!!!!!!!!
		main_window.setResizable(false); //??????!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	}
	
	private void add_and_layout()
	{
		main_window.setLayout(new BorderLayout());
		
		main_window.add(northPanel, BorderLayout.NORTH);
		northPanel.setLayout(new BorderLayout());
		
		northPanel.add(loginPanel, BorderLayout.CENTER);
		
		loginPanel.setLayout(new BorderLayout());
		loginPanel.add(pseudoLabel, BorderLayout.CENTER);
		loginPanel.add(logoLabel, BorderLayout.WEST);
		loginPanel.add(modifyPseudoButton, BorderLayout.SOUTH);
		
		northPanel.add(exitButton, BorderLayout.EAST);
		
		main_window.add(eastPanel, BorderLayout.EAST);
		eastPanel.add(listScroller);
		listScroller.add(areaListActiveUsers);
		
		main_window.add(centralChatsPanel, BorderLayout.CENTER);
		centralChatsPanel.setLayout(new BorderLayout());
		
		centralChatsPanel.add(topChatPanel, BorderLayout.NORTH);
		topChatPanel.add(exitCurrentChatButton);
		topChatPanel.add(chatWithLabel);
		centralChatsPanel.add(currentChatPanel, BorderLayout.CENTER);

		main_window.pack(); //to keep all the size of the wallpaper
	}
	
	//---------- Functions to manage the visual Interface / MainApplication (BUTTONS AND LISTENERS)---------------------------------------
	
	
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
	
	public JList<String> getObjectListActiveUsers()
	{
		return areaListActiveUsers;
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
	
	//------------------------------- INFORMATION ------------------------------------------
	
	public String getWrittenMessage()
	{
		return currentChatPanel.getWrittenMessage();
	}
	
	public String get_currentChatAddress()
	{
		return currentChatVisibleAddress;
	}
	
	public boolean chatAlreadyDownloaded(String ipAddress)
	{
		return listOfChats.containsKey(ipAddress);
	}
	
	//--------------------------- CHANGE VALUES OF DATA ------------------------------------
	
	public void create_openChat(String ipAddress)
	{
		listOfChats.put(ipAddress, "");
	}
	
	//--------------------------- REFRESHING/DISPLAYING ACTIONS ----------------------------
	

	public void download_listOfActiveUsers(LinkedHashMap<String,String> initialList)
	{
		listOfActiveUsers.putAll(initialList);
		
		//Test 
		System.out.println("TEST/ ApplicationWindow download_listOfActiveUsers: listofActiveUsers ");
		debugging(initialList);
	}
	
	public void modifyPseudo(String newPseudo)
	{
		pseudoLabel.setText(newPseudo);
		
		if(currentChatVisibleAddress.equals(local_ipAddress))
		{
			display_distantPseudoCurrentChat(newPseudo);
		}
	}
	
	public void showChatSelected(String ipAddress)
	{
		String totalText = "<html>" + listOfChats.get(ipAddress) + "</html>";
		currentChatPanel.showConversation(totalText);
		
		System.out.println("TEST/ ApplicationWindow showChatSelected: listofActiveUsers " + currentChatPanel);
		
		if(!currentChatVisibleAddress.equals(ipAddress))
		{
			display_distantPseudoCurrentChat(listOfActiveUsers.get(ipAddress));
			System.out.println("TEST/ ApplicationWindow showChatSelected: listofActiveUsers ");
		}

		clean_errorMessage();
		centralChatsPanel.setVisible(true);
		currentChatVisibleAddress = ipAddress;
	}

	
	public void process_applyMessage(Origin nature, String distantAddress, String strDate, String message)
	{
		if(listOfChats.containsKey(distantAddress))
		{
			String balise = "<hr>";
			
			if(nature == Origin.RECEIVED)
			{
				balise = "<hr align=\"left\" color =\"#4664B5\">";
			}
			else if(nature == Origin.SENT)
			{
				//#0066FF
				balise = "<hr align=\"right\" color =\"#0066FF\">";
			}
			
			String txtDate = balise + strDate + "</hr>";
			String txtMessage = balise + message + "</hr>";
			String txtConversation = listOfChats.get(distantAddress) + "<br><br>" + txtDate + "<br>" + txtMessage;
			
			listOfChats.replace(distantAddress, txtConversation);
			
			if(currentChatVisibleAddress.equals(distantAddress))
			{
				showChatSelected(distantAddress);
			}
		}
	}
	
	public void process_exitCurrentChat()
	{
		centralChatsPanel.setVisible(false);
		display_distantPseudoCurrentChat("");
		currentChatPanel.process_exitCurrentChat();
	}
	
	//---------------little Actions  ---------------------------------------------
	
	private void display_distantPseudoCurrentChat(String pseudo)
	{
		chatWithLabel.setText("You are chatting with: " + pseudo);
	}
	
	public void clean_errorMessage()
	{
		currentChatPanel.clean_errorMessage();
	}
	
	public void display_errorMessage(String errorMessage)
	{
		currentChatPanel.display_errorMessage(errorMessage);
	}
	
	public void enable_modifyButton()
	{
		modifyPseudoButton.setEnabled(true);
	}
	
	public void disable_modifyButton()
	{
		modifyPseudoButton.setEnabled(false);
	}

	
	//---------------Modifications from received messages among network --------------------
	
	public void showNewActiveUser(String ipAddress, String pseudonyme)
	{
		listOfActiveUsers.put(ipAddress, pseudonyme);
		listActiveUsers.addElement(pseudonyme);
		System.out.println("TEST/ ApplicationWindow showNewActiveUser: listofActiveUsers ");
		System.out.println("TEST/ ApplicationWindow showNewActiveUser: IP = " + ipAddress + " PSEUDO = " + pseudonyme );
		//areaListActiveUsers.setModel(listActiveUsers); ????? /!\ ca annule la selection precedente
		//
		//refresh ?????????????????????????????????????????????????????????????????
	}
	
	public void showModificationActiveUser(String ipAddress, String pseudonyme)
	{
		listActiveUsers.removeElement(listOfActiveUsers.get(ipAddress));
		listOfActiveUsers.replace(ipAddress, pseudonyme);
		listActiveUsers.addElement(pseudonyme);
		areaListActiveUsers.setModel(listActiveUsers); //????? /!\ ca annule la selection precedente
		System.out.println("TEST/ ApplicationWindow showModificationActiveUser: listofActiveUsers ");
		//
		//refresh ?????????????????????????????????????????????????????????????????
		
		if(currentChatVisibleAddress.equals(ipAddress))
		{
			display_distantPseudoCurrentChat(pseudonyme);
		}
	}
	
	public void removeActiveUser(String ipAddress)
	{
		listActiveUsers.removeElement(listOfActiveUsers.get(ipAddress));
		listOfActiveUsers.remove(ipAddress);
		listOfChats.remove(ipAddress);
		//areaListActiveUsers.setModel(listActiveUsers); ????? /!\ ca annule la selection precedente
		//
		//refresh ?????????????????????????????????????????????????????????????????
		
		if(currentChatVisibleAddress.equals(ipAddress))
		{
			process_exitCurrentChat();
		}
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

	*/
	
	
	//Debugging Permet d'afficher les valeurs de LinkedHashMap. 
	
	public void debugging (LinkedHashMap<String,String> initialList) {
	// Affecter les valeurs à une collection
	Collection c = initialList.values();

	//Créer un itérateur sur la collection
	Iterator itr = c.iterator();

	System.out.println("--------------------Affichage des valeurs de la LinkedHashMap---------------------");
	//itérer la collection et afficher le résultat
	while(itr.hasNext())
	System.out.println(itr.next());
	
	System.out.println("-----------------FIN DE L'AFFICHAGE--------------");
	}
}