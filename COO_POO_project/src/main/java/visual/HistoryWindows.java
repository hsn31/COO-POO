package visual;

import java.awt.*;
import java.util.*;

import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.*;

@SuppressWarnings("serial")
public class HistoryWindows extends Frame
{
   private ArrayList<String> listMessages;
   private Dialog log;

   public class MyButtonExit implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      { 
         log.dispose();
      }
   }

	WindowListener exit = new WindowAdapter() {


	public void windowClosing(WindowEvent e) {
		     log.dispose();
		}
	};

	public HistoryWindows(ArrayList<String> l)
	{
		this.listMessages = l;
        log = new Dialog(this);
        JLabel input = new JLabel(listMessages.get(0), JLabel.CENTER);
		JLabel inputBis = new JLabel(listMessages.get(1), JLabel.CENTER);
        log.setLayout(new GridLayout(4, 1));   
		TextArea mDisplay = new TextArea();
		mDisplay.setRows(10);
        JButton exit = new JButton("Exit");
		exit.addActionListener(new MyButtonExit());
        log.setSize(850, 450);
        log.add(input);
		log.add(inputBis);
		log.add(mDisplay);
		for(String m: listMessages) {
			mDisplay.append(m + "\n");
		}
		log.add(exit);    
		log.setVisible(true);
		log.addWindowListener((WindowListener) exit);
	}

}