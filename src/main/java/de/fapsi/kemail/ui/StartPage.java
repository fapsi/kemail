/**
 * 
 */
package de.fapsi.kemail.ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import com.amazon.kindle.kindlet.KindletContext;

import de.fapsi.kemail.email.Account;
import de.fapsi.kemail.email.MailAccountManager;
import edu.emory.mathcs.backport.java.util.concurrent.ArrayBlockingQueue;

/**
 * @author fapsi
 *
 */
public class StartPage extends JPanel implements ActionListener{
		
	private MailAccountManager manager;
	
	private JButton exitbtn;

	private GraphicalUserInterface gui;
	
	public StartPage (GraphicalUserInterface gui){
		this.gui = gui;
		ArrayList<Account> accounts = new ArrayList<Account>();
		accounts.add(new Account("test@testos.de", "test1",	 "pw"));
		accounts.add(new Account("test@testos.com", "test2", "pw"));
		GridLayout layout = new GridLayout(0,1);
		setLayout(layout);
		
		for (Account a : accounts){
			add(new JButton(a.getHost()));
		}
		
		add(new JButton("New..."));
		
		exitbtn = new JButton("Exit");
		exitbtn.addActionListener(this);
		add(exitbtn);
		
	}
	
	/**
	 * For local tests! TODO: Delete in release! Do not commit!!
	 * @param args
	 * @throws MessagingException 
	 * @throws NoSuchProviderException 
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws Exception {
		
		MailAccountManager am = new MailAccountManager();
		
		am.accounts.add(new Account("hostfadasfaafdsfads","usersaafdsfsaaffas","pw"));
		
		am.storeAccounts();
		
		am.accounts = null;
		
		am.readAccounts();
		
		System.out.println(am.accounts.size());
		
		
		/*
		IMAPController controller = new IMAPController(1);
		
		controller.connect();
		System.out.println(controller.readMessages(1));
		controller.disconnect();*/
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == exitbtn){
			try {
				Runtime.getRuntime()
						.exec("lipc-set-prop com.lab126.appmgrd stop app://com.lab126.booklet.kindlet");
			} catch (Throwable ex) {
			}
		}
		
	}

}
