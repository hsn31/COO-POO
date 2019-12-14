package visual;

import javax.swing.*;
import java.awt.*;
import java.net.*;


public class ChatWindow extends JPanel {

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
	
}
