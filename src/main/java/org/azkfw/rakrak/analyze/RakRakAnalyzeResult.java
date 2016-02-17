package org.azkfw.rakrak.analyze;

import java.util.List;

import org.azkfw.rakrak.analyze.entity.XDDEntity;
import org.azkfw.rakrak.analyze.entity.XMDEntity;
import org.azkfw.rakrak.analyze.entity.XPDEntity;

public class RakRakAnalyzeResult {

	private List<XMDEntity> xmdEntityList;
	private List<XDDEntity> xddEntityList;
	private List<XPDEntity> xpdEntityList;
	private List<XPDEntity> xwdEntityList;
	
	private Messages messages;
	private Items items;
	private Programs programs;
	
	public void setXMDEntityList(final List<XMDEntity> list) {
		xmdEntityList = list;
	}
	public List<XMDEntity> getXMDEntityList() {
		return xmdEntityList;
	}

	public void setXDDEntityList(final List<XDDEntity> list) {
		xddEntityList = list;
	}
	public List<XDDEntity> getXDDEntityList() {
		return xddEntityList;
	}

	public void setXPDEntityList(final List<XPDEntity> list) {
		xpdEntityList = list;
	}
	public List<XPDEntity> getXPDEntityList() {
		return xpdEntityList;
	}

	public void setXWDEntityList(final List<XPDEntity> list) {
		xwdEntityList = list;
	}
	public List<XPDEntity> getXWDEntityList() {
		return xwdEntityList;
	}

	public void setMessages(final Messages messages) {
		this.messages = messages;
	}
	public Messages getMessages() {
		return messages;
	}
	public void setItems(final Items items) {
		this.items = items;
	}
	public Items getItems() {
		return items;
	}
	public void setPrograms(final Programs programs) {
		this.programs = programs;
	}
	public Programs getPrograms() {
		return programs;
	}
}
