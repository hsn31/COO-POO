package visual;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.*;


public class ChatWindow implements ActionListener
{

	private JFrame main_window; 
	private JLabel wallpaper_area;
	private JTextField text_area;
	private JButton send_message;
	private JScrollPane scrollbar;
	
	// do we need something like this if message is not fine?  
	// private static JLabel labelError;
	
	
	
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
	
	public ChatWindow() throws FontFormatException, IOException
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
	
	
	
	// building the chatwindow -väinö
    main_window.add(send_message) {
    	
    }
	
	private void initialize_coding_parameters()
	{
		
	}
	
	private void creation_elements() throws FontFormatException, IOException
	{
		//DESIGN
		wallpaper = new ImageIcon("wallpaper_pseudonyme.jpg");
		
		//MAIN WINDOW

		//new : JPanel JButton JLabel par zone dans la frame
		// there need for code?
		
		
		main_window = new JFrame("titleWindow"); 
		wallpaper_area = new JLabel(wallpaper);
		
		text_area = new JTextField();
		send_message = new JButton("Send");
		scrollbar = new JScrollPane();
		


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
		main_window.pack(); //to keep all the size of the wallpaper
	}
	
	//--------------------------- Functions to manage the visual Interface / MainApplication ---------------------------------------
	
	/*
	public JButton get_button()
	{
		return button;
	}
	*/
	
	public void creation_listener_button(Visual_Interface application)
	{
		send_message.addActionListener(application);
	}
	
	
	public void close_window()
	{
		main_window.dispose();
	}
	
	// GETIT TÄHÄN
	public JTextField getTextArea()
	{
		return text_area;
	}
	public JButton getSendMessageButton()
	{
		return send_message;
	}
	public JScrollPane getScrollBar()
	{
		return scrollbar;
	}

	//---------------------------Functions to manage the interaction with the user------------------------------
	
	
	// 12.1 THIS METHOD TBD...
	private void click_on_send_button() throws InterruptedException
	{
		/*if(state_== )
		{
			
		}
		else 
*/
	}

	
	
	private void refresh_display()
	{
		
	}
	
	private void clean_alert_message()
	{
		
	}
	
	private void send_Message()
	{
		// when send-button is pressed,
		// from this method we send
		// the written text forward
	}
	
	
	// tämä nyt viimeinen 12.1
	
	
	/*public void display_error_message(String message)
	{
		labelError.setText(message);
	}
	
	public void clean_error_message()
	{
		labelError.setText("");
	} 
	
	public void clean_areaEnterPseudonyme()
	{
		areaEnterPseudonyme.setText("");
	} */
	
	
	//------------------Management of interaction with the User and Listeners---------------------------------
	
	
	public void actionPerformed(ActionEvent e) 
	{
		
		if(e.getSource().equals(send_message))
		{
			try 
			{
				click_on_send_button();
			} 
			catch (InterruptedException e1) 
			{
				e1.printStackTrace();
			}
		}
		
	}
	
	
	
	
	/*
	 * 
	 * HICHEM
	 * 
	 * 
	private JLabel jLabelPrompt;
	private JTextField jTextField;
	private JButton jButtonSend;

	public ChatWindow() {
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

		this.jLabelPrompt = new JLabel("Message : ");
		this.add(jLabelPrompt);

		this.jTextField = new JTextField();
		this.add(jTextField);
		
		this.jButtonSend = new JButton("Send");
		this.add(jButtonSend);
		
	}
	
	public JButton getJButtonSend() {
		return this.jButtonSend;
	}
	
	public JTextField getJTextField() {
		return this.jTextField;
	}
	*/
}
