package de.fapsi.kemail.ui;

import javax.mail.MessagingException;
import javax.swing.JPanel;

import de.fapsi.kemail.email.Account;
import de.fapsi.kemail.email.IMAPController;

public class AccountOverviewPage extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3878874885011711816L;

	private Account current;

	private GraphicalUserInterface gui;
	
	private IMAPController controller;
	
	public AccountOverviewPage(GraphicalUserInterface gui,Object params){
		if (!(params instanceof Account)){
			throw new IllegalArgumentException("Expected Account parameter.");
		}
		this.gui = gui;
		this.current = (Account) params;
		
		try {
			processDedicatedFileSystem();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	private void processDedicatedFileSystem() throws MessagingException{
		controller = new IMAPController(current);
	}
	
	
	
}
