package org.azkfw.rakrak.analyze;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.azkfw.rakrak.analyze.entity.DDEntity;

public class Items {
	
	private Map<String, Item> items;

	public Items() {
		items = new HashMap<String, Item>();
	}

	public void add(final DDEntity dd) {
		Item item = new Item(dd);
		items.put(item.getName(), item);
	}

	public List<Item> toList() {
		List<Item> list = new ArrayList<Item>();

		for (Item item : items.values()) {
			list.add(item);
		}

		Collections.sort(list, new Comparator<Item>() {
			@Override
			public int compare(final Item o1, final Item o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});

		return list;
	}
}
