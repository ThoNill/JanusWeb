package org.janus.gui.web;

import java.io.Serializable;

public class ChangeOfGuiElement implements Serializable{
	private ChangeKey key;
	private Serializable oldValue;
	private Serializable newValue;

	public ChangeOfGuiElement(ChangeKey key, Serializable oldValue,Serializable newValue) {
		super();
		this.key = key;
		this.oldValue =oldValue;
		this.newValue =newValue;
	}

	public int getId() {
		return key.getId();
	}
	
	public String getPropertyName() {
		return key.getField().name();
	}
	
	public ChangeKey getKey() {
		return key;
	}



	public Serializable getOldValue() {
		return oldValue;
	}
	
	public Serializable getNewValue() {
		return newValue;
	}

	@Override
	public String toString() {
		return "ChangeOfGuiElement [key=" + key + ", oldValue=" + oldValue
				+ ", newValue=" + newValue + "]";
	}


}
