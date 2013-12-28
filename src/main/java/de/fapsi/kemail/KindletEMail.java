package de.fapsi.kemail;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.EventQueue;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.amazon.kindle.kindlet.AbstractKindlet;
import com.amazon.kindle.kindlet.KindletContext;

public class KindletEMail extends AbstractKindlet {

	private boolean first_start = true;
	private KindletContext ctx;

	private Container rootContainer;
	private boolean running;

	@Override
	public void create(final KindletContext context) {
		this.ctx = context;
		this.rootContainer = this.ctx.getRootContainer();
	}

	@Override
	public void start() {
		synchronized (this) {
			super.start();
			if (first_start) {
				final Runnable runnable = new Runnable() {
					public void run() {
						KindletEMail.this.initial_start();
					}
				};
				EventQueue.invokeLater(runnable);
			}
		}
	}

	public void stop() {
		synchronized (this) {
			running = false;
		}
	}

	public void destroy() {
		this.rootContainer.setLayout(null);
		this.rootContainer.removeAll();
		System.gc();
	}

	protected void initial_start() {
		this.ctx.setSubTitle("Sponsored by Fapsi");
		
		this.rootContainer.setLayout(new BorderLayout());
        this.rootContainer.requestFocus();
        this.rootContainer.add(new JScrollPane(new JTextArea("test")));
        
		first_start = false;
	}
}
