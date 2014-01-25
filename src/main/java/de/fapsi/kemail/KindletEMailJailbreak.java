/**
 * 
 */
package de.fapsi.kemail;

import java.security.AllPermission;

import ixtab.jailbreak.Jailbreak;

/**
 * @author fapsi
 *
 */
public class KindletEMailJailbreak extends Jailbreak {
	public boolean enable() {
		if (!super.enable()) {
			return false;
		}
		return getContext().requestPermission(new AllPermission());
	}
}
