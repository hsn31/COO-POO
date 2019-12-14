package visual;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import network.*;

import java.io.IOException;
import java.text.DateFormat;
import java.util.*;


public class VisualInterface extends JFrame {

	private JLabel jLabelBienvenue;
	private JPanel jNorthPanel;
	private JList<Account> jListConnectedUser;
	private DefaultListModel<Account> lm;
	private JPanel jPanelBottom;
	private JTextArea jTextArea;
	private ArrayList<Account> connectedUsers = new ArrayList<>();

	public VisualInterface() {
		super("Clavardage");

		this.setLayout(new BorderLayout());

		this.jNorthPanel = new JPanel(new GridBagLayout());
		this.jLabelBienvenue = new JLabel("Bienvenue");
		jNorthPanel.add(jLabelBienvenue);

		this.getContentPane().add(jNorthPanel, BorderLayout.NORTH);

		this.lm = new DefaultListModel<>();

		this.jListConnectedUser = new JList<>(lm);
		JScrollPane jsp = new JScrollPane(jListConnectedUser);
		jListConnectedUser.setAutoscrolls(true);
		jsp.setPreferredSize(new Dimension(100, 100));
		this.getContentPane().add(jsp, BorderLayout.WEST);
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

		this.jPanelBottom = new JPanel();
		this.getContentPane().add(jPanelBottom, BorderLayout.SOUTH);

		this.jTextArea = new JTextArea();
		jTextArea.setEditable(false);
		
		JScrollPane jspTa = new JScrollPane(jTextArea);		
		this.getContentPane().add(jspTa, BorderLayout.CENTER);

		ActionListener al = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Date date = new Date();
				DateFormat shortDateFormat = DateFormat.getDateTimeInstance(
				        DateFormat.SHORT,
				        DateFormat.SHORT);
				jTextArea.setText(jTextArea.getText() +shortDateFormat.format(date) + " -- Me : " + "\n");
		
				
			}
		};


		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				System.exit(0);
			}
		});

		this.pack();
		this.setVisible(true);
		this.setSize(500, 500);

	}

	public void updateTextArea(String mess) {
		jTextArea.setText(jTextArea.getText() + mess + "\n");
	}

	public void updateTextAreaWithNewText(String mess) {
		jTextArea.setText(mess);
	}

	
	public void updateConnectedUsers(ArrayList<Account> connectedUsers) {

		this.connectedUsers = connectedUsers;
		this.lm.clear();
		for (Account u : connectedUsers) {
			lm.addElement(u);
		}

	}
}
