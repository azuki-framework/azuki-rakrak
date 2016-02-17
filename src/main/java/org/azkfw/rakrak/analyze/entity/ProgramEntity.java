package org.azkfw.rakrak.analyze.entity;

import java.util.ArrayList;
import java.util.List;

public class ProgramEntity {

	private String name;
	private List<ProgramTitleEntity> titles;
	private List<ProgramPatternEntity> patterns;
	private List<ProgramPPEntity> pps;

	public ProgramEntity() {
		name = null;
		titles = new ArrayList<ProgramTitleEntity>();
		patterns = new ArrayList<ProgramPatternEntity>();
		pps = new ArrayList<ProgramPPEntity>();
	}

	public void setName(final String value) {
		name = value;
	}

	public String getName() {
		return name;
	}

	public void addTitle(final ProgramTitleEntity title) {
		titles.add(title);
	}

	public List<ProgramTitleEntity> getTitleList() {
		return titles;
	}

	public void addPattern(final ProgramPatternEntity pattern) {
		patterns.add(pattern);
	}

	public List<ProgramPatternEntity> getPatternList() {
		return patterns;
	}

	public void addPP(final ProgramPPEntity pp) {
		pps.add(pp);
	}

	public List<ProgramPPEntity> getPPList() {
		return pps;
	}

	public ProgramPPEntity getDefaultPP() {
		ProgramPPEntity pp = null;
		for (ProgramPPEntity p : pps) {
			if (null == p.getPage()) {
				pp = p;
				break;
			}
		}
		return pp;
	}

	public List<String> getPlugins() {
		List<String> plugins = new ArrayList<String>();
		for (ProgramPPEntity p : pps) {
			String plugin = p.getPlugin();
			if (null != plugin && 0 < plugin.length()) {
				plugins.add(plugin);
			}
		}
		return plugins;
	}

}
