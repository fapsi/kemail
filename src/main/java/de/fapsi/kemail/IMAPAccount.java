package de.fapsi.kemail;

import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.imap.IMAPClient;

public class IMAPAccount extends IMAPClient{
	
	private String username;
	private String address;
	private String password;
	private String hostname;
	private int port;

	public IMAPAccount(String username, String address,String password,String hostname,int port){
		super();
		this.username = username;
		this.address =  address;
		this.password = password;
		
		this.hostname = hostname;
		this.port = port;
	}
	
	public void connect() throws SocketException, IOException{
		
		connect(hostname);
	}
	
	public boolean login() throws IOException{
		return login(username, password);
	}
	
	public static void main(String[] args) throws SocketException, IOException{
		IMAPAccount account = new IMAPAccount("fapsi@go4more.de", "fapsi@go4more.de", "31_morPsnS", "imap.1und1.de", 993);
		account.addProtocolCommandListener(new PrintCommandListener(System.out, true));
		account.connect();
		account.login();
		System.out.println(account.select("inbox"));
		System.out.println(account.examine("inbox"));
		System.out.println(account.status("inbox", new String[]{"MESSAGES"}));
	}
}
