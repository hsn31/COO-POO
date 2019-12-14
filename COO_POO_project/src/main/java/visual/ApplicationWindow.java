package visual;

import java.awt.*;
import java.awt.event.WindowListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.net.*;

import network.*;

public class ApplicationWindow extends Frame
{
	private static ApplicationWindow app;
	private static JLabel labelInfo;
	private static JLabel labelError;
	private Dialog login;
	private TextField box;
    private static network.NetworkManager discovery;
	   
	 class Beginner implements ActionListener
   {
	  
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
					
   
   
   
	
}