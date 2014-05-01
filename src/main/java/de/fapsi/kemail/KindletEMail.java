/**
 * Created on 31.12.2013;
 * Many thanks to ixtab, who published very similarly code in his <a>https://bitbucket.org/ixtab/ktcollectionsmgr</a> Collection Manager project.
 */
package de.fapsi.kemail;

import ixtab.jailbreak.Jailbreak;
import ixtab.jailbreak.SuicidalKindlet;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.security.AllPermission;

import javax.swing.JLabel;

import com.amazon.kindle.kindlet.KindletContext;
import com.amazon.kindle.kindlet.ui.KOptionPane;

import de.fapsi.kemail.firmware.UnsupportedFirmwareException;
import de.fapsi.kemail.lipc.LipcController;
import de.fapsi.kemail.ui.GraphicalUserInterface;

public class KindletEMail extends SuicidalKindlet {
	//TODO: properties file
	public static final String input_accounts_file_path ="KEmail/data/t.accounts";	
	public static final String documentsroot = "/mnt/us/";
	public static final String data_file_path = "KEmail/data/";
	public static final String error_mkk_failed_title = "Mobileread Kindlet Kit Failed";	
	public static final String error_mkk_failed = "The Mobileread Kindlet Kit failed to obtain all required permissions. Please report this error.";
	public static final String error_mkk_required_title = "Mobileread Kindlet Kit Required";
	public static final String error_mkk_required = "This application requires the Mobileread Kindlet Kit be installed. Please install the MobileRead Kindlet Kit before using this application.";
	public static final String error_firmware_version_unsupported_title = "Firmware Version Unsupported";
	public static final String error_firmware_version_unsupported = "This firmware version  is currently  not supported!";
	public static final String error_unknown_title = "Unknown";
	public static final String error_unknown = "Unknown error. Contact author.";
	
	private boolean first_start = true;
	
	private KindletContext ctx;

	private Container rootContainer;
	
	private GraphicalUserInterface gui;

	private JLabel centerLabel;
	
	public KindletEMail(){
		super(true);
	}
	
	protected Jailbreak instantiateJailbreak() {
		return new LocalJailbreak();
	}

	protected void onCreate(KindletContext context) {
		this.ctx = context;
		this.rootContainer = this.ctx.getRootContainer();
		this.centerLabel = new JLabel("KEmail");
		initPreUI();
	}

	protected void onStart() {
		synchronized (this) {
			if (first_start) {
				new Thread() {
					public void run() {
						try {
							while (!(rootContainer.isValid() && rootContainer.isVisible())) {
								Thread.sleep(100);
							}
							Thread.sleep(2000);
						} catch (Exception e) {}
					
						final Runnable runnable = new Runnable() {
							public void run() {
								KindletEMail.this.onInitialLongStart();
							}
						};
						EventQueue.invokeLater(runnable);
					}
				}.start();
				first_start = false;
			}
		}
	}

	protected void onStop() {
		synchronized (this) {
			gui.onStop();
		}
	}

	protected void onDestroy() {
		gui = null;
		this.rootContainer.setLayout(null);
		this.rootContainer.removeAll();
		System.gc();
	}

	protected void onInitialLongStart() {
		try{
			if (jailbreak.isAvailable()) {
				if (((LocalJailbreak) jailbreak).requestPermissions()) {
					ctx.getRootContainer().removeAll();
					
					gui = new GraphicalUserInterface(ctx);
					
					gui.init();
				} else {
					showAndQuit(error_mkk_failed_title , error_mkk_failed , null);
				}
			} else {
				showAndQuit(error_mkk_required_title, error_mkk_required , null);
			}
		} catch (UnsupportedFirmwareException e){
			showAndQuit(error_firmware_version_unsupported_title, error_firmware_version_unsupported 
					+ " " + e.getVersion(), null);
		} catch (Throwable t){
			showAndQuit(error_unknown_title, error_unknown , t);
		}
	}

	private void showAndQuit(String title, String message, Throwable crash) {
		setCentralMessage(title);
		KOptionPane.showMessageDialog(ctx.getRootContainer(), 
				message + (crash!=null?crash.getMessage():""), 
				title);
		if (crash != null) {
			throw new RuntimeException(crash);
		}
		LipcController.getInstance().closeBookletKindlet();
	}
	
	private void setCentralMessage(String centered) {
		centerLabel.setText(centered);
		rootContainer.validate();
	}
	
	private void initPreUI() {
		rootContainer.removeAll();
		centerLabel.setFont(new Font(centerLabel.getFont().getName(),
				Font.BOLD, centerLabel.getFont().getSize() + 6));

		rootContainer.setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.fill |= GridBagConstraints.VERTICAL;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;

		rootContainer.add(centerLabel, gbc);
		rootContainer.validate();
	}
	
	private static class LocalJailbreak extends Jailbreak {

		public boolean requestPermissions() {
			boolean ok = getContext().requestPermission(new AllPermission());
			return ok;
		}

	}
}
