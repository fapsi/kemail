/**
 * 
 */
package de.fapsi.kemail.ui;

import java.awt.Container;
import java.util.ArrayList;

import de.fapsi.kemail.KindletEMail;
import de.fapsi.kemail.email.Account;
import de.fapsi.kemail.email.MailAccountManager;

/**
 * @author fapsi
 * Wrapper to switch pages
 */
public class GraphicalUserInterface {
	
	private GUIPage state = GUIPage.START;
	
	private KindletEMail kemail;
	
	protected MailAccountManager manager;

	public GraphicalUserInterface (KindletEMail kemail){
		this.kemail = kemail;
		updatePage();
	}
	
	public void updatePage(GUIPage newstate){
		this.state = newstate;
		updatePage();
	}
	
	private void updatePage(){
		Container rootcontainer = kemail.getContainer();
		rootcontainer.removeAll();
		switch (state){
			case START:
				rootcontainer.add(new StartPage(this));
			break;
		}
		
	}
}
