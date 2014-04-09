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
	
	private static final Protocol defaultprotocol = Protocol.IMAPS;
	
	protected String hash;
	
	protected String protocol;
	
	protected String host;
	
	protected int port;
	
	protected String user;
	
	protected String password;
	
	public Account(){
		setProtocol(defaultprotocol.toString());
		setPort(defaultport);
	}
	
	public Account(String host,String user,String password){
		this(defaultprotocol.toString().toLowerCase(),host,defaultport,user,password);
	}
	
	public Account(String protocol,String host,int port,String user,String password){
		setProtocol(protocol);
		setPort(port);
		setHost(host);
		setUser(user);
		setPassword(password);
	}
	
	public void setProtocol(String protocol){
		Protocol p = Protocol.valueOf(protocol.trim().toUpperCase());
		this.protocol = p.toString().toLowerCase();
	}
	
	public void setPort(int port){
		if (port <= 0)
			throw new IllegalArgumentException();
		this.port = port;
	}
	
	public void setPort(String port) {
		setPort(Integer.parseInt(port.trim()));
	}
	
	public void setHost(String host){
		this.host = host;
	}
	
	public void setUser(String user){
		this.user = user;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	
	public String getProtocol() {
		return protocol.toString().toLowerCase();
	}
	
	public String getPort() {
		return Integer.toString(port);
	}
	
	public String getHost(){
		return host;
	}

	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}
}
