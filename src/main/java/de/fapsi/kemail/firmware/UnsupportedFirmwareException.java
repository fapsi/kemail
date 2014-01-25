/**
 * Copied from ixtab 
 * <a>https://bitbucket.org/ixtab/ktcollectionsmgr</a>
 */
package de.fapsi.kemail.firmware;

public class UnsupportedFirmwareException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	private final String version;
	
	public UnsupportedFirmwareException(String version) {
		super();
		this.version = version == null ?  null : version.trim();
	}

	public String getVersion() {
		return version == null ? "UNKNOWN" : version;
	}

}
