/**
 * 
 */
package de.fapsi.kemail.email;

import java.util.List;
import java.util.LinkedList;
import java.util.Properties;

import javax.mail.FetchProfile;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;




/**
 * @author fapsi
 *
 */
public class IMAPController {
	
	private static final String protocol = "imaps";
	
	private static final int amountmessagesfetched = 10;
	
	private Session session;
	
	private Store remotestore = null;
	
	private Store localstore = null;
	
	private String lasterror = null;
	
	private Folder localINBOX = null;
	
	private Account account = null;
	
	public IMAPController(int account_id) throws MessagingException{
		Properties props = new Properties();
		props.put("mail.debug", "true");	
		
		session = Session.getInstance(props, null);
		remotestore = session.getStore(protocol);
		
		readMailAccount(account_id);
		
		createLocalStore();
	}
	
	private void readMailAccount(int account_id) {
		
	}

	private void createLocalStore() throws MessagingException {
		Properties p = new Properties();
		p.setProperty("mstor.mbox.metadataStrategy", "XML");
		p.setProperty("mstor.metadata", "enabled");
		/*
		this.properties.setProperty("mail.store.protocol", "mstor");
        this.properties.setProperty("mstor.mbox.metadataStrategy", "none");
        this.properties.setProperty("mstor.mbox.cacheBuffers", "disabled");
        this.properties.setProperty("mstor.mbox.bufferStrategy", "mapped");
        this.properties.setProperty("mstor.metadata", "disabled");
        this.properties.setProperty("mstor.mozillaCompatibility", "enbled");
		 */
		Session session = Session.getDefaultInstance(p);
		localstore = session.getStore(new URLName("mstor:"  + System.getProperty("user.dir") + "/MyStore"));
		
			localstore.connect();
			localINBOX = localstore.getDefaultFolder().getFolder("INBOX");
			
			if (!localINBOX.exists())
				localINBOX.create(Folder.HOLDS_MESSAGES);
			
			localINBOX.open(Folder.READ_WRITE);
			
			//localstore.close();
		
		
	}

	public boolean connect(){
		if (remotestore != null){
				try {
					remotestore.connect(account.host, account.port, account.user, account.password);
				} catch (MessagingException e) {
					lasterror = e.getMessage();
					return false;
				}
		}
		return true;
	}
	public List<Message> readMessages(int site) throws MessagingException{
		return readMessages(site, amountmessagesfetched);
	}
	
	public List<Message> readMessages(int site, int amountmessages) throws MessagingException{
		List<Message> result = new LinkedList<Message>();
		
		// examine rootfolder
		Folder rootfolder = remotestore.getDefaultFolder();
		if (rootfolder == null)
			return result;
		
		// check arguments
		if (site < 1 || amountmessages < 1)
			return result;
		
		// open inbox folder
		Folder inbox = rootfolder.getFolder("INBOX");
		inbox.open(Folder.READ_ONLY);
		
		// read amount messages
		int totalmessages = inbox.getMessageCount();
		
		 // Attributes & Flags for all messages ..
		Message[] msgs = inbox.getMessages(1, 2);
		
		// Use a suitable FetchProfile
		FetchProfile fp = new FetchProfile();
		fp.add(FetchProfile.Item.ENVELOPE);
		fp.add(FetchProfile.Item.FLAGS);
		fp.add("Message-ID");
		fp.add("X-Mailer");
		inbox.fetch(msgs, fp);
		
		if (localINBOX == null)
			System.err.println("NULL!!!");
		
		inbox.copyMessages(msgs, localINBOX);
		for (Message m : msgs){
			result.add(m);
		}
		inbox.close(false);
		
		
		localINBOX.close(false);
		localstore.close();
		
		return result;
	}
	
	public boolean disconnect(){
		try {
			remotestore.close();
		} catch (MessagingException e) {
			lasterror = e.getMessage();
			return false;
		}
		return true;
	}
	
	public boolean destroy(){
		return false;
		
	}

}
