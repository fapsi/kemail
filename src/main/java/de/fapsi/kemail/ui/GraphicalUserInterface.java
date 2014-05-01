/**
 * 
 */
package de.fapsi.kemail.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

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
public class GraphicalUserInterface extends JPanel {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -1016998773864645201L;

	private SearchBarState searchbarstate = SearchBarState.TITLE_AND_SEARCH;
	
	private GUIPage state = GUIPage.START;
	
	protected Container rootcontainer;
	
	protected KindletContext kindletcontext;
	
	protected BookletContext bookletcontext;
	
	protected MailAccountManager manager;

	public GraphicalUserInterface (KindletContext kindletcontext){
		setLayout(new BorderLayout());
		setBookletContext();
		
		this.kindletcontext = kindletcontext;
		
		try {
			this.manager = new MailAccountManager();
		} catch (ClassNotFoundException e) {
		} catch (IOException e) {
		}
		//setPreferredSize(new Dimension(748,1024));
	}
	
	public void init(){
		this.rootcontainer = kindletcontext.getRootContainer();
		this.rootcontainer.setLayout(new BorderLayout());
		this.rootcontainer.add(this , BorderLayout.CENTER);
		updatePage(null);
		
	}
	
	private void setBookletContext(){
		this.bookletcontext = KindletBooklet.getInstance().getBookletContext();
	}
	
	public void updatePage(GUIPage newstate,Object params){
		this.state = newstate;
		updatePage(params);
	}
	
	private void updatePage(Object params){
		removeAll();
		validate();
		System.gc();
		switch (state){
			case START:
				add(new StartPage(this), BorderLayout.CENTER);
			break;
			case CREATE_ACCOUNT:
				JPanel pane = new AccountPreferencesPage(this);
				add(pane, BorderLayout.CENTER);
			break;
			case OVERVIEW_ACCOUNT:
				add(new AccountOverviewPage(this,params), BorderLayout.CENTER);
			break;
			default:
			break;
		}
		this.rootcontainer.validate();
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
