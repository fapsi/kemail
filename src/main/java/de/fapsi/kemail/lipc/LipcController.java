/**
 * 
 */
package de.fapsi.kemail.lipc;

import java.net.URI;
import java.net.URISyntaxException;

import com.amazon.kindle.util.lipc.LipcException;
import com.amazon.kindle.util.lipc.LipcService;
import com.amazon.kindle.util.lipc.LipcSource;

/**
 * @author fapsi
 *
 */
public class LipcController {
	
	private static LipcController instance = null;
	
	private LipcSource source;
	
	private LipcController() {
		source = LipcService.getInstance().getDefaultSource();
	}
	
	public static synchronized LipcController getInstance() {
        if (instance == null) {
            instance = new LipcController();
        }
        return instance;
    }
	
	@Deprecated
	//TODO: java-vice lipc-communication
	public void closeBookletKindlet(){
		try {
			Runtime.getRuntime()
					.exec("lipc-set-prop com.lab126.appmgrd stop app://com.lab126.booklet.kindlet");
		} catch (Throwable ignored) {
		}
	}
	
	public boolean closeApplication(URI app){
		try {
			source.getTarget("com.lab126.appmgrd").setProperty("stop", app.toString());
		} catch (LipcException e) {
			return false;
		}
		return true;
	}
	
	public boolean closeKindlet(){
		URI kindleturi;
		try {
			kindleturi = new URI("app", "com.lab126.booklet.kindlet", null, null);
		} catch (URISyntaxException e) {
			return false;
		}
		return closeApplication(kindleturi);
	}
	
	public void createSystemAlert(){
		
	}
	public boolean setWLANState(){
		return false;
	}
}
