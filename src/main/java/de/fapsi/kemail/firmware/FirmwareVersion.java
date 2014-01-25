/**
 * Copied from ixtab 
 * <a>https://bitbucket.org/ixtab/ktcollectionsmgr</a>
 */
package de.fapsi.kemail.firmware;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FirmwareVersion {
	private static String filename = "/etc/prettyversion.txt";

	private FirmwareVersion() {
	}

	/**
	 * Gets the identifier of the currently running Kindle firmware, as a
	 * string.
	 * 
	 * @return a firmware identifier, like "5.0.0", "5.1.2", "5.3.2.1", etc., if
	 *         found, or <tt>null</tt> on error.
	 */
	public static String getIdentifier() {
		BufferedReader r = null;
		try {
			r = new BufferedReader(new FileReader(filename));
			for (String line = r.readLine(); line != null; line = r.readLine()) {
				// we're pretty strict here with what we recognize.
				// this may have the disadvantage of having to revise this class
				// at times,
				// but has the advantage of being sure that we only FW versions
				// that we're sure about.
				if (!line.startsWith("Kindle ")) {
					continue;
				}
				line = line.substring(7);
				int space = line.indexOf(' ');
				if (space == -1) {
					continue;
				}
				return line.substring(0, space);
			}
			return null;
		} catch (Throwable t) {
			return null;
		} finally {
			if (r != null) {
				try {
					r.close();
				} catch (IOException e) {
				}
			}
		}
	}
}
