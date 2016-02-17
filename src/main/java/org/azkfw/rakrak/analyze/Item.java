package org.azkfw.rakrak.analyze;

import org.azkfw.rakrak.analyze.entity.DDEntity;
import org.azkfw.rakrak.analyze.entity.DDTitleEntity;

public class Item {

	private String name;
	private DDEntity entity;

	public Item(final DDEntity entity) {
		this.name = entity.getName();
		this.entity = entity;
	}

	public String getName() {
		return name;
	}

	public DDEntity getEntity() {
		return entity;
	}
	
	public String getDefaultTitle() {
		String title = null;
		for (DDTitleEntity t : entity.getTitleList()) {
			if (null == t.getLang() || 0 == t.getLang().length()) {
				title = t.getValue();
				break;
			}
		}
		if (null == title && 0 < entity.getTitleList().size()) {
			title = entity.getTitleList().get(0).getValue();
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
		s.append(String.format("DD : %s", entity.getName())).append(ls);
		for (DDTitleEntity title : entity.getTitleList()) {
			s.append(String.format("  TITLE : %s %s", title.getLang(), title.getValue())).append(ls);
		}
		s.append(String.format("  DBFIELD : %s", entity.getDbfield())).append(ls);
		s.append(String.format("  SIZE : %s", entity.getSize())).append(ls);
		s.append(String.format("  INPUTTYPE : %s", entity.getInputType())).append(ls);
		if (null != entity.getRefWindow()) {
			s.append(String.format("  REFWINDOW : %s", entity.getRefWindow())).append(ls);
		}
		return s.toString();
	}
}
