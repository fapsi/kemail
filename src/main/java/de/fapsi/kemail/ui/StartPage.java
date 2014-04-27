/**
 * 
 */
package de.fapsi.kemail.ui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.amazon.agui.swing.KindleFrameFactory;
import com.amazon.kindle.booklet.BookletContext;
import com.amazon.kindle.kindlet.internal.KindletBooklet;
import com.amazon.kindle.restricted.booklet.BookletContextExtension;

import de.fapsi.kemail.KindletEMail;
import de.fapsi.kemail.email.Account;
import de.fapsi.kemail.email.MailAccountManager;
import de.fapsi.kemail.lipc.LipcController;

/**
 * @author fapsi
 *
 */
public class StartPage extends JPanel implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3912173366496016740L;
	
	private boolean isfullscreen = false;

	private GraphicalUserInterface gui;

	private JButton newbtn;
	
	private JButton exitbtn;

	private JButton fullscreenbtn;
	
	public StartPage (GraphicalUserInterface gui){
		this.gui = gui;
		
		List<Account> accounts = gui.manager.getAccounts();
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		
		c.weightx = 0.0;
		c.weighty = 0.0;
		
		c.gridwidth = 2;
		c.gridheight = 1;
		
		c.fill = GridBagConstraints.BOTH;
		
		
		for (Account a : accounts){
			add(new JButton(a.getHost()),c);
			c.gridy++;
		}
		
		c.gridx = 0;
		c.gridwidth = 1;
		newbtn = new JButton("New...");
		newbtn.addActionListener(this);
		add(newbtn,c);
		c.gridx = 1;
		fullscreenbtn = new JButton("Fullscreen");
		fullscreenbtn.addActionListener(this);
		add(fullscreenbtn,c);
		
		c.gridx = 0;
		c.gridwidth = 2;
		
		c.gridy++;
		c.weightx = 1.0;
		c.weighty = 1.0;
		exitbtn = new JButton("Exit");
		exitbtn.addActionListener(this);
		add(exitbtn,c);	
		
		validate();
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
			LipcController.getInstance().closeBookletKindlet();
		} else if (e.getSource() == newbtn){
			gui.updatePage(GUIPage.CREATE_ACCOUNT,null);
		} else if (e.getSource() == fullscreenbtn){
			//TODO
			//gui.setSearchBarState(wanted);
		}
		
	}

}
