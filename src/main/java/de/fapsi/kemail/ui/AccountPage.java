/**
 * 
 */
package de.fapsi.kemail.ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import de.fapsi.kemail.email.Account;
import edu.emory.mathcs.backport.java.util.Arrays;

/**
 * @author fapsi
 *
 */
public class AccountPage extends JPanel implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6033862591444585964L;
	
	private boolean newaccount = false;
	
	private Account account;
	
	private JTextField fieldprotocol;
	
	private JTextField fieldhost;
	
	private JTextField fielduser;
	
	private JTextField fieldport;
	
	private JPasswordField fieldpassword;
	
	private JButton savebtn;
	
	private JButton discardbtn;

	private GraphicalUserInterface gui;
	
	public AccountPage(GraphicalUserInterface gui) {
		this(gui,new Account());
		newaccount = true;
	}
	
	public AccountPage(GraphicalUserInterface gui, Account account){
		this.gui = gui;
		this.account = account;
		
		add(makePanel());
		
		showAccount();
	}
	
	private void showAccount(){
		fieldprotocol.setText(account.getProtocol());
		fieldport.setText(account.getPort());
		fieldhost.setText(account.getHost());
		fielduser.setText(account.getUser());
		fieldpassword.setText(account.getPassword());
	}

	private JPanel makePanel() {
		JPanel newone =  new JPanel();
		newone.setLayout(new GridLayout(0, 2));
		
		newone.add(new JLabel("Protocol"));
		fieldprotocol = new JTextField(15);
		newone.add(fieldprotocol);
		
		newone.add(new JLabel("Port"));
		fieldport = new JTextField(5);
		newone.add(fieldport);
		
		newone.add(new JLabel("Host"));
		fieldhost = new JTextField(15);
		newone.add(fieldhost);
		
		newone.add(new JLabel("User"));
		fielduser = new JTextField(15);
		newone.add(fielduser);
		
		newone.add(new JLabel("Password"));
		fieldpassword = new JPasswordField(15);
		newone.add(fieldpassword);
		
		discardbtn = new JButton("Discard");
		discardbtn.addActionListener(this);
		newone.add(discardbtn);
		
		savebtn = new JButton("Save");
		savebtn.addActionListener(this);
		newone.add(savebtn);
		
		return newone;
	}

	private void save() throws IOException{
		try {
			account.setProtocol(fieldprotocol.getText());
		} catch (IllegalArgumentException iae) {
			
		}
		try {
			account.setPort(fieldport.getText());
		} catch (IllegalArgumentException iae) {
			
		}
		try {
			account.setHost(fieldhost.getText());
		} catch (IllegalArgumentException iae) {
			
		}
		try {
			account.setUser(fielduser.getText());
		} catch (IllegalArgumentException iae) {
			
		}
		try {
			account.setPassword(Arrays.toString(fieldpassword.getPassword()));
		} catch (IllegalArgumentException iae) {
			
		}
		
		if (newaccount){
			gui.manager.addAccount(account);
		} else {
			gui.manager.storeAccounts();
		}
		gui.updatePage(GUIPage.START);
	}
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == savebtn){
			try {
				save();				
			} catch (IOException ioex) {
				
			}			
		} else if (e.getSource() == discardbtn){
			gui.updatePage(GUIPage.START);
		}
		
	}
}
