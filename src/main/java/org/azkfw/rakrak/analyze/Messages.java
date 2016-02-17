package org.azkfw.rakrak.analyze;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.azkfw.rakrak.analyze.entity.MSGEntity;

public class Messages {

	private Map<String, Message> messages;

	public Messages() {
		messages = new HashMap<String, Message>();
	}

	public void add(final MSGEntity msg) {
		Message message = null;
		if (messages.containsKey(msg.getId())) {
			message = messages.get(msg.getId());
		} else {
			message = new Message(msg.getId());
			messages.put(msg.getId(), message);
		}
		message.add(msg);
	}

	public List<Message> toList() {
		List<Message> list = new ArrayList<Message>();

		for (Message message : messages.values()) {
			list.add(message);
		}

		Collections.sort(list, new Comparator<Message>() {
			@Override
			public int compare(final Message o1, final Message o2) {
				return o1.getId().compareTo(o2.getId());
			}
		});

		return list;
	}
}
