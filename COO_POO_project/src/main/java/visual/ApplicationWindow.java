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

public class ApplicationWindow implements ActionListener
{

	private JFrame main_window;
	private JLabel wallpaper_area;
	
	//+ JPanel JButton JLabel par zone dans la frame
	
	//DESIGN
	private ImageIcon wallpaper;
	//palette couleurs ?
	
	/*
	private enum State
	{
		BLA,
		BLO,
		BLU,
	}
	*/
	
	/*
	 * //coding_parameters : values to follow the progression
	 * private State state;
	*/
	
	public ApplicationWindow() throws FontFormatException, IOException
	{
		initialize_coding_parameters();
		
		creation_elements();
		creation_listeners();
		
		esthetic_parameters();
		design_elements();
		
		main_window.setLocation(200, 0);
		main_window.setVisible(true);
		main_window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); //!!
	}
	
	//------------------------- DIVISION OF PSEUDONYME WINDOW CONSTRUCTOR -------------------------------------------
	
	private void initialize_coding_parameters()
	{
		
	}
	
	private void creation_elements() throws FontFormatException, IOException
	{
		//DESIGN
		wallpaper = new ImageIcon("wallpaper_pseudonyme.jpg");
		
		//MAIN WINDOW
		main_window = new JFrame("pseudonyme window");
		wallpaper_area = new JLabel(wallpaper);
		
		//new : JPanel JButton JLabel par zone dans la frame
	}
	
	private void creation_listeners()
	{
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
		
		//main window
		main_window.setSize(new Dimension(1000, 700));
		main_window.setResizable(false); //??????
	}
	
	private void add_and_layout()
	{
		/*
		JPanel.add(button / JLabel); , BorderLayout.WEST);
		
		//main window
		main_window.setLayout(new GridBagLayout());
		GridBagConstraints coord = new GridBagConstraints();
		coord.gridx = 0 ;
		coord.gridy = 0 ;
		main_window.add(JLabel/JPanel, coord);
		*/
		main_window.pack(); //to keep all the size of the wallpaper
	}
	
	//--------------------------- Functions to manage the visual Interface / MainApplication ---------------------------------------
	
	/*
	public JButton get_button()
	{
		return button;
	}
	*/
	/*
	public void creation_listener_button(Visual_Interface application)
	{
		button.addActionListener(application);
	}
	*/
	
	public void close_window()
	{
		main_window.dispose();
	}
	

	//---------------------------Functions to manage the interaction with the user------------------------------
	
	/*
	private void click_on_button() throws InterruptedException
	{
		if(state_== )
		{
			
		}
		else 

	}
	*/
	
	
	private void refresh_display()
	{
		
	}
	
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
	private static ApplicationWindow app;
	private static JLabel labelInfo;
	private static JLabel labelError;
	private Dialog login;
	private TextField box;
    private static network.NetworkManager discovery;
    
    public void actionPerformed(ActionEvent event)
	{
		  
		String wantedPseudo = box.getText();
		if(wantedPseudo.equals("")) {
			labelError.setText("Impossible to login with an empty pseudo !");
		} else {
			if(discovery.getOnlineUsers().contains(wantedPseudo)) {
				labelError.setText("Impossible to login because : " + wantedPseudo + " is already Online.");
			}
			else {
				try {
					discovery.closeServer();
				}
				catch(Exception e){
					e.printStackTrace();
				}
				finally {
					new VisualInterface();
						app.dispose();
					}
				}
			}
	}
	  
	public class MyButtonExitListener implements ActionListener
   {
      public void actionPerformed(ActionEvent event)
      {
	 		try {
				discovery.closeServer();
			}
			catch(Exception e){
				e.printStackTrace();
			}
         login.dispose();
         System.exit(0);
      }
   }
   
   	WindowListener exitListener = new WindowAdapter() {

		@Override
		public void windowClosing(WindowEvent event) {
		     try {
				discovery.closeServer();
			}
			catch(Exception e){
				e.printStackTrace();
			}
         login.dispose();
         System.exit(0);
		}
	};
	
	public ApplicationWindow () throws SocketException
	{
		this.setTitle("Chat Session");	
		try {
			discovery = new network.NetworkManager();
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		(new Thread(discovery)).start();
			box = new TextField();
            login = new Dialog(this);
			labelError = new JLabel("");
            labelInfo = new JLabel("Welcome. Please enter your pseudonyme", JLabel.CENTER);
            login.setLayout(new GridLayout(0, 1));
            JButton validate = new JButton("ValidationPseudo");        
            validate.addActionListener(new MyButtonExitListener());
            JButton exit = new JButton("Exit");
            exit.addActionListener(new MyButtonExitListener());		   
            login.setSize(850, 400);
            login.add(labelInfo);   
			login.add(labelError);
			login.add(box);
			login.add(validate);
			
			login.add(exit);    
			login.setVisible(true);
			login.addWindowListener(exitListener);
			
	}

	public static void main(String argv[]) {
		try {
			app = new ApplicationWindow();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
					
   
   
   
	*/
}