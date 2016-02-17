package org.azkfw.rakrak.analyze;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Kawakicchi
 */
public final class SystemProperty {

	private List<String> xpds;
	private List<String> xwds;
	private List<String> xmds;
	private List<String> xdds;

	public String toString() {
		String ls = "\n";
		try {
			ls = System.getProperty("line.separator");
		} catch (SecurityException e) {
		}

		StringBuffer s = new StringBuffer(this.getClass().getName() + ls);
		s.append("XPD ---------------" + ls);
		for (String xpd : xpds) {
			s.append("  " + xpd + ls);
		}
		s.append("XWD ---------------" + ls);
		for (String xwd : xwds) {
			s.append("  " + xwd + ls);
		}
		s.append("XMD ---------------" + ls);
		for (String xmd : xmds) {
			s.append("  " + xmd + ls);
		}
		s.append("XDD ---------------" + ls);
		for (String xdd : xdds) {
			s.append("  " + xdd + ls);
		}
		return s.toString();
	}

	public SystemProperty() {
		xpds = new ArrayList<String>();
		xwds = new ArrayList<String>();
		xmds = new ArrayList<String>();
		xdds = new ArrayList<String>();
	}

	public void addXPD(final String name) {
		xpds.add(name);
	}

	public void setXPDList(final List<String> list) {
		xpds = list;
	}

	public List<String> getXPDList() {
		return xpds;
	}

	public void addXWD(final String name) {
		xwds.add(name);
	}

	public void setXWDList(final List<String> list) {
		xwds = list;
	}

	public List<String> getXWDList() {
		return xwds;
	}

	public void addXMD(final String name) {
		xmds.add(name);
	}

	public void setXMDList(final List<String> list) {
		xmds = list;
	}

	public List<String> getXMDList() {
		return xmds;
	}

	public void addXDD(final String name) {
		xdds.add(name);
	}

	public void setXDDList(final List<String> list) {
		xdds = list;
	}

	public List<String> getXDDList() {
		return xdds;
	}
}