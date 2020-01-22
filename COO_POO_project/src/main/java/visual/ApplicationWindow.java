package visual;

import java.awt.*;
import java.io.IOException;
import javax.swing.*;
import java.net.*;
import java.util.ArrayList;
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
	
	private JPanel centralChatsPanel;
	private JPanel topChatPanel;
	private JButton exitCurrentChatButton;
	private JLabel chatWithLabel;
	private JComboBox<CoordUser> listBox;
	private ChatWindow currentChatPanel;
	
	//DESIGN
	private ImageIcon wallpaper;
	private ImageIcon logo;

	
	//Coding_parameters :
	
	public class CoordUser extends Object
	{
		public String ip;
		public String pseudo;
		public boolean chat_downloaded;
		
		public CoordUser(String address, String name)
		{
			ip = address;
			pseudo = name;
			chat_downloaded = false;
		}
		
		public String toString()
		{
			String result = pseudo;
			
			if(chat_downloaded)
			{
				result = result + " (Open)";
			}
			
			return result;
		}
		
		public boolean equals(CoordUser cu)
		{
			return (cu.ip.equals(this.ip) || cu.pseudo.equals(this.pseudo));
		}
	}
	
	public class ListCoord extends ArrayList<CoordUser>
	{
		private static final long serialVersionUID = 1L; //mandatory by default

		public int indexOf(CoordUser cu)
		{
			int result = -1;
			int index = 0;
			
			while(index<this.size() && result==-1)
			{
				if(this.get(index).equals(cu))
				{
					result = index;
				}
				
				index++;
			}
		
			return result;
		}
	}
	
	private ListCoord listOfActiveUsers;
	//<Key : AdresseIP, Value : total_conversation> => all the chat Panels open : max 50, 1000 on the network
	private LinkedHashMap<String,String> listOfChats;
	
	private String local_ipAddress;
	private String currentChatVisibleAddress;
	
	public ApplicationWindow(String stringlocal_address) throws FontFormatException, IOException
	{
		initialize_coding_parameters(stringlocal_address);
		
		creation_elements();
		
		esthetic_parameters();
		design_elements();
		
		main_window.setLocation(425, 150);
		main_window.setVisible(false);
		disable_chatArea();
		main_window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); 
	}
	
	//------------------------- DIVISION OF PSEUDONYME WINDOW CONSTRUCTOR -------------------------------------------
	
	private void initialize_coding_parameters(String local_address) throws UnknownHostException
	{
		listOfActiveUsers = new ListCoord();
		listOfChats = new LinkedHashMap<String,String>();
		
		local_ipAddress = local_address;
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
			
		centralChatsPanel = new JPanel();
		topChatPanel = new JPanel();
		exitCurrentChatButton = new JButton("\u00d7"); //croix du multiplié
		chatWithLabel = new JLabel("You are chatting with: ");
		listBox = new JComboBox<CoordUser>();
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
		
		//centralChatsPanel.setPreferredSize(new Dimension(x, x));
		//topChatPanel.setPreferredSize(new Dimension(x, x));
		exitCurrentChatButton.setPreferredSize(new Dimension(50, 20));
		//chatWithLabel.setPreferredSize(new Dimension(x, x));
		listBox.setPreferredSize(new Dimension(200, 20));
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

		main_window.add(centralChatsPanel, BorderLayout.CENTER);
		centralChatsPanel.setLayout(new BorderLayout());
		
		centralChatsPanel.add(topChatPanel, BorderLayout.NORTH);
		topChatPanel.add(exitCurrentChatButton);
		topChatPanel.add(chatWithLabel);
		topChatPanel.add(listBox);
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
	
	public JComboBox<CoordUser> getObjectListActiveUsers()
	{
		return listBox;
	}
	
	public void creation_listeners_appliWindow(MainApplication application, VisualInterface local_interface)
	{
		exitButton.addActionListener(application);
		listBox.addActionListener(application);
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
	
	public void create_openChat(String ipAddress, String history)
	{
		listOfChats.put(ipAddress, history);
		
		int place = listOfActiveUsers.indexOf(new CoordUser(ipAddress, ""));
		CoordUser cu = listOfActiveUsers.get(place);
		cu.chat_downloaded = true;
		listOfActiveUsers.set(place, cu);
		System.out.println("TEST/ ApplicationWindow create_openChat" + place + cu);
		
		refresh_comboBox();
	}
	
	//--------------------------- REFRESHING/DISPLAYING ACTIONS ----------------------------
	

	public void download_listOfActiveUsers(LinkedHashMap<String,String> initialList)
	{
		initialList.forEach((k,v)->
		{
			listOfActiveUsers.add(new CoordUser(k, v));
			
			
			System.out.println("TEST/ ApplicationWindow download_listOfActiveUsers" + k + v);
		});
		
		//Test 
		System.out.println("TEST/ ApplicationWindow download_listOfActiveUsers: listofActiveUsers ");
		debugging(initialList);
		
		refresh_comboBox();
	}
	
	public void modifyPseudo(String newPseudo)
	{
		pseudoLabel.setText(newPseudo);
		
		if(currentChatVisibleAddress.equals(local_ipAddress))
		{
			display_distantPseudoCurrentChat(newPseudo);
		}
		
		int place = listOfActiveUsers.indexOf(new CoordUser(local_ipAddress, ""));
		CoordUser cu = listOfActiveUsers.get(place);
		cu.pseudo = newPseudo;
		listOfActiveUsers.set(place, cu);
		System.out.println("TEST/ ApplicationWindow modifyPseudo" + place + cu);
	}
	
	public void showChatSelected(String ipAddress)
	{
		//already existing !!
		String totalText = "<html>" + listOfChats.get(ipAddress) + "</html>";
		currentChatPanel.showConversation(totalText);
		
		//if(!currentChatVisibleAddress.equals(ipAddress))
		//{
			int place = listOfActiveUsers.indexOf(new CoordUser(ipAddress, ""));
			display_distantPseudoCurrentChat(listOfActiveUsers.get(place).pseudo);
			System.out.println("TEST/ ApplicationWindow showChatSelected: listofActiveUsers ");
		//}
		
		enable_chatArea();
		
		clean_errorMessage();
		currentChatVisibleAddress = ipAddress;
	}

	
	public void process_applyMessage(Origin nature, String distantAddress, String strDate, String message)
	{
		System.out.println("process_applyMessage ");
		if(listOfChats.containsKey(distantAddress))
		{
			System.out.println("process_applyMessage Dans la boucle ?"+listOfChats.containsKey(distantAddress) + "\n");
			String balise = "<p>";
			
			if(nature == Origin.RECEIVED)
			{
				//blanc : #FFFFFF
				balise = "<p color =#FFFFFF>";
			}
			else if(nature == Origin.SENT)
			{
				//bleu : #0066FF
				balise = "<p color =#0066FF>";
			}
			
			String txtDate = balise + strDate + "</p>";
			String txtMessage = balise + message + "</p>";
			//les balises p sautent déjà une ligne
			String txtConversation = listOfChats.get(distantAddress) + "<br>" + txtDate + txtMessage;
			
			listOfChats.replace(distantAddress, txtConversation);
			
			if(currentChatVisibleAddress.equals(distantAddress))
			{
				System.out.println("process_applyMessage variable"+currentChatVisibleAddress + "\n");
				System.out.println("process_applyMessage variable"+currentChatVisibleAddress.equals(distantAddress) + "\n");
				//System.out.println("s" + distantAddress);
				showChatSelected(distantAddress);
			}
		}
	}
	
	public void process_exitCurrentChat()
	{
		listOfChats.remove(currentChatVisibleAddress);
		
		int place = listOfActiveUsers.indexOf(new CoordUser(currentChatVisibleAddress, ""));
		CoordUser cu = listOfActiveUsers.get(place);
		cu.chat_downloaded = false;
		listOfActiveUsers.set(place, cu);
		
		disable_chatArea();
	
		currentChatPanel.process_exitCurrentChat();
		currentChatVisibleAddress = "";
	}
	
	//---------------little Actions  ---------------------------------------------
	
	private void display_distantPseudoCurrentChat(String pseudo)
	{
		if(pseudo.equals(""))
		{
			chatWithLabel.setText("You are not chatting");
		}
		else
		{
			chatWithLabel.setText("You are chatting with: ");
		}
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
	
	public void enable_chatArea()
	{
		currentChatPanel.enable_sendButton();
		exitCurrentChatButton.setEnabled(true);
		currentChatPanel.enable_textArea();
		//---------------TEST -------------------------
		//currentChatPanel.showConversation("<html> bravo ca écrit <br> <p color=#FF0000> toutes mes félicitations !! </p> <p color=#FFFFFF> received </p> <br> <p color=#0066FF> sent </p> <p color=#0066FF> bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb bbbbbbbbbbbbbbbbbbbbbbb bbbbbbbbbbb </p> </html>");
	}
	
	public void disable_chatArea()
	{
		currentChatPanel.disable_sendButton();
		exitCurrentChatButton.setEnabled(false);
		display_distantPseudoCurrentChat("");
		clean_errorMessage();
		currentChatPanel.disable_textArea();
		currentChatPanel.hideConversation();
	}

	
	//---------------Modifications from received messages among network --------------------
	
	private void refresh_comboBox()
	{
		listBox.removeAllItems();
		
		System.out.println("*********************************************************");
		System.out.println("refresh_comboBox " + listOfActiveUsers);
		System.out.println("refresh_comboBox " + listOfActiveUsers.size());
		System.out.println("*********************************************************");
		
		for(int i = 0 ; i < listOfActiveUsers.size() ; i++)
		{
			listBox.addItem(listOfActiveUsers.get(i));
		}
	}
	
	public void showNewActiveUser(String ipAddress, String pseudonyme)
	{
		listOfActiveUsers.add(new CoordUser(ipAddress, pseudonyme));
		
		refresh_comboBox();
		
		System.out.println("TEST/ ApplicationWindow showNewActiveUser: listofActiveUsers ....." + listOfActiveUsers);
		System.out.println("TEST/ ApplicationWindow showNewActiveUser: IP = " + ipAddress + " PSEUDO = " + pseudonyme );
		
	}
	
	public void showModificationActiveUser(String ipAddress, String pseudonyme)
	{
		System.out.println("TEST/ ApplicationWindow showModificationActiveUser /1  " + ipAddress + pseudonyme);
		System.out.println("TEST/ ApplicationWindow showModificationActiveUser /1  " + local_ipAddress);
		System.out.println("TEST/ ApplicationWindow showModificationActiveUser  /2 " + listOfActiveUsers);
		
		if (!(ipAddress.equals(local_ipAddress)))
		{
			int place = listOfActiveUsers.indexOf(new CoordUser(ipAddress, ""));
			listOfActiveUsers.set(place, new CoordUser(ipAddress, pseudonyme));
		}
		
		System.out.println("TEST/ ApplicationWindow showModificationActiveUser /3 " + ipAddress + pseudonyme);
		refresh_comboBox();
	
		System.out.println("TEST/ ApplicationWindow showModificationActiveUser: listofActiveUsers  / 4 "  + listOfActiveUsers );
		
		
		if(currentChatVisibleAddress.equals(ipAddress))
		{
			display_distantPseudoCurrentChat(pseudonyme);
		}
	}
	
	public void removeActiveUser(String ipAddress)
	{
		int place = listOfActiveUsers.indexOf(new CoordUser(ipAddress, ""));
		listOfActiveUsers.remove(place);
		listOfChats.remove(ipAddress);
		
		refresh_comboBox();
		
		if(currentChatVisibleAddress.equals(ipAddress))
		{
			process_exitCurrentChat();
		}
	}
	
	
	//Debugging Permet d'afficher les valeurs de LinkedHashMap. 
	
	public void debugging (LinkedHashMap<String,String> initialList) {
	// Affecter les valeurs à une collection
	Collection<String> c = initialList.values();

	//Créer un itérateur sur la collection
	Iterator<String> itr = c.iterator();

	System.out.println("--------------------Affichage des valeurs de la LinkedHashMap---------------------");
	/*
	//itérer la collection et afficher le résultat
	while(itr.hasNext())
	System.out.println(itr.next());
	*/
	System.out.println(initialList.toString());
	
	System.out.println("-----------------FIN DE L'AFFICHAGE--------------");
	}
}