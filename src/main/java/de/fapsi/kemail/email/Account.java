/**
 * 
 */
package de.fapsi.kemail.email;

import java.io.Serializable;

/**
 * @author fapsi
 *
 */
public class Account implements Serializable{
	
	private static final long serialVersionUID = -807568521251882187L;

	private static final int defaultport = 993;
	
	private static final String defaultprotocol = "imaps";
	
	protected String hash;
	
	protected String protocol;
	
	protected String host;
	
	protected int port;
	
	protected String user;
	
	protected String password;
	
	public Account(String host,String user,String password){
		this(defaultprotocol,host,defaultport,user,password);
	}
	
	public Account(String protocol,String host,int port,String user,String password){
		this.host = host;
		this.user = user;
		this.password = password;	
		this.port = port;
		this.protocol = protocol;
	}
	
	public String getHost(){
		return host;
	}
}
