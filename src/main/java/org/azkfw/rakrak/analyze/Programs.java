package org.azkfw.rakrak.analyze;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.azkfw.rakrak.analyze.entity.ProgramEntity;

public class Programs {

	private Map<String, Program> programs;

	public Programs() {
		programs = new HashMap<String, Program>();
	}

	public void addWindow(final ProgramEntity entity) {
		Program program = new Program(entity, false);
		programs.put(program.getName(), program);
	}
	public void addPopupWindow(final ProgramEntity entity) {
		Program program = new Program(entity, true);
		programs.put(program.getName(), program);
	}

	public List<Program> toList() {
		List<Program> list = new ArrayList<Program>();

		for (Program program : programs.values()) {
			list.add(program);
		}

		Collections.sort(list, new Comparator<Program>() {
			@Override
			public int compare(final Program o1, final Program o2) {
				if ( ! o1.isPopupWindow() && o2.isPopupWindow() ) {
					return -1;
				} else if ( o1.isPopupWindow() && ! o2.isPopupWindow() ) {
					return 1;
				} else {
					return o1.getName().compareTo(o2.getName());
				}
			}
		});

		return list;
	}
	
	public List<Program> toListWindow() {
		List<Program> list = new ArrayList<Program>();

		for (Program program : programs.values()) {
			if (!program.isPopupWindow()) {
				list.add(program);
			}
		}

		Collections.sort(list, new Comparator<Program>() {
			@Override
			public int compare(final Program o1, final Program o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});

		return list;
	}

	public List<Program> toListPopupWindow() {
		List<Program> list = new ArrayList<Program>();

		for (Program program : programs.values()) {
			if (program.isPopupWindow()) {
				list.add(program);
			}
		}

		Collections.sort(list, new Comparator<Program>() {
			@Override
			public int compare(final Program o1, final Program o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});

		return list;
	}
}
