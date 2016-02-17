package org.azkfw.rakrak.analyze;

import org.azkfw.rakrak.analyze.entity.ProgramEntity;
import org.azkfw.rakrak.analyze.entity.ProgramPatternEntity;
import org.azkfw.rakrak.analyze.entity.ProgramTitleEntity;

public class Program {

	private String name;
	private boolean popupFlag;
	private ProgramEntity entity;

	public Program(final ProgramEntity entity, final boolean popup) {
		this.name = entity.getName();
		this.popupFlag = popup;
		this.entity = entity;
	}

	public String getName() {
		return name;
	}

	public ProgramEntity getEntity() {
		return entity;
	}

	public boolean isPopupWindow() {
		return popupFlag;
	}
	
	public ProgramTitleEntity getDefaultTitle() {
		ProgramTitleEntity title = null;
		for (ProgramTitleEntity t : entity.getTitleList()) {
			if (null == t.getLang() || "0".equals(t.getLang())) {
				title = t;
				break;
			}
		}
		if (null == title) {
			if (0 < entity.getTitleList().size()) {
				title = entity.getTitleList().get(0);
			}
		}
		return title;
	}
	
	public String toString() {
		String ls = "\n";
		try {
			ls = System.getProperty("line.separator");
		} catch (SecurityException e) {
		}

		StringBuilder s = new StringBuilder();

		s.append(String.format("Program : %s", name)).append(ls);
		if (null != getDefaultTitle()) {
			s.append(String.format("  Title : %s", getDefaultTitle().getValue())).append(ls);
		}

		StringBuilder buf = new StringBuilder();
		for (ProgramPatternEntity ptn : entity.getPatternList()) {
			if (0 < buf.length()) {
				buf.append(", ");
			}
			buf.append(ptn.getValue());
		}
		s.append(String.format("  Pattern : %s", buf.toString())).append(ls);

		return s.toString();
	}
}
