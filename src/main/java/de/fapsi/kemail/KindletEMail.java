/**
 * Created on 31.12.2013;
 * Many thanks to ixtab, who published similarly code in his <a>https://bitbucket.org/ixtab/ktcollectionsmgr</a> Collection Manager project.
 */
package de.fapsi.kemail;

import ixtab.jailbreak.Jailbreak;
import ixtab.jailbreak.SuicidalKindlet;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.EventQueue;
import java.lang.reflect.Field;
import java.security.AllPermission;
import java.util.Arrays;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.amazon.kindle.kindlet.KindletContext;
import com.amazon.kindle.kindlet.internal.G;
import com.amazon.kindle.kindlet.ui.KOptionPane;

import de.fapsi.kemail.firmware.UnsupportedFirmwareException;
import de.fapsi.kemail.ui.KEmailStartPage;

public class KindletEMail extends SuicidalKindlet {
	
	//TODO: create properties file

	public static final String input_accounts_file_path ="data/t.accounts";

	private boolean first_start = true;
	private KindletContext ctx;

	private Container rootContainer;
	private boolean running;
	
	public KindletEMail(){
		super(true);
	}

	protected Jailbreak instantiateJailbreak() {
		return new LocalJailbreak();
	}

	public void onCreate(KindletContext context) {
		this.ctx = context;
		this.rootContainer = this.ctx.getRootContainer();
		
		initPreUI();
	}


	private void initPreUI() {
		this.ctx.setSubTitle("View mails on your kindle.");
		
	}

	public void onStart() {
		synchronized (this) {
			if (first_start) {
				final Runnable runnable = new Runnable() {
					public void run() {
						KindletEMail.this.onInitialLongStart();
					}
				};
				EventQueue.invokeLater(runnable);
			}
		}
	}


	public void onStop() {
		synchronized (this) {
			running = false;
		}
	}

	public void onDestroy() {
		this.rootContainer.setLayout(null);
		this.rootContainer.removeAll();
		System.gc();
	}

	protected void onInitialLongStart() {
		String txt = "";
		try{
			if (jailbreak.isAvailable()) {
				if (((LocalJailbreak) jailbreak).requestPermissions()) {
					txt += "Permissions allowed";
				} else {
					showAndQuit("Kindlet jailbreak failed","The Kindlet Jailbreak failed to obtain all required permissions. Please report this error.",null);
				}
			} else {
				showAndQuit("Kindlet jailbreak required", "This application requires the Kindlet Jailbreak to be installed. This is an ADDITIONAL jailbreak that must be installed on top of the Device Jailbreak, in order to allow Kindlets to get the required permissions. Please install the Kindlet Jailbreak before using this application.", null);
			}
		} catch (UnsupportedFirmwareException e){
			showAndQuit("Firmware version", "This firmware version ("+e.getVersion()+") is currently unsupported!" , null);
		} catch (Throwable t){
			showAndQuit("Unknown", "Unknown error. Contact author." , t);
		}
		

		KEmailStartPage gui = new KEmailStartPage();
		this.rootContainer.add(gui);
        this.rootContainer.requestFocus();
        
        try{
        	txt += this.ctx.getClass().getName();
        }catch(Exception e){
        	txt += e.getMessage();
        }
        //this.rootContainer.add(new JScrollPane(new JTextArea(
        //		txt)));
        
		first_start = false;
	}

	private void showAndQuit(String title, String message, Throwable crash) {
		KOptionPane.showMessageDialog(ctx.getRootContainer(), message, title);
		if (crash != null) {
			throw new RuntimeException(crash);
		}
		try {
			Runtime.getRuntime().exec("lipc-set-prop com.lab126.appmgrd stop app://com.lab126.booklet.kindlet");
		} catch (Throwable ignored) {
		}
	}
	
	private static class LocalJailbreak extends Jailbreak {

		public boolean requestPermissions() {
			boolean ok = getContext().requestPermission(new AllPermission());
			return ok;
		}

	}
}
