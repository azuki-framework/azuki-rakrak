package org.azkfw.rakrak.analyze;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.azkfw.rakrak.analyze.entity.MSGEntity;

public class Message {

	private String id;
	private List<MSGEntity> entitys;

	public Message(final String id) {
		this.id = id;
		entitys = new ArrayList<MSGEntity>();
	}

	public void add(final MSGEntity entity) {
		entitys.add(entity);
		
		Collections.sort(entitys, new Comparator<MSGEntity>() {
			@Override
			public int compare(MSGEntity o1, MSGEntity o2) {
				int i = o1.getId().compareTo(o2.getId());
				if (0 != i) {
					return i;
				} else {
					if ((null == o1.getLang() || 0 == o1.getLang().length()) &&
						(null != o2.getLang() && 0 < o2.getLang().length())) {
						return 1;
					} else if ((null == o2.getLang() || 0 == o2.getLang().length()) &&
							(null != o1.getLang() && 0 < o1.getLang().length())) {
						return -1;
					}
					return 0;
				}
			}
		});
	}

	public String getId() {
		return id;
	}

	public List<MSGEntity> getEntitys() {
		return entitys;
	}

	public MSGEntity getDefaultEntity() {
		MSGEntity msg = null;
		for (MSGEntity e : entitys) {
			if (null == e.getLang() || 0 == e.getLang().length()) {
				msg = e;
				break;
			}
		}
		if (null == msg && 0 < entitys.size()) {
			msg = entitys.get(0);
		}
		return msg;
	}

	public String toString() {
		String ls = "\n";
		try {
			ls = System.getProperty("line.separator");
		} catch (SecurityException e) {
		}

		StringBuilder s = new StringBuilder();
		for (MSGEntity entity : entitys) {
			s.append(String.format("MSG : %s", entity.getValue())).append(ls);
			s.append(String.format("  ID : %s", entity.getId())).append(ls);
			s.append(String.format("  MODE : %s", entity.getMode())).append(ls);
			s.append(String.format("  LANG : %s", entity.getLang())).append(ls);
		}
		return s.toString();
	}
}
