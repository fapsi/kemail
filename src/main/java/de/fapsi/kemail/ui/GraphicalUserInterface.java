/**
 * 
 */
package de.fapsi.kemail.ui;

import java.awt.Container;
import java.io.IOException;

import javax.swing.JFrame;

import com.amazon.agui.swing.KindleFrameFactory;
import com.amazon.agui.swing.KindleFrameFactory.PersistentChromeMode;
import com.amazon.kindle.booklet.BookletContext;
import com.amazon.kindle.kindlet.KindletContext;
import com.amazon.kindle.kindlet.internal.KindletBooklet;
import com.amazon.kindle.restricted.booklet.BookletContextExtension;

import de.fapsi.kemail.email.MailAccountManager;

/**
 * @author fapsi
 * Wrapper to switch pages
 */
public class GraphicalUserInterface {
	
	private SearchBarState searchbarstate = SearchBarState.TITLE_AND_SEARCH;
	
	private GUIPage state = GUIPage.START;
	
	protected Container rootcontainer;
	
	protected KindletContext kindletcontext;
	
	protected BookletContext bookletcontext;
	
	protected MailAccountManager manager;

	public GraphicalUserInterface (KindletContext kindletcontext){
		setBookletContext();
		
		this.kindletcontext = kindletcontext;
		
		try {
			this.manager = new MailAccountManager();
		} catch (ClassNotFoundException e) {
		} catch (IOException e) {
		}
		this.rootcontainer = kindletcontext.getRootContainer();
		updatePage();
	}
	
	private void setBookletContext(){
		this.bookletcontext = KindletBooklet.getInstance().getBookletContext();
	}
	
	public void updatePage(GUIPage newstate){
		this.state = newstate;
		updatePage();
	}
	
	private void updatePage(){
		rootcontainer.removeAll();
		System.gc();
		switch (state){
			case START:
				rootcontainer.add(new StartPage(this));
			break;
			case CREATE_ACCOUNT:
				rootcontainer.add(new AccountPage(this));
			default:
			break;
		}
		rootcontainer.validate();
	}
	
	public void setSearchBarState(SearchBarState wanted){
		if (bookletcontext == null || searchbarstate == wanted)
			return;
		BookletContextExtension contextext = (BookletContextExtension) bookletcontext;
		JFrame frame = contextext.Em(); 
		//TODO: use PersistentChromeMode.valueOf() to get rid of switch. Note: have to import utilities.jar
		switch (wanted){
			case NO_HEADER:
				KindleFrameFactory.setPersistentChromeMode(frame,KindleFrameFactory.PersistentChromeMode.NO_HEADER);
				break;
			case TITLE_AND_SEARCH:
				KindleFrameFactory.setPersistentChromeMode(frame,KindleFrameFactory.PersistentChromeMode.TITLE_AND_SEARCH);
				break;
			case TITLE_ONLY:
				KindleFrameFactory.setPersistentChromeMode(frame,KindleFrameFactory.PersistentChromeMode.TITLE_ONLY);
				break;
		}
		searchbarstate=wanted;
	}

	public void onStop() {
	}	
}
